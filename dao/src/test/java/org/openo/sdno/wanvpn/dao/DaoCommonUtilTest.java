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

package org.openo.sdno.wanvpn.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Assert;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.l3vpn.L3VpnPo;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

import mockit.Mock;
import mockit.MockUp;

public class DaoCommonUtilTest {

    @Test
    public void safeListWithNullInput() throws Exception {

        assertTrue(DaoCommonUtil.safeList(null).isEmpty());
    }

    @Test
    public void safeList() throws Exception {
        List<String> list = new ArrayList<>();
        assertTrue(DaoCommonUtil.safeList(list).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitListInput0() throws Exception {
        DaoCommonUtil.splitList(null, 0);
    }

    @Test
    public void splitListInputNull() throws Exception {
        assertTrue(DaoCommonUtil.splitList(null, 1).isEmpty());
    }

    @Test
    public void splitList() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("1");
        assertEquals(1, DaoCommonUtil.splitList(list, 1).size());
    }

    @Test
    public void splitList2() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        assertEquals(2, DaoCommonUtil.splitList(list, 2).size());
    }

    @Test
    public void getFieldVal() throws Exception {
        final Vpn vpn = new Vpn();
        vpn.setId("133");

        final Object id = DaoCommonUtil.getFieldVal(vpn, "id");
        Assert.assertNotNull(id);
    }

    @Test(expected = ServiceException.class)
    public void getFieldVal3() throws Exception {
        final Vpn vpn = new Vpn();
        vpn.setId("133");

        final Object id = DaoCommonUtil.getFieldVal(vpn, "uuid");
        Assert.assertNotNull(id);
    }

    @Test
    public void updateSlaveMoWithNullInput() throws Exception {
        MockUp<PriDaoForTest> mock = new MockUp<PriDaoForTest>() {

            @Mock
            public T selectById(final String uuid) throws ServiceException {

                return null;
            }
        };

        final Vpn vpn = new Vpn();
        vpn.setUuid("1");
        PriDaoForTest dao = new PriDaoForTest();

        try {
            DaoCommonUtil.updateSlaveMo(vpn, null, dao, null, null);
            assertFalse(true);
        } catch(ServiceException e) {
            assertTrue(true);
        } finally {
            mock.tearDown();
        }
    }

    @Test
    public void updateSlaveMo() throws Exception {
        MockUp<PriDaoForTest> mock = new MockUp<PriDaoForTest>() {

            @Mock
            public L3VpnPo selectById(final String uuid) throws ServiceException {
                L3VpnPo vpn = new L3VpnPo();
                vpn.setId("2");
                return vpn;
            }
        };
        MockUp<DaoCommonUtil> mock2 = new MockUp<DaoCommonUtil>() {

            @Mock
            public Object getFieldVal(Object target, String fieldName) throws ServiceException {

                return "2";
            }
        };

        final Vpn vpn = new Vpn();
        vpn.setUuid("1");
        PriDaoForTest dao = new PriDaoForTest();

        assertFalse(DaoCommonUtil.updateSlaveMo(vpn, null, dao, dao, "Id"));

        mock.tearDown();
        mock2.tearDown();
    }

    @Test
    public void updateSlaveMo2() throws Exception {
        MockUp<PriDaoForTest> mock = new MockUp<PriDaoForTest>() {

            @Mock
            public L3VpnPo selectById(final String uuid) throws ServiceException {
                L3VpnPo vpn = new L3VpnPo();
                vpn.setId("2");
                return vpn;
            }
        };
        MockUp<DaoCommonUtil> mock2 = new MockUp<DaoCommonUtil>() {

            @Mock
            public Object getFieldVal(Object target, String fieldName) throws ServiceException {

                return "";
            }
        };

        final Vpn vpn = new Vpn();
        vpn.setUuid("1");
        PriDaoForTest dao = new PriDaoForTest();

        assertTrue(DaoCommonUtil.updateSlaveMo(vpn, null, dao, dao, "Id"));

        mock.tearDown();
        mock2.tearDown();
    }

    @Test
    public void updateSlaveMo3() throws Exception {
        MockUp<PriDaoForTest> mock = new MockUp<PriDaoForTest>() {

            @Mock
            public L3VpnPo selectById(final String uuid) throws ServiceException {
                L3VpnPo vpn = new L3VpnPo();
                vpn.setId("2");
                return vpn;
            }
        };
        MockUp<DaoCommonUtil> mock2 = new MockUp<DaoCommonUtil>() {

            @Mock
            public Object getFieldVal(Object target, String fieldName) throws ServiceException {

                return "";
            }
        };

        final Vpn vpn = new Vpn();
        vpn.setUuid("1");
        PriDaoForTest dao = new PriDaoForTest();
        Vpn vpn2 = new Vpn();
        assertTrue(DaoCommonUtil.updateSlaveMo(vpn, vpn2, dao, dao, "Id"));

        mock.tearDown();
        mock2.tearDown();
    }

    private class PriDaoForTest extends DefaultDao {

        @Override
        public List assembleMo(List pos) throws ServiceException {

            return null;
        }

        @Override
        public void addMos(List mos) throws ServiceException {

        }

        @Override
        public boolean delMos(List mos) throws ServiceException {

            return false;
        }

        @Override
        public boolean updateMos(List mos) throws ServiceException {

            return false;
        }

        @Override
        protected Class getPoClass() {

            return null;
        }
    }
}
