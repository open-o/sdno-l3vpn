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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.db.l3vpn.L3VpnCeTpPo;
import org.openo.sdno.model.db.l3vpn.L3VpnRouteProtocolSpecPo;
import org.openo.sdno.model.db.l3vpn.L3VpnTpPo;
import org.openo.sdno.model.db.l3vpn.L3VpnTpTypeSpecPo;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.CeTp;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.dao.AbstractDao;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;

public class AbstractTpDaoTest {

    static class L3VpnTpDao extends AbstractTpDao {

        @Override
        protected Class getPoClass() {
            return L3VpnTpPo.class;
        }
    }

    static class L3VpnCeTpDao extends AbstractCeTpDao {

        @Override
        protected Class getPoClass() {
            return L3VpnCeTpPo.class;
        }
    }

    static class L3VpnRouteProtocolSpecDao extends AbstractRouteProtocolSpecDao {

        @Override
        protected Class getPoClass() {
            return L3VpnRouteProtocolSpecPo.class;
        }
    }

    static class L3VpnTpTypeSpecDao extends AbstractTpTypeSpecDao {

        @Override
        protected Class getPoClass() {
            return L3VpnTpTypeSpecPo.class;
        }
    }

    static class L3VpnTpDaoHelper extends TpDaoHelper {

    }

    private AbstractTpDao tpDao = new L3VpnTpDao();

    private void mock() {
        tpDao.setCeTpDao(new L3VpnCeTpDao());
        tpDao.setRouteProtocolSpecDao(new L3VpnRouteProtocolSpecDao());
        tpDao.setTpTypeSpecDao(new L3VpnTpTypeSpecDao());
        tpDao.setTpDaoHelper(new L3VpnTpDaoHelper());

        new MockUp<L3VpnCeTpDao>() {

            @Mock
            public void addMos(List<CeTp> mos) throws ServiceException {

            };

            @Mock
            public boolean delMos(final List<CeTp> mos) throws ServiceException {
                return true;
            }
        };

        new MockUp<L3VpnRouteProtocolSpecDao>() {

            @Mock
            public void addMos(List<RouteProtocolSpec> mos) throws ServiceException {

            };

            @Mock
            public boolean delMos(final List<RouteProtocolSpec> mos) throws ServiceException {
                return true;
            }

        };

        new MockUp<L3VpnTpTypeSpecDao>() {

            @Mock
            public void addMos(List<TpTypeSpec> mos) throws ServiceException {

            };

            @Mock
            public boolean delMos(final List<TpTypeSpec> mos) throws ServiceException {
                return true;
            }
        };
    }

    private <PO extends PoModel> List<String> getIdList(final List<PO> pos) {
        String[] ids = new String[pos.size()];
        for(int i = 0; i < ids.length; i++) {
            ids[i] = "" + i;
        }
        return Arrays.asList(ids);
    }

    @Test
    public void addMos() throws Exception {
        new MockUp<AbstractDao>() {

            @Mock
            protected List<String> insert(final List<L3VpnTpPo> pos) throws ServiceException {
                return getIdList(pos);
            }
        };

        mock();

        final Tp tp = getTp();
        try {
            tpDao.addMos(Collections.singletonList(tp));
        } catch(ServiceException e) {
            //Failed is exception is thrown
            Assert.assertTrue(false);
        }
    }

    private Tp getTp() throws IOException {
        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "tp.json";
        return JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Tp.class);
    }

    @Test
    public void addEmpty() {
        try {
            tpDao.addMos(null);
            tpDao.addMos(new ArrayList<Tp>());
        } catch(ServiceException e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void delMos() throws Exception {
        new MockUp<AbstractDao>() {

            @Mock
            protected boolean delete(final List<String> poIds) throws ServiceException {
                return true;
            }
        };

        mock();
        final boolean result = tpDao.delMos(Collections.singletonList(getTp()));
        Assert.assertTrue(result);
    }

    @Test
    public void delEmpty() throws Exception {
        final boolean result1 = tpDao.delMos(null);
        final boolean result3 = tpDao.delMos(Collections.<Tp> emptyList());
        Assert.assertTrue(result1);
        Assert.assertTrue(result3);
    }

    @Test
    public void updateMos() throws Exception {
        new MockUp<AbstractDao>() {

            @Mock
            public boolean update(final List<L3VpnTpPo> pos) throws ServiceException {
                return true;
            };
        };

        final boolean result = tpDao.updateMos(Collections.singletonList(getTp()));
        Assert.assertTrue(result);
    }

    @Test
    public void updateEmpty() throws Exception {

        final boolean result1 = tpDao.updateMos(null);
        final boolean result3 = tpDao.updateMos(Collections.<Tp> emptyList());
        Assert.assertTrue(result1);
        Assert.assertTrue(result3);
    }

    @Test
    public void assembleMo() throws Exception {
        new MockUp<L3VpnTpDaoHelper>() {

            @Mock
            public Map<String, CeTp> getCeTpMap(final List<String> ceTpIds) throws ServiceException {
                final Map<String, CeTp> ceTpMap = new HashMap<>();
                for(final String ceTpId : ceTpIds) {
                    final CeTp ceTp = new CeTp();
                    ceTp.setUuid(ceTpId);
                    ceTpMap.put(ceTpId, ceTp);
                }
                return ceTpMap;
            }

            @Mock
            public Map<String, List<TpTypeSpec>> getTpTypeSpecMap(final List<String> tpIds) throws ServiceException {
                final Map<String, List<TpTypeSpec>> map = new HashMap<>();
                for(final String tpId : tpIds) {
                    final TpTypeSpec typeSpec = new TpTypeSpec();
                    typeSpec.setUuid("");
                    map.put(tpId, Collections.singletonList(typeSpec));
                }
                return map;
            }

            @Mock
            public Map<String, List<RouteProtocolSpec>> getRouteProtocolSpecMap(final List<String> tpIds) {
                final Map<String, List<RouteProtocolSpec>> map = new HashMap<>();
                for(final String tpId : tpIds) {
                    final RouteProtocolSpec protocolSpec = new RouteProtocolSpec();
                    protocolSpec.setUuid("");
                    map.put(tpId, Collections.singletonList(protocolSpec));
                }
                return map;
            }
        };

        mock();

        final L3VpnTpPo tpPo = new L3VpnTpPo();
        tpPo.setUuid("tpId");
        tpPo.setOutBoundTpCarId("outcarid");
        tpPo.setInBoundTpCarId("incarid");
        final List<Tp> mos = tpDao.assembleMo(Collections.singletonList(tpPo));
        Assert.assertTrue(CollectionUtils.isNotEmpty(mos));
        final Tp tp = mos.get(0);
        Assert.assertNotNull(tp.getTypeSpecList());
        Assert.assertNotNull(tp.getPeerCeTp());
        Assert.assertNotNull(tp.getRouteProtocolSpecs());
    }

    @Test
    public void assembleBriefMo() throws Exception {
        new MockUp<L3VpnTpDaoHelper>() {

            @Mock
            public Map<String, CeTp> getCeTpMap(final List<String> ceTpIds) throws ServiceException {
                final Map<String, CeTp> ceTpMap = new HashMap<>();
                for(final String ceTpId : ceTpIds) {
                    final CeTp ceTp = new CeTp();
                    ceTp.setUuid(ceTpId);
                    ceTpMap.put(ceTpId, ceTp);
                }
                return ceTpMap;
            }
        };

        mock();

        final L3VpnTpPo tpPo = new L3VpnTpPo();
        tpPo.setUuid("tpId");
        tpPo.setOutBoundTpCarId("outcarid");
        tpPo.setInBoundTpCarId("incarid");
        final List<Tp> mos = tpDao.assembleBriefMo(Collections.singletonList(tpPo));
        Assert.assertTrue(CollectionUtils.isNotEmpty(mos));
        final Tp tp = mos.get(0);
        Assert.assertNotNull(tp.getUuid());
        Assert.assertNull(tp.getTypeSpecList());
        Assert.assertNotNull(tp.getPeerCeTp());
        Assert.assertNull(tp.getRouteProtocolSpecs());
    }

    @Test
    public void updateStatus() throws Exception {
        new MockUp<AbstractDao>() {

            @Mock
            public boolean update(final List<L3VpnTpPo> pos) throws ServiceException {
                return true;
            };
        };

        final Tp tp = getTp();
        try {
            tpDao.updateMos(Collections.singletonList(tp));
        } catch(ServiceException e) {
            //Failed is exception is thrown
            Assert.assertTrue(false);
        }
    }

    @Test
    public void getPoClass() throws Exception {
        Assert.assertSame(tpDao.getPoClass(), L3VpnTpPo.class);
    }

}
