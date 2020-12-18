package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class FileSigning {
    @SerializedName("id") @Setter @Getter private String id;
    @SerializedName("name") @Setter @Getter private String name;
}
