package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoGuiRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DanhSachDonViNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonInGroupChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReplyChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SendChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChiDaoFlowRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DanhSachDonViNhanRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DeleteChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DonViNhanRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FileChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonGroupChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonInGroupChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonReceiveChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReplyChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SaveChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SavePersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SendChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UploadResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IChiDaoService {
    @POST(ServiceUrl.GET_CHIDAO_NHAN_URL)
    Call<ChiDaoRespone> getChiDaoNhan(@Body ChiDaoNhanRequest chiDaoNhanRequest);

    @POST(ServiceUrl.GET_CHIDAO_GUI_URL)
    Call<ChiDaoRespone> getChiDaoGui(@Body ChiDaoGuiRequest chiDaoGuiRequest);

    @Multipart
    @POST(ServiceUrl.UPLOAD_FILE_URL)
    Call<UploadResponse> uploadFiles(@Part MultipartBody.Part[] files);

    @POST(ServiceUrl.CREATE_CHIDAO_URL)
    Call<SaveChiDaoRespone> createChiDao(@Body ChiDaoRequest chiDaoRequest);

    @PUT(ServiceUrl.EDIT_CHIDAO_URL)
    Call<SaveChiDaoRespone> editChiDao(@Body ChiDaoRequest chiDaoRequest);

    @POST(ServiceUrl.GET_PERSON_CHIDAO_URL)
    Call<PersonChiDaoRespone> getPersons(@Body PersonChiDaoRequest personChiDaoRequest);

    @POST(ServiceUrl.SAVE_PERSON_CHIDAO_URL)
    Call<SavePersonChiDaoRespone> savePersons(@Body SavePersonChiDaoRequest savePersonChiDaoRequest);

    @POST(ServiceUrl.GET_PERSON_RECEIVE_CHIDAO_URL)
    Call<PersonReceiveChiDaoRespone> getPersonsReceive(@Body PersonReceiveChiDaoRequest personReceiveChiDaoRequest);

    @GET(ServiceUrl.GET_PERSON_GROUP_CHIDAO_URL)
    Call<PersonGroupChiDaoRespone> getPersonsGroup();

    @POST(ServiceUrl.SEND_CHIDAO_URL)
    Call<SendChiDaoRespone> send(@Body SendChiDaoRequest sendChiDaoRequest);

    @GET(ServiceUrl.GET_FLOW_CHIDAO_URL)
    Call<ChiDaoFlowRespone> getFlows(@Path("id") String id);

    @GET(ServiceUrl.GET_FILE_CHIDAO_URL)
    Call<FileChiDaoRespone> getFiles(@Path("id") String id);

    @DELETE(ServiceUrl.GET_DELETE_CHIDAO_URL)
    Call<DeleteChiDaoRespone> delete(@Path("id") String id);

    @GET(ServiceUrl.GET_DETAIL_CHIDAO_URL)
    Call<DetailChiDaoRespone> getDetail(@Path("id") String id);

    @POST(ServiceUrl.GET_PERSON_IN_GROUP_CHIDAO_URL)
    Call<PersonInGroupChiDaoRespone> getPersonInGroup(@Body PersonInGroupChiDaoRequest personInGroupChiDaoRequest);

    @POST(ServiceUrl.GET_PERSON_REPLY_CHIDAO_URL)
    Call<ReplyChiDaoRespone> getPersonReply(@Body ReplyChiDaoRequest replyChiDaoRequest);

    @POST(ServiceUrl.GET_PERSON_RECEIVED_CHIDAO_URL)
    Call<PersonReceiveChiDaoRespone> getPersonsReceived(@Body PersonReceiveChiDaoRequest personReceiveChiDaoRequest);
    @GET(ServiceUrl.GET_DONVI_NHAN_URL)
    Call<DonViNhanRespone> getDonViNhan();

    @POST(ServiceUrl.GET_DANHSACH_DONVI_NHAN_URL)
    Call<DanhSachDonViNhanRespone> getDanhSachDonViNhan(@Body DanhSachDonViNhanRequest danhSachDonViNhanRequest);

}