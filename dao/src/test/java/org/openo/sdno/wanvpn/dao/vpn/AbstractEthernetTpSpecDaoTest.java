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
import org.openo.sdno.model.db.l3vpn.L3VpnEthernetTpSpecPo;
import org.openo.sdno.model.servicemodel.tp.EthernetTpSpec;
import org.openo.sdno.wanvpn.dao.AbstractDao;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.vpn.AbstractEthernetTpSpecDao;

import mockit.Mock;
import mockit.MockUp;

public class AbstractEthernetTpSpecDaoTest {

    public static class L3VpnEthernetTpSpecDao extends AbstractEthernetTpSpecDao {

        @Override
        protected Class getPoClass() {
            return L3VpnEthernetTpSpecPo.class;
        }
    }

    private final AbstractEthernetTpSpecDao dao = new L3VpnEthernetTpSpecDao();

    @Before
    public void setUp() {
        new MockUp<DaoUtil>() {

            @Mock
            public List<L3VpnEthernetTpSpecPo> batchMoConvert(List<EthernetTpSpec> mos,
                    Class<L3VpnEthernetTpSpecPo> poClass) {
                L3VpnEthernetTpSpecPo po = new L3VpnEthernetTpSpecPo();
                return Collections.singletonList(po);
            }

            @Mock
            public List<String> getUuids(List<EthernetTpSpec> mos) {
                return Collections.singletonList("uuid");
            }

            @Mock
            public List<EthernetTpSpec> batchPoConvert(List<L3VpnEthernetTpSpecPo> pos, Class<EthernetTpSpec> moClass) {
                EthernetTpSpec mo = new EthernetTpSpec();
                return Collections.singletonList(mo);
            }
        };

        new MockUp<AbstractDao>() {

            @Mock
            public List<String> insert(final List<L3VpnEthernetTpSpecPo> pos) throws ServiceException {
                return Collections.singletonList("success");
            }

            @Mock
            public boolean delete(final List<String> poIds) throws ServiceException {
                return true;
            }

            @Mock
            public boolean update(final List<L3VpnEthernetTpSpecPo> pos) throws ServiceException {
                return true;
            }
        };
    }

    @Test
    public void testAddMos() throws ServiceException {
        List<EthernetTpSpec> mos = new ArrayList<EthernetTpSpec>();
        mos.add(new EthernetTpSpec());
        dao.addMos(mos);
    }

    @Test
    public void testDelMos() throws ServiceException {
        List<EthernetTpSpec> mos = new ArrayList<EthernetTpSpec>();
        mos.add(new EthernetTpSpec());
        dao.delMos(mos);
    }

    @Test
    public void testUpdateMos() throws ServiceException {
        List<EthernetTpSpec> mos = new ArrayList<EthernetTpSpec>();
        mos.add(new EthernetTpSpec());
        dao.updateMos(mos);
    }

    @Test
    public void testAssembleMo() throws ServiceException {
        List<L3VpnEthernetTpSpecPo> pos = new ArrayList<L3VpnEthernetTpSpecPo>();
        pos.add(new L3VpnEthernetTpSpecPo());
        dao.assembleMo(pos);
    }
}
