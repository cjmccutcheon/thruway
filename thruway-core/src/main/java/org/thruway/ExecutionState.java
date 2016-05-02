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
 * Values describing various states of an execution, as delivered inside
 * an {@link ExecutionToken}.  Accepting a token with a given
 * ExecutionState value means taking responsibilty for handling that value.
 */
public enum ExecutionState {
    
    /**
     * Everything is normal, continue processing without exception.
     */
    CONTINUE,
    
    /**
     * A rational error has occurred within a node.  This token should skip
     * all Ingresses and Transforms and be forwarded to Egresses.
     * If an Egress fails, move to ERROR state.
     */
    NODE_FAILURE,
    
    /**
     * An irrational error has occurred within a node.  This token should skip
     * all INgresses and Transforms and be forwarded to Egresses. 
     * Egresses should be allowed to fail if necessary.
     */
    NODE_ERROR,
    
    /**
     * An error has occurred outside of node processing, indicating system
     * instability.  The token attached to this state should have its issuing
     * Node halted on the system.  Token-combining ingresses must refrain
     * from consuming other non-SYSTEM_ERROR tokens.
     */
    SYSTEM_ERROR,
    
    /**
     * A stop has been called by the user. This token should skip
     * all Ingresses and Transforms and be forwarded to Egresses.
     * If an Egress fails, move to FAILURE state.
     */
    STOP;

}

