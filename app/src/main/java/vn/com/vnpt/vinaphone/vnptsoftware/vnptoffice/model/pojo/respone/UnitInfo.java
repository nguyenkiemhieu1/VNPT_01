package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/14/2017.
 */

public class UnitInfo {
    @SerializedName("id") @Setter @Getter private String id;
    @SerializedName("name") @Setter @Getter private String name;
}
