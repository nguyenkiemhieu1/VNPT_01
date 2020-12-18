package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.savedocument;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSavedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FinishDocumentSaveRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSaveDocumentResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IDocumentSavedService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IDocumentWaitingService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

public class SaveDocumentDao extends BaseDao implements ISaveDocumentDao {
    IDocumentSavedService iDocumentSavedService;
    @Override
    public void onGetSaveDocumentDao(ICallFinishedListener iCallFinishedListener) {
        iDocumentSavedService = BaseService.createService(IDocumentSavedService.class);
        Call<GetListSaveDocumentResponse> call = iDocumentSavedService.getListDocumentSaved();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onPostSaveDocumentDao(DocumentSavedRequest documentSavedRequest, ICallFinishedListener iCallFinishedListener) {
        iDocumentSavedService = BaseService.createService(IDocumentSavedService.class);
        Call<FinishDocumentSaveRespone> call = iDocumentSavedService.postFinishDocumentSaved(documentSavedRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
