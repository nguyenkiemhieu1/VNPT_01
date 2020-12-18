package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor(suppressConstructorProperties = true)
public class DonViNhan {
    @SerializedName("id")
    @Getter
    @Setter
    private String id;
    @SerializedName("name")
    @Getter
    @Setter
    private String name;
}
