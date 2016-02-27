package com.holz.web.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.FeedDao;
import com.holz.web.daos.FeedingDao;
import com.holz.web.daos.ReportsDao;
import com.holz.web.models.Feed;
import com.holz.web.models.Feeding;
import com.holz.web.models.reports.FeedHistory;
import com.holz.web.models.reports.FeedingHistory;
import com.holz.web.models.reports.PoundsGainedPerPoundDriedFood;
import com.holz.web.models.reports.SalesOverview;
import com.holz.web.services.ReportsServices;

@Transactional
@Service
public class ReportsServicesImpl implements ReportsServices {

	@Autowired
	private ReportsDao reportsDao;
	
	@Autowired
	private FeedingDao feedingDao;
	
	@Autowired
	private FeedDao feedDao;

	@Override
	public List<PoundsGainedPerPoundDriedFood> getPoundsGainedPerPoundDriedFoods(int farmId) {
		return this.reportsDao.getPoundsGainedPerPoundDriedFoods(farmId);
	}
	
	@Override
	public List<SalesOverview> getSalesOverview(int farmId) {
		return this.reportsDao.getSalesOverview(farmId);
	}

	@Override
	public FeedingHistory getFeedingHistory(int farmId, int groupId) {
		List<Feeding> feedings = this.feedingDao.getAllFeedingsForHerd(farmId, groupId);
		FeedingHistory history = new FeedingHistory();

		/* Build this format...
		 *  var data = google.visualization.arrayToDataTable([
		 *      ['Year', 'Sales', 'Expenses'],
		 *      ['2013',  1000,      400],
		 *      ['',      1170,      460]
		 *     ]);
		 */
		
		HashSet<String> feedTypes = new HashSet<String>();		
		//Get all feed types
		for(Feeding fs : feedings){
			fs.setFeeds(this.feedDao.getFeedsForFeeding(fs.getId()));
			for(Feed f : fs.getFeeds()){
				String type = f.getFeedType().getFeedType().toString();
				if(feedTypes.contains(type)){
					
				} else {
					feedTypes.add(type);
				}
			}
		}
		
		//Initialize all the column headers
		List<FeedHistory> cols = new ArrayList<FeedHistory>();
		for(String type : feedTypes){
			FeedHistory col = new FeedHistory();
			col.setType(type);
			col.setAmounts(new ArrayList<Double>());
			cols.add(col);
		}
		
		List<String> dateLabels = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy");
		int i = 0;
		for(Feeding fs : feedings){
			if(i == 0 || i == (int)(feedings.size()/2) || i == (feedings.size() - 1))
				dateLabels.add(sdf.format(fs.getFeedingTime()));
			else
				dateLabels.add("");
			for(FeedHistory col : cols){
				col.getAmounts().add(getFeedAmount(col.getType(),fs.getFeeds(),fs.getDeliveredAmount()));
			}
			i++;
		}
		
		history.setFeedAmounts(cols);
		history.setDateLabels(dateLabels);
		return history;
	}

	private double getFeedAmount(String type, List<Feed> feeds, double delivered){
		double amount = 0;
		for(Feed f : feeds){
			if(f.getFeedType().getFeedType().toString().equals(type)){
				return f.getRatio() * delivered * (f.getFeedType().getDriedMatterPercentage()/100.0);
			}
		}
		return amount;
	}

}
