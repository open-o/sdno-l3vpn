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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.uniformsbi.l3vpn.L3LoopbackIf;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3LoopbackIfTranslatorImpl;

public class L3LoopbackIfTranslatorImplTest {

    private final L3LoopbackIfTranslatorImpl service = new L3LoopbackIfTranslatorImpl();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws ServiceException {
        TranslatorCtx ctx = new TranslatorCtxImpl();
        ctx.setOperType(OperType.CREATE);
        String str =
                "{     \"id\": \"9912eaee-8eda-43b8-a991-6d056f57a454\",   \"name\": \"NEPort1\",  \"description\": \"\",  \"adminStatus\": \"inactive\",  \"operStatus\": \"down\",   \"neId\": \"2d9793c4-02dd-4b7d-975e-53dbc59c6d2b\",     \"edgePointRole\": null, \"hubSpoke\":null,     \"type\": \"LoopBack\",  \"workingLayer\": \"LR_IP\",    \"typeSpecList\": [         {           \"uuid\": \"50b623a9-f4b6-471b-b949-948998fd5a4b\",             \"layerRate\": \"LR_IP\",           \"ethernetTpSpec\": {               \"uuid\": \"3a9efe5c-d6c4-426d-ac8e-141ebfed934d\",                 \"accessType\": \"dot1q\",              \"vlanAction\": null,               \"actionValue\": null,              \"qinqCvlanList\": null,                \"qinqSvlanList\": null,                \"dot1qVlanList\": \"23\",              \"addtionalInfo\": null             },          \"ipTpSpec\": {                 \"uuid\": \"5864f7e2-12d7-4493-aa73-37399d3aaca5\",                 \"masterIp\": \"10.10.10.1/24\",                \"addtionalInfo\": null             }       }   ],  \"peerCeTp\": {         \"uuid\": \"0f1d0673-8823-456a-8a13-6c0b28d1a3c0\",         \"ceID\": \"d45d9df5-4a1c-4f6f-8be0-dce91394f1c3\",         \"ceDirectNeID\": \"2d9793c4-02dd-4b7d-975e-53dbc59c6d2b\",         \"ceDirectTPID\": \"3eb1a10d-5d43-468c-80cc-5f067d784b8a\",         \"siteName\": \"SiteA\",        \"ceName\": \"SiteA\",      \"ceIfmasterIp\": \"10.10.10.2/24\",        \"location\": null,         \"addtionalInfo\": null     },  \"inboundQosPolicyId\": null,   \"outboundQosPolicyId\": null,  \"inBoundTpCar\": {         \"uuid\": \"e437859c-8b62-4155-9703-1be47287ff41\",         \"enableCar\": false,       \"cir\": 0,         \"pir\": 0,         \"cbs\": 0,         \"pbs\": 0  },  \"outBoundTpCar\": {        \"uuid\": \"c4ca48fb-cdbb-4037-b6a7-013ade3f046d\",         \"enableCar\": false,       \"cir\": 0,         \"pir\": 0,         \"cbs\": 0,         \"pbs\": 0  },  \"routeProtocolSpecs\": null,   \"containedMainTP\": \"3eb1a10d-5d43-468c-80cc-5f067d784b8a\",  \"addtionalInfo\": null }";
        Tp tp = JsonUtil.fromJson(str, Tp.class);
        ctx.addVal(VpnContextKeys.TP, tp);
        L3LoopbackIf ac = service.translate(ctx);

        TranslatorCtx ctx2 = new TranslatorCtxImpl();
        ctx2.addVal(VpnContextKeys.TP, null);
        Assert.assertEquals(null, service.translate(ctx2));
    }

}
