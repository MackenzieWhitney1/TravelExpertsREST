package model;

public class CustomersRewardDTO {
    private Integer customerId;
    private Integer rewardId;
    private String rwdNumber;

    public CustomersRewardDTO(Integer customerId, Integer rewardId, String rwdNumber) {
        this.customerId = customerId;
        this.rewardId = rewardId;
        this.rwdNumber = rwdNumber;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public String getRwdNumber() {
        return rwdNumber;
    }

    public void setRwdNumber(String rwdNumber) {
        this.rwdNumber = rwdNumber;
    }
}