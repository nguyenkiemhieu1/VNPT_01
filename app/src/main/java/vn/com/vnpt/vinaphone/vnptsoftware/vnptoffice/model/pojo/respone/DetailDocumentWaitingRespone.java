package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.DetailDocumentWaiting;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */

public class DetailDocumentWaitingRespone extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private DetailDocumentWaiting data;
}
