package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekInfo;

public interface ISelectPersonScheduleWeekView {
    void onSuccess(List<PersonAndUnitScheduleWeekInfo> personAndUnitScheduleWeekInfos);
    void onError(APIError apiError);

    void showProgress();

    void hideProgress();
}
