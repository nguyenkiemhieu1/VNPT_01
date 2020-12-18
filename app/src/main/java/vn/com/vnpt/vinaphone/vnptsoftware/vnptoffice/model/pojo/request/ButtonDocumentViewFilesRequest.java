package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class ButtonDocumentViewFilesRequest {
    @SerializedName("docId")
    @Setter
    @Getter
    private int docId;
    @SerializedName("processDefinitionId")
    @Setter
    @Getter
    private int processDefinitionId;
}
