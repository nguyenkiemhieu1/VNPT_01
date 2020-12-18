package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class ChangeNotifyRequest {
    @SerializedName("docId")
    @Setter @Getter
    private String docId;
    @SerializedName("xlc")
    @Setter @Getter
    private String xlc;
    @SerializedName("phoiHop")
    @Setter @Getter
    private String phoiHop;
    @SerializedName("comment")
    @Setter @Getter
    private String comment;
    @SerializedName("type")
    @Setter @Getter
    private String type;
}
