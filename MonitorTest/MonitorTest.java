
public class MonitorTest implements Runnable {
	final static int SIZE = 200;
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
		while(true){
			System.out.println("The Number " + inc);
		}
	}
	
	public static void increment(){
		try{
			mA.await();
			inc++;
			mA.unlock();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		int i = 0;
		for (i=0; i<400000; i++){
			increment();
		}
			
	}
	
}
