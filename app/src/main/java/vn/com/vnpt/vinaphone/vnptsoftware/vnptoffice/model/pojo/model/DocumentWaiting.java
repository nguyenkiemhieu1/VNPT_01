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
 * Created by LinhLK - 0948012236 on 9/5/17.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DocumentWaiting  extends RealmObject {
    @PrimaryKey @Getter @Setter private String id;
    @Getter @Setter private String ngayNhan;
    @Getter @Setter private String thoiGianNhan;
    @Getter @Setter private String trichYeu;
    @Getter @Setter private String trangThai;
    @Getter @Setter private String kiHieu;
    @Getter @Setter private String coQuanBanHanh;
    @Getter @Setter private String ngayVanBan;
    @Setter @Getter private String congVanDenDi;
    @Getter @Setter private String processDefinitionId;
    @Setter @Getter private String processInstanceId;
    @Getter @Setter private String doKhan;
    @Getter @Setter private String fileDinhKem;
    @Setter @Getter private String isComment;
    @Setter @Getter private String isCheck;
    @Setter @Getter private String isChuTri;
    @Setter @Getter private String isSign;
}
