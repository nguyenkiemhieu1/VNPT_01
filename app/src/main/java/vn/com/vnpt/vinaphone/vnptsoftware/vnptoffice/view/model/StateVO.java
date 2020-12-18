package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class StateVO {
    @Getter @Setter private String title;
    @Getter @Setter private boolean selected;
    @Getter @Setter private String id;
}
