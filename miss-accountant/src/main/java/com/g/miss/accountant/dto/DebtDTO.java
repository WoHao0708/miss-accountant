package com.g.miss.accountant.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author G
 * @description 債權DTO
 * @date 2023/6/19 4:13 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "債權")
public class DebtDTO {

    /**
     * Id
     */
    @ApiModelProperty(name = "ID", value = "ID", dataType = "String")
    private Integer id;
    /**
     * 群組id
     */
    @ApiModelProperty(name = "groupId", value = "群組ID", dataType = "String")
    private String groupId;
    /**
     * 欠債人id
     */
    @ApiModelProperty(name = "userId", value = "使用者ID", dataType = "String")
    private String userId;
    /**
     * 債權人id
     */
    @ApiModelProperty(name = "creditorId", value = "債權人ID", dataType = "String")
    private String creditorId;
    /**
     * 金額
     */
    @ApiModelProperty(name = "amount", value = "金額", dataType = "Integer")
    private int amount;
    /**
     * 描述
     */
    @ApiModelProperty(name = "note", value = "描述", dataType = "String")
    private String note;
}
