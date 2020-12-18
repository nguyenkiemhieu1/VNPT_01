package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class CountNumberDenDiEvent {
    @Getter @Setter private int numberDen;
    @Getter @Setter private int numberDi;
    @Getter @Setter private int numberXemDeBiet;
    @Getter @Setter private int numberQLCV;
    @Getter @Setter private int numberTTDH;
}
