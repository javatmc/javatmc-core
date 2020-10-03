package org.jtmc.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * EnumParametersTest
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class EnumParametersTest {

    private static Object OBJ = new Object();

    private Prototype proto;

    @Before
    public void createObject() {
        proto = new Prototype();
    }

    @Test
    public void testEmptyObject() {
        assertEquals(false, proto.has(Parameters.ALPHA));
        assertEquals(false, proto.has(Parameters.BETA));
        assertEquals(false, proto.has(Parameters.GAMMA));
    }

    @Test
    public void testPut() {
        assertEquals(null, proto.put(Parameters.ALPHA, OBJ));
        assertEquals(OBJ, proto.put(Parameters.ALPHA, OBJ));
        assertEquals(true, proto.has(Parameters.ALPHA));
    }

    @Test
    public void testRemove() {
        assertEquals(null, proto.remove(Parameters.ALPHA));
        proto.put(Parameters.ALPHA, OBJ);
        assertEquals(OBJ, proto.remove(Parameters.ALPHA));
        assertEquals(false, proto.has(Parameters.ALPHA));
    }

    @Test
    public void testGet() {
        proto.put(Parameters.ALPHA, 1.0f);
        assertEquals(1.0f, proto.getFloat(Parameters.ALPHA), 0.1f);

        proto.put(Parameters.ALPHA, 1);
        assertEquals(1, proto.getInt(Parameters.ALPHA));

        proto.put(Parameters.ALPHA, true);
        assertEquals(true, proto.getBoolean(Parameters.ALPHA));
        assertEquals(true, proto.<Boolean>get(Parameters.ALPHA).isPresent());
        assertEquals(true, proto.<Boolean>get(Parameters.ALPHA).get());
    }

    private static class Prototype extends EnumParameters<Parameters> {
        
        @Override
        public Object put(Parameters param, Object value) {
            return super.put(param, value);
        }

        @Override
        public Object remove(Parameters param) {
            return super.remove(param);
        }
    }

    private static enum Parameters {
        ALPHA, BETA, GAMMA
    }
    
}