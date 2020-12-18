package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class UploadFileWorkResponse extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private List<String> data;
}
