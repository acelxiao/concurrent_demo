package main.java.com.acel.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class UseFuture implements Callable<String>{
	private String para;
	
	public UseFuture(String para){
		this.para = para;
	}
	
	/**
	 * 这里是真实的业务逻辑，其执行可能很慢
	 */
	@Override
	public String call() throws Exception {
		//模拟执行耗时
		Thread.sleep(3000);
		String result = this.para + "处理完成";
		return result;
	}
	
	//主控制函数
	public static void main(String[] args) throws Exception {
		String queryStr = "AAAA";
		//构造FutureTask，并且传入需要真正进行业务逻辑处理的类,该类一定是实现了Callable接口的类
		FutureTask<String> future = new FutureTask<String>(new UseFuture(queryStr));
		//创建一个固定线程的线程池且线程数为1,
		ExecutorService executor = Executors.newFixedThreadPool(1);
		//这里提交任务future,则开启线程执行RealData的call()方法执行
		executor.submit(future);
		System.out.println("发出请求复杂数据~~~");
		try {
			//这里可以做额外的数据操作，也就是主程序执行其他业务逻辑
			System.out.println("我继续执行下去~正在做其他事情了~~~~");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//调用获取数据方法,如果call()方法没有执行完成,则依然会进行等待
		System.out.println("处理完成了,获得复杂数据：" + future.get());// 此步骤会阻塞主线程
		System.out.println("end");
		executor.shutdown();
	}

}
