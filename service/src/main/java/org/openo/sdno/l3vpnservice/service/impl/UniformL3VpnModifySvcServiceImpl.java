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
import org.openo.sdno.l3vpnservice.service.inf.L3VpnModifySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN modify service implement class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnModifySvcService")
public class UniformL3VpnModifySvcServiceImpl implements L3VpnModifySvcService {

    @Resource
    private L3VpnDao vpnDao;

    @Autowired
    private L3VpnSbiApiService l3VpnSbiApiService;

    @Override
    public Vpn modifyDesc(final Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        final String ctrlUuid = ControllerUtils.getControllerUUID(vpn);

        final L3Vpn l3vpn = new L3Vpn();

        final String uuid = vpn.getUuid();
        final String description = vpn.getDescription();
        l3vpn.setUuid(uuid);
        l3vpn.setDescription(description);
        l3VpnSbiApiService.modifyVpnDescrition(ctrlUuid, l3vpn, request);

        vpnDao.updateDescription(uuid, description);

        return vpn;
    }

    public void setVpnDao(L3VpnDao vpnDao) {
        this.vpnDao = vpnDao;
    }

    public void setL3VpnSbiApiService(L3VpnSbiApiService l3VpnSbiApiService) {
        this.l3VpnSbiApiService = l3VpnSbiApiService;
    }

}
