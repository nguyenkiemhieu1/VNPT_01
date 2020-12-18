package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/28/2017.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Contact extends RealmObject {
    @PrimaryKey @Getter @Setter private long uid;
    @Getter @Setter private String id;
    @Getter @Setter private String userName;
    @Getter @Setter private String parentId;
    @Getter @Setter private String position;
    @Getter @Setter private String unitName;
    @Getter @Setter private String sex;
    @Getter @Setter private String phone;
    @Getter @Setter private String email;
    @Getter @Setter private String address;
    @Getter @Setter private String nation;
    @Getter @Setter private String religion;
    @Getter @Setter private String level;
    @Getter @Setter private String status;
    @Getter @Setter private String userId;
    @Getter @Setter private String dateBorn;
    @Getter @Setter private String avatar;
    @Getter @Setter private String isUser;
}
