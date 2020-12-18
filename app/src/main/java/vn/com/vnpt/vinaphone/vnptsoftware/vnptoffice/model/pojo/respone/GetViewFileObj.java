package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linhl on 10/3/2018.
 */

public class GetViewFileObj {

    @Expose
    @SerializedName("data")
    @Getter
    @Setter
    private String data;
    @Expose
    @SerializedName("status")
    @Getter
    @Setter
    private Status status;

    public static class Status {
        @Expose
        @SerializedName("message")
        @Getter
        @Setter
        private String message;
        @Expose
        @Getter
        @Setter
        @SerializedName("code")
        private String code;
    }
}
