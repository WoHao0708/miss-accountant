package com.g.miss.accountant.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 * @author G
 * @description 債權
 * @date 2023/6/8 11:48 AM
 */
public class Debt {

    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 欠債人id
     */
    private String userId;
    /**
     * 群組id
     */
    private String groupId;
    /**
     * 債權人id
     */
    private String creditor;
    /**
     * 金額
     */
    private int amount;
    /**
     * 描述
     */
    private String note;
    /**
     * 刪除標記
     */
    private final int isDelete = 0;

    /**
     * 新增時間
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    @Transient
    private String name;
    @Transient
    private final int isOwner = 0;
}
