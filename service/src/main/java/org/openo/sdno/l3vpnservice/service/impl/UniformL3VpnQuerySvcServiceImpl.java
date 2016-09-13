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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l3vpnservice.dao.L3VpnDao;
import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQuerySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.VpnOperStatus;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.wanvpn.translator.common.OperStatusUtils;
import org.openo.sdno.wanvpn.translator.uniformsbi.util.TranslatorUtil;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN query service implement class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnQuerySvcService")
public class UniformL3VpnQuerySvcServiceImpl implements L3VpnQuerySvcService {

    @Resource
    private L3VpnDao vpnDao;

    @Resource
    private L3VpnTpDao tpDao;

    @Resource
    private L3VpnSbiApiService l3VpnSbiApiService;

    @Override
    public Vpn getDetail(String uuid, @Context HttpServletRequest request) throws ServiceException {
        // Query detailed info from database
        final Vpn vpn = vpnDao.getMoById(uuid);
        if(vpn == null) {
            throw ServiceExceptionUtil.getBadRequestServiceException(CommonErrorCode.VPN_NOT_EXIST);
        }

        return vpn;
    }

    @Override
    public Vpn getStatus(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        if(vpn == null) {
            throw ServiceExceptionUtil.getBadRequestServiceException(CommonErrorCode.VPN_NOT_EXIST);
        }

        OperStatusUtils.setVpnDefaultStatus(vpn);

        final String ctrluuid = ControllerUtils.getControllerUUID(vpn);

        final L3Vpn l3vpnSbi = l3VpnSbiApiService.getL3vpnDetail(vpn.getUuid(), ctrluuid, request);

        if(null != l3vpnSbi) {

            updateStatus(vpn, l3vpnSbi);

            if(null != l3vpnSbi.getAcs()) {
                OperStatusUtils.updateTpOperAndName(vpn, l3vpnSbi.getAcs());
            }

            tpDao.updateMos(vpn.getAccessPointList());
            vpnDao.updateVpn(vpn);
        }

        return vpn;
    }

    private void updateStatus(Vpn vpn, L3Vpn l3vpnSbi) {
        final String operstatus = TranslatorUtil.n2sOperStatus(l3vpnSbi.getOperStatus());
        vpn.setOperStatus(operstatus);

        final String adminstatus = TranslatorUtil.n2sAdminStatus(l3vpnSbi.getAdminStatus());
        vpn.getVpnBasicInfo().setAdminStatus(adminstatus);

    }

    @Override
    public VpnOperStatus getOperStatus(String vpnId, String ctrluuid, @Context HttpServletRequest request)
            throws ServiceException {
        // Unimplemented.
        return null;
    }

    @Override
    public Vpn getL3vpnAdminStatus(Vpn vpn, String ctrluuid, @Context HttpServletRequest request)
            throws ServiceException {

        vpn.getVpnBasicInfo().setAdminStatus(AdminStatus.NOP.getCommonName());

        final L3Vpn l3vpnSbi = l3VpnSbiApiService.getL3vpnDetail(vpn.getId(), ctrluuid, request);

        if(null != l3vpnSbi) {
            final String adminstatus = TranslatorUtil.n2sAdminStatus(l3vpnSbi.getAdminStatus());
            vpn.getVpnBasicInfo().setAdminStatus(adminstatus);
            vpnDao.updateVpn(vpn);
        }

        return vpn;
    }

}
