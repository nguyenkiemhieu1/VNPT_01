package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.notify;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface INotifyDao {
    void onSendGetNotifysDao(ListNotifyRequest listNotifyRequest,ICallFinishedListener iCallFinishedListener);
    void onReadedNotifysDao(ICallFinishedListener iCallFinishedListener, ReadedNotifyRequest readedNotifyRequest);
    void onGetDetailNotifyDao(ICallFinishedListener iCallFinishedListener, int notifyId);
    void onGetCountDocumentDao(ICallFinishedListener iCallFinishedListener);
}
