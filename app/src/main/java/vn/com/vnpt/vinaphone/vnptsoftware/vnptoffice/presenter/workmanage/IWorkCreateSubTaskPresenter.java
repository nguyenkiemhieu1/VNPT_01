package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage;

import okhttp3.MultipartBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.AddPersonWorkRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CreateSubTaskRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.GetListJobAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateStatusJobRequest;

/**
 * Created by LinhLK - 0948012236 on 4/20/2017.
 */

public interface IWorkCreateSubTaskPresenter {
    void getListBoss();

    void getListUnit(int typeView);

    void getListPerson(String idUnit, int typeView);
    void getListFileSubTask(String idWork);

    void createSubTask(CreateSubTaskRequest createSubTaskRequest);
    void addPersonTask(AddPersonWorkRequest addPersonWorkRequest);
    void UploadFileWork(MultipartBody.Part[] files);
}
