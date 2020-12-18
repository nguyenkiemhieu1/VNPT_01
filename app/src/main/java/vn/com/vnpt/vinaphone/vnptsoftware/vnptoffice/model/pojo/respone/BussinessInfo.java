package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class BussinessInfo {
    @SerializedName("position")
    @Setter @Getter
    private String position;
    @SerializedName("startTime")
    @Setter @Getter
    private String startTime;
    @SerializedName("endTime")
    @Setter @Getter
    private String endTime;
    @SerializedName("title")
    @Setter @Getter
    private String title;
    @SerializedName("content")
    @Setter @Getter
    private String content;
    @SerializedName("note")
    @Setter @Getter
    private String note;
    @SerializedName("userId")
    @Setter @Getter
    private String userId;
    @SerializedName("fullName")
    @Setter @Getter
    private String fullName;
    @SerializedName("thanhPhan")
    @Setter @Getter
    private String thanhPhan;
}
