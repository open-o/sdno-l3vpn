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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQueryTePathService;
import org.openo.sdno.model.common.query.BatchQueryResult;
import org.openo.sdno.model.servicemodel.common.enumeration.PathRole;
import org.openo.sdno.model.servicemodel.tepath.ServiceTePath;
import org.openo.sdno.model.servicemodel.tepath.TePath;
import org.openo.sdno.model.servicemodel.tepath.TePathQueryKey;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.PathStatus;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN query TE path service implement class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniforml3vpnQueryTePathService")
public class UniformL3VpnQueryTePathServiceImpl implements L3VpnQueryTePathService {

    @Override
    public BatchQueryResult<TePath> getTePath(Vpn vpn, TePathQueryKey queryKey, @Context HttpServletRequest request)
            throws ServiceException {
        TePath tePath = new TePath();

        String egressNeId = "";
        String ingressNeId = "";
        String ingressAcId = queryKey.getSrcAcId();
        String egressAcId = queryKey.getDestAcId();

        List<ServiceTePath> serviceTePathList = new ArrayList<ServiceTePath>();
        List<Tp> tps = vpn.getAccessPointList();
        for(Tp tp : tps) {
            ServiceTePath serviceTePath = new ServiceTePath();
            serviceTePath.setNeId(tp.getNeId());

            if(ingressAcId.equals(tp.getUuid())) {
                ingressNeId = tp.getNeId();
                serviceTePath.setEgressTpId(ingressAcId);
            }

            if(egressAcId.equals(tp.getUuid())) {
                egressNeId = tp.getNeId();
                serviceTePath.setIngressTpId(egressAcId);
            }
            serviceTePathList.add(serviceTePath);
        }

        tePath.setPathRole(PathRole.WORK.getCommonName());
        tePath.setPathStatus(PathStatus.ACTIVE.getName());
        tePath.setIngressNeid(ingressNeId);
        tePath.setEgressNeid(egressNeId);
        tePath.setPathList(serviceTePathList);

        return new BatchQueryResult<TePath>(Collections.singletonList(tePath), 0);
    }
}
