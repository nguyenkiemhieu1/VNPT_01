package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleDepartmentRequest;

public interface IScheduleDepartPresenter {
    void getDepartments();
    void getScheduleDepartments(ScheduleDepartmentRequest scheduleDepartmentRequest);
}
