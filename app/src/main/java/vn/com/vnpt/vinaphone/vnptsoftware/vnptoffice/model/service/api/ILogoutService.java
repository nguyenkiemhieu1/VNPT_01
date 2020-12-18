package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogoutRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface ILogoutService {
    @GET(ServiceUrl.LOGOUT_URL)
    Call<LogoutRespone> logout();
}