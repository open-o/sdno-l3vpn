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
/*
 * Title:        NetMatrix V1R3C00<br>
 * Description:  [描述模块的功能、作用、使用方法和注意事项]<br>
 * Company:      Huawei Tech. Co., Ltd<br>
 * @author       h00313442
 * @version      1.0  2016-3-31
 */

package org.openo.sdno.l3vpnservice.common;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.brs.ControllerMO;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;

import mockit.Mock;
import mockit.MockUp;

public class ControllerUtilsTest {

    @Before
    public void setUp() throws Exception {
        new MockUp<ControllerUtils>() {

            @Mock
            public ControllerMO mGetControllerMo(final Tp tp) {
                ControllerMO controllerMO = null;
                if(null != tp) {
                    controllerMO = new ControllerMO();
                }
                return controllerMO;
            }

            @Mock
            public ControllerMO mGetControllerMo(final String neId) {
                ControllerMO controllerMO = null;
                if(null != neId) {
                    controllerMO = new ControllerMO();
                }
                return controllerMO;
            }

        };
    }

    @Test
    public void testGetControllerType() throws ServiceException, IOException {
        final String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        ControllerUtils.getControllerType(vpn);

        vpn.getAccessPointList().get(0).setContollerMO(null);
        ControllerUtils.getControllerType(vpn);
    }

    @Test
    public void testGetControllerTypeByTp() throws ServiceException, IOException {
        final String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        Tp tp = vpn.getAccessPointList().get(0);
        ControllerUtils.getControllerType(tp);

        tp.setContollerMO(null);
        ControllerUtils.getControllerType(tp);
        tp = null;
        ControllerUtils.getControllerType(tp);
    }

    @Test
    public void testGetControllerTypeVpnVoNull() throws ServiceException, IOException {
        VpnVo vpn = null;
        ControllerUtils.getControllerType(vpn);
    }

    @Test
    public void testGetControllerTypeVpnVo() throws ServiceException, IOException {
        final String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        VpnVo vpnVo = new VpnVo();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        vpnVo.setVpn(vpn);
        ControllerUtils.getControllerType(vpnVo);

        vpn.getAccessPointList().get(0).setContollerMO(null);
        ControllerUtils.getControllerType(vpnVo);
    }

    @Test
    public void testGetControllerTypeNull() throws ServiceException, IOException {
        Vpn vpn = null;
        ControllerUtils.getControllerType(vpn);
    }

    @Test
    public void testGetControllerTypeTpNull() throws ServiceException, IOException {
        final String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        vpn.setAccessPointList(null);
        ControllerUtils.getControllerType(vpn);
    }

    @Test
    public void testGetControllerUUID() throws ServiceException, IOException {
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        VpnVo vpnVo = new VpnVo();
        vpnVo.setVpn(vpn);
        ControllerUtils.getControllerUUID(vpnVo);

        ControllerUtils.getControllerUUID(vpn);

        vpnVo = null;
        ControllerUtils.getControllerUUID(vpnVo);
        // tp
        filePath = new File("src/test/resources/tp.json").getCanonicalPath();
        final Tp tp = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Tp.class);
        ControllerUtils.getControllerUUID(tp);

    }

    @Test
    public void testFillControllerMoNull() throws ServiceException {

        VpnVo vpnVo = null;
        ControllerUtils.fillControllerMo(vpnVo);

    }

    @Test
    public void testFillControllerMo() throws ServiceException, IOException {
        final String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        VpnVo vpnVo = new VpnVo();
        ControllerUtils.fillControllerMo(vpnVo);
        vpnVo.setVpn(vpn);
        ControllerUtils.fillControllerMo(vpnVo);

        vpn.setAccessPointList(Collections.EMPTY_LIST);
        ControllerUtils.fillControllerMo(vpnVo);
    }

    @Test
    public void testGetControllerMO() throws ServiceException, IOException {
        final String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        Assert.assertNotNull(ControllerUtils.getControllerMO(vpn));
        vpn = null;
        Assert.assertNull(ControllerUtils.getControllerMO(vpn));

    }

    @Test
    public void testMGetControllerMo() throws ServiceException, IOException {
        ControllerUtils.mGetControllerMo("test");

    }

}
