package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.userinfo;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/24/2017.
 */

public interface IUserInfoDao {
    void onGetUserInfoDao(ICallFinishedListener iCallFinishedListener);
    void onGetUserInfoDao(ICallFinishedListener iCallFinishedListener, String id);
    void onGetAvatarDao(ICallFinishedListener iCallFinishedListener, String id);
    void onGetListSwitchUserDao(ICallFinishedListener iCallFinishedListener, String id);
}
