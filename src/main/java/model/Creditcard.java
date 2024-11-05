package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;

@Entity
@Table(name = "creditcards")
public class Creditcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CreditCardId", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "CCName", nullable = false, length = 10)
    private String cCName;

    @Size(max = 50)
    @NotNull
    @Column(name = "CCNumber", nullable = false, length = 50)
    private String cCNumber;

    @NotNull
    @Column(name = "CCExpiry", nullable = false)
    private Date cCExpiry;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CustomerId", nullable = false)
    private model.Customer customer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCCName() {
        return cCName;
    }

    public void setCCName(String cCName) {
        this.cCName = cCName;
    }

    public String getCCNumber() {
        return cCNumber;
    }

    public void setCCNumber(String cCNumber) {
        this.cCNumber = cCNumber;
    }

    public Date getCCExpiry() {
        return cCExpiry;
    }

    public void setCCExpiry(Date cCExpiry) {
        this.cCExpiry = cCExpiry;
    }

    public model.Customer getCustomer() {
        return customer;
    }

    public void setCustomer(model.Customer customer) {
        this.customer = customer;
    }

}