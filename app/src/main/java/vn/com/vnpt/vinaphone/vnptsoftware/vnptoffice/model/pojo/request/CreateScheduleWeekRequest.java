package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class CreateScheduleWeekRequest {
    @SerializedName("date")
    @Setter
    @Getter
    private String date;
    @SerializedName("chuTri")
    @Setter
    @Getter
    private String chuTri;
    @SerializedName("timeStart")
    @Setter
    @Getter
    private String timeStart;
    @SerializedName("timeEnd")
    @Setter
    @Getter
    private String timeEnd;
    @SerializedName("content")
    @Setter
    @Getter
    private String content;
    @SerializedName("location")
    @Setter
    @Getter
    private String location;
    @SerializedName("chuanBi")
    @Setter
    @Getter
    private String chuanBi;
    @SerializedName("thanhPhan")
    @Setter
    @Getter
    private String thanhPhan;
    @SerializedName("tenChuTri")
    @Setter
    @Getter
    private String tenChuTri;
    @SerializedName("tenThanhPhan")
    @Setter
    @Getter
    private String tenThanhPhan;
}
