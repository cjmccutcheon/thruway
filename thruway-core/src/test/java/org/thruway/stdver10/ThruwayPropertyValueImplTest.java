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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.thruway.*;

public class ThruwayPropertyValueImplTest 
{

    @Test
    public void testValueIdentity() 
    {
        ThruwayPropertyImpl tp = new ThruwayPropertyImpl(null, "foo");
        ThruwayPropertyValueImpl tpv = new ThruwayPropertyValueImpl(tp);
        tpv.setValue("bar");
        assertEquals("Property value identity", "bar", tpv.getValue());
    }
    
    
    @Test (expected = ThruwaySystemException.class)
    public void testCannotResetValue() 
    {
        ThruwayPropertyImpl tp = new ThruwayPropertyImpl(null, "foo");
        ThruwayPropertyValueImpl tpv = new ThruwayPropertyValueImpl(tp);
        tpv.setValue("bar");
        tpv.setValue("baz");
    }
    
    @Test
    public void testCannotInitialValueIsNull() 
    {
        ThruwayPropertyImpl tp = new ThruwayPropertyImpl(null, "foo");
        ThruwayPropertyValueImpl tpv = new ThruwayPropertyValueImpl(tp);
        assertNull("Initial property value is null", tpv.getValue());
    }
    
    @Test
    public void testHashString() 
    {
        ThruwayPropertyImpl tp = new ThruwayPropertyImpl(null, "foo", "bar");
        ThruwayPropertyValueImpl tpv = new ThruwayPropertyValueImpl(tp);
        tpv.setValue("baz");
        String hash = tpv.hashString();
        assertNotNull("Hash is not null", hash);
    }
    
    @Test
    public void testHashStringChangesWithValue() 
    {
        ThruwayPropertyImpl tp = new ThruwayPropertyImpl(null, "foo");
        ThruwayPropertyValueImpl tpv = new ThruwayPropertyValueImpl(tp);
        String hash1 = tpv.hashString();
        tpv.setValue("baz");
        String hash2 = tpv.hashString();
        assertNotNull("Hash is not null: 1", hash1);
        assertNotNull("Hash is not null: 2", hash2);
        assertFalse("Hashes are different", hash1.equals(hash2));
    }
    
    @Test
    public void testHashStringConsistency() 
    {
        ThruwayPropertyImpl tp1 = new ThruwayPropertyImpl(null, "foo", "bar");
        ThruwayPropertyImpl tp2 = new ThruwayPropertyImpl(null, "foo", "bar");
        // Different ThruwayPropertyImpl in 1
        ThruwayPropertyValueImpl tpv1 = new ThruwayPropertyValueImpl(tp1);
        // Same ThruwayPropertyImpl in 2 and 3
        ThruwayPropertyValueImpl tpv2 = new ThruwayPropertyValueImpl(tp2);
        ThruwayPropertyValueImpl tpv3 = new ThruwayPropertyValueImpl(tp2);
        tpv1.setValue("baz");
        tpv2.setValue("baz");
        tpv3.setValue("baz");
        String hash1 = tpv1.hashString();
        String hash2 = tpv2.hashString();
        String hash3 = tpv3.hashString();
        assertTrue("Hashes are consistent: 1,2", hash1.equals(hash2));
        assertTrue("Hashes are consistent: 2,3", hash2.equals(hash3));
    }

}

