package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.version;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CheckVersionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IVersionDao {
    void onCheckVersionDao(CheckVersionRequest checkVersionRequest, ICallFinishedListener iCallFinishedListener);
}
