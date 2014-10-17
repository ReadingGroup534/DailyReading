package com.aiteu.http.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * xml文件解析器：主要是把xml解析成想要的文档结构 
 * <article> <title></title> <abstract></abstract>
 * <author></author> <time></time> <sharetime></sharetime>
 * <articletype></articletype> <parts> <part></part> ...... </parts> </article>
 * 
 * @author liwei
 * 
 */
public class XmlParser {
	private static final String TAG = "XmlParser";

	private static XmlParser mParser = null;
	private XmlPullParser xmlParser = null; // pull method parse
	private SAXParser saxParser = null; // sax method parse
	private ParserType currentParserType = ParserType.SAX_PARSER;

	public enum ParserType {
		PULL_PARSER, SAX_PARSER
	}

	private XmlParser() {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xmlParser = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 可根据自己的需要传入xml解析方式的类型：pull , sax
	 * @param pt
	 * @return
	 */
	public static synchronized XmlParser getParser(ParserType pt) {
		if (mParser == null) {
			mParser = new XmlParser();
		}
		// 初始化所需的xml解析器
		mParser.initParser(pt);
		return mParser;
	}

	private void initParser(ParserType pt) {
		this.currentParserType = pt;
		if (pt == ParserType.PULL_PARSER) {
			if (xmlParser == null) {
				try {
					XmlPullParserFactory factory = XmlPullParserFactory
							.newInstance();
					factory.setNamespaceAware(true);
					xmlParser = factory.newPullParser();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (saxParser == null) {
				try {
					SAXParserFactory factory = SAXParserFactory.newInstance();
					saxParser = factory.newSAXParser();

				} catch (FactoryConfigurationError e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解析xml文件的输入流，提取制定的文档数据
	 * 
	 * @param in
	 * @return
	 */
	public XmlDocument getDocument(InputStream in) {

		if (this.currentParserType == ParserType.PULL_PARSER) {
			return getDocumentByPullParser(in);
		} else {
			return getDocumentBySaxParser(in);
		}
	}

	private XmlDocument getDocumentByPullParser(InputStream in) {
		Log.d(TAG, "getDocumentByPullParser");
		XmlDocument document = new XmlDocument();
		if (xmlParser == null) {
			return document;
		}
		try {
			xmlParser.setInput(in, "utf-8");
			int eventType = xmlParser.getEventType();
			Log.d(TAG, "start parse document");
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					final String tagName = xmlParser.getName();
					if (tagName.equals("title")) {
						String title = safeNextText(xmlParser);
						document.setTitle(title);
					} else if (tagName.equals("abstract")) {
						String abstractStr = safeNextText(xmlParser);
						document.setAbstracts(abstractStr);
					} else if (tagName.equals("author")) {
						String author = safeNextText(xmlParser);
						document.setAuthor(author);
					} else if (tagName.equals("time")) {
						String time = safeNextText(xmlParser);
						document.setTime(time);
					} else if (tagName.equals("sharetime")) {
						int shareTime = Integer
								.parseInt(safeNextText(xmlParser));
						document.setShare_times(shareTime);
					} else if (tagName.equals("articletype")) {
						String articletype = safeNextText(xmlParser);
						document.setArticle_type(articletype);
					} else if (tagName.equals("part")) {
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
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return document;
	}

	private XmlDocument getDocumentBySaxParser(InputStream in) {
		Log.d(TAG, "getDocumentBySaxParser");
		XmlDocument document = new XmlDocument();
		if (saxParser == null) {
			return document;
		}
		try {
			saxParser.parse(in, new SaxHandler(document));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} 
		return document;
	}

	private String safeNextText(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		String result = parser.nextText();
		if (parser.getEventType() != XmlPullParser.END_TAG) {
			parser.nextTag();
		}
		return result;
	}

	class SaxHandler extends DefaultHandler {

		private XmlDocument document = null;
		private String tagName = "";

		public SaxHandler(XmlDocument doc) {
			this.document = doc;
		}
	

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.startDocument();
			Log.d(TAG, "start parse document");
		}

		@SuppressLint("DefaultLocale")
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			 tagName= localName.length()!=0?localName:qName;
			 tagName = tagName.toLowerCase().trim();
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			if(null != tagName){
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
					ArticlePart part = new ArticlePart(data);
					document.addArticlePart(part);
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			this.tagName = null;
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
			Log.d(TAG, "end parse document");
		}
	}
}
