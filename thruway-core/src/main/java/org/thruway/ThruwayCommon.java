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
