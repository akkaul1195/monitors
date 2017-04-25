/*
 * ak27997
 * jk36542
 *
 */
import java.util.concurrent.Semaphore; // for implementation using Semaphores
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrier {

	int parties;
	AtomicInteger currentIndex;
	Semaphore barrierSem;
	Semaphore leavingSem;

	public CyclicBarrier(int parties) {
		this.parties = parties;
		this.currentIndex = new AtomicInteger(parties);
		this.barrierSem = new Semaphore(0);
		this.leavingSem = new Semaphore(parties);
	}

	public int await() throws InterruptedException {
		leavingSem.acquire();
		int index = currentIndex.decrementAndGet();
		if(index > 0){
			//this means that there are still spots empty in the barrier so we add to barrier
			barrierSem.acquire();
		} else {
			//release parties - 1 of the semaphores then let them run
			barrierSem.release(parties-1);
		}

		if (currentIndex.incrementAndGet() == parties)
			leavingSem.release(parties);

	    return index;
	}
}
