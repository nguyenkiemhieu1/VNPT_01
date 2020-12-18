package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileSigning;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class SigningDocumentRequest {
    @SerializedName("tokenId")
    @Setter @Getter
    private String tokenId;
    @SerializedName("documentId")
    @Setter @Getter
    private String documentId;
    @SerializedName("location")
    @Setter @Getter
    private String location;
    @SerializedName("reason")
    @Setter @Getter
    private String reason;
    @SerializedName("fileList")
    @Setter @Getter
    private List<FileSigning> signingList;
}
