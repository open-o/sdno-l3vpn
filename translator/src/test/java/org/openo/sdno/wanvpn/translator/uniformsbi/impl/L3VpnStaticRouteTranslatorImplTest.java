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

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.routeprotocol.StaticRouteTable;
import org.openo.sdno.model.uniformsbi.l3vpn.StaticRoute;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnStaticRouteTranslatorImpl;

import junit.framework.Assert;

public class L3VpnStaticRouteTranslatorImplTest {

    private final L3VpnStaticRouteTranslatorImpl service = new L3VpnStaticRouteTranslatorImpl();

    @Before
    public void setUp() {

    }

    @Test
    public void test() throws ServiceException, IOException {
        TranslatorCtx ctx = new TranslatorCtxImpl();

        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "routeTable.json";

        StaticRouteTable routeTable = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), StaticRouteTable.class);
        ctx.addVal(VpnContextKeys.STARICROUTE, routeTable);

        StaticRoute route = service.translate(ctx);

        TranslatorCtx ctx2 = new TranslatorCtxImpl();
        Assert.assertEquals(null, service.translate(ctx2));
    }
}
