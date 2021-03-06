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

package org.openo.sdno.model.uniformsbi.l3vpn;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Static route class.<br>
 *
 * @author
 * @version SDNO 0.5 July 22, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class StaticRoute {

    private String ipPrefix;

    private String nextHop;

    private Integer routePreference;

    private String trackBfdEnable;

    public String getIpPrefix() {
        return ipPrefix;
    }

    public void setIpPrefix(String ipPrefix) {
        this.ipPrefix = ipPrefix;
    }

    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }

    /**
     * @return Returns the routePreference.
     */
    public Integer getRoutePreference() {
        return routePreference;
    }

    /**
     * @param routePreference The routePreference to set.
     */
    public void setRoutePreference(Integer routePreference) {
        this.routePreference = routePreference;
    }

    /**
     * @return Returns the trackBfdEnable.
     */
    public String getTrackBfdEnable() {
        return trackBfdEnable;
    }

    /**
     * @param trackBfdEnable The trackBfdEnable to set.
     */
    public void setTrackBfdEnable(String trackBfdEnable) {
        this.trackBfdEnable = trackBfdEnable;
    }

}
