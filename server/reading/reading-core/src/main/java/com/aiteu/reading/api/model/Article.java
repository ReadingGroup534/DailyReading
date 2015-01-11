package com.aiteu.reading.api.model;

public class Article {

	private String title;
	private String author;
	private String articleType;
	private String abstracts;
	private int recommendStar;
	private int praiseTimes;
	private int shareTimes;
	private int scanTimes;
	private String detailUrl;
	private int browseId;

	public Article() {

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

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public int getRecommendStar() {
		return recommendStar;
	}

	public void setRecommendStar(int recommendStar) {
		this.recommendStar = recommendStar;
	}

	public int getPraiseTimes() {
		return praiseTimes;
	}

	public void setPraiseTimes(int praiseTimes) {
		this.praiseTimes = praiseTimes;
	}

	public int getShareTimes() {
		return shareTimes;
	}

	public void setShareTimes(int shareTimes) {
		this.shareTimes = shareTimes;
	}

	public int getScanTimes() {
		return scanTimes;
	}

	public void setScanTimes(int scanTimes) {
		this.scanTimes = scanTimes;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public int getBrowseId() {
		return browseId;
	}

	public void setBrowseId(int browseId) {
		this.browseId = browseId;
	}
	
}
