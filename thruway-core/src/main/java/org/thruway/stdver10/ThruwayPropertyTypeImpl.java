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

import org.thruway.ThruwayPropertyValue;

/**
 * Standard implentation of {@link com.thruway.ThruwayPropertyType}, v1.0
 * Also contains instances of itself for common types.
 */
abstract public class ThruwayPropertyTypeImpl {
    
    /** @inheritDoc */
    abstract public String description();
    
    /** @inheritDoc */
    public String [] enumerateAllValidValues() {
        return null;
    }
    
    /** @inheritDoc */
    public String improvePrevalidatedValue(String value)
    {
        return value;
    }
    
    /** @inheritDoc */
    abstract public String validate(String value);
    
    /**
     * ThruwayPropertyType instance accepting values of "true" and "false".
     */
    static public ThruwayPropertyTypeImpl BOOLEAN = 
        new ThruwayPropertyTypeImpl() 
    {
        final private String [] VALUES = new String [] {"true", "false"};
        
        /** @inheritDoc */
        @Override
        public String description() 
        {
            return "'true' or 'false'";
        }
        
        /**
         * @return <code>true</code> and <code>false</code>
         */
        @Override
        public String [] enumerateAllValidValues() 
        {
            return VALUES;
        }
        
        /** 
         * Forces lowercase and trims all characters not
         * matching <code>[truefals]</code>
         */
        @Override
        public String improvePrevalidatedValue(String value)
        {
            return value.toLowerCase().replaceAll("[^truefals]", "");
        }
        
        /** @inheritDoc */
        @Override
        public String validate(String improvedValue) 
        {
            if ("true".equals(improvedValue) || "false".equals(improvedValue))
            {
                return null;
            }
            return "Value must be 'true' or 'false'";
        }
        
    };
}

