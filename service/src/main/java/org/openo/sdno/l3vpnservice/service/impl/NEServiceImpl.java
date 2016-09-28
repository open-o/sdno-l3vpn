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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l3vpnservice.service.inf.NEService;
import org.openo.sdno.model.common.query.BatchQueryParams;
import org.openo.sdno.model.common.query.BatchQueryResult;
import org.openo.sdno.model.servicemodel.brs.LtpMO;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.inventory.sdk.inf.IBaseInvDao;
import org.openo.sdno.wanvpn.util.constans.InvConstants;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.openo.sdno.wanvpn.util.inventory.TerminationPointUtil;
import org.openo.sdno.wanvpn.util.query.BatchQueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Network element service implement class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("neService")
public class NEServiceImpl implements NEService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NEServiceImpl.class);

    @Autowired
    @Qualifier("invTpService")
    private IBaseInvDao<LtpMO> invTpService;

    @Override
    public BatchQueryResult<LtpMO> getNePorts(final String neUuid, final BatchQueryParams batchQueryParams,
            @Context HttpServletRequest request) throws ServiceException {

        final Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put(LtpMO.NE_ID, neUuid);
        final Result<List<LtpMO>> ltpResult = invTpService.queryMOByParam(paraMap);

        if(ltpResult == null || CollectionUtils.isEmpty(ltpResult.getResultObj())) {
            throw ServiceExceptionUtil.getServiceException(CommonErrorCode.SITE_NOT_BIND_WITH_NE);
        }

        final List<LtpMO> allPorts = ltpResult.getResultObj();

        if(CollectionUtils.isEmpty(allPorts)) {
            return BatchQueryUtils.getFakeBatchQueryResult(null, batchQueryParams);
        }

        final String portName = batchQueryParams.getStringBusinessParam(InvConstants.COMMONNAME);
        List<LtpMO> selectedPorts = filterByName(allPorts, portName);
        selectedPorts = filterSubInterface(selectedPorts);
        if(batchQueryParams.getPageSize() == 0) {
            return new BatchQueryResult<>(selectedPorts, selectedPorts.size());
        } else {
            return BatchQueryUtils.getFakeBatchQueryResult(selectedPorts, batchQueryParams);
        }
    }

    private List<LtpMO> filterByName(final List<LtpMO> allPorts, final String portName) {
        final List<LtpMO> selectedPorts;
        if(StringUtils.isNotBlank(portName)) {
            selectedPorts = new LinkedList<>();
            for(final LtpMO port : allPorts) {
                if(port.getName().contains(portName)) {
                    selectedPorts.add(port);
                }
            }
        } else {
            selectedPorts = allPorts;
        }
        return selectedPorts;
    }

    private List<LtpMO> filterSubInterface(final List<LtpMO> allPorts) {
        final List<LtpMO> masterPorts = new LinkedList<>();
        final Iterator<LtpMO> iterator = allPorts.iterator();
        LtpMO ltpMO = null;
        while(iterator.hasNext()) {
            ltpMO = iterator.next();
            if(TerminationPointUtil.isSubEthInterface(ltpMO)) {
                LOGGER.info("Port " + ltpMO.getName() + " is a sub interface,will be ignored");
            } else {
                masterPorts.add(ltpMO);
            }
        }
        return masterPorts;
    }

    public void setInvTpService(IBaseInvDao<LtpMO> invTpService) {
        this.invTpService = invTpService;
    }
}
