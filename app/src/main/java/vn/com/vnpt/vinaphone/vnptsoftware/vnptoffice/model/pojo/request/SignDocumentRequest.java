package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class SignDocumentRequest {
    @SerializedName("docId")
    @Setter
    @Getter
    private int docId;
    @SerializedName("fileId")
    @Setter @Getter
    private int fileId;
    @SerializedName("hanXuLy")
    @Setter @Getter
    private String hanXuLy;
    @SerializedName("comment")
    @Setter
    @Getter
    private String comment;
}
