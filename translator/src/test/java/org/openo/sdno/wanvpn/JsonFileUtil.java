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

package org.openo.sdno.wanvpn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 为保证编译通过 ，临时添加的文件。
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version SDNO 0.5 Jul 29, 2016
 */
public class JsonFileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileUtil.class);

    public static String getJsonString(String filePath) {
        File file = new File(filePath);

        return getJsonString(file);
    }

    public static String getJsonString(File file) {
        String jsonString = "";
        FileInputStream fileStream;
        try {
            fileStream = new FileInputStream(file);
            jsonString = IOUtils.toString(fileStream);
        } catch(FileNotFoundException e) {
            LOGGER.error("Read from file in path failed!!", e);

        } catch(IOException e) {
            LOGGER.error("Read from file in path failed!!", e);
        }

        return jsonString;
    }

}
