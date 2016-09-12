/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import org.openo.sdno.model.db.l2vpn.L2VpnPWSpecPo;
import org.openo.sdno.model.servicemodel.tunnel.PWSpec;
import org.openo.sdno.wanvpn.dao.vpn.AbstractPWSpecDao;

import junit.framework.Assert;

/**
 * AbstractPWSpecDaoTest class.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 11, 2016
 */
public class AbstractPWSpecDaoTest {

    private static class PriPWSpecDao extends AbstractPWSpecDao {

        @Override
        protected Class getPoClass() {
            return L2VpnPWSpecPo.class;
        }
    }

    private final AbstractPWSpecDao dao = new PriPWSpecDao();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAddMosInputEmpty() {
        List<PWSpec> mos = new ArrayList<>();
        try {
            dao.addMos(mos);
        } catch(ServiceException e) {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void testDelMosInputEmpty() throws ServiceException {
        List<PWSpec> mos = new ArrayList<>();
        assertTrue(dao.delMos(mos));
    }

}
