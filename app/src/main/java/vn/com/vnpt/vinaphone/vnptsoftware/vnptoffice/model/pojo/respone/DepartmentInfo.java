package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class DepartmentInfo {
    @SerializedName("id")
    @Setter
    @Getter
    private String id;
    @SerializedName("name")
    @Setter @Getter
    private String name;

    @Override
    public String toString() {
        return "DepartmentInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
