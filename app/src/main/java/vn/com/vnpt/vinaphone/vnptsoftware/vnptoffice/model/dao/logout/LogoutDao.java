package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.logout;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogoutRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.ILogoutService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */

public class LogoutDao extends BaseDao implements ILogoutDao {
    private ILogoutService logoutService;

    @Override
    public void onSendLogoutDao(ICallFinishedListener iCallFinishedListener) {
        logoutService = BaseService.createService(ILogoutService.class);
        Call<LogoutRespone> call = logoutService.logout();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
