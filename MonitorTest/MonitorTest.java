
public class MonitorTest implements Runnable {
	final static int SIZE = 5;
	static int inc;
	static MonitorA mA = new MonitorA();
	
	public static void main(String args[]){
		Thread[] t = new Thread[SIZE];
		
		for (int i = 0; i < SIZE; ++i) {
			t[i] = new Thread(new MonitorTest());
		}
		
		for (int i = 0; i < SIZE; ++i) {
			t[i].start();
		}
		
		System.out.println("The Number " + inc);
		
	}
	
	public static void increment(){
		mA.await();
		inc++;
		mA.unlock();
	}
	
	public void run(){
		increment();
		
	}
	
}
