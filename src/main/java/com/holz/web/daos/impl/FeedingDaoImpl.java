package com.holz.web.daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			final String sql = "INSERT INTO FEEDING VALUES (?,?,?,?,?,?,?,?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(
					new PreparedStatementCreator() {
						public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
							PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
							ps.setInt(1, 0);
							ps.setTimestamp(2,toTimstamp(feeding.getFeedingTime()));
							ps.setInt(3,feeding.getBunkScore());
							ps.setDouble(4,feeding.getDeliveredAmount());
							ps.setInt(5,feeding.getUser().getId());
							ps.setBoolean(6,true);
							ps.setInt(7,feeding.getGroupedHerd().getId());
							ps.setInt(8, farm);
							return ps;
						}
					},keyHolder);
			currentFeeding.setId(keyHolder.getKey().intValue());
			return currentFeeding;
		} else {
			String sql = "UPDATE FEEDING SET feedingTime=?, bunkScore=?, deliveredAmount=?," 
					+ " userId=?," 
					+ " groupedHerdsId=?," 
					+ " hasLeftovers=? "
					+ "WHERE feedingId=? AND farmId=?";
			this.jdbcTemplate.update(sql,feeding.getFeedingTime(),feeding.getBunkScore(),feeding.getDeliveredAmount(),
					feeding.getUser().getId(),feeding.getGroupedHerd().getId(),feeding.isHasLeftovers(),feeding.getId(),farmId);
		}
		return null;
	}

	private java.sql.Timestamp toTimstamp(java.util.Date date)
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
		String sql ="SELECT feedingId,feedingTime,bunkScore,deliveredAmount,userId,hasLeftovers,groupedHerdsId,farmId "
				+ "FROM FEEDING F "
				+ "WHERE F.feedingId = ?";
		return this.jdbcTemplate.query(sql, new Object[]{id}, new ResultSetExtractor<Feeding>() {
			@Override
			public Feeding extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.first()){
					Feeding f = new Feeding();
					f.setId(rs.getInt("feedingId"));
					f.setFeedingTime(rs.getTimestamp("feedingTime"));
					f.setBunkScore(rs.getInt("bunkScore"));
					f.setDeliveredAmount(rs.getDouble("deliveredAmount"));
					User user = new User();
					user.setUserId(rs.getInt("userId"));
					f.setUser(user);
					f.setHasLeftovers(rs.getBoolean("hasLeftovers"));
					GroupedHerd groupedHerd = new GroupedHerd();
					groupedHerd.setId(rs.getInt("groupedHerdsId"));
					f.setGroupedHerd(groupedHerd);
					return f;
				}
				return null;
			}
		});
	}

	@Override
	public void updateFeedingNoLeftovers(int feedingId) {
		String sql = "UPDATE FEEDING SET hasLeftovers=false WHERE feedingId=?";
		this.jdbcTemplate.update(sql,feedingId);
	}
}