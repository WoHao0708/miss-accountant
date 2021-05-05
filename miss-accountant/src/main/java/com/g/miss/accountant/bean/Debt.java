package com.g.miss.accountant.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

// todo 雙主鍵
@Getter
@Setter
@Entity
@Table(name = "debt")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "group_id")
    private String groupId;
    private String creditor;
    private int amount;
    private String note;
    private Date createdTime;
    @Column(name = "is_delete")
    private Integer isDelete = 0;

    @Transient
    private String name;
    @Transient
    private int isOwner = 0;

    public Debt(String userId, String groupId, String creditor, int amount, String note) {
        this.userId = userId;
        this.groupId = groupId;
        this.creditor = creditor;
        this.amount = amount;
        this.note = note;
        this.createdTime = new Date();
        this.isDelete = 0;
    }

    public Debt() {
    }
}
