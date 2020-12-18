package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.LoginRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface ILoginService {
    @POST(ServiceUrl.LOGIN_V2_URL)
    @Headers({"Content-Type: application/json"})
    Call<LoginRespone> getUserLogin(@Body LoginRequest loginRequest);
}