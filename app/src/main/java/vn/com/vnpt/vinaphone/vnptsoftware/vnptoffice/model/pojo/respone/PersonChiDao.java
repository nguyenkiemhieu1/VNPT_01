package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class PersonChiDao {
    @SerializedName("id")
    @Setter @Getter
    private String id;
    @SerializedName("fullName")
    @Setter @Getter
    private String fullName;
    @SerializedName("parentId")
    @Setter @Getter
    private String parentId;
    @SerializedName("unitId")
    @Setter @Getter
    private String unitId;
    @SerializedName("email")
    @Setter @Getter
    private String email;
    @SerializedName("chucVu")
    @Setter @Getter
    private String chucVu;
    @Setter @Getter
    private boolean isTrace;
    @Setter @Getter
    private List<PersonChiDao> childrenList;
}
