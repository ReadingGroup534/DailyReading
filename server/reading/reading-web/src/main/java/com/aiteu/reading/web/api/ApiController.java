package com.aiteu.reading.web.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiteu.reading.api.service.ArticleService;
import com.aiteu.reading.api.service.BrowseService;

@Controller
public class ApiController extends BaseController{
	
	private ApiDealer mApiDealer = new ApiDealer();
	
	@Autowired
	private BrowseService browseService;
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 获得类目列表
	 * @param modelMap
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/category.json")
	public String category(ModelMap modelMap, HttpServletRequest req){
		Map<String, String> form = initForm(req);
		mApiDealer.doGetBrowses(modelMap, form, browseService);
		return "category.json";
	}
	
	/**
	 * 获得文章列表
	 * @param modelMap
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/list.json")
	public String list(ModelMap modelMap, HttpServletRequest req){
		Map<String, String> form = initForm(req);
		mApiDealer.doGetList(modelMap, form, articleService);
		return "list.json";
	}
	
	/**
	 * 获得当天的文章列表
	 * @return
	 */
	public String today(ModelMap modelMap, HttpServletRequest req){
		
		return "tooday.json";
	}
	
	/**
	 * 数据更新
	 * @param modelMap
	 * @param req
	 * @return
	 */
	@RequestMapping("/reading/api/update.json")
	public String update(ModelMap modelMap, HttpServletRequest req){
		
		return "update.json";
	}
	
	@RequestMapping("/reading/api/detail.json")
	public String detail(ModelMap modelMap, HttpServletRequest req){
		
		return "detail.json";
	}
}
