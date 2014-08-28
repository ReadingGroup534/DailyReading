package com.aiteu.dailyreading.book;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * //SAX类：DefaultHandler，它实现了ContentHandler接口。在实现的时候，只需要继承该类，重载相应的方法即可。
 * 
 * @author liyangchao
 * 
 */

public class XMLContentHandler extends DefaultHandler {

	private List<BookBean> books = null;
	private BookBean currentBook;
	private String tagName = null;// 当前解析的元素标签

	public List<BookBean> getBookBeans() {
		return books;
	}

	/**
	 * 接收字符数据的通知。该方法用来处理在XML文件中读到的内容，第一个参数用于存放文件的内容，
	 * 后面两个参数是读到的字符串在这个数组中的起始位置和长度，使用new String(ch,start,length)就可以获取内容。
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName != null) {
			String data = new String(ch, start, length);
			if (tagName.equals("title")) {
				this.currentBook.setTitle(data);
			} else if (tagName.equals("content")) {
				this.currentBook.setContent(data);
			}
		}
	}

	/**
	 * 接收文档开始的通知。当遇到文档的开头的时候，调用这个方法，可以在其中做一些预处理的工作。
	 */
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		books = new ArrayList<BookBean>();
	}

	/**
	 * 接收元素开始的通知。当读到一个开始标签的时候，会触发这个方法。其中namespaceURI表示元素的命名空间；
	 * localName表示元素的本地名称（不带前缀）；qName表示元素的限定名（带前缀）；attributes 表示元素的属性集合
	 */

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		if (localName.equals("book")) {
			currentBook = new BookBean();
			currentBook.setTitle(attributes.getValue("id"));
		}
		this.tagName = localName;
	}

	/**
	 * 接收文档的结尾的通知。在遇到结束标签的时候，调用这个方法。其中，uri表示元素的命名空间；
     * localName表示元素的本地名称（不带前缀）；name表示元素的限定名（带前缀）

	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if (localName.equals("book")) {
			books.add(currentBook);
			currentBook = null;
		}
		this.tagName = null;
	}

}
