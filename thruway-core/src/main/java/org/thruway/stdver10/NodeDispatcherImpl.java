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

import java.util.ArrayList;
import java.util.HashMap;

import org.thruway.*;
import org.apache.commons.collections4.list.CursorableLinkedList;
import org.apache.commons.collections4.list.CursorableLinkedList.Cursor;

public class NodeDispatcherImpl implements NodeDispatcher
{
    
    /**
     * Pattern registered via {@link #addPattern(Pattern)}.
     */
    private HashMap<String, Pattern> registeredPatternsByHashString =
        new HashMap<String, Pattern>();
    
    /**
     * The list of Nodes watched by the dispatcher, in ascending order
     * of last execution time.  When a Node is executed, the node
     * is popped out of the list and put at the bottom.  This
     * balances the order of execution to ensure balancing.
     */
    // Proper linked list that allows for O(1) deletions without losing the 
    // cursor (like you would with java.util.LinkedList).
    private CursorableLinkedList<Node> nodesOrderedByLastExecuted = 
        new CursorableLinkedList<Node>();
        
    /**
     * List of registered listeners
     */
    private ArrayList<NodeDispatcherListener> listeners = 
        new ArrayList<NodeDispatcherListener>();
        
    /**
     * Bean-friendly constructor.  This is a safe constructor for
     * normal use.
     */
    public NodeDispatcherImpl()
    {
    }
    
    /**
     * @inheritDoc
     */
    public void evaluateAllNodes() 
    {
        Node lastEvalNode = nodesOrderedByLastExecuted.getLast();
        Cursor<Node> cursor = nodesOrderedByLastExecuted.cursor();
        Node evalNode;
        do {
            evalNode = cursor.next();
            NodeEvaluation evalReturn = evalNode.evaluate();
            if (evalReturn.nodeExecutionComplete())
            {
                cursor.remove();
                nodesOrderedByLastExecuted.add(evalNode);
            }
            
        } while (evalNode != lastEvalNode);
        
    }
    
    /**
     * @inheritDoc
     */
    public boolean addPattern(Pattern newPattern)
    {
        String patternHash = newPattern.hashString();
        boolean added = false;
        if (!registeredPatternsByHashString.containsKey(patternHash))
        {
            registeredPatternsByHashString.put(patternHash, newPattern);
            added = true;
        }
        return added;
    }
    
    /**
     * @inheritDoc
     */
    public boolean removePattern(Pattern newPattern)
    {
        String patternHash = newPattern.hashString();
        boolean removed = false;
        if (registeredPatternsByHashString.containsKey(patternHash))
        {
            registeredPatternsByHashString.remove(patternHash);
            removed = true;
        }
        return removed;
    }
}
