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
 * Interface used to restrict potential values for a {@link ThruwayProperty}.
 */
public interface ThruwayPropertyType {
    
    /**
     * @return a string acceptable for display
     *     which might tell a user what would
     *     be appropriate as a value
     */
    public String description();
    
    /**
     * If this type allows/requires it, list
     * all the values that are correct for this
     * type.
     * 
     * @return all values that could be validated
     *     by this class, or if not available or
     *     possible, return <code>null</code>
     */
    public String [] enumerateAllValidValues();
    
    /**
     * Method that allows for the possibilty
     * that dumb mistakes can be removed from
     * a value before validating. Say, if
     * this is a boolean type and 
     * <code>" true"</code> is submitted to 
     * this method, we can remove the leading
     * space. If this method returns a 
     * different value, then it must become
     * the value up for application (assuming
     * it validates, of course).
     * 
     * @param value A value that is obstensibly
     *     being offered as a potential value
     *     for a {@link ThruwayPropertyValue}
     *     that is associated with this Type.
     * @return An enriched value that has obvious
     *     syntactical issues removed.
     */
    public String improvePrevalidatedValue(String value);
    
    /**
     * Method for checking if a value adheres to the Type.
     * 
     * @param value A value that is obstensibly being offered as a potential 
     *     value for a {@link ThruwayPropertyValue} that is associated with this
     *     Type. Never <code>null</code>.
     * @return An error string if the given value
     *     does not adhere to the Type
     */
    public String validate(String value);
    
}

