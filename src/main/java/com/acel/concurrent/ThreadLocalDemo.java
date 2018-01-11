package main.java.com.acel.concurrent;

public class ThreadLocalDemo {

	public static ThreadLocal<String> th = new ThreadLocal<String>();
	
	public static void main(String[] args) throws InterruptedException {
		
		final ThreadLocalDemo ct = new ThreadLocalDemo();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				ThreadLocalDemo.th.set("张三");
				System.out.println("t1线程里去获取:"+ThreadLocalDemo.th.get());
			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					System.out.println("t2线程里去获取:"+ThreadLocalDemo.th.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t2");
		System.out.println("主线程里去获取1:"+ThreadLocalDemo.th.get());
		t1.start();
		t2.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("主线程里去获取2:"+ThreadLocalDemo.th.get());
	}
	
}
