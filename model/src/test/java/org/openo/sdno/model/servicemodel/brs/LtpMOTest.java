
package org.openo.sdno.model.servicemodel.brs;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class LtpMOTest {

    @Test
    public void testLtpMONull() {
        LtpMO demo = new LtpMO(null);
        assertNull(demo.getId());
    }

    @Test
    public void testLtpMONormal() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "value");
        map.put("meid", "value");
        map.put("name", "value");
        map.put("description", "value");
        map.put("ipaddress", "value");
        map.put("ipmask", "value");
        map.put("phyw", "value");
        map.put("direction", "value");
        map.put("logicalType", "value");
        map.put("adminState", "value");
        map.put("operState", "value");
        LtpMO demo = new LtpMO(map);
        assertTrue(demo.getId().equals("value"));
    }

}
