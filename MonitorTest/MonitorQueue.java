import java.util.concurrent.locks.*;
import java.util.*;

public class MonitorQueue{
	private final Lock mutex = new ReentrantLock();
	private final Condition isEmpty = mutex.newCondition();
  ArrayList<Integer> queue;
  ArrayList<Integer> cumulative;

  public MonitorQueue(){
    queue = new ArrayList<Integer>();
    cumulative = new ArrayList<Integer>();
  }

  public void unlock() throws InterruptedException {
		mutex.lock();
		try {
			    queue.remove(0);
			    Thread t = Thread.currentThread();
			    Integer i = (int) (long) t.getId();
			    System.out.println("Removed Thread: " + i);
			isEmpty.signalAll();
		} finally {
			mutex.unlock();
		}
  }

  public void await() throws InterruptedException {
		mutex.lock();
		try {
			    Thread t = Thread.currentThread();
			    Integer i = (int) (long) t.getId();
			    cumulative.add(i);
			    System.out.println("Added Thread: " + i);
			    queue.add(i);
			while (!    (queue.get(0) == i)) {
				isEmpty.await();
			}
		} finally {
			mutex.unlock();
		}
	  }
}
