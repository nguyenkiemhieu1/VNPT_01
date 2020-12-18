package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentProcessedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckDocProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckMarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckRecoverDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RecoverDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface IDocumentProcessedService {
    @POST(ServiceUrl.GET_COUNT_DOC_PROCECSSED_URL)
    Call<CountDocumentProcessedRespone> getCount(@Body DocumentProcessedRequest documentProcessedRequest);
    @POST(ServiceUrl.GET_DOC_PROCECSSED_URL)
    Call<DocumentProcessedRespone> getRecords(@Body DocumentProcessedRequest documentProcessedRequest);
    @GET(ServiceUrl.CHECK_RECOVER_DOC_RECEIVE_URL)
    Call<CheckRecoverDocumentRespone> checkReceive(@Path("id") int docId);
    @GET(ServiceUrl.CHECK_RECOVER_DOC_SEND_URL)
    Call<CheckRecoverDocumentRespone> checkSend(@Path("id") int docId);
    @GET(ServiceUrl.RECOVER_DOC_RECEIVE_URL)
    Call<RecoverDocumentRespone> recoverDocumentReceive(@Path("id") int docId);
    @GET(ServiceUrl.RECOVER_DOC_SEND_URL)
    Call<RecoverDocumentRespone> recoverDocumentSend(@Path("id") int docId);
    @GET(ServiceUrl.GET_DETAIL_DOCUMENT_PROCESSED_URL)
    Call<DetailDocumentProcessedRespone> getDetail(@Path("id") int docId);
    @GET(ServiceUrl.GET_LOGS_DOCUMENT_PROCESSED_URL)
    Call<LogRespone> getLogs(@Path("id") int docId);
    @GET(ServiceUrl.GET_ATTACH_FILE_DOCUMENT_PROCESSED_URL)
    Call<AttachFileRespone> getAttachFiles(@Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_DOCUMENT_PROCESSED_URL)
    Call<RelatedDocumentRespone> getRelatedDocs(@Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_FILE_PROCESSED_URL)
    Call<RelatedFileRespone> getRelatedFiles(@Path("id") int docId);
    @GET(ServiceUrl.CHECK_MARK_DOC_URL)
    Call<CheckMarkDocumentRespone> checkMark(@Path("id") int docId);
    @GET(ServiceUrl.MARK_DOC_URL)
    Call<MarkDocumentRespone> mark(@Path("id") int docId);
    @GET(ServiceUrl.VIEW_DIAGRAM_URL)
    Call<ResponseBody> getBitmapDiagram(@Query("insid") String insId, @Query("defid") String defId);
    @GET(ServiceUrl.CHECK_CHANGE_DOC_PROCECSSED_URL)
    Call<CheckDocProcessedRespone> checkChangeDoc(@Path("id") int docId);
}