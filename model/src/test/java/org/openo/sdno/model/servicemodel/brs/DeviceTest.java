
package org.openo.sdno.model.servicemodel.brs;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.ssl.EncryptionUtil;

import mockit.Mock;
import mockit.MockUp;

public class DeviceTest {

    @Test
    public void testGetAuthInfoCommParamsNull() {
        Device device = new Device();
        AuthInfo authInfoResult = device.getAuthInfo();
        assertEquals(authInfoResult.getPassword(), null);
        assertEquals(authInfoResult.getUserName(), null);
    }

    @Test
    public void testGetAuthInfoCommParamsEmpty() {
        Device device = new Device();
        device.setCommParams("");
        AuthInfo authInfoResult = device.getAuthInfo();
        assertEquals(authInfoResult.getPassword(), null);
        assertEquals(authInfoResult.getUserName(), null);
    }

    @Test
    public void testGetAuthInfoCommParamsNormal() {
        new MockUp<EncryptionUtil>() {

            @Mock
            public char[] decode(char[] plain) {
                Map<String, String> info = new HashMap<String, String>();
                info.put("userName", "user");
                info.put("password", "value");
                return JsonUtil.toJson(info).toCharArray();
            }

        };
        Device device = new Device();
        device.setCommParams("test");
        AuthInfo authInfoResult = device.getAuthInfo();
        assertEquals(authInfoResult.getPassword(), "value");
    }

    @Test
    public void testGetAuthInfoCommParamsNull1() {
        new MockUp<EncryptionUtil>() {

            @Mock
            public char[] decode(char[] plain) {
                return null;
            }

        };
        Device device = new Device();
        device.setCommParams("test");
        AuthInfo authInfoResult = device.getAuthInfo();
        assertEquals(authInfoResult.getPassword(), null);
        assertEquals(authInfoResult.getUserName(), null);
    }

}
