/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.g.miss.accountant.controller;

import com.g.miss.accountant.service.AccountService;
import com.g.miss.accountant.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DebtController {
    @Autowired
    AccountService accountService;
    @Autowired
    DebtService debtService;

    /**
     * 記帳頁
     */
    @GetMapping("/debt")
    public String debt() {
        return "/debt/set";
    }

    /**
     * 個人明細頁
     */
    @GetMapping("/debt/list")
    public String list() {
        return "/debt/list";
    }

    /**
     * 取得群組內的user
     */
    @ResponseBody
    @PostMapping("/debt/getAccount")
    public String getAccount(@RequestParam String userId, @RequestParam String groupId, @RequestParam String name) {
        return accountService.getGroupUserExcept(groupId, userId, name);
    }

    /**
     * 記帳
     */
    @ResponseBody
    @PostMapping("/debt/add")
    public String addDebt(String[] userIds, @RequestParam int amount, @RequestParam String userId, @RequestParam String groupId, String note) {
        return debtService.addDebt(userIds, userId, groupId, amount, note);
    }

    /**
     * Get debt list
     */
    @ResponseBody
    @PostMapping("/debt/getList")
    public String getList( @RequestParam String userId, @RequestParam String groupId){
        return debtService.getDebt(groupId, userId);
    }
}
