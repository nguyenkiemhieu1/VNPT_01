package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;


import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IUserSwitchView {
    void onSuccessSwitchUser(LoginInfo userInfo);
    void onErrorSwitchUser(APIError apiError);
    void showProgress();
    void hideProgress();
}