package kr.ac.kopo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
//	@Autowired
//	private NewsDAO dao;
//	
//	@Autowired
//	private NewsVO vo;
	
//	@RequestMapping("/newsApi")
//	public ResponseEntity<null> newsViewApi() {
//		
//		
//		return ResponseEntity.ok(null);
//	}
	
	@RequestMapping("/testApi")
	public List<String> testListApi() {
		
		List<String> li = new ArrayList<>();
		li.add("100");
		li.add("200");
		li.add("200");
		
		return li;
	}
	
}