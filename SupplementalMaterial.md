This appendix contains supplemental material for Chapter 3 - Synchronization Primitives.

**Supplemental Practice Problem:**

This is called the Mississippi River problem.

You and a group of n friends are on the eastern bank of the Mississippi river and want to cross to the western bank. The local bridge has been hit by a tornado and is unusable. The only way to cross the river is via a river boat. There is one thread called the riverboat. The river boat waits until full and then ferries the riders across the river. After crossing the boat again waits for more passengers. The boat can hold 2 people at a time.

(1)  Write a Java class called RiverBoat using java Monitors that allows the following methods:

runBoat() //called by boat thread; always runs

crossRiver() //called by rider thread

(2)  How would you extend your algorithm to work for multiple river boats?

**Java Monitors in C#:**

Up to this point, all monitor concepts in chapter 3 are implemented using java monitors. Although java is very popular, it is important to apply these same monitor concepts in a variety of different languages as certain industries and developers prefer certain software platforms. Below we have provided a description of the Monitor class in C# as a reference for utilizing monitor concepts in C#.

C# Class Monitors:

Monitor Actions:

Enter(Object) – Monitor.Enter attempts to acquire a lock on a certain object. It is used at the beginning of a Critical Section to begin mutual exclusion. Monitor.Enter is a blocking call and thus will block the thread until the lock is acquired.

TryEnter(Object) – Monitor.TryEnter is similar to Monitor.Enter with the important distinction that it doesn&#39;t block the calling thread if it fails. If TryEnter fails to acquire the lock it immediately returns.

Wait() – Monitor.Wait is used by a thread that has already acquired a lock to temporarily give up the lock. After giving up the lock it then blocks the thread. Threads then wait until a predefined timeout period or a object change communicated to them by Pulses.

Pulse(Object) – Monitor.Pulse sends a signal to waiting threads to notify them that the object status has changed. The waiting thread that receives a pulse is placed in the ready queue and, after reacquiring the lock, checks the object status.

Exit(Object) – Monitor.Exit releases the lock of an object and ends a Critical Section.

The &quot;Lock&quot; Keyword:

In C# the Lock keyword works much like the synchronized keyword in java. Objects can be passed to the keyword in the lock(Object){} format to create a synchronized block on that Object. Unlike the java synchronized keyword, the Lock keyword cannot be added as a descriptor to functions.