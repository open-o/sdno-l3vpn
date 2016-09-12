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

package org.openo.sdno.wanvpn.dao;

import java.util.ArrayList;
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.QueryComplexParams;
import org.openo.sdno.wanvpn.inventory.sdk.inf.IInvDAO;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;

public class InvDaoMock<MO extends PoModel> implements IInvDAO<MO> {

    @Override
    public ResultRsp<List<String>> add(final List<MO> moList, final Class<?> moType) throws ServiceException {
        final List<String> uuids = new ArrayList<String>();
        if(moList != null) {
            for(final PoModel mo : moList) {
                uuids.add(mo == null ? null : mo.getUuid());
            }
        }
        return new ResultRsp<List<String>>(ResultRsp.rspSuccess(), uuids);
    }

    @Override
    public ResultRsp<String> delete(final String uuid, final Class moType) throws ServiceException {
        return new ResultRsp<String>(ResultRsp.rspSuccess());
    }

    @Override
    public ResultRsp<List<String>> delete(final List<MO> moList, final Class<?> moType) throws ServiceException {
        return new ResultRsp<List<String>>(ResultRsp.rspSuccess());
    }

    @Override
    public ResultRsp<?> batchDelete(final List<String> uuidList, final Class<?> moType) throws ServiceException {
        return new ResultRsp<MO>(ResultRsp.rspSuccess());
    }

    @Override
    public ResultRsp<?> update(final List<MO> moList, final Class<?> moType) throws ServiceException {
        return new ResultRsp<MO>(ResultRsp.rspSuccess());
    }

    @Override
    public ResultRsp<List<MO>> queryComplex(final Class moType, final QueryComplexParams params)
            throws ServiceException {
        return new ResultRsp<List<MO>>(ResultRsp.rspSuccess());
    }

    @Override
    public ResultRsp<MO> query(final String uuid, final Class moType) throws ServiceException {
        MO mo = null;
        try {
            mo = (MO)moType.newInstance();
            mo.setUuid(uuid);
        } catch(final Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return new ResultRsp<MO>(ResultRsp.rspSuccess(), mo);
    }

    @Override
    public ResultRsp<List<MO>> queryAll(final Class moType, final QueryComplexParams params) throws ServiceException {
        return new ResultRsp<List<MO>>(ResultRsp.rspSuccess());
    }

}
