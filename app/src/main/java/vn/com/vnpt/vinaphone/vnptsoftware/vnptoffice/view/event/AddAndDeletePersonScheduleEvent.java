package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekInfo;

@AllArgsConstructor(suppressConstructorProperties = true)
public class AddAndDeletePersonScheduleEvent {
    @Setter
    @Getter
    private String type;
    @Setter
    @Getter
    private ArrayList<PersonAndUnitScheduleWeekInfo> listSelectPesonAndUnit;
}
