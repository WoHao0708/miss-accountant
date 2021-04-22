package com.g.miss.accountant.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountId implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;

    private String groupId;
    private String userId;
}
