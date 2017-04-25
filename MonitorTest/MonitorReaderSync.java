import java.util.Random;

public class MonitorReaderSync {
  int full;
  int numWriters;
  int numReaders;

  public MonitorReaderSync(){
    full = 0;
    numWriters = 0;
    numReaders = 0;
  }

  public synchronized void unlock(int i) throws InterruptedException{
    if(i == 0){
      numWriters = 0;
    } else if (i == 1){
      numReaders -= 1;
    }
    System.out.println("Number of Readers: " + numReaders + " Number of Writers: " + numWriters);
    notifyAll();
  }

  public synchronized void await(int i) throws InterruptedException{
    if(i == 0){
      while(!(numReaders == 0 && numWriters == 0)){
        wait();
      }
      numWriters = 1;
    } else if (i == 1){
      while(!(numReaders <= 5 && numWriters == 0)){
        wait();
      }
      numReaders += 1;
    }
    System.out.println("Number of Readers: " + numReaders + " Number of Writers: " + numWriters);
  }
}
