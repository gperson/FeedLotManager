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

import com.holz.web.daos.HerdDao;
import com.holz.web.models.Herd;
import com.holz.web.models.Supplier;

@Repository
public class HerdDaoImpl implements HerdDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 

	@Override
	public List<Herd> getHerds(int farmId) {
		String sql = "SELECT herdId, quantity, weight, cost, tagNumber, estimatedSaleDate, implantDate, optiflexDate, dateEntered, "
				+ "S.supplierId, supplierName, supplierLocation "
				+ "FROM HERD H JOIN SUPPLIER S ON H.supplierId = S.supplierId WHERE H.farmId=" + farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Herd>>() {
			@Override
			public List<Herd> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Herd> herds = new ArrayList<Herd>();
				while(rs.next()) {
					Herd h = new Herd();
					h.setId(rs.getInt("herdId"));
					h.setQuantity(rs.getInt("quantity"));
					h.setWeight(rs.getDouble("weight"));
					h.setWeight(rs.getDouble("cost"));
					h.setTagNumber(rs.getString("tagNumber"));
					h.setEstimatedSaleDate(rs.getDate("estimatedSaleDate"));
					h.setImplantDate(rs.getDate("implantDate"));
					h.setOptiflexDate(rs.getDate("optiflexDate"));
					h.setDateEntered(rs.getDate("dateEntered"));
					Supplier s = new Supplier();
					s.setId(rs.getInt("supplierId"));
					s.setLocation(rs.getString("supplierLocation"));
					s.setName(rs.getString("supplierName"));
					herds.add(h);
				}
				return herds;
			}
		});
	}

	@Override
	public void saveOrUpdate(Herd herd, int farmId) {
		String sql = "INSERT INTO HERD "+
				 "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	if(herd.getId() != 0){
		sql = "UPDATE HERD SET quantity=?,weight=?,cost=?,tagNumber=?,estimatedSaleDate=?,implantDate=?,optiflexDate=?,dateEntered=?,supplierId=? where herdId=? AND farmId =?";
		this.jdbcTemplate.update(sql, new Object[]{herd.getQuantity(),herd.getWeight(),herd.getCost(),herd.getTagNumber(),herd.getEstimatedSaleDate(),herd.getImplantDate(),herd.getOptiflexDate(),herd.getDateEntered(),herd.getSupplier().getId(),herd.getId(),farmId});
	} else {
		this.jdbcTemplate.update(sql, new Object[]{0,farmId,herd.getQuantity(), herd.getWeight(),herd.getTagNumber(),herd.getEstimatedSaleDate(),herd.getImplantDate(),herd.getOptiflexDate(),herd.getDateEntered(),herd.getSupplier().getId()});
	}
	}

}