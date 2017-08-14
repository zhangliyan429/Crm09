package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.base.ResultInfo;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.vo.UserLoginIdentity;
import com.shsxt.crm.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("login")
	@ResponseBody
	public ResultInfo login(String userName,String password){
		ResultInfo result=null;
		//try {
			UserLoginIdentity userLoginIdentity=userService.login(userName, password);
			result=success(userLoginIdentity);
			
	//	} catch (Exception e) {
	//		result=new ResultInfo(Constant.ERROR_CODE,e.getMessage(),"操作失败");
	//		result = failure(e);
		//}
		return result;
	}
	@RequestMapping("find_customer_manager")
	@ResponseBody
	public List<UserVO> findCustomerManager(){
		List<UserVO> customerManagers=userService.findCustomerManager();
		return customerManagers;
	}
}
