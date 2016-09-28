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
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.uniformsbi.l3vpn.L2Access;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Access;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3AcTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnL2AccessTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnL3AccessTranslatorImpl;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;

public class L3AcTranslatorImplTest {

    private L3AcTranslatorImpl service = new L3AcTranslatorImpl();

    @Before
    public void setUp() {
        new MockUp<L3VpnL2AccessTranslatorImpl>() {

            @Mock
            public L2Access translate(TranslatorCtx ctx) throws ServiceException {
                return new L2Access();
            }
        };

        new MockUp<L3VpnL3AccessTranslatorImpl>() {

            @Mock
            public L3Access translate(TranslatorCtx ctx) throws ServiceException {
                return new L3Access();
            }
        };
    }

    @Test
    public void testTranslate() throws ServiceException, IOException {
        TranslatorCtx ctx = new TranslatorCtxImpl();
        ctx.setOperType(OperType.CREATE);

        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "tp2.json";
        Tp tp = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Tp.class);
        ctx.addVal(VpnContextKeys.TP, tp);
        service.translate(ctx);

        TranslatorCtx ctx2 = new TranslatorCtxImpl();
        ctx2.addVal(VpnContextKeys.TP, null);
        Assert.assertEquals(null, service.translate(ctx2));
    }

}
