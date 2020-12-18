package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.notify;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailNotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.NotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReadedNotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.INotifyService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class NotifyDao extends BaseDao implements INotifyDao {
    private INotifyService notifyService;

    @Override
    public void onSendGetNotifysDao(ListNotifyRequest listNotifyRequest, ICallFinishedListener iCallFinishedListener) {
        notifyService = BaseService.createService(INotifyService.class);
        Call<NotifyRespone> call = notifyService.getNotifys(listNotifyRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onReadedNotifysDao(ICallFinishedListener iCallFinishedListener, ReadedNotifyRequest readedNotifyRequest) {
        notifyService = BaseService.createService(INotifyService.class);
        Call<ReadedNotifyRespone> call = notifyService.readedNotifys(readedNotifyRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDetailNotifyDao(ICallFinishedListener iCallFinishedListener, int notifyId) {
        notifyService = BaseService.createService(INotifyService.class);
        Call<DetailNotifyRespone> call = notifyService.getDetailNotify(notifyId);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetCountDocumentDao(ICallFinishedListener iCallFinishedListener) {
        notifyService = BaseService.createService(INotifyService.class);
        Call<CountDocumentRespone> call = notifyService.getCountDocument();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
