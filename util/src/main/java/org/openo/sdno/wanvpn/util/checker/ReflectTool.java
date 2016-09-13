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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

/**
 * The tools class to reflect the fields.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class ReflectTool {

    private static Object[] EMPTY_ARR = new Object[] {};

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectTool.class);

    private ReflectTool() {
    }

    /**
     * Get all the fields.<br>
     * 
     * @param clazz The class object
     * @return The array of all the fields
     * @since SDNO 0.5
     */
    public static Field[] getAllFields(final Class<?> clazz) {
        final List<Field> fields = new ArrayList<Field>();
        Class<?> superClazz = clazz.getSuperclass();
        while(superClazz != null) {
            fields.addAll(Arrays.asList(superClazz.getDeclaredFields()));
            superClazz = superClazz.getSuperclass();
        }
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return fields.toArray(new Field[fields.size()]);
    }

    /**
     * Read object value.<br>
     * 
     * @param target The target object
     * @param fieldName The field name
     * @return The object value
     * @throws BeansException
     * @since SDNO 0.5
     */
    public static Object readVal(final Object target, final String fieldName) throws ServiceException {
        final PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(target.getClass(), fieldName);
        if(pd == null) {
            return null;
        }
        final Method readMethod = pd.getReadMethod();
        if(readMethod == null) {
            throw new ServiceException(fieldName + " has no read method");
        }
        try {
            return readMethod.invoke(target, EMPTY_ARR);
        } catch(IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("readVal failed", e);
            return null;
        }
    }

    /**
     * Write object value.<br>
     * 
     * @param target The target object
     * @param fieldName The field name
     * @param objectVal The value
     * @throws ServiceException when write fail
     * @since SDNO 0.5
     */
    public static void writeVal(final Object target, final String fieldName, final Object objectVal)
            throws ServiceException {
        final PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(target.getClass(), fieldName);
        if(pd == null) {
            return;
        }
        final Method writeMethod = pd.getWriteMethod();
        if(writeMethod == null) {
            throw new ServiceException(fieldName + " has no write method");
        }

        try {
            writeMethod.invoke(target, objectVal);
        } catch(IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("wirteVal failed", e);
        }
    }
}
