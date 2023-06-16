package com.g.miss.accountant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author G
 * @description 新增債權專用
 * @date 2023/6/16 4:26 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "債權")
public class DebtVO {

    @NotNull(message = "為甚麼不選人?")
    @NotEmpty(message = "為甚麼不選人?")
    @ApiModelProperty(name = "userIds", value = "使用者Ids", dataType = "String[]")
    String[] userIds;

    @Min(message = "為什麼要亂打?", value = 0)
    @NotNull(message = "為什麼不輸入金額?")
    @ApiModelProperty(name = "amount", value = "群組Id", dataType = "Integer")
    Integer amount;

    @NotNull(message = "群組Id不能為空")
    @ApiModelProperty(name = "userId", value = "使用者Id", dataType = "String")
    String userId;

    @NotNull(message = "群組Id不能為空")
    @ApiModelProperty(name = "groupId", value = "群組Id", dataType = "String")
    String groupId;

    @ApiModelProperty(name = "note", value = "描述", dataType = "String")
    String note;
}
