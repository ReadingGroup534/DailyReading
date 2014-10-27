package com.aiteu.dailyreading.reader;

import java.util.Vector;

import android.graphics.Canvas;

/**
 * 定义书本的页面
 * @author liwei
 *
 */
public class BookPage {
	
	private Vector<String> mPageContent;
	
	public BookPage(){
		mPageContent = new Vector<String>();
	}
	
	public void addOneLines(String line){
		this.mPageContent.add(line);
	}
	
	public Vector<String> getPageContent(){
		return this.mPageContent;
	}
	
	public void setPageContent(Vector<String> contents){
		this.mPageContent.clear();
		this.mPageContent.addAll(contents);
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(String line : mPageContent){
			sb.append(line+"\n");
		}
		return sb.toString();
	}

}
