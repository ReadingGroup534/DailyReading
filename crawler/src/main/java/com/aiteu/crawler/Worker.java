package com.aiteu.crawler;

/**
 * 爬虫的实际执行者
 * @author liwei
 *
 */
public class Worker extends Thread{
	public static final int READY = -0x01; //已经准备就绪，可分配任务
	public static final int WORKING = 0x01; //正在工作中，不可打扰
	public static final int FINISHED = 0x00; //任务已经完成
	public boolean flag = true;
	private int state;
	private String url;
	private String name;
	private WorkerSchedule mSchedule = null;
	
	public void setStatus(int st){
		this.state = st;
	}
	
	public int getStatus(){
		return state;
	}
	
	public void setWorkerSchedule(WorkerSchedule ws){
		this.mSchedule = ws;
	}
	
	public Worker(String name){
		this.name = name;
		this.state = READY;
	}
	
	public boolean isAvaliable(){
		if((state & READY) == READY){
			return true;
		}
		return false;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public void run(){
		this.state = WORKING;
		//do something
		this.state = FINISHED;
		//release resource
		this.state = READY;
	}
}
