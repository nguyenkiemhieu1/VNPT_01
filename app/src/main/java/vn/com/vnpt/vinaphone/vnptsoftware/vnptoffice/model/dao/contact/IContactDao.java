package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.contact;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IContactDao {
    void onSendGetContactsDao(ICallFinishedListener iCallFinishedListener);
}
