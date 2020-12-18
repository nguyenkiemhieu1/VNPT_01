package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.changepassword;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangePasswordRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChangePasswordRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IChangePasswordService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */

public class ChangePasswordDao extends BaseDao implements IChangePasswordDao {
    private IChangePasswordService changePasswordService;

    @Override
    public void onSendChangePasswordDao(ChangePasswordRequest changePasswordRequest, ICallFinishedListener iCallFinishedListener) {
        changePasswordService = BaseService.createService(IChangePasswordService.class);
        Call<ChangePasswordRespone> call = changePasswordService.changePassword(changePasswordRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
