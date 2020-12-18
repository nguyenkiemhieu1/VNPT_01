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
public class UpdateCongViecRequest {
    @SerializedName("id")
    @Setter @Getter
    private String id;
    @SerializedName("name")
    @Setter @Getter
    private String name;
    @SerializedName("content")
    @Setter @Getter
    private String content;
    @SerializedName("expireDate")
    @Setter @Getter
    private String expireDate;
    @SerializedName("files")
    @Setter @Getter
    private List<String> listFile;
    @SerializedName("priority")
    @Setter @Getter
    private String priority;
    @SerializedName("status")
    @Setter @Getter
    private String status;

}
