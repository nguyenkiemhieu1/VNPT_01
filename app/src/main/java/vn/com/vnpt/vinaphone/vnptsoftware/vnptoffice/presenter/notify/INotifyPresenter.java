package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.notify;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReadedNotifyRequest;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface INotifyPresenter {
    void getNotifys(ListNotifyRequest listNotifyRequest);
    void readedNotifys(ReadedNotifyRequest readedNotifyRequest);
    void getDetailNotify(int notifyId);
    void getCountDocument();
}
