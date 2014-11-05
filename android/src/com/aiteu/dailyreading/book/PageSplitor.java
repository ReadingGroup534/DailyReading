package com.aiteu.dailyreading.book;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * @author liwei
 *
 */
public class PageSplitor {
	
	public static final int LIMIT = 4;
	private int start = 0;
	private int totalPage = -1;
	private int count = 0;
	private List<ItemDaily> dailyList = null;
	
	public PageSplitor(){
		this.init();
	}
	
	private void init(){
		this.start = 0;
		this.totalPage = -1;
		if(dailyList != null){
			dailyList.clear();
		}else{
			dailyList = new ArrayList<ItemDaily>();
		}
		resplit();
	}


	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<ItemDaily> getDailyList() {
		return dailyList;
	}

	public void addDailyList(List<ItemDaily> dailyList) {
		this.dailyList.addAll(0, dailyList);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		resplit();
	}
	
	private void resplit(){
		if(this.count <= 0){
			return;
		}
		int pages = this.count / LIMIT;
		this.totalPage = ((this.count % LIMIT) == 0) ? pages : pages+1;
	}
}
