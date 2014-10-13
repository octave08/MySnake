package com.iot.mysnake;

import android.os.Handler;

public class ThreadTime extends Thread {
	Handler m_handler;
	Boolean isRun = true;
	Boolean isWait = false;

	public ThreadTime(Handler handler) {
		this.m_handler = handler;
	}
	
	public void pauseNresumeThread(boolean isWait) {
		synchronized(this) {
			this.isWait = isWait;
			notify();
		}
	}
	
	public void stopThread() {
		synchronized(this) {
			isRun = false;
			notify();
		}
	}
	
	public void run() {
		while(isRun) {
			m_handler.sendEmptyMessage(0);
			try{
				Thread.sleep(10);
				
			}catch(Exception e) {}
			
			if(isWait) {
				try {
					synchronized(this) {
					wait();
					}
				}catch(Exception e) {}
			}
		
		}
		
		
	}
	
	
	
}
