package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class SendChiDaoRequest {
    @SerializedName("id")
    @Getter @Setter private String id;
    @SerializedName("sms")
    @Getter @Setter private String sms;
    @SerializedName("user")
    @Getter @Setter private String user;
}
