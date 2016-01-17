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

import com.holz.web.daos.FeedDao;
import com.holz.web.models.Feed;

@Repository
public class FeedDaoImpl implements FeedDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 
	
	@Override
	public List<Feed> getFeeds(int farmId) {
		String sql = "SELECT F.feedTypeId, F.feedType, F.driedMatterPercentage, F.enabled "+ 
					 "FROM FEED_TYPES F WHERE F.farmId=" + farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Feed>>() {
			@Override
			public List<Feed> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Feed> feeds = new ArrayList<Feed>();
				while(rs.next()) {
					Feed f = new Feed();
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

	@Override
	public void saveOrUpdate(Feed feed, int farmId) {
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
	public void enableDisableFeed(Feed feed, int farmId) {
		String sql =  "UPDATE FEED_TYPES SET enabled =? WHERE feedTypeId=? AND farmId=?";
		this.jdbcTemplate.update(sql, new Object[]{feed.isEnabled(),feed.getId(),farmId});
	}

}