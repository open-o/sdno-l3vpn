/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.l3vpnservice.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.cbb.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.cbb.wanvpn.inventory.sdk.common.ServiceTypeEnum;
import org.openo.sdno.cbb.wanvpn.util.constans.InvConstants;
import org.openo.sdno.cbb.wanvpn.util.executor.resource.app.AppResourceQueryBatchExecutor;
import org.openo.sdno.framework.container.service.IResource;
import org.openo.sdno.l3vpnservice.service.inf.NEService;
import org.openo.sdno.model.common.query.BatchQueryParams;
import org.openo.sdno.model.common.query.BatchQueryResult;
import org.openo.sdno.model.servicemodel.brs.LtpMO;

/**
 * Restful interface class of NE resource.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-5-30
 */
@Path("/svc/l3vpn/v1/ne")
public class NEResource extends IResource<NEService> {

    @Override
    public String getResUri() {
        return "/svc/l3vpn/v1/ne";
    }

    /**
     * Batch query ports info by NE ID and port name.<br/>
     * 
     * @param neUuid NE UUID
     * @param portName port name
     * @param request HttpServlet request
     * @return ports info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    @GET
    @Path("/{uuid}/terminalpoint")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public BatchQueryResult<LtpMO> getNePorts(@PathParam("uuid") final String neUuid,
            @QueryParam("commonName") final String portName, @Context final HttpServletRequest request)
            throws ServiceException {
        OwnerInfoThreadLocal.setOwnerInfo(request, ServiceTypeEnum.BRS);
        return new AppResourceQueryBatchExecutor<LtpMO>(request) {

            @Override
            protected void assembleParams(final BatchQueryParams batchQueryParams) throws ServiceException {
                if(StringUtils.isNotBlank(portName)) {
                    batchQueryParams.addBusinessParam(InvConstants.COMMONNAME, portName);
                }
            }

            @Override
            protected BatchQueryResult<LtpMO> implementQuery(final BatchQueryParams batchQueryParams)
                    throws ServiceException {
                return service.getNePorts(neUuid, batchQueryParams, request);
            }
        }.execute().getResult();
    }
}
