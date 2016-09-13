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
/*
 * Title:        NetMatrix V1R3C00<br>
 * Description:  [描述模块的功能、作用、使用方法和注意事项]<br>
 * Company:      Huawei Tech. Co., Ltd<br>
 * @author       h00313442
 * @version      1.0  2016-4-8
 */

package org.openo.sdno.wanvpn.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.openo.baseservice.remoteservice.exception.ServiceException;

/**
 * The tools class of url encoder.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class URLEncoderUtil {

    private static final String UTF_8 = "UTF-8";

    private URLEncoderUtil() {
    }

    /**
     * Encode the url with utf_8.<br>
     * 
     * @param s The url
     * @return The the encoded url with utf_8
     * @since SDNO 0.5
     */
    public static String encode(String s) throws ServiceException {
        try {
            return URLEncoder.encode(s, URLEncoderUtil.UTF_8);
        } catch(UnsupportedEncodingException e) {
            throw new ServiceException(e);
        }
    }
}
