package com.aiteu.dailyreading.book;

import java.io.Serializable;

import android.R.string;

public class BookBean implements Serializable {

	private String title;
	private String content;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "title:" + title + ", content:" + content;
	}
	
	
}
