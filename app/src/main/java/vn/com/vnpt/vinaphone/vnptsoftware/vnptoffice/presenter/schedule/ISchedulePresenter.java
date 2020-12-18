package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CreateScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonAndUnitScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleWeekRequest;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */

public interface ISchedulePresenter {
    void getWeekSchedules(ScheduleRequest scheduleRequest);
    void getDetailMeeting(int scheduleId);
    void getDetailBussiness(int scheduleId);
    void getWeekSchedulesBoss(ScheduleBossRequest scheduleBossRequest);
    void getScheduleWeek(ScheduleWeekRequest scheduleWeekRequest);
    void createScheduleWeek(CreateScheduleWeekRequest createScheduleWeekRequest);
    void getPersonAndUnitScheduleWeek(PersonAndUnitScheduleWeekRequest personAndUnitScheduleWeekRequest);
    void getChairScheduleWeek();
}
