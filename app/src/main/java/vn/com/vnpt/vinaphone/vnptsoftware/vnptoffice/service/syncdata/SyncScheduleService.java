package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.service.syncdata;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.RealmDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.login.LoginDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.schedule.ScheduleDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.builder.ScheduleBuilder;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Schedule;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 9/23/2017.
 */

public class SyncScheduleService extends Service implements HandleSyncService.HandleGetSchedules, ICallFinishedListener {

    private LoginDao loginDao;
    private ScheduleDao scheduleDao;
    private Realm realm;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private String startDateCurrent;
    private String endDateCurrent;

    @Override
    public void onCreate() {
        super.onCreate();
        scheduleDao = new ScheduleDao();
        Calendar firstDayOfWeek = Calendar.getInstance();
        while (firstDayOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            firstDayOfWeek.add(Calendar.DATE, -1);
        }
        Calendar lastDayOfWeek = Calendar.getInstance();
        while (lastDayOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            lastDayOfWeek.add(Calendar.DATE, 1);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_SCHEDULE);
        startDateCurrent = sdf.format(firstDayOfWeek.getTime());
        endDateCurrent = sdf.format(lastDayOfWeek.getTime());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (connectionDetector.isConnectingToInternet()) {
            scheduleDao.onSendGetSchedulesDao(this, new ScheduleRequest(startDateCurrent, endDateCurrent));
        }
        return START_STICKY;
    }

    @Override
    public void onCallSuccess(Object object) {
        LoginRespone loginRespone = (LoginRespone) object;
        if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
            Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
            if (connectionDetector.isConnectingToInternet()) {
                scheduleDao.onSendGetSchedulesDao(this, new ScheduleRequest(startDateCurrent, endDateCurrent));
            }
        }
    }

    @Override
    public void onCallError(Object object) {

    }

    @Override
    public void onSuccessGetSchedules(Object object) {
        //Đồng bộ DB Local
        ScheduleRespone scheduleRespone = (ScheduleRespone) object;
        if (scheduleRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            List<ScheduleInfo> scheduleInfoList = ConvertUtils.fromJSONList(scheduleRespone.getData(), ScheduleInfo.class);
            realm = RealmDao.with(getApplication()).getRealm();
            clearSchedules();
            if (scheduleInfoList != null && scheduleInfoList.size() > 0) {
                ScheduleBuilder scheduleBuilder = new ScheduleBuilder(this);
                List<Schedule> schedules = scheduleBuilder.convertFromScheduleInfo(scheduleInfoList);
                if (schedules != null && schedules.size() > 0) {
                    saveSchedule(schedules);
                }
            }
        }
    }

    @Override
    public void onErrorGetSchedules(Object object) {
        APIError apiError = (APIError) object;
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            loginDao = new LoginDao();
            if (connectionDetector.isConnectingToInternet()) {
                loginDao.onSendLoginDao(Application.getApp().getAppPrefs().getAccount(), this);
            }
        }
    }

    private void clearSchedules() {
        realm.beginTransaction();
        realm.delete(Schedule.class);
        realm.commitTransaction();
    }

    private void saveSchedule(List<Schedule> schedules) {
        realm.beginTransaction();
        realm.copyToRealm(schedules);
        realm.commitTransaction();
    }

}
