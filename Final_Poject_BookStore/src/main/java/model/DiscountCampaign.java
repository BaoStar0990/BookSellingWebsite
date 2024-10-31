package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
@Entity
public class DiscountCampaign implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campaignId;
    private String campaignName;
    
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @OneToMany(mappedBy = "discoundCampaign")
    private HashSet<DiscountDetail> discountDetails;
    
    public DiscountCampaign()
    {

    }
    public DiscountCampaign(int campaignId, String campaignName, Date startDate, Date endDate) {
        this.campaignId = campaignId;
        this.campaignName = campaignName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountDetails = new HashSet<>();
    }

    public DiscountCampaign(String campaignName, Date startDate, Date endDate, HashSet<DiscountDetail> discountDetails) {
        this.campaignName = campaignName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountDetails = discountDetails;
    }
    
    public int getCampaignId() {
        return campaignId;
    }
    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }
    public String getCampaignName() {
        return campaignName;
    }
    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public HashSet<DiscountDetail> getDiscountDetails() {
        return discountDetails;
    }
    public void setDiscountDetails(HashSet<DiscountDetail> discountDetails) {
        this.discountDetails = discountDetails;
    }
}
