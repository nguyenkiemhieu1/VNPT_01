package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ScheduleDepartBossInfo {
    @SerializedName("date")
    @Setter
    @Getter
    private String date;
    @SerializedName("sang")
    @Setter @Getter
    private List<ScheduleDepartInfo> dataSang ;
    @SerializedName("chieu")
    @Setter @Getter
    private List<ScheduleDepartInfo> dataChieu;
}
