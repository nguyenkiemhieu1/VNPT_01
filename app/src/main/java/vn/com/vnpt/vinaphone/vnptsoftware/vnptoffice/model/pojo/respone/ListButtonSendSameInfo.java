package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ListButtonSendSameInfo {
    @SerializedName("type")
    @Setter
    @Getter
    private int type;
    @SerializedName("nextStep")
    @Setter
    @Getter
    private String nextStep;
    @SerializedName("approvedValue")
    @Setter
    @Getter
    private String approvedValue;
    @SerializedName("name")
    @Setter
    @Getter
    private String name;
    @SerializedName("formId")
    @Setter
    @Getter
    private String formId;
    @SerializedName("roleId")
    @Setter
    @Getter
    private String roleId;
    @SerializedName("color")
    @Setter
    @Getter
    private String color;
    @SerializedName("uploadFile")
    @Setter
    @Getter
    private String uploadFile;
    @SerializedName("commentEnable")
    @Setter
    @Getter
    private String commentEnable;


}

