package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/7/2017.
 */

public class CountDocumentWaiting {
    @SerializedName("pageNo")
    @Setter @Getter
    private int pageNo;
    @SerializedName("pageRec")
    @Setter @Getter
    private int pageRec;
}
