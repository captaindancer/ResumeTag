package com.wind.thread;

import java.util.concurrent.TimeUnit;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 6, 2014  4:50:42 PM
 *@Description
 */
public class StopThread {
	
	private static boolean stopRequested;
	
	private static synchronized void requestStop(){
		stopRequested=true;
	}
	
	private static synchronized boolean stopRequested(){
		return stopRequested;
	}

	public static void main(String[] args) throws InterruptedException {
		Thread backgroudThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i=0;
				while(!stopRequested()){
					i++;
					System.out.println(i);
				}
			}
		});
		backgroudThread.start();
		TimeUnit.SECONDS.sleep(1);
//		stopRequested=true;
		requestStop();
	}

}
