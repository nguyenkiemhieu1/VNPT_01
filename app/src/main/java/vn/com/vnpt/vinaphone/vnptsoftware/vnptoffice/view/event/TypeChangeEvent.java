package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeChangeDocument;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class TypeChangeEvent {
    @Getter @Setter private String note;
    @Getter @Setter private int selected;
    @Getter @Setter private List<TypeChangeDocument> typeChangeDocumentList;
}
