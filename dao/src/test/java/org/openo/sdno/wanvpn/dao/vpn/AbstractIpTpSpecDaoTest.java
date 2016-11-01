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
import org.openo.sdno.model.db.l3vpn.L3VpnIpTpSpecPo;
import org.openo.sdno.model.servicemodel.tp.IpTpSpec;
import org.openo.sdno.wanvpn.dao.AbstractDao;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.vpn.AbstractIpTpSpecDao;

import mockit.Mock;
import mockit.MockUp;

public class AbstractIpTpSpecDaoTest {

    public static class L3VpnIpTpSpecDao extends AbstractIpTpSpecDao {

        @Override
        protected Class getPoClass() {
            return L3VpnIpTpSpecPo.class;
        }
    }

    private final AbstractIpTpSpecDao dao = new L3VpnIpTpSpecDao();

    @Before
    public void setUp() {
        new MockUp<DaoUtil>() {

            @Mock
            public List<L3VpnIpTpSpecPo> batchMoConvert(List<IpTpSpec> mos, Class<L3VpnIpTpSpecPo> poClass) {
                L3VpnIpTpSpecPo po = new L3VpnIpTpSpecPo();
                return Collections.singletonList(po);
            }

            @Mock
            public List<String> getUuids(List<IpTpSpec> mos) {
                return Collections.singletonList("uuid");
            }

            @Mock
            public List<IpTpSpec> batchPoConvert(List<L3VpnIpTpSpecPo> pos, Class<IpTpSpec> moClass) {
                IpTpSpec mo = new IpTpSpec();
                return Collections.singletonList(mo);
            }
        };

        new MockUp<AbstractDao>() {

            @Mock
            public List<String> insert(final List<L3VpnIpTpSpecPo> pos) throws ServiceException {
                return Collections.singletonList("success");
            }

            @Mock
            public boolean delete(final List<String> poIds) throws ServiceException {
                return true;
            }

            @Mock
            public boolean update(final List<L3VpnIpTpSpecPo> pos) throws ServiceException {
                return true;
            }
        };
    }

    @Test
    public void testAddMos() throws ServiceException {
        List<IpTpSpec> mos = new ArrayList<IpTpSpec>();
        mos.add(new IpTpSpec());
        dao.addMos(mos);
    }

    @Test
    public void testDelMos() throws ServiceException {
        List<IpTpSpec> mos = new ArrayList<IpTpSpec>();
        mos.add(new IpTpSpec());
        dao.delMos(mos);
    }

    @Test
    public void testUpdateMos() throws ServiceException {
        List<IpTpSpec> mos = new ArrayList<IpTpSpec>();
        mos.add(new IpTpSpec());
        dao.updateMos(mos);
    }

    @Test
    public void testAssembleMo() throws ServiceException {
        List<L3VpnIpTpSpecPo> pos = new ArrayList<L3VpnIpTpSpecPo>();
        pos.add(new L3VpnIpTpSpecPo());
        dao.assembleMo(pos);
    }
}
