package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class PersonReceiveChiDao {
    @Setter @Getter private String id;
    @Setter @Getter private String fullName;
    @Setter @Getter private String unitName;
    @Setter @Getter private String email;
    @Setter @Getter private String ngayXem;
    @Setter @Getter private String ngayNhan;
}
