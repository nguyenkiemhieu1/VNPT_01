package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;

/**
 * Created by LinhLK - 0948012236 on 9/11/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class SelectUnitForPersonEvent {
    @Setter @Getter private PersonSendNotifyInfo unitSelected;
}
