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

package org.openo.sdno.model.uniformsbi.base;

/**
 * <br>
 *
 * @author zhaozhongchao@huawei.com
 * @version SDNO 0.5 Dec 6, 2016
 */
public class QosIfPhb {

    private Boolean trust8021p;

    private Boolean trustUpstream;

    private Boolean phbMap;

    /**
     * @return Returns the trust8021p.
     */
    public Boolean getTrust8021p() {
        return trust8021p;
    }

    /**
     * @param trust8021p The trust8021p to set.
     */
    public void setTrust8021p(Boolean trust8021p) {
        this.trust8021p = trust8021p;
    }

    /**
     * @return Returns the trustUpstream.
     */
    public Boolean getTrustUpstream() {
        return trustUpstream;
    }

    /**
     * @param trustUpstream The trustUpstream to set.
     */
    public void setTrustUpstream(Boolean trustUpstream) {
        this.trustUpstream = trustUpstream;
    }

    /**
     * @return Returns the phbMap.
     */
    public Boolean getPhbMap() {
        return phbMap;
    }

    /**
     * @param phbMap The phbMap to set.
     */
    public void setPhbMap(Boolean phbMap) {
        this.phbMap = phbMap;
    }

}
