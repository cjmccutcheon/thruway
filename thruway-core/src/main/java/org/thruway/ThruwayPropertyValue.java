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
 * Class used to specify a property that is filled with a value.
 * The {@link ThruwayProperty} class is used as a static field
 * definition.
 */
public interface ThruwayPropertyValue extends Hashable
{
    
    /**
     * @param newValue
     *     The value to set to this instance
     * @throws ThruwaySystemException 
     *     if the value has already been set
     */
    public void setValue(String newValue);
    
    /**
     * @return
     *     The value set via {@link #setValue()},
     *     or <code>null</code> if no value
     *     has been set
     */
    public String getValue();

    /**
     * @return
     *     A {@link ThruwayProperty} instance
     *     that describes the property structure.
     */
    public ThruwayProperty getThruwayProperty();
}

