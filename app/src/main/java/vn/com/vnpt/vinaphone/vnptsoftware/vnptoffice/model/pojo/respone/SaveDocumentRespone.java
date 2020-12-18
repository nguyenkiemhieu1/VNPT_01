package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class SaveDocumentRespone {
    @SerializedName("id")
    @Setter
    @Getter
    private String id;
    @SerializedName("name")
    @Setter @Getter
    private String name;
    @SerializedName("parentId")
    @Setter @Getter
    private String parentId;
    @SerializedName("createDate")
    @Setter @Getter
    private String createDate;
    @Setter @Getter
    private boolean isTrace;
    @Setter @Getter
    private List<SaveDocumentRespone> childrenList;

}
