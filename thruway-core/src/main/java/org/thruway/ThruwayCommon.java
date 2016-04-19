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

import java.util.Collection;

public class ThruwayCommon {

    static private String hashValueImpl(StringBuilder sb) 
    {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(sb.toString());
    }

    static public String hashValue(Collection<String> strings) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings)
        {
            if (s == null)
            {
                builder.append('\u0001');
            }
            else
            {
                builder.append(s);
            }
            builder.append('\u0000');
        }
        return hashValueImpl(builder);
    }

    static public String hashValue(String ... strings) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings)
        {
            if (s == null)
            {
                builder.append('\u0001');
            }
            else
            {
                builder.append(s);
            }
            builder.append('\u0000');
        }
        return hashValueImpl(builder);
    }

    static public String hashValue(String s) {
        org.apache.commons.codec.digest.DigestUtils.md5Hex(s);
        StringBuilder builder = new StringBuilder();
        builder.append(s);
        builder.append('\u0000');
        return hashValueImpl(builder);
    }

    static public String hashValue(Object o) {
        return hashValue(o.toString());
    }

}
