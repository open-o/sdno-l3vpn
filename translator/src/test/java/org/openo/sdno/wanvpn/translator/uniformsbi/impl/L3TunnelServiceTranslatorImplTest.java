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
import org.openo.sdno.model.servicemodel.businesstype.TunnelSchema;
import org.openo.sdno.model.servicemodel.common.enumeration.PwVlanActionType;
import org.openo.sdno.model.servicemodel.common.enumeration.TunnelTechType;
import org.openo.sdno.model.uniformsbi.base.TunnelService;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.SignalType;
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
public class L3TunnelServiceTranslatorImplTest {

    @Autowired
    L3TunnelServiceTranslatorImpl l3TunnelServiceTranslatorImpl;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testTranslate() throws ServiceException, IOException {
        final String filePath = new File("src/test/resources/tunnelSchema.json").getCanonicalPath();
        TunnelSchema tunnelSchema = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), TunnelSchema.class);
        tunnelSchema.setTunnelTech(TunnelTechType.RSVP_TE.getAlias());
        tunnelSchema.getPwTech().setPwVlanAction(PwVlanActionType.TAGGED.getAlias());
        TranslatorCtxFactoryImpl translatorCtxFactory = new TranslatorCtxFactoryImpl();
        final TranslatorCtx translatorCtx = translatorCtxFactory.getTranslatorCtx(OperType.CREATE);
        translatorCtx.addVal(VpnContextKeys.TUNNEL_SCHEMA, tunnelSchema);
        TunnelService tunnelService = l3TunnelServiceTranslatorImpl.translate(translatorCtx);
        assertEquals(tunnelService.getMplsTe().getSignalType(), SignalType.RSVP_TE.getAlias());
        assertEquals(PwVlanActionType.TAGGED, PwVlanActionType.fromName(tunnelSchema.getPwTech().getPwVlanAction()));

        tunnelSchema.setTunnelTech(TunnelTechType.LDP.getAlias());
        tunnelService = l3TunnelServiceTranslatorImpl.translate(translatorCtx);
        assertEquals(tunnelService.getMplsTe().getSignalType(), SignalType.LDP.getAlias());

        tunnelSchema.setTunnelTech(TunnelTechType.SR_TE.getAlias());
        tunnelService = l3TunnelServiceTranslatorImpl.translate(translatorCtx);
        assertEquals(tunnelService.getMplsTe().getSignalType(), SignalType.RSVP_TE.getAlias());

        tunnelSchema.setTunnelTech(TunnelTechType.GRE.getAlias());
        tunnelService = l3TunnelServiceTranslatorImpl.translate(translatorCtx);
        assertEquals(tunnelService.getMplsTe().getSignalType(), "");
    }

}
