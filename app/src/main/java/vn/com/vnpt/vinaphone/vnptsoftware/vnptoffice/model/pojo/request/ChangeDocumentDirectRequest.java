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
public class ChangeDocumentDirectRequest {
    @SerializedName("docId")
    @Setter @Getter
    private String docId;
    @SerializedName("primaryProcess")
    @Setter @Getter
    private String primaryProcess;
    @SerializedName("coevalProcess")
    @Setter @Getter
    private String coevalProcess;
    @SerializedName("referProcess")
    @Setter @Getter
    private String referProcess;
    @SerializedName("primaryInternal")
    @Setter @Getter
    private String primaryInternal;
    @SerializedName("coevalInternal")
    @Setter @Getter
    private String coevalInternal;
    @SerializedName("referInternal")
    @Setter @Getter
    private String referInternal;
    @SerializedName("comment")
    @Setter @Getter
    private String comment;
    @SerializedName("approvedValue")
    @Setter @Getter
    private String approvedValue;
    @SerializedName("strAction")
    @Setter @Getter
    private String strAction;
    @SerializedName("processDefinitionId")
    @Setter @Getter
    private String processDefinitionId;
    @SerializedName("actionType")
    @Setter @Getter
    private String actionType;
    @SerializedName("sms")
    @Setter @Getter
    private int sms;
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
