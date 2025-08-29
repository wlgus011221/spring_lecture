package com.kopo.project1;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@GetMapping(value = "/")
	public String home(Locale locale, Model model, HttpServletRequest request) {
		DB db = new DB();
		int totalCount = db.countAll();
		int todayCount = db.countDay();
		
		HttpSession session = request.getSession();
	    String loginId = (String) session.getAttribute("loginId");
	    String userType = (String) session.getAttribute("user_type");
		
		model.addAttribute("totalCount", totalCount);
	    model.addAttribute("todayCount", todayCount);
	    model.addAttribute("loginId", loginId);
	    model.addAttribute("userType", userType);
	    
		return "home";
	}
	
	// 회원 가입 화면
	@GetMapping(value = "/signup")
	public String insert(Locale locale, Model model) {
		return "signup";
	}
	
	@PostMapping(value = "/insert")
	public String insertAction(Locale locale, Model model,
			HttpServletRequest request
			) {
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		DB db = new DB();
		db.insertData(new User(id, pwd, name, phone, address));
		return "redirect:/";
	}
	
	// 삭제
	@GetMapping(value = "/delete")
	public String delete(Locale locale, Model model, @RequestParam("idx") String inputIdx) {
//		HashMap<String, String> data = new HashMap<>();
		try {
			// 정수 변환
			int idx = Integer.parseInt(inputIdx);

			DB db = new DB();
			db.deleteData(idx);

//			data.put("message", "삭제 성공");
			model.addAttribute("text", "삭제 성공");
			return "message";
		} catch (Exception e) {
//			data.put("message", "삭제 실패");
			model.addAttribute("text", "삭제 실패");
			return "message";
		}
//		return data;
	}

	// 수정 화면
	@GetMapping(value = "/update")
	public String update(Locale locale, Model model, @RequestParam("idx") String idx) {
		 try {
			DB db = new DB();
			User user = db.select(Integer.parseInt(idx));
			if (user == null) {
				model.addAttribute("text", "해당 회원이 존재하지 않습니다.");
				return "message";
			}
			model.addAttribute("idx", idx);
			model.addAttribute("id", user.id);
			model.addAttribute("pwd", user.pwd);
			model.addAttribute("name", user.name);
			model.addAttribute("phone", user.phone);
			model.addAttribute("address", user.address);
			return "update";
		} catch (Exception e) {
			model.addAttribute("text", "잘못된 접근입니다.");
			return "message";
		}
	}
	
	// 수정 처리
	@PostMapping(value = "/updateAction")
	public String update(Locale locale, Model model, HttpServletRequest request,
			@RequestParam("idx") String inputIdx,
			@RequestParam("id") String id,
			@RequestParam("pwd") String pwd,
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			@RequestParam("address") String address) {	
		try {
			// 정수 변환
			int idx = Integer.parseInt(inputIdx);

			DB db = new DB();
			db.updateData(new User(id, pwd, name, phone, address), idx);

			model.addAttribute("text", "수정 성공");
		} catch (Exception e) {
			model.addAttribute("text", "수정 실패");
		}
		return "message";
	}
	
	// 로그인 화면
	@GetMapping(value = "/login")
	public String login(Locale locale, Model model) {
		return "login";
	}
	
	// 로그인
	@PostMapping(value = "/loginAction")
	public String loginAction(Locale locale, Model model,
			HttpServletRequest request, HttpSession session
			) {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		DB db = new DB();
		User loggedUser = db.login(new User(id, pwd));
		if (loggedUser.userType == null || loggedUser.userType.isEmpty()) {
			session.setAttribute("is_login", false);
			session.setAttribute("user_type", "");
			session.removeAttribute("user_idx"); // 로그아웃 상태면 id 제거
			session.removeAttribute("loginId"); // 로그인 실패 시 제거
		} else {
			session.setAttribute("is_login", true);
			session.setAttribute("user_type", loggedUser.userType);
			session.setAttribute("user_idx", loggedUser.idx);
			session.setAttribute("loginId", loggedUser.id);
		}
		return "redirect:/";
	}
	
	// 로그아웃
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
	    session.invalidate();  // 모든 세션 삭제
	    return "redirect:/";
	}
	
	// 회원 목록 화면
	@GetMapping(value = "/list")
	public String list(Locale locale, Model model, HttpSession session) {
		boolean isLogin = false;
		String userType = "guest";
		try {
			isLogin = (Boolean)session.getAttribute("is_login");
			userType = (String) session.getAttribute("user_type");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isLogin && userType.equals("admin")) {
			return "list";
		}
		return "redirect:/";
	}
	
	// 마이페이지
	@GetMapping(value = "/mypage")
	public String mypage(Locale locale, Model model, HttpSession session) {
		boolean isLogin = false;
		try {
			isLogin = (Boolean)session.getAttribute("is_login");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(isLogin) {
			Integer userIdx = (Integer) session.getAttribute("user_idx");
			if (userIdx != null) {
			    DB db = new DB();
			    User user = db.select(userIdx);
			    model.addAttribute("idx", userIdx);
			    model.addAttribute("id", user.id);
			    model.addAttribute("name", user.name);
			    model.addAttribute("phone", user.phone);
			    model.addAttribute("address", user.address);
			    model.addAttribute("userType", user.userType);
			    model.addAttribute("created", user.created);
			    model.addAttribute("lastUpdated", user.lastUpdated);
			    return "mypage";
			}
		}
		return "redirect:/";
	}
}
