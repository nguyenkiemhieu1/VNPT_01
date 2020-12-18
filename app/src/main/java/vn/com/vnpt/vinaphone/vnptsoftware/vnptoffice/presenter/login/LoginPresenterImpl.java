package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.login.LoginDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.LoginRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 4/20/2017.
 */

public class LoginPresenterImpl implements ILoginPresenter, ICallFinishedListener {
    public ILoginView loginView;
    public LoginDao loginDao;

    public LoginPresenterImpl(ILoginView loginView) {
        this.loginView = loginView;
        this.loginDao = new LoginDao();
    }

    @Override
    public void getUserLoginPresenter(LoginRequest loginRequest) {
        if (loginView != null) {
            loginDao.onSendLoginDao(loginRequest, this);
        }
    }

    @Override
    public void loginPresenter(LoginRequest loginRequest) {
        if (loginView != null) {
            loginView.showProgress();
            loginDao.onSendLoginDao(loginRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (loginView != null) {
            loginView.hideProgress();
        }
        LoginRespone loginRespone = (LoginRespone) object;
        if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
            loginView.onSuccessLogin(loginInfo);
        } else {
            loginView.onErrorLogin(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
        }
    }

    @Override
    public void onCallError(Object object) {
        if(loginView!=null){
            loginView.onErrorLogin((APIError) object);
            loginView.hideProgress();
        }

    }

}
