package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentMarkRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface IDocumentMarkService {
    @POST(ServiceUrl.GET_COUNT_DOC_MARK_URL)
    Call<CountDocumentMarkRespone> getCount(@Body DocumentMarkRequest documentMarkRequest);
    @POST(ServiceUrl.GET_DOC_MARK_URL)
    Call<DocumentMarkRespone> getRecords(@Body DocumentMarkRequest documentMarkRequest);
    @GET(ServiceUrl.GET_DETAIL_DOCUMENT_MARK_URL)
    Call<DetailDocumentMarkRespone> getDetail(@Path("id") int docId);
    @GET(ServiceUrl.GET_LOGS_DOCUMENT_MARK_URL)
    Call<LogRespone> getLogs(@Path("id") int docId);
    @GET(ServiceUrl.GET_ATTACH_FILE_DOCUMENT_MARK_URL)
    Call<AttachFileRespone> getAttachFiles(@Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_DOCUMENT_MARK_URL)
    Call<RelatedDocumentRespone> getRelatedDocs(@Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_FILE_MARK_URL)
    Call<RelatedFileRespone> getRelatedFiles(@Path("id") int docId);
    @GET(ServiceUrl.VIEW_DIAGRAM_URL)
    Call<ResponseBody> getBitmapDiagram(@Query("insid") String insId, @Query("defid") String defId);
    @GET(ServiceUrl.MARK_DOC_URL)
    Call<MarkDocumentRespone> mark(@Path("id") int docId);
}