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
 * @description 公款
 * @date 2023/6/7 4:08 PM
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_public_fund")
public class PublicFund {

    /**
     * 群組id
     */
    @TableId
    private String groupId;

    /**
     * 餘額
     */
    private int balance;

    /**
     * 更新時間
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 調整餘額
     *
     * @param amount 調整金額
     * @return 結果
     */
    public int addBalance(int amount) {
        return this.balance += amount;
    }
}
