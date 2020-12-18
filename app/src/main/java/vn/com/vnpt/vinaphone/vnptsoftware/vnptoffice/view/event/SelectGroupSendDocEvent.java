package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by linhl on 12/4/2018.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class SelectGroupSendDocEvent {
    @Getter
    @Setter
    private boolean editEvent;
}
