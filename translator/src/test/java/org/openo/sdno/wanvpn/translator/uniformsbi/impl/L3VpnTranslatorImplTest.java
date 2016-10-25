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
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;

public class L3VpnTranslatorImplTest {

    @Autowired
    private L3VpnTranslatorImpl service = new L3VpnTranslatorImpl();

    @Before
    public void setUp() {
        new MockUp<L3AcTranslatorImpl>() {

            @Mock
            public L3Ac translate(TranslatorCtx ctx) throws ServiceException {
                return new L3Ac();
            }
        };
    }

    @Test
    public void test() throws ServiceException, IOException {
        TranslatorCtx ctx = new TranslatorCtxImpl();
        ctx.setOperType(OperType.CREATE);

        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "vpn.json";
        Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        ctx.addVal(VpnContextKeys.VPN, vpn);
        service.setL3AcTranslator(new L3AcTranslatorImpl());
        service.setL3TunnelServiceTranslator(new L3TunnelServiceTranslatorImpl());
        service.translate(ctx);

        ctx.setOperType(OperType.DELETE);
        Assert.assertEquals(null, service.translate(ctx));
    }
}
