package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentNotificationRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckFinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface IDocumentNotificationService {
    @POST(ServiceUrl.GET_COUNT_DOC_NOTIFICATION_URL)
    Call<CountDocumentNotificationRespone> getCount(@Body DocumentNotificationRequest documentNotificationRequest);
    @POST(ServiceUrl.GET_DOC_NOTIFICATION_URL)
    Call<DocumentNotificationRespone> getRecords(@Body DocumentNotificationRequest documentNotificationRequest);
    @GET(ServiceUrl.GET_DETAIL_DOCUMENT_NOTIFICATION_URL)
    Call<DetailDocumentNotificationRespone> getDetail(@Path("id") int docId);
    @GET(ServiceUrl.GET_LOGS_DOCUMENT_NOTIFICATION_URL)
    Call<LogRespone> getLogs(@Path("id") int docId);
    @GET(ServiceUrl.GET_ATTACH_FILE_DOCUMENT_NOTIFICATION_URL)
    Call<AttachFileRespone> getAttachFiles(@Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_DOCUMENT_NOTIFICATION_URL)
    Call<RelatedDocumentRespone> getRelatedDocs(@Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_FILE_NOTIFICATION_URL)
    Call<RelatedFileRespone> getRelatedFiles(@Path("id") int docId);
    @GET(ServiceUrl.VIEW_DIAGRAM_URL)
    Call<ResponseBody> getBitmapDiagram(@Query("insid") String insId, @Query("defid") String defId);
    @GET(ServiceUrl.MARK_DOC_URL)
    Call<MarkDocumentRespone> mark(@Path("id") int docId);
    @POST(ServiceUrl.FINISH_DOC_URL_V2)
    Call<FinishDocumentRespone> finish(@Body CommentRequest commentRequest);
    @GET(ServiceUrl.CHECK_FINISH_DOC_URL)
    Call<CheckFinishDocumentRespone> checkFinish(@Path("id") int docId);
}