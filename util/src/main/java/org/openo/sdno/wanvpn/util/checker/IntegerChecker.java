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
import org.openo.sdno.model.paradesc.IntegerDesc;
import org.openo.sdno.model.servicemodel.SvcModel;

/**
 * Check the integer field.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class IntegerChecker {

    private IntegerChecker() {
    }

    /**
     * Check the integer field.<br>
     * 
     * @param model The service model
     * @param field The field object
     * @since SDNO 0.5
     */
    public static void checkInteger(final SvcModel model, final Field field) throws ServiceException {
        final IntegerDesc integerDesc = field.getAnnotation(IntegerDesc.class);
        if(integerDesc == null) {
            return;
        }
        final String fieldName = field.getName();
        if(checkFieldType(field)) {
            final Object val = ReflectTool.readVal(model, fieldName);
            doCheckInteger(integerDesc, val, fieldName);
        } else if(Collection.class.isAssignableFrom(field.getType())) {
            final Collection<?> collection = (Collection<?>)ReflectTool.readVal(model, fieldName);
            if(collection == null) {
                return;
            }
            final Iterator<?> it = collection.iterator();
            while(it.hasNext()) {
                final Object obj = it.next();
                if(obj == null) {
                    continue;
                } else if(obj instanceof Integer || obj instanceof Long) {
                    doCheckInteger(integerDesc, obj, fieldName);
                } else {
                    throw CheckerUtils.getUnSupportedFieldTypeServiceException(IntegerChecker.class, model, fieldName);
                }
            }
        } else {
            throw CheckerUtils.getUnSupportedFieldTypeServiceException(IntegerChecker.class, model, fieldName);
        }
    }

    private static boolean checkFieldType(Field field) {
        final Class<?> type = field.getType();
        if(type == Integer.class || type == Long.class || type == int.class || type == long.class) {
            return true;
        }
        return false;
    }

    private static void doCheckInteger(final IntegerDesc integerDesc, final Object val, final String fieldName)
            throws ServiceException {
        final long minVal = integerDesc.minVal();
        final long maxVal = integerDesc.maxVal();

        if(val instanceof Long) {
            final Long longVal = (Long)val;
            if(checkValValue(integerDesc, longVal.longValue())) {
                return;
            }
            if(longVal.longValue() < minVal || longVal.longValue() > maxVal) {
                throw new ServiceException("the value of " + fieldName + ":" + longVal + " is out of range.", 400);
            }
        }
        if(val instanceof Integer) {
            final Integer intVal = (Integer)val;
            if(checkValValue(integerDesc, intVal.longValue())) {
                return;
            }
            if(intVal.longValue() < minVal || intVal.longValue() > maxVal) {
                throw new ServiceException("the value of " + fieldName + ":" + intVal + " is out of range.", 400);
            }
        }
    }

    private static boolean checkValValue(final IntegerDesc integerDesc, long value) {
        final boolean hasDefault = integerDesc.hasDefault();
        
        if(hasDefault && integerDesc.defaultVal() == value) {
            return true;
        }
        return false;
    }
}
