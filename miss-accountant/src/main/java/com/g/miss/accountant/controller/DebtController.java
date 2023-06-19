package com.g.miss.accountant.controller;

import com.g.miss.accountant.dto.DebtDTO;
import com.g.miss.accountant.service.DebtService;
import com.g.miss.accountant.util.ValidationUtils;
import com.g.miss.accountant.vo.DebtVO;
import com.g.miss.accountant.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author G
 * @description 債權功能
 * @date 2023/6/8 12:19 PM
 */
@Api(tags = "債權功能")
@RestController
public class DebtController {
    @Autowired
    DebtService debtService;

    /**
     * 記賬
     *
     * @param groupId 群組id
     * @param userId  債權人id
     * @param userIds 負債人ids
     * @param amount  金額
     * @param note    筆記
     * @return 結果
     */
    @PostMapping("/debt/add")
    @ApiOperation(value = "記帳")
    public Result<?> addDebt(String groupId, String userId, String[] userIds, Integer amount, String note) {
        DebtVO debtVO = new DebtVO(groupId, userId, userIds, amount, note);
        ValidationUtils.validate(debtVO);
        return Result.ok(debtService.addDebt(debtVO));
    }

    /**
     * 取得與使用者相關的債權
     *
     * @param userId  使用者id
     * @param groupId 群組id
     * @return 與使用者相關的債權
     */
    @GetMapping("/debt/list")
    @ApiOperation(value = "取得與使用者相關的債權")
    public Result<List<DebtDTO>> getList(@RequestParam String userId, @RequestParam String groupId) {
        return Result.ok(debtService.listDebt(groupId, userId));
    }

    /**
     * 刪除單筆債權
     *
     * @param debtId 債權id
     * @return 結果
     */
    @DeleteMapping("/debt/delete")
    @ApiOperation(value = "刪除單筆債權")
    public Result<?> deleteDebt(@RequestParam String debtId) {
        return Result.ok(debtService.deleteDebt(Integer.parseInt(debtId)));
    }
}
