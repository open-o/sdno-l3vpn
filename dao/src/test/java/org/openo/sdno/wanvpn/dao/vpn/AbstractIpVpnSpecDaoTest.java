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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.l2vpn.L2VpnIpVpnSpecPo;
import org.openo.sdno.model.servicemodel.businesstype.IpVpnSpec;
import org.openo.sdno.wanvpn.dao.vpn.AbstractIpVpnSpecDao;

/**
 * AbstractIpVpnSpecDaoTest class.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 11, 2016
 */
public class AbstractIpVpnSpecDaoTest {

    private static class PriIpVpnSpecDao extends AbstractIpVpnSpecDao {

        @Override
        protected Class getPoClass() {
            return L2VpnIpVpnSpecPo.class;
        }
    }

    private final AbstractIpVpnSpecDao dao = new PriIpVpnSpecDao();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testDelMosInputEmpty() throws ServiceException {
        List<IpVpnSpec> mos = new ArrayList<>();
        assertTrue(dao.delMos(mos));
    }

    @Test
    public void testAssembleMoInputEmpty() throws ServiceException {
        List<IpVpnSpec> mos = new ArrayList<>();
        assertTrue(dao.assembleMo(mos).isEmpty());
    }

}
