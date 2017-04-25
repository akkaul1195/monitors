import java.util.concurrent.locks.*;
public class MonitorA {
	private final Lock mutex = new ReentrantLock();
	private final Condition isEmpty = mutex.newCondition();
	int full; 
	
	public MonitorA(){
		full = 0;
	}
	
	public void unlock() throws InterruptedException {
		mutex.lock();
		try {
			full = 0;
			isEmpty.signal();
		} finally {
			mutex.unlock();
		}
	}
	
	public void await() throws InterruptedException {
		mutex.lock();
		try {
			while (!(full == 0)) {
				isEmpty.await();
				if (!(full == 0)) {
					isEmpty.signal();
				}
			}
			full = 1;
		} finally {
			mutex.unlock();
		}
	}
}
