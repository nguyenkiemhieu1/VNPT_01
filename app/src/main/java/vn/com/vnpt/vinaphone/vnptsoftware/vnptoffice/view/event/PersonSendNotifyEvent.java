package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;

/**
 * Created by LinhLK - 0948012236 on 9/25/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class PersonSendNotifyEvent {
    @Getter @Setter private List<PersonSendNotifyInfo> personSendNotifyInfos;
}
