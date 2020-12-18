package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.schedule;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CreateScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonAndUnitScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleDepartmentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChairScheduleWeekResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CreateScheduleWeekResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DepartmentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleBossRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleBussinessRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DepartmentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleDepartmentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleMeetingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IScheduleService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.service.syncdata.HandleSyncService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class ScheduleDao extends BaseDao implements IScheduleDao {
    private IScheduleService scheduleService;

    @Override
    public void onSendGetSchedulesDao(HandleSyncService.HandleGetSchedules handleGetSchedules, ScheduleRequest scheduleRequest) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleRespone> call = scheduleService.getSchedules(scheduleRequest);
        call(call, handleGetSchedules);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onSendGetWeekSchedules(ICallFinishedListener callFinishedListener, ScheduleRequest scheduleRequest) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleRespone> call = scheduleService.getSchedules(scheduleRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDetailMeeting(int scheduleId, ICallFinishedListener callFinishedListener) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleMeetingRespone> call = scheduleService.getDetailMeeting(scheduleId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDetailBussiness(int scheduleId, ICallFinishedListener callFinishedListener) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleBussinessRespone> call = scheduleService.getDetailBussiness(scheduleId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onSendGetWeekSchedulesBoss(ICallFinishedListener callFinishedListener, ScheduleBossRequest scheduleBossRequest) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleBossRespone> call = scheduleService.getSchedulesBoss(scheduleBossRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListSchedulesWeek(ICallFinishedListener callFinishedListener, ScheduleWeekRequest getScheduleWeek) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleWeekResponse> call = scheduleService.getScheduleWeek(getScheduleWeek);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));

    }

    @Override
    public void onCreateSchedulesWeek(ICallFinishedListener callFinishedListener, CreateScheduleWeekRequest createScheduleWeekRequest) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<CreateScheduleWeekResponse> call = scheduleService.createScheduleWeek(createScheduleWeekRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));


    }

    @Override
    public void onGetPersonAndUnitSchedulesWeek(ICallFinishedListener callFinishedListener, PersonAndUnitScheduleWeekRequest personAndUnitScheduleWeekRequest) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<PersonAndUnitScheduleWeekResponse> call = scheduleService.getPersonAndUnitScheduleWeek(personAndUnitScheduleWeekRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));


    }

    @Override
    public void onGetChairSchedulesWeek(ICallFinishedListener callFinishedListener) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ChairScheduleWeekResponse> call = scheduleService.getChairScheduleWeek();
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDepartments(ICallFinishedListener callFinishedListener) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<DepartmentRespone> call = scheduleService.getDepartments();
        call(call,callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetScheduleDepartments(ICallFinishedListener callFinishedListener, ScheduleDepartmentRequest scheduleDepartmentRequest) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleDepartmentRespone> call = scheduleService.getScheduleDepartments(scheduleDepartmentRequest);
        call(call,callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
