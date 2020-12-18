package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.workmanage;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import okhttp3.MultipartBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.AddPersonWorkRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CreateSubTaskRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DanhGiaCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.GetListJobAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateStatusJobRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DanhGiaCongViecResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailJobManageResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListBossSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListFileSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobAssignRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobHandLingResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListPersonSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListPersonUnitDetailResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListStatusComboxRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListUnitSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ListFileCongViecResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.StatusRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateJobResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateStatusJobResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UploadFileWorkResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UploadResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IChangeDocumentService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IWorkManageService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */

public class WorkManageDao extends BaseDao implements IWorkManageDao {
    private IWorkManageService workManageService;

    @Override
    public void onGetListJobAssignDao(GetListJobAssignRequest getListJobAssignRequest, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<GetListJobAssignRespone> call = workManageService.getListJobAssign(getListJobAssignRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListJobReceiveDao(GetListJobAssignRequest getListJobAssignRequest, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<GetListJobAssignRespone> call = workManageService.getListJobReceive(getListJobAssignRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListStatusComboxDao(String typeDoc, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<GetListStatusComboxRespone> call = workManageService.getListStatusCombox(typeDoc);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDetailJobReceiveDao(String id, String nxlid, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<DetailJobManageResponse> call = workManageService.getDetailReceiveJob(id, nxlid);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDetailJobAssignDao(String id, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<DetailJobManageResponse> call = workManageService.getDetailAssignJob(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onUpdateStatusJobDao(UpdateStatusJobRequest request, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<UpdateStatusJobResponse> call = workManageService.updateStatusJob(request);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListJobHandling(String id, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<GetListJobHandLingResponse> call = workManageService.getListJobHandling(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListSubTaskDetail(String id, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<GetListSubTaskResponse> call = workManageService.getListSubTaskDetail(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListUnitPersonDetail(String id, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<GetListPersonUnitDetailResponse> call = workManageService.getListUnitPersonDetail(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetUpdatejob(UpdateCongViecRequest updateCongViecRequest, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<UpdateJobResponse> call = workManageService.updateJob(updateCongViecRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDanhGiajob(DanhGiaCongViecRequest danhGiaCongViecRequest, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<DanhGiaCongViecResponse> call = workManageService.getDanhGiaCongViec(danhGiaCongViecRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListFileCongViec(String id, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<ListFileCongViecResponse> call = workManageService.getListFileCongViec(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListBoss(ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<GetListBossSubTaskResponse> call = workManageService.getListBossSubTask();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListUnit(ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<GetListUnitSubTaskResponse> call = workManageService.getListUnitSubTask();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListPerson(String idUnit, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<GetListPersonSubTaskResponse> call = workManageService.getListPersonSubTask(idUnit);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListFileSubTask(String idWork, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<GetListFileSubTaskResponse> call = workManageService.getListFileSubTask(idWork);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCreateSubTask(CreateSubTaskRequest createSubTaskRequest, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<UpdateStatusJobResponse> call = workManageService.getListCreateSubTask(createSubTaskRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onAddPersonTask(AddPersonWorkRequest addPersonWorkRequest, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<UpdateStatusJobResponse> call = workManageService.getApiAddPersonUpdateTask(addPersonWorkRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onUpLoadFileWork(MultipartBody.Part[] files, ICallFinishedListener iCallFinishedListener) {
        workManageService = BaseService.createService(IWorkManageService.class);
        Call<UploadFileWorkResponse> call = workManageService.uploadFilesWork( files);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
