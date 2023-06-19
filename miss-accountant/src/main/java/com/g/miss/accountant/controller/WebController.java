package com.g.miss.accountant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author G
 * @description
 * @date 2023/6/19 3:37 PM
 */
@ApiIgnore
@Controller
public class WebController {

    /**
     * Home page
     */
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    /**
     * 記帳頁
     */
    @GetMapping("/debt/add")
    public String debt() {
        return "/debt/add";
    }

    /**
     * 個人明細頁
     */
    @GetMapping("/debt")
    public String list() {
        return "/debt/list";
    }
}
