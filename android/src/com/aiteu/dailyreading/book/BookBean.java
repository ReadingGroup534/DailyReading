package com.aiteu.dailyreading.book;

import java.io.Serializable;

import android.R.integer;
import android.R.string;

public class BookBean implements Serializable {

	private int article_id;
	private boolean active;
	private int recommend_star;
	private String title;
	private String content;
	private String author;
	private String article_type;
	private String abstracts;
	private String create_time;
	private String source;

	
	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getArticle_type() {
		return article_type;
	}

	public void setArticle_type(String article_type) {
		this.article_type = article_type;
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


	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
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
