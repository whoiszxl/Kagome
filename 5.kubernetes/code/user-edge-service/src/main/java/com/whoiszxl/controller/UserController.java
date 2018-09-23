package com.whoiszxl.controller;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoiszxl.response.Response;
import com.whoiszxl.thrift.ServiceProvider;
import com.whoiszxl.thrift.user.UserInfo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
    private ServiceProvider serviceProvider;
    
    @GetMapping("/info")
    @ResponseBody
    public Object getUserById(@RequestParam(value="userId", required = true) int userId) {

		try {
			UserInfo userInfo = serviceProvider.getUserService().getUserById(userId);
			return userInfo;
		} catch (TException e) {
			e.printStackTrace();
            return Response.USERNAME_PASSWORD_INVALID;
		}
    	
    }
}
