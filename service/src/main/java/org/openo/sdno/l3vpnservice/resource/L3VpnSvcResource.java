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

package org.openo.sdno.l3vpnservice.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpStatus;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.util.RestUtils;
import org.openo.sdno.framework.container.service.IResource;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSvcService;
import org.openo.sdno.model.common.query.BatchQueryParams;
import org.openo.sdno.model.common.query.BatchQueryResult;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.model.servicemodel.tepath.TePath;
import org.openo.sdno.model.servicemodel.tepath.TePathQueryKey;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.inventory.sdk.common.ServiceTypeEnum;
import org.openo.sdno.wanvpn.util.checker.ScopeChecker;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.openo.sdno.wanvpn.util.executor.ExecutorUtils;
import org.openo.sdno.wanvpn.util.executor.resource.svc.SvcResourceCreateExecutor;
import org.openo.sdno.wanvpn.util.executor.resource.svc.SvcResourceDeleteUUIDExecutor;
import org.openo.sdno.wanvpn.util.executor.resource.svc.SvcResourceQueryBatchExecutor;
import org.openo.sdno.wanvpn.util.executor.resource.svc.SvcResourceQuerySingleExecutor;
import org.openo.sdno.wanvpn.util.executor.resource.svc.SvcResourceUpdateExecutor;
import org.openo.sdno.wanvpn.util.rest.ROAInputStreamParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Restful interface class of L3 VPN service resource.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-30
 */
@Path("/sdnol3vpn/v1/l3vpns")
public class L3VpnSvcResource extends IResource<L3VpnSvcService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(L3VpnSvcResource.class);

    @Override
    public String getResUri() {
        return "/sdnol3vpn/v1/l3vpns";
    }

    /**
     * Create Single L3Vpn.<br>
     *
     * @param request HttpServletRequest Object
     * @return L3Vpn object created
     * @throws ServiceException when create failed
     * @since SDNO 0.5
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Vpn createSingleVpn(@Context final HttpServletRequest request) throws ServiceException {
        return new SvcResourceCreateExecutor<VpnVo, Vpn>(request) {

            @Override
            protected void assertParam(VpnVo vpnVo) throws ServiceException {
                checkScope(vpnVo.getVpn());
                checkScope(vpnVo.getTunnelSchema());
            }

            @Override
            protected VpnVo extractParam(@Context final HttpServletRequest request) throws ServiceException {
                return ROAInputStreamParser.fromJson(RestUtils.getRequestBody(request), VpnVo.class);
            }

            @Override
            protected Vpn implementCreate(VpnVo vpnVo) throws ServiceException {
                OwnerInfoThreadLocal.setOwnerInfo(request, ServiceTypeEnum.L3VPN);
                return service.create(vpnVo, request);
            }
        }.execute().getResult();
    }

    /**
     * Delete Single L3Vpn by id.<br>
     *
     * @param uuid L3Vpn id
     * @param request HttpServletRequest object
     * @return L3Vpn object deleted
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    @DELETE
    @Path("/{uuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Vpn deleteSingleVpn(@PathParam(value = "uuid") final String uuid, @Context final HttpServletRequest request)
            throws ServiceException {
        return new SvcResourceDeleteUUIDExecutor<Vpn>(uuid) {

            @Override
            protected Vpn implementDelete(String uuid) throws ServiceException {
                OwnerInfoThreadLocal.setOwnerInfo(request, ServiceTypeEnum.L3VPN);
                Vpn vpn = getVpnForDelete(uuid, request);

                if(null == vpn) {
                    return generateDeletedVpn(uuid);
                }
                return service.delete(vpn, request);
            }
        }.execute().getResult();
    }

    private Vpn generateDeletedVpn(final String uuid) {
        final Vpn result = new Vpn();
        result.setUuid(uuid);
        return result;
    }

    private Vpn getVpnForDelete(final String uuid, @Context final HttpServletRequest request) throws ServiceException {
        try {
            return service.getDetail(uuid, request);
        } catch(ServiceException e) {
            if(HttpStatus.SC_NOT_FOUND == e.getHttpCode()) {
                LOGGER.warn("vpn doesn't exist or has been deleted " + uuid);
            } else {
                throw e;
            }
        }
        return null;
    }

    /**
     * Query One L3Vpn Object.<br>
     *
     * @param uuid L3Vpn id
     * @param request HttpServletRequest Object
     * @return L3Vpn queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    @GET
    @Path("/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Vpn getDetail(@PathParam("uuid") final String uuid, @Context final HttpServletRequest request)
            throws ServiceException {
        return new SvcResourceQuerySingleExecutor<Vpn>(uuid) {

            @Override
            protected Vpn implementQuery(final String uuid) throws ServiceException {
                OwnerInfoThreadLocal.setOwnerInfo(request, ServiceTypeEnum.L3VPN);
                return service.getDetail(uuid, request);
            }
        }.execute().getResult();
    }

    /**
     * Query L3vpn status info.<br>
     * 
     * @param uuid L3vpn id
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when query VPN failed
     * @since SDNO 0.5
     */
    @GET
    @Path("/{uuid}/status")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Vpn getStatus(@PathParam("uuid") final String uuid, @Context final HttpServletRequest request)
            throws ServiceException {
        return new SvcResourceQuerySingleExecutor<Vpn>(uuid) {

            @Override
            protected Vpn implementQuery(String uuid) throws ServiceException {
                OwnerInfoThreadLocal.setOwnerInfo(request, ServiceTypeEnum.L3VPN);
                Vpn vpn = service.getDetail(uuid, request);
                return service.getStatus(vpn, request);
            }
        }.execute().getResult();
    }

    /**
     * Add TP.<br>
     * 
     * @param l3vpnUuid L3Vpn UUID
     * @param request HttpServlet request
     * @return TP info
     * @throws ServiceException when add TP failed
     * @since SDNO 0.5
     */
    @POST
    @Path("/{uuid}/tps")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Tp addTp(@PathParam("uuid") final String l3vpnUuid, @Context final HttpServletRequest request)
            throws ServiceException {
        return new SvcResourceCreateExecutor<Tp, Tp>(request) {

            @Override
            protected Tp extractParam(@Context final HttpServletRequest request) throws ServiceException {
                return ROAInputStreamParser.fromJson(RestUtils.getRequestBody(request), Tp.class);
            }

            @Override
            protected Tp implementCreate(Tp tp) throws ServiceException {
                OwnerInfoThreadLocal.setOwnerInfo(request, ServiceTypeEnum.L3VPN);
                final Vpn vpn = service.getDetail(l3vpnUuid, request);
                return service.addTp(vpn, tp, request);
            }

            @Override
            protected void assertParam(Tp tp) throws ServiceException {
                checkScope(tp);
            }

        }.execute().getResult();
    }

    /**
     * Delete single TP.<br>
     * 
     * @param tpUuid TP UUID
     * @param vpnUuid L3vpn id
     * @param request HttpServlet request
     * @return TP info
     * @throws ServiceException when delete TP failed
     * @since SDNO 0.5
     */
    @DELETE
    @Path("/{vpnUuid}/tps/{tpUuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Tp deleteSingleTp(@PathParam("tpUuid") final String tpUuid, @PathParam("vpnUuid") final String vpnUuid,
            @Context final HttpServletRequest request) throws ServiceException {
        return new SvcResourceDeleteUUIDExecutor<Tp>(tpUuid) {

            @Override
            protected Tp implementDelete(String tpUuid) throws ServiceException {
                ExecutorUtils.assertUUID(tpUuid);
                ExecutorUtils.assertUUID(vpnUuid);
                OwnerInfoThreadLocal.setOwnerInfo(request, ServiceTypeEnum.L3VPN);
                final Vpn vpn = service.getDetail(vpnUuid, request);
                return service.deleteTp(vpn, tpUuid, request);
            }

        }.execute().getResult();
    }

    /**
     * Update L3vpn by uuid.<br>
     *
     * @param uuid uuid of a l3vpn you want to update
     * @param request HttpServletRequest request object to put some data
     * @return L3Vpn the new updated l3vpn model
     * @throws ServiceException if something wrong happens when update
     * @since SDNO 0.5
     */
    @PUT
    @Path("/{uuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Vpn updateDesc(@PathParam("uuid") final String uuid, @Context final HttpServletRequest request)
            throws ServiceException {
        return new SvcResourceUpdateExecutor<Vpn, Vpn>(request) {

            @Override
            protected Vpn extractParam(@Context final HttpServletRequest request) throws ServiceException {
                return ROAInputStreamParser.fromJson(RestUtils.getRequestBody(request), Vpn.class);
            }

            @Override
            protected void assertParam(final Vpn param) throws ServiceException {
                ExecutorUtils.assertUUID(uuid);
            }

            @Override
            protected Vpn implementUpdate(final Vpn param) throws ServiceException {
                OwnerInfoThreadLocal.setOwnerInfo(request, ServiceTypeEnum.L3VPN);
                param.setUuid(uuid);
                Vpn vpn = service.getDetail(uuid, request);
                if(null == vpn) {
                    ServiceExceptionUtil.throwNotFoundException();
                }
                if(null != vpn) {
                    vpn.setDescription(param.getDescription());
                }
                return service.modifyDesc(vpn, request);
            }
        }.execute().getResult();
    }

    /**
     * Query Te path.<br>
     * 
     * @param uuid L3vpn id
     * @param srcNeId source NE Id
     * @param destNeId destination NE Id
     * @param srcAcId source AC Id
     * @param destAcId destination AC Id
     * @param request request info
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    @GET
    @Path("/{uuid}/tepath")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BatchQueryResult<TePath> getTePath(@PathParam("uuid") final String uuid,
            @QueryParam("srcNeId") final String srcNeId, @QueryParam("destNeId") final String destNeId,
            @QueryParam("srcAcId") final String srcAcId, @QueryParam("destAcId") final String destAcId,
            @Context final HttpServletRequest request) throws ServiceException {
        return new SvcResourceQueryBatchExecutor<TePath>(request) {

            @Override
            protected void assembleParams(final BatchQueryParams batchQueryParams) throws ServiceException {
                UuidUtils.checkUuid(uuid);
                UuidUtils.checkUuid(srcAcId);
                UuidUtils.checkUuid(destAcId);
            }

            @Override
            protected BatchQueryResult<TePath> implementQuery(final BatchQueryParams batchQueryParams)
                    throws ServiceException {
                OwnerInfoThreadLocal.setOwnerInfo(request, ServiceTypeEnum.L3VPN);
                final Vpn vpn = service.getDetail(uuid, request);
                TePathQueryKey pathQueryKey = new TePathQueryKey(uuid, srcNeId, destNeId, srcAcId, destAcId);
                return service.getTePath(vpn, pathQueryKey, request);
            }

        }.execute().getResult();
    }

    private void checkScope(final SvcModel model) throws ServiceException {
        ScopeChecker.checkScope(model);
    }

    /**
     * @return Returns the l3VpnSvcService.
     */
    public L3VpnSvcService getL3VpnSvcService() {
        return service;
    }

    /**
     * @param l3VpnSvcService The l3VpnSvcService to set.
     */
    public void setL3VpnSvcService(L3VpnSvcService l3VpnSvcService) {
        this.service = l3VpnSvcService;
    }
}
