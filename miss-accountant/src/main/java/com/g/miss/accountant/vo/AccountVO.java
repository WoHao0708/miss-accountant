package com.g.miss.accountant.vo;

import io.swagger.annotations.ApiModel;
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

    @NotNull(message = "群組Id為必填")
    private String groupId;

    @NotNull(message = "使用者Id為必填")
    private String userId;

    @NotNull(message = "使用者名稱為必填")
    private String name;
}
