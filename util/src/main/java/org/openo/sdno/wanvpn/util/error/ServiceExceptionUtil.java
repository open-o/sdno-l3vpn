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

package org.openo.sdno.wanvpn.util.error;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;

/**
 * Service exception util class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class ServiceExceptionUtil {

    public static final int NOT_FOUND = 404;

    public static final int BAD_REQUEST = 400;

    private ServiceExceptionUtil() {
    }

    /**
     * Create bad request service exception(HTTP Code 400) according to error code.<br/>
     * 
     * @param errCode error code
     * @return bad request service exception
     * @since SDNO 0.5
     */
    public static final ServiceException getBadRequestServiceException(final String errCode) {
        return getBadRequestServiceException(errCode, null);
    }

    /**
     * Create bad request service exception(HTTP Code 400) according to error code and
     * ExceptionArgs.<br/>
     * 
     * @param errCode error code
     * @param exceptionArgs exception info parameters
     * @return bad request service exception
     * @since SDNO 0.5
     */
    public static final ServiceException getBadRequestServiceException(final String errCode,
            final ExceptionArgs exceptionArgs) {
        return getServiceException(errCode, BAD_REQUEST, exceptionArgs);
    }

    /**
     * Create bad request service exception(HTTP Code 400) according to error code and
     * commonArgs.<br/>
     * 
     * @param errCode error code
     * @param commonArgs Uniform exception information parameter
     * @return bad request service exception
     * @since SDNO 0.5
     */
    public static final ServiceException getBadRequestServiceExceptionWithCommonArgs(final String errCode,
            final String[] commonArgs) {
        final ExceptionArgs exceptionArgs = new ExceptionArgs(commonArgs, commonArgs, commonArgs, commonArgs);
        return getBadRequestServiceException(errCode, exceptionArgs);
    }

    /**
     * Create service exception(HTTP Code 500) according to error code.<br/>
     * 
     * @param errCode error code
     * @return service exception
     * @since SDNO 0.5
     */
    public static final ServiceException getServiceException(final String errCode) {
        return getServiceException(errCode, null);
    }

    /**
     * Create service exception(HTTP Code 500) according to error code and exceptionArgs.<br/>
     * 
     * @param errCode error code
     * @param exceptionArgs exception info parameters
     * @return service exception
     * @since SDNO 0.5
     */
    private static final ServiceException getServiceException(final String errCode, final ExceptionArgs exceptionArgs) {
        return mGetServiceException(String.valueOf(errCode), exceptionArgs);
    }

    /**
     * Create service exception according to error code and HTTP status code.<br/>
     * 
     * @param errCode error code
     * @param httpCode HTTP status code
     * @return service exception
     * @since SDNO 0.5
     */
    public static final ServiceException getServiceException(final String errCode, final int httpCode) {
        return getServiceException(errCode, httpCode, null);
    }

    /**
     * Create service exception according to error code, HTTP status code and ExceptionArgs.<br/>
     * 
     * @param errCode error code
     * @param httpCode HTTP status code
     * @param exceptionArgs exception info parameters
     * @return service exception
     * @since SDNO 0.5
     */
    private static final ServiceException getServiceException(final String errCode, final int httpCode,
            final ExceptionArgs exceptionArgs) {
        final ServiceException ex = getServiceException(errCode, exceptionArgs);
        ex.setHttpCode(httpCode);
        return ex;
    }

    /**
     * Create service exception according to error code and commonArgs.<br/>
     * 
     * @param errCode error code
     * @param commonArgs Uniform exception information parameter
     * @return service exception
     * @since SDNO 0.5
     */
    public static final ServiceException getServiceExceptionWithCommonArgs(final String errCode,
            final String[] commonArgs) {
        final ExceptionArgs exceptionArgs = new ExceptionArgs();
        exceptionArgs.setDescArgs(commonArgs);
        exceptionArgs.setDetailArgs(commonArgs);
        exceptionArgs.setReasonArgs(commonArgs);
        exceptionArgs.setAdviceArgs(commonArgs);
        return mGetServiceException(String.valueOf(errCode), exceptionArgs);
    }

    private static final ServiceException mGetServiceException(final String errCode,
            final ExceptionArgs exceptionArgs) {
        final ServiceException ex = new ServiceException(errCode, EMPTY);
        if(null != exceptionArgs) {
            ex.setExceptionArgs(exceptionArgs);
        }
        return ex;
    }

    /**
     * Throw a not found exception.<br/>
     * 
     * @throws ServiceException when called
     * @since SDNO 0.5
     */
    public static final void throwNotFoundException() throws ServiceException {
        throw new ServiceException(ServiceException.DEFAULT_ID, NOT_FOUND);
    }

}
