package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */
public class ExceptionRequest implements Serializable {
    @SerializedName("userId")
    @Getter @Setter private String userId;
    @SerializedName("device")
    @Getter @Setter private String device;
    @SerializedName("function")
    @Getter @Setter private String function;
    @SerializedName("exception")
    @Getter @Setter private String exception;

    public ExceptionRequest(){

    }

}
