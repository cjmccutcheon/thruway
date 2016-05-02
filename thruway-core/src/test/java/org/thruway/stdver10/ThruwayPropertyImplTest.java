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

public class ThruwayPropertyImplTest 
{

    @Test (expected = IllegalArgumentException.class)
    public void testNullOriginKey() 
    {
        ThruwayPropertyImpl tp = new ThruwayPropertyImpl(null, null, "foo");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullUserKey() 
    {
        ThruwayPropertyImpl tp = new ThruwayPropertyImpl(null, "foo", null);
    }
    
    @Test
    public void testNoUserKey() 
    {
        ThruwayPropertyImpl tp = new ThruwayPropertyImpl(null, "foo");
        assertEquals("Origin Key identity", "foo", tp.getOriginKey());
        assertEquals("User Key identity", "foo", tp.getUserKey());
    }
    
    @Test
    public void testHashString() 
    {
        ThruwayPropertyImpl tp1 = new ThruwayPropertyImpl(null, "foo");
        ThruwayPropertyImpl tp2 = new ThruwayPropertyImpl(null, "foo", "bar");
        String hash1 = tp1.hashString();
        String hash2 = tp2.hashString();
        assertNotNull("Hash is not null: 1", hash1);
        assertNotNull("Hash is not null: 2", hash2);
        assertFalse("Hashes are different: 1, 2", hash1.equals(hash2));
    }
    
    @Test
    public void testHashStringConsistency() 
    {
        ThruwayPropertyImpl tp1a = new ThruwayPropertyImpl(null, "foo");
        ThruwayPropertyImpl tp1b = new ThruwayPropertyImpl(null, "foo");
        ThruwayPropertyImpl tp2a = new ThruwayPropertyImpl(null, "foo", "bar");
        ThruwayPropertyImpl tp2b = new ThruwayPropertyImpl(null, "foo", "bar");
        String hash1a = tp1a.hashString();
        String hash2a = tp2a.hashString();
        String hash1b = tp1b.hashString();
        String hash2b = tp2b.hashString();
        assertTrue("Hashes are consistent: 1", hash1a.equals(hash1b));
        assertTrue("Hashes are consistent: 2", hash2a.equals(hash2b));
    }

}

