package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateJobResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateStatusJobResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UploadFileWorkResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UploadResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface IWorkManageService {
    @POST(ServiceUrl.GET_LIST_JOB_DATA_ASSIGN)
    Call<GetListJobAssignRespone> getListJobAssign(@Body GetListJobAssignRequest getListJobAssignRequest);

    @POST(ServiceUrl.GET_LIST_JOB_DATA_RECEIVE)
    Call<GetListJobAssignRespone> getListJobReceive(@Body GetListJobAssignRequest getListJobAssignRequest);

    @GET(ServiceUrl.GET_LIST_STATUS_BOX)
    Call<GetListStatusComboxRespone> getListStatusCombox(@Path("type") String type);

    @GET(ServiceUrl.GET_LIST_JOB_RECEIVE)
    Call<DetailJobManageResponse> getDetailReceiveJob(@Path("id") String workId, @Path("nxlid") String nxlid);

    @GET(ServiceUrl.GET_LIST_JOB_ASSIGN)
    Call<DetailJobManageResponse> getDetailAssignJob(@Path("id") String workId);

    @POST(ServiceUrl.CALL_JOB_UPDATE_STATUS)
    Call<UpdateStatusJobResponse> updateStatusJob(@Body UpdateStatusJobRequest updateStatusJobRequest);

    @PUT(ServiceUrl.UPDATE_CONGVIEC)
    Call<UpdateJobResponse> updateJob(@Body UpdateCongViecRequest updateCongViecRequest);

    @GET(ServiceUrl.GET_LIST_JOB_HANDLING_PROGRESS)
    Call<GetListJobHandLingResponse> getListJobHandling(@Path("id") String workIdt);

    @GET(ServiceUrl.GET_LIST_SUB_TASK_DETAIL)
    Call<GetListSubTaskResponse> getListSubTaskDetail(@Path("id") String workIdt);

    @GET(ServiceUrl.GET_LIST_UNIT_PERSON_DETAIL)
    Call<GetListPersonUnitDetailResponse> getListUnitPersonDetail(@Path("id") String workIdt);

    @GET(ServiceUrl.GET_LIST_BOSS_SUBTASK)
    Call<GetListBossSubTaskResponse> getListBossSubTask();

    @GET(ServiceUrl.GET_LIST_UNIT_SUBTASK)
    Call<GetListUnitSubTaskResponse> getListUnitSubTask();

    @GET(ServiceUrl.GET_LIST_PERSON_SUBTASK)
    Call<GetListPersonSubTaskResponse> getListPersonSubTask(@Path("id") String idUnit);

    @GET(ServiceUrl.GET_LIST_FILE_SUBTASK)
    Call<GetListFileSubTaskResponse> getListFileSubTask(@Path("id") String idWork);

    @POST(ServiceUrl.CREATE_SUBTASK)
    Call<UpdateStatusJobResponse> getListCreateSubTask(@Body CreateSubTaskRequest createSubTaskRequest);

    @POST(ServiceUrl.DANHGIA_CONGVIEC)
    Call<DanhGiaCongViecResponse> getDanhGiaCongViec(@Body DanhGiaCongViecRequest danhGiaCongViecRequest);

    @POST(ServiceUrl.CALL_API_UPDATE_ASSIGN)
    Call<UpdateStatusJobResponse> getApiAddPersonUpdateTask(@Body AddPersonWorkRequest addPersonWorkRequest);

    @GET(ServiceUrl.LIST_FILE_CONG_VIEC)
    Call<ListFileCongViecResponse> getListFileCongViec(@Path("id") String id);

    @Multipart
    @POST(ServiceUrl.UPLOAD_FILE_CHUYEN_VB_URL)
    Call<UploadFileWorkResponse> uploadFilesWork(@Part MultipartBody.Part[] files);
}