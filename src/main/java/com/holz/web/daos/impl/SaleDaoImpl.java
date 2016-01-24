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

import com.holz.web.daos.SaleDao;
import com.holz.web.models.GroupedHerd;
import com.holz.web.models.Packer;
import com.holz.web.models.Sale;

@Repository
public class SaleDaoImpl implements SaleDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 
	
	@Override
	public List<Sale> getAllSales(int farmId) {
		return getSales(farmId,0);
	}
	
	@Override
	public List<Sale> getSalesForGroupHerd(int farmId, int groupId) {
		return getSales(farmId,0);
	}

	@Override
	public void saveOrUpdate(Sale sale, int farmId) {
		String sql = "INSERT INTO Sale "+
					 "VALUES (?,?,?,?,?,?,?,?,?)";
		if(sale.getId() != 0){
			sql = "UPDATE Sale SET salePrice=?, groupedHerdsId=? ,saleWeight=?, quantity=?, saleDate?, dressingPercent=?, shrinkPercent=?, packerId=? where saleId=? AND farmId =?";
			this.jdbcTemplate.update(sql, new Object[]{sale.getSalePrice(),sale.getGroupedHerd().getId(),sale.getSaleWeight(),sale.getQuantity(),sale.getDressingPercent(),sale.getShrinkPercent(),sale.getPacker().getId(),sale.getId(),farmId});
		} else {
			this.jdbcTemplate.update(sql, new Object[]{0,sale.getGroupedHerd().getId(),sale.getSalePrice(),sale.getSaleWeight(),sale.getQuantity(),sale.getDressingPercent(),sale.getShrinkPercent(),sale.getPacker().getId()});
		}	
	}
	
	private List<Sale> getSales(int farmId, int groupId) {
		String sql = "SELECT S.saleId, S.salePrice, S.saleWeight, S.quantity, S.saleDate, S.dressingPercent, S.shrinkPercent, S.groupedHerdsId, P.packerName, P.packerLocation, P.packerId "+ 
					 "FROM Sale S " +
					 "JOIN PACKER P ON S.packerId = P.packerId "+
					 "WHERE P.farmId=" + farmId + (groupId == 0 ? "" : " AND S.groupedHerdsId="+groupId);
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Sale>>() {
			@Override
			public List<Sale> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Sale> Sales = new ArrayList<Sale>();
				while(rs.next()) {
					Sale s = new Sale();
					s.setId(rs.getInt("saleId"));
					s.setDressingPercent(rs.getDouble("dressingPercent"));
					Packer packer = new Packer();
					packer.setLocation(rs.getString("packerLocation"));
					packer.setId(rs.getInt("packerId"));
					packer.setName(rs.getString("packerName"));
					s.setPacker(packer);
					s.setQuantity(rs.getInt("quantity"));
					s.setSaleDate(rs.getTimestamp("saleDate"));
					s.setSalePrice(rs.getDouble("salePrice"));
					s.setSaleWeight(rs.getDouble("saleWeight"));
					s.setShrinkPercent(rs.getDouble("shrinkPercent"));
					GroupedHerd groupedHerd = new GroupedHerd();
					groupedHerd.setId(rs.getInt("groupedHerdsId"));
					s.setGroupedHerd(groupedHerd);
					Sales.add(s);
				}
				return Sales;
			}
		});
	}
}