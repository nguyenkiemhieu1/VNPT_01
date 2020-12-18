package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/7/2017.
 */

public class DocumentProcessedInfo implements Serializable {
    @SerializedName("id") @Setter @Getter private String id;
    @SerializedName("trichYeu") @Setter @Getter private String trichYeu;
    @SerializedName("soKihieu") @Setter @Getter private String soKihieu;
    @SerializedName("coQuanBanHanh") @Setter @Getter private String coQuanBanHanh;
    @SerializedName("ngayVanBan") @Setter @Getter private String ngayVanBan;
    @SerializedName("doKhan") @Setter @Getter private String doKhan;
    @SerializedName("congVanDenDi") @Setter @Getter private String congVanDenDi;
    @SerializedName("processDefinitionId") @Setter @Getter private String processDefinitionId;
    @SerializedName("processInstanceId") @Setter @Getter private String processInstanceId;
    @SerializedName("hasFile") @Setter @Getter private String hasFile;
    @SerializedName("ngayNhan") @Setter @Getter private String ngayNhan;
    @SerializedName("isRead") @Setter @Getter private String isRead;
    @SerializedName("isComment") @Setter @Getter private String isComment;
    @SerializedName("isCheck") @Setter @Getter private String isCheck;
    @SerializedName("isChuTri") @Setter @Getter private String isChuTri;
    @SerializedName("layLai") @Setter @Getter private String layLai;
}
