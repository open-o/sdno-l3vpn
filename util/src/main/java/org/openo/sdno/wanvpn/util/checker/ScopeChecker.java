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

package org.openo.sdno.wanvpn.util.checker;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.SvcModel;

/**
 * Check the scope.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class ScopeChecker {

    private ScopeChecker() {
    }

    /**
     * Check the scope<br>
     * 
     * @param model The service model
     * @throws ServiceException when the field check failed
     * @since SDNO 0.5
     */
    public static void checkScope(final SvcModel model) throws ServiceException {
        if(model == null) {
            return;
        }
        final Field[] fields = ReflectTool.getAllFields(model.getClass());
        for(final Field field : fields) {
            NotNullChecker.checkNotNull(model, field);
            EnumChecker.checkEnum(model, field);
            StringChecker.checkStirng(model, field);
            IntegerChecker.checkInteger(model, field);
            IPChecker.checkIP(model, field);
            VlanScopeChecker.checkVlanScope(model, field);
            if(SvcModel.class.isAssignableFrom(field.getType())) {
                final SvcModel subVal = (SvcModel)ReflectTool.readVal(model, field.getName());
                checkScope(subVal);
            } else if(Collection.class.isAssignableFrom(field.getType())) {
                final Collection<?> collection = (Collection<?>)ReflectTool.readVal(model, field.getName());
                checkCollection(collection);
            } else {
                continue;
            }
        }
    }

    private static void checkCollection(final Collection<?> collection) throws ServiceException {
        if(collection == null) {
            return;
        }
        final Iterator<?> it = collection.iterator();
        while(it.hasNext()) {
            final Object obj = it.next();
            if(obj instanceof SvcModel) {
                checkScope((SvcModel)obj);
                continue;
            }
            if(obj instanceof Collection) {
                checkCollection((Collection<?>)obj);
                continue;
            }
        }
    }

}
