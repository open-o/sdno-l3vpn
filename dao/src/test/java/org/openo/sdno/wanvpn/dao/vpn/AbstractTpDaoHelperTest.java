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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.common.VpnModelAccessor;
import org.openo.sdno.model.db.l3vpn.L3VpnCeTpPo;
import org.openo.sdno.model.db.l3vpn.L3VpnRouteProtocolSpecPo;
import org.openo.sdno.model.db.l3vpn.L3VpnTpTypeSpecPo;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.CeTp;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;
import org.openo.sdno.wanvpn.util.constant.TpConstants;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;

public class AbstractTpDaoHelperTest {

    private TpDaoHelper helper = new L3VpnTpDaoHelper();

    @Test
    public void getCeTpMap() throws Exception {
        mock();

        final List<String> tpId = Collections.singletonList("tpId");
        final Map<String, CeTp> ceTpMap = helper.getCeTpMap(tpId);
        Assert.assertNull(ceTpMap);
    }

    private void mock() {
        helper.setTpTypeSpecDao(new L3VpnTpTypeSpecDao());
        helper.setCeTpDao(new L3VpnCeTpDao());
        helper.setRouteProtocolSpecDao(new L3VpnRouteProtocolSpecDao());

        new MockUp<L3VpnCeTpDao>() {

            @Mock
            public List<SvcModel> getMoByIds(final List<String> uuids) throws ServiceException {
                final List<SvcModel> svcModels = new LinkedList<>();
                int i = 0;
                for(final String uuid : uuids) {
                    i++;
                    final CeTp ceTp = new CeTp();
                    ceTp.setUuid("ceid" + i);
                    svcModels.add(ceTp);
                }
                return svcModels;
            }
        };

        new MockUp<L3VpnTpTypeSpecDao>() {

            @Mock
            public List<L3VpnTpTypeSpecPo> selectByConditions(final String fieldName, final List<?> fieldVals)
                    throws ServiceException {
                if(TpConstants.TP_ID.equalsIgnoreCase(fieldName)) {
                    final List<L3VpnTpTypeSpecPo> poModels = new LinkedList<>();
                    int i = 0;
                    for(final Object fieldVal : fieldVals) {
                        i++;
                        final L3VpnTpTypeSpecPo po = new L3VpnTpTypeSpecPo();
                        po.setUuid("typespecid" + i);
                        po.setTpId("tpid" + i);
                        poModels.add(po);
                    }
                    return poModels;
                }
                return null;
            }

            @Mock
            public List<TpTypeSpec> assembleMo(final List<L3VpnTpTypeSpecPo> typeSpecPos) throws ServiceException {
                final List<TpTypeSpec> specList = new ArrayList<>(typeSpecPos.size());
                for(final L3VpnTpTypeSpecPo typeSpecPo : typeSpecPos) {
                    specList.add(typeSpecPo.toSvcModel());
                }
                return specList;
            }
        };
        new MockUp<L3VpnRouteProtocolSpecDao>() {

            @Mock
            public List<L3VpnRouteProtocolSpecPo> selectByConditions(final String fieldName, final List<?> fieldVals)
                    throws ServiceException {
                if(TpConstants.TP_ID.equalsIgnoreCase(fieldName)) {
                    final List<L3VpnRouteProtocolSpecPo> poModels = new LinkedList<>();
                    int i = 0;
                    for(final Object fieldVal : fieldVals) {
                        i++;
                        final L3VpnRouteProtocolSpecPo po = new L3VpnRouteProtocolSpecPo();
                        po.setUuid("routeprotocolid" + i);
                        po.setTpId("tpid" + i);
                        poModels.add(po);
                    }
                    return poModels;
                }
                return null;
            }

            @Mock
            public List<RouteProtocolSpec> assembleMo(final List<L3VpnRouteProtocolSpecPo> routeProtocolSpecPos)
                    throws ServiceException {
                final List<RouteProtocolSpec> specList = new ArrayList<>(routeProtocolSpecPos.size());
                for(final L3VpnRouteProtocolSpecPo routeProtocolSpecPo : routeProtocolSpecPos) {
                    specList.add(routeProtocolSpecPo.toSvcModel());
                }
                return specList;
            }
        };
    }

    @Test
    public void getRouteProtocolSpecMap() throws Exception {
        mock();

        final List<String> tpId = Collections.singletonList("tpId");
        final Map<String, List<RouteProtocolSpec>> ceTpMap = helper.getRouteProtocolSpecMap(tpId);
        Assert.assertNotNull(ceTpMap);
        for(final Map.Entry<String, List<RouteProtocolSpec>> entry : ceTpMap.entrySet()) {
            Assert.assertNotNull(entry.getKey());
            Assert.assertTrue(CollectionUtils.isNotEmpty(entry.getValue()));
        }
    }

    @Test
    public void getTpTypeSpecMap() throws Exception {
        mock();

        final List<String> tpId = Collections.singletonList("tpId");
        final Map<String, List<TpTypeSpec>> ceTpMap = helper.getTpTypeSpecMap(tpId);
        Assert.assertNotNull(ceTpMap);
        for(final Map.Entry<String, List<TpTypeSpec>> entry : ceTpMap.entrySet()) {
            Assert.assertNotNull(entry.getKey());
            Assert.assertTrue(CollectionUtils.isNotEmpty(entry.getValue()));
        }
    }

    @Test
    public void prepareCeTpForAdd() throws Exception {
        final Tp tp = new Tp();
        tp.setPeerCeTp(new CeTp());

        final List<CeTp> ceTps = helper.prepareCeTpForAdd(Collections.singletonList(tp));
        Assert.assertTrue(ceTps.size() == 1);
    }

    @Test
    public void prepareTpTypeSpecForAdd() throws Exception {
        final Tp tp = new Tp();
        tp.setTypeSpecList(Collections.singletonList(new TpTypeSpec()));

        final List<TpTypeSpec> tpTypeSpecs = helper.prepareTpTypeSpecForAdd(Collections.singletonList(tp));
        Assert.assertTrue(tpTypeSpecs.size() == 1);
    }

    @Test
    public void prepareRouteProtocolForAdd() throws Exception {
        final Tp tp = new Tp();
        tp.setRouteProtocolSpecs(Collections.singletonList(new RouteProtocolSpec()));

        final List<RouteProtocolSpec> tpTypeSpecs = helper.prepareRouteProtocolForAdd(Collections.singletonList(tp));
        Assert.assertTrue(tpTypeSpecs.size() == 1);
    }

    @Test
    public void getCeTps() throws Exception {
        final Tp tp = new Tp();
        tp.setPeerCeTp(new CeTp());

        final List<CeTp> ceTps = VpnModelAccessor.getCeTps(Collections.singletonList(tp));
        Assert.assertTrue(ceTps.size() == 1);
    }

    @Test
    public void getTpTypeSpecs() throws Exception {
        final Tp tp = new Tp();
        tp.setTypeSpecList(Collections.singletonList(new TpTypeSpec()));

        final List<TpTypeSpec> tpTypeSpecs = VpnModelAccessor.getTpTypeSpecs(Collections.singletonList(tp));
        Assert.assertTrue(tpTypeSpecs.size() == 1);
    }

    @Test
    public void getRouteProtocols() throws Exception {
        final Tp tp = new Tp();
        tp.setRouteProtocolSpecs(Collections.singletonList(new RouteProtocolSpec()));

        final List<RouteProtocolSpec> tpTypeSpecs = VpnModelAccessor.getRouteProtocols(Collections.singletonList(tp));
        Assert.assertTrue(tpTypeSpecs.size() == 1);
    }

    public static class L3VpnTpDaoHelper extends TpDaoHelper {

    }

    public static class L3VpnTpTypeSpecDao extends AbstractTpTypeSpecDao {

        @Override
        protected Class getPoClass() {
            return L3VpnTpTypeSpecPo.class;
        }
    }

    public static class L3VpnCeTpDao extends AbstractCeTpDao {

        @Override
        protected Class getPoClass() {
            return L3VpnCeTpPo.class;
        }
    }

    public static class L3VpnRouteProtocolSpecDao extends AbstractRouteProtocolSpecDao {

        @Override
        protected Class getPoClass() {
            return L3VpnRouteProtocolSpecPo.class;
        }
    }
}
