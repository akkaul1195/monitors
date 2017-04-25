import java.util.*;

public class MonitorQueueSync {
  ArrayList<Integer> queue;
  ArrayList<Integer> cumulative;

  public MonitorQueueSync(){
    queue = new ArrayList<Integer>();
    cumulative = new ArrayList<Integer>();
  }

  public synchronized void unlock() throws InterruptedException {
    queue.remove(0);
    Thread t = Thread.currentThread();
    Integer i = (int) (long) t.getId();
    notifyAll();
  }

  public synchronized void await() throws InterruptedException {
    Thread t = Thread.currentThread();
    Integer i = (int) (long) t.getId();
    cumulative.add(i);
    System.out.println("Added Thread: " + i);
    queue.add(i);
    while (queue.get(0) != i){
      wait();
    }
  }

}
