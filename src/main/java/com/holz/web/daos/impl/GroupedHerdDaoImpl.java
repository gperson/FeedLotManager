package com.holz.web.daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.holz.web.daos.GroupedHerdDao;
import com.holz.web.models.GroupedHerd;
import com.holz.web.models.Locale;

@Repository
public class GroupedHerdDaoImpl implements GroupedHerdDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 

	@Override
	public boolean userHasAccessToGroupedHerd(String username, int groupedHerdId, int farmId){
		String sql ="SELECT COUNT(*) AS accessCount FROM GROUPED_HERDS GH "
			+ "JOIN LOCALE L on GH.localeId = L.localeId "
			+ "WHERE GH.groupedHerdsId = ? "
			+ "AND L.farmId = ? "
			+ "AND L.farmId = (SELECT U.farmId FROM USERS U WHERE U.username = ?)";
		return this.jdbcTemplate.query(sql, new Object[]{groupedHerdId,farmId,username}, new ResultSetExtractor<Boolean>() {
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
	public GroupedHerd getGroupedHerdForLocale(int localeId, int farmId) {
		String sql = "SELECT groupedHerdsId FROM GROUPED_HERDS GH "
				+ "JOIN LOCALE L ON L.localeId = GH.localeId "
				+ "WHERE farmId = " + farmId + " AND GH.localeId = "+ localeId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<GroupedHerd>() {
			@Override
			public GroupedHerd extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.first()){
					GroupedHerd gh = new GroupedHerd();
					gh.setId(rs.getInt("groupedHerdsId"));
					return gh;
				}
				return null;
			}
		});
	}

	@Override
	public List<GroupedHerd> getGroupedHerds(int farmId) {
		String sql = "SELECT groupedHerdsId, L.localeId, L.localeName FROM GROUPED_HERDS GH "
				+ "JOIN LOCALE L ON L.localeId = GH.localeId "
				+ "WHERE farmId = " + farmId + " "
				+ "AND GH.localeId IS NOT NULL";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<GroupedHerd>>() {
			@Override
			public List<GroupedHerd> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<GroupedHerd> groups = new ArrayList<GroupedHerd>();
				while(rs.next()){					
					groups.add(buildGroupedHerd(rs,false));
				}
				return groups;
			}
		});
	}

	private GroupedHerd buildGroupedHerd(ResultSet rs, boolean ignoreLocale) throws SQLException{
		GroupedHerd gh = new GroupedHerd();
		gh.setId(rs.getInt("groupedHerdsId"));
		if(!ignoreLocale){
			Locale l = new Locale();
			l.setLocaleName(rs.getString("localeName"));
			l.setId(rs.getInt("localeId"));
			gh.setLocale(l);
		}
		return gh;
	}
	
	@Override
	public GroupedHerd saveNewGroupedHerd(GroupedHerd group) {
		final String sql = "INSERT INTO GROUPED_HERDS "+
				"VALUES (?,?,?)";
		final GroupedHerd g = group;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
			new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, 0);
					ps.setInt(2, g.getLocale().getId());
					ps.setBoolean(3, false);
					return ps;
				}
			},keyHolder);
		group.setId(keyHolder.getKey().intValue());
		return group;
	}

	@Override
	public void updateGroupedHerdLocationForHerd(GroupedHerd group) {
		this.jdbcTemplate.update("UPDATE GROUPED_HERDS GH "
			+ "JOIN HERD H ON H.groupedHerdsId = GH.groupedHerdsId "
			+ "SET localeId=? "
			+ "WHERE GH.groupedHerdsId=? AND H.herdId=?",
			new Object[]{group.getLocale().getId() == -1 ? null : group.getLocale().getId(),group.getId(),group.getHerds().get(0).getId()});
	}
	
	@Override
	public void updateGroupedHerdSoldStatus(GroupedHerd group) {
		this.jdbcTemplate.update("UPDATE GROUPED_HERDS GH "
			+ "SET isSold=? "
			+ "WHERE GH.groupedHerdsId=?",
			new Object[]{group.isSold(),group.getId()});
	}
	
	@Override
	public void deleteGroupedHerd(int groupHerdId){
		this.jdbcTemplate.execute("delete from grouped_herds where groupedHerdsId = "+groupHerdId);
	}

	@Override
	public GroupedHerd getGroupedHerdForFeeding(int feedingId, int farmId) {
		String sql = "SELECT GH.groupedHerdsId, L.localeId, L.localeName "
				+ "FROM GROUPED_HERDS GH "
				+ "JOIN LOCALE L ON L.localeId = GH.localeId "
				+ "JOIN FEEDING F ON GH.groupedHerdsId = F.groupedHerdsId "
				+ "WHERE F.farmId = " + farmId + " "
				+ "AND F.feedingId = " + feedingId + " " 
				+ "AND GH.localeId IS NOT NULL";
		return jdbcTemplate.query(sql, new ResultSetExtractor<GroupedHerd>() {
			@Override
			public GroupedHerd extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.first()){					
					return buildGroupedHerd(rs,false);
				}
				return null;
			}
		});
	}

	@Override
	public GroupedHerd getGroupedHerd(int groupId) {
		String sql = "SELECT GH.groupedHerdsId, L.localeId, L.localeName " 
				+ "FROM GROUPED_HERDS GH "
				+ "JOIN LOCALE L ON L.localeId = GH.localeId "
				+ "WHERE GH.groupedHerdsId = " + groupId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<GroupedHerd>() {
			@Override
			public GroupedHerd extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.first()){					
					return buildGroupedHerd(rs,false);
				}
				return null;
			}
		});
	}

	@Override
	public GroupedHerd getGroupedHerdForHerd(int herdId, int farmId) {
		String sql = "SELECT GH.groupedHerdsId " 
				+ "FROM GROUPED_HERDS GH "
				+ "JOIN HERD H ON H.groupedHerdsId = GH.groupedHerdsId "
				+ "WHERE H.herdId = " + herdId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<GroupedHerd>() {
			@Override
			public GroupedHerd extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.first()){					
					return buildGroupedHerd(rs,true);
				}
				return null;
			}
		});
	}
}