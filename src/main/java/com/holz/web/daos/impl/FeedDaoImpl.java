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
import com.holz.web.models.FeedType;
import com.holz.web.models.Leftovers;

@Repository
public class FeedDaoImpl implements FeedDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 

	@Override
	public void saveFeedSelections(List<Feed> feeds,int feedingId) {
		String sql = "INSERT INTO FEED VALUES (?,?,?,?,?)";
		for(Feed f : feeds){
			this.jdbcTemplate.update(sql,0,f.getAmount(),f.getFeedType().getId(),feedingId,f.getRatio());
		}
	}

	@Override
	public void updateFeedAmounts(List<Feed> feeds, int farmId) {
		String sql = "UPDATE FEED F "
				+ "JOIN FEEDING FG ON F.feedingId = FG.feedingId "
				+ "SET feedAmount=? WHERE FG.farmId=? AND F.feedId=?";
		for(Feed f : feeds){
			this.jdbcTemplate.update(sql,f.getAmount(),farmId,f.getId());
		}
	}

	@Override
	public List<Leftovers> getLeftoverFeeds(int farmId) {
		String sql = "SELECT FG.feedingId, F.feedId, (F.feedAmount-(F.ratio*FG.deliveredAmount)) AS feedAmount, FT.feedTypeId, FT.feedType "+
				"FROM FEEDING FG "+
				"JOIN FEED F ON F.feedingId = FG.feedingId "+
				"JOIN FEED_TYPES FT ON F.feedTypeId = FT.feedTypeId "+
				"WHERE hasLeftovers = true "+
				"AND FG.farmId = "+farmId +" ORDER BY FG.feedingId";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Leftovers>>() {
			@Override
			public List<Leftovers> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Leftovers> lfs = new ArrayList<Leftovers>();
				int current = 0;
				int i = 0;
				while(rs.next()) {
					int feedingId = rs.getInt("feedingId");
					if(current != feedingId){
						lfs.add(new Leftovers());
						lfs.get(i).setFeeds(new ArrayList<Feed>());
						lfs.get(i).setFeedingId(feedingId);
						i++;
						current = feedingId;
					}
					Feed f = new Feed();
					f.setAmount(rs.getDouble("feedAmount"));
					FeedType feedType = new FeedType();
					feedType.setId(rs.getInt("feedTypeId"));
					feedType.setFeedType(rs.getString("feedType"));
					f.setFeedType(feedType);
					f.setId(rs.getInt("feedId"));
					lfs.get(lfs.size()-1).getFeeds().add(f);
				}
				return lfs;
			}
		});
	}

	@Override
	public List<Feed> getFeedsForFeeding(int feedingId) {
		String sql = "SELECT F.feedId, F.feedAmount, F.ratio, FT.feedTypeId, FT.feedType "+
				"FROM FEED F "+
				"JOIN FEED_TYPES FT ON F.feedTypeId = FT.feedTypeId "+
				"WHERE F.feedingId="+ feedingId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Feed>>() {
			@Override
			public List<Feed> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Feed> feeds = new ArrayList<Feed>();
				while(rs.next()) {
					Feed f = new Feed();
					f.setAmount(rs.getDouble("feedAmount"));
					FeedType feedType = new FeedType();
					feedType.setId(rs.getInt("feedTypeId"));
					feedType.setFeedType(rs.getString("feedType"));
					f.setFeedType(feedType);
					f.setId(rs.getInt("feedId"));
					feeds.add(f);
				}
				return feeds;
			}
		});
	}
}