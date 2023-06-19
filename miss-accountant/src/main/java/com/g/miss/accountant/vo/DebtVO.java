package com.g.miss.accountant.vo;

import io.swagger.annotations.ApiModel;
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
    @NotNull(message = "群組Id不能為空")
    String groupId;

    @NotNull(message = "群組Id不能為空")
    String userId;

    @NotNull(message = "為甚麼不選人?")
    @NotEmpty(message = "為甚麼不選人?")
    String[] userIds;

    @Min(message = "為什麼要亂打?", value = 0)
    @NotNull(message = "為什麼不輸入金額?")
    Integer amount;

    String note;
}
