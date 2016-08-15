
package org.openo.sdno.model.servicemodel.brs;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class NetworkElementMOTest {

    @Test
    public void testNetworkElementMONull() {
        NetworkElementMO demo = new NetworkElementMO(null);
        assertNull(demo.getId());
    }

    @Test
    public void testNetworkElementMONormal() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> ctrlid = new ArrayList<String>();
        List<String> nedid = new ArrayList<String>();
        ctrlid.add("value");
        nedid.add("value");
        map.put("id", "value");
        map.put("name", "value");
        map.put("description", "value");
        map.put("ipaddress", "value");
        map.put("manufacturer", "value");
        map.put("controllerID", ctrlid);
        map.put("networkControlDomainID", nedid);
        map.put("adminState", "value");
        map.put("operState", "value");
        NetworkElementMO demo = new NetworkElementMO(map);
        assertTrue(demo.getId().equals("value"));
    }

}
