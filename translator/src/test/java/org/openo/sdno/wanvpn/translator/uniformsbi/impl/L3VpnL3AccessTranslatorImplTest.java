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
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.uniformsbi.l3vpn.Route;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnL3AccessTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnRouteTranslatorImpl;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;

public class L3VpnL3AccessTranslatorImplTest {

    private final L3VpnL3AccessTranslatorImpl service = new L3VpnL3AccessTranslatorImpl();

    @Before
    public void setUp() {
        service.setL3VpnRouteTranslator(new L3VpnRouteTranslatorImpl());
        new MockUp<L3VpnRouteTranslatorImpl>() {

            @Mock
            public Route translate(TranslatorCtx ctx) throws ServiceException {
                return new Route();
            }
        };

    }

    @Test
    public void test() throws ServiceException, IOException {
        TranslatorCtx ctx = new TranslatorCtxImpl();

        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "tp.json";
        Tp tp = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Tp.class);

        List<RouteProtocolSpec> routeProtocolSpecs = new ArrayList();
        RouteProtocolSpec route = new RouteProtocolSpec();
        route.setType(RouteProtocolType.STATIC_ROUTING.getCommonName());
        routeProtocolSpecs.add(route);
        route = new RouteProtocolSpec();
        route.setType(RouteProtocolType.BGP.getCommonName());
        routeProtocolSpecs.add(route);
        tp.setRouteProtocolSpecs(routeProtocolSpecs);
        ctx.addVal(VpnContextKeys.TP, tp);

        service.translate(ctx);

        TranslatorCtx ctx2 = new TranslatorCtxImpl();
        Assert.assertEquals(null, service.translate(ctx2));
    }
}
