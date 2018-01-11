package main.java.com.acel.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * wait notfiy 方法，wait释放锁，notfiy不释放锁
 * @author alienware
 *
 */
public class WaitNotify {
	private volatile static List list = new ArrayList();	
	
	public void add(){
		list.add("bjsxt");
	}
	public int size(){
		return list.size();
	}
	
	public static void main(String[] args) {
		
		final WaitNotify list2 = new WaitNotify();
		
		// 1 实例化出来一个 lock
		// 当使用wait 和 notify 的时候 ， 一定要配合着synchronized关键字去使用
		final Object lock = new Object();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (lock) {
						for(int i = 0; i <10; i++){
							list2.add();
							System.out.println("t1得到了锁,并添加了一个元素..list大小="+list2.size());
							Thread.sleep(500);
							if(list2.size() == 5){
								System.out.println("list大小是5,并唤醒t2线程..");
								lock.notify();
							}
						}						
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					System.out.println("t2拿到锁,并运行...");
					try {
						System.out.println("t2释放锁,并进入等待状态...");
						lock.wait();
						System.out.println("t2再次得到锁,并执行剩下逻辑...");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "t2");	
		
		t2.start();// 这里因为有可能t1先执行得到锁,给个时间间隔确保t2先得到锁

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		t1.start();
		
	}
	
}
