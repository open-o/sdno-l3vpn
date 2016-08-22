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

package org.openo.sdno.wanvpn.util.rest;

import java.io.IOException;

import org.apache.commons.httpclient.HttpStatus;
import org.codehaus.jackson.map.ObjectMapper;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ROA input stream parser class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class ROAInputStreamParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ROAInputStreamParser.class);

    private ROAInputStreamParser() {

    }

    /**
     * Covert input JSON stream to the specified object type.<br/>
     * 
     * @param str input JSON stream
     * @param clazz object type
     * @return the request target type's data
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public static <T> T fromJson(String str, Class<T> clazz) throws ServiceException {
        if(str == null || clazz == null) {
            throw new IllegalArgumentException("input or clazz is null");
        }
        return formJsonStr(str, clazz);
    }

    private static <T> T formJsonStr(String jsonStr, Class<T> clazz) throws ServiceException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonStr, clazz);
        } catch(IOException e) {
            LOGGER.error("Fail to parse json ", e);
            throw getBadJsonException();
        }
    }

    private static ServiceException getBadJsonException() {
        return ServiceExceptionUtil.getServiceException(CommonErrorCode.JSON_FORMAT_ERROR, HttpStatus.SC_BAD_REQUEST);
    }
}
