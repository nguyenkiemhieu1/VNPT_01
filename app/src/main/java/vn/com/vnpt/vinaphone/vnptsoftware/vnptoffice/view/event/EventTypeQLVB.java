package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linhl on 8/17/2018.
 */

public class EventTypeQLVB {
    @Setter
    @Getter
    private int type;
    public EventTypeQLVB(int type) {
        this.type = type;
    }
}
