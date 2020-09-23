package com.g.miss.accountant.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "public_fund")
public class PublicFund {

    @Id
    @Column(name = "group_id")
    private String groupId;
    private int amount;
    @Column(name = "update_time")
    private Date updateTime;

    public PublicFund(String groupId, int amount) {
        this.groupId = groupId;
        this.amount = amount;
        this.updateTime = new Date();
    }

    public PublicFund() {
    }
}
