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

package org.openo.sdno.wanvpn.translator.uniformsbi.util;

import static org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.ACTIVE;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.model.servicemodel.common.enumeration.EncapType;
import org.openo.sdno.model.servicemodel.common.enumeration.OperStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.L2AccessType;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.TopologyType;
import org.openo.sdno.wanvpn.translator.uniformsbi.util.TranslatorUtil;

import junit.framework.Assert;

public class TranslatorUtilTest {

    private TranslatorUtil service;

    @Before
    public void setUp() {

    }

    @Test
    public void test() {
        Assert.assertEquals(AdminStatus.ADMIN_UP, service.s2nAdminStatus(ACTIVE.getCommonName()));

        Assert.assertEquals(AdminStatus.ADMIN_DOWN, service.s2nAdminStatus("INACTIVE"));

        Assert.assertEquals(L2AccessType.QINQ, service.s2nL2AccessType(EncapType.QINQ.getCommonName()));

        Assert.assertEquals(L2AccessType.PORT, service.s2nL2AccessType(EncapType.UNTAG.getCommonName()));

        Assert.assertEquals(L2AccessType.DOT1Q, service.s2nL2AccessType(EncapType.DEFAULT.getCommonName()));

        Assert.assertEquals(TopologyType.FULL_MESH, service.s2nTopologyType(
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.FULL_MESH.getCommonName()));

        Assert.assertEquals(TopologyType.POINT_TO_MULTIPOINT, service.s2nTopologyType(
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.POINT_TO_MULTIPOINT.getCommonName()));

        Assert.assertEquals(TopologyType.POINT_TO_POINT, service.s2nTopologyType(
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.POINT_TO_POINT.getCommonName()));

        Assert.assertEquals(TopologyType.SINGLEPOINT, service.s2nTopologyType(
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.SINGLEPOINT.getCommonName()));

        Assert.assertEquals(OperStatus.UP.getCommonName(),
                service.n2sOperStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus.OPERATE_UP));

        Assert.assertEquals(OperStatus.DOWN.getCommonName(),
                service.n2sOperStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus.OPERATE_DOWN));

        Assert.assertEquals(OperStatus.DOWN.getCommonName(), service.n2sOperStatus(null));

        Assert.assertEquals(org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.INACTIVE.getCommonName(),
                service.n2sAdminStatus(null));

        Assert.assertEquals(org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.INACTIVE.getCommonName(),
                service.n2sAdminStatus(AdminStatus.ADMIN_DOWN));

        Assert.assertEquals(org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.ACTIVE.getCommonName(),
                service.n2sAdminStatus(AdminStatus.ADMIN_UP));

    }
}
