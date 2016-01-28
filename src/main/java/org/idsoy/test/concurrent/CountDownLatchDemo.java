package org.idsoy.test.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
/**
 * 测试CountDownLatch
 * 构造方法参数指定了计数的次数
 * 
 * 测试 await 方法和 countDown
 * countDown方法，当前线程调用此方法，则计数减一
 * awaint方法，调用此方法会一直阻塞当前线程，直到计数器的值为0
 * 
 * @author idsoy
 * @since 1.0.0
 */
public class CountDownLatchDemo {
	
	//TODO 非线程安全
	final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(2);// 两个工人的协作
		Worker worker1 = new Worker("zhang san", 5000, latch);
		Worker worker2 = new Worker("li si", 8000, latch);
		worker1.start();//
		worker2.start();//
		latch.await();// 等待所有工人完成工作
		System.out.println("all work done at " + sdf.format(new Date()));
	}

	static class Worker extends Thread {
		String			workerName;
		int				workTime;
		CountDownLatch	latch;

		public Worker(String workerName, int workTime, CountDownLatch latch) {
			this.workerName = workerName;
			this.workTime = workTime;
			this.latch = latch;
		}

		public void run() {
			System.out.println("Worker " + workerName + " do work begin at " + sdf.format(new Date()));
			doWork();// 工作了
			System.out.println("Worker " + workerName + " do work complete at " + sdf.format(new Date()));
			latch.countDown();// 工人完成工作，计数器减一

		}

		private void doWork() {
			try {
				Thread.sleep(workTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}