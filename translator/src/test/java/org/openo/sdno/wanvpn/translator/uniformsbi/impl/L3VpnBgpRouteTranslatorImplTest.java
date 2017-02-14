/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.routeprotocol.BgpProtocolItem;
import org.openo.sdno.model.uniformsbi.l3vpn.BgpRoute;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxFactoryImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext.xml",
                "classpath*:META-INF/spring/service.xml", "classpath*:spring/service.xml"})
public class L3VpnBgpRouteTranslatorImplTest {

    @Autowired
    L3VpnBgpRouteTranslatorImpl l3VpnBgpRouteTranslatorImpl;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testTranslate() throws ServiceException, IOException {
        final String filePath = new File("src/test/resources/bgpProtocolItem.json").getCanonicalPath();
        BgpProtocolItem bgpProtocolItem =
                JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), BgpProtocolItem.class);
        bgpProtocolItem.setPassword("Test_1234");
        bgpProtocolItem.setPeerAsNumber(3);
        bgpProtocolItem.setPeerIp("127.0.0.2");
        TranslatorCtxFactoryImpl translatorCtxFactory = new TranslatorCtxFactoryImpl();
        final TranslatorCtx translatorCtx = translatorCtxFactory.getTranslatorCtx(OperType.CREATE);
        translatorCtx.addVal(VpnContextKeys.BGPROUTE, bgpProtocolItem);
        BgpRoute bgpRoute = l3VpnBgpRouteTranslatorImpl.translate(translatorCtx);
        assertEquals(bgpRoute.getPassword(), bgpProtocolItem.getPassword());
        assertEquals(bgpRoute.getPeerIp(), bgpProtocolItem.getPeerIp());
        assertEquals(bgpRoute.getRemoteAs(), bgpProtocolItem.getPeerAsNumber().toString());
    }
}
