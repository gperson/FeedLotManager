package com.holz.web.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.FeedingDao;
import com.holz.web.daos.FeedDao;
import com.holz.web.models.Feed;
import com.holz.web.models.Feeding;
import com.holz.web.models.Leftovers;
import com.holz.web.services.FeedingServices;

@Transactional
@Service
public class FeedingServicesImpl implements FeedingServices {

	@Autowired
	private FeedingDao feedingDao;
	
	@Autowired
	private FeedDao feedDao;

	@Override
	public Feeding saveOrUpdateFeeding(Feeding feeding, int farmId) {		
		if(feeding.getId() != 0){
			//Fill in info from step 1
			Feeding original = this.feedingDao.getFeeding(feeding.getId());
			feeding.setGroupedHerd(original.getGroupedHerd());
			
			//Subtract from Feed amounts
		
			//
		}
		
		return this.feedingDao.saveOrUpdate(feeding,farmId);		
	}

	@Override
	public boolean userHasAccessToFeeding(int farmId,int feedingId) {
		return this.feedingDao.userHasAccessToFeeding(farmId,feedingId);
	}

	@Override
	public void saveFeedSelections(Feeding feeding, int farmId) {
		double total = 0;
		
		if(feeding.getLeftovers() != null){
			for(Leftovers lf : feeding.getLeftovers()){
				//Copy feeds in new feeds
				List<Feed> originals = this.feedDao.getFeedsForFeeding(lf.getFeedingId());
				
				//Set new amounts and ids to zero so it inserts them
				List<Feed> duplicates = new ArrayList<Feed>();
				for(Feed o : originals){
					duplicates.add(o);
					o.setId(0);
					o.setAmount(lf.getAmount() * o.getRatio());
				}
				if(lf.getFeeds() == null){
					lf.setFeeds(new ArrayList<Feed>());
				}
				lf.getFeeds().addAll(originals);
				
				//Set old feeds amount to zero
				for(Feed d : duplicates){
					d.setAmount(0);
				}
				this.feedDao.updateFeedAmounts(duplicates,farmId);
				
				//Set feeding to false have leftovers
				this.feedingDao.updateFeedingNoLeftovers(lf.getFeedingId());
			}
		}
		
		for(Feed f : feeding.getFeeds()){
			total = total + f.getAmount();
		}	
		for(Feed f : feeding.getFeeds()){
			f.setRatio(f.getAmount()/total);
		}
		this.feedDao.saveFeedSelections(feeding.getFeeds(),feeding.getId());
	}

}
