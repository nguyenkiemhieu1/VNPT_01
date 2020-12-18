package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */
public  class ObjectAssignRequest {
    @SerializedName("endDate")
    @Setter
    @Getter
    private String endDate;
    @SerializedName("startDate")
    @Setter
    @Getter
    private String startDate;
    @SerializedName("vaiTro")
    @Setter
    @Getter
    private String vaiTro;
    @SerializedName("unitId")
    @Setter
    @Getter
    private String unitId;
    @SerializedName("userId")
    @Setter
    @Getter
    private String userId;
    @Setter
    @Getter
    @SerializedName("name")
    private String nameUser;
}
