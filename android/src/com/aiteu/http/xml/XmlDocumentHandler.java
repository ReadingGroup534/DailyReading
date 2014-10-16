package com.aiteu.http.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlDocumentHandler extends DefaultHandler{
	
	private String tagName = null;
	private XmlDocument document = null;
	

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName != null) {
			String data = new String(ch, start, length);
			if (tagName.equals("title")) {
				document.setTitle(data);
			}else if(tagName.equals("abstract")){
				document.setAbstracts(data);
			}else if (tagName.equals("author")) {
				document.setAuthor(data);
			}else if (tagName.equals("time")) {
				document.setTime(data);
			}else if (tagName.equals("sharetime")) {
				document.setShare_times(Integer.parseInt(data));
			}else if (tagName.equals("articletype")) {
				document.setArticle_type(data);
			}else if (tagName.equals("part")) {
				ArticlePart part = new ArticlePart();
				document.addArticlePart(part);
			}
		}
	}

	/**
	 * 接收元素开始的通知。当读到一个开始标签的时候，会触发这个方法。其中namespaceURI表示元素的命名空间；
	 * localName表示元素的本地名称（不带前缀）；qName表示元素的限定名（带前缀）；attributes 表示元素的属性集合
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		 tagName= localName.length()!=0?localName:qName;
		 tagName = tagName.toLowerCase().trim();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		this.tagName = null;
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		document = new XmlDocument();
	}
	
	
	

}
