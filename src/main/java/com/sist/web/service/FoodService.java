package com.sist.web.service;
import java.util.*;

import org.springframework.stereotype.Service;

import com.sist.web.vo.*;

import lombok.RequiredArgsConstructor;

import com.sist.web.mapper.*;
@Service
@RequiredArgsConstructor
public class FoodService {

	private final FoodMapper fMapper;
	
	public List<FoodVO> foodListData(Map map)
	{
	   List<FoodVO> list = fMapper.foodListData(map);
	   for(FoodVO vo:list)
	   {
		   String[] temp = vo.getTheme().split(",");
		   // 화면에 레시피 카드 해시태그 같이 보이는 그게 테마인데 너무 개수가 많아서 두개만 나오게 함
		   vo.setTheme(temp[0]+","+temp[1]);
	   }
	   return list;
	}
	
	// 총 개수
	public int foodListTotalPage(String search)
	{
		return fMapper.foodListTotalPage(search);
	}
	
	public FoodVO foodDetailData(int no)
	{
		return fMapper.foodDetailData(no);
	}
}
