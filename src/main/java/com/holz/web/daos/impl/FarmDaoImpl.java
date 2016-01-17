package com.holz.web.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.holz.web.daos.FarmDao;
import com.holz.web.models.Farm;
import com.holz.web.models.enums.FarmLoadOption;

@Repository
public class FarmDaoImpl implements FarmDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 

	@Override
	public Farm getFarmByUserName(String userName, FarmLoadOption loadOption) {
		String sql = "SELECT U.farmId FROM FARM F "
				+ "JOIN USERS U ON U.farmId = F.farmId "
				+ "WHERE U.userName = '"+userName +"'";
		int farmId = this.jdbcTemplate.queryForObject(sql, Integer.class);	
		
		sql = "SELECT * FROM FARM F WHERE F.farmId=" + farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<Farm>() {
			@Override
			public Farm extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					Farm f = new Farm();
					f.setFarmName(rs.getString("farmName"));
					f.setId(rs.getInt("farmId"));
					return f;
				}
				return null;
			}
		});
	}
}