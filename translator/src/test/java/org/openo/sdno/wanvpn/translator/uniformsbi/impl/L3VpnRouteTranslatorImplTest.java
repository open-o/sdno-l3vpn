/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.wanvpn.translator.uniformsbi.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.common.enumeration.RouteProtocolType;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.uniformsbi.l3vpn.Route;
import org.openo.sdno.model.uniformsbi.l3vpn.StaticRoute;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnRouteTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnStaticRouteTranslatorImpl;
import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;

public class L3VpnRouteTranslatorImplTest {

    @Autowired
    private L3VpnRouteTranslatorImpl service = new L3VpnRouteTranslatorImpl();

    @Before
    public void setUp() {
        new MockUp<L3VpnStaticRouteTranslatorImpl>() {

            @Mock
            public StaticRoute translate(TranslatorCtx ctx) throws ServiceException {
                return new StaticRoute();
            }
        };
    }

    @Test
    public void test() throws ServiceException, IOException {
        TranslatorCtx ctx = new TranslatorCtxImpl();
        ctx.setOperType(OperType.CREATE);

        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "routeSpec.json";
        RouteProtocolSpec routeSpec = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), RouteProtocolSpec.class);
        List<RouteProtocolSpec> routeSpecLst = new ArrayList<RouteProtocolSpec>();
        routeSpecLst.add(routeSpec);
        ctx.addVal(VpnContextKeys.PROTOCOL, routeSpecLst);
        service.setL3VpnStaticRouteTranslator(new L3VpnStaticRouteTranslatorImpl());
        Route route = service.translate(ctx);
        Assert.assertNotNull(route);

        routeSpec = new RouteProtocolSpec();
        routeSpec.setType(RouteProtocolType.BGP.getCommonName());
        routeSpecLst = new ArrayList<RouteProtocolSpec>();
        routeSpecLst.add(routeSpec);
        ctx.addVal(VpnContextKeys.PROTOCOL, routeSpecLst);
        route = service.translate(ctx);
        Assert.assertNotNull(route);

        TranslatorCtx ctx2 = new TranslatorCtxImpl();
        ctx2.addVal(VpnContextKeys.TP, null);
        Assert.assertEquals(null, service.translate(ctx2));
    }
}
