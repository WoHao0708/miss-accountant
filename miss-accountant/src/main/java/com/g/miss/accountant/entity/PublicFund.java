package com.g.miss.accountant.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author G
 * @description
 * @date 2023/6/7 4:08 PM
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("public_fund")
public class PublicFund {

    @TableId(value = "group_id")
    private String groupId;

    private int amount;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;
}
