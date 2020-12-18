package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/7/2017.
 */

public class DocumentWaitingInfo implements Serializable {
    @SerializedName("id") @Setter @Getter private String id;
    @SerializedName("trichYeu") @Setter @Getter private String trichYeu;
    @SerializedName("soKihieu") @Setter @Getter private String soKihieu;
    @SerializedName("coQuanBanHanh") @Setter @Getter private String coQuanBanHanh;
    @SerializedName("ngayVanBan") @Setter @Getter private String ngayVanBan;
    @SerializedName("doKhan") @Setter @Getter private String doKhan;
    @SerializedName("role") @Setter @Getter private String role;
    @SerializedName("congVanDenDi") @Setter @Getter private String congVanDenDi;
    @SerializedName("processDefinitionId") @Setter @Getter private String processDefinitionId;
    @SerializedName("processInstanceId") @Setter @Getter private String processInstanceId;
    @SerializedName("hasFile") @Setter @Getter private String hasFile;
    @SerializedName("ngayNhan") @Setter @Getter private String ngayNhan;
    @SerializedName("isRead") @Setter @Getter private String isRead;
    @SerializedName("isComment") @Setter @Getter private String isComment;
    @SerializedName("isCheck") @Setter @Getter private String isCheck;
    @SerializedName("isChuTri") @Setter @Getter private String isChuTri;
    @SerializedName("isSign") @Setter @Getter private String isSign;
    @SerializedName("isChuyenTiep") @Setter @Getter private String isChuyenTiep;
    @SerializedName("type") @Setter @Getter private String type;
    @Setter @Getter private boolean select=false;
    @Override
    public String toString() {
        return "DocumentWaitingInfo{" +
                "id='" + id + '\'' +
                ", trichYeu='" + trichYeu + '\'' +
                ", soKihieu='" + soKihieu + '\'' +
                ", coQuanBanHanh='" + coQuanBanHanh + '\'' +
                ", ngayVanBan='" + ngayVanBan + '\'' +
                ", doKhan='" + doKhan + '\'' +
                ", congVanDenDi='" + congVanDenDi + '\'' +
                ", processDefinitionId='" + processDefinitionId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", hasFile='" + hasFile + '\'' +
                ", ngayNhan='" + ngayNhan + '\'' +
                ", isRead='" + isRead + '\'' +
                ", isComment='" + isComment + '\'' +
                ", isCheck='" + isCheck + '\'' +
                ", isChuTri='" + isChuTri + '\'' +
                ", isSign='" + isSign + '\'' +
                ", isChuyenTiep='" + isChuyenTiep + '\'' +
                '}';
    }
}
