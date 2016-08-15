
package org.openo.sdno.model.uniformsbi.comnontypes.enums;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OperStatusTest {

    @Test
    public void testGetIntValueByNameDown() {
        OperStatus demo = OperStatus.OPERATE_DOWN;
        assertTrue(demo.getIntValueByName("operateDown") == 1);
    }

    @Test
    public void testGetIntValueByNameUp() {
        OperStatus demo = OperStatus.OPERATE_UP;
        assertTrue(demo.getIntValueByName("operateUp") == 0);
    }

    @Test
    public void testGetIntValueByNameFail() {
        OperStatus demo = OperStatus.OPERATE_UP;
        assertTrue(demo.getIntValueByName("name") == -1);
    }

}
