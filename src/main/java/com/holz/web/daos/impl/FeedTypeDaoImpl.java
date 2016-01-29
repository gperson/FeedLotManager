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

import com.holz.web.daos.FeedTypeDao;
import com.holz.web.models.FeedType;

@Repository
public class FeedTypeDaoImpl implements FeedTypeDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 
	
	String SELECT_FEED_TYPE = "SELECT F.feedTypeId, F.feedType, F.driedMatterPercentage, F.enabled "+ 
					 "FROM FEED_TYPES F WHERE F.farmId=";
	
	@Override
	public List<FeedType> getFeedTypes(int farmId) {
		String sql = SELECT_FEED_TYPE + farmId;
		return getFeedTypes(sql);
	}

	@Override
	public void saveOrUpdate(FeedType feed, int farmId) {
		String sql = "INSERT INTO FEED_TYPES "+
					 "VALUES (?,?,?,?,?)";
		if(feed.getId() != 0){
			sql = "UPDATE FEED_TYPES SET driedMatterPercentage=?,enabled=?, feedType=? where feedTypeId=? AND farmId =?";
			this.jdbcTemplate.update(sql, new Object[]{feed.getDriedMatterPercentage(),feed.isEnabled(),feed.getFeedType(),feed.getId(),farmId});
		} else {
			this.jdbcTemplate.update(sql, new Object[]{0,farmId,feed.getFeedType(),feed.getDriedMatterPercentage(),feed.isEnabled()});
		}	
	}

	@Override
	public void enableDisableFeedType(FeedType feed, int farmId) {
		String sql =  "UPDATE FEED_TYPES SET enabled =? WHERE feedTypeId=? AND farmId=?";
		this.jdbcTemplate.update(sql, new Object[]{feed.isEnabled(),feed.getId(),farmId});
	}

	@Override
	public List<FeedType> getEnabledFeedTypes(int farmId) {
		String sql = SELECT_FEED_TYPE + farmId + " AND enabled=1";
		return getFeedTypes(sql);
	}

	private List<FeedType> getFeedTypes(String sql){
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<FeedType>>() {
			@Override
			public List<FeedType> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<FeedType> feeds = new ArrayList<FeedType>();
				while(rs.next()) {
					FeedType f = new FeedType();
					f.setId(rs.getInt("feedTypeId"));
					f.setDriedMatterPercentage(rs.getDouble("driedMatterPercentage"));
					f.setFeedType(rs.getString("feedType"));
					f.setEnabled(rs.getBoolean("enabled"));
					feeds.add(f);
				}
				return feeds;
			}
		});
	}
}