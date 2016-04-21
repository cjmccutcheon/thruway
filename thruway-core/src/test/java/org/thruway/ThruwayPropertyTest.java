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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ThruwayPropertyTest {

    @Test (expected = IllegalArgumentException.class)
    public void testNullOriginKey() {
        ThruwayProperty tp = new ThruwayProperty(null, "foo");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullUserKey() {
        ThruwayProperty tp = new ThruwayProperty("foo", null);
    }
    
    @Test
    public void testNoUserKey() {
        ThruwayProperty tp = new ThruwayProperty("foo");
        assertEquals("Origin Key identity", "foo", tp.getOriginKey());
        assertEquals("User Key identity", "foo", tp.getUserKey());
    }
    
    @Test
    public void testValueIdentity() {
        ThruwayProperty tp = new ThruwayProperty("foo");
        tp.setValue("bar");
        assertEquals("Property value identity", "bar", tp.getValue());
    }
    
    
    @Test (expected = IllegalStateException.class)
    public void testCannotResetValue() {
        ThruwayProperty tp = new ThruwayProperty("foo");
        tp.setValue("bar");
        tp.setValue("baz");
    }
    
    @Test
    public void testCannotInitialValueIsNull() {
        ThruwayProperty tp = new ThruwayProperty("foo");
        assertNull("Initial property value is null", tp.getValue());
    }
    
    @Test
    public void testHashString() {
        ThruwayProperty tp1 = new ThruwayProperty("foo");
        ThruwayProperty tp2 = new ThruwayProperty("foo", "bar");
        ThruwayProperty tp3 = new ThruwayProperty("foo", "bar");
        tp3.setValue("baz");
        String hash1 = tp1.hashString();
        String hash2 = tp2.hashString();
        String hash3 = tp3.hashString();
        assertNotNull("Hash is not null: 1", hash1);
        assertNotNull("Hash is not null: 2", hash2);
        assertNotNull("Hash is not null: 3", hash3);
        assertFalse("Hashes are all different: 1, 2", hash1.equals(hash2));
        assertFalse("Hashes are all different: 2, 3", hash2.equals(hash3));
        assertFalse("Hashes are all different: 1, 3", hash1.equals(hash3));
    }
    
    @Test
    public void testHashStringChangesWithValue() {
        ThruwayProperty tp1 = new ThruwayProperty("foo");
        String hash1 = tp1.hashString();
        tp1.setValue("baz");
        String hash2 = tp1.hashString();
        assertNotNull("Hash is not null: 1", hash1);
        assertNotNull("Hash is not null: 2", hash2);
        assertFalse("Hashes are different", hash1.equals(hash2));
    }
    
    @Test
    public void testHashStringConsistency() {
        ThruwayProperty tp1a = new ThruwayProperty("foo");
        ThruwayProperty tp1b = new ThruwayProperty("foo");
        ThruwayProperty tp2a = new ThruwayProperty("foo", "bar");
        ThruwayProperty tp2b = new ThruwayProperty("foo", "bar");
        ThruwayProperty tp3a = new ThruwayProperty("foo", "bar");
        ThruwayProperty tp3b = new ThruwayProperty("foo", "bar");
        tp3a.setValue("baz");
        tp3b.setValue("baz");
        String hash1a = tp1a.hashString();
        String hash2a = tp2a.hashString();
        String hash3a = tp3a.hashString();
        String hash1b = tp1b.hashString();
        String hash2b = tp2b.hashString();
        String hash3b = tp3b.hashString();
        assertTrue("Hashes are consistent: 1", hash1a.equals(hash1b));
        assertTrue("Hashes are consistent: 2", hash2a.equals(hash2b));
        assertTrue("Hashes are consistent: 3", hash3a.equals(hash3b));
    }

}
