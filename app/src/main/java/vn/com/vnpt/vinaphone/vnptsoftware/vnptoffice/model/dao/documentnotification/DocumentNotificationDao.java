package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentnotification;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentNotificationRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckFinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IDocumentNotificationService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IDocumentWaitingService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public class DocumentNotificationDao extends BaseDao implements IDocumentNotificationDao {
    private IDocumentNotificationService documentNotificationService;

    @Override
    public void onCountDocumentNotificationDao(DocumentNotificationRequest documentNotificationRequest, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<CountDocumentNotificationRespone> call = documentNotificationService.getCount(documentNotificationRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onRecordsDocumentNotificationDao(DocumentNotificationRequest documentNotificationRequest, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<DocumentNotificationRespone> call = documentNotificationService.getRecords(documentNotificationRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<DetailDocumentNotificationRespone> call = documentNotificationService.getDetail(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetLogs(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<LogRespone> call = documentNotificationService.getLogs(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<AttachFileRespone> call = documentNotificationService.getAttachFiles(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<RelatedDocumentRespone> call = documentNotificationService.getRelatedDocs(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<RelatedFileRespone> call = documentNotificationService.getRelatedFiles(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<ResponseBody> call = documentNotificationService.getBitmapDiagram(insId, defId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<MarkDocumentRespone> call = documentNotificationService.mark(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onFinish(int id, String comment, ICallFinishedListener iCallFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<FinishDocumentRespone> call = documentNotificationService.finish(new CommentRequest(id,comment));
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCheckFinishDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<CheckFinishDocumentRespone> call = documentNotificationService.checkFinish(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

}
