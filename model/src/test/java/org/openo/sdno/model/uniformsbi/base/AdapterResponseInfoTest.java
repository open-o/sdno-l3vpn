
package org.openo.sdno.model.uniformsbi.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AdapterResponseInfoTest {

    @Test
    public void testControllerSuccess() {
        AdapterResponseInfo adapterResponseInfo = new AdapterResponseInfo();
        adapterResponseInfo.setRet(200);
        assertTrue(adapterResponseInfo.isControllerSuccess());
    }

    @Test
    public void testControllerFalse() {
        AdapterResponseInfo adapterResponseInfo = new AdapterResponseInfo();
        adapterResponseInfo.setRet(500);
        assertFalse(adapterResponseInfo.isControllerSuccess());
    }

    @Test
    public void testControllerFound() {
        AdapterResponseInfo adapterResponseInfo = new AdapterResponseInfo();
        adapterResponseInfo.setRet(200);
        assertFalse(adapterResponseInfo.isController404());
    }

    @Test
    public void testControllerNotFound() {
        AdapterResponseInfo adapterResponseInfo = new AdapterResponseInfo();
        adapterResponseInfo.setRet(404);
        assertTrue(adapterResponseInfo.isController404());
    }

}
