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

package org.openo.sdno.wanvpn.util.checker;

import static org.openo.sdno.wanvpn.util.error.CommonErrorCode.CHECKER_VLAN_SCOPE_INVALID;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.paradesc.VlanScopeDesc;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;

/**
 * The checker of the vlan scope.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 1, 2016
 */
public class VlanScopeChecker {

    private static final String VLAN_RANGE_SPLIT = "-";

    private static final String VLAN_SEGMENTS_SPLIT = ",";

    private static final String VLAN_VALID_CHARS = "0123456789-,";

    private static final int VLAN_SEGMENT_ARRAY_LENGTH = 2;

    private String fieldName;

    private String fieldValue;

    private VlanScopeChecker() {
    }

    /**
     * Check the vlan scope.<br>
     * 
     * @param model The service model
     * @param field The field to be checked
     * @throws ServiceException when checking the field failed
     * @since SDNO 0.5
     */
    public static void checkVlanScope(final SvcModel model, final Field field) throws ServiceException {
        new VlanScopeChecker().doCheckVlanScope(model, field);
    }

    private void doCheckVlanScope(final SvcModel model, final Field field) throws ServiceException {
        final VlanScopeDesc vlanScopeDesc = field.getAnnotation(VlanScopeDesc.class);
        if(null == vlanScopeDesc) {
            return;
        }

        this.fieldName = field.getName();
        if(field.getType() == String.class) {
            mCheckVlanScope(ReflectTool.readVal(model, fieldName));
        } else {
            throw CheckerUtils.getUnSupportedFieldTypeServiceException(VlanScopeChecker.class, model, fieldName);
        }
    }

    private ServiceException getVlanScopeInvaildServiceException() {
        return ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(CHECKER_VLAN_SCOPE_INVALID,
                new String[] {fieldValue});
    }

    private void mCheckVlanId(final String... vlanIds) throws ServiceException {
        for(final String vlanId : vlanIds) {
            final int vlanInt = NumberUtils.toInt(vlanId);
            if((vlanInt < 1) || (vlanInt > 4094)) {
                throw getVlanScopeInvaildServiceException();
            }
        }
    }

    private void mCheckVlanScope(final Object vlanScope) throws ServiceException {
        if(null == vlanScope) {
            return;
        }

        this.fieldValue = String.valueOf(vlanScope);

        if(!StringUtils.containsOnly(fieldValue, VLAN_VALID_CHARS)) {
            throw getVlanScopeInvaildServiceException();
        }

        final String[] vlanSegments = StringUtils.split(fieldValue, VLAN_SEGMENTS_SPLIT);
        for(final String vlanSegment : vlanSegments) {
            mCheckVlanSegment(vlanSegment);
        }
    }

    private void mCheckVlanSegment(final String vlanSegment) throws ServiceException {
        if(StringUtils.contains(vlanSegment, VLAN_RANGE_SPLIT)) {
            final String[] vlanSegmentStartEnd = StringUtils.split(vlanSegment, VLAN_RANGE_SPLIT);
            if(!(vlanSegmentStartEnd.length == VLAN_SEGMENT_ARRAY_LENGTH)) {
                throw getVlanScopeInvaildServiceException();
            }

            mCheckVlanId(vlanSegmentStartEnd);

            if(Integer.parseInt(vlanSegmentStartEnd[0]) > Integer.parseInt(vlanSegmentStartEnd[1])) {
                throw getVlanScopeInvaildServiceException();
            }
        } else {
            mCheckVlanId(vlanSegment);
        }
    }
}
