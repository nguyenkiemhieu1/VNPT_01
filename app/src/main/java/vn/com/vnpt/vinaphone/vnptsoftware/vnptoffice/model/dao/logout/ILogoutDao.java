package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.logout;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */

public interface ILogoutDao {
    void onSendLogoutDao(ICallFinishedListener iCallFinishedListener);
}
