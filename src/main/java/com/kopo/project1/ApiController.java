package com.kopo.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestParam;
=======
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
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
<<<<<<< HEAD
		// 그냥 홈 화면으로 전환할지 고민중.. 만들어졌다는걸 알아야할거 같은데 흠..
//		return "redirect:/";
	}
	
	@ResponseBody
	@GetMapping(value = "/selectAllUser")
	public ArrayList<User> selectAllUser(Locale locale, Model mode) {
		DB db = new DB();
		ArrayList<User> data = db.selectAllUser();

		return data;
	}
	
	@ResponseBody
	@GetMapping(value = "/selectAllPost")
	public ArrayList<Post> selectAllPost(Locale locale, Model mode) {
		DB db = new DB();
		ArrayList<Post> data = db.selectAllPost();

		return data;
	}
	
	@ResponseBody
	@GetMapping(value = "/selectPostRecent")
	public ArrayList<Post> selectPostRecent(Locale locale, Model mode) {
		DB db = new DB();
		ArrayList<Post> data = db.selectPostRecent();

		return data;
	}
	
	@ResponseBody
	@GetMapping(value = "/selectPostUpdateRecent")
	public ArrayList<Post> selectPostUpdateRecent(Locale locale, Model mode) {
		DB db = new DB();
		ArrayList<Post> data = db.selectPostUpdateRecent();

		return data;
	}
	
	@ResponseBody
	@GetMapping(value = "/searchPost")
	public ArrayList<Post> selectAllPost(Locale locale, Model mode, @RequestParam("keyword") String keyword) {
		DB db = new DB();
		ArrayList<Post> data = db.searchPost(keyword);
		return data;
	}
=======
	}
	
	@ResponseBody
	@GetMapping(value = "/selectAll")
	public ArrayList<User> selectAll(Locale locale, Model mode) {
		DB db = new DB();
		ArrayList<User> data = db.selectAll();

		return data;
	}
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
}
