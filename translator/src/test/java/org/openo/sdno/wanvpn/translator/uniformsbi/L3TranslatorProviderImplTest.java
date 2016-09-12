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

package org.openo.sdno.wanvpn.translator.uniformsbi;

import org.junit.Assert;
import org.junit.Test;
import org.openo.sdno.wanvpn.translator.uniformsbi.L3TranslatorProviderImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3AcTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnRouteTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.ResponsTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3AcTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnRouteTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.ResponsTranslator;

public class L3TranslatorProviderImplTest {

    private final L3TranslatorProviderImpl l3Provider = new L3TranslatorProviderImpl();

    @Test
    public void test() {
        L3VpnTranslator L3VpnTranslator = new L3VpnTranslatorImpl();
        l3Provider.setL3VpnTranslator(L3VpnTranslator);

        L3AcTranslator tpTranslator = new L3AcTranslatorImpl();
        l3Provider.setL3AcTranslator(tpTranslator);

        L3VpnRouteTranslator vpnRouteTranslator = new L3VpnRouteTranslatorImpl();
        l3Provider.setL3VpnRouteTranslator(vpnRouteTranslator);

        ResponsTranslator responsTranslator = new ResponsTranslatorImpl();
        l3Provider.setResponsTranslator(responsTranslator);

        Assert.assertNotNull(l3Provider.getL3AcTranslator());
        Assert.assertNotNull(l3Provider.getL3VpnTranslator());
        Assert.assertNotNull(l3Provider.getL3VpnRouteTranslator());
        Assert.assertNotNull(l3Provider.getL3VpnRouteTranslator());
        Assert.assertNotNull(l3Provider.getResponsTranslator());
    }
}
