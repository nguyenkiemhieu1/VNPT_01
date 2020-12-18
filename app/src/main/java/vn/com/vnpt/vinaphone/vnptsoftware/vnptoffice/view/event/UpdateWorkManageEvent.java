package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ThaoPX on 9/11/2017.
 */
public class UpdateWorkManageEvent {
    @Setter @Getter private String id;
    @Setter @Getter private String nxlid;
    @Setter @Getter private String name;
    @Setter @Getter private String content;
    @Setter @Getter private String priority;
    @Setter @Getter private String mucdo;
    @Setter @Getter private String status;
    @Setter @Getter private String endDate;
    @Setter @Getter private String chuTri;
    @Setter @Getter private String statusDanhGia;
    @Setter @Getter private String noidungDanhGia;
    @Setter @Getter private String noiDung;

    public UpdateWorkManageEvent() {
    }
}
