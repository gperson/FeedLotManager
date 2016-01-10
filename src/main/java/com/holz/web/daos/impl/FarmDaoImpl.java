package com.holz.web.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.holz.web.daos.FarmDao;
import com.holz.web.models.Farm;
import com.holz.web.models.Herd;
import com.holz.web.models.Locale;
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

		/*
		 * Load just farm name and id
		 */
		if(FarmLoadOption.FARM_NAME_AND_ID == loadOption){			
			return getFarmNameAndId(farmId);
		} else if(FarmLoadOption.LOCALES_WITH_HERDS == loadOption){
			/*
			 * Load farm with locales and herds
			 */			
			Farm f = getFarmNameAndId(farmId);
			f.setHerds(getGetHerds(farmId));
			f.setLocales(getGetLocales(farmId));
			return f; 
		} else {
			return null;
		}


	}

	private Farm getFarmNameAndId(int farmId){
		String sql = "SELECT * FROM FARM F WHERE F.farmId=" + farmId;
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

	private List<Herd> getGetHerds(int farmId){
		String sql = "SELECT * FROM HERD H WHERE H.farmId=" + farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Herd>>() {
			@Override
			public List<Herd> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Herd> herds = new ArrayList<Herd>();
				while(rs.next()) {
					Herd h = new Herd();
					h.setHerdId(rs.getInt("herdId"));
					h.setQuantity(rs.getInt("quantity"));
					h.setWeight(rs.getDouble("weight"));
					h.setWeight(rs.getDouble("cost"));
					h.setTagNumber(rs.getString("tagNumber"));
					h.setEstimatedSaleDate(rs.getDate("estimatedSaleDate"));
					h.setImplantDate(rs.getDate("implantDate"));
					h.setOptiflexDate(rs.getDate("optiflexDate"));
					h.setDateEntered(rs.getDate("dateEntered"));
					herds.add(h);
				}
				return herds;
			}
		});
	}

	private List<Locale> getGetLocales(int farmId){
		String sql = "SELECT L.*, (SELECT GROUP_CONCAT(H.herdId) "+
						"FROM HERD_LOCALE_MAP H "+ 
						"WHERE L.localeId = H.localeId) AS herds "+
					 "FROM LOCALE L WHERE L.farmId = " + farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Locale>>() {
			@Override
			public List<Locale> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Locale> locales = new ArrayList<Locale>();
				while(rs.next()) {
					Locale l = new Locale();
					l.setLocaleId(rs.getInt("localeId"));
					l.setLocaleName(rs.getString("localeName"));
					String herds = rs.getString("herds");
					if(herds != null && !herds.equals(""))
						l.setHerdIds(Arrays.asList(rs.getString("herds").split(",")));
					else
						l.setHerdIds(new ArrayList<String>());
					locales.add(l);
				}
				return locales;
			}
		});
	}

}