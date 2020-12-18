package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.schedule.ScheduleDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CreateScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonAndUnitScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.BussinessInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChairScheduleWeekInfor;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChairScheduleWeekResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CreateScheduleWeekResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DepartmentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DepartmentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MeetingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleBossRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleBussinessRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleMeetingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleDetailView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleWeekView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ISelectPersonScheduleWeekView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */

public class SchedulePresenterImpl implements ISchedulePresenter, ICallFinishedListener {
    public IScheduleView scheduleView;
    public IScheduleWeekView scheduleWeekView;
    public ISelectPersonScheduleWeekView selectPersonScheduleWeekView;
    public IScheduleDetailView scheduleDetailView;
    private ScheduleDao scheduleDao;

    public SchedulePresenterImpl(IScheduleView scheduleView) {
        this.scheduleView = scheduleView;
        this.scheduleDao = new ScheduleDao();
    }

    public SchedulePresenterImpl(IScheduleDetailView scheduleDetailView) {
        this.scheduleDetailView = scheduleDetailView;
        this.scheduleDao = new ScheduleDao();
    }

    public SchedulePresenterImpl(ISelectPersonScheduleWeekView selectPersonScheduleWeekView) {
        this.selectPersonScheduleWeekView = selectPersonScheduleWeekView;
        this.scheduleDao = new ScheduleDao();
    }

    public SchedulePresenterImpl(IScheduleWeekView scheduleWeekView) {
        this.scheduleWeekView = scheduleWeekView;
        this.scheduleDao = new ScheduleDao();
    }


    @Override
    public void getWeekSchedules(ScheduleRequest scheduleRequest) {
        if (scheduleView != null) {
            scheduleView.showProgress();
            scheduleDao.onSendGetWeekSchedules(this, scheduleRequest);
        }
    }

    @Override
    public void getDetailMeeting(int scheduleId) {
        if (scheduleDetailView != null) {
            scheduleDao.onGetDetailMeeting(scheduleId, this);
        }
    }

    @Override
    public void getDetailBussiness(int scheduleId) {
        if (scheduleDetailView != null) {
            scheduleDao.onGetDetailBussiness(scheduleId, this);
        }
    }

    @Override
    public void getWeekSchedulesBoss(ScheduleBossRequest scheduleBossRequest) {
        if (scheduleView != null) {
            scheduleView.showProgress();
            scheduleDao.onSendGetWeekSchedulesBoss(this, scheduleBossRequest);
        }
    }

    @Override
    public void getScheduleWeek(ScheduleWeekRequest scheduleWeekRequest) {
        if (scheduleWeekView != null) {
            scheduleWeekView.showProgress();
            scheduleDao.onGetListSchedulesWeek(this, scheduleWeekRequest);
        }
    }

    @Override
    public void createScheduleWeek(CreateScheduleWeekRequest createScheduleWeekRequest) {
        if (scheduleWeekView != null) {
            scheduleWeekView.showProgress();
            scheduleDao.onCreateSchedulesWeek(this, createScheduleWeekRequest);
        }
    }

    @Override
    public void getPersonAndUnitScheduleWeek(PersonAndUnitScheduleWeekRequest personAndUnitScheduleWeekRequest) {
        if (selectPersonScheduleWeekView != null) {
            selectPersonScheduleWeekView.showProgress();
            scheduleDao.onGetPersonAndUnitSchedulesWeek(this, personAndUnitScheduleWeekRequest);
        }
    }

    @Override
    public void getChairScheduleWeek() {
        if (scheduleWeekView != null) {
            scheduleWeekView.showProgress();
            scheduleDao.onGetChairSchedulesWeek(this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof ScheduleRespone) {
            scheduleView.hideProgress();
            ScheduleRespone scheduleRespone = (ScheduleRespone) object;
            if (scheduleRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleView.onSuccess(ConvertUtils.fromJSONList(scheduleRespone.getData(), ScheduleInfo.class));
            } else {
                scheduleView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof ScheduleWeekResponse) {
            scheduleWeekView.hideProgress();
            ScheduleWeekResponse scheduleWeekResponse = (ScheduleWeekResponse) object;
            if (scheduleWeekResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleWeekView.onSuccess(ConvertUtils.fromJSONList(scheduleWeekResponse.getData(), ScheduleWeekInfo.class));
            } else {
                scheduleWeekView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof CreateScheduleWeekResponse) {
            scheduleWeekView.hideProgress();
            CreateScheduleWeekResponse createScheduleWeekResponse = (CreateScheduleWeekResponse) object;
            if (createScheduleWeekResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (createScheduleWeekResponse.getData().equals("TRUE")) {
                    scheduleWeekView.onCreateScheduleWeekSuccess();
                } else {
                    scheduleWeekView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
                }
            } else {
                scheduleWeekView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }

        if (object instanceof ChairScheduleWeekResponse) {
            scheduleWeekView.hideProgress();
            ChairScheduleWeekResponse chairScheduleWeekResponse = (ChairScheduleWeekResponse) object;
            if (chairScheduleWeekResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleWeekView.onGetChairSuccess(ConvertUtils.fromJSONList(chairScheduleWeekResponse.getData(), ChairScheduleWeekInfor.class));
            } else {
                scheduleWeekView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof PersonAndUnitScheduleWeekResponse) {
            selectPersonScheduleWeekView.hideProgress();
            PersonAndUnitScheduleWeekResponse personAndUnitScheduleWeekResponse = (PersonAndUnitScheduleWeekResponse) object;
            if (personAndUnitScheduleWeekResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                selectPersonScheduleWeekView.onSuccess(ConvertUtils.fromJSONList(personAndUnitScheduleWeekResponse.getData(), PersonAndUnitScheduleWeekInfo.class));
            } else {
                selectPersonScheduleWeekView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof ScheduleMeetingRespone) {
            ScheduleMeetingRespone scheduleMeetingRespone = (ScheduleMeetingRespone) object;
            if (scheduleMeetingRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleDetailView.onSuccess(ConvertUtils.fromJSON(scheduleMeetingRespone.getData(), MeetingInfo.class));
            } else {
                scheduleDetailView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof ScheduleBussinessRespone) {
            ScheduleBussinessRespone scheduleBussinessRespone = (ScheduleBussinessRespone) object;
            if (scheduleBussinessRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleDetailView.onSuccess(ConvertUtils.fromJSON(scheduleBussinessRespone.getData(), BussinessInfo.class));
            } else {
                scheduleDetailView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof ScheduleBossRespone) {
            scheduleView.hideProgress();
            ScheduleBossRespone scheduleRespone = (ScheduleBossRespone) object;
            if (scheduleRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleView.onSuccessBoss(ConvertUtils.fromJSONList(scheduleRespone.getData(), ScheduleBossInfo.class));
            } else {
                scheduleView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (scheduleView != null) {
            scheduleView.hideProgress();
            scheduleView.onError((APIError) object);
        }
        if (scheduleDetailView != null) {
            scheduleDetailView.onError((APIError) object);
        }
        if (scheduleWeekView != null) {
            scheduleWeekView.hideProgress();
            scheduleWeekView.onError((APIError) object);
        }
        if (selectPersonScheduleWeekView != null) {
            selectPersonScheduleWeekView.hideProgress();
            selectPersonScheduleWeekView.onError((APIError) object);
        }
    }

}
