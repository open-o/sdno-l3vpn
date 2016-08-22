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

package org.openo.sdno.wanvpn.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.model.servicemodel.mss.annotation.MOResType;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Data access object util class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public final class DaoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoUtil.class);

    private static final int MAX_LIST_SIZE = 500;

    private DaoUtil() {

    }

    /**
     * Add a collection to another collection.<br/>
     * 
     * @param fromClctn collection
     * @param toClctn another collection
     * @since SDNO 0.5
     */
    public static <T> void addCollections(Collection<T> fromClctn, Collection<T> toClctn) {
        if(fromClctn != null && toClctn != null) {
            toClctn.addAll(fromClctn);
        }
    }

    /**
     * Batch convert MOs to POs.<br/>
     * 
     * @param mos list of MOs
     * @param poClass PO type
     * @return list of POs
     * @since SDNO 0.5
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends PoModel> List<T> batchMoConvert(List<? extends SvcModel> mos, Class<T> poClass) {
        final List<T> pos = new ArrayList<T>();
        if(mos == null) {
            return pos;
        }
        try {
            for(final SvcModel mo : mos) {
                final T po = poClass.newInstance();
                po.fromSvcModel(mo);
                pos.add(po);
            }
        } catch(final InstantiationException e) {
            DaoUtil.LOGGER.error("batchMoConvert", e);
            return null;
        } catch(final IllegalAccessException e) {
            DaoUtil.LOGGER.error("batchMoConvert", e);
            return null;
        }
        return pos;
    }

    /**
     * Batch convert POs to MOs.<br/>
     * 
     * @param pos list of POs
     * @param moClass MO type
     * @return list of MOs
     * @since SDNO 0.5
     */
    public static <T extends SvcModel> List<T> batchPoConvert(List<? extends PoModel<T>> pos, Class<T> moClass) {
        final List<T> mos = new ArrayList<T>();
        if(pos == null) {
            return mos;
        }
        for(final PoModel<T> po : pos) {
            final T mo = po.toSvcModel();
            mos.add(mo);
        }
        return mos;
    }

    @SuppressWarnings("rawtypes")
    static void fillUuid(List<? extends PoModel> pos, List<String> uuids) {
        if(CollectionUtils.isEmpty(pos) || CollectionUtils.isEmpty(uuids)) {
            return;
        }
        if(pos.size() != uuids.size()) {
            throw new IllegalArgumentException("parametets size is not euqal");
        }
        for(int i = 0; i < pos.size(); i++) {
            final PoModel po = pos.get(i);
            po.setUuid(uuids.get(i));
        }
    }

    private static Method findGetMethod(Class<?> clazz, Field field) {
        final String methodsName = "get" + field.getName();
        final Method[] methods = DaoUtil.getMethods(clazz);
        for(final Method method : methods) {
            if(!method.getName().equalsIgnoreCase(methodsName)) {
                continue;
            }
            if(method.getReturnType() != field.getType()) {
                continue;
            }
            if(method.getParameterTypes().length != 0) {
                continue;
            }
            return method;
        }
        return null;
    }

    static Object getFieldVal(Object target, String fieldName) throws ServiceException {
        final Class<?> clazz = target.getClass();
        try {
            final Method getMethod = DaoUtil.findGetMethod(clazz, clazz.getDeclaredField(fieldName));
            if(null == getMethod) {
                throw new ServiceException("unsupported field" + fieldName);
            }
            return getMethod.invoke(target, new Object[] {});
        } catch(final Exception ex) {
            throw new ServiceException("500", ex);
        }
    }

    private static Method[] getMethods(Class<?> clazz) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> superClazz = clazz.getSuperclass();
        while(superClazz != null) {
            methods.addAll(Arrays.asList(superClazz.getDeclaredMethods()));
            superClazz = superClazz.getSuperclass();
        }
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        return methods.toArray(new Method[methods.size()]);
    }

    static String getTableName(Class<?> clazz) {
        final MOResType moResType = clazz.getAnnotation(MOResType.class);
        if(moResType != null) {
            return moResType.infoModelName();
        }
        return clazz.getName();
    }

    /**
     * Get UUID from list of MOs.<br/>
     * 
     * @param mos list of MOs.
     * @return UUIDs
     * @since SDNO 0.5
     */
    public static List<String> getUuids(List<? extends SvcModel> mos) {
        final List<String> uuids = new ArrayList<String>();
        if(CollectionUtils.isEmpty(mos)) {
            return uuids;
        }
        for(final SvcModel mo : mos) {
            uuids.add(mo == null ? null : mo.getUuid());
        }
        return uuids;
    }

    /**
     * Get PO model's UUIDs.<br/>
     * 
     * @param pos list of POs.
     * @return UUIDs
     * @since SDNO 0.5
     */
    public static List<String> getPoModelUuids(List<? extends PoModel> pos) {
        final List<String> uuids = new ArrayList<>(pos.size());
        if(CollectionUtils.isEmpty(pos)) {
            return uuids;
        }
        for(final PoModel poModel : pos) {
            uuids.add(poModel.getUuid());
        }
        return uuids;
    }

    /**
     * Reset MO's UUID.<br/>
     * 
     * @param mo MO
     * @return new UUID
     * @since SDNO 0.5
     */
    public static String resetUuid(SvcModel mo) {
        if(mo == null) {
            return null;
        }
        final String uuid = UuidUtils.createUuid();
        mo.setUuid(uuid);
        return uuid;
    }

    /**
     * Batch reset MOs' UUIDs.<br/>
     * 
     * @param mos list of MOs
     * @return new UUIDs
     * @since SDNO 0.5
     */
    public static List<String> resetUuids(List<? extends SvcModel> mos) {
        if(mos == null) {
            return new ArrayList<String>();
        }
        final List<String> uuids = new ArrayList<String>(mos.size());
        for(final SvcModel mo : mos) {
            if(mo == null) {
                throw new IllegalArgumentException("there is null element in list");
            }
            final String uuid = UuidUtils.createUuid();
            uuids.add(uuid);
            mo.setUuid(uuid);
        }
        return uuids;
    }

    /**
     * Check the input list is null or not. If it's null, return a new list object.<br/>
     * 
     * @param list list
     * @return list object
     * @since SDNO 0.5
     */
    public static <T> List<T> safeList(List<T> list) {
        if(list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    /**
     * Set the UUIDs of MOs if they are empty.<br/>
     * 
     * @param mos list of MOs
     * @return UUIDs
     * @since SDNO 0.5
     */
    public static List<String> setUuidIfEmpty(List<? extends SvcModel> mos) {
        if(mos == null) {
            return new ArrayList<String>();
        }
        final List<String> uuids = new ArrayList<String>(mos.size());
        for(final SvcModel mo : mos) {
            if(mo == null) {
                throw new IllegalArgumentException("there is null element in list");
            }
            if(StringUtils.hasLength(mo.getUuid())) {
                uuids.add(mo.getUuid());
                continue;
            }
            final String uuid = UuidUtils.createUuid();
            uuids.add(uuid);
            mo.setUuid(uuid);
        }
        return uuids;
    }

    static <T> List<List<T>> splitList(List<T> list) {
        return DaoUtil.splitList(list, DaoUtil.MAX_LIST_SIZE);
    }

    static <T> List<List<T>> splitList(List<T> list, int maxSize) {
        if(maxSize <= 0) {
            throw new IllegalArgumentException("maxSize should bigger than zero");
        }
        if(list == null) {
            return Collections.emptyList();
        }
        int count = list.size() / maxSize;
        if(count * maxSize < list.size()) {
            count++;
        }
        List<List<T>> rst = new ArrayList<List<T>>(count);
        for(int i = 0; i < count; i++) {
            int toIndex = (i + 1) * maxSize;
            toIndex = toIndex > list.size() ? list.size() : toIndex;
            rst.add(list.subList(i * maxSize, toIndex));
        }
        return rst;
    }

    /**
     * Update slave MO.<br/>
     * 
     * @param mastMo master MO
     * @param slaveMo slave MO
     * @param mastDao DAO of master MO
     * @param slaveDao DAO of slave MO
     * @param slaveIdName slave PO ID in master PO
     * @return boolean
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    
    public static <M extends SvcModel, S extends SvcModel, P extends PoModel<M>, O extends PoModel<S>>
            boolean updateSlaveMo(M mastMo, S slaveMo, DefaultDao<P, M> mastDao,
                    DefaultDao<O, S> slaveDao, String slaveIdName) throws ServiceException {
        O oldSlavePo = null;
        final P mastPo = mastDao.selectById(mastMo.getUuid());
        if(null == mastPo) {
            throw new ServiceException("Invalid mo uuid");
        }
        final String slaveUuid = (String)DaoUtil.getFieldVal(mastPo, slaveIdName);
        if(StringUtils.hasLength(slaveUuid)) {
            oldSlavePo = slaveDao.selectById(slaveUuid);
        }

        if(slaveMo == null) {
            // delete old PO
            if(oldSlavePo != null) {
                final S oldSlaveMo = slaveDao.getMoById(oldSlavePo.getUuid());
                return slaveDao.delMos(Collections.singletonList(oldSlaveMo));
            }

            // Slave MO not exist
            return true;
        }

        // Newly added Slave MO
        if(oldSlavePo == null) {
            DaoUtil.resetUuid(slaveMo);
            slaveDao.addMos(Collections.singletonList(slaveMo));
            return true;
        }

        // UPDATE
        slaveMo.setUuid(oldSlavePo.getUuid());
        return slaveDao.updateMos(Collections.singletonList(slaveMo));
    }

}
