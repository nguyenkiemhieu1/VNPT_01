package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class DanhSachDonViNhanRequest {
    @SerializedName("name")
    @Getter
    @Setter
    private String name;
    @SerializedName("agentId")
    @Getter
    @Setter
    private String agentId;
}
