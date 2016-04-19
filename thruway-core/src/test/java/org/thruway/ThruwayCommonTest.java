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
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ThruwayCommonTest {

    @Test
    public void testHashValue_Basic() {
        String hash = ThruwayCommon.hashValue("hi");
        assertNotNull(hash);
        assertTrue("Hash results are always >= 16 chars", hash.length() >= 16);
        assertTrue("Hashes are always hexadecimal", hash.matches("^[0-9a-f]+$"));
    }

    @Test
    public void testHashValue_EmptyString() {
        String hash = ThruwayCommon.hashValue("");
        assertNotNull("Extant arguments get extant results", hash);
        assertTrue("Hash results are always >= 16 chars", hash.length() >= 16);
        assertTrue("Hashes are always hexadecimal", hash.matches("^[0-9a-f]+$"));
    }

    @Test (expected = NullPointerException.class)
    public void testHashValue_NullArgument() {
        ThruwayCommon.hashValue((Object) null);
    }

    @Test
    public void testHashValue_ArrayAndCollectionEquality() {
        String hash1 = ThruwayCommon.hashValue("a", "b");
        String hash2 = ThruwayCommon.hashValue(new String [] {"a", "b"} );
        assertNotNull("Extant arguments get extant results", hash1);
        assertNotNull("Extant arguments get extant results", hash2);
        assertTrue("Collection method and Array method" +
                   " give same result for same tuples", hash1.equals(hash2));
    }

    @Test
    public void testHashValue_FieldDivision() {
        // Goal: if hashing (for example) two strings, we can't just
        //       paste the fields together, then the hash for ("ab","c") 
        //       will equal the hash for ("a","bc")
        String hash1 = ThruwayCommon.hashValue("ab", "c");
        String hash2 = ThruwayCommon.hashValue("a", "bc");
        assertNotNull("Extant arguments get extant results", hash1);
        assertNotNull("Extant arguments get extant results", hash2);
        assertFalse("hash(ab,c) != hash(a,bc)", hash1.equals(hash2));
    }

}

