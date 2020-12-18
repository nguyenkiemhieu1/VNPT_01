package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class GetListJobAssignRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private List<Data> data;

    public static class Data {
        @SerializedName("nxlId")
        @Setter
        @Getter
        private String nxlId;
        @SerializedName("status")
        @Setter
        @Getter
        private String status;
        @SerializedName("vaitro")
        @Setter
        @Getter
        private String vaitro;
        @SerializedName("name")
        @Setter
        @Getter
        private String name;
        @SerializedName("chuTri")
        @Setter
        @Getter
        private String chuTri;
        @SerializedName("endDate")
        @Setter
        @Getter
        private String endDate;
        @SerializedName("startDate")
        @Setter
        @Getter
        private String startDate;
        @SerializedName("lanhDaoGiaoViec")
        @Setter
        @Getter
        private String lanhDaoGiaoViec;
        @SerializedName("id")
        @Setter
        @Getter
        private String id;
        @SerializedName("dondoc")
        @Setter
        @Getter
        private boolean dondoc;
        @SerializedName("mucDo")
        @Setter
        @Getter
        private String mucDo;

        @SerializedName("statusDanhGia")
        @Setter
        @Getter
        private String statusDanhGia;

        @SerializedName("noiDungDanhGia")
        @Setter
        @Getter
        private String noiDungDanhGia;
    }
}
