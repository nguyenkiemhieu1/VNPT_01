package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.exceptionError;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ExceptionResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IExceptionErrorService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class ExceptionErrorDao extends BaseDao implements IExceptionErrorDao {
    private IExceptionErrorService exceptionErrorService;

    @Override
    public void onSendExceptionErrorDao(ExceptionRequest exceptionRequest, ICallFinishedListener iCallFinishedListener) {
        exceptionErrorService = BaseService.createServiceException(IExceptionErrorService.class);
        Call<ExceptionResponse> call = exceptionErrorService.exceptionError(BaseService.createAuthenHeadersException(),exceptionRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
