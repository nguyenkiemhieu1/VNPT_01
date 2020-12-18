package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public  class DetailJobManageResponse extends StatusRespone {

    @SerializedName("data")
    @Setter
    @Getter
    private Data data;

    public static class Data {
        @SerializedName("files")
        @Setter
        @Getter
        private List<AttachFileInfo> files;
        @SerializedName("status")
        @Setter
        @Getter
        private String status;
        @SerializedName("noiDungDanhGia")
        @Setter
        @Getter
        private String noiDungDanhGia;
        @SerializedName("ngayHetHan")
        @Setter
        @Getter
        private String ngayHetHan;
        @SerializedName("ngayGiaoViec")
        @Setter
        @Getter
        private String ngayGiaoViec;
        @SerializedName("priority")
        @Setter
        @Getter
        private String priority;
        @SerializedName("noiDung")
        @Setter
        @Getter
        private String noiDung;
        @SerializedName("name")
        @Setter
        @Getter
        private String name;
        @SerializedName("lanhDaoGiaoViec")
        @Setter
        @Getter
        private String lanhDaoGiaoViec;
        @SerializedName("statusDanhGia")
        @Setter
        @Getter
        private String statusDanhGia;
        @SerializedName("btnAddAssign")
        @Setter
        @Getter
        private String btnAddAssign;
        @SerializedName("btnUpdate")
        @Setter
        @Getter
        private String btnUpdate;
        @SerializedName("btnFlow")
        @Setter
        @Getter
        private String btnFlow;
        @SerializedName("btnEvaluate")
        @Setter
        @Getter
        private String btnEvaluate;
        @SerializedName("btnStatus")
        @Setter
        @Getter
        private String btnStatus;
        @SerializedName("btnCreateJob")
        @Setter
        @Getter
        private String btnCreateJob;

    }

}
