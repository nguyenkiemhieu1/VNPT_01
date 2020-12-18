package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/24/2017.
 */

public class UserInfo {
    @SerializedName("userId") @Setter @Getter private String userId;
    @SerializedName("userName") @Setter @Getter private String username;
    @SerializedName("dob") @Setter @Getter private String dob;
    @SerializedName("sexName") @Setter @Getter private String sexName;
    @SerializedName("status") @Setter @Getter private String status;
    @SerializedName("address") @Setter @Getter private String address;
    @SerializedName("mobile") @Setter @Getter private String mobile;
    @SerializedName("email") @Setter @Getter private String email;
    @SerializedName("password") @Setter @Getter private String password;
    @SerializedName("agentId") @Setter @Getter private String agentId;
    @SerializedName("languageId") @Setter @Getter private String languageId;
    @SerializedName("createdDate") @Setter @Getter private String createdDate;
    @SerializedName("createdBy") @Setter @Getter private String createdBy;
    @SerializedName("danToc") @Setter @Getter private String danToc;
    @SerializedName("tonGiao") @Setter @Getter private String tonGiao;
    @SerializedName("trinhDo") @Setter @Getter private String trinhDo;
    @SerializedName("avatar") @Setter @Getter private String avatar;
    @SerializedName("unitName") @Setter @Getter private String unitName;
}
