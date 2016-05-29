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

/**
 * Interface for listening to the NodeDispatcher
 */
public interface NodeDispatcherListener {  
    
    /**
     * Fires when a Node Dispatcher has registered
     * a new Pattern.
     */
    public void dispatcherAddedPattern(Pattern added);
    
    /**
     * Fires when a Node Dispatcher has removed
     * a registered Pattern.
     */
    public void dispatcherRemovedPattern(Pattern removed);
    
    /**
     * Fires when a Node Dispatcher is about to start
     * an evaluation run over all Nodes
     */
    public void dispatcherStartingEvaluateOfAllNodes();
    
    /**
     * Fires when a Node Dispatcher has finished
     * an evaluation run over all Nodes
     */
    public void dispatcherFinishedEvaluateOfAllNodes();
    
    /**
     * Fires when a Node Dispatcher is about to start an
     * evaluation.
     */
    public void nodeStartingEvaluation(Node evaluating);
    
    /**
     * Fires when a Node Dispatcher has completed evaluation,
     * regardless of outcome.
     */
    public void nodeFinishedEvaluation(Node evaluated, 
                                       NodeEvaluation evaluation);
                                       
    /**
     * Fires when a Node Dispatcher has been told to stop execution
     * on a Node.  This method does not guarantee that the 
     * cancellation succeeded, but does ensure that if processing
     * is not stopped, then the ExecutionToken produced will be
     * suppressed.
     */
    public void nodeEvaluationExternalCancelRequest(
                                       Node evaluated, 
                                       NodeEvaluation evaluation);
    
}
