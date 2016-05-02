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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;

public class BulkBlockingServiceTest 
{
    
    /** debug util function to help keep track of thread behavior */
    static void stdout(String str)
    {
        System.out.println("[" + Thread.currentThread().getName() + "] " + str);
    }

    static class ClientThread extends Thread 
    {
        
        long millisFinished = -1;
        
        BulkBlockingService service;
        
        boolean didTheWork = false;
        
        ClientThread(BulkBlockingService service)
        {
            this.service = service;
        }
        
        public void run() 
        {
            try 
            {
                service.submitForWork(new Object());
            } 
            catch (InterruptedException e) 
            {
            }
            millisFinished = System.currentTimeMillis();
        }
    
    }
    
    @Test
    public void testSimplestBehavior() 
    {
        final int delay = 500;
        BulkBlockingService<Object> service = new BulkBlockingService<Object>()
        {
            protected void processWork(ArrayList<Object> argumentsList) 
            {
                try { Thread.sleep(delay); } catch (InterruptedException e) {}
            }
        };
        
        ClientThread run1 = new ClientThread(service); 
        run1.start();
        try 
        {
            run1.join();
        }
        catch (InterruptedException e)
        {
        }
    }

    @Test
    public void testBlocking() 
    {
        final int delay = 500;
        BulkBlockingService<Object> service = new BulkBlockingService<Object>()
        {
            protected void processWork(ArrayList<Object> argumentsList) 
            {
                try { Thread.sleep(delay); } catch (InterruptedException e) {}
            }
        };
        
        ClientThread run1 = new ClientThread(service); 
        ClientThread run2 = new ClientThread(service); 
        ClientThread run3 = new ClientThread(service); 
        ClientThread run4 = new ClientThread(service); 
        run1.start(); // Thread gets its own WorkPackage
        run2.start(); // Thread queues in second WorkPackage
        run3.start(); // Thread queues in second WorkPackage
        run4.start(); // Thread queues in second WorkPackage
        try 
        {
            run1.join();
            run2.join();
            run3.join();
            run4.join();
        }
        catch (InterruptedException e)
        {
        }
        
        assertTrue("First package thread done earlier", 
            run1.millisFinished + (delay - 20) <= run2.millisFinished);
        assertTrue("Second package threads released close to each other: 2,3",
            Math.abs(run2.millisFinished - run3.millisFinished) < 20);
        assertTrue("Second package threads released close to each other: 2,4",
            Math.abs(run2.millisFinished - run4.millisFinished) < 20);
        assertTrue("Second package threads released close to each other: 3,4",
            Math.abs(run3.millisFinished - run4.millisFinished) < 20);
    }
    
    @Test
    public void testContinuingFunctionality() 
    {
        final int delay = 500;
        BulkBlockingService<Object> service = new BulkBlockingService<Object>()
        {
            protected void processWork(ArrayList<Object> argumentsList) 
            {
                try { Thread.sleep(delay); } catch (InterruptedException e)  {}
            }
        };
        
        ClientThread run1 = new ClientThread(service); 
        ClientThread run2 = new ClientThread(service); 
        ClientThread run3 = new ClientThread(service); 
        ClientThread run4 = new ClientThread(service); 
        ClientThread run5 = new ClientThread(service); 
        // First round
        run1.start(); // Thread gets its own WorkPackage
        try { Thread.sleep(delay / 2); } catch (InterruptedException e) {}
        // Second round
        run2.start(); // Thread queues in second WorkPackage
        run3.start(); // Thread queues in second WorkPackage
        try { Thread.sleep(delay); } catch (InterruptedException e) {}
        // Third round
        run4.start(); // Thread queues in third WorkPackage
        run5.start(); // Thread queues in third WorkPackage
        try 
        {
            run1.join();
            run2.join();
            run3.join();
            run4.join();
            run5.join();
        }
        catch (InterruptedException e)
        {
        }
        
        assertTrue("First package thread done earliest", 
            run1.millisFinished + delay <= run2.millisFinished);
        assertTrue("Second package threads done earlier", 
            run2.millisFinished + delay <= run4.millisFinished);
    }
    
    @Test
    public void testSubmittingThreadsAreWorkerThreads() 
    {
        BulkBlockingService<Object> service = new BulkBlockingService<Object>()
        {
            protected void processWork(ArrayList<Object> argumentsList) 
            {
                ClientThread ct = (ClientThread) Thread.currentThread();
                ct.didTheWork = true;
            }
        };
        
        ClientThread run1 = new ClientThread(service); 
        ClientThread run2 = new ClientThread(service); 
        ClientThread run3 = new ClientThread(service); 
        ClientThread run4 = new ClientThread(service); 
        // First round
        run1.start(); // Thread gets its own WorkPackage
        // Second round
        run2.start(); // Thread queues in second WorkPackage
        run3.start(); // Thread queues in second WorkPackage
        run4.start(); // Thread queues in second WorkPackage
        try 
        {
            run1.join();
            run2.join();
            run3.join();
            run4.join();
        }
        catch (InterruptedException e)
        {
        }
        
        assertTrue("First package thread worker check", run1.didTheWork);
        assertTrue("Second package threads worker check", 
            run2.didTheWork || run3.didTheWork || run4.didTheWork);
    }

}

