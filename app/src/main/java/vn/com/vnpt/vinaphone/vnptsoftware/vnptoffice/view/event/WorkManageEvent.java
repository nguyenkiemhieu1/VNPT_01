package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/11/2017.
 */
public class WorkManageEvent {
    @Setter @Getter private String id;
    @Setter @Getter private String nxlid;
    @Setter @Getter private String name;
    @Setter @Getter private String priority;
    @Setter @Getter private String nameWork;
    @Setter @Getter private String dateEnd;
    @Setter @Getter private String statusWork;

    public WorkManageEvent(String id, String nxlid) {
        this.id = id;
        this.nxlid = nxlid;
    }
    public WorkManageEvent() {
    }

    public WorkManageEvent(String id, String nxlid, String nameWork) {
        this.id = id;
        this.nxlid = nxlid;
        this.nameWork = nameWork;
    }

    public WorkManageEvent(String id, String nxlid, String nameWork, String dateEnd) {
        this.id = id;
        this.nxlid = nxlid;
        this.nameWork = nameWork;
        this.dateEnd = dateEnd;
    }

    public WorkManageEvent(String id, String nxlid, String nameWork, String dateEnd, String statusWork) {
        this.id = id;
        this.nxlid = nxlid;
        this.nameWork = nameWork;
        this.dateEnd = dateEnd;
        this.statusWork = statusWork;
    }
}
