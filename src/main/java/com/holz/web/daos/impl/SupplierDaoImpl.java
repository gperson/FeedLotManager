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

import com.holz.web.daos.SupplierDao;
import com.holz.web.models.Supplier;

@Repository
public class SupplierDaoImpl implements SupplierDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 
	
	@Override
	public List<Supplier> getSuppliers(int farmId) {
		String sql = "SELECT S.supplierId, S.supplierName, S.supplierLocation "+ 
					 "FROM SUPPLIER S WHERE S.farmId=" + farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Supplier>>() {
			@Override
			public List<Supplier> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Supplier> suppliers = new ArrayList<Supplier>();
				while(rs.next()) {
					Supplier s = new Supplier();
					s.setId(rs.getInt("supplierId"));
					s.setLocation(rs.getString("supplierLocation"));
					s.setName(rs.getString("supplierName"));
					suppliers.add(s);
				}
				return suppliers;
			}
		});
	}

	@Override
	public void saveOrUpdate(Supplier supplier, int farmId) {
		String sql = "INSERT INTO SUPPLIER "+
					 "VALUES (?,?,?,?)";
		if(supplier.getId() != 0){
			sql = "UPDATE SUPPLIER SET supplierName=?,supplierLocation=? where supplierId=? AND farmId =?";
			this.jdbcTemplate.update(sql, new Object[]{supplier.getName(),supplier.getLocation(),supplier.getId(),farmId});
		} else {
			this.jdbcTemplate.update(sql, new Object[]{0,supplier.getName(),supplier.getLocation(),farmId});
		}	
	}

}