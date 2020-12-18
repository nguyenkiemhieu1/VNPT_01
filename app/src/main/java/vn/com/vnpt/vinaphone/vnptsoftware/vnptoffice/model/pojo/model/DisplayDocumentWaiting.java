package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/5/17.
 */
@NoArgsConstructor
public class DisplayDocumentWaiting {

    public DisplayDocumentWaiting(DocumentWaiting documentWaiting) {
        this.id = documentWaiting.getId();
        this.ngayNhan = documentWaiting.getNgayNhan();
        this.thoiGianNhan = documentWaiting.getThoiGianNhan();
        this.trichYeu = documentWaiting.getTrichYeu();
        this.trangThai = documentWaiting.getTrangThai();
        this.kiHieu = documentWaiting.getKiHieu();
        this.coQuanBanHanh = documentWaiting.getCoQuanBanHanh();
        this.ngayVanBan = documentWaiting.getNgayVanBan();
        this.congVanDenDi = documentWaiting.getCongVanDenDi();
        this.processDefinitionId = documentWaiting.getProcessDefinitionId();
        this.processInstanceId = documentWaiting.getProcessInstanceId();
        this.doKhan = documentWaiting.getDoKhan();
        this.fileDinhKem = documentWaiting.getFileDinhKem();
        this.isCheck = documentWaiting.getIsCheck();
        this.isChuTri = documentWaiting.getIsChuTri();
        this.isComment = documentWaiting.getIsComment();
        this.isSign = documentWaiting.getIsSign();
    }

    @Getter @Setter private String id;
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
