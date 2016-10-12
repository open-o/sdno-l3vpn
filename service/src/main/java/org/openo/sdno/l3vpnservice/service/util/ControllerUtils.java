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

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.brs.ControllerMO;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.wanvpn.inventory.sdk.util.InventoryProxy;

/**
 * Controller utility class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-30
 */
public class ControllerUtils {

    private ControllerUtils() {
    }

    /**
     * Query controller type.<br>
     * 
     * @param vpnVo VPN info
     * @return controller's product name
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static String getControllerType(final VpnVo vpnVo) throws ServiceException {
        if((null == vpnVo) || (null == vpnVo.getVpn())) {
            return null;
        }
        return ControllerUtils.getControllerType(vpnVo.getVpn());
    }

    /**
     * Query controller type.<br>
     * 
     * @param vpn VPN info
     * @return controller's product name
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static String getControllerType(final Vpn vpn) throws ServiceException {
        if(null == vpn) {
            return null;
        }

        final ControllerMO controllerMO = ControllerUtils.getControllerMO(vpn);
        if(null == controllerMO) {
            return null;
        }
        return controllerMO.getProductName();
    }

    /**
     * Fill controller MO info.<br>
     * 
     * @param vpnVo VPN info
     * @throws ServiceException when fill failed
     * @since SDNO 0.5
     */
    public static void fillControllerMo(final VpnVo vpnVo) throws ServiceException {
        if(null == vpnVo) {
            return;
        }
        final Vpn vpn = vpnVo.getVpn();
        if(null == vpn) {
            return;
        }

        final List<Tp> tps = vpn.getAccessPointList();
        if(CollectionUtils.isEmpty(tps)) {
            return;
        }

        final Tp tp = tps.get(0);
        if(tp == null) {
            return;
        }

        final ControllerMO controllerMO = tp.getContollerMO();
        if(null != controllerMO) {
            return;
        }

        tp.setContollerMO(ControllerUtils.mGetControllerMo(tp));
    }

    /**
     * Query controller type.<br>
     * 
     * @param tp TP info
     * @return controller's product name
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static String getControllerType(final Tp tp) throws ServiceException {
        if(null == tp) {
            return null;
        }

        final ControllerMO controllerMO = ControllerUtils.getControllerMO(tp);
        if(null == controllerMO) {
            return null;
        }
        return controllerMO.getProductName();
    }

    private static ControllerMO mGetControllerMo(final Tp tp) throws ServiceException {
        if(null != tp.getContollerMO()) {
            return tp.getContollerMO();
        }
        ControllerMO queryController = InventoryProxy.queryController(tp.getNeId());
        tp.setContollerMO(queryController);
        return queryController;
    }

    /**
     * Query controller MO info by NE ID.<br>
     * 
     * @param neId NE ID
     * @return controller MO info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static ControllerMO mGetControllerMo(final String neId) throws ServiceException {
        return InventoryProxy.queryController(neId);
    }

    /**
     * Query controller MO info.<br>
     * 
     * @param vpn VPN info
     * @return controller MO info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static ControllerMO getControllerMO(final Vpn vpn) throws ServiceException {
        if(null == vpn) {
            return null;
        }

        final List<Tp> tps = vpn.getAccessPointList();
        if(CollectionUtils.isEmpty(tps)) {
            return null;
        }

        final Tp tp = tps.get(0);
        if(tp == null) {
            return null;
        }
        final ControllerMO controllerMO = tp.getContollerMO();
        if(null != controllerMO) {
            return controllerMO;
        }
        return ControllerUtils.mGetControllerMo(tp);
    }

    /**
     * Query controller UUID.<br>
     * 
     * @param vpn VPN info
     * @return controller UUID
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static String getControllerUUID(final Vpn vpn) throws ServiceException {
        final ControllerMO controllerMO = ControllerUtils.getControllerMO(vpn);
        if(null == controllerMO) {
            return null;
        }
        return controllerMO.getObjectId();
    }

    /**
     * Query controller MO info.<br>
     * 
     * @param tp TP info
     * @return controller MO info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static ControllerMO getControllerMO(final Tp tp) throws ServiceException {
        if(null == tp) {
            return null;
        }

        final ControllerMO controllerMO = tp.getContollerMO();
        if(null != controllerMO) {
            return controllerMO;
        }
        return ControllerUtils.mGetControllerMo(tp);
    }

    /**
     * Query controller UUID.<br>
     * 
     * @param vpnVo VPN info
     * @return controller UUID
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static String getControllerUUID(final VpnVo vpnVo) throws ServiceException {
        if(null == vpnVo) {
            return null;
        }
        return ControllerUtils.getControllerUUID(vpnVo.getVpn());
    }

    /**
     * Query controller UUID.<br>
     * 
     * @param tp TP info
     * @return controller UUID
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static String getControllerUUID(final Tp tp) throws ServiceException {
        final ControllerMO controllerMO = ControllerUtils.mGetControllerMo(tp);
        if(null == controllerMO) {
            return null;
        }
        return controllerMO.getObjectId();
    }

}
