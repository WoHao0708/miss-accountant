package com.g.miss.accountant.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * 使用者id
     */
    @TableId
    private String userId;

    /**
     * 群組id
     */
    @TableId
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
}
