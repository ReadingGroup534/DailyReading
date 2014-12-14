package com.aiteu.crawler;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * url存储器，主要用来存储爬虫遍历过程中记录的url
 * @author liwei
 *
 */
public class UrlStorage {
	//记录所有url抓取情况
	private HashMap<String, UrlStatus> urlsMap = null;
	//将要抓取的url队列
	private LinkedList<String> urlQueue = null;
	
	public UrlStorage(){
		urlsMap = new HashMap<String, UrlStatus>();
		urlQueue = new LinkedList<String>();
	}
	
	public boolean isEmpty(){
		synchronized (urlQueue) {
			return urlQueue.isEmpty();
		}
	}
	
	public void enqueue(String url){
		synchronized (urlQueue) {
			urlQueue.addFirst(url);
		}
	}
	
	public String dequeue(){
		synchronized (urlQueue) {
			return urlQueue.poll();
		}
	}
	
	public void addFetched(String url, UrlStatus status){
		synchronized (urlsMap) {
			urlsMap.put(url, status);
		}
	}
	
	public int getUrlSize(){
		return urlQueue.size();
	}
}
