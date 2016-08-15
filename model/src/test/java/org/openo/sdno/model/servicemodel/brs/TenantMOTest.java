
package org.openo.sdno.model.servicemodel.brs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TenantMOTest {

    @Test
    public void testEqualsObjectNull() {
        TenantMO a = new TenantMO();
        assertFalse(a.equals(null));
    }

    @Test
    public void testEqualsObjectSame() {
        TenantMO a = new TenantMO();
        TenantMO b = a;
        assertTrue(a.equals(b));
    }

    @Test
    public void testEqualsObjectDifClass() {
        TenantMO a = new TenantMO();
        SiteMOTest b = new SiteMOTest();
        assertTrue(!a.equals(b));
    }

    @Test
    public void testEqualsObjectNullId() {
        TenantMO a = new TenantMO();
        TenantMO b = new TenantMO();
        b.setId("b1");
        assertTrue(!a.equals(b));
    }

    @Test
    public void testEqualsObjectDifId() {
        TenantMO a = new TenantMO();
        a.setId("a1");
        TenantMO b = new TenantMO();
        b.setId("b1");
        assertTrue(!a.equals(b));
    }

    @Test
    public void testEqualsObjectSameId() {
        TenantMO a = new TenantMO();
        a.setId("a1");
        TenantMO b = new TenantMO();
        b.setId("a1");
        assertTrue(a.equals(b));
    }

}
