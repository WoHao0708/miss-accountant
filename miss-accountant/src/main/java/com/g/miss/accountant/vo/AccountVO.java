package com.g.miss.accountant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/15 5:46 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "帳號")
public class AccountVO {

    @NotNull(message = "群組Id不能為空")
    @ApiModelProperty(name = "groupId", value = "群組ID", dataType = "String")
    private String groupId;

    @NotNull(message = "使用者Id不能為空")
    @ApiModelProperty(name = "userId", value = "使用者ID", dataType = "String")
    private String userId;

    @NotNull(message = "使用者名稱不能為空")
    @ApiModelProperty(name = "name", value = "使用者名稱", dataType = "String")
    private String name;
}
