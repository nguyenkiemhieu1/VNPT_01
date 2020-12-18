package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.NumberCountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SearchPersonRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FormDataResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.JobPositionRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LienThongRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonGroupChangeDocRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonListRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonOrUnitExpectedResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UploadResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface IChangeDocumentService {
    @POST(ServiceUrl.GET_LIST_TYPE_CHANGE_DOC_URL)
    Call<TypeChangeDocumentRespone> getTypeChange(@Body TypeChangeDocRequest typeChangeDocumentRequest);

    @POST(ServiceUrl.GET_LIST_TYPE_CHANGE_DOC_VIEW_FILES_URL)
    Call<TypeChangeDocumentRespone> getTypeChangeViewFiles(@Body TypeChangeDocRequest typeChangeDocumentRequest);

    @POST(ServiceUrl.GET_PERSONS_PROCESS_URL)
    Call<PersonListRespone> getPersonProcess(@Body ListProcessRequest listProcessRequest);

    @GET(ServiceUrl.GET_PERSONS_OR_UNIT_EXPECTED_URL)
    Call<PersonOrUnitExpectedResponse> getPersonOrUnitExpected(@Path("id") int docId);

    @POST(ServiceUrl.GET_PERSONS_SEND_URL)
    Call<PersonListRespone> getPersonSend(@Body SearchPersonRequest searchPersonRequest);

    @GET(ServiceUrl.GET_PERSONS_NOTIFY_URL)
    Call<PersonListRespone> getPersonNotify();

    @POST(ServiceUrl.CHANGE_DOC_SEND_URL)
    Call<ChangeDocumentRespone> changeSend(@Body ChangeDocumentSendRequest changeDocumentSendRequest);

    @POST(ServiceUrl.CHANGE_DOC_DIRECT_URL)
    Call<ChangeDocumentRespone> changeDirect(@Body ChangeDocumentDirectRequest changeDocumentDirectRequest);
    @POST(ServiceUrl.ISSUING_DOCUMENTS_COME_SAME_URL)
    Call<ChangeDocumentRespone> issuingDocumentComeSame(@Body ChangeDocumentDirectRequest changeDocumentDirectRequest);

    @POST(ServiceUrl.CHANGE_DOC_RECEIVE_URL)
    Call<ChangeDocumentRespone> changeReceive(@Body ChangeDocumentReceiveRequest changeDocumentReceiveRequest);
    @POST(ServiceUrl.SEND_DOCUMENT_COME_SAME_URL)
    Call<ChangeDocumentRespone> sendDocumentComeSame(@Body ChangeDocumentReceiveRequest changeDocumentReceiveRequest);

    @POST(ServiceUrl.CHANGE_DOC_PROCESS_URL)
    Call<ChangeDocumentRespone> changeProcess(@Body ChangeProcessRequest changeProcessRequest);
    @POST(ServiceUrl.SEND_PROCESS_DOCUMENT_SAME_URL)
    Call<ChangeDocumentRespone> sendProcessDocumentSame(@Body ChangeProcessRequest changeProcessRequest);
    @POST(ServiceUrl.CHANGE_DOC_NOTIFY_URL)
    Call<ChangeDocumentRespone> changeNotify(@Body ChangeNotifyRequest changeNotifyRequest);
    @POST(ServiceUrl.SEND_PROCESS_DOCUMENT_SAME_URL)
    Call<ChangeDocumentRespone> sendNotifyDocumentSame(@Body ChangeNotifyRequest changeNotifyRequest);

    @GET(ServiceUrl.GET_JOB_POSSITION_URL)
    Call<JobPositionRespone> getJobPossitions();

    @GET(ServiceUrl.GET_UNIT_URL)
    Call<UnitRespone> getUnits();

    @GET(ServiceUrl.GET_LIEN_THONG_XL_URL)
    Call<LienThongRespone> getLienThongXL();

    @GET(ServiceUrl.GET_LIEN_THONG_BH_URL)
    Call<LienThongRespone> getLienThongBH(@Path("id") String docId);

    @POST(ServiceUrl.CHANGE_DOC_NOTIFY_XEMDB_URL)
    Call<ChangeDocumentRespone> changeNotify(@Body ChangeDocumentNotifyRequest changeDocumentNotifyRequest);

    @GET(ServiceUrl.GET_GROUP_PERSON_CN_URL)
    Call<PersonGroupChangeDocRespone> getGroupPersonCN();

    @GET(ServiceUrl.GET_GROUP_PERSON_DV_URL)
    Call<PersonGroupChangeDocRespone> getGroupPersonDV();

    @POST(ServiceUrl.GET_PERSONS_SEND_PROCESS_URL)
    Call<PersonListRespone> getPersonSendProcess(@Body SearchPersonRequest searchPersonRequest);

    @GET(ServiceUrl.GET_UNIT_CLERK_URL)
    Call<UnitRespone> getUnitClerks();

    @GET(ServiceUrl.GET_LIST_FORM_DATA_URL)
    Call<FormDataResponse> getListFormContent();

    @GET(ServiceUrl.GET_COUNT_COMMENT_URL)
    Call<NumberCountDocument> getCountDocument();

    @Multipart
    @POST(ServiceUrl.UPLOAD_FILE_CHUYEN_VB_URL)
    Call<UploadResponse> uploadFiles(@Part MultipartBody.Part[] files);
}