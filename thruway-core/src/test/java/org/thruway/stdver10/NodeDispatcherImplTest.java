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

package org.thruway.stdver10;

import org.thruway.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.ReadableDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class NodeDispatcherImplTest
{
    //static class TestPattern implements Pattern
    //{
    //}
    
    static abstract class TestNode implements Node
    {
        DateTime lastExecuted; 
        
        public ReadableDateTime lastExecuted()
        {
            return lastExecuted;
        }
    
        // public NodeEvaluation evaluate(); // for the tests to modify
    
        public Pattern ownedBy() 
        {
            return null; // not necessary here
        }
    }
    
    /**
     * Tests that if Nodes {@code a} and {@code b} are in the 
     * execution list and are in that order, and {@code a} is 
     * executed and {@code b} is not, then 
     * after that pass, {@code b} comes before {@code a}
     * in the execution list.
     */
    @Test
    public void testExecutedNodesMovedToEndOfList() 
    {
        NodeDispatcherImpl disp = new NodeDispatcherImpl();
        //TestNode node_a = new TestNode();
        //TestNode node_b = new TestNode();
        //TestPattern pattern_a = new TestPattern();
        //TestPattern pattern_b = new TestPattern();
        fail("TODO");
    }
    
}