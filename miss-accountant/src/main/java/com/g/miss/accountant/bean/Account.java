package com.g.miss.accountant.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "account_info")
@IdClass(AccountId.class)
public class Account {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Id
    @Column(name = "group_id")
    private String groupId;
    private String name;
    private int advance;
    @Column(name = "is_advance")
    private Integer isAdvance = 0;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "created_time")
    private Date createdTime;

    public Account(String userId, String groupId, int amount, int type) {
        this.userId = userId;
        this.groupId = groupId;
        if (type != 1)
            this.advance = amount;
        this.createdTime = new Date();
    }

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

    public String switchIsAdvance() {
        if (isAdvance == null || isAdvance == 0) {
            this.isAdvance = 1;
            return "設定為要分帳";
        } else {
            this.isAdvance = 0;
            return "設定為不分帳";
        }
    }
}
