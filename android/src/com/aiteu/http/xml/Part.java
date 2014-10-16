package com.aiteu.http.xml;

/**
 * 定义文章的段落
 * @author liwei
 *
 */
public class Part {
	
	private String content;
	
	public Part(){
		this.content = "";
	}
	
	public Part(String content){
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
