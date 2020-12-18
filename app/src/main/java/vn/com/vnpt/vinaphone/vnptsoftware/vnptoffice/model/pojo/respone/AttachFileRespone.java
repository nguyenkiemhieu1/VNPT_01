package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class AttachFileRespone extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private Object data;
}
