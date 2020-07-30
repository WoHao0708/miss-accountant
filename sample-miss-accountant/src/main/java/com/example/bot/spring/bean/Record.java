package com.example.bot.spring.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "group_id")
    private String groupId;
    private int amount;
    @Column(name = "created_time")
    private Date createdTime;

    public Record(String userId, String groupId, int amount){
        this.userId = userId;
        this.groupId = groupId;
        this.amount = amount;
        this.createdTime = new Date();
    }

    public Record(){}
}
