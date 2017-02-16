/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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
import org.junit.runner.RunWith;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext.xml",
                "classpath*:META-INF/spring/service.xml", "classpath*:spring/service.xml"})
public class L3AcTranslatorImplTest {

    @Autowired
    L3AcTranslatorImpl service;

    @Before
    public void setUp() {

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
        Assert.assertNotNull(service.getL3VpnL2AccessTranslator());
        Assert.assertNotNull(service.getL3VpnL3AccessTranslator());

        service.setL3VpnL3AccessTranslator(new L3VpnL3AccessTranslatorImpl());
        service.setL3VpnL2AccessTranslator(new L3VpnL2AccessTranslatorImpl());
        Assert.assertNotNull(service.getL3VpnL2AccessTranslator());
        Assert.assertNotNull(service.getL3VpnL3AccessTranslator());
    }

}
