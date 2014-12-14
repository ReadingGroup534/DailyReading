package com.aiteu.crawler;

/**
 * 抓取调度系统：主要是维护爬虫抓取队列
 * @author liwei
 *
 */
public class Scheduler implements WorkerSchedule{
	private static final int WORKER_INIT = 5;
	private Worker[] mWorkers = null; //爬虫专业户的工作组们
	CrawlerManager cm = CrawlerManager.getInstance();
	private UrlStorage mStorage = null;
	
	public Scheduler(){
		this(WORKER_INIT);
	}
	
	public Scheduler(int workerNum){
		mStorage = cm.getUrlStorage();
		mWorkers = new Worker[workerNum];
		for(int i=0;i < mWorkers.length;i ++){
			mWorkers[i] = new Worker("worker"+i);
			mWorkers[i].setWorkerSchedule(this);
		}
	}
	
	public void start(){
		schedule("");
	}

	public void schedule(String name) {
		if(mStorage.isEmpty()){
			System.out.println("url set empty");
			return;
		}
		synchronized (mWorkers) {
			for(int i=0;i<mWorkers.length;i++){
				if(mWorkers[i].isAvaliable()){
					mWorkers[i].setUrl(mStorage.dequeue());
					mWorkers[i].start();
					return;
				}
			}
		}
	}
}
