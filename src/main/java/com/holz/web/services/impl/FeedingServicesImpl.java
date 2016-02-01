package com.holz.web.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.FeedingDao;
import com.holz.web.daos.FeedDao;
import com.holz.web.daos.GroupedHerdDao;
import com.holz.web.models.Feed;
import com.holz.web.models.FeedType;
import com.holz.web.models.Feeding;
import com.holz.web.models.GroupedHerd;
import com.holz.web.models.Leftovers;
import com.holz.web.services.FeedingServices;

@Transactional
@Service
public class FeedingServicesImpl implements FeedingServices {

	@Autowired
	private FeedingDao feedingDao;

	@Autowired
	private FeedDao feedDao;

	@Autowired
	private GroupedHerdDao groupedHerdDao;

	@Override
	public Feeding saveOrUpdateFeeding(Feeding feeding, int farmId) {		
		if(feeding.getId() != 0){
			//Fill in info from step 1 and 2
			Feeding original = this.feedingDao.getFeeding(feeding.getId());
			feeding.setGroupedHerd(original.getGroupedHerd());
			feeding.setFeeds(this.feedDao.getFeedsForFeeding(feeding.getId()));

			//Subtract from Feed amounts
			double total = 0;
			for(Feed f : feeding.getFeeds()){
				if(feeding.isHasLeftovers()){
					f.setAmount(f.getAmount() - (f.getRatio()*feeding.getDeliveredAmount()));
				} else {
					//Used all remaining
					feeding.setDeliveredAmount(feeding.getDeliveredAmount() + f.getAmount());
					f.setAmount(0);
				}
				total = total + f.getAmount();
			}
			this.feedDao.updateFeedAmounts(feeding.getFeeds(),farmId);

			if(total <= 0.0000001){
				//Set feeding to false have leftovers
				this.feedingDao.updateFeedingNoLeftovers(feeding.getId());
			} else {
				feeding.setHasLeftovers(true);
			}
		}

		return this.feedingDao.saveOrUpdate(feeding,farmId);		
	}

	@Override
	public boolean userHasAccessToFeeding(int farmId,int feedingId) {
		return this.feedingDao.userHasAccessToFeeding(farmId,feedingId);
	}

	@Override
	public void saveFeedSelections(Feeding feeding, int farmId) {
		HashMap<Integer, Double> combined  = new HashMap<Integer, Double>();
		if(feeding.getLeftovers() != null){
			for(Leftovers lf : feeding.getLeftovers()){
				//Copy feeds in new feeds
				List<Feed> originals = this.feedDao.getFeedsForFeeding(lf.getFeedingId());

				//Set new amounts and ids to zero so it inserts them
				List<Feed> duplicates = new ArrayList<Feed>();
				for(Feed o : originals){
					Feed f = new Feed();
					f.setId(0);
					f.setAmount(o.getAmount());
					f.setFeedType(o.getFeedType());
					duplicates.add(f);
				}

				if(feeding.getFeeds() == null){
					feeding.setFeeds(new ArrayList<Feed>());
					feeding.getFeeds().addAll(duplicates);
				} else {

					//Combine like feeds
					for(Feed o : originals){
						int id = o.getFeedType().getId();
						if(combined.containsKey(id)){
							combined.put(id, o.getAmount() + combined.get(id));							 
						} else {
							combined.put(id, o.getAmount());
						}
					}
				}

				//Set old feeds amount to zero
				for(Feed d : originals){
					d.setAmount(0);
				}
				this.feedDao.updateFeedAmounts(originals,farmId);

				//Set feeding to false have leftovers
				this.feedingDao.updateFeedingNoLeftovers(lf.getFeedingId());
			}
		}

		//Add existing feeds to the newly entered ones if 
		for(Feed f : feeding.getFeeds()){
			int id = f.getFeedType().getId();
			if(combined.containsKey(id)){
				f.setAmount(f.getAmount() + combined.get(id));
				combined.remove(id);
			}
		}

		//Add one that didn't have a match
		for (Map.Entry<Integer, Double> entry : combined.entrySet())
		{
			Feed f = new Feed();
			f.setId(0);
			FeedType feedType = new FeedType();
			feedType.setId(entry.getKey());
			f.setFeedType(feedType);
			f.setAmount(entry.getValue());
			feeding.getFeeds().add(f);
		}


		double total = 0;
		for(Feed f : feeding.getFeeds()){
			total = total + f.getAmount();
		}	
		for(Feed f : feeding.getFeeds()){
			f.setRatio(f.getAmount()/total);
		}
		this.feedDao.saveFeedSelections(feeding.getFeeds(),feeding.getId());
	}

	@Override
	public List<Feeding> getAllFeedings(int farmId) {
		List<Feeding> feedings = this.feedingDao.getAllActiveFeedings(farmId);
		for(Feeding fdg : feedings){
			fdg.setFeeds(this.feedDao.getFeedsForFeeding(fdg.getId()));
			GroupedHerd groupedHerd = new GroupedHerd();
			groupedHerd.setLocale(this.groupedHerdDao.getGroupedHerdForFeeding(fdg.getId(), farmId).getLocale());
			fdg.setGroupedHerd(groupedHerd);
		}
		return feedings;
	}

	@Override
	public void editedFeeding(Feeding feeding, int farmId) {		
		if(feeding.getId() != 0){
			//Delete old feed if any of the feeds change, just delete old ones and insert as new 
			List<Feed> old = this.feedDao.getFeedsForFeeding(feeding.getId());
			boolean deleteAll = !(feeding.getFeeds().size() == old.size());
			if(!deleteAll){
				for(Feed f : feeding.getFeeds()){
					boolean found = false;
					for(Feed f2 : old){
						if(f2.getFeedType().getId() == f.getFeedType().getId() 
								&& f2.getRatio() == f.getRatio()){
							found = true;
						}
					}
					if(!found){
						deleteAll = true;
						break;
					}
				}
			}

			if(deleteAll){
				this.feedDao.saveFeedSelections(feeding.getFeeds(), feeding.getId());
				this.feedDao.deleteFeeds(old);
			}

			//Save feeding
			this.feedingDao.saveOrUpdate(feeding,farmId);
		}		
	}
}
