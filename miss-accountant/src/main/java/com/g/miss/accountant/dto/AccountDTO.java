package com.g.miss.accountant.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author G
 * @description 帳號DTO
 * @date 2023/6/19 4:10 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "帳號")
public class AccountDTO {
    /**
     * 群組id
     */
    @ApiModelProperty(name = "groupId", value = "群組ID", dataType = "String")
    private String groupId;
    /**
     * 使用者id
     */
    @ApiModelProperty(name = "userId", value = "使用者ID", dataType = "String")
    private String userId;
    /**
     * 名稱
     */
    @ApiModelProperty(name = "name", value = "使用者名稱", dataType = "String")
    private String name;
}
