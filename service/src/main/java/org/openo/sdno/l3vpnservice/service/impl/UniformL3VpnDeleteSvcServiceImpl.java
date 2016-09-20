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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l3vpnservice.dao.L3VpnDao;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnDeleteSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQuerySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN delete service implement class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnDeleteSvcService")
public class UniformL3VpnDeleteSvcServiceImpl implements L3VpnDeleteSvcService {

    @Autowired
    private L3VpnDao vpnDao;

    @Autowired
    @Qualifier("uniformL3VpnQuerySvcService")
    private L3VpnQuerySvcService l3VpnQuerySvcService;

    @Autowired
    private L3VpnSbiApiService l3VpnSbiApiService;

    @Override
    public Vpn delete(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        if(null == vpn || null == vpn.getVpnBasicInfo()) {
            return null;
        }

        l3VpnSbiApiService.deleteVpn(vpn.getId(), ControllerUtils.getControllerUUID(vpn), request);

        this.vpnDao.delMos(Collections.singletonList(vpn));
        return vpn;
    }

    public void setVpnDao(L3VpnDao vpnDao) {
        this.vpnDao = vpnDao;
    }

    public void setL3VpnQuerySvcService(L3VpnQuerySvcService l3VpnQuerySvcService) {
        this.l3VpnQuerySvcService = l3VpnQuerySvcService;
    }

    public void setL3VpnSbiApiService(L3VpnSbiApiService l3VpnSbiApiService) {
        this.l3VpnSbiApiService = l3VpnSbiApiService;
    }

}
