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
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.db.l3vpn.L3VpnEthernetTpSpecPo;
import org.openo.sdno.model.db.l3vpn.L3VpnIpTpSpecPo;
import org.openo.sdno.model.db.l3vpn.L3VpnTpTypeSpecPo;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.model.servicemodel.tp.EthernetTpSpec;
import org.openo.sdno.model.servicemodel.tp.IpTpSpec;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.dao.AbstractDao;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;

public class AbstractTpTypeSpecDaoTest {

    public static class L3VPnTpTypeSpecDao extends AbstractTpTypeSpecDao {

        @Override
        protected Class getPoClass() {
            return L3VpnTpTypeSpecPo.class;
        }
    };

    private final AbstractTpTypeSpecDao dao = new L3VPnTpTypeSpecDao();

    private void mock() {
        dao.setEthernetTpSpecDao(new AbstractEthernetTpSpecDao() {

            @Override
            protected Class getPoClass() {
                return L3VpnEthernetTpSpecPo.class;
            }
        });
        dao.setIpTpSpecDao(new AbstractIpTpSpecDao() {

            @Override
            protected Class getPoClass() {
                return L3VpnIpTpSpecPo.class;
            }
        });

        new MockUp<AbstractEthernetTpSpecDao>() {

            @Mock
            public List<SvcModel> getMoByIds(final List<String> uuids) throws ServiceException {
                final List<SvcModel> svcModels = new LinkedList<>();
                int i = 0;
                for(final String uuid : uuids) {
                    i++;
                    final EthernetTpSpec tpSpec = new EthernetTpSpec();
                    tpSpec.setUuid("ethspec" + i);
                    svcModels.add(tpSpec);
                }
                return svcModels;
            }

            @Mock
            public boolean delMos(List<EthernetTpSpec> mos) throws ServiceException {
                return true;
            }
        };

        new MockUp<AbstractIpTpSpecDao>() {

            @Mock
            public List<SvcModel> getMoByIds(final List<String> uuids) throws ServiceException {
                final List<SvcModel> svcModels = new LinkedList<>();
                int i = 0;
                for(final String uuid : uuids) {
                    i++;
                    final IpTpSpec tpSpec = new IpTpSpec();
                    tpSpec.setUuid("tpspec" + i);
                    svcModels.add(tpSpec);
                }
                return svcModels;
            }

            @Mock
            public boolean delMos(List<IpTpSpec> mos) throws ServiceException {
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

    private Tp getTp() throws IOException {

        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "tp.json";

        return JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Tp.class);
    }

    @Test
    public void assembleMo() throws Exception {
        mock();

        final L3VpnTpTypeSpecPo po = new L3VpnTpTypeSpecPo();
        po.setEthernetTpSpecId("tpSpecId");
        po.setIpTpSpecId("ipTpSpecId");
        final List<TpTypeSpec> mos = dao.assembleMo(Collections.singletonList(po));
        Assert.assertNotNull(mos);
        Assert.assertNotNull(mos.get(0));

        List<L3VpnTpTypeSpecPo> list = new ArrayList();
        Assert.assertNull(dao.assembleMo(list));
    }

    @Test
    public void addMos() throws Exception {
        new MockUp<AbstractDao>() {

            @Mock
            protected List<String> insert(final List<PoModel> pos) throws ServiceException {
                return getIdList(pos);
            }
        };

        mock();

        final Tp tp = getTp();
        List<TpTypeSpec> typeSpecList = tp.getTypeSpecList();
        try {
            dao.addMos(typeSpecList);

            typeSpecList = new ArrayList();
            dao.addMos(typeSpecList);
        } catch(ServiceException e) {
            //Failed is exception is thrown
            Assert.assertTrue(false);
        }
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
        final Tp tp = getTp();
        final List<TpTypeSpec> typeSpecList = tp.getTypeSpecList();
        try {
            dao.delMos(typeSpecList);
        } catch(ServiceException e) {
            //Failed is exception is thrown
            Assert.assertTrue(false);
        }
    }

    @Test
    public void getPoClass() throws Exception {
        Assert.assertSame(dao.getPoClass(), L3VpnTpTypeSpecPo.class);
    }

}
