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

import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3AcTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnRouteTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.ResponsTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The implement of L3 translator provider.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-2
 */
@Service
public class L3TranslatorProviderImpl implements L3TranslatorProvider {

    @Autowired
    private L3VpnTranslator l3VpnTranslator;

    @Autowired
    private L3AcTranslator l3AcTranslator;

    @Autowired
    private L3VpnRouteTranslator l3VpnRouteTranslator;

    @Autowired
    private ResponsTranslator responsTranslator;

    public void setL3VpnTranslator(L3VpnTranslator l3VpnTranslator) {
        this.l3VpnTranslator = l3VpnTranslator;
    }

    public void setL3AcTranslator(L3AcTranslator l3AcTranslator) {
        this.l3AcTranslator = l3AcTranslator;
    }

    public void setL3VpnRouteTranslator(L3VpnRouteTranslator l3VpnRouteTranslator) {
        this.l3VpnRouteTranslator = l3VpnRouteTranslator;
    }

    public void setResponsTranslator(ResponsTranslator responsTranslator) {
        this.responsTranslator = responsTranslator;
    }

    @Override
    public L3VpnTranslator getL3VpnTranslator() {
        return l3VpnTranslator;
    }

    @Override
    public L3AcTranslator getL3AcTranslator() {
        return l3AcTranslator;
    }

    @Override
    public L3VpnRouteTranslator getL3VpnRouteTranslator() {
        return l3VpnRouteTranslator;
    }

    @Override
    public ResponsTranslator getResponsTranslator() {
        return responsTranslator;
    }
}
