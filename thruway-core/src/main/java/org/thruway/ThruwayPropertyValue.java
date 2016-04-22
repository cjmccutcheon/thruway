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
public class ThruwayPropertyValue {
    
    ThruwayProperty definition;
    
    String value = null;

    ThruwayPropertyValue(ThruwayProperty definition)
    {
        if (definition == null) 
        {
            throw new IllegalArgumentException("definition cannot be null");
        }
        this.definition = definition;
    }

    void setValue(String newValue)
    {
        if (this.value != null)
        {
            throw new IllegalStateException("Value already set");
        }
        this.value = newValue;
    }

    public String hashString()
    {
        // All 3 have to be included.  originKey, userKey, and value
        return ThruwayCommon.hashValue(definition, value);
    }

    String getValue()
    {
        return this.value;
    }

    public String toString() 
    {
        return "PropVal(" + definition + ", " + value + ")";
    }
}

