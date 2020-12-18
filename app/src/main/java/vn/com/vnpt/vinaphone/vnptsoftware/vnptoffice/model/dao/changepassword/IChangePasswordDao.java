package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.changepassword;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangePasswordRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */

public interface IChangePasswordDao {
    void onSendChangePasswordDao(ChangePasswordRequest changePasswordRequest, ICallFinishedListener iCallFinishedListener);
}
