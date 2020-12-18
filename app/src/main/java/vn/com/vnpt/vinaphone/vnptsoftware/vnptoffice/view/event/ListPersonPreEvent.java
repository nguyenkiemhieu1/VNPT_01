package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;

/**
 * Created by LinhLK - 0948012236 on 9/11/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class ListPersonPreEvent {
    @Setter @Getter private List<Person> personProcessPreList;
    @Setter @Getter private List<Person> personSendPreList;
    @Setter @Getter private List<Person> personNotifyPreList;
}
