package com.g.miss.accountant.controller;

import com.g.miss.accountant.entity.Account;
import com.g.miss.accountant.service.Impl.AccountServiceImpl;
import com.g.miss.accountant.vo.AccountVO;
import com.g.miss.accountant.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author G
 * @description 帳號
 * @date 2023/6/8 12:19 PM
 */
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
     * @param accountVO 帳號
     * @return 成功或失敗
     */
    @ResponseBody
    @ApiOperation(value = "更新使用者資訊")
    @PostMapping("/account/update")
    public Result<?> update(@Valid @RequestBody AccountVO accountVO) {
        return Result.ok(accountService.updateInfo(accountVO));
    }

    /**
     * 取得群組內的user
     *
     * @param accountVO 帳號
     * @return 群組內帳號
     */
    @ResponseBody
    @PostMapping("/account/group")
    public Result<List<Account>> group(@Valid @RequestBody AccountVO accountVO) {
        return Result.ok(accountService.listGroupUserExceptItself(accountVO));
    }
}
