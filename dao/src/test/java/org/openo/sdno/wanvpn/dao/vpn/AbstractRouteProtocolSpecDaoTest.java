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

package org.openo.sdno.wanvpn.dao.vpn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.db.l3vpn.L3VpnRouteProtocolSpecPo;
import org.openo.sdno.model.db.l3vpn.L3VpnStaticRouteTablePo;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.routeprotocol.StaticRouteTable;
import org.openo.sdno.wanvpn.dao.AbstractDao;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractRouteProtocolSpecDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractStaticRouteTableDao;

import mockit.Mock;
import mockit.MockUp;

public class AbstractRouteProtocolSpecDaoTest {

    static class L3VpnRouteProtocolSpecDao extends AbstractRouteProtocolSpecDao {

        @Override
        protected Class getPoClass() {
            return L3VpnRouteProtocolSpecPo.class;
        }
    }

    public static class L3VpnStaticRouteTableDao extends AbstractStaticRouteTableDao {

        @Override
        protected Class getPoClass() {
            return L3VpnStaticRouteTablePo.class;
        }
    }

    private final AbstractRouteProtocolSpecDao dao = new L3VpnRouteProtocolSpecDao();

    @Before
    public void setUp() {
        dao.setStaticRouteTableDao(new L3VpnStaticRouteTableDao());

        new MockUp<AbstractStaticRouteTableDao>() {

            @Mock
            public List<StaticRouteTable> getMoByIds(final List<String> uuids) throws ServiceException {
                StaticRouteTable route = new StaticRouteTable();
                route.setUuid("staticid");
                return Collections.singletonList(route);
            }

            @Mock
            public void addMos(List<StaticRouteTable> mos) throws ServiceException {
                return;
            }

            @Mock
            public boolean delMos(List<StaticRouteTable> mos) throws ServiceException {
                return true;
            }
        };

        new MockUp<DaoUtil>() {

            @Mock
            public List<L3VpnRouteProtocolSpecPo> batchMoConvert(List<RouteProtocolSpec> mos,
                    Class<L3VpnRouteProtocolSpecPo> poClass) {
                L3VpnRouteProtocolSpecPo po = new L3VpnRouteProtocolSpecPo();
                return Collections.singletonList(po);
            }

            @Mock
            public List<String> getUuids(List<RouteProtocolSpec> mos) {
                return Collections.singletonList("uuid");
            }

            @Mock
            public List<RouteProtocolSpec> batchPoConvert(List<L3VpnRouteProtocolSpecPo> pos,
                    Class<RouteProtocolSpec> moClass) {
                RouteProtocolSpec mo = new RouteProtocolSpec();
                return Collections.singletonList(mo);
            }

            @Mock
            public <Mast extends SvcModel, Slave extends SvcModel, MastPo extends PoModel<Mast>, SlavePo extends PoModel<Slave>>
                    boolean updateSlaveMo(Mast mastMo, Slave slaveMo, DefaultDao<MastPo, Mast> mastDao,
                            DefaultDao<SlavePo, Slave> slaveDao, String slaveIdName) throws ServiceException {
                return true;
            }
        };

        new MockUp<AbstractDao>() {

            @Mock
            public List<String> insert(final List<L3VpnRouteProtocolSpecPo> pos) throws ServiceException {
                return Collections.singletonList("success");
            }

            @Mock
            public boolean delete(final List<String> poIds) throws ServiceException {
                return true;
            }

            @Mock
            public boolean update(final List<L3VpnRouteProtocolSpecPo> pos) throws ServiceException {
                return true;
            }
        };
    }

    @Test
    public void testAddMos() throws ServiceException {
        List<RouteProtocolSpec> mos = new ArrayList();
        RouteProtocolSpec route = new RouteProtocolSpec();
        route.setStaticRoute(new StaticRouteTable());
        mos.add(route);
        dao.addMos(mos);
    }

    @Test
    public void testDelMos() throws ServiceException {
        List<RouteProtocolSpec> mos = new ArrayList();
        RouteProtocolSpec route = new RouteProtocolSpec();
        route.setStaticRoute(new StaticRouteTable());
        mos.add(route);
        dao.delMos(mos);
    }

    @Test
    public void testUpdateMos() throws ServiceException {
        List<RouteProtocolSpec> mos = new ArrayList();
        RouteProtocolSpec route = new RouteProtocolSpec();
        route.setStaticRoute(new StaticRouteTable());
        mos.add(route);
        dao.updateMos(mos);
    }

    @Test
    public void testAssembleMo() throws ServiceException {
        List<L3VpnRouteProtocolSpecPo> pos = new ArrayList();
        L3VpnRouteProtocolSpecPo route = new L3VpnRouteProtocolSpecPo();
        route.setBgpRouteId("bgpid");
        route.setStaticRouteId("staticid");
        pos.add(route);
        dao.assembleMo(pos);
    }
}
