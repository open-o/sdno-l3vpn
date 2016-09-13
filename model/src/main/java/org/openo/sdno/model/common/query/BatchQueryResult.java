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

package org.openo.sdno.model.common.query;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Batch query result class.<br/>
 * 
 * @param <T> Generic paradigm
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class BatchQueryResult<T> {

    private List<T> data;

    private long totalNum = 0;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param data Data to set
     * @param totalNum totalNum to set
     */
    public BatchQueryResult(final List<T> data, final long totalNum) {
        this.data = data;
        this.totalNum = totalNum;
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    public BatchQueryResult() {
        // empty constructor to construct a object and not set any thing
    }

    public List<T> getData() {
        return data;
    }

    public void setData(final List<T> data) {
        this.data = data;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(final long totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * Null-safe check if the specified collection is empty.<br/>
     * 
     * @return true if empty or null
     * @since SDNO 0.5
     */
    @JsonIgnore
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(data);
    }
}
