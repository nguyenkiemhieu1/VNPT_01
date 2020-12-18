package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/8/17.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DefaultHome extends RealmObject {
    @PrimaryKey @Getter @Setter private String userId;
    @Getter @Setter private String name;
}
