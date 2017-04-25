import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class MonitorReadWrite implements Runnable {
  final static int SIZE = 200;
  static int inc;
  static MonitorReaderSync mon = new MonitorReaderSync();

  public static void main(String args[]){
    Thread[] t = new Thread[SIZE];

    for (int i = 0; i < SIZE; ++i){
      System.out.println("NEW THREAD: " + i);
      t[i] = new Thread(new MonitorReadWrite());
    }

    for (int i = 0; i < SIZE; ++i){
      System.out.println("START THREAD: " + i);
      t[i].start();
    }

    Instant start = Instant.now();
		while(true){
			if (inc == 30000){
				Instant end = Instant.now();
				Duration timeElapsed = Duration.between(start, end);
				System.out.println("Time taken: "+ timeElapsed.getSeconds() +" Seconds");
				break;
			}
			System.out.println(inc);
		}
    // Instant end = Instant.now();
    // Duration timeElapsed = Duration.between(start, end);
    // System.out.println("Time taken: "+ timeElapsed.getSeconds() +" Seconds");

  }

  public static void nextThread(){
    try{
      Random rand = new Random();
      int randomNumber = rand.nextInt((1-0)+1) + 0;
      mon.await(randomNumber);
      inc++;
      mon.unlock(randomNumber);
    } catch (InterruptedException e){
      e.printStackTrace();
    }
  }

  public void run(){
    for(int i = 0; i < 150; i++){
      nextThread();
    }

  }
}
