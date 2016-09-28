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
import org.openo.sdno.model.uniformsbi.comnontypes.enums.RouteType;

/**
 * Route model class.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 4, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Route {

    private String type;

    private StaticRoutes staticRoutes;

    private BgpRoutes bgpRoutes;

    private IsisRoute isisRoute;

    /**
     * Constructor<br>
     * 
     * @param staticRoutes Static routes to be set
     * @since SDNO 0.5
     */
    public Route(StaticRoutes staticRoutes) {
        this.type = RouteType.STATIC.getName();
        this.staticRoutes = staticRoutes;
    }

    /**
     * Constructor<br>
     * 
     * @param bgpRoutes BGP routes to be set
     * @since SDNO 0.5
     */
    public Route(BgpRoutes bgpRoutes) {
        this.type = RouteType.BGP.getName();
        this.bgpRoutes = bgpRoutes;
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public Route() {
        // a empty constructor that construct a object without set any thing
    }

    public String getType() {
        return type;
    }

    public void setRouteType(String type) {
        this.type = type;
    }

    public StaticRoutes getStaticRoutes() {
        return staticRoutes;
    }

    public void setStaticRoutes(StaticRoutes staticRoutes) {
        this.staticRoutes = staticRoutes;
    }

    public BgpRoutes getBgpRoutes() {
        return bgpRoutes;
    }

    public void setBgpRoutes(BgpRoutes bgpRoutes) {
        this.bgpRoutes = bgpRoutes;
    }

    public IsisRoute getIsisRoute() {
        return isisRoute;
    }

    public void setIsisRoute(IsisRoute isisRoute) {
        this.isisRoute = isisRoute;
    }

}
