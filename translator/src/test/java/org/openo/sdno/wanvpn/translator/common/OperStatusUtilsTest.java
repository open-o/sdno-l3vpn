/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.wanvpn.translator.common;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus;
import org.openo.sdno.model.servicemodel.common.enumeration.OperStatus;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Acs;

public class OperStatusUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSetVpnDefaultStatus() {
        Vpn vpn = new Vpn();
        vpn.setVpnBasicInfo(new VpnBasicInfo());
        List<Tp> accessPointList = new ArrayList<>();
        accessPointList.add(new Tp());
        accessPointList.add(new Tp());
        vpn.setAccessPointList(accessPointList);
        OperStatusUtils.setVpnDefaultStatus(vpn);
        assertEquals(vpn.getOperStatus(), OperStatus.NOP.getAlias());
        assertEquals(vpn.getVpnBasicInfo().getAdminStatus(), AdminStatus.NOP.toString());
        assertEquals(vpn.getAccessPointList().get(0).getOperStatus(), OperStatus.NOP.getAlias());
        assertEquals(vpn.getAccessPointList().get(0).getAdminStatus(), AdminStatus.NOP.getAlias());
    }

    @Test
    public void testUpdateTpOperAndName() {
        Vpn vpn = new Vpn();
        List<Tp> accessPointList = new ArrayList<>();
        Tp tp1 = new Tp();
        tp1.setId("tp1");

        Tp tp2 = new Tp();
        tp2.setId("tp2");
        accessPointList.add(tp1);
        accessPointList.add(tp2);
        vpn.setAccessPointList(accessPointList);

        L3Acs l3Acs = new L3Acs();
        List<L3Ac> l3AcList = new ArrayList<>();
        L3Ac l3Ac = new L3Ac();
        l3Ac.setUuid("tp2");
        l3Ac.setName("l3ac");
        l3Ac.setOperStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus.OPERATE_DOWN);
        l3Ac.setAdminStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus.ADMIN_UP);
        l3AcList.add(l3Ac);
        l3Acs.setL3Ac(l3AcList);

        OperStatusUtils.updateTpOperAndName(vpn, l3Acs);
        assertEquals(tp2.getOperStatus(),
                org.openo.sdno.model.servicemodel.common.enumeration.OperStatus.DOWN.getAlias());
        assertEquals(tp2.getAdminStatus(),
                org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.ACTIVE.toString());
        assertEquals(tp2.getName(), l3Ac.getName());

        assertEquals(tp1.getOperStatus(), null);
        assertEquals(tp1.getAdminStatus(), null);
        assertEquals(tp1.getName(), null);
    }
}
