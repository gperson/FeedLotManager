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
		String sql = "SELECT localeId, localeName, enabled FROM LOCALE WHERE farmId = "+farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Locale>>() {
			@Override
			public List<Locale> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Locale> locales = new ArrayList<Locale>();
				while(rs.next()) {
					Locale l = new Locale();
					l.setId(rs.getInt("localeId"));
					l.setLocaleName(rs.getString("localeName"));
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
			sql = "UPDATE LOCALE SET localeName=? where localeId=? AND farmId=?";
			this.jdbcTemplate.update(sql, new Object[]{locale.getLocaleName(),locale.getId(),farmId});
		} else {
			this.jdbcTemplate.update(sql, new Object[]{0,farmId,locale.getLocaleName(),true});
		}
	}

	@Override
	public void enableDisableLocale(Locale locale, int farmId) {
		String sql =  "UPDATE LOCALE SET enabled =? WHERE localeId=? AND farmId=?";
		this.jdbcTemplate.update(sql, new Object[]{locale.isEnabled(),locale.getId(),farmId});
	}

	@Override
	public boolean hasAccessToLocale(int farmId, Locale locale) {
		String sql = "SELECT COUNT(*) AS LocalesFound FROM LOCALE WHERE farmId = "+ farmId +
				" AND localeId = " + locale.getId();
		
		int found = jdbcTemplate.query(sql, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()){
					return rs.getInt("LocalesFound");
				}
				return 0;
			}
		});
		return found == 1;
	}
}