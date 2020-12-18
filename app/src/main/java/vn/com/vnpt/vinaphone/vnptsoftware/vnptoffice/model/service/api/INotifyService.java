package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailNotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.NotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReadedNotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface INotifyService {
    @POST(ServiceUrl.GET_NOTIFY_URL)
    Call<NotifyRespone> getNotifys(@Body ListNotifyRequest listNotifyRequest);
    @POST(ServiceUrl.READED_NOTIFY_URL)
    Call<ReadedNotifyRespone> readedNotifys(@Body ReadedNotifyRequest readedNotifyRequest);
    @GET(ServiceUrl.GET_DETAIL_NOTIFY_URL)
    Call<DetailNotifyRespone> getDetailNotify(@Path("id") int notifyId);
    @GET(ServiceUrl.GET_COUNT_COMMENT_URL)
    Call<CountDocumentRespone> getCountDocument();
}