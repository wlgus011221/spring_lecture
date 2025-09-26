package com.kopo.project1;

import java.util.ArrayList;
import java.util.Arrays;
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
			return "userUpdate";
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
			session.removeAttribute("loginName");
		} else {
			session.setAttribute("is_login", true);
			session.setAttribute("user_type", loggedUser.userType);
			session.setAttribute("user_idx", loggedUser.idx);
			session.setAttribute("loginId", loggedUser.id);
			session.setAttribute("loginName", loggedUser.name);
		}
		return "redirect:/";
	}
	
	// 로그아웃
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
	    session.invalidate();  // 모든 세션 삭제
	    return "redirect:/";
	}
	
	// 회원 목록 화면 - 관리자만
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
			return "userList";
		}
		return "redirect:/";
	}
	
	// 공지사항 작성 - 관리자만
	@GetMapping(value = "/post")
	public String post(Locale locale, Model model, HttpSession session) {
		boolean isLogin = false;
		String userType = "guest";
		try {
			isLogin = (Boolean)session.getAttribute("is_login");
			userType = (String) session.getAttribute("user_type");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isLogin && userType.equals("admin")) {
			return "post";
		}
		return "redirect:/";
	}
	
	// 제목 초성 저장
	public String getChosung(String str) {
		StringBuilder sb = new StringBuilder();
		char[] chosung = {
	        'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'
	    };	
		
		for(char c : str.toCharArray()) {
	        if(c >= '가' && c <= '힣') {
	            int unicode = c - '가';
	            int choIndex = unicode / (21*28);
	            sb.append(chosung[choIndex]);
	        } else {
	            sb.append(c); // 한글이 아니면 그대로
	        }
	    }
	    return sb.toString();
	}
	
	// 공지사항 작성
	@PostMapping(value = "/postInsert")
	public String insertPost(Locale locale, Model model,
			HttpServletRequest request, HttpSession session) {
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
			    String title = request.getParameter("title");
				String contents = request.getParameter("contents");
			    User user = db.select(userIdx);
			    String writer = user.name;
			    
			    String titleChosung = this.getChosung(title);
			    
			    db.insertData(new Post(title, contents, writer, titleChosung));
			    
			    model.addAttribute("text", "등록 완료");
				return "message";
			}
		}
		return "redirect:/";
	}
		
	// 공지사항 화면
	@GetMapping(value = "/postList")
	public String postList(Locale locale, Model model, HttpSession session) {
		return "postList";
	}
	
	// 공지사항 상세화면
	@GetMapping(value = "/postDetail")
	public String postDetail(Locale locale, Model model, @RequestParam("idx") String postIdx, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    String userType = (String) session.getAttribute("user_type");
	    model.addAttribute("userType", userType);
	    model.addAttribute("idx", postIdx);
	    
		try {
			// 정수 변환
			int idx = Integer.parseInt(postIdx);

			DB db = new DB();
			Post post = db.selectPost(idx);

			model.addAttribute("title", post.title);
			model.addAttribute("contents", post.contents);
			model.addAttribute("writer", post.writer);
			model.addAttribute("created", post.created);
			model.addAttribute("lastUpdated", post.lastUpdated);
			return "postDetail";
		} catch (Exception e) {
			model.addAttribute("text", "잘못된 접근입니다.");
			return "message";
		}
	}
	
	// 게시글 수정
	@GetMapping(value = "/updatePost")
	public String updatePost(Locale locale, Model model, @RequestParam("idx") String idx) {
		 try {
			DB db = new DB();
			Post post = db.selectPost(Integer.parseInt(idx));
			if (post == null) {
				model.addAttribute("text", "해당 게시글이 존재하지 않습니다.");
				return "message";
			}
			model.addAttribute("idx", idx);
			model.addAttribute("title", post.title);
			model.addAttribute("contents", post.contents);
			return "postUpdate";
		} catch (Exception e) {
			model.addAttribute("text", "잘못된 접근입니다.");
			return "message";
		}
	}
	
	// 수정 처리
	@PostMapping(value = "/updatePostAction")
	public String updatePostAction(Locale locale, Model model, HttpServletRequest request,
			@RequestParam("idx") String inputIdx,
			@RequestParam("title") String title,
			@RequestParam("contents") String contents) {	
		try {
			// 정수 변환
			int idx = Integer.parseInt(inputIdx);
			
			String titleChosung = this.getChosung(title);
			
			
			DB db = new DB();
			db.updatePost(new Post(title, contents, titleChosung), idx);

			model.addAttribute("text", "수정 성공");
		} catch (Exception e) {
			model.addAttribute("text", "수정 실패");
		}
		return "message";
	}
	
	// 게시글 삭제
	@GetMapping(value = "/deletePost")
	public String deletePost(Locale locale, Model model, @RequestParam("idx") String inputIdx) {
//		HashMap<String, String> data = new HashMap<>();
		try {
			// 정수 변환
			int idx = Integer.parseInt(inputIdx);

			DB db = new DB();
			db.deletePost(idx);

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
