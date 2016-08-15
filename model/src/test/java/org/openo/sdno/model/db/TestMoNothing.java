
package org.openo.sdno.model.db;

import org.openo.sdno.model.servicemodel.mss.annotation.MOExtendField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MORelationField;
import org.openo.sdno.model.servicemodel.mss.annotation.NONInvField;

public class TestMoNothing {

    public String getId() {
        return id;
    }

    public void setId(String uuid) {
        this.id = uuid;
    }

    public String getNoInv() {
        return noInv;
    }

    public void setNoInv(String noInv) {
        this.noInv = noInv;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @MOInvField(invName = "id")
    private String id = "uuid";

    @NONInvField
    private String noInv = "noinv";

    @MORelationField
    private String relation = "relation";

    @MOExtendField
    private String extend = "extend";

}
