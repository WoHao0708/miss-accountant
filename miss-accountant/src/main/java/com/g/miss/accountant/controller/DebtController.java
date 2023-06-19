package com.g.miss.accountant.controller;

import com.g.miss.accountant.entity.Debt;
import com.g.miss.accountant.service.Impl.DebtServiceImpl;
import com.g.miss.accountant.util.ValidationUtils;
import com.g.miss.accountant.vo.DebtVO;
import com.g.miss.accountant.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author G
 * @description 債權
 * @date 2023/6/8 12:19 PM
 */
@Controller
public class DebtController {
    @Autowired
    DebtServiceImpl debtService;

    /**
     * 記帳頁
     */
    @GetMapping("/debt")
    public String debt() {
        return "/debt/add";
    }

    /**
     * 個人明細頁
     */
    @GetMapping("/debt/list")
    public String list() {
        return "/debt/list";
    }

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
    @ResponseBody
    @PostMapping("/debt/add")
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
    @ResponseBody
    @PostMapping("/debt/list")
    public Result<List<Debt>> getList(@RequestParam String userId, @RequestParam String groupId) {
        throw new RuntimeException("ddsd");
//        return Result.ok(debtService.listDebt(groupId, userId));
    }

    /**
     * 刪除單筆債權
     *
     * @param debtId 債權id
     * @return 結果
     */
    @ResponseBody
    @PostMapping("/debt/delete")
    public Result<?> deleteDebt(@RequestParam String debtId) {
        return Result.ok(debtService.deleteDebt(Integer.parseInt(debtId)));
    }
}
