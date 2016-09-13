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

package org.openo.sdno.l3vpnservice.service.util;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

/**
 * L3 VPN service util class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-30
 */
public class L3VpnServiceUtils {

    private L3VpnServiceUtils() {
    }

    /**
     * Query TP info by VPN info and TP ID.<br>
     * 
     * @param vpn VPN info
     * @param tpId TP ID
     * @return TP info
     * @since SDNO 0.5
     */
    public static Tp getTpFromVpn(final Vpn vpn, final String tpId) {
        final List<Tp> tpList = vpn.getAccessPointList();

        if(CollectionUtils.isNotEmpty(tpList)) {
            for(final Tp tp : tpList) {
                if(Objects.equals(tpId, tp.getUuid())) {
                    return tp;
                }
            }
        }
        return null;
    }

    /**
     * Query route protocol info from TP info.<br>
     * 
     * @param tempTp TP info
     * @param routeId route ID
     * @return route protocol info
     * @since SDNO 0.5
     */
    public static RouteProtocolSpec getRouteFromTp(Tp tempTp, String routeId) {
        final List<RouteProtocolSpec> routeList = tempTp.getRouteProtocolSpecs();

        if(CollectionUtils.isNotEmpty(routeList)) {
            for(final RouteProtocolSpec route : routeList) {
                if(Objects.equals(routeId, route.getUuid())) {
                    return route;
                }
            }
        }
        return null;
    }

}
