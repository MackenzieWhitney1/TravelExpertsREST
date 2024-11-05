package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    @Column(name = "RewardId", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "RwdName", length = 50)
    private String rwdName;

    @Size(max = 50)
    @Column(name = "RwdDesc", length = 50)
    private String rwdDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRwdName() {
        return rwdName;
    }

    public void setRwdName(String rwdName) {
        this.rwdName = rwdName;
    }

    public String getRwdDesc() {
        return rwdDesc;
    }

    public void setRwdDesc(String rwdDesc) {
        this.rwdDesc = rwdDesc;
    }

}