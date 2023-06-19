package com.g.miss.accountant.controller;

import com.g.miss.accountant.dto.AccountDTO;
import com.g.miss.accountant.service.AccountService;
import com.g.miss.accountant.util.ValidationUtils;
import com.g.miss.accountant.vo.AccountVO;
import com.g.miss.accountant.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author G
 * @description 帳號功能
 * @date 2023/6/8 12:19 PM
 */
@Api(tags = "帳號功能")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 更新帳號資訊
     *
     * @param userId  帳號id
     * @param groupId 使用者id
     * @param name    名稱
     * @return 成功或失敗
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群組ID", dataType = "string", required = true),
            @ApiImplicitParam(name = "userId", value = "債權人ID", dataType = "string", required = true),
            @ApiImplicitParam(name = "name", value = "筆記", dataType = "string", required = true),
    })
    @PostMapping("/account/update")
    @ApiOperation(value = "更新使用者資訊")
    public Result<?> update(String groupId, String userId, String name) {
        AccountVO accountVO = new AccountVO(groupId, userId, name);
        ValidationUtils.validate(accountVO);
        return Result.ok(accountService.updateInfo(accountVO));
    }

    /**
     * 取得群組內的使用者
     *
     * @param userId  帳號id
     * @param groupId 使用者id
     * @param name    名稱
     * @return 群組內帳號
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群組ID", dataType = "string", required = true),
            @ApiImplicitParam(name = "userId", value = "債權人ID", dataType = "string", required = true),
            @ApiImplicitParam(name = "name", value = "筆記", dataType = "string", required = true),
    })
    @GetMapping("/account/group")
    @ApiOperation(value = "取得群組內的使用者")
    public Result<List<AccountDTO>> group(String groupId, String userId, String name) {
        AccountVO accountVO = new AccountVO(groupId, userId, name);
        ValidationUtils.validate(accountVO);
        return Result.ok(accountService.listGroupUserExceptItself(accountVO));
    }
}
