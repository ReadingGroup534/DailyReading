package com.aiteu.http.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.aiteu.http.util.ArticleType;

import android.R.integer;

/**
 * 定义文档所包含的元素
 * @author liwei
 *
 */
public class XmlDocument implements Serializable{
	
	private String title; //文章的标题
	private String author; //文章的作者
	private String time; //文章发表的时间
	private String abstracts; //文章摘要
	private int share_times; //点赞次数
	private String article_type; //文章类型
	private List<ArticlePart> content; //文章的内容
	
	public XmlDocument(){
		this.title = "";
		this.author = "";
		this.share_times = 0;
		this.abstracts = "";
		this.article_type = ArticleType.EMOTIONAL;
		this.content = new ArrayList<ArticlePart>();
	}
	
	public XmlDocument(String title){
		this.title = title;
		this.author = "";
		this.share_times = 0;
		this.abstracts = "";
		this.article_type = ArticleType.EMOTIONAL;
		this.content = new ArrayList<ArticlePart>();
	}
	
	/**
	 * 给文档增加一个段落
	 * @param part
	 */
	public void addArticlePart(ArticlePart part){
		this.content.add(part);
	}

	public String getTitle() {
		return title;
	}
	
	
	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public int getShare_times() {
		return share_times;
	}

	public void setShare_times(int share_times) {
		this.share_times = share_times;
	}

	public String getArticle_type() {
		return article_type;
	}

	public void setArticle_type(String article_type) {
		this.article_type = article_type;
	}

	/**
	 * 设置文档的标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}
	/**
	 * 设置文档的作者
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<ArticlePart> getContent() {
		return content;
	}
	
	/**
	 * 设置文档的内容
	 * @param content
	 */
	public void setContent(List<ArticlePart> content) {
		this.content = content;
	}
	
	/**
	 * 只提取文档的内容
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(null == content){
			return "";
		}
		for(ArticlePart part : content){
			sb.append(part.toString());
		}
		return sb.toString();
	}
}
