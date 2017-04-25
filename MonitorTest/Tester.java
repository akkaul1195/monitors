
public class Tester{
	int full;
	public Tester(){
		full = 0;
	}

	public synchronized void unlock() throws InterruptedException {
		full = 0;
		notifyAll();
	}

	public synchronized void await() throws InterruptedException {
		while (full != 0){
			wait();
		}
		full = 1;
	}

}