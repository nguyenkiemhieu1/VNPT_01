package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.userinfo;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.userinfo.UserInfoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UserInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UserInfoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IUserAvatarView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IUserInfoView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IUserSwitchView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 8/24/2017.
 */

public class UserInfoPresenterImpl implements IUserInfoPresenter, ICallFinishedListener {
    public IUserInfoView userInfoView;
    public IUserAvatarView userAvatarView;
    public IUserSwitchView userSwitchView;
    public UserInfoDao userInfoDao;
    private int typeApi = -1;

    public UserInfoPresenterImpl(IUserInfoView userInfoView) {
        this.userInfoView = userInfoView;
        this.userInfoDao = new UserInfoDao();
    }

    public UserInfoPresenterImpl(IUserAvatarView userAvatarView) {
        typeApi = -1;
        this.userAvatarView = userAvatarView;
        this.userInfoDao = new UserInfoDao();
    }

    public UserInfoPresenterImpl(IUserAvatarView userAvatarView, IUserSwitchView userSwitchView) {
        typeApi = -1;
        this.userSwitchView = userSwitchView;
        this.userAvatarView = userAvatarView;
        this.userInfoDao = new UserInfoDao();
    }

    public UserInfoPresenterImpl(IUserInfoView userInfoView, IUserAvatarView userAvatarView) {
        typeApi = -1;
        this.userAvatarView = userAvatarView;
        this.userInfoView = userInfoView;
        this.userInfoDao = new UserInfoDao();
    }

    @Override
    public void getUserInfo() {
        typeApi = 1;
        if (userInfoView != null) {
            userInfoView.showProgress();
            userInfoDao.onGetUserInfoDao(this);
        }
    }

    @Override
    public void getUserInfo(String id) {
        typeApi = 1;
        if (userInfoView != null) {
            userInfoView.showProgress();
            userInfoDao.onGetUserInfoDao(this, id);
        }
    }

    @Override
    public void getAvatar(String userId) {
        typeApi = 2;
        if (userAvatarView != null) {
            userInfoDao.onGetAvatarDao(this, userId);
        }
    }
    @Override
    public void getListSwitchUser(String userId) {
        typeApi = 3;
        if (userSwitchView != null) {
            userSwitchView.showProgress();
            userInfoDao.onGetListSwitchUserDao(this, userId);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof UserInfoRespone) {
            UserInfoRespone userInfoRespone = (UserInfoRespone) object;
            if (userInfoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                userInfoView.onSuccessGetUserInfo(ConvertUtils.fromJSON(userInfoRespone.getData(), UserInfo.class));
            } else {
                userInfoView.onErrorGetUserInfo(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
            userInfoView.hideProgress();
        }
        if (object instanceof LoginRespone) {
            userSwitchView.hideProgress();
            LoginRespone loginRespone = (LoginRespone) object;
            if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
                userSwitchView.onSuccessSwitchUser(loginInfo);
            } else {
                userSwitchView.onErrorSwitchUser(new APIError(0, resources.getString(R.string.str_ERROR_CHANGE_ACCOUNT_DIALOG)));
            }
        }
        if (object instanceof ResponseBody) {
            ResponseBody responseBody = (ResponseBody) object;
            if (responseBody != null) {
                try {
                    userAvatarView.onSuccessAvatar(responseBody.bytes());
                } catch (Exception ex) {
                    userAvatarView.onErrorAvatar(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
                }
            } else {
                userAvatarView.onErrorAvatar(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        switch (typeApi) {
            case 1:
                if (userInfoView != null) {
                    userInfoView.hideProgress();
                    userInfoView.onErrorGetUserInfo((APIError) object);
                }
                break;
            case 2:
                if (userAvatarView != null) {
                    userAvatarView.onErrorAvatar(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
                }
                break;
            case 3:
                if (userSwitchView != null) {
                    userSwitchView.hideProgress();
                    userSwitchView.onErrorSwitchUser(new APIError(0, resources.getString(R.string.str_ERROR_CHANGE_ACCOUNT_DIALOG)));
                }
                break;
            default:
                if (userInfoView != null) {
                    userInfoView.hideProgress();
                    userInfoView.onErrorGetUserInfo((APIError) object);
                }
                break;
        }
    }

}
