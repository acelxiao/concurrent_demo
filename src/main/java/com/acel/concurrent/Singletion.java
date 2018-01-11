package main.java.com.acel.concurrent;

public class Singletion {
	
	private static class InnerSingletion {
		private static Singletion single = new Singletion();
	}
	
	public static Singletion getInstance(){
		return InnerSingletion.single;
	}

	public static void main(String[] args) {
		System.out.println(Singletion.getInstance().hashCode());
		System.out.println(Singletion.getInstance().hashCode());
		System.out.println(Singletion.getInstance().hashCode());
		System.out.println(Singletion.getInstance().hashCode());
	}
}
