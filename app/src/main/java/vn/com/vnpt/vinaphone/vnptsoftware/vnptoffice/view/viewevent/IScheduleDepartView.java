package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DepartmentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleDepartBossInfo;

public interface IScheduleDepartView {
    void onSuceessDepart(List<DepartmentInfo> departments);

    void onSuccessScheduleDepart(List<ScheduleDepartBossInfo> schedules);

    void onError(APIError apiError);

    void showProgress();

    void hideProgress();
}
