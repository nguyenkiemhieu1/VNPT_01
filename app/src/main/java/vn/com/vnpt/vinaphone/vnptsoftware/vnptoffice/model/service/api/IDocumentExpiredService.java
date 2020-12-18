package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentExpiredRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface IDocumentExpiredService {
    @POST(ServiceUrl.GET_COUNT_DOC_EXPIRED_URL)
    Call<CountDocumentExpiredRespone> getCount(@Body DocumentExpiredRequest documentExpiredRequest);
    @POST(ServiceUrl.GET_DOC_EXPIRED_URL)
    Call<DocumentExpiredRespone> getRecords(@Body DocumentExpiredRequest documentExpiredRequest);
    @GET(ServiceUrl.VIEW_DIAGRAM_URL)
    Call<ResponseBody> getBitmapDiagram(@Query("insid") String insId, @Query("defid") String defId);
    @GET(ServiceUrl.GET_DETAIL_DOCUMENT_EXPIRED_URL)
    Call<DetailDocumentExpiredRespone> getDetail(@Path("id") int docId);
    @GET(ServiceUrl.GET_LOGS_DOCUMENT_EXPIRED_URL)
    Call<LogRespone> getLogs(@Path("id") int docId);
    @GET(ServiceUrl.GET_ATTACH_FILE_DOCUMENT_EXPIRED_URL)
    Call<AttachFileRespone> getAttachFiles(@Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_DOCUMENT_EXPIRED_URL)
    Call<RelatedDocumentRespone> getRelatedDocs(@Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_FILE_EXPIRED_URL)
    Call<RelatedFileRespone> getRelatedFiles(@Path("id") int docId);
    @GET(ServiceUrl.MARK_DOC_URL)
    Call<MarkDocumentRespone> mark(@Path("id") int docId);
}