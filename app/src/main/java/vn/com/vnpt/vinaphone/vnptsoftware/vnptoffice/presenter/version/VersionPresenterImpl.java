package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.version;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.version.VersionDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CheckVersionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckVersionResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ICheckVersionView;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class VersionPresenterImpl implements IVersionPresenter, ICallFinishedListener {
    public ICheckVersionView checkVersionView;
    public VersionDao versionDao;

    public VersionPresenterImpl(ICheckVersionView checkVersionView) {
        this.checkVersionView = checkVersionView;
        this.versionDao = new VersionDao();
    }

    @Override
    public void checkVersion(CheckVersionRequest checkVersionRequest) {
        if (checkVersionView != null) {
            checkVersionView.showProgress();
            versionDao.onCheckVersionDao(checkVersionRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        checkVersionView.hideProgress();
        CheckVersionResponse checkVersionResponse = (CheckVersionResponse) object;
        //checkVersionView.hideProgress();
        if (checkVersionResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            checkVersionView.onSuccess();
        } else {
            checkVersionView.onError(new APIError(0, checkVersionResponse.getResponeAPI().getMessage()));
        }
    }

    @Override
    public void onCallError(Object object) {
        checkVersionView.hideProgress();
        checkVersionView.onError((APIError) object);
        //checkVersionView.hideProgress();
    }

}
