package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class ChangeDocumentSendRequest {
    @SerializedName("docId")
    @Setter @Getter
    private String docId;
    @SerializedName("chuTri")
    @Setter @Getter
    private String chuTri;
    @SerializedName("phoiHop")
    @Setter @Getter
    private String phoiHop;
    @SerializedName("sFunc")
    @Setter @Getter
    private String sFunc;
    @SerializedName("sApproved")
    @Setter @Getter
    private String sApproved;
    @SerializedName("sMore")
    @Setter @Getter
    private String sMore;
    @SerializedName("act")
    @Setter @Getter
    private String act;
    @SerializedName("assigner")
    @Setter @Getter
    private String assigner;
    @SerializedName("dongGui")
    @Setter @Getter
    private String dongGui;
    @SerializedName("formId")
    @Setter @Getter
    private String formId;
    @SerializedName("sendType")
    @Setter @Getter
    private String sendType;
    @SerializedName("job")
    @Setter @Getter
    private int job;
    @SerializedName("hanXuLy")
    @Setter @Getter
    private String hanXuLy;
    @SerializedName("files")
    @Setter @Getter
    private List<Object> files;

}
