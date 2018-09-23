package com.whoiszxl.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试远程应用通信
 * @author whoiszxl
 *
 */
@RestController
public class ServerController {

    @GetMapping("/msg")
    public String msg() {
        return "远程通信测试";
    }
}
