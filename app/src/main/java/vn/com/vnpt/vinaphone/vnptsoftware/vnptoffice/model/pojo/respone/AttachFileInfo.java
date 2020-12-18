package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/14/2017.
 */

public class AttachFileInfo {
    @SerializedName("id") @Setter @Getter private int id;
    @SerializedName("name") @Setter @Getter private String name;
    @SerializedName("attachmentId") @Setter @Getter private int attachmentId;
    @SerializedName("creator") @Setter @Getter private String creator;
    @SerializedName("fileSize") @Setter @Getter private String fileSize;
    @SerializedName("filePage") @Setter @Getter private String filePage;
    @SerializedName("isKy") @Setter @Getter private String isKy;
    @SerializedName("kyPki") @Setter @Getter private String kyPki;
    @SerializedName("kyServer") @Setter @Getter private String kyServer;
    @SerializedName("kyCA") @Setter @Getter private String kyCA;
    @SerializedName("info") @Setter @Getter private List<String> info;
    @SerializedName("commentDisplay") @Setter @Getter private String commentDisplay;
    @SerializedName("editFile") @Setter @Getter private String editFile;
    @SerializedName("btnChuyen") @Setter @Getter private String btnChuyen;
}
