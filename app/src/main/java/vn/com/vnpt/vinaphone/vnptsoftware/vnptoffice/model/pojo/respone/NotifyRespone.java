package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public class NotifyRespone extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private DataObject data;

    public class DataObject{
        @SerializedName("count")
        @Setter @Getter
        private String count;

        @SerializedName("notifys")
        @Setter @Getter
        private List<NotifyInfo> notifys;
    }
}
