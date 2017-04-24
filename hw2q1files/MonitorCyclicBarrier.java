/*
 * ak27997
 * jk36542
 *
 */
//import java.util.concurrent.Semaphore; // for implementation using Semaphores

public class MonitorCyclicBarrier {

	int parties;
	int initParties;
	int currentIndex;
//	Semaphore barrierSem;

	public MonitorCyclicBarrier(int parties) {
		this.parties = parties;
		this.initParties = parties;
		//need this because parties-1 is the first to arrive and 0 is the last one to arrive
		this.currentIndex = parties;
//		this.barrierSem = new Semaphore(0);
	}

	public synchronized int await() throws InterruptedException {
		this.currentIndex = this.currentIndex - 1;
		int index = this.currentIndex;

		if(index > 0){
			//this means that there are still spots empty in the barrier so we add to barrier
//			barrierSem.acquire();
			this.wait();
		} else {
			//release parties - 1 of the semaphores then let them run
//			barrierSem.release(parties-1);
			this.notifyAll();
			this.currentIndex = initParties;
		}

	    return index;
	}
}
