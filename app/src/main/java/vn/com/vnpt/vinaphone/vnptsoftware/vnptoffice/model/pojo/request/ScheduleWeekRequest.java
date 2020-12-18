package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class ScheduleWeekRequest {
    @SerializedName("startDate")
    @Setter
    @Getter
    private String startDate;
    @SerializedName("endDate")
    @Setter
    @Getter
    private String endDate;
    @SerializedName("chuTri")
    @Setter
    @Getter
    private String chuTri;
}