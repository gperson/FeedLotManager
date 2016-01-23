package com.holz.web.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.holz.web.daos.HerdDao;
import com.holz.web.models.GroupedHerd;
import com.holz.web.models.Herd;
import com.holz.web.models.Supplier;

@Repository
public class HerdDaoImpl implements HerdDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 

	String SELECT_HERD_INFO = "SELECT H.herdId, quantity, weight, cost, tagNumber, estimatedSaleDate, implantDate, optiflexDate, dateEntered, S.supplierId, supplierName, supplierLocation, H.groupedHerdsId "
			+ "FROM HERD H "
			+ "JOIN SUPPLIER S ON H.supplierId = S.supplierId ";
	
	@Override
	public List<Herd> getAllHerds(int farmId) {
		String sql = SELECT_HERD_INFO 
				+ "WHERE H.farmId=" + farmId + " ORDER BY H.herdId DESC";
		return getHerds(sql);
	}

	@Override
	public void saveOrUpdate(Herd herd, int farmId) {
		String sql = "INSERT INTO HERD "+
				"VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		if(herd.getId() != 0){
			sql = "UPDATE HERD SET quantity=?,weight=?,cost=?,tagNumber=?,estimatedSaleDate=?,implantDate=?,"
					+ "optiflexDate=?,supplierId=?, groupedHerdsId=? where herdId=? AND farmId =?";
			this.jdbcTemplate.update(sql, new Object[]{herd.getQuantity(),herd.getWeight(),herd.getCost(),
					herd.getTagNumber(),herd.getEstimatedSaleDate(),herd.getImplantDate(),herd.getOptiflexDate(),
					herd.getSupplier().getId(),herd.getGroupedHerd().getId(),herd.getId(),farmId});
		} else {
			this.jdbcTemplate.update(sql, 
					new Object[]{0,farmId,herd.getQuantity(), herd.getWeight(), herd.getCost(),
					herd.getTagNumber(), herd.getEstimatedSaleDate(),herd.getImplantDate(),
					herd.getOptiflexDate(),new Date() ,herd.getSupplier().getId(),
					null});
		}
	}

	@Override
	public List<Herd> getHerdsForGroupedLocal(int farmId, int groupedHerdId) {
		String sql = SELECT_HERD_INFO 
				+ "JOIN GROUPED_HERDS GH ON GH.groupedHerdsId = H.groupedHerdsId "
				+ "WHERE H.farmId=" + farmId +" "
				+ "AND GH.groupedHerdsId="+groupedHerdId + " "
				+ "AND GH.groupedHerdsId NOT IN (SELECT groupedHerdsId FROM SALE) ORDER BY H.herdId DESC";
		return getHerds(sql);
	}

	@Override
	public List<Herd> getOrphanHerds(int farmId){
		String sql = SELECT_HERD_INFO 
				+ "WHERE H.farmId=" + farmId +" "
				+ "AND groupedHerdsId IS NULL ORDER BY H.herdId DESC";
		return getHerds(sql);
	}
	
	private List<Herd> getHerds(String sql){		
		System.out.println(sql);
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Herd>>() {
			@Override
			public List<Herd> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Herd> herds = new ArrayList<Herd>();
				while(rs.next()) {
					Herd h = new Herd();
					h.setId(rs.getInt("herdId"));
					h.setQuantity(rs.getInt("quantity"));
					h.setWeight(rs.getDouble("weight"));
					h.setCost(rs.getDouble("cost"));
					h.setTagNumber(rs.getString("tagNumber"));
					h.setEstimatedSaleDate(rs.getDate("estimatedSaleDate"));
					h.setImplantDate(rs.getDate("implantDate"));
					h.setOptiflexDate(rs.getDate("optiflexDate"));
					h.setDateEntered(rs.getDate("dateEntered"));
					h.setGroupedHerd(new GroupedHerd());
					h.getGroupedHerd().setId(rs.getInt("groupedHerdsId"));
					Supplier s = new Supplier();
					s.setId(rs.getInt("supplierId"));
					s.setLocation(rs.getString("supplierLocation"));
					s.setName(rs.getString("supplierName"));
					h.setSupplier(s);
					herds.add(h);
				}
				return herds;
			}
		});
	}

	@Override
	public void updateGroupIds(List<Herd> herds, int groupId, int farmId) {
		String ids = "";
		for(Herd h : herds)
			ids = ids + "," + h.getId();
		String sql = "UPDATE HERD SET groupedHerdsId=? where herdId IN("+ids.substring(1)+") AND farmId =?";
		this.jdbcTemplate.update(sql, new Object[]{groupId,farmId});
	}

}