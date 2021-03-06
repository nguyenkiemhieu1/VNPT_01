package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class LoginEvent {
    @Getter @Setter private boolean login;
    @Getter @Setter private String action;
}
