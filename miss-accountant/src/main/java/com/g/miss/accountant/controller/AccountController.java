package com.g.miss.accountant.controller;

import com.g.miss.accountant.entity.Account;
import com.g.miss.accountant.service.Impl.AccountServiceImpl;
import com.g.miss.accountant.vo.AccountVO;
import com.g.miss.accountant.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/8 12:19 PM
 */
@Api(tags = "帳號模組")
@Controller
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    /**
     * Home page
     */
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    /**
     * 更新帳號資訊
     *
     * @param userId  帳號id
     * @param groupId 使用者id
     * @param name    名稱
     * @return 成功或失敗
     */
    @ResponseBody
    @ApiOperation(value = "更新使用者資訊")
    @PostMapping("/account/update")
    public Result<?> update(String groupId, String userId, String name) {
        AccountVO accountVO = new AccountVO(groupId, userId, name);
        return Result.ok(accountService.updateInfo(accountVO));
    }

    /**
     * 取得群組內的user
     *
     * @param userId  帳號id
     * @param groupId 使用者id
     * @param name    名稱
     * @return 群組內帳號
     */
    @ResponseBody
    @PostMapping("/account/group")
    public Result<List<Account>> group(String groupId, String userId, String name) {
        AccountVO accountVO = new AccountVO(groupId, userId, name);
        return Result.ok(accountService.listGroupUserExceptItself(accountVO));
    }
}
