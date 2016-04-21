/*
The MIT License (MIT)

Copyright (c) 2016 Christopher James McCutcheon (github user: cjmccutcheon)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package org.thruway;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Service that allows for multiple threads to submit
 * requests for a single service operation and <i>not</i> continue until the
 * work is completed, either blocking or being the thread to complete the work.
 * In this manner, work is carried out in bulk.
 * 
 * If it seems like this could be used to choke performance, you are very right.  
 * This is used to hold threads while the File Persistence module writes state
 * changes to file, because a write operation is non-trivial and non-atomic. 
 * It is this reason why the File Persistence module is not advisable.
 * 
 */
abstract public class BulkBlockingService<ArgObj> {
    
/*
      |    
      |    
      1    
      |    
      | |
      | | |
      | 2 | |
      | . 3 |
      | . . 4
      | . . .
      | . . .
      5 . . .
      | 6 . .
      | | . .
      | | . .
      | 7----
      | | | |
    
    1.) First thread reaches unloaded service, submits
        its arguments, and does the service work
    2.) Second thread reaches loaded service, submits its arguments, and is
        blocked
    3.) Third thread reaches loaded service, submits its arguments, and is
        blocked
    4.) Fourth thread reaches loaded service, submits its arguments, and is
        blocked
    5.) First thread finishes, unblocks one thread.
    6.) Second thread unblocked, starts doing work with arguments for threads
        2, 3, and 4
    7.) Second thread finishes work, unblocks remaining blocked
    
    
*/   

    private class WorkPackage {
        Semaphore threads = new Semaphore(0);
        ArrayList<ArgObj> listOfArguments = new ArrayList<ArgObj>();
        volatile boolean workCompleted = false; // volatile just to be safe
        
        WorkPackage(ArgObj argument)
        {
            listOfArguments.add(argument);
        }
        
        void block() throws InterruptedException
        {
            threads.acquire();
        }
        
        void unblockThread()
        {
            threads.release();
        }
        
        void workCompleted()
        {
            this.workCompleted = true;
            while (threads.hasQueuedThreads())
            {
                unblockThread();
            }
        }
    }
    
    Object lock = new Object();
    
    
    ArrayDeque<WorkPackage> waitingWork = new ArrayDeque<WorkPackage>();
    
    /**
     * Submit the <code>ArgObj</code> for work, and this thread will do one 
     * of these three things: <ul>
     * <li>Start doing the work immediately</li>
     * <li>Block until the previous work is done, then start doing the work</li>
     * <li>Block until the previous and the requested work is done, then 
     *     continue</li>
     * 
     * @param argument The argument necessary for the work task.  Can be
     *     <code>null</code> if that's appropriate for the implementing subclass
     */
    public void submitForWork(ArgObj argument) throws InterruptedException
    {
        int oldSize = 0;
        WorkPackage pkg = null;
        synchronized(waitingWork)
        {
            oldSize = waitingWork.size();
            if (oldSize == 2) 
            {
                // Add to the pending work at the end
                pkg =  waitingWork.peekLast();
                pkg.listOfArguments.add(argument);
            }
            else
            {
                // if size == 0, then nothing is going on
                // if size == 1, then there is a WorkPackage undergoing work
                //   and we need a new WP to wait for the next turn
                // In either case: need a new WorkPackage.
                pkg = new WorkPackage(argument);
                waitingWork.add(pkg);
            }
        }
        if (oldSize > 0) 
        {
            // oldSize==0 --> work is being done, this thread has to wait
            pkg.block(); // block *after* letting go of the waitingWork lock
        }
        // Check if the package is completed when we came out of the block
        // If so, then another thread did the work, and we can just leave.
        if (pkg.workCompleted)
        {
            return;
        }
        processNextWorkPackage();
    }
    
    private void processNextWorkPackage()
    {
        WorkPackage pkg = null;
        synchronized(waitingWork)
        {
            if (waitingWork.size() == 0)
            {
                return;  // Do not think this is ever possible...
            }
            pkg = waitingWork.peek();
        }
        
        // Do the work
        processWork(pkg.listOfArguments);
        
        // Work is done, let go of all the threads in the WorkPackage
        pkg.workCompleted();
        
        synchronized(waitingWork)
        {
            // WorkPackage is finished, we can take it off of the queue now.
            // This allows other threads to know that either:
            //   a.) New size == 0 --> new threads can start right away
            //       (after we give up this lock)
            //   b.) New size == 1 --> the next WorkPackage is getting ready
            //       to run and they need to create a new WorkPackage (after we
            //       give up this lock)
            waitingWork.pop();
            
            // We have finished the work and taken it off
            // the deque.  Now, if there's another in line
            // we need to let one of the WorkPackage's blocked threads
            // free so that it can call this method
            
            if (waitingWork.size() > 0)
            {
                pkg = waitingWork.peek();
                pkg.unblockThread();
            }
        }
    }
    
    abstract protected void processWork(ArrayList<ArgObj> argumentsList);

}

