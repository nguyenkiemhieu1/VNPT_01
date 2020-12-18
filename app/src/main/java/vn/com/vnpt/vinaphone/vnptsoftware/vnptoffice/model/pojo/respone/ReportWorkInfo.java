package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class ReportWorkInfo {
    @SerializedName("total")
    @Setter @Getter
    private int total;
    @SerializedName("chuaThucHien")
    @Setter @Getter
    private int chuaThucHien;
    @SerializedName("dangThucHien")
    @Setter @Getter
    private int dangThucHien;
    @SerializedName("daThucHien")
    @Setter @Getter
    private int daThucHien;
    @SerializedName("quaHan")
    @Setter @Getter
    private int quaHan;
    @SerializedName("dungHan")
    @Setter @Getter
    private int dungHan;
}
