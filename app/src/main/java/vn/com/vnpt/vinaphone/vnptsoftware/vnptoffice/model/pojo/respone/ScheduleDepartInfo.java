package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ScheduleDepartInfo {
    @SerializedName("title")
    @Setter
    @Getter
    private String title;
    @SerializedName("location")
    @Setter @Getter
    private String location;
    @SerializedName("attendee")
    @Setter @Getter
    private String attendee;
}
