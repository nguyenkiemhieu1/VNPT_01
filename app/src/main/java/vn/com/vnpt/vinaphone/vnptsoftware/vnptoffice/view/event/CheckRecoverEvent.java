package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/11/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class CheckRecoverEvent {
    @SerializedName("id") @Setter @Getter private int id;
    @SerializedName("type") @Setter @Getter private String type;
}
