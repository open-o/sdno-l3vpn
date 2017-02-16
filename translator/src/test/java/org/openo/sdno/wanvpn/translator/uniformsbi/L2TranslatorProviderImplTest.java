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

package org.openo.sdno.wanvpn.translator.uniformsbi;

import org.junit.Assert;
import org.junit.Test;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L2AcTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L2VpnTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.ResponsTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L2AcTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L2VpnTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.ResponsTranslator;

public class L2TranslatorProviderImplTest {

    private final L2TranslatorProviderImpl l2Provider = new L2TranslatorProviderImpl();

    @Test
    public void test() {
        L2VpnTranslator L2VpnTranslator = new L2VpnTranslatorImpl();
        l2Provider.setL2VpnTranslator(L2VpnTranslator);

        L2AcTranslator tpTranslator = new L2AcTranslatorImpl();
        l2Provider.setL2AcTranslator(tpTranslator);

        ResponsTranslator responsTranslator = new ResponsTranslatorImpl();
        l2Provider.setResponsTranslator(responsTranslator);

        Assert.assertNotNull(l2Provider.getL2AcTranslator());
        Assert.assertNotNull(l2Provider.getL2VpnTranslator());
        Assert.assertNotNull(l2Provider.getResponsTranslator());
    }

}
