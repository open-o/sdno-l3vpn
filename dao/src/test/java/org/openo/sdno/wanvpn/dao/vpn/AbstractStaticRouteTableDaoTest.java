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
import org.openo.sdno.model.db.l3vpn.L3VpnStaticRouteTablePo;
import org.openo.sdno.model.servicemodel.routeprotocol.StaticRouteTable;
import org.openo.sdno.wanvpn.dao.AbstractDao;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.vpn.AbstractStaticRouteTableDao;

import mockit.Mock;
import mockit.MockUp;

public class AbstractStaticRouteTableDaoTest {

    public static class L3VpnStaticRouteTableDao extends AbstractStaticRouteTableDao {

        @Override
        protected Class getPoClass() {
            return L3VpnStaticRouteTablePo.class;
        }
    }

    private final AbstractStaticRouteTableDao dao = new L3VpnStaticRouteTableDao();

    @Before
    public void setUp() {
        new MockUp<DaoUtil>() {

            @Mock
            public List<L3VpnStaticRouteTablePo> batchMoConvert(List<StaticRouteTable> mos,
                    Class<L3VpnStaticRouteTablePo> poClass) {
                L3VpnStaticRouteTablePo po = new L3VpnStaticRouteTablePo();
                return Collections.singletonList(po);
            }

            @Mock
            public List<String> getUuids(List<StaticRouteTable> mos) {
                return Collections.singletonList("uuid");
            }

            @Mock
            public List<StaticRouteTable> batchPoConvert(List<L3VpnStaticRouteTablePo> pos,
                    Class<StaticRouteTable> moClass) {
                StaticRouteTable mo = new StaticRouteTable();
                return Collections.singletonList(mo);
            }
        };

        new MockUp<AbstractDao>() {

            @Mock
            public List<String> insert(final List<L3VpnStaticRouteTablePo> pos) throws ServiceException {
                return Collections.singletonList("success");
            }

            @Mock
            public boolean delete(final List<String> poIds) throws ServiceException {
                return true;
            }

            @Mock
            public boolean update(final List<L3VpnStaticRouteTablePo> pos) throws ServiceException {
                return true;
            }
        };
    }

    @Test
    public void testAddMos() throws ServiceException {
        List<StaticRouteTable> mos = new ArrayList();
        mos.add(new StaticRouteTable());
        dao.addMos(mos);
    }

    @Test
    public void testDelMos() throws ServiceException {
        List<StaticRouteTable> mos = new ArrayList();
        mos.add(new StaticRouteTable());
        dao.delMos(mos);
    }

    @Test
    public void testUpdateMos() throws ServiceException {
        List<StaticRouteTable> mos = new ArrayList();
        mos.add(new StaticRouteTable());
        dao.updateMos(mos);
    }

    @Test
    public void testAssembleMo() throws ServiceException {
        List<L3VpnStaticRouteTablePo> pos = new ArrayList();
        pos.add(new L3VpnStaticRouteTablePo());
        dao.assembleMo(pos);
    }
}
