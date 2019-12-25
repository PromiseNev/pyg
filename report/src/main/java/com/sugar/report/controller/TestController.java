package com.sugar.report.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建时间:   2019/12/25
 *
 * @author ZhangQing
 * 功能描述:
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test(String json) {
        System.out.println(json);
        return "1LilI0oO";
    }
}