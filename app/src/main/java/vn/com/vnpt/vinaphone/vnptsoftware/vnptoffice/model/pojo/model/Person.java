package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;


import android.support.annotation.Nullable;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class Person {
    @Setter
    @Getter
    private String id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String unit;
    @Setter
    @Getter
    private String avatar;
    @Setter
    @Getter
    private String parrentId;
    @Setter
    @Getter
    private boolean xlc;
    @Setter
    @Getter
    private boolean dxl;
    @Setter
    @Getter
    private boolean xem;
    @Setter
    @Getter
    private int unitId;

    public Person(String id, String name, String unit, String avatar, boolean xlc, boolean dxl, boolean xem, int unitId) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.avatar = avatar;
        this.xlc = xlc;
        this.dxl = dxl;
        this.xem = xem;
        this.unitId = unitId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Person) {
            Person persons = (Person) obj;
            if (this.id.equals(persons.id) && this.xlc == persons.xlc && this.dxl == persons.dxl && this.xem == persons.xem)
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
       return 1;
        //(this.id.hashCode() + this.name.hashCode() + this.unit.hashCode() + this.avatar.hashCode() + this.parrentId.hashCode() + this.xlc + this.xem + this.xem + this.unitId);
    }

}
