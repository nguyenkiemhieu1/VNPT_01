package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentprocessed;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentProcessedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckDocProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckMarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckRecoverDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RecoverDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IDocumentProcessedService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public class DocumentProcessedDao extends BaseDao implements IDocumentProcessedDao {
    private IDocumentProcessedService documentProcessedService;

    @Override
    public void onCountDocumentProcessedDao(DocumentProcessedRequest documentProcessedRequest,
                                            ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<CountDocumentProcessedRespone> call = documentProcessedService.getCount(documentProcessedRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onRecordsDocumentProcessedDao(DocumentProcessedRequest documentProcessedRequest,
                                              ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<DocumentProcessedRespone> call = documentProcessedService.getRecords(documentProcessedRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCheckRecoverDocumentDao(String type, int id, ICallFinishedListener iCallFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<CheckRecoverDocumentRespone> call = null;
        switch (type) {
            case Constants.DOCUMENT_RECEIVE:
                call = documentProcessedService.checkReceive(id);
                EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
                break;
            case Constants.DOCUMENT_SEND:
                call = documentProcessedService.checkSend(id);
                EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
                break;
        }
        if (call != null) {
            call(call, iCallFinishedListener);
        }
    }

    @Override
    public void onRecoverDocumentDao(String type, int id, ICallFinishedListener iCallFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<RecoverDocumentRespone> call = null;
        switch (type) {
            case Constants.DOCUMENT_RECEIVE:
                call = documentProcessedService.recoverDocumentReceive(id);
                EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
                break;
            case Constants.DOCUMENT_SEND:
                call = documentProcessedService.recoverDocumentSend(id);
                EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
                break;
        }
        if (call != null) {
            call(call, iCallFinishedListener);
        }
    }

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<DetailDocumentProcessedRespone> call = documentProcessedService.getDetail(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetLogs(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<LogRespone> call = documentProcessedService.getLogs(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<AttachFileRespone> call = documentProcessedService.getAttachFiles(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<RelatedDocumentRespone> call = documentProcessedService.getRelatedDocs(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<RelatedFileRespone> call = documentProcessedService.getRelatedFiles(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCheckMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<CheckMarkDocumentRespone> call = documentProcessedService.checkMark(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<MarkDocumentRespone> call = documentProcessedService.mark(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<ResponseBody> call = documentProcessedService.getBitmapDiagram(insId, defId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCheckChangeDocDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<CheckDocProcessedRespone> call = documentProcessedService.checkChangeDoc(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

}
