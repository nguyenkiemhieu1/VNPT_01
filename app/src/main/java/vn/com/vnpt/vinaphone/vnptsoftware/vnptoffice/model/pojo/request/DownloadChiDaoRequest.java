package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class DownloadChiDaoRequest {
    @SerializedName("param")
    @Setter @Getter
    private String param;
}
