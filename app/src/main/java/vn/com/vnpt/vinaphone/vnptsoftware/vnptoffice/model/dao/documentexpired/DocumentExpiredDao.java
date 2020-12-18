package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentexpired;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentExpiredRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IDocumentExpiredService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public class DocumentExpiredDao extends BaseDao implements IDocumentExpiredDao {
    private IDocumentExpiredService documentExpiredService;

    @Override
    public void onCountDocumentExpiredDao(DocumentExpiredRequest documentExpiredRequest, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<CountDocumentExpiredRespone> call = documentExpiredService.getCount(documentExpiredRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onRecordsDocumentExpiredDao(DocumentExpiredRequest documentExpiredRequest, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<DocumentExpiredRespone> call = documentExpiredService.getRecords(documentExpiredRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<ResponseBody> call = documentExpiredService.getBitmapDiagram(insId, defId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<DetailDocumentExpiredRespone> call = documentExpiredService.getDetail(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetLogs(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<LogRespone> call = documentExpiredService.getLogs(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<AttachFileRespone> call = documentExpiredService.getAttachFiles(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<RelatedDocumentRespone> call = documentExpiredService.getRelatedDocs(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<RelatedFileRespone> call = documentExpiredService.getRelatedFiles(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<MarkDocumentRespone> call = documentExpiredService.mark(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

}
