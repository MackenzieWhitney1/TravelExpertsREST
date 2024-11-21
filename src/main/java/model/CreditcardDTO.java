package model;

import java.util.Date;

public class CreditcardDTO {
    private int id;
    private String cCName;
    private String cCNumber;
    private Date cCExpiry;
    private int customerId;

    public CreditcardDTO(int id, int customerId, String cCNumber, Date cCExpiry, String cCName) {
        this.id = id;
        this.customerId = customerId;
        this.cCNumber = cCNumber;
        this.cCExpiry = cCExpiry;
        this.cCName = cCName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcCName() {
        return cCName;
    }

    public void setcCName(String cCName) {
        this.cCName = cCName;
    }

    public String getcCNumber() {
        return cCNumber;
    }

    public void setcCNumber(String cCNumber) {
        this.cCNumber = cCNumber;
    }

    public Date getcCExpiry() {
        return cCExpiry;
    }

    public void setcCExpiry(Date cCExpiry) {
        this.cCExpiry = cCExpiry;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
