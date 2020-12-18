package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CheckVersionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckVersionResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IVersionService {
    @POST(ServiceUrl.CHECK_VERSION_URL)
    Call<CheckVersionResponse> checkVersion(@Body CheckVersionRequest checkVersionRequest);
}