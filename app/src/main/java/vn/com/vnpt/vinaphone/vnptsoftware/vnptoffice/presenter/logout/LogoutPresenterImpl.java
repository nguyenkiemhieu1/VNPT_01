package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.logout;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.logout.LogoutDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogoutRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILogoutView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 4/20/2017.
 */

public class LogoutPresenterImpl implements ILogoutPresenter, ICallFinishedListener {
    public ILogoutView logoutView;
    public LogoutDao logoutDao;

    public LogoutPresenterImpl(ILogoutView logoutView) {
        this.logoutView = logoutView;
        this.logoutDao = new LogoutDao();
    }

    @Override
    public void logout() {
        if (logoutView != null) {
            logoutView.showLogoutProgress();
            logoutDao.onSendLogoutDao(this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        logoutView.hideLogoutProgress();
        LogoutRespone logoutRespone = (LogoutRespone) object;
        if (logoutRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            logoutView.onSuccessLogout();
        } else {
            logoutView.onErrorLogout(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
        }
    }

    @Override
    public void onCallError(Object object) {
        logoutView.hideLogoutProgress();
        logoutView.onErrorLogout((APIError) object);
    }

}
