package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;

@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class RemoveSelectPersonEvent {
    @Setter
    @Getter
    private ArrayList<Person> personProcessRemove;
    @Setter
    @Getter
    private ArrayList<Person> PersonLienThongRemove;
    @Setter
    @Getter
    private ArrayList<Person> personNotifyRemove;
    @Setter
    @Getter
    private ArrayList<Person> personDirectRemove;
}