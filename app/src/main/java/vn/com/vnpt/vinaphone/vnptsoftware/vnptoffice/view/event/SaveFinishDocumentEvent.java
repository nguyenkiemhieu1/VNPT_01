package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor(suppressConstructorProperties = true)
public class SaveFinishDocumentEvent {
    @Getter
    @Setter
    private boolean finish;
}
