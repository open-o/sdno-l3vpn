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

/**
 * The interface class of L3 translator provider.<br>
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public interface L3TranslatorProvider {

    /**
     * The interface of getting L3VpnTranslator.<br>
     *
     * @param controllerType The controller type
     * @return The L3VpnTranslator
     * @since SDNO 0.5
     */
    L3VpnTranslator getL3VpnTranslator();

    /**
     * The interface of getting L3AcTranslator.<br>
     *
     * @param controllerType The controller type
     * @return The L3AcTranslator
     * @since SDNO 0.5
     */
    L3AcTranslator getL3AcTranslator();

    /**
     * The interface of getting L3VpnRouteTranslator.<br>
     *
     * @return The L3VpnRouteTranslator
     * @since SDNO 0.5
     */
    L3VpnRouteTranslator getL3VpnRouteTranslator();

    /**
     * The interface of getting ResponsTranslator.<br>
     *
     * @return The ResponsTranslator
     * @since SDNO 0.5
     */
    ResponsTranslator getResponsTranslator();
}
