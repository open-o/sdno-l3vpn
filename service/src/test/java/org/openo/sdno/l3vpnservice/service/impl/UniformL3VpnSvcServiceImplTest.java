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

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnCreateSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnDeleteSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnModifySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQuerySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQueryTePathService;
import org.openo.sdno.l3vpnservice.service.inf.L3vpnTpAddSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3vpnTpDeleteSvcService;
import org.openo.sdno.wanvpn.util.query.BatchQueryResult;
import org.openo.sdno.model.servicemodel.tepath.TePath;
import org.openo.sdno.model.servicemodel.tepath.TePathQueryKey;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.VpnOperStatus;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class UniformL3VpnSvcServiceImplTest {

    @Mocked
    private @Context HttpServletRequest httpServletRequest;

    private final UniformL3VpnSvcServiceImpl service = new UniformL3VpnSvcServiceImpl();

    private final static String tpId = "123";

    @Before
    public void setUp() {
        new MockUp<L3VpnTpDao>() {

            @Mock
            public void addMos(List<Vpn> mos) throws ServiceException {
            }

            @Mock
            public Tp getMoById(String tpId) throws ServiceException, IOException {
                String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
                final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
                Tp tp = vpn.getAccessPointList().get(0);
                return tp;
            }

            @Mock
            public boolean delMos(List<Vpn> mos) throws ServiceException {
                return true;
            }
        };
        service.setL3VpnActiveSvcService(new UniformL3VpnActiveSvcServiceImpl() {

            @Override
            public Vpn active(final Vpn vpn, final @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return vpn;
            }

            @Override
            public Vpn deactive(final Vpn vpn, final @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return vpn;
            }

            @Override
            public Tp activeSite(Vpn vpn, String tpId, @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return new Tp();
            }

            @Override
            public Tp deactiveSite(Vpn vpn, String tpId, @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return new Tp();
            }
        });

        service.setL3vpnTpAddSvcService(new L3vpnTpAddSvcService() {

            @Override
            public Tp addTp(final Vpn vpn, final Tp tp, final @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return tp;
            }

        });
        service.setL3VpnCreateSvcService(new L3VpnCreateSvcService() {

            @Override
            public Vpn create(final VpnVo vpnVo, final @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return vpnVo.getVpn();
            }
        });

        service.setL3VpnDeleteSvcService(new L3VpnDeleteSvcService() {

            @Override
            public Vpn delete(final Vpn vpn, final @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return vpn;
            }
        });

        service.setL3VpnModifySvcService(new L3VpnModifySvcService() {

            @Override
            public Vpn modifyDesc(final Vpn vpn, final @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return vpn;
            }
        });

        service.setL3VpnQuerySvcService(new L3VpnQuerySvcService() {

            @Override
            public Vpn getDetail(final String uuid, final @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                final Vpn vpn = new Vpn();
                vpn.setId(uuid);
                return vpn;
            }

            @Override
            public Vpn getStatus(final Vpn vpn, final @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return vpn;
            }

            @Override
            public VpnOperStatus getOperStatus(String vpnId, String ctrluuid,
                    @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return null;
            }

            @Override
            public Vpn getL3vpnAdminStatus(Vpn vpn, String ctrluuid, @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return null;
            }
        });

        service.setL3VpnTpDeleteSvcService(new L3vpnTpDeleteSvcService() {

            @Override
            public Tp deleteTp(final Vpn vpn, final String tpuuid, final @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return new Tp();
            }
        });

        service.setL3VpnQueryTePathService(new L3VpnQueryTePathService() {

            @Override
            public BatchQueryResult<TePath> getTePath(final Vpn vpn, final TePathQueryKey queryKey,
                    final @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return new BatchQueryResult();
            }
        });
    }

    @Test
    public void active() throws Exception {
        final Vpn vpn = new Vpn();
        final Vpn vpn1 = service.active(vpn, httpServletRequest);
        Assert.assertNotNull(vpn1);
    }

    @Test
    public void create() throws Exception {
        final Vpn vpn = new Vpn();
        final Vpn vpn1 = service.create(new VpnVo(vpn), httpServletRequest);
        Assert.assertNotNull(vpn1);
    }

    @Test
    public void deactive() throws Exception {
        final Vpn vpn = new Vpn();
        final Vpn vpn1 = service.deactive(vpn, httpServletRequest);
        Assert.assertNotNull(vpn1);
    }

    @Test
    public void delete() throws Exception {
        final Vpn vpn = new Vpn();
        final Vpn vpn1 = service.delete(vpn, httpServletRequest);
        Assert.assertNotNull(vpn1);
    }

    @Test
    public void getDetail() throws Exception {
        final Vpn vpn1 = service.getDetail("", httpServletRequest);
        Assert.assertNotNull(vpn1);
    }

    @Test
    public void getStatus() throws Exception {
        final Vpn vpn = new Vpn();
        final Vpn vpn1 = service.getStatus(vpn, httpServletRequest);
        Assert.assertNotNull(vpn1);
    }

    @Test
    public void modifyDesc() throws Exception {
        final Vpn vpn = new Vpn();
        final Vpn vpn1 = service.modifyDesc(vpn, httpServletRequest);
        Assert.assertNotNull(vpn1);
    }

    @Test
    public void activeSite() throws Exception {
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);

        String filePath_2 = new File("src/test/resources/tp.json").getCanonicalPath();
        final Tp tp = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath_2), Tp.class);

        new MockUp<L3VpnTpDao>() {

            @Mock
            public Tp getMoById(String tpId) throws ServiceException {
                return tp;
            }
        };

        final Tp activeTp = service.activeSite(vpn, tp.getId(), httpServletRequest);
        Assert.assertNotNull(activeTp);
    }

    @Test
    public void deactiveSite() throws Exception {
        final Vpn vpn = new Vpn();
        final Tp tp = service.deactiveSite(vpn, tpId, httpServletRequest);
        Assert.assertNotNull(tp);
    }

    @Test
    public void addTp() throws Exception {
        final Vpn vpn = new Vpn();
        final Tp tp = service.addTp(vpn, new Tp(), httpServletRequest);
        Assert.assertNotNull(tp);
    }

    @Test
    public void deleteTp() throws Exception {
        final Vpn vpn = new Vpn();
        final Tp tp = service.deleteTp(vpn, "", httpServletRequest);
        Assert.assertNotNull(tp);
    }

    @Test
    public void getTePath() throws ServiceException {
        final Vpn vpn = new Vpn();
        service.getTePath(vpn, new TePathQueryKey("", "", "", "", ""), httpServletRequest);
    }
}
