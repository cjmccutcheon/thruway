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
 * This class is almost your basic key/value pair, except that there is the
 * ability for the user to choose a "user" key, which can then be used
 * by all downstream nodes, while simultaneously the system has chosen
 * a hardcoded "origin" key, which the node then uses to keep track of which
 * field is which.  This allows the user to choose appropriate names for
 * different outputs and inputs, when, for example, TODO add example.
 *
 */
public class ThruwayProperty implements Hashable
{
    
    /**
     * Regular expression that matches any valid origin or
     * user key.
     *
     */
    // I want to keep this tightly manicured; it is easy to introduce
    // characters that then break parsing elsewhere.  I want characters
    // like '-' available for DSLs, and this should get characters LAST.
    // I do expect non-english characters to be added at some time, but don't
    // know how to do it yet.
    //
    // Definitely disallowed char classes:
    //    [${}]      -- Because of "${foo}" interpolation notation
    static final public String ALLOWED_KEY_REGEX = "[A-Za-z0-9_]*";


    /** Key for internal-use only */
    final private String originKey;

    /** Key for inter-node use. Can equal originKey */
    final private String userKey;

    /** Value of the property.  Once set, cannot be changed. */
    private String value = null;

    ThruwayProperty (String originKey, String userKey)
    {
        if (originKey == null) 
        {
            throw new IllegalArgumentException("originKey cannot be null");
        }
        if (!originKey.matches(ALLOWED_KEY_REGEX))
        {
            throw new RuntimeException("originKey does not match necessary " +
                "regex: " + ALLOWED_KEY_REGEX);
        }
        this.originKey = originKey;

        if (originKey != userKey)
        {
            if (userKey == null) 
            {
                throw new IllegalArgumentException("userKey cannot be null");
            }
            if (!userKey.matches(ALLOWED_KEY_REGEX))
            {
                throw new RuntimeException("userKey does not match necessary " +
                    "regex: " + ALLOWED_KEY_REGEX);
            }
        }
        this.userKey = userKey;
    }

    ThruwayProperty (String originKey)
    {
        this(originKey, originKey);
    }

    String getOriginKey()
    {
        return this.originKey;
    }

    String getUserKey()
    {
        return this.userKey;
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
        return ThruwayCommon.hashValue(originKey, userKey, value);
    }

    String getValue()
    {
        return this.value;
    }

    public String toString() 
    {
        return "Prop(" + originKey + "|" + userKey + "=" + value + ")";
    }

}

