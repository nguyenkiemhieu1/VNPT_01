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
public class SavePersonChiDaoRequest {
    @SerializedName("id")
    @Getter @Setter private String id;
    @SerializedName("empAdd")
    @Getter @Setter private List<String> empAdd;
    @SerializedName("empDelete")
    @Getter @Setter private List<String> empDelete;
}
