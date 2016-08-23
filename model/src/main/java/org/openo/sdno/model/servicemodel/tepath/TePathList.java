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

package org.openo.sdno.model.servicemodel.tepath;

import java.util.List;

/**
 * TE path list class.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 4, 2016
 */
public class TePathList {

    private String direction;

    private List<TePath> tePaths;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<TePath> getTePaths() {
        return tePaths;
    }

    public void setTePaths(List<TePath> tePaths) {
        this.tePaths = tePaths;
    }

}
