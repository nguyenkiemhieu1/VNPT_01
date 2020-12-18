package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/28/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class ChiDaoDetail {
    @Getter @Setter private String id;
    @Getter @Setter private String tieuDe;
    @Getter @Setter private String noiDung;
    @Getter @Setter private String ngayTao;
    @Getter @Setter private String nguoiTao;
    @Getter @Setter private String fullName;
}
