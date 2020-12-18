package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.schedule;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CreateScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonAndUnitScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleDepartmentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.service.syncdata.HandleSyncService;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IScheduleDao {
    void onSendGetSchedulesDao(HandleSyncService.HandleGetSchedules handleGetSchedules, ScheduleRequest scheduleRequest);
    void onSendGetWeekSchedules(ICallFinishedListener callFinishedListener, ScheduleRequest scheduleRequest);
    void onGetDepartments(ICallFinishedListener callFinishedListener);
    void onGetScheduleDepartments(ICallFinishedListener callFinishedListener, ScheduleDepartmentRequest scheduleDepartmentRequest);
    void onGetDetailMeeting(int scheduleId, ICallFinishedListener callFinishedListener);
    void onGetDetailBussiness(int scheduleId, ICallFinishedListener callFinishedListener);
    void onSendGetWeekSchedulesBoss(ICallFinishedListener callFinishedListener, ScheduleBossRequest scheduleBossRequest);
    void onGetListSchedulesWeek(ICallFinishedListener callFinishedListener, ScheduleWeekRequest scheduleWeekRequest);
    void onCreateSchedulesWeek(ICallFinishedListener callFinishedListener, CreateScheduleWeekRequest createScheduleWeekRequest);
    void onGetPersonAndUnitSchedulesWeek(ICallFinishedListener callFinishedListener, PersonAndUnitScheduleWeekRequest personAndUnitScheduleWeekRequest);
    void onGetChairSchedulesWeek(ICallFinishedListener callFinishedListener);

}
