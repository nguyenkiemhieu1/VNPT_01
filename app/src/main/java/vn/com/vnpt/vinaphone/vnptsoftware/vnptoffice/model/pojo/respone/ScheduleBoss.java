package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class ScheduleBoss {
    @SerializedName("username")
    @Setter @Getter
    private String username;
    @SerializedName("position")
    @Setter @Getter
    private String position;
    @SerializedName("parameters")
    @Setter @Getter
    private List<ScheduleDayBoss> parameters;
}
