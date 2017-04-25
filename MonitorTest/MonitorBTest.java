public class MonitorBTest implements Runnable {
  final static int SIZE = 400;
  static int inc;
  static MonitorB mB = new MonitorB();

  public static void main(String args[]){
    Thread[] t = new Thread[SIZE];

    for (int i = 0; i < SIZE; ++i) {
			t[i] = new Thread(new MonitorBTest());
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
			mB.await();
			inc++;
			mB.unlock();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
  }

  public void run(){
    for (int i = 0; i<100000; i++){
      increment();
    }
  }

}
