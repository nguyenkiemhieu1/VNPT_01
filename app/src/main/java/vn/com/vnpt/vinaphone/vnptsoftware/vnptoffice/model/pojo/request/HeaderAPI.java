package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class HeaderAPI {
    @Getter @Setter private String key;
    @Getter @Setter private String value;
}