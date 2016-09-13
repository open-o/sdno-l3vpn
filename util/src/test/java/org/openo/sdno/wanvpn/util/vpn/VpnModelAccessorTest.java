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

package org.openo.sdno.wanvpn.util.vpn;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.CeTp;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;

public class VpnModelAccessorTest {

    @Test
    public void testGetSpecMapNull() {
        assertEquals(null, VpnModelAccessor.getSpecMap(null));
    }

    @Test
    public void testGetCeTpsEmpty() {
        Tp tp = new Tp();
        List<Tp> tpMos = new ArrayList<Tp>();
        tpMos.add(tp);
        List<CeTp> ceTps = new ArrayList<>(tpMos.size());
        assertEquals(ceTps, VpnModelAccessor.getCeTps(tpMos));
    }

    @Test
    public void testGetTpTypeSpecsEmpty() {
        Tp tp = new Tp();
        List<Tp> tpMos = new ArrayList<Tp>();
        tpMos.add(tp);
        List<TpTypeSpec> tpTypeSpecs = new ArrayList<>(tpMos.size());
        assertEquals(tpTypeSpecs, VpnModelAccessor.getTpTypeSpecs(tpMos));
    }

    @Test
    public void testGetRouteProtocolsEmpty() {
        Tp tp = new Tp();
        List<Tp> tpMos = new ArrayList<Tp>();
        tpMos.add(tp);
        List<RouteProtocolSpec> routeProtocolSpecs = new ArrayList<>(tpMos.size());
        assertEquals(routeProtocolSpecs, VpnModelAccessor.getRouteProtocols(tpMos));
    }
}
