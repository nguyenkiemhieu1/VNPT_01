package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ScheduleWeekResponse extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private Object data;
}
