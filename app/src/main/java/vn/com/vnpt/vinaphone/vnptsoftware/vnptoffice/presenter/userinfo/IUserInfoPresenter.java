package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.userinfo;

/**
 * Created by LinhLK - 0948012236 on 8/24/2017.
 */

public interface IUserInfoPresenter {
    void getUserInfo();
    void getUserInfo(String id);
    void getAvatar(String userId);
    void getListSwitchUser(String userId);
}
