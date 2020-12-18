package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.exceptionError;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IExceptionErrorDao {
    void onSendExceptionErrorDao(ExceptionRequest exceptionRequest, ICallFinishedListener iCallFinishedListener);
}
