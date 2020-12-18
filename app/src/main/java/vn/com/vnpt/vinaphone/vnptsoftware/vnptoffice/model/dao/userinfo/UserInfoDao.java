package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.userinfo;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UserInfoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IUserInfoService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 8/24/2017.
 */

public class UserInfoDao extends BaseDao implements IUserInfoDao {
    private IUserInfoService userInfoService;

    @Override
    public void onGetUserInfoDao(ICallFinishedListener iCallFinishedListener) {
        userInfoService = BaseService.createService(IUserInfoService.class);
        Call<UserInfoRespone> call = userInfoService.getUserInfo();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetUserInfoDao(ICallFinishedListener iCallFinishedListener, String id) {
        userInfoService = BaseService.createService(IUserInfoService.class);
        Call<UserInfoRespone> call = userInfoService.getUserInfo(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetAvatarDao(ICallFinishedListener iCallFinishedListener, String id) {
        userInfoService = BaseService.createService(IUserInfoService.class);
        Call<ResponseBody> call = userInfoService.getAvatar(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
    @Override
    public void onGetListSwitchUserDao(ICallFinishedListener iCallFinishedListener, String id) {
        userInfoService = BaseService.createService(IUserInfoService.class);
        Call<LoginRespone> call = userInfoService.getListSwitchUser(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
