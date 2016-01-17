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

import com.holz.web.daos.LocaleDao;
import com.holz.web.models.Locale;

@Repository
public class LocaleDaoImpl implements LocaleDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 

	@Override
	public List<Locale> getLocales(int farmId) {
		String sql = "SELECT localeId, localeName, livestockCount, enabled FROM LOCALE WHERE farmId = "+farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Locale>>() {
			@Override
			public List<Locale> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Locale> locales = new ArrayList<Locale>();
				while(rs.next()) {
					Locale l = new Locale();
					l.setId(rs.getInt("localeId"));
					l.setLocaleName(rs.getString("localeName"));
					l.setLivestockCount(rs.getInt("livestockCount"));
					l.setEnabled(rs.getBoolean("enabled"));
					locales.add(l);
				}
				return locales;
			}
		});
	}

	@Override
	public void saveOrUpdate(Locale locale, int farmId) {
		String sql = "INSERT INTO LOCALE "+
				"VALUES (?,?,?,?)";
		if(locale.getId() != 0){
			sql = "UPDATE SUPPLIER SET localeName=?,livestockCount=? where localeId=? AND farmId=?";
			this.jdbcTemplate.update(sql, new Object[]{locale.getLocaleName(),locale.getLivestockCount(),locale.getId(),farmId});
		} else {
			this.jdbcTemplate.update(sql, new Object[]{0,farmId,locale.getLocaleName(),locale.getLivestockCount()});
		}
	}

	@Override
	public void enableDisableLocale(Locale locale, int farmId) {
		String sql =  "UPDATE LOCALE SET enabled =? WHERE localeId=? AND farmId=?";
		this.jdbcTemplate.update(sql, new Object[]{locale.isEnabled(),locale.getId(),farmId});
	}
}