package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;

@Embeddable
public class CustomersRewardId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -7986746786734255324L;
    @NotNull
    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;

    @NotNull
    @Column(name = "RewardId", nullable = false)
    private Integer rewardId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomersRewardId entity = (CustomersRewardId) o;
        return Objects.equals(this.rewardId, entity.rewardId) &&
                Objects.equals(this.customerId, entity.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rewardId, customerId);
    }

}