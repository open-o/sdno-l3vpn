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
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.wanvpn.util.query.BatchQueryParams;
import org.openo.sdno.wanvpn.util.query.BatchQueryResult;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.db.l3vpn.L3VpnBasicInfoPo;
import org.openo.sdno.model.db.l3vpn.L3VpnPo;
import org.openo.sdno.model.db.l3vpn.L3VpnTpPo;
import org.openo.sdno.model.db.vpn.AbstractVpnBasicInfoPo;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.dao.AbstractDao;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;

public class AbstractVpnDaoTest {

    private AbstractVpnDao vpnDao = new AbstractVpnDao() {

        @Override
        protected Class getPoClass() {
            // TODO Auto-generated method stub
            return L3VpnPo.class;
        }

    };

    @Test
    public void isVpnNameExisted() throws Exception {
        new MockUp<AbstractVpnDao>() {

            @Mock
            public BatchQueryResult<L3VpnPo> selectByPage(final String rawFilterDesc,
                    final BatchQueryParams batchQueryParams) throws ServiceException {
                return new BatchQueryResult<>();
            }
        };

        final boolean existed = vpnDao.isVpnNameExisted("");
        Assert.assertFalse(existed);
    }

    @Test
    public void assembleBasicInfo() throws Exception {
        mock();

        final L3VpnPo po = new L3VpnPo();
        po.setUuid("");
        final List<Vpn> mos = vpnDao.assembleBasicInfo(Collections.singletonList(po));
        Assert.assertNotNull(mos);
        Assert.assertNotNull(mos.get(0));
    }

    private void mock() {
        AbstractVpnBasicInfoDao vpnBasicInfoDao = new L3VpnBasicInfoDao();
        vpnBasicInfoDao.setIpVpnSpecDao(new VpnPolicyIpVpnSpecDao());

        vpnDao.setVpnBasicInfoDao(vpnBasicInfoDao);
        vpnDao.setTpDao(new L3VpnTpDao());
        new MockUp<L3VpnBasicInfoDao>() {

            @Mock
            public List<PoModel> selectByIds(final List<String> uuids) throws ServiceException {
                final List<PoModel> poModels = new ArrayList<>(uuids.size());
                int i = 0;
                for(final String uuid : uuids) {
                    i++;
                    final L3VpnBasicInfoPo po = new L3VpnBasicInfoPo();
                    po.setUuid("poid" + i);

                    poModels.add(po);
                }
                return poModels;
            }

            @Mock
            public void addMos(final List<VpnBasicInfo> mos) throws ServiceException {

            }

            @Mock
            public boolean delMos(final List<VpnBasicInfo> mos) throws ServiceException {
                return true;
            }

            @Mock
            public List<VpnBasicInfo> assembleMo(final List<AbstractVpnBasicInfoPo> pos) throws ServiceException {
                VpnBasicInfo vpnBasicInfo = null;
                try {
                    vpnBasicInfo = getVpn().getVpnBasicInfo();
                } catch(IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return Collections.singletonList(vpnBasicInfo);

            }
        };
        new MockUp<L3VpnTpDao>() {

            @Mock
            public void addMos(final List<L3VpnTpPo> mos) throws ServiceException {

            }

            @Mock
            public boolean delMos(final List<Tp> mos) throws ServiceException {
                return true;
            }

            @Mock
            public List<PoModel> selectByIds(final List<String> uuids) throws ServiceException {
                final List<PoModel> svcModels = new LinkedList<>();
                int i = 0;
                for(final String uuid : uuids) {
                    i++;
                    final L3VpnTpPo l3VpnTpPo = new L3VpnTpPo();
                    l3VpnTpPo.setUuid("tpid" + i);
                    svcModels.add(l3VpnTpPo);
                }
                return svcModels;
            }

            @Mock
            public List<PoModel> selectByConditions(final String fieldName, final List<?> fieldVals)
                    throws ServiceException {
                final List<PoModel> svcModels = new LinkedList<>();
                for(final Object uuid : fieldVals) {
                    final L3VpnTpPo l3VpnTpPo = new L3VpnTpPo();
                    l3VpnTpPo.setUuid((String)uuid);
                    svcModels.add(l3VpnTpPo);
                }
                return svcModels;
            }

            @Mock
            public List<SvcModel> assembleMo(final List<L3VpnTpPo> pos) throws ServiceException {
                final List<SvcModel> tps = new ArrayList<>(pos.size());

                for(final PoModel po : pos) {
                    tps.add(po.toSvcModel());
                }
                return tps;
            }
        };
    }

    @Test
    public void updateVpn() throws Exception {
        new MockUp<AbstractDao>() {

            @Mock
            public boolean update(final List<L3VpnPo> pos) throws ServiceException {
                return true;
            }

            ;
        };

        new MockUp<L3VpnBasicInfoDao>() {

            @Mock
            public boolean updateMos(final List<VpnBasicInfo> mos) throws ServiceException {
                return true;
            }
        };

        mock();

        final Vpn vpn = getVpn();
        try {
            vpnDao.updateVpn(vpn);
        } catch(ServiceException e) {
            Assert.assertFalse(true);
        }
    }

    private Vpn getVpn() throws IOException {

        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "vpn.json";
        return JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
    }

    @Test
    public void addMos() throws Exception {
        final Vpn vpn = getVpn();

        mock();

        new MockUp<AbstractDao>() {

            @Mock
            protected List<String> insert(final List<L3VpnPo> pos) throws ServiceException {
                return getIdList(pos);
            }
        };
        try {
            vpnDao.addMos(Collections.singletonList(vpn));
        } catch(ServiceException e) {
            Assert.assertFalse(true);
        }
    }

    private <PO extends PoModel> List<String> getIdList(final List<PO> pos) {
        String[] ids = new String[pos.size()];
        for(int i = 0; i < ids.length; i++) {
            ids[i] = "" + i;
        }
        return Arrays.asList(ids);
    }

    @Test
    public void delMos() throws Exception {
        mock();

        new MockUp<AbstractDao>() {

            @Mock
            protected boolean delete(final List<String> poIds) throws ServiceException {
                return true;
            }
        };

        final Vpn vpn = getVpn();
        final boolean result = vpnDao.delMos(Collections.singletonList(vpn));
        Assert.assertTrue(result);
    }

    @Test
    public void updateMos() throws Exception {
        new MockUp<AbstractDao>() {

            @Mock
            public boolean update(final List<L3VpnPo> pos) throws ServiceException {
                return true;
            }

            ;
        };

        new MockUp<L3VpnBasicInfoDao>() {

            @Mock
            public boolean updateMos(final List<VpnBasicInfo> mos) throws ServiceException {
                return true;
            }
        };

        mock();

        final Vpn vpn = getVpn();
        try {
            vpnDao.updateMos(Collections.singletonList(vpn));
        } catch(ServiceException e) {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void assembleMo() throws Exception {
        mock();

        final L3VpnPo po = new L3VpnPo();
        po.setUuid("");
        final List<Vpn> mos = vpnDao.assembleMo(Collections.singletonList(po));
        Assert.assertNotNull(mos);
        Assert.assertNotNull(mos.get(0));
    }

    @Test
    public void updateDescription() throws Exception {
        new MockUp<AbstractDao>() {

            @Mock
            public L3VpnPo selectById(final String uuid) throws ServiceException {
                final L3VpnPo po = new L3VpnPo();
                po.setUuid(uuid);
                return po;
            }

            @Mock
            public boolean update(final List<L3VpnPo> pos) throws ServiceException {
                return true;
            }

            ;
        };

        mock();

        try {
            vpnDao.updateDescription("", "");
        } catch(ServiceException e) {
            Assert.assertFalse(true);
        }
    }

    public static class L3VpnDao extends AbstractVpnDao {

        @Override
        protected Class getPoClass() {
            return L3VpnPo.class;
        }
    }

    public static class L3VpnBasicInfoDao extends AbstractVpnBasicInfoDao {

        @Override
        protected Class getPoClass() {
            return L3VpnBasicInfoPo.class;
        }
    }

    public static class L3VpnTpDao extends AbstractTpDao {

        @Override
        protected Class getPoClass() {
            return L3VpnTpPo.class;
        }
    }

    private static final class VpnPolicyIpVpnSpecDao extends AbstractIpVpnSpecDao {

        @Override
        protected Class getPoClass() {
            return VpnPolicyIpVpnSpecDao.class;
        }

    };

}
