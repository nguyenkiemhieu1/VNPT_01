package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.FileMeeting;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IDownloadService {
    @GET(ServiceUrl.DOWNLOAD_FILE_URL)
    Call<ResponseBody> download(@HeaderMap Map<String, String> headers, @Path("id") int docId);

    @GET(ServiceUrl.GET_FILE_URL_DOC)
    Call<GetViewFileObj> getUrlFileDoc(@HeaderMap Map<String, String> headers, @Path("id") int docId);

    @GET(ServiceUrl.THONG_TIN_DIEU_HANH_DOC_URL)
    Call<GetViewFileObj> getUrlFileDoc(@HeaderMap Map<String, String> headers, @Query("file") String fileDoc);

    @POST(ServiceUrl.DOWNLOAD_FILE_CHIDAO_URL)
    Call<ResponseBody> download(@HeaderMap Map<String, String> headers, @Body DownloadChiDaoRequest downloadChiDaoRequest);

    @POST(ServiceUrl.FILE_MEETING_URL)
    Call<ResponseBody> download(@HeaderMap Map<String, String> headers, @Body FileMeeting fileMeeting);

    @GET(ServiceUrl.LICH_HOP_DOC_URL)
    Call<GetViewFileObj> getUrlFileLichHopDoc(@HeaderMap Map<String, String> headers, @Query("file") String fileDoc);

    @GET(ServiceUrl.GET_VIEW_FILE_DOCUMENT)
    Call<ResponseBody> getViewFileDocument(@HeaderMap Map<String, String> headers, @Path("id") int id);

    @GET(ServiceUrl.GET_VIEW_FILE_CHIDAO)
    Call<ResponseBody> getViewFileChiDao(@HeaderMap Map<String, String> headers, @Query("file") String  file);
    @GET(ServiceUrl.GET_VIEW_FILE_SCHEDULE)
    Call<ResponseBody> getViewFileSchedule(@HeaderMap Map<String, String> headers, @Query("file") String  file);

}