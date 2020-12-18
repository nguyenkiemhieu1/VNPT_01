package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.workmanage;

import okhttp3.MultipartBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.AddPersonWorkRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CreateSubTaskRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DanhGiaCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.GetListJobAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateStatusJobRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListStatusComboxRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */

public interface IWorkManageDao {
    void onGetListJobAssignDao(GetListJobAssignRequest getListJobAssignRequest, ICallFinishedListener iCallFinishedListener);

    void onGetListJobReceiveDao(GetListJobAssignRequest getListJobAssignRequest, ICallFinishedListener iCallFinishedListener);

    void onGetListStatusComboxDao(String typyeDoc, ICallFinishedListener iCallFinishedListener);

    void onGetDetailJobReceiveDao(String id, String nxlid, ICallFinishedListener iCallFinishedListener);

    void onGetDetailJobAssignDao(String id, ICallFinishedListener iCallFinishedListener);

    void onUpdateStatusJobDao(UpdateStatusJobRequest request, ICallFinishedListener iCallFinishedListener);

    void onGetListJobHandling(String id, ICallFinishedListener iCallFinishedListener);

    void onGetListSubTaskDetail(String id, ICallFinishedListener iCallFinishedListener);

    void onGetListUnitPersonDetail(String id, ICallFinishedListener iCallFinishedListener);

    void onGetUpdatejob(UpdateCongViecRequest updateCongViecRequest, ICallFinishedListener iCallFinishedListener);

    void onGetDanhGiajob(DanhGiaCongViecRequest danhGiaCongViecRequest, ICallFinishedListener iCallFinishedListener);

    void onGetListFileCongViec(String id, ICallFinishedListener iCallFinishedListener);

    void onGetListBoss(ICallFinishedListener iCallFinishedListener);

    void onGetListUnit(ICallFinishedListener iCallFinishedListener);

    void onGetListPerson(String idUnit, ICallFinishedListener iCallFinishedListener);

    void onGetListFileSubTask(String idWork, ICallFinishedListener iCallFinishedListener);

    void onCreateSubTask(CreateSubTaskRequest createSubTaskRequest, ICallFinishedListener iCallFinishedListener);

    void onAddPersonTask(AddPersonWorkRequest addPersonWorkRequest, ICallFinishedListener iCallFinishedListener);

    void onUpLoadFileWork(MultipartBody.Part[] files, ICallFinishedListener iCallFinishedListener);
}
