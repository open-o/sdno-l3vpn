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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l3vpnservice.constant.L3VpnSvcErrorCode;
import org.openo.sdno.l3vpnservice.dao.L3VpnBasicInfoDao;
import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnActiveSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN activate service implement class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnActiveSvcService")
public class UniformL3VpnActiveSvcServiceImpl implements L3VpnActiveSvcService {

    @Resource
    private L3VpnBasicInfoDao l3VpnBasicInfoDao;

    @Resource
    private L3VpnTpDao tpDao;

    @Autowired
    private L3VpnSbiApiService l3VpnSbiApiService;

    @Override
    public Vpn active(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return modifyVpnAdminStatus(vpn, AdminStatus.ACTIVE, request);
    }

    @Override
    public Vpn deactive(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return modifyVpnAdminStatus(vpn, AdminStatus.INACTIVE, request);
    }

    private Vpn modifyVpnAdminStatus(final Vpn vpn, AdminStatus adminStatus, @Context HttpServletRequest request)
            throws ServiceException {
        if(vpn == null) {
            throw ServiceExceptionUtil.getBadRequestServiceException(L3VpnSvcErrorCode.VPN_NOT_EXIST);
        }

        final L3Vpn l3Vpn = new L3Vpn();

        l3Vpn.setUuid(vpn.getId());
        if(adminStatus == AdminStatus.ACTIVE) {
            l3Vpn.setAdminStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus.ADMIN_UP);
        } else {
            l3Vpn.setAdminStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus.ADMIN_DOWN);
        }

        final String ctrlUuid = ControllerUtils.getControllerUUID(vpn);

        l3VpnSbiApiService.deployVpnStatus(l3Vpn, ctrlUuid, request);

        vpn.getVpnBasicInfo().setAdminStatus(adminStatus.getAlias());

        l3VpnBasicInfoDao.updateStatus(Collections.singletonList(vpn.getVpnBasicInfo()));

        return vpn;
    }

    public void setVpnBasicInfoDao(final L3VpnBasicInfoDao vpnBasicInfoDao) {
        this.l3VpnBasicInfoDao = vpnBasicInfoDao;
    }

    @Override
    public Tp activeSite(Vpn vpn, String tpId, @Context HttpServletRequest request) throws ServiceException {
        return modifyTpAdminStatus(vpn, tpId, AdminStatus.ACTIVE, request);
    }

    @Override
    public Tp deactiveSite(Vpn vpn, String tpId, @Context HttpServletRequest request) throws ServiceException {
        return modifyTpAdminStatus(vpn, tpId, AdminStatus.INACTIVE, request);
    }

    private Tp modifyTpAdminStatus(final Vpn vpn, final String tpUuid, AdminStatus adminStatus,
            @Context HttpServletRequest request) throws ServiceException {

        if(vpn == null) {
            throw ServiceExceptionUtil.getBadRequestServiceException(L3VpnSvcErrorCode.VPN_NOT_EXIST);
        }
        final Tp tempTp = tpDao.getMoById(tpUuid);

        if(null == tempTp) {
            throw ServiceExceptionUtil.getBadRequestServiceException(L3VpnSvcErrorCode.TP_NOT_EXIST);
        }

        L3Ac l3ac = new L3Ac();
        l3ac.setUuid(tempTp.getUuid());
        if(adminStatus == AdminStatus.ACTIVE) {
            l3ac.setAdminStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus.ADMIN_UP);
        } else {
            l3ac.setAdminStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus.ADMIN_DOWN);
        }

        final String ctrlUuid = ControllerUtils.getControllerUUID(vpn);

        l3VpnSbiApiService.deployTpStatus(vpn.getUuid(), l3ac, ctrlUuid, request);
        tempTp.setAdminStatus(adminStatus.getAlias());

        tpDao.updateStatus(Collections.singletonList(tempTp));
        return tempTp;

    }

    public void setTpDao(final L3VpnTpDao tpDao) {
        this.tpDao = tpDao;
    }

    public void setL3VpnSbiApiService(L3VpnSbiApiService l3VpnSbiApiService) {
        this.l3VpnSbiApiService = l3VpnSbiApiService;
    }

}
