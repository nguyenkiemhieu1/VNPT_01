package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class ChiDaoRequest {
    @SerializedName("id")
    @Getter @Setter private String id;
    @SerializedName("tieuDe")
    @Getter @Setter private String tieuDe;
    @SerializedName("noiDung")
    @Getter @Setter private String noiDung;
    @SerializedName("files")
    @Getter @Setter private List<String> files;
    @SerializedName("deleteFiles")
    @Getter @Setter private List<String> deleteFiles;
    @SerializedName("parentId")
    @Getter @Setter private String parentId;
    @SerializedName("chuyenTiep")
    @Getter @Setter private int chuyenTiep;
}
