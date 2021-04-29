package com.g.miss.accountant.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "account")
@IdClass(AccountId.class)
public class Account {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Id
    @Column(name = "group_id")
    private String groupId;
    private String name;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "created_time")
    private Date createdTime;

    @Transient
    private Integer amount = 0;

    public Account(String userId, String groupId) {
        this.userId = userId;
        this.groupId = groupId;
        this.createdTime = new Date();
    }

    public Account() {
    }

    public void updateInfo(String name) {
        this.name = name;
        this.updateTime = new Date();
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }
}
