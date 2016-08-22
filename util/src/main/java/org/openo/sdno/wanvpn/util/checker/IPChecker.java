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

package org.openo.sdno.wanvpn.util.checker;

import static org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.paradesc.IPDesc;
import org.openo.sdno.model.paradesc.IPDesc.IPType;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.util.ip.IpUtils;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;

/**
 * Check the Ip field.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class IPChecker {

    private IPChecker() {
    }

    /**
     * Check IP field.<br/>
     * 
     * @param model The service model
     * @param field The field to be checked
     * @throws ServiceException when the field class is not matched or the IP item is not string
     *             type or instance of string type
     * @since SDNO 0.5
     */
    public static void checkIP(final SvcModel model, final Field field) throws ServiceException {
        final IPDesc ipDesc = field.getAnnotation(IPDesc.class);
        if(ipDesc == null) {
            return;
        }

        final String fieldName = field.getName();

        if(field.getType() == String.class) {
            final String strVal = (String)ReflectTool.readVal(model, fieldName);
            doCheckIP(ipDesc, strVal, fieldName);
        } else if(Collection.class.isAssignableFrom(field.getType())) {
            final Collection<?> ipCollection = (Collection<?>)ReflectTool.readVal(model, fieldName);
            if(ipCollection == null) {
                return;
            }
            final Iterator<?> ipIt = ipCollection.iterator();
            while(ipIt.hasNext()) {
                final Object obj = ipIt.next();
                if(obj == null) {
                    continue;
                } else if(obj instanceof String) {
                    doCheckIP(ipDesc, (String)obj, fieldName);
                } else {
                    throw CheckerUtils.getUnSupportedFieldTypeServiceException(IPChecker.class, model, fieldName);
                }
            }
        } else {
            throw CheckerUtils.getUnSupportedFieldTypeServiceException(IPChecker.class, model, fieldName);
        }
    }

    private static void assertIPv4ClassA(final int firstSeg, final String strVal) throws ServiceException {
        if(firstSeg > 127) {
            throw ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(CommonErrorCode.CHECK_IP_NOT_A_CLASS,
                    new String[] {strVal});
        }
    }

    private static void assertIPv4ClassABC(final int firstSeg, final String strVal) throws ServiceException {
        if(firstSeg > 223) {
            throw ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(
                    CommonErrorCode.CHECK_IP_NOT_ABC_CLASS, new String[] {strVal});
        }
    }

    private static void assertIPv4ClassB(final int firstSeg, final String strVal) throws ServiceException {
        if(firstSeg < 128 || firstSeg > 191) {
            throw ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(CommonErrorCode.CHECK_IP_NOT_B_CLASS,
                    new String[] {strVal});
        }
    }

    private static void assertIPv4ClassC(final int firstSeg, final String strVal) throws ServiceException {
        if(firstSeg < 192 || firstSeg > 223) {
            throw ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(CommonErrorCode.CHECK_IP_NOT_C_CLASS,
                    new String[] {strVal});
        }
    }

    private static void assertIPv4ClassD(final int firstSeg, final String strVal) throws ServiceException {
        if(firstSeg < 224 || firstSeg > 239) {
            throw ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(CommonErrorCode.CHECK_IP_NOT_D_CLASS,
                    new String[] {strVal});
        }
    }

    private static void assertIPv4ClassE(final int firstSeg, final String strVal) throws ServiceException {
        if(firstSeg < 240 || firstSeg > 247) {
            throw ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(CommonErrorCode.CHECK_IP_NOT_E_CLASS,
                    new String[] {strVal});
        }
    }

    private static void checkIPType(final IPType ipType, final int firstSeg, final String strVal)
            throws ServiceException {
        switch(ipType) {
            case IPV4_A: {
                assertIPv4ClassA(firstSeg, strVal);
                break;
            }
            case IPV4_B: {
                assertIPv4ClassB(firstSeg, strVal);
                break;
            }
            case IPV4_C: {
                assertIPv4ClassC(firstSeg, strVal);
                break;
            }
            case IPV4_D: {
                assertIPv4ClassD(firstSeg, strVal);
                break;
            }
            case IPV4_E: {
                assertIPv4ClassE(firstSeg, strVal);
                break;
            }
            case IPV4_ABC: {
                assertIPv4ClassABC(firstSeg, strVal);
                break;
            }
            default:
                break;
        }
    }

    private static void checkPattern(final String strVal, final String fieldName, final boolean hasMask)
            throws ServiceException {
        if(hasMask) {
            if(!IpUtils.isValidCidr(strVal)) {
                throw getBadRequestServiceExceptionWithCommonArgs(CommonErrorCode.CHECKER_CIDR_INVALID,
                        new String[] {fieldName, strVal});
            }
        } else {
            if(!IpUtils.isValidAddress(strVal)) {
                throw getBadRequestServiceExceptionWithCommonArgs(CommonErrorCode.CHECKER_IP_INVALID,
                        new String[] {fieldName, strVal});
            }
        }
    }

    private static void doCheckIP(final IPDesc ipDesc, final String strVal, final String fieldName)
            throws ServiceException {
        if(strVal == null) {
            return;
        }
        checkPattern(strVal, fieldName, ipDesc.hasMask());

        final String ipStr = ipDesc.hasMask() ? strVal.substring(0, strVal.indexOf('/')) : strVal;
        checkIPType(ipDesc.ipType(), Integer.parseInt(ipStr.split("\\.")[0]), strVal);
    }

}
