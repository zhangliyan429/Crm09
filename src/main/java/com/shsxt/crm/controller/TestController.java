package com.shsxt.crm.controller;

import com.shsxt.crm.model.User;
import com.shsxt.crm.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@GetMapping("get/{id}")
//	@PostMapping
//	@PutMapping
//	@DeleteMapping
	public Map<String, Object> get(@PathVariable Integer id) {
		
		User user = testService.findById(id);
//		User user = testService.findById(id);

		Map<String, Object> result = new HashMap<>();
		result.put("resultCode", 1);
		result.put("resultMessage", "Success");
		result.put("result", user);
		
		return result;
	}
}
