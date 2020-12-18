package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class ParamDetailNotifyInfo {
    @SerializedName("isChuTri")
    @Setter @Getter
    private String isChuTri;

    @SerializedName("isCheck")
    @Setter @Getter
    private String isCheck;

    @SerializedName("processKey")
    @Setter @Getter
    private String processKey;

    @SerializedName("congVanDenDi")
    @Setter @Getter
    private String congVanDenDi;

    @SerializedName("idThongTin")
    @Setter @Getter
    private String idThongTin;
    @SerializedName("isChuyenTiep")
    @Setter @Getter
    private String isChuyenTiep;
    @SerializedName("idCongViec")
    @Setter @Getter
    private String idCongViec;
    @SerializedName("nxlId")
    @Setter @Getter
    private String nxlId;
}
