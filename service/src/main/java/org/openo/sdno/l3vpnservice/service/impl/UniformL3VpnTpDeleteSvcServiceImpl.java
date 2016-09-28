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

package org.openo.sdno.l3vpnservice.service.impl;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l3vpnservice.constant.L3VpnSvcErrorCode;
import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQuerySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.inf.L3vpnTpDeleteSvcService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.l3vpnservice.service.util.L3VpnServiceUtils;
import org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN TP delete service implement class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnTpDeleteSvcService")
public class UniformL3VpnTpDeleteSvcServiceImpl implements L3vpnTpDeleteSvcService {

    @Autowired
    private L3VpnTpDao tpDao;

    @Autowired
    private L3VpnSbiApiService l3VpnSbiApiService;

    @Autowired
    @Qualifier("uniformL3VpnQuerySvcService")
    private L3VpnQuerySvcService l3VpnQuerySvcService;

    @Override
    public Tp deleteTp(Vpn vpn, String tpUuid, @Context HttpServletRequest request) throws ServiceException {
        if(null == vpn) {
            throw ServiceExceptionUtil.getServiceException(L3VpnSvcErrorCode.L3VPN_DELETE_TPNOTEXIST);
        }

        final Vpn l3VpnNew = l3VpnQuerySvcService.getStatus(vpn, request);

        Tp tempTp = L3VpnServiceUtils.getTpFromVpn(l3VpnNew, tpUuid);
        if(null == tempTp) {
            throw ServiceExceptionUtil.getServiceException(L3VpnSvcErrorCode.L3VPN_DELETE_TPNOTEXIST);
        }

        if(AdminStatus.ACTIVE.getCommonName().equals(tempTp.getAdminStatus())) {
            throw ServiceExceptionUtil.getServiceException(L3VpnSvcErrorCode.L3VPN_DELETE_ACTIVETP);
        }

        final List<Tp> tpLst = vpn.getAccessPointList();
        // If VPN only bind to a TP,it is not allowed to delete the TP.
        if(1 == tpLst.size()) {
            if(tpLst.get(0).getId().equals(tpUuid)) {
                throw ServiceExceptionUtil.getServiceException(L3VpnSvcErrorCode.L3VPN_DELETE_ONLYONETPBIND);
            } else {
                return tempTp;
            }

        }

        String controllerUuid = ControllerUtils.getControllerUUID(vpn);
        l3VpnSbiApiService.deleteTp(vpn.getUuid(), tpUuid, controllerUuid, request);

        tpDao.delMos(Collections.singletonList(tempTp));
        return tempTp;

    }

    public void setTpDao(L3VpnTpDao tpDao) {
        this.tpDao = tpDao;
    }
}
