/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.l3vpnservice.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.l3vpnservice.service.util.L3VpnServiceUtils;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

public class L3VpnServiceUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetTpFromVpn() throws IOException {
        final String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        Assert.assertNotNull(vpn);
        Tp tp = L3VpnServiceUtils.getTpFromVpn(vpn, "ff9b2bbb-0db1-414b-83db-cde41990e2b9");

        Assert.assertNotNull(tp);
        tp = L3VpnServiceUtils.getTpFromVpn(vpn, "notexist");
        Assert.assertNull(tp);
        vpn.setAccessPointList(Collections.EMPTY_LIST);
        tp = L3VpnServiceUtils.getTpFromVpn(vpn, "notexist");
        Assert.assertEquals(null, tp);
    }

    @Test
    public void testGetRouteFromTp() throws IOException {
        String filePath = new File("src/test/resources/tp.json").getCanonicalPath();
        Tp tp = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Tp.class);
        Assert.assertNotNull(tp);
        RouteProtocolSpec routeProtocolSpec = L3VpnServiceUtils.getRouteFromTp(tp, "null");
        Assert.assertEquals(null, routeProtocolSpec);

        List<RouteProtocolSpec> routeProtocolSpecs = new ArrayList<RouteProtocolSpec>();
        filePath = new File("src/test/resources/route.json").getCanonicalPath();
        RouteProtocolSpec route = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), RouteProtocolSpec.class);
        routeProtocolSpecs.add(route);
        tp.setRouteProtocolSpecs(routeProtocolSpecs);
        routeProtocolSpec = L3VpnServiceUtils.getRouteFromTp(tp, "null");
        Assert.assertEquals(null, routeProtocolSpec);

        routeProtocolSpecs.remove(0);
        route.setUuid("3a9efe5c-d6c4-426d-ac8e-141ebfed934d");
        routeProtocolSpecs.add(route);
        tp.setRouteProtocolSpecs(routeProtocolSpecs);
        routeProtocolSpec = L3VpnServiceUtils.getRouteFromTp(tp, "3a9efe5c-d6c4-426d-ac8e-141ebfed934d");
        Assert.assertNotNull(routeProtocolSpec);
    }

}
