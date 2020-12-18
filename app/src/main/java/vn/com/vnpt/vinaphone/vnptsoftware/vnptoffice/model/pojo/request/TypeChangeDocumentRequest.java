package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class TypeChangeDocumentRequest implements Serializable {
    @SerializedName("docId")
    @Setter @Getter
    private String docId;
    @SerializedName("processDefinitionId")
    @Setter @Getter
    private String processDefinitionId;
    @SerializedName("type")
    @Setter @Getter
    private String type;
}
