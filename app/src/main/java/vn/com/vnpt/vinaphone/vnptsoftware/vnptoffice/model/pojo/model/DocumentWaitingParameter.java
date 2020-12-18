package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class DocumentWaitingParameter {
    @SerializedName("trichYeu")
    @Getter @Setter
    private String trichYeu;
    @SerializedName("soKiHieu")
    @Getter @Setter
    private String soKiHieu;
    @SerializedName("type")
    @Getter @Setter
    private String type;
    @SerializedName("linhVuc")
    @Getter @Setter
    private String linhVuc;
    @SerializedName("priority")
    @Getter @Setter
    private String priority;
    @SerializedName("confidential")
    @Getter @Setter
    private String confidential;
    @SerializedName("startDateBanHanh")
    @Getter @Setter
    private String startDateBanHanh;
    @SerializedName("endDateBanHanh")
    @Getter @Setter
    private String endDateBanHanh;
    @SerializedName("startDateSoanThao")
    @Getter @Setter
    private String startDateSoanThao;
    @SerializedName("endDateSoanThao")
    @Getter @Setter
    private String endDateSoanThao;
    @SerializedName("startDateHanXuLy")
    @Getter @Setter
    private String startDateHanXuLy;
    @SerializedName("endDateHanXuLy")
    @Getter @Setter
    private String endDateHanXuLy;
    @SerializedName("coQuanBanHanh")
    @Getter @Setter
    private String coQuanBanHanh;
    @SerializedName("soVanBan")
    @Getter @Setter
    private String soVanBan;
    @SerializedName("soDenDi")
    @Getter @Setter
    private String soDenDi;
    @SerializedName("uyQuyen")
    @Getter @Setter
    private String uyQuyen;
}
