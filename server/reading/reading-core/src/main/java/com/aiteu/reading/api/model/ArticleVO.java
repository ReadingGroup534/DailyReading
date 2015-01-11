package com.aiteu.reading.api.model;

public class ArticleVO {
	private String title;
	private String author;
	private String abstracts;
	private int recommends;
	private int scans;
	private int shares;
	private String url;
	
	public ArticleVO(){
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getRecommends() {
		return recommends;
	}

	public void setRecommends(int recommends) {
		this.recommends = recommends;
	}

	public int getScans() {
		return scans;
	}

	public void setScans(int scans) {
		this.scans = scans;
	}

	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
