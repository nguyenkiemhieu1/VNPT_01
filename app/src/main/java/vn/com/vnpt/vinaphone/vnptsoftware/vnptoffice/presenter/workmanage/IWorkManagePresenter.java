package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DanhGiaCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.GetListJobAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.LoginRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateStatusJobRequest;

/**
 * Created by LinhLK - 0948012236 on 4/20/2017.
 */

public interface IWorkManagePresenter {
    void getListJobAssign(GetListJobAssignRequest getListJobAssignRequest);
    void getListJobReceive(GetListJobAssignRequest getListJobAssignRequest);
    void getListStatusCombox(String typeDoc);
    void getDetailReceiveWork(String id, String nxlid);
    void getDetailAssignWork(String id);
    void updateStatusJob(UpdateStatusJobRequest request);
    void getListJobHandling(String id);
    void getListSubTaskDetail(String id);
    void getListUnitPersonDetailProcess(String id);
    void updateJob(UpdateCongViecRequest updateCongViecRequest);
    void danhgiaJob(DanhGiaCongViecRequest danhGiaCongViecRequest);
    void getListFile(String id);

}
