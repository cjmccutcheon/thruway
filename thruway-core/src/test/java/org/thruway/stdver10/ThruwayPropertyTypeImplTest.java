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

public class ThruwayPropertyTypeImplTest 
{

    @Test
    public void testBOOLEAN_ValidValues() 
    {
        ThruwayPropertyTypeImpl b = ThruwayPropertyTypeImpl.BOOLEAN;
        assertNull("Valid 1", b.validate("true"));
        assertNull("Valid 2", b.validate("false"));
        assertNotNull("Invalid 1", b.validate("bobby"));
        assertNotNull("Invalid 2", b.validate(" "));
        assertNotNull("Invalid 3", b.validate("tru"));
        assertNotNull("Invalid 4", b.validate("fals"));
    }
    
    @Test
    public void testBOOLEAN_ImproveValues() 
    {
        ThruwayPropertyTypeImpl b = ThruwayPropertyTypeImpl.BOOLEAN;
        assertEquals("Improve 1", 
            "true",
            b.improvePrevalidatedValue(" true "));
        assertEquals("Improve 2", 
            "true",
            b.improvePrevalidatedValue(" TRUE "));
        assertEquals("Improve 3", 
            "false",
            b.improvePrevalidatedValue(" false "));
        assertEquals("Improve 4", 
            "false",
            b.improvePrevalidatedValue(" FALSE "));
    }

}

