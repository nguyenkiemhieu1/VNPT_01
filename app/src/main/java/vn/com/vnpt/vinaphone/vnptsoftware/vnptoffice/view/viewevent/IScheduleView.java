package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DepartmentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IScheduleView {
    void onSuccess(List<ScheduleInfo> schedules);
    void onSuccessBoss(List<ScheduleBossInfo> schedules);
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}