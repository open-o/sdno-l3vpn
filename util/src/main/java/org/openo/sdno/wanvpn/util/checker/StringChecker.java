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

import static org.openo.sdno.wanvpn.util.error.CommonErrorCode.CHECKER_STRING_IS_BLANK;
import static org.openo.sdno.wanvpn.util.error.CommonErrorCode.CHECKER_STRING_IS_EMPTY;
import static org.openo.sdno.wanvpn.util.error.CommonErrorCode.CHECKER_STRING_IS_INVALID;
import static org.openo.sdno.wanvpn.util.error.CommonErrorCode.CHECKER_STRING_OVER_LENGTH;
import static org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.springframework.util.StringUtils;

/**
 * Check the string field.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class StringChecker {

    private StringChecker() {
    }

    /**
     * Check the length of the string value.<br/>
     * 
     * @param minLen The min length
     * @param maxLen The max length
     * @param strVal The string value
     * @param fieldName The field name
     * @since SDNO 0.5
     */
    public static void checkLength(final int minLen, final int maxLen, final String strVal, final String fieldName)
            throws ServiceException {
        if(strVal == null) {
            return;
        }
        if(strVal.length() < minLen || strVal.length() > maxLen) {
            throw getBadRequestServiceExceptionWithCommonArgs(CHECKER_STRING_OVER_LENGTH,
                    new String[] {fieldName, strVal});
        }
    }

    /**
     * Check the string value is not blank.<br/>
     * 
     * @param strVal The string value
     * @param fieldName The field name
     * @since SDNO 0.5
     */
    public static void checkNotBlank(final String strVal, final String fieldName) throws ServiceException {
        if(!StringUtils.hasText(strVal)) {
            throw getBadRequestServiceExceptionWithCommonArgs(CHECKER_STRING_IS_BLANK, new String[] {fieldName});
        }
    }

    /**
     * Check the string value is not empty.<br/>
     * 
     * @param strVal The string value
     * @param fieldName The field name
     * @since SDNO 0.5
     */
    public static void checkNotEmpty(final String strVal, final String fieldName) throws ServiceException {
        if(!StringUtils.hasLength(strVal)) {
            throw getBadRequestServiceExceptionWithCommonArgs(CHECKER_STRING_IS_EMPTY, new String[] {fieldName});
        }
    }

    /**
     * Check the pattern of string value.<br/>
     * 
     * @param pattern The pattern
     * @param strVal The string value
     * @param fieldName The field name
     * @since SDNO 0.5
     */
    public static void checkPattern(final String pattern, final String strVal, final String fieldName)
            throws ServiceException {
        if(strVal == null) {
            return;
        }
        if(!StringUtils.hasText(pattern)) {
            return;
        }
        final Pattern pt = Pattern.compile(pattern);
        final Matcher matcher = pt.matcher(strVal);
        if(!matcher.matches()) {
            throw getBadRequestServiceExceptionWithCommonArgs(CHECKER_STRING_IS_INVALID,
                    new String[] {fieldName, strVal});
        }
    }

    /**
     * Check the string object.<br/>
     * 
     * @param model The service model
     * @param field The field object
     * @since SDNO 0.5
     */
    public static void checkStirng(final SvcModel model, final Field field) throws ServiceException {
        final StringDesc stringDesc = field.getAnnotation(StringDesc.class);
        if(stringDesc == null) {
            return;
        }

        final String fieldName = field.getName();
        if(field.getType() == String.class) {
            final String strVal = (String)ReflectTool.readVal(model, fieldName);
            doCheckString(stringDesc, strVal, field.getName());
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
                } else if(obj instanceof String) {
                    doCheckString(stringDesc, (String)obj, fieldName);
                } else {
                    throw CheckerUtils.getUnSupportedFieldTypeServiceException(StringChecker.class, model, fieldName);
                }
            }
        } else {
            throw CheckerUtils.getUnSupportedFieldTypeServiceException(StringChecker.class, model, fieldName);
        }
    }

    private static void doCheckString(final StringDesc stringDesc, final String strVal, final String fieldName)
            throws ServiceException {
        if(strVal == null) {
            return;
        }
        if(stringDesc.notBlank()) {
            checkNotBlank(strVal, fieldName);
        }

        if(stringDesc.notEmpty()) {
            checkNotEmpty(strVal, fieldName);
        }

        final int minLen = stringDesc.minLen();
        final int maxLen = stringDesc.maxLen();
        checkLength(minLen, maxLen, strVal, fieldName);

        final String pattern = stringDesc.pattern();
        checkPattern(pattern, strVal, fieldName);
    }

}
