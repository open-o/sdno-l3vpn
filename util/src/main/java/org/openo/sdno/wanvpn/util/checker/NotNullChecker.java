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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.paradesc.NotNullDesc;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;

/**
 * Check whether the field is not null.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class NotNullChecker {

    private NotNullChecker() {
    }

    /**
     * Check the field whether is not null.<br>
     * 
     * @param model The service model
     * @param field The field to be checked
     * @throws ServiceException when the field is null
     * @since SDNO 0.5
     */
    public static void checkNotNull(final SvcModel model, final Field field) throws ServiceException {
        final NotNullDesc notNullDesc = field.getAnnotation(NotNullDesc.class);
        if(notNullDesc == null) {
            return;
        }
        final Object val = ReflectTool.readVal(model, field.getName());
        checkNotNull(val, field.getName());
    }

    /**
     * Check whether the object is not null.<br>
     * 
     * @param val The object to be checked
     * @param fieldName The field name
     * @since SDNO 0.5
     */
    private static void checkNotNull(final Object val, final String fieldName) throws ServiceException {
        if(val == null) {
            throw ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(
                    CommonErrorCode.CHECKER_FILED_IS_NULL, new String[] {fieldName});
        }
    }
}
