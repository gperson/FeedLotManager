package com.holz.web.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.holz.web.daos.PackerDao;
import com.holz.web.models.Packer;

@Repository
public class PackerDaoImpl implements PackerDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 
	
	@Override
	public List<Packer> getPackers(int farmId) {
		String sql = "SELECT S.packerId, S.packerName, S.packerLocation "+ 
					 "FROM Packer S WHERE S.farmId=" + farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Packer>>() {
			@Override
			public List<Packer> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Packer> Packers = new ArrayList<Packer>();
				while(rs.next()) {
					Packer s = new Packer();
					s.setId(rs.getInt("packerId"));
					s.setLocation(rs.getString("packerLocation"));
					s.setName(rs.getString("packerName"));
					Packers.add(s);
				}
				return Packers;
			}
		});
	}

	@Override
	public void saveOrUpdate(Packer packer, int farmId) {
		String sql = "INSERT INTO PACKER "+
					 "VALUES (?,?,?,?)";
		if(packer.getId() != 0){
			sql = "UPDATE PACKER SET packerName=?,packerLocation=? where packerId=? AND farmId =?";
			this.jdbcTemplate.update(sql, new Object[]{packer.getName(),packer.getLocation(),packer.getId(),farmId});
		} else {
			this.jdbcTemplate.update(sql, new Object[]{0,packer.getName(),packer.getLocation(),farmId});
		}	
	}

}