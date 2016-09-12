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
import org.openo.sdno.model.db.l3vpn.L3VpnCeTpPo;
import org.openo.sdno.model.servicemodel.tp.CeTp;
import org.openo.sdno.wanvpn.dao.AbstractDao;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.vpn.AbstractCeTpDao;

import mockit.Mock;
import mockit.MockUp;

public class AbstractCeTpDaoTest {

    public static class L3VpnCeTpDao extends AbstractCeTpDao {

        @Override
        protected Class getPoClass() {
            return L3VpnCeTpPo.class;
        }
    };

    private final AbstractCeTpDao dao = new L3VpnCeTpDao();

    @Before
    public void setUp() {
        new MockUp<DaoUtil>() {

            @Mock
            public List<L3VpnCeTpPo> batchMoConvert(List<CeTp> mos, Class<L3VpnCeTpPo> poClass) {
                L3VpnCeTpPo po = new L3VpnCeTpPo();
                return Collections.singletonList(po);
            }

            @Mock
            public List<String> getUuids(List<CeTp> mos) {
                return Collections.singletonList("uuid");
            }

            @Mock
            public List<CeTp> batchPoConvert(List<L3VpnCeTpPo> pos, Class<CeTp> moClass) {
                CeTp mo = new CeTp();
                return Collections.singletonList(mo);
            }
        };

        new MockUp<AbstractDao>() {

            @Mock
            public List<String> insert(final List<L3VpnCeTpPo> pos) throws ServiceException {
                return Collections.singletonList("success");
            }

            @Mock
            public boolean delete(final List<String> poIds) throws ServiceException {
                return true;
            }

            @Mock
            public boolean update(final List<L3VpnCeTpPo> pos) throws ServiceException {
                return true;
            }
        };
    }

    @Test
    public void testDeleteById() throws ServiceException {
        dao.deleteById("uuid");
    }

    @Test
    public void testAddMos() throws ServiceException {
        List<CeTp> mos = new ArrayList<CeTp>();
        mos.add(new CeTp());
        dao.addMos(mos);
    }

    @Test
    public void testDelMos() throws ServiceException {
        List<CeTp> mos = new ArrayList<CeTp>();
        mos.add(new CeTp());
        dao.delMos(mos);
    }

    @Test
    public void testUpdateMos() throws ServiceException {
        List<CeTp> mos = new ArrayList<CeTp>();
        mos.add(new CeTp());
        dao.updateMos(mos);
    }

    @Test
    public void testAssembleMo() throws ServiceException {
        List<L3VpnCeTpPo> pos = new ArrayList<L3VpnCeTpPo>();
        pos.add(new L3VpnCeTpPo());
        dao.assembleMo(pos);
    }
}
