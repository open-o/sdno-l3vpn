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
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Ac;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxFactoryImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;

public class L2AcTranslatorImplTest {

    L2AcTranslatorImpl l2AcTranslatorImpl;

    @Before
    public void setUp() throws Exception {
        l2AcTranslatorImpl = new L2AcTranslatorImpl();
    }

    @Test
    public void testTranslate() throws ServiceException, IOException {
        final String filePath = new File("src/test/resources/tp2.json").getCanonicalPath();
        Tp tp = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Tp.class);
        TranslatorCtxFactoryImpl translatorCtxFactory = new TranslatorCtxFactoryImpl();
        final TranslatorCtx translatorCtx = translatorCtxFactory.getTranslatorCtx(OperType.CREATE);
        translatorCtx.addVal(VpnContextKeys.TP, tp);

        L2Ac l2Ac = l2AcTranslatorImpl.translate(translatorCtx);
        assertEquals(l2Ac.getName(), tp.getName());
        assertEquals(l2Ac.getUuid(), tp.getUuid());
    }

    @Test
    public void testTranslateNull() throws ServiceException, IOException {
        TranslatorCtxFactoryImpl translatorCtxFactory = new TranslatorCtxFactoryImpl();
        final TranslatorCtx translatorCtx = translatorCtxFactory.getTranslatorCtx(OperType.CREATE);
        translatorCtx.addVal(VpnContextKeys.VPN, new Vpn());

        L2Ac l2Ac = l2AcTranslatorImpl.translate(translatorCtx);
        assertEquals(l2Ac, null);
    }
}
