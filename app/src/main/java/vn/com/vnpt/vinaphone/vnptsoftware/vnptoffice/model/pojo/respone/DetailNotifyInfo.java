package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class DetailNotifyInfo {
    @SerializedName("type")
    @Setter @Getter
    private String type;
    @SerializedName("parameter")
    @Setter @Getter
    private ParamDetailNotifyInfo paramDetailNotifyInfo;
}
