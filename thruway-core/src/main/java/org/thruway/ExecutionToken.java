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

import java.util.HashMap;

/**
 * The medium in which Nodes push their work forward to 
 * downstream Nodes.
 */
public class ExecutionToken 
{  
    /**
     * The node that created this ExecutionToken
     */
    private final Node issuedBy;
    
    /**
     * The state of the execution going forward.
     */
    private final ExecutionState executionState;
            
    /**
     * The state of ThruwayProperties as set
     * by <code>issuedBy</code> and all of its
     * upstream Nodes.
     */
    private HashMap<String, ThruwayPropertyValue> propertiesByUserKey =
        new HashMap<String, ThruwayPropertyValue>();
    
    public ExecutionToken(Node issuedBy, ExecutionState executionState)
    {
        this.issuedBy = issuedBy;
        this.executionState = executionState;
    }
    
    /**
     *  @return The node that created this ExecutionToken
     */
    public Node getIssuedBy() 
    {
        return issuedBy;        
    }
    
    /**
     *  @return The state of the execution going forward.
     */
    public ExecutionState getExecutionState()
    {
        return executionState;
    }
    
    public void putThruwayPropertyValue(ThruwayPropertyValue value)
    {
        if (propertiesByUserKey.containsKey(value.getThruwayProperty()))
        {
            throw new ThruwaySystemException(
                "ThruwayPropertyValue for userKey already set: " + 
                value.getThruwayProperty().getUserKey());
        }
        propertiesByUserKey.put(value.getThruwayProperty().getUserKey(), value);
    }
    
    public ThruwayPropertyValue getThruwayPropertyValue(String userKey)
    {
        if (!propertiesByUserKey.containsKey(userKey))
        {
            throw new ThruwayUserException(
                "ThruwayPropertyValue for userKey not set: " + userKey,
                "It looks like you expected your upstream Nodes to provide " + 
                "user key '" + userKey + "' but it was not made available.  " + 
                "Perhaps place an upstream Ingress to filter for situations " + 
                "where you need this Property to be set, or instead choose a " + 
                "different Property.");
        }
        return propertiesByUserKey.get(userKey);
    }
    
}
