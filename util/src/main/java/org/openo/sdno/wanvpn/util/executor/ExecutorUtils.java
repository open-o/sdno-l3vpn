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

package org.openo.sdno.wanvpn.util.executor;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;

/**
 * Executor util class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class ExecutorUtils {

    private ExecutorUtils() {
    }

    /**
     * Check UUID.<br/>
     * 
     * @param uuid UUID
     * @throws ServiceException when UUID is invalid
     * @since SDNO 0.5
     */
    public static void assertUUID(final String uuid) throws ServiceException {
        if(!validate(uuid)) {
            throw ServiceExceptionUtil.getBadRequestServiceExceptionWithCommonArgs(CommonErrorCode.UUID_INVALID,
                    new String[] {String.valueOf(uuid)});
        }
    }

    private static boolean validate(String uuid) {
        return uuid != null && !uuid.isEmpty() && uuid.length() <= 36;
    }

}
