

How can I restart a thread that has stopped execution? 
----------------------------------------------------------------

You can't. 

It depends what you mean by "stopped". Once the 
thread has reached the end of its run method or is explicitely 
stopped (don't!) using the deprecated stop method, it cannot be 
restarted. You will have to create a new Thread instance on that 
object and call start again.
 

