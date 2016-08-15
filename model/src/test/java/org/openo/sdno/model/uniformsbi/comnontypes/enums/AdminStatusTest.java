
package org.openo.sdno.model.uniformsbi.comnontypes.enums;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AdminStatusTest {

    @Test
    public void testAdminDown() {
        AdminStatus demo = AdminStatus.ADMIN_DOWN;
        assertTrue(demo.getIntValueByName("adminDown") == 1);

    }

    @Test
    public void testAdminUp() {
        AdminStatus demo = AdminStatus.ADMIN_UP;
        assertTrue(demo.getIntValueByName("adminUp") == 0);

    }

    @Test
    public void testError() {
        AdminStatus demo = AdminStatus.ADMIN_UP;
        assertTrue(demo.getIntValueByName("name") == -1);

    }

}
