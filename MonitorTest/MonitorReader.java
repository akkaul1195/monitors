import java.util.concurrent.locks.*;
import java.util.Random;

public class MonitorReader {
	private final Lock mutex = new ReentrantLock();
	private final Condition isEmpty = mutex.newCondition();
  int full;
  int numWriters;
  int numReaders;

  public MonitorReader(){
    full = 0;
    numWriters = 0;
    numReaders = 0;
  }

  public void unlock(int i) throws InterruptedException {
		mutex.lock();
		try {
				    if(i == 0){
				      numWriters = 0;
				    } else if (i == 1){
				      numReaders -= 1;
			    }
			    System.out.println("Number of Readers: " + numReaders + " Number of Writers: " + numWriters);
			isEmpty.signalAll();
		} finally {
			mutex.unlock();
		}
  }

  public void await(int i) throws InterruptedException {
		mutex.lock();
		try {
				    if(i == 0){
				while (!      (numReaders == 0 && numWriters == 0)) {
					isEmpty.await();
				}
				      numWriters = 1;
				    } else if (i == 1){
				while (!      (numReaders <= 5 && numWriters == 0)) {
					isEmpty.await();
				}
				      numReaders += 1;
			    }
			    System.out.println("Number of Readers: " + numReaders + " Number of Writers: " + numWriters);
		} finally {
			mutex.unlock();
		}
	  }
}
