package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PersonAndUnitScheduleWeekInfo {
    @SerializedName("id")
    @Setter
    @Getter
    private String id;
    @SerializedName("name")
    @Setter
    @Getter
    private String name;
    @SerializedName("parentId")
    @Setter
    @Getter
    private String parentId;
    @SerializedName("level")
    @Setter
    @Getter
    private String level;
    @SerializedName("isUnit")
    @Setter
    @Getter
    private String isUnit;
    @Setter @Getter
    private List<PersonAndUnitScheduleWeekInfo> childrenList;
}
