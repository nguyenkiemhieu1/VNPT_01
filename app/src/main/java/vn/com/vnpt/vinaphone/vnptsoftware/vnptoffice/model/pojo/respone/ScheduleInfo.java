package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class ScheduleInfo {
    @SerializedName("id")
    @Setter @Getter
    private String id;
    @SerializedName("title")
    @Setter @Getter
    private String title;
    @SerializedName("chuTri")
    @Setter @Getter
    private String chuTri;
    @SerializedName("participation")
    @Setter @Getter
    private String participation;
    @SerializedName("position")
    @Setter @Getter
    private String position;
    @SerializedName("startTime")
    @Setter @Getter
    private String startTime;
    @SerializedName("endTime")
    @Setter @Getter
    private String endTime;
    @SerializedName("dayOfWeek")
    @Setter @Getter
    private String dayOfWeek;
    @SerializedName("note")
    @Setter @Getter
    private String note;
    @SerializedName("content")
    @Setter @Getter
    private String content;
    @SerializedName("thanhPhan")
    @Setter @Getter
    private String thanhPhan;
    @SerializedName("type")
    @Setter @Getter
    private String type;
}
