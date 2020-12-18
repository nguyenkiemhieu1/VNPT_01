package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UserInfoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface IUserInfoService {
    @GET(ServiceUrl.GET_USER_INFO_URL)
    Call<UserInfoRespone> getUserInfo();
    @GET(ServiceUrl.GET_CONTACT_INFO_URL)
    Call<UserInfoRespone> getUserInfo(@Path("userid") String id);
    @GET(ServiceUrl.GET_AVATAR_URL)
    Call<ResponseBody> getAvatar(@Path("userid") String id);
    @GET(ServiceUrl.GET_LIST_SWITCH_USER_URL)
    Call<LoginRespone> getListSwitchUser(   @Path("userid") String id);
}