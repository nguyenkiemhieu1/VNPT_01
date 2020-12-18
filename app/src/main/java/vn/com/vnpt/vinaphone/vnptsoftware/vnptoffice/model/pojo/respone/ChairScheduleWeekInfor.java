package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ChairScheduleWeekInfor {
    @SerializedName("userId")
    @Setter
    @Getter
    private String userId;
    @SerializedName("name")
    @Setter @Getter
    private String name;
}
