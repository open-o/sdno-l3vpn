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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.l3vpn.L3VpnPo;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;

public class DaoUtilTest {

    @Test
    public void addCollections() throws Exception {
        final ArrayList<String> list1 = new ArrayList<>();
        final ArrayList<String> list3 = new ArrayList<>(Collections.singleton(""));

        DaoUtil.addCollections(list3, list1);
        Assert.assertFalse(list1.isEmpty());
    }

    @Test
    public void addCollectionsWithNullInput() throws Exception {
        final ArrayList<String> list1 = new ArrayList<>();
        DaoUtil.addCollections(list1, null);
        assertTrue(list1.isEmpty());
    }

    @Test
    public void addCollectionsWith2NullInput() throws Exception {

        DaoUtil.addCollections(null, null);

    }

    @Test
    public void batchMoConvert() throws Exception {
        final Vpn vpn = new Vpn();
        final List<L3VpnPo> pos = DaoUtil.batchMoConvert(Collections.singletonList(vpn), L3VpnPo.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pos));
    }

    @Test()
    public void batchMoConvertWithException1() {
        MockUp<Class> mock = new MockUp<Class>() {

            @Mock
            public T newInstance() throws InstantiationException, IllegalAccessException {
                throw new InstantiationException();

            }
        };

        final Vpn vpn = new Vpn();

        assertNull(DaoUtil.batchMoConvert(Collections.singletonList(vpn), L3VpnPo.class));
        mock.tearDown();
    }

    @Test()
    public void batchMoConvertWithException2() {
        MockUp<Class> mock = new MockUp<Class>() {

            @Mock
            public T newInstance() throws InstantiationException, IllegalAccessException {
                throw new IllegalAccessException();

            }
        };

        final Vpn vpn = new Vpn();
        assertNull(DaoUtil.batchMoConvert(Collections.singletonList(vpn), L3VpnPo.class));
        mock.tearDown();
    }

    @Test
    public void batchMoConvert3() throws Exception {
        new Vpn();
        final List<L3VpnPo> pos = DaoUtil.batchMoConvert(null, L3VpnPo.class);
        Assert.assertTrue(CollectionUtils.isEmpty(pos));
    }

    @Test
    public void batchPoConvert() throws Exception {
        final List<Vpn> pos = DaoUtil.batchPoConvert(null, Vpn.class);
        Assert.assertTrue(CollectionUtils.isEmpty(pos));
    }

    @Test
    public void getFieldVal() throws Exception {
        final Vpn vpn = new Vpn();
        vpn.setId("133");

        final Object id = DaoUtil.getFieldVal(vpn, "id");
        Assert.assertNotNull(id);
    }

    @Test(expected = ServiceException.class)
    public void getFieldVal3() throws Exception {
        final Vpn vpn = new Vpn();
        vpn.setId("133");

        final Object id = DaoUtil.getFieldVal(vpn, "uuid");
        Assert.assertNotNull(id);
    }

    @Test
    public void getTableName() throws Exception {
        final String tableName = DaoUtil.getTableName(L3VpnPo.class);
        Assert.assertTrue(org.apache.commons.lang3.StringUtils.isNotEmpty(tableName));
    }

    @Test
    public void getUuids() throws Exception {
        final Vpn vpn = new Vpn();
        vpn.setUuid("123");
        final List<Vpn> list1 = new ArrayList<>();
        list1.add(vpn);
        assertEquals("123", DaoUtil.getUuids(list1).get(0));
    }

    @Test
    public void getUuidsWithEmptyInput() throws Exception {

        final List<Vpn> list1 = new ArrayList<>();

        assertTrue(DaoUtil.getUuids(list1).isEmpty());
    }

    @Test
    public void getPoModelUuids() throws Exception {
        L3VpnPo vpn = new L3VpnPo();
        vpn.setUuid("123");
        final List<L3VpnPo> list1 = new ArrayList<>();
        list1.add(vpn);
        assertEquals("123", DaoUtil.getPoModelUuids(list1).get(0));

    }

    @Test
    public void getPoModelUuidsWithEmptyInput() throws Exception {

        final List<L3VpnPo> list1 = new ArrayList<>();

        assertTrue(DaoUtil.getPoModelUuids(list1).isEmpty());

    }

    @Test
    public void resetUuidWithNullInput() throws Exception {
        assertNull(DaoUtil.resetUuid(null));
    }

    @Test
    public void resetUuid() throws Exception {
        final Vpn vpn = new Vpn();
        vpn.setUuid("123");
        String rst = DaoUtil.resetUuid(vpn);

        assertFalse("123".equals(rst));
    }

    @Test
    public void resetUuids() throws Exception {
        final Vpn vpn = new Vpn();
        final List<String> uuids = DaoUtil.resetUuids(Collections.singletonList(vpn));
        Assert.assertTrue(CollectionUtils.isNotEmpty(uuids));
    }

    @Test
    public void resetUuidsWithNullInput() throws Exception {
        final List<String> uuids = DaoUtil.resetUuids(null);
        assertTrue(uuids.isEmpty());
    }

    @Test
    public void safeListWithNullInput() throws Exception {

        assertTrue(DaoUtil.safeList(null).isEmpty());
    }

    @Test
    public void safeList() throws Exception {
        List<String> list = new ArrayList<>();
        assertTrue(DaoUtil.safeList(list).isEmpty());
    }

    @Test
    public void setUuidIfEmptyWithNullInput() throws Exception {
        assertTrue(DaoUtil.setUuidIfEmpty(null).isEmpty());
    }

    @Test
    public void setUuidIfEmpty() throws Exception {
        final Vpn vpn = new Vpn();
        final Vpn vpn2 = new Vpn();
        vpn.setUuid("123");
        final List<Vpn> list = new ArrayList<>();
        list.add(vpn);
        list.add(vpn2);
        assertEquals("123", DaoUtil.setUuidIfEmpty(list).get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitListInput0() throws Exception {
        DaoUtil.splitList(null, 0);
    }

    @Test
    public void splitListInputNull() throws Exception {
        assertTrue(DaoUtil.splitList(null, 1).isEmpty());
    }

    @Test
    public void splitList() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("1");
        assertEquals(1, DaoUtil.splitList(list, 1).size());
    }

    @Test
    public void splitList2() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        assertEquals(2, DaoUtil.splitList(list, 2).size());
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
            DaoUtil.updateSlaveMo(vpn, null, dao, null, null);
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
        MockUp<DaoUtil> mock2 = new MockUp<DaoUtil>() {

            @Mock
            public Object getFieldVal(Object target, String fieldName) throws ServiceException {

                return "2";
            }
        };

        final Vpn vpn = new Vpn();
        vpn.setUuid("1");
        PriDaoForTest dao = new PriDaoForTest();

        assertFalse(DaoUtil.updateSlaveMo(vpn, null, dao, dao, "Id"));

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
        MockUp<DaoUtil> mock2 = new MockUp<DaoUtil>() {

            @Mock
            public Object getFieldVal(Object target, String fieldName) throws ServiceException {

                return "";
            }
        };

        final Vpn vpn = new Vpn();
        vpn.setUuid("1");
        PriDaoForTest dao = new PriDaoForTest();

        assertTrue(DaoUtil.updateSlaveMo(vpn, null, dao, dao, "Id"));

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
        MockUp<DaoUtil> mock2 = new MockUp<DaoUtil>() {

            @Mock
            public Object getFieldVal(Object target, String fieldName) throws ServiceException {

                return "";
            }
        };

        final Vpn vpn = new Vpn();
        vpn.setUuid("1");
        PriDaoForTest dao = new PriDaoForTest();
        Vpn vpn2 = new Vpn();
        assertTrue(DaoUtil.updateSlaveMo(vpn, vpn2, dao, dao, "Id"));

        mock.tearDown();
        mock2.tearDown();
    }

    private class PriDaoForTest extends DefaultDao {

        /**
         * <br/>
         * 
         * @param pos
         * @return
         * @throws ServiceException
         * @since SDNO 0.5
         */
        @Override
        public List assembleMo(List pos) throws ServiceException {

            return null;
        }

        /**
         * <br/>
         * 
         * @param mos
         * @throws ServiceException
         * @since SDNO 0.5
         */
        @Override
        public void addMos(List mos) throws ServiceException {

        }

        /**
         * <br/>
         * 
         * @param mos
         * @return
         * @throws ServiceException
         * @since SDNO 0.5
         */
        @Override
        public boolean delMos(List mos) throws ServiceException {

            return false;
        }

        /**
         * <br/>
         * 
         * @param mos
         * @return
         * @throws ServiceException
         * @since SDNO 0.5
         */
        @Override
        public boolean updateMos(List mos) throws ServiceException {

            return false;
        }

        /**
         * <br/>
         * 
         * @return
         * @since SDNO 0.5
         */
        @Override
        protected Class getPoClass() {

            return null;
        }
    }
}
