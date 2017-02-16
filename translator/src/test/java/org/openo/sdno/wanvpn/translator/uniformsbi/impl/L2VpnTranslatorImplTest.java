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
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.businesstype.TunnelSchema;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxFactoryImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;

public class L2VpnTranslatorImplTest {

    L2VpnTranslatorImpl l2VpnTranslatorImpl;

    @Before
    public void setUp() throws Exception {
        l2VpnTranslatorImpl = new L2VpnTranslatorImpl();
        l2VpnTranslatorImpl.setL2AcTranslator(new L2AcTranslatorImpl());
        l2VpnTranslatorImpl.setL3TunnelServiceTranslator(new L3TunnelServiceTranslatorImpl());
    }

    @Test
    public void testTranslate() throws ServiceException, IOException {
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        TranslatorCtxFactoryImpl translatorCtxFactory = new TranslatorCtxFactoryImpl();
        final TranslatorCtx translatorCtx = translatorCtxFactory.getTranslatorCtx(OperType.CREATE);
        translatorCtx.addVal(VpnContextKeys.VPN, vpn);

        filePath = new File("src/test/resources/tunnelSchema.json").getCanonicalPath();
        TunnelSchema tunnelSchema = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), TunnelSchema.class);
        translatorCtx.addVal(VpnContextKeys.TUNNEL_SCHEMA, tunnelSchema);

        L2Vpn l2Vpn = l2VpnTranslatorImpl.translate(translatorCtx);
        assertEquals(l2Vpn.getUuid(), vpn.getUuid());
        assertEquals(l2Vpn.getName(), vpn.getName());
    }

    @Test
    public void testTranslateNull() throws ServiceException, IOException {
        TranslatorCtxFactoryImpl translatorCtxFactory = new TranslatorCtxFactoryImpl();
        final TranslatorCtx translatorCtx = translatorCtxFactory.getTranslatorCtx(OperType.CREATE);
        translatorCtx.addVal(VpnContextKeys.TP, new Tp());

        L2Vpn l2Vpn = l2VpnTranslatorImpl.translate(translatorCtx);
        assertEquals(l2Vpn, null);
    }
}
