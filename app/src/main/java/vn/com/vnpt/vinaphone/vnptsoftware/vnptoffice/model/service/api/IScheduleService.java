package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleDepartmentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleMeetingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IScheduleService {
    @POST(ServiceUrl.GET_SCHEDULES_URL)
    Call<ScheduleRespone> getSchedules(@Body ScheduleRequest scheduleRequest);
    @GET(ServiceUrl.GET_DETAIL_SCHEDULE_MEETING_URL)
    Call<ScheduleMeetingRespone> getDetailMeeting(@Path("id") int scheduleId);
    @GET(ServiceUrl.GET_DETAIL_SCHEDULE_BUSSINESS_URL)
    Call<ScheduleBussinessRespone> getDetailBussiness(@Path("id") int scheduleId);
    @POST(ServiceUrl.GET_SCHEDULES_BOSS_URL)
    Call<ScheduleBossRespone> getSchedulesBoss(@Body ScheduleBossRequest scheduleBossRequest);
    @POST(ServiceUrl.GET_LIST_SCHEDULE_WEEK_URL)
    Call<ScheduleWeekResponse> getScheduleWeek(@Body ScheduleWeekRequest scheduleWeekRequest);
    @POST(ServiceUrl.CREATE_SCHEDULE_WEEK_URL)
    Call<CreateScheduleWeekResponse> createScheduleWeek(@Body CreateScheduleWeekRequest createScheduleWeekRequest);
    @POST(ServiceUrl.GET_PERSON_AND_UNIT_SCHEDULE_WEEK_URL)
    Call<PersonAndUnitScheduleWeekResponse> getPersonAndUnitScheduleWeek(@Body PersonAndUnitScheduleWeekRequest personAndUnitScheduleWeekRequest);
    @GET(ServiceUrl.GET_CHUTRI_SCHEDULE_WEEK_URL)
    Call<ChairScheduleWeekResponse> getChairScheduleWeek();//lấy danh sách chủ trì lịch tuần
    @GET(ServiceUrl.GET_LIST_UNIT_SCHEDULES_URL)
    Call<DepartmentRespone> getDepartments();
    @POST(ServiceUrl.GET_LIST_SCHEDULES_DEPART_URL)
    Call<ScheduleDepartmentRespone> getScheduleDepartments(@Body ScheduleDepartmentRequest scheduleDepartmentRequest);
}