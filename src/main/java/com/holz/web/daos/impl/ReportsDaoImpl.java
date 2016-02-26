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

import com.holz.web.daos.ReportsDao;
import com.holz.web.models.reports.PoundsGainedPerPoundDriedFood;

@Repository
public class ReportsDaoImpl implements ReportsDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 
		
	@Override
	public List<PoundsGainedPerPoundDriedFood> getPoundsGainedPerPoundDriedFoods(int farmId) {
		String sql = "CALL feedlot.PoundGainedPerDriedMaterPound("+farmId+");";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<PoundsGainedPerPoundDriedFood>>() {
			@Override
			public List<PoundsGainedPerPoundDriedFood> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<PoundsGainedPerPoundDriedFood> reports = new ArrayList<PoundsGainedPerPoundDriedFood>();
				while(rs.next()) {
					PoundsGainedPerPoundDriedFood report = new PoundsGainedPerPoundDriedFood();
					report.setpGpD(rs.getDouble("poundGainedPerPoundOfDriedFood"));
					report.setHerdsLabels("herdsLabels");
					reports.add(report);
				}
				return reports;
			}
		});	
	}
}