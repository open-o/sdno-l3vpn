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

package org.openo.sdno.wanvpn.translator.common;

import java.util.HashMap;
import java.util.List;

import org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus;
import org.openo.sdno.model.servicemodel.common.enumeration.OperStatus;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Acs;

/**
 * The tool class to operate the vpn status.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public class OperStatusUtils {

    private OperStatusUtils() {
    }

    /**
     * Set default status of Vpn.<br/>
     * 
     * @param vpn The Vpn object
     * @since SDNO 0.5
     */
    public static void setVpnDefaultStatus(Vpn vpn) {
        vpn.setOperStatus(OperStatus.NOP.getCommonName());
        vpn.getVpnBasicInfo().setAdminStatus(AdminStatus.NOP.getCommonName());

        final List<Tp> tps = vpn.getAccessPointList();
        for(final Tp tp : tps) {
            tp.setOperStatus(OperStatus.NOP.getCommonName());
            tp.setAdminStatus(AdminStatus.NOP.getCommonName());
        }
    }

    /**
     * Update the operation status and name of Tp.<br/>
     * 
     * @param vpn The Vpn object
     * @param l3Acs The L3Acs object
     * @since SDNO 0.5
     */
    public static void updateTpOperAndName(Vpn vpn, L3Acs l3Acs) {

        final List<Tp> tps = vpn.getAccessPointList();
        final List<L3Ac> lstL3Ac = l3Acs.getL3Ac();

        final HashMap<String, L3Ac> acsMap = new HashMap<String, L3Ac>();

        for(final L3Ac l3ac : lstL3Ac) {
            acsMap.put(l3ac.getUuid(), l3ac);
        }

        for(final Tp tp : tps) {
            final String tpId = tp.getId();
            if(acsMap.containsKey(tpId)) {
                final L3Ac l3ac = acsMap.get(tpId);
                tp.setOperStatus(org.openo.sdno.wanvpn.translator.uniformsbi.util.TranslatorUtil
                        .n2sOperStatus(l3ac.getOperStatus()));
                tp.setAdminStatus(org.openo.sdno.wanvpn.translator.uniformsbi.util.TranslatorUtil
                        .n2sAdminStatus(l3ac.getAdminStatus()));
                tp.setName(l3ac.getName());
            }
        }
    }

}
