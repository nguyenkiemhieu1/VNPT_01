package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class MeetingInfo {
    @SerializedName("chuTri")
    @Setter @Getter
    private String chuTri;
    @SerializedName("tenPhongHop")
    @Setter @Getter
    private String tenPhongHop;
    @SerializedName("soNguoi")
    @Setter @Getter
    private String soNguoi;
    @SerializedName("timeStart")
    @Setter @Getter
    private String timeStart;
    @SerializedName("endStart")
    @Setter @Getter
    private String endStart;
    @SerializedName("title")
    @Setter @Getter
    private String title;
    @SerializedName("content")
    @Setter @Getter
    private String content;
    @SerializedName("note")
    @Setter @Getter
    private String note;
    @SerializedName("userId")
    @Setter @Getter
    private String userId;
    @SerializedName("fullName")
    @Setter @Getter
    private String fullName;
    @SerializedName("thanhPhan")
    @Setter @Getter
    private String thanhPhan;

    @SerializedName("files")
    @Setter @Getter
    private List<FilesInfo> files;

    public class FilesInfo{
        @SerializedName("name")
        @Setter @Getter
        private String name;
        @SerializedName("hdd")
        @Setter @Getter
        private String hdd;
    }
}
