
package org.openo.sdno.model.servicemodel.brs;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SiteMOTest {

    @Test
    public void testSiteMONull() {
        Map<String, Object> map = new HashMap<String, Object>();
        map = null;
        SiteMO demo = new SiteMO(map);
        assertNull(demo.getId());
    }

    @Test
    public void testSiteMONormal() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> ctrlid = new ArrayList<String>();
        List<String> nedid = new ArrayList<String>();
        map.put("type", "value");
        map.put("location", "value");
        map.put("description", "value");
        map.put("tenantID", "value");
        SiteMO demo = new SiteMO(map);
        assertTrue(demo.getDescription().equals("value"));
    }

}
