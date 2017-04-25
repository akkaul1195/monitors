import java.time.Duration;
import java.time.Instant;

public class MonitorQueueTest implements Runnable {
  final static int SIZE = 200;
  static int inc;
  static MonitorQueueSync mon = new MonitorQueueSync();

  public static void main(String args[]){
    Thread[] t = new Thread[SIZE];

    for (int i = 0; i < SIZE; ++i){
      System.out.println("NEW THREAD: " + i);
      t[i] = new Thread(new MonitorQueueTest());
    }

    for (int i = 0; i < SIZE; ++i){
      System.out.println("START THREAD: " + i);
      t[i].start();
    }

    Instant start = Instant.now();
		while(true){
			if (inc == 10000){
				Instant end = Instant.now();
				Duration timeElapsed = Duration.between(start, end);
				System.out.println("Time taken: "+ timeElapsed.getSeconds() +" Seconds");
				break;
			}
			System.out.println(inc);
		}

  }

  public static void nextThread(){
    try{
      mon.await();
      inc++;
      mon.unlock();
    } catch (InterruptedException e){
      e.printStackTrace();
    }
  }

  public void run(){
    for (int i = 0; i < 50; i++){
      nextThread();
    }
  }
}
