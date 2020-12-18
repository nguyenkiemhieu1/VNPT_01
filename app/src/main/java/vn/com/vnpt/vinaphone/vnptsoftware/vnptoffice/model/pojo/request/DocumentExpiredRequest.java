package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class DocumentExpiredRequest {
    @SerializedName("pageNo")
    @Getter @Setter private String pageNo;
    @SerializedName("pageRec")
    @Getter @Setter private String pageRec;
    @SerializedName("param")
    @Getter @Setter private String parameter;
}
