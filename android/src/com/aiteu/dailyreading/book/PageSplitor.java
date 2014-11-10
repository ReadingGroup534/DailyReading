package com.aiteu.dailyreading.book;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * @author liwei
 *
 */
public class PageSplitor {
	public static final int LOAD_TYPE_REFRESH = 0x01;
	public static final int LOAD_DATA_MORE = 0x02;
	public static final int LIMIT = 2;
	private int start = 0;
	private int totalPage = -1;
	private int count = 0;
	private List<ItemDaily> dailyList = null;
	private int currentPage = 1;
	private int loadType = LOAD_DATA_MORE;
	
	public PageSplitor(){
		this.init();
	}
	
	public void init(){
		this.start = 0;
		this.currentPage = 1;
		this.totalPage = -1;
		this.loadType = LOAD_DATA_MORE;
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
		if(loadType == LOAD_TYPE_REFRESH){
			this.dailyList.addAll(0, dailyList);
		}else{
			this.dailyList.addAll(dailyList);
		}
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
	
	public boolean hasNextPage(){
		if(totalPage > currentPage){
			return true;
		}else{
			return false;
		}
	}

	public int getLoadType() {
		return loadType;
	}

	public void setLoadType(int loadType) {
		this.loadType = loadType;
	}
	
}
