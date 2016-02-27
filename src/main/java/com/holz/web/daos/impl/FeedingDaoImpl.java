package com.holz.web.daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.holz.web.daos.FeedingDao;
import com.holz.web.models.Feeding;
import com.holz.web.models.GroupedHerd;
import com.holz.web.models.User;

@Repository
public class FeedingDaoImpl implements FeedingDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 

	@Override
	public Feeding saveOrUpdate(Feeding currentFeeding, int farmId) {
		final Feeding feeding = currentFeeding;
		final int farm = farmId;
		if(feeding.getId() == 0){
			final String sql = "INSERT INTO FEEDING VALUES (?,?,?,?,?,?,?,?,?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(
					new PreparedStatementCreator() {
						public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
							PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
							ps.setInt(1, 0);
							ps.setTimestamp(2,toTimestamp(feeding.getFeedingTime()));
							ps.setInt(3,feeding.getBunkScore());
							ps.setDouble(4,feeding.getDeliveredAmount());
							ps.setInt(5,feeding.getUser().getId());
							ps.setBoolean(6,true);
							ps.setInt(7,feeding.getGroupedHerd().getId());
							ps.setInt(8, farm);
							ps.setTimestamp(9, toTimestamp(new Date()));
							return ps;
						}
					},keyHolder);
			currentFeeding.setId(keyHolder.getKey().intValue());
			return currentFeeding;
		} else {
			String sql = "UPDATE FEEDING SET feedingTime=?, bunkScore=?, deliveredAmount=?," 
					+ " userId=?," 
					+ " groupedHerdsId=?," 
					+ " hasLeftovers=?, "
					+ " lastUpdated=? "
					+ "WHERE feedingId=? AND farmId=?";
			this.jdbcTemplate.update(sql,feeding.getFeedingTime(),feeding.getBunkScore(),feeding.getDeliveredAmount(),
					feeding.getUser().getId(),feeding.getGroupedHerd().getId(),feeding.isHasLeftovers(),new Date(),feeding.getId(),farmId);
		}
		return null;
	}

	private java.sql.Timestamp toTimestamp(java.util.Date date)
	{
		if(date == null)
			return null;
		return new java.sql.Timestamp(date.getTime());
	}

	@Override
	public boolean userHasAccessToFeeding(int farmId, int feedingId) {
		String sql ="SELECT COUNT(*) AS accessCount FROM FEEDING F "
				+ "WHERE F.farmId = ? "
				+ "AND F.feedingId = ?";
		return this.jdbcTemplate.query(sql, new Object[]{farmId,feedingId}, new ResultSetExtractor<Boolean>() {
			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.first()){
					int accessCount = rs.getInt("accessCount");
					return (accessCount > 0);
				}
				return false;
			}
		});
	}

	@Override
	public Feeding getFeeding(int id) {
		String sql ="SELECT feedingId,feedingTime,bunkScore,deliveredAmount,userId,hasLeftovers,groupedHerdsId,farmId,lastUpdated "
				+ "FROM FEEDING F "
				+ "WHERE F.feedingId = ?";
		return this.jdbcTemplate.query(sql, new Object[]{id}, new ResultSetExtractor<Feeding>() {
			@Override
			public Feeding extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.first()){
					return buildFeeding(rs,false);
				}
				return null;
			}
		});
	}

	private Feeding buildFeeding(ResultSet rs, boolean ignoreUser) throws SQLException{
		Feeding f = new Feeding();
		f.setId(rs.getInt("feedingId"));
		f.setFeedingTime(rs.getTimestamp("feedingTime"));
		f.setBunkScore(rs.getInt("bunkScore"));
		f.setDeliveredAmount(rs.getDouble("deliveredAmount"));
		f.setLastUpdated(rs.getTimestamp("lastUpdated"));
		if(!ignoreUser){
			User user = new User();
			user.setUserId(rs.getInt("userId"));
			f.setUser(user);
		}
		f.setHasLeftovers(rs.getBoolean("hasLeftovers"));
		GroupedHerd groupedHerd = new GroupedHerd();
		groupedHerd.setId(rs.getInt("groupedHerdsId"));
		f.setGroupedHerd(groupedHerd);
		return f;
	}
	
	@Override
	public void updateFeedingNoLeftovers(int feedingId) {
		String sql = "UPDATE FEEDING SET hasLeftovers=false WHERE feedingId=?";
		this.jdbcTemplate.update(sql,feedingId);
	}

	@Override
	public List<Feeding> getAllActiveFeedings(int farmId) {
		String sql ="SELECT feedingId,feedingTime,bunkScore,deliveredAmount,U.userId,hasLeftovers,F.groupedHerdsId,lastUpdated, "
				+ "firstName, lastName "
				+ "FROM FEEDING F "
				+ "JOIN GROUPED_HERDS GH on GH.groupedHerdsId = F.groupedHerdsId "
				+ "JOIN LOCALE L ON GH.localeId = L.localeId "
				+ "JOIN USERS U ON F.userId = U.userId "
				+ "WHERE F.farmId = ? ORDER BY lastUpdated DESC";
		return this.jdbcTemplate.query(sql, new Object[]{farmId}, new ResultSetExtractor<List<Feeding>>() {
			@Override
			public List<Feeding> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Feeding> fdgs = new ArrayList<Feeding>();
				while(rs.next()){
					Feeding f = buildFeeding(rs,false);
					f.getUser().setFirstName(rs.getString("firstName"));
					f.getUser().setLastName(rs.getString("lastName"));
					fdgs.add(f);
				}
				return fdgs;
			}
		});
	}
	
	@Override
	public List<Feeding> getAllFeedingsForHerd(int farmId, int groupId) {
		String sql ="SELECT feedingId,feedingTime,bunkScore,deliveredAmount,hasLeftovers,F.groupedHerdsId,lastUpdated "
				+ "FROM FEEDING F "
				+ "JOIN GROUPED_HERDS GH on GH.groupedHerdsId = F.groupedHerdsId "
				+ "WHERE F.farmId = ? AND GH.groupedHerdsId = ? ORDER BY feedingTime ASC";
		return this.jdbcTemplate.query(sql, new Object[]{farmId,groupId}, new ResultSetExtractor<List<Feeding>>() {
			@Override
			public List<Feeding> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Feeding> fdgs = new ArrayList<Feeding>();
				while(rs.next()){
					Feeding f = buildFeeding(rs,true);
					fdgs.add(f);
				}
				return fdgs;
			}
		});
	}
}