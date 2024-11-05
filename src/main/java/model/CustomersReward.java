package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customers_rewards")
public class CustomersReward {
    @EmbeddedId
    private CustomersRewardId id;

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @Size(max = 25)
    @NotNull
    @Column(name = "RwdNumber", nullable = false, length = 25)
    private String rwdNumber;

    public CustomersRewardId getId() {
        return id;
    }

    public void setId(CustomersRewardId id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getRwdNumber() {
        return rwdNumber;
    }

    public void setRwdNumber(String rwdNumber) {
        this.rwdNumber = rwdNumber;
    }

}