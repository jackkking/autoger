package com.coracle.test;

public class Test {
	private static int count = 0;
	public synchronized static void inc(){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		count++;
	}
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable(){

				public void run() {
					Test.inc();
				}
				
			}).start();
		}
		System.out.println(count);
	}

}
