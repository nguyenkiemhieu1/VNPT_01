package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.login;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.LoginRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */

public interface ILoginDao {
    void onSendLoginDao(LoginRequest loginRequest, ICallFinishedListener iCallFinishedListener);
}
