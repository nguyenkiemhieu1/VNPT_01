package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.schedule.ScheduleDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleDepartmentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DepartmentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DepartmentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleDepartBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleDepartmentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleDepartView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

public class ScheduleDepartPresenterImpl implements IScheduleDepartPresenter, ICallFinishedListener {
    public IScheduleDepartView scheduleDepartView;
    private ScheduleDao scheduleDao;

    public ScheduleDepartPresenterImpl(IScheduleDepartView scheduleDepartView) {
        this.scheduleDepartView = scheduleDepartView;
        this.scheduleDao = new ScheduleDao();
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof DepartmentRespone) {
            scheduleDepartView.hideProgress();
            DepartmentRespone departmentRespone = (DepartmentRespone) object;
            if (departmentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleDepartView.onSuceessDepart(ConvertUtils.fromJSONList(departmentRespone.getData(), DepartmentInfo.class));
            } else {
                scheduleDepartView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof ScheduleDepartmentRespone) {
            scheduleDepartView.hideProgress();
            ScheduleDepartmentRespone scheduleDepartmentRespone = (ScheduleDepartmentRespone) object;
            if (scheduleDepartmentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleDepartView.onSuccessScheduleDepart(ConvertUtils.fromJSONList(scheduleDepartmentRespone.getData(), ScheduleDepartBossInfo.class));

            } else {
                scheduleDepartView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (scheduleDepartView != null) {
            scheduleDepartView.hideProgress();
            scheduleDepartView.onError((APIError) object);
        }
    }

    @Override
    public void getDepartments() {
        if (scheduleDepartView != null) {
            scheduleDepartView.showProgress();
            scheduleDao.onGetDepartments(this);
        }
    }

    @Override
    public void getScheduleDepartments(ScheduleDepartmentRequest scheduleDepartmentRequest) {
        if (scheduleDepartView != null) {
        //    scheduleDepartView.showProgress();
            scheduleDao.onGetScheduleDepartments(this, scheduleDepartmentRequest);
        }
    }
}
