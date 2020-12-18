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
public class DanhGiaCongViecRequest {
    @SerializedName("id")
    @Setter @Getter
    private String id;
    @SerializedName("content")
    @Setter @Getter
    private String content;
    @SerializedName("status")
    @Setter @Getter
    private String status;

}
