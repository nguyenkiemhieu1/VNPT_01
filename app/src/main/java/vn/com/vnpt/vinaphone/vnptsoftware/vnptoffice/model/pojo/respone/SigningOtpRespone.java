package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK 0948012236 on 8/29/2017.
 */

public class SigningOtpRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private Data data;

    public class Data {
        @SerializedName("fileId")
        @Setter
        @Getter
        private int fileId;
        @SerializedName("docId")
        @Setter
        @Getter
        private String docId;
        @SerializedName("comment")
        @Setter
        @Getter
        private String comment;
        @SerializedName("hanXuLy")
        @Setter
        @Getter
        private String hanXuLy;

    }
}
