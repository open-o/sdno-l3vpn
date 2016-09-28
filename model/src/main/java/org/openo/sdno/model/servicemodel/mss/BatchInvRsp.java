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

package org.openo.sdno.model.servicemodel.mss;

import java.util.List;
import java.util.Map;

/**
 * Return object for batch operation.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
public class BatchInvRsp {

    private String retcode;

    private String message;

    private int row;

    private List<Map<String, Object>> data;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(final String retcode) {
        this.retcode = retcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public int getRow() {
        return row;
    }

    public void setRow(final int row) {
        this.row = row;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(final List<Map<String, Object>> data) {
        this.data = data;
    }

}
