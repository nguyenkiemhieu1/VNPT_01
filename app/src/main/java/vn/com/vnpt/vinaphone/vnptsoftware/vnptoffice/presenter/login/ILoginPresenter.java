package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.LoginRequest;

/**
 * Created by LinhLK - 0948012236 on 4/20/2017.
 */

public interface ILoginPresenter {
    void getUserLoginPresenter(LoginRequest loginRequest);
    void loginPresenter(LoginRequest loginRequest);
}
