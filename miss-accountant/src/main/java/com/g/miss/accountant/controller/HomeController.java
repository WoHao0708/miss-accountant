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

import com.g.miss.accountant.service.Impl.AccountServiceImpl;
import com.g.miss.accountant.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

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
    @PostMapping("/")
    public Result<?> postIndex(@RequestParam String userId, @RequestParam String groupId, @RequestParam String name) {
        return Result.ok(accountService.updateInfo(groupId, userId, name));
    }
}
