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

package org.openo.sdno.model.servicemodel.tepath;

import java.util.List;

/**
 * 
 * TE path class<br/>
 * 
 * @author
 * @version     SDNO 0.5  Aug 4, 2016
 */

public class TePath {

    private String pathRole;

    private String pathStatus;

    private String ingressNeid;

    private String egressNeid;

    private List<ServiceTePath> pathList;

    public List<ServiceTePath> getPathList() {
        return pathList;
    }

    public void setPathList(List<ServiceTePath> pathList) {
        this.pathList = pathList;
    }

    public String getPathRole() {
        return pathRole;
    }

    public void setPathRole(String pathRole) {
        this.pathRole = pathRole;
    }

    public String getPathStatus() {
        return pathStatus;
    }

    public void setPathStatus(String pathStatus) {
        this.pathStatus = pathStatus;
    }

    public String getIngressNeid() {
        return ingressNeid;
    }

    public void setIngressNeid(String ingressNeid) {
        this.ingressNeid = ingressNeid;
    }

    public String getEgressNeid() {
        return egressNeid;
    }

    public void setEgressNeid(String egressNeid) {
        this.egressNeid = egressNeid;
    }

}
