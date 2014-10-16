package com.aiteu.http.xml;

/**
 * 定义文章的段落
 * @author liwei
 *
 */
public class ArticlePart {
	
	private String part;
	
	public ArticlePart(){
		this.part = "";
	}
	
	public ArticlePart(String part){
		this.part = part;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}
	
	public String toString(){
		
		return this.part +"\n";
	}
}
