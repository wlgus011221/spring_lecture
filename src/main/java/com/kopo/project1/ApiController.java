package com.kopo.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {
	@ResponseBody
	@GetMapping(value = "/create")
	public HashMap<String, String> create(Locale locale, Model model) {
		HashMap<String, String> data = new HashMap<>();
		
		DB db = new DB();
		db.createTable();
		
		data.put("message", "테이블이 생성되었습니다.");
		return data;
	}
	
	@ResponseBody
	@GetMapping(value = "/selectAll")
	public ArrayList<User> selectAll(Locale locale, Model mode) {
		DB db = new DB();
		ArrayList<User> data = db.selectAll();

		return data;
	}
}
