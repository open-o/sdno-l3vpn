
package org.openo.sdno.model.db;

import org.openo.sdno.model.paradesc.EnumDesc;
import org.openo.sdno.model.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.LabelModeType;

public class ObjectA extends AbstractSvcModel {

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String id;

    @EnumDesc(LabelModeType.class)
    private String labelMode;

    private String frrEnable;

    private String ahave;

    private String onlyA;

    public int getOnlyA() {
        return 1;
    }

    public int getOnlyA(int i) {
        return 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelMode() {
        return labelMode;
    }

    public void setLabelMode(String labelMode) {
        this.labelMode = labelMode;
    }

    public void setFrrEnable(String frrEnable) {
        this.frrEnable = frrEnable;
    }

    @Override
    public String getUuid() {
        return id;
    }

    @Override
    public void setUuid(String uuid) {
        id = uuid;
    }

}
