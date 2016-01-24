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

import com.holz.web.daos.GroupedHerdDao;
import com.holz.web.models.GroupedHerd;

@Repository
public class GroupedHerdDaoImpl implements GroupedHerdDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 

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
	public GroupedHerd saveNewGroupedHerd(GroupedHerd group) {
		final String sql = "INSERT INTO GROUPED_HERDS "+
				"VALUES (?,?)";
		final GroupedHerd g = group;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
			new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, 0);
					ps.setInt(2, g.getLocale().getId());
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
	public void deleteGroupedHerd(int groupHerdId){
		this.jdbcTemplate.execute("delete from grouped_herds where groupedHerdsId = "+groupHerdId);
	}
}