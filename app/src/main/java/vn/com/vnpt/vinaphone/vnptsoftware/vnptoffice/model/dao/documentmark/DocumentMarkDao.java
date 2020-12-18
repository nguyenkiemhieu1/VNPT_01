package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentmark;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentMarkRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IDocumentMarkService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public class DocumentMarkDao extends BaseDao implements IDocumentMarkDao {
    private IDocumentMarkService documentMarkService;

    @Override
    public void onCountDocumentMarkDao(DocumentMarkRequest documentMarkRequest, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<CountDocumentMarkRespone> call = documentMarkService.getCount(documentMarkRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onRecordsDocumentMarkDao(DocumentMarkRequest documentMarkRequest, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<DocumentMarkRespone> call = documentMarkService.getRecords(documentMarkRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<DetailDocumentMarkRespone> call = documentMarkService.getDetail(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetLogs(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<LogRespone> call = documentMarkService.getLogs(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<AttachFileRespone> call = documentMarkService.getAttachFiles(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<RelatedDocumentRespone> call = documentMarkService.getRelatedDocs(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<RelatedFileRespone> call = documentMarkService.getRelatedFiles(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<ResponseBody> call = documentMarkService.getBitmapDiagram(insId, defId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<MarkDocumentRespone> call = documentMarkService.mark(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

}
