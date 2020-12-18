package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/20/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class UploadResponse extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private Object data;
}
