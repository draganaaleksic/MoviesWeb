package rs.is.fon.parse;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool{
	
	private int poolSize = 10;
	private int maxPoolSize = 10;
	private long keepAliveTime = 10;
	private ThreadPoolExecutor threadPool;
	private ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10);
	
	
	public ThreadPool(){
		threadPool = new ThreadPoolExecutor(poolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue);
		RejectedExecutionHandler handler = new RejectedExecutionHandler() {			
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				executor.execute(r);
			}
		};
		threadPool.setRejectedExecutionHandler(handler);
		threadPool.prestartAllCoreThreads();
	}
	
	public void runTask(Runnable task){
		threadPool.execute(task);
	}
	
	public void shutDown(){
		threadPool.shutdown();
	}
	
}
