package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class ScheduleBossInfo {
    @SerializedName("date")
    @Setter @Getter
    private String date;
    @SerializedName("sang")
    @Setter @Getter
    private List<ScheduleInfo> dataSang ;
    @SerializedName("chieu")
    @Setter @Getter
    private List<ScheduleInfo> dataChieu;
}
