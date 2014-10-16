package com.aiteu.http.xml;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;


/**
 * xml文件解析器：主要是把xml解析成想要的文档结构
 * <article>
 * <title></title>
 * <author></author>
 * <time></time>
 * <parts>
 * <part></part>
 * ......
 * </parts>
 * </article>
 * 
 * @author liwei
 *
 */
public class XmlParser {
	private static final String TAG = "XmlParser";
	
	private static XmlParser mParser = null;
	private XmlPullParser xmlParser = null;
	
	private XmlParser(){
		try{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xmlParser = factory.newPullParser();
		}catch(XmlPullParserException e){
			e.printStackTrace();
		}
	}
	
	public static synchronized XmlParser getParser(){
		if(mParser == null){
			mParser = new XmlParser();
		}
		return mParser;
	} 
	
	/**
	 * 解析xml文件的输入流，提取制定的文档数据
	 * @param in
	 * @return
	 */
	public XmlDocument getDocument(InputStream in){
		XmlDocument document = new XmlDocument();
		if(xmlParser == null){
			return document;
		}
		try{
			xmlParser.setInput(in, "utf-8");
			int eventType = xmlParser.getEventType();
			Log.d(TAG, "start parse document");
			while(eventType != XmlPullParser.END_DOCUMENT){
				switch(eventType){
					case XmlPullParser.START_TAG:
						final String tagName = xmlParser.getName();
						if(tagName.equals("title")){
							String title = safeNextText(xmlParser);
							document.setTitle(title);
						}else if(tagName.equals("author")){
							String author = safeNextText(xmlParser);
							document.setAuthor(author);
						}else if(tagName.equals("time")){
							String time = safeNextText(xmlParser);
							document.setTime(time);
						}else if(tagName.equals("part")){
							String partStr = safeNextText(xmlParser);
							ArticlePart part = new ArticlePart(partStr);
							document.addArticlePart(part);
						}
						break;
						default:
							break;
				}
				eventType = xmlParser.next();
			}
			Log.d(TAG, "end parse document");
		}catch(XmlPullParserException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		return document;
	}
	
	private String safeNextText(XmlPullParser parser) throws XmlPullParserException, IOException{
		String result = parser.nextText();
		if(parser.getEventType() != XmlPullParser.END_TAG){
			parser.nextTag();
		}
		return result;
	}
}
