package com.g.miss.accountant.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/7 6:30 PM
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("account")
public class Account {

    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 使用者id
     */
    private String userId;

    /**
     * 群組id
     */
    private String groupId;

    /**
     * 名稱
     */
    private String name;

    /**
     * 更新時間
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 新增時間
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @Transient
    private Integer amount = 0;

    public void addAmount(int amount) {
        this.amount += amount;
    }
}
