package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ButtonDocumentViewFilesRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.FinishDocumentSameRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListButtonSendSameRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SignDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SigningDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FinishDocumentSameResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetLinkEditFilesResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ListButtonSendSameResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeChangeDocumentViewFilesRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckCommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckFinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckMarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SigningOtpRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface IDocumentWaitingService {
    @POST(ServiceUrl.GET_COUNT_DOC_WAIT_URL)
    Call<CountDocumentWaitingRespone> getCount(@Body DocumentWaitingRequest documentWaitingRequest);

    @POST(ServiceUrl.GET_DOC_WAIT_URL)
    Call<DocumentWaitingRespone> getAll(@Body DocumentWaitingRequest documentWaitingRequest);

    @POST(ServiceUrl.GET_LIST_BUTTON_SEND_SAME_URL)
    Call<ListButtonSendSameResponse> getListButtonSendSame(@Body ListButtonSendSameRequest listButtonSendSameRequest);

    @POST(ServiceUrl.FINISH_DOCUMENT_SAME_URL)
    Call<FinishDocumentSameResponse> finishDocumentSame(@Body FinishDocumentSameRequest finishDocumentSameRequest);

    @GET(ServiceUrl.VIEW_DIAGRAM_URL)
    Call<ResponseBody> getBitmapDiagram(@Query("insid") String insId, @Query("defid") String defId);

    @GET(ServiceUrl.GET_DETAIL_DOCUMENT_WAITING_URL)
    Call<DetailDocumentWaitingRespone> getDetail(@Path("id") int docId);

    @GET(ServiceUrl.GET_LOGS_DOCUMENT_WAITING_URL)
    Call<LogRespone> getLogs(@Path("id") int docId);

    @GET(ServiceUrl.GET_ATTACH_FILE_DOCUMENT_WAITING_URL)
    Call<AttachFileRespone> getAttachFiles(@Path("id") int docId);

    @GET(ServiceUrl.GET_RELATED_DOCUMENT_WAITING_URL)
    Call<RelatedDocumentRespone> getRelatedDocs(@Path("id") int docId);

    @GET(ServiceUrl.GET_RELATED_FILE_WAITING_URL)
    Call<RelatedFileRespone> getRelatedFiles(@Path("id") int docId);

    @GET(ServiceUrl.CHECK_MARK_DOC_URL)
    Call<CheckMarkDocumentRespone> checkMark(@Path("id") int docId);

    @GET(ServiceUrl.MARK_DOC_URL)
    Call<MarkDocumentRespone> mark(@Path("id") int docId);

    @GET(ServiceUrl.CHECK_COMMENT_DOC_URL)
    Call<CheckCommentDocumentRespone> checkComment(@Path("id") int docId);

    @POST(ServiceUrl.COMMENT_DOC_URL)
    Call<CommentDocumentRespone> comment(@Body CommentDocumentRequest commentDocumentRequest);

    @POST(ServiceUrl.SIGN_DOC_URL)
    Call<SigningRespone> signDoc(@Body SigningDocumentRequest signingDocumentRequest);

    @POST(ServiceUrl.FINISH_DOC_URL_V2)
    Call<FinishDocumentRespone> finish(@Body CommentRequest commentRequest);

    @GET(ServiceUrl.CHECK_FINISH_DOC_URL)
    Call<CheckFinishDocumentRespone> checkFinish(@Path("id") int docId);

    @POST(ServiceUrl.KY_VAN_BAN_URL)
    Call<SigningRespone> signdocument(@Body SignDocumentRequest signDocumentRequest);

    @POST(ServiceUrl.KY_VAN_BAN_CA_URL)
    Call<SigningRespone> signCAdocument(@Body SignDocumentRequest signDocumentRequest);

    @POST(ServiceUrl.KY_VAN_BAN_PKI_URL)
    Call<SigningRespone> signdocumentPKI(@Body SignDocumentRequest signDocumentRequest);

    @GET(ServiceUrl.KY_VAN_BAN_SIGN_SERVICE_OTP_URL)
    Call<SigningOtpRespone> signServiceOtp(@Path("otp") String otp);

    @GET(ServiceUrl.KY_VAN_BAN_RESEND_SERVICE_OTP_URL)
    Call<SigningRespone> reSendServiceOtp();

    @GET(ServiceUrl.GET_LINK_EDIT_FILES)
    Call<GetLinkEditFilesResponse> GetLinkEditFile(@Path("id") int fileId);
}