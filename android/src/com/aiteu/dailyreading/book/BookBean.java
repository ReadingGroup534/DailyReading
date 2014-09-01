package com.aiteu.dailyreading.book;

import java.io.Serializable;

import android.R.integer;
import android.R.string;

public class BookBean implements Serializable {

	private int id;
	private int recommend_star;
	private String title;
	private String content;
	private String author;
	private String subtitle;
	private String create_time;
	private String source;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int getRecommend_star() {
		return recommend_star;
	}

	public void setRecommend_star(int recommend_star) {
		this.recommend_star = recommend_star;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "title:" + title + ", content:" + content;
	}

}
