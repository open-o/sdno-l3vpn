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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;

/**
 * The tools of checker to get unsupported service exception.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class CheckerUtils {

    private CheckerUtils() {
    }

    /**
     * Get unsupported field type service exception.<br/>
     * 
     * @param checkerClass Checker class object
     * @param model Service model
     * @param fieldName The field name
     * @return The ServiceException with commonArgs
     * @since SDNO 0.5
     */
    public static ServiceException getUnSupportedFieldTypeServiceException(final Class<?> checkerClass,
            final SvcModel model, final String fieldName) {
        return ServiceExceptionUtil.getServiceExceptionWithCommonArgs(CommonErrorCode.CHECKER_UNSUPPORT_FIELD_TYPE,
                new String[] {checkerClass.getClass().getName(), model.getClass().getName(), fieldName});
    }

}
