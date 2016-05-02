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
import org.thruway.ThruwayProperty;
import org.thruway.ThruwayPropertyType;

/**
 * Standard implentation of {@link com.thruway.ThruwayProperty}, v1.0
 * Also contains instances of itself for common types.
 */
public class ThruwayPropertyImpl implements ThruwayProperty
{
    
    /**
     * Type applicable to this Property.  If null, then this is unbounded,
     * therefore all non-null Strings are valid values.
     */
    final private ThruwayPropertyType type;

    /** Key for internal-use only */
    final private String originKey;

    /** Key for inter-node use. Can equal originKey */
    final private String userKey;

    public ThruwayPropertyImpl(ThruwayPropertyType type, String originKey, String userKey)
    {
        this.type = type;
        
        if (originKey == null)
        {
            throw new IllegalArgumentException("originKey cannot be null");
        }
        
        if (!originKey.matches(ThruwayProperty.ALLOWED_KEY_REGEX))
        {
            throw new IllegalArgumentException("originKey does not match necessary " +
                "regex: " + ThruwayProperty.ALLOWED_KEY_REGEX);
        }
        this.originKey = originKey;

        if (originKey != userKey)
        {
            if (userKey == null) 
            {
                throw new IllegalArgumentException("userKey cannot be null");
            }
            if (!userKey.matches(ThruwayProperty.ALLOWED_KEY_REGEX))
            {
                throw new RuntimeException("userKey does not match necessary " +
                    "regex: " + ThruwayProperty.ALLOWED_KEY_REGEX);
            }
        }
        this.userKey = userKey;
    }

    public ThruwayPropertyImpl(ThruwayPropertyType type, String originKey)
    {
        this(type, originKey, originKey);
    }

    /** @inheritDoc */
    public String getOriginKey()
    {
        return this.originKey;
    }

    /** @inheritDoc */
    public String getUserKey()
    {
        return this.userKey;
    }

    /** @inheritDoc */
    public String hashString()
    {
        // Both originKey and userKey have to be included. Always.
        return ThruwayCommon.hashValue(new Object [] {originKey, userKey});
    }

    /** @inheritDoc */
    public String toString() 
    {
        if (originKey == userKey)
        {
            return "PropDef(" + originKey + ")";
        }
        return "PropDef(" + originKey + "," + userKey + ")";
    }

}

