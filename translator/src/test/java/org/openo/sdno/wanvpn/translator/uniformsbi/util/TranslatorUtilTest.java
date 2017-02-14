/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

import static org.junit.Assert.assertEquals;
import static org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.ACTIVE;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.model.servicemodel.common.enumeration.EncapType;
import org.openo.sdno.model.servicemodel.common.enumeration.OperStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.L2AccessType;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.TopologyType;

import junit.framework.Assert;

public class TranslatorUtilTest {

    @Before
    public void setUp() {

    }

    @Test
    public void test() {
        Assert.assertEquals(AdminStatus.ADMIN_UP, TranslatorUtil.s2nAdminStatus(ACTIVE.getAlias()));

        Assert.assertEquals(AdminStatus.ADMIN_DOWN, TranslatorUtil.s2nAdminStatus("INACTIVE"));

        Assert.assertEquals(L2AccessType.QINQ, TranslatorUtil.s2nL2AccessType(EncapType.QINQ.getAlias()));

        Assert.assertEquals(L2AccessType.PORT, TranslatorUtil.s2nL2AccessType(EncapType.UNTAG.getAlias()));

        Assert.assertEquals(L2AccessType.DOT1Q, TranslatorUtil.s2nL2AccessType(EncapType.DEFAULT.getAlias()));

        Assert.assertEquals(TopologyType.FULL_MESH, TranslatorUtil.s2nTopologyType(
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.FULL_MESH.getAlias()));

        Assert.assertEquals(TopologyType.POINT_TO_MULTIPOINT, TranslatorUtil.s2nTopologyType(
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.POINT_TO_MULTIPOINT.getAlias()));

        Assert.assertEquals(TopologyType.POINT_TO_POINT, TranslatorUtil.s2nTopologyType(
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.POINT_TO_POINT.getAlias()));

        Assert.assertEquals(TopologyType.SINGLEPOINT, TranslatorUtil.s2nTopologyType(
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.SINGLEPOINT.getAlias()));

        Assert.assertEquals(TopologyType.POINT_TO_POINT, TranslatorUtil.s2nTopologyType(
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.HUBSPOKE_VIA_HUBCE.getAlias()));

        Assert.assertEquals(OperStatus.UP.getAlias(),
                TranslatorUtil.n2sOperStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus.OPERATE_UP));

        Assert.assertEquals(OperStatus.DOWN.getAlias(), TranslatorUtil
                .n2sOperStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus.OPERATE_DOWN));

        Assert.assertEquals(OperStatus.PARTIAL.getAlias(),
                TranslatorUtil.n2sOperStatus(org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus.PARTIAL_UP));

        Assert.assertEquals(OperStatus.DOWN.getAlias(), TranslatorUtil.n2sOperStatus(null));

        Assert.assertEquals(org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.INACTIVE.getAlias(),
                TranslatorUtil.n2sAdminStatus(null));

        Assert.assertEquals(org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.INACTIVE.getAlias(),
                TranslatorUtil.n2sAdminStatus(AdminStatus.ADMIN_DOWN));

        Assert.assertEquals(org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.ACTIVE.getAlias(),
                TranslatorUtil.n2sAdminStatus(AdminStatus.ADMIN_UP));

        assertEquals(TranslatorUtil.n2sL2AccessType(L2AccessType.DOT1Q.getAlias()), EncapType.DOT1Q);
        assertEquals(TranslatorUtil.n2sL2AccessType(L2AccessType.PORT.getAlias()), EncapType.UNTAG);
        assertEquals(TranslatorUtil.n2sL2AccessType(L2AccessType.QINQ.getAlias()), EncapType.QINQ);

        assertEquals(TranslatorUtil.n2sTopologyType(TopologyType.FULL_MESH.toString()),
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.FULL_MESH);
        assertEquals(TranslatorUtil.n2sTopologyType(TopologyType.POINT_TO_MULTIPOINT.toString()),
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.POINT_TO_MULTIPOINT);
        assertEquals(TranslatorUtil.n2sTopologyType(TopologyType.POINT_TO_POINT.toString()),
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.POINT_TO_POINT);
        assertEquals(TranslatorUtil.n2sTopologyType(TopologyType.SINGLEPOINT.toString()),
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.SINGLEPOINT);
        assertEquals(TranslatorUtil.n2sTopologyType(TopologyType.HUB_SPOKE.toString()),
                org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.POINT_TO_POINT);

        assertEquals(TranslatorUtil.getOperStatus(null), null);
        assertEquals(TranslatorUtil.getOperStatus("up"),
                org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus.OPERATE_UP);
        assertEquals(TranslatorUtil.getOperStatus("down"),
                org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus.OPERATE_DOWN);
        assertEquals(TranslatorUtil.getOperStatus("unknown"), null);
    }
}
