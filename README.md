# monitors
Final Lab
Monitors with Implicit Signals
Develop a new type of Monitor lock that uses implicit signals. Every time a thread leaves the monitor (by unlock or await), it determines if there is any thread that should be signaled. Implement and evaluate different strategies: such as one that signals all threads, or one that signals one thread for each condition variable. Solve some synchronization problems using implicit monitors and compare the performance with standard monitors with explicit signals. Add supplementary material to topics in Chapter 3 or develop a library to test correctness for sample synchronization problems on Canvas. A related work is available here.
