package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */
public class CreateSubTaskRequest {

    @SerializedName("units")
    @Setter
    @Getter
    private List<ObjectAssignRequest> units;
    @SerializedName("users")
    @Setter
    @Getter
    private List<ObjectAssignRequest> users;
    @SerializedName("parentNxlId")
    @Setter
    @Getter
    private String parentNxlId;
    @SerializedName("parentId")
    @Setter
    @Getter
    private String parentId;
    @SerializedName("lanhDaoGiaoViec")
    @Setter
    @Getter
    private String lanhDaoGiaoViec;
    @SerializedName("priority")
    @Setter
    @Getter
    private String priority;
    @SerializedName("files")
    @Setter
    @Getter
    private List<String> files;
    @SerializedName("hanXuLy")
    @Setter
    @Getter
    private String hanXuLy;
    @SerializedName("content")
    @Setter
    @Getter
    private String content;
    @SerializedName("name")
    @Setter
    @Getter
    private String name;

}
