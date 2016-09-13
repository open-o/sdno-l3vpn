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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.l3vpn.L3VpnPo;
import org.openo.sdno.model.servicemodel.mss.QueryComplexParams;
import org.openo.sdno.wanvpn.dao.AbstractDao;
import org.openo.sdno.wanvpn.inventory.sdk.common.ErrorCode;
import org.openo.sdno.wanvpn.inventory.sdk.impl.PuerInvDAOImpl;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;

import mockit.Mock;
import mockit.MockUp;

/**
 * AbstractDaoTest class.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 3, 2016
 */
public class AbstractDaoTest {

    public static class TestDao extends AbstractDao {

        @Override
        protected Class getPoClass() {
            return TestDao.class;
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
    };

    private final AbstractDao dao = new TestDao();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSelectAllWithEmpty() throws ServiceException, CloneNotSupportedException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<String>> queryAll(final Class moType, final QueryComplexParams params)
                    throws ServiceException, CloneNotSupportedException {
                ResultRsp<List<String>> rst = new ResultRsp();
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);
        List<String> list = dao.selectAll();
        assertTrue(list.isEmpty());

        mock.tearDown();
    }

    @Test
    public void testSelectAll() throws ServiceException, CloneNotSupportedException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<String>> queryAll(final Class moType, final QueryComplexParams params)
                    throws ServiceException, CloneNotSupportedException {
                ResultRsp<List<String>> rst = new ResultRsp();
                List<String> data = new ArrayList<>();
                data.add("123");
                rst.setData(data);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);
        List<String> list = dao.selectAll();
        assertEquals("123", list.get(0));

        mock.tearDown();
    }

    @Test(expected = ServiceException.class)
    public void testSelectReturnFail() throws ServiceException, CloneNotSupportedException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<String>> queryAll(final Class moType, final QueryComplexParams params)
                    throws ServiceException, CloneNotSupportedException {
                ResultRsp<List<String>> rst = new ResultRsp();
                List<String> data = new ArrayList<>();
                data.add("123");
                rst.setData(data);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_FAILED);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);
        List<String> list = dao.selectAll();

        mock.tearDown();
    }

    @Test
    public void testSelectByIdInputEmpty() throws ServiceException {
        assertNull(dao.selectById(null));
    }

    @Test
    public void testSelectById() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<L3VpnPo> query(String uuid, Class<?> moType) throws ServiceException {
                ResultRsp<L3VpnPo> rst = new ResultRsp();
                L3VpnPo vpn = new L3VpnPo();
                vpn.setUuid("123");
                rst.setData(vpn);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        assertEquals("123", dao.selectById("uuid").getUuid());

        mock.tearDown();
    }

    @Test
    public void testSelectByIdReturnFail() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<L3VpnPo> query(String uuid, Class<?> moType) throws ServiceException {
                ResultRsp<L3VpnPo> rst = new ResultRsp();
                L3VpnPo vpn = new L3VpnPo();
                vpn.setUuid("123");
                rst.setData(vpn);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_FAILED);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        assertNull(dao.selectById("uuid"));

        mock.tearDown();
    }

    @Test
    public void testSelectByFiniteConditionsWithEmptyInput() throws ServiceException {
        List<String> list = new ArrayList<>();

        assertTrue(dao.selectByFiniteConditions("fieldName", list).isEmpty());
    }

    @Test
    public void testSelectByFiniteConditionsReturnFail() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<L3VpnPo>> queryComplex(Class<?> moType, QueryComplexParams params)
                    throws ServiceException {
                ResultRsp<List<L3VpnPo>> rst = new ResultRsp();
                List<L3VpnPo> list = new ArrayList<>();
                L3VpnPo vpn = new L3VpnPo();
                vpn.setUuid("123");
                list.add(vpn);
                rst.setData(list);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_FAILED);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<String> list = new ArrayList<>();
        list.add("fieldVal");
        try {
            dao.selectByFiniteConditions("fieldName", list);
            assertFalse(true);
        } catch(ServiceException e) {
            assertTrue(true);
        } finally {
            mock.tearDown();
        }
    }

    @Test
    public void testSelectByFiniteConditionsReturnEmpty() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<L3VpnPo>> queryComplex(Class<?> moType, QueryComplexParams params)
                    throws ServiceException {
                ResultRsp<List<L3VpnPo>> rst = new ResultRsp();

                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<String> list = new ArrayList<>();
        list.add("fieldVal");
        assertTrue(dao.selectByFiniteConditions("fieldName", list).isEmpty());

        mock.tearDown();

    }

    @Test
    public void testSelectByFiniteConditions() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<L3VpnPo>> queryComplex(Class<?> moType, QueryComplexParams params)
                    throws ServiceException {
                ResultRsp<List<L3VpnPo>> rst = new ResultRsp();
                List<L3VpnPo> list = new ArrayList<>();
                L3VpnPo vpn = new L3VpnPo();
                vpn.setUuid("123");
                list.add(vpn);
                rst.setData(list);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<String> list = new ArrayList<>();
        list.add("fieldVal");
        L3VpnPo rst = (L3VpnPo)dao.selectByFiniteConditions("fieldName", list).get(0);
        assertEquals("123", rst.getUuid());

        mock.tearDown();

    }

    @Test
    public void testQueryComplexReturnFail() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<L3VpnPo>> queryComplex(Class<?> moType, QueryComplexParams params)
                    throws ServiceException {
                ResultRsp<List<L3VpnPo>> rst = new ResultRsp();
                List<L3VpnPo> list = new ArrayList<>();
                L3VpnPo vpn = new L3VpnPo();
                vpn.setUuid("123");
                list.add(vpn);
                rst.setData(list);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_FAILED);
                return rst;
            }
        };

        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);
        try {
            dao.queryComplex(new QueryComplexParams());
            assertFalse(true);
        } catch(ServiceException e) {
            assertTrue(true);
        } finally {
            mock.tearDown();
        }
    }

    @Test
    public void testQueryComplexReturnEmpty() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<L3VpnPo>> queryComplex(Class<?> moType, QueryComplexParams params)
                    throws ServiceException {
                ResultRsp<List<L3VpnPo>> rst = new ResultRsp();

                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };

        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        assertTrue(dao.queryComplex(new QueryComplexParams()).isEmpty());

        mock.tearDown();

    }

    @Test
    public void testQueryComplex() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<String>> queryComplex(Class<?> moType, QueryComplexParams params)
                    throws ServiceException {
                ResultRsp<List<String>> rst = new ResultRsp();
                List<String> list = new ArrayList<>();

                list.add("123");
                rst.setData(list);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };

        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        assertEquals("123", dao.queryComplex(new QueryComplexParams()).get(0));

        mock.tearDown();

    }

    @Test
    public void testSelectByFiniteIdsWithEmptyInput() throws ServiceException {
        List<String> list = new ArrayList<>();
        assertTrue(dao.selectByFiniteIds(list).isEmpty());
    }

    @Test
    public void testSelectByFiniteIdsReturnFail() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<L3VpnPo>> queryComplex(Class<?> moType, QueryComplexParams params)
                    throws ServiceException {
                ResultRsp<List<L3VpnPo>> rst = new ResultRsp();
                List<L3VpnPo> list = new ArrayList<>();
                L3VpnPo vpn = new L3VpnPo();
                vpn.setUuid("123");
                list.add(vpn);
                rst.setData(list);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_FAILED);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<String> list = new ArrayList<>();
        list.add("uuid");

        try {
            dao.selectByFiniteIds(list);
            assertFalse(true);
        } catch(ServiceException e) {
            assertTrue(true);
        } finally {
            mock.tearDown();
        }
    }

    @Test
    public void testSelectByFiniteIdsReturnEmpty() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<L3VpnPo>> queryComplex(Class<?> moType, QueryComplexParams params)
                    throws ServiceException {
                ResultRsp<List<L3VpnPo>> rst = new ResultRsp();

                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<String> list = new ArrayList<>();
        list.add("uuid");

        assertTrue(dao.selectByFiniteIds(list).isEmpty());

        mock.tearDown();

    }

    @Test
    public void testSelectByFiniteIds() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<L3VpnPo>> queryComplex(Class<?> moType, QueryComplexParams params)
                    throws ServiceException {
                ResultRsp<List<L3VpnPo>> rst = new ResultRsp();
                List<L3VpnPo> list = new ArrayList<>();
                L3VpnPo vpn = new L3VpnPo();
                vpn.setUuid("123");
                list.add(vpn);
                rst.setData(list);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<String> list = new ArrayList<>();
        list.add("uuid");

        L3VpnPo rst = (L3VpnPo)dao.selectByFiniteIds(list).get(0);
        assertEquals("123", rst.getUuid());
        mock.tearDown();

    }

    @Test
    public void testInsertWithEmptyInput() throws ServiceException {
        List<String> list = new ArrayList<>();
        assertNull(dao.insert(list));
    }

    @Test
    public void testInsertReturnFail() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<String>> add(List<L3VpnPo> moList, Class<?> moType) throws ServiceException {
                ResultRsp<List<String>> rst = new ResultRsp();
                List<String> list = new ArrayList<>();

                list.add("123");
                rst.setData(list);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_FAILED);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<String> list = new ArrayList<>();
        list.add("uuid");

        assertNull(dao.insert(list));
        mock.tearDown();
    }

    @Test
    public void testInsert() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp<List<String>> add(List<L3VpnPo> moList, Class<?> moType) throws ServiceException {
                ResultRsp<List<String>> rst = new ResultRsp();
                List<String> list = new ArrayList<>();

                list.add("123");
                rst.setData(list);
                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<L3VpnPo> list = new ArrayList<>();
        L3VpnPo vpn = new L3VpnPo();
        list.add(vpn);

        assertEquals("123", dao.insert(list).get(0));
        mock.tearDown();
    }

    @Test
    public void testUpdateWithEmptyInput() throws ServiceException {
        List<String> list = new ArrayList<>();
        assertTrue(dao.update(list));
    }

    @Test
    public void testUpdateReturnFail() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp update(List<L3VpnPo> moList, Class<?> moType) throws ServiceException {
                ResultRsp<List<String>> rst = new ResultRsp();

                rst.setErrorCode(ErrorCode.UNDERLAYVPN_FAILED);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<String> list = new ArrayList<>();
        list.add("uuid");

        assertFalse(dao.update(list));
        mock.tearDown();
    }

    @Test
    public void testUpdate() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp update(List<L3VpnPo> moList, Class<?> moType) throws ServiceException {
                ResultRsp<List<String>> rst = new ResultRsp();

                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<L3VpnPo> list = new ArrayList<>();
        L3VpnPo vpn = new L3VpnPo();
        list.add(vpn);

        assertTrue(dao.update(list));
        mock.tearDown();
    }

    @Test
    public void testDeleteWithEmptyInput() throws ServiceException {
        List<String> list = new ArrayList<>();
        assertTrue(dao.delete(list));
    }

    @Test
    public void testDeleteReturnFail() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp batchDelete(List<String> uuidList, Class<?> moType) throws ServiceException {
                ResultRsp<List<String>> rst = new ResultRsp();

                rst.setErrorCode(ErrorCode.UNDERLAYVPN_FAILED);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<String> list = new ArrayList<>();
        list.add("uuid");

        assertFalse(dao.delete(list));
        mock.tearDown();
    }

    @Test
    public void testDelete() throws ServiceException {
        MockUp<PuerInvDAOImpl> mock = new MockUp<PuerInvDAOImpl>() {

            @Mock
            public ResultRsp batchDelete(List<String> uuidList, Class<?> moType) throws ServiceException {
                ResultRsp<List<String>> rst = new ResultRsp();

                rst.setErrorCode(ErrorCode.UNDERLAYVPN_SUCCESS);
                return rst;
            }
        };
        PuerInvDAOImpl invDao = new PuerInvDAOImpl();
        dao.setInvDao(invDao);

        List<String> list = new ArrayList<>();
        list.add("uuid");

        assertTrue(dao.delete(list));
        mock.tearDown();
    }
}
