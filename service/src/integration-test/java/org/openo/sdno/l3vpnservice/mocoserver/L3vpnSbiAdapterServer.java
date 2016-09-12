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

package org.openo.sdno.l3vpnservice.mocoserver;

import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.uniformsbi.base.AdapterResponseInfo;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.moco.MocoHttpServer;
import org.openo.sdno.testframework.moco.responsehandler.MocoResponseHandler;

public class L3vpnSbiAdapterServer extends MocoHttpServer {

    private static final String CREATE_L3VPN_FILE = "src/integration-test/resources/l3vpnsbiadapter/createl3vpn.json";

    private static final String QUERY_L3VPN_DETAIL_FILE =
            "src/integration-test/resources/l3vpnsbiadapter/queryl3vpndetail.json";

    private static final String DELETE_L3VPN_FILE = "src/integration-test/resources/l3vpnsbiadapter/deletel3vpn.json";

    private static final String UPDATE_L3VPN_FILE = "src/integration-test/resources/l3vpnsbiadapter/updatel3vpn.json";

    private static final String ADD_TPS_FILE = "src/integration-test/resources/l3vpnsbiadapter/addtps.json";

    private static final String DELELE_TPS_FILE = "src/integration-test/resources/l3vpnsbiadapter/deletetps.json";

    @Override
    public void addRequestResponsePairs() {
        this.addRequestResponsePair(CREATE_L3VPN_FILE);
        this.addRequestResponsePair(QUERY_L3VPN_DETAIL_FILE, new QueryL3VpnDetailResponseHandler());
        this.addRequestResponsePair(DELETE_L3VPN_FILE);
        this.addRequestResponsePair(UPDATE_L3VPN_FILE);
        this.addRequestResponsePair(ADD_TPS_FILE);
        this.addRequestResponsePair(DELELE_TPS_FILE);
    }

    private class QueryL3VpnDetailResponseHandler extends MocoResponseHandler {

        @Override
        public void processRequestandResponse(HttpRquestResponse httpObject) {

            HttpResponse httpResponse = httpObject.getResponse();
            MocoL3vpnAdapterResponseInfo l3VpnResponse =
                    JsonUtil.fromJson(httpResponse.getData(), MocoL3vpnAdapterResponseInfo.class);

            AdapterResponseInfo responseInfo = new AdapterResponseInfo();
            responseInfo.setFormat(l3VpnResponse.getFormat());
            responseInfo.setRespHeaders(l3VpnResponse.getRespHeaders());
            responseInfo.setRet(l3VpnResponse.getRet());
            responseInfo.setMsg(JsonUtil.toJson(l3VpnResponse.getMsg()));

            httpResponse.setData(JsonUtil.toJson(responseInfo));
        }
    }

}
