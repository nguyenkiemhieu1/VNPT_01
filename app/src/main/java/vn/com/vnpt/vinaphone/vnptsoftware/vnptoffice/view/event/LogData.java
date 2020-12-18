package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;

/**
 * Created by LinhLK - 0948012236 on 9/21/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class LogData {
    @Getter @Setter private List<UnitLogInfo> unitLogInfoList;
}