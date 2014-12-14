package com.aiteu.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class CrawlerManager {
	private static final String TAG = CrawlerManager.class.getSimpleName();
	private static final String SEEDS_PATH = "/seeds.txt";
	private static CrawlerManager mInstance = null;
	private UrlStorage mUrlStorage = null;
	private Scheduler mScheduler = null;
	
	private CrawlerManager(){
		initSeeds();
	}
	
	public static synchronized CrawlerManager getInstance(){
		if(mInstance != null){
			return mInstance;
		}else{
			mInstance = new CrawlerManager();
			return mInstance;
		}
	}
	
	/**
	 * 初始化爬虫种子文件
	 */
	private void initSeeds(){
		mUrlStorage = new UrlStorage();
		try{
			File seedsFile = new File(this.getClass().getResource(SEEDS_PATH).getPath());
			BufferedReader reader = new BufferedReader(new FileReader(seedsFile));
			while(reader.ready()){
				String line = reader.readLine();
				mUrlStorage.enqueue(line);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("total seeds size: "+mUrlStorage.getUrlSize());
	}
	
	//启动爬虫 
	public void start(){
		mScheduler = new Scheduler();
		mScheduler.start();
	}
	
	public void stop(){
		
	}
	
	public UrlStorage getUrlStorage(){
		return this.mUrlStorage;
	}
}
