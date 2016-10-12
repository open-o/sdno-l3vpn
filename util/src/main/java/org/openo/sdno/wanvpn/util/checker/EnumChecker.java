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

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.paradesc.EnumDesc;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.wanvpn.util.EnumUtil;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;

/**
 * Check the field of enum.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class EnumChecker {

    private EnumChecker() {
    }

    /**
     * Check the field of enumeration.<br>
     * 
     * @param model The service model
     * @param field The field to be checked
     * @throws ServiceException when the field class is not matched or the enum item is not string
     *             type or instance of string type
     * @since SDNO 0.5
     */
    public static void checkEnum(final SvcModel model, final Field field) throws ServiceException {
        final EnumDesc enumDesc = field.getAnnotation(EnumDesc.class);
        if(enumDesc == null) {
            return;
        }
        final Class<? extends Enum<?>>[] enumClasses = enumDesc.value();
        final String fieldName = field.getName();
        if(field.getType() == String.class) {
            final String commonName = (String)ReflectTool.readVal(model, fieldName);
            doCheckEnum(enumClasses, commonName, fieldName);
        } else if(Collection.class.isAssignableFrom(field.getType())) {
            final Collection<?> enumCollection = (Collection<?>)ReflectTool.readVal(model, fieldName);
            if(enumCollection == null) {
                return;
            }
            final Iterator<?> enumIt = enumCollection.iterator();
            while(enumIt.hasNext()) {
                final Object obj = enumIt.next();
                if(obj == null) {
                    continue;
                } else if(obj instanceof String) {
                    doCheckEnum(enumClasses, (String)obj, fieldName);
                } else {
                    throw CheckerUtils.getUnSupportedFieldTypeServiceException(EnumChecker.class, model, fieldName);
                }
            }
        } else {
            throw CheckerUtils.getUnSupportedFieldTypeServiceException(EnumChecker.class, model, fieldName);
        }
    }

    @SuppressWarnings("unchecked")
    private static void doCheckEnum(final Class<? extends Enum<?>>[] enumClasses, final String commonName,
            final String fieldName) throws ServiceException {
        if(commonName == null) {
            return;
        }
        for(final Class<? extends Enum<?>> enumClasse : enumClasses) {
            final Enum<?> ev = EnumUtil.valueOf(cast(enumClasse), commonName);
            if(ev != null) {
                return;
            }
        }
        throw ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(
                CommonErrorCode.CHECKER_ENUM_OUT_OF_RANGE, new String[] {fieldName, commonName});
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T extends Enum> Class<T> cast(final Class<? extends Enum<?>> e) {
        return (Class<T>)e;
    }
}
