package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ScheduleWeekInfo {
    @SerializedName("dayOfWeek")
    @Setter
    @Getter
    private String dayOfWeek;
    @SerializedName("date")
    @Setter
    @Getter
    private String date;
    @SerializedName("time")
    @Setter
    @Getter
    private String time;
    @SerializedName("chuTri")
    @Setter
    @Getter
    private String chuTri;
    @SerializedName("thamGia")
    @Setter
    @Getter
    private String thamGia;
    @SerializedName("content")
    @Setter
    @Getter
    private String content;
    @SerializedName("location")
    @Setter
    @Getter
    private String location;
    @SerializedName("note")
    @Setter
    @Getter
    private String note;


}
