package com.eCommerce.eCommerceApp.service;

import java.util.*;

import com.eCommerce.eCommerceApp.entity.Campaign;
import com.eCommerce.eCommerceApp.entity.CampaignCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.repository.CampaignCostRepository;
import com.eCommerce.eCommerceApp.repository.CampaignRepostiroy;
import com.eCommerce.eCommerceApp.repository.ProductRepository;

@Service
@Configuration
@EnableScheduling
public class CampaignService {
    // need to add history
    private static final String toBeUpdatedTime = "0 0 0 * * *";  // midnight
    private static final String timeZone = "Africa/Tunis";  // tunisia time zone

    @Autowired
    CampaignCostRepository costRep;
    @Autowired
    CampaignRepostiroy compaingRep;
    @Autowired
    ProductRepository prodRep;

//    @Autowired
//    Depence

    // add compaign cost to depense at midnight
    public void updateDepenseFromCampaignAtSpecificTime(){

    }

    @Scheduled(cron = toBeUpdatedTime, zone = timeZone)
    public void updateCampaignAtSpecificTime(){
        // updating currentCostId
        List<Campaign> campaignWhereNewCostIdIsNotNull = compaingRep.findByNewCostIdIsNotNull();
        if(!campaignWhereNewCostIdIsNotNull.isEmpty()){
            for(Campaign campaign : campaignWhereNewCostIdIsNotNull){
                Long newCostId = campaign.getNewCostId();
                campaign.setCurrentCostId(newCostId);
                campaign.setNewCostId(null);
                compaingRep.save(campaign);
            }
        }
        // updating newIsActive
        List<Campaign> campaignWhereNewIsActiveIsNotNull = compaingRep.findByNewIsActiveIsNotNull();
        if (!campaignWhereNewIsActiveIsNotNull.isEmpty()) {
            for (Campaign campaign : campaignWhereNewIsActiveIsNotNull) {
                Boolean newIsActive = campaign.getNewIsActive();
                campaign.setIsActive(newIsActive);
                campaign.setNewIsActive(null);
                compaingRep.save(campaign);
            }
        }

    }

    public void editCampaign(Long campaignId , Map<String, String> campaignInfo){

        Campaign currentCampaign = compaingRep.findById(campaignId).get();

        String newcampaignName = campaignInfo.get("name") ;
        String newcampaignDescription = campaignInfo.get("description") ;

        // product
        Long newcampaignProductId = Long.parseLong(campaignInfo.get("productId"));
        Product newcampaignProduct = prodRep.findById(newcampaignProductId).get();

//        Boolean iscampaignActive = Boolean.parseBoolean(campaignInfo.get("isActive"));

        currentCampaign.setName(newcampaignName);
        currentCampaign.setDescription(newcampaignDescription);
        currentCampaign.setProduct(newcampaignProduct);
//        currentCampaign.setIsActive(iscampaignActive);

        // setting the new currentCostId
        Float newGivencampaignCostAmount = Float.parseFloat(campaignInfo.get("amount"));
        // getting the oldcampaignCostAmount
        Float campaignCurrentCostAmount = costRep.findById(currentCampaign.getCurrentCostId()).get().getCostAmount();

        // updating the currentCostId and the newCostId
        if(!newGivencampaignCostAmount.equals(campaignCurrentCostAmount) ) {
            /** where the cost is updated, the currentCost need to stay the same and it will be updated only at the end of the day (00:00),
             so a newCost will be created and added to costs so it can be used to store the new cost. **/
            // test if the newCostAmount is equal to the oldNewCostAmount

            if (currentCampaign.getNewCostId() == null) {
                CampaignCost newCurrentCost = createCampaignCostWithoutCampaign(newGivencampaignCostAmount);
                Long newCurrentCostId = newCurrentCost.getIdCost();
                currentCampaign.setNewCostId(newCurrentCostId);

                compaingRep.save(currentCampaign);
                newCurrentCost.setcampaign(currentCampaign);
                costRep.save(newCurrentCost);

            } else {
                Optional<CampaignCost> newCurrentCost = costRep.findById(currentCampaign.getNewCostId());
                if(newGivencampaignCostAmount != newCurrentCost.get().getCostAmount())
                {
                    newCurrentCost.get().setCostAmount(newGivencampaignCostAmount);
                    costRep.save(newCurrentCost.get());
                }

            }
        }

        // updating newIsActive
        Boolean newIsActive = Boolean.valueOf(campaignInfo.get("newIsActive"));

        if(newIsActive!=currentCampaign.getIsActive()){
            currentCampaign.setNewIsActive(newIsActive);
        }
        compaingRep.save(currentCampaign);
    }

    public List<Campaign> getAllCampaigns(){
        return compaingRep.findAll() ;
    }

    public void addCampaignToProduct(Map<String, String> campaignInfo) {

        Campaign campaign = new Campaign();

        Calendar calendar = Calendar.getInstance();
        campaign.setCreationDate(calendar.getTime());

        campaign.setName(campaignInfo.get("name"));
        campaign.setDescription(campaignInfo.get("description"));

        campaign.setIsActive(true);

        // creating the cost and setting the currentCostId
        Float campaignCostAmount = Float.parseFloat(campaignInfo.get("amount"));
        CampaignCost currentCost =createCampaignCostWithoutCampaign(campaignCostAmount);
        campaign.setCurrentCostId(currentCost.getIdCost());
//        campaign.setNewCostId(currentCost.getIdCost());

        List<CampaignCost> costs = new ArrayList<>();
        costs.add(currentCost);
        campaign.setCosts(costs);

        // product
        Long campaignProductId = Long.parseLong(campaignInfo.get("productId"));
        Product campaignProduct = prodRep.findById(campaignProductId).get();
        campaign.setProduct(campaignProduct);

        compaingRep.save(campaign);
        currentCost.setcampaign(campaign);
        costRep.save(currentCost);

        compaingRep.save(campaign);

    }

    /** create cost object before initializing the campaign**/
    public CampaignCost createCampaignCostWithoutCampaign(float costAmount) {
        CampaignCost cost = new CampaignCost();
        Calendar calendar = Calendar.getInstance();
        cost.setCreationDate(calendar.getTime());

        cost.setCostAmount(costAmount);

        costRep.save(cost);

        return (cost);
    }
}
