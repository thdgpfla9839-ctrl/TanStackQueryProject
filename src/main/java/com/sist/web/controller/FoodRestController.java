package com.sist.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.sist.web.vo.*;

import lombok.RequiredArgsConstructor;

import com.sist.web.service.*;

// Server(java) --------- 자바스크립트 이 둘이 통신할 수 있게 해주는게 JSON
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FoodRestController {

	private final FoodService fService;
	
	@GetMapping("/food/list_react/{search}/{page}") // 이렇게 전송하는 방식이 PathVariable
	public ResponseEntity<Map> food_list(@PathVariable("search") String search,
			@PathVariable("page") int page)
	{
		Map map = new HashMap();
		try
		{
			   map.put("search", search);
			   map.put("start", (page*12)-12);
			   
			   List<FoodVO> list = fService.foodListData(map);
			   int count = fService.foodListTotalPage(search);
			   
			   // 페이지 나누기
			   int totalpage = (int)(Math.ceil(count/12.0));
			   final int BLOCK = 10;
			   int startPage = ((page-1)/BLOCK*BLOCK)+1;
			   int endPage = ((page-1)/BLOCK*BLOCK)+BLOCK;
			   if(endPage>totalpage)
				   endPage=totalpage;
			   
			   // 초기화
			   map = new HashMap();
			   
			   // 값 보내기
			   map.put("list", list);
			   map.put("search", search);
			   map.put("curpage", page);
			   map.put("totalpage", totalpage);
			   map.put("count", count);
			   map.put("startPage", startPage);
			   map.put("endPage", endPage);
			   	
		} 
		catch (Exception ex)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			
		}
		return ResponseEntity.ok(map);
	}
	
	// 레시피 상세보기
	@GetMapping("/food/detail_react/{no}")
	public ResponseEntity<FoodVO> food_detail(@PathVariable("no") int no)
	{
		FoodVO vo = new FoodVO();
		try 
		{
		   vo = fService.foodDetailData(no);	
		} 
		catch (Exception ex)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(vo);
	}
}
