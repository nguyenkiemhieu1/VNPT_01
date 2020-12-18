package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentsearch;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentAdvanceSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentAdvanceSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentAdvanceSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FieldDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PriorityDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IDocumentSearchService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 9/5/2017.
 */

public class DocumentSearchDao extends BaseDao implements IDocumentSearchDao {
    private IDocumentSearchService documentSearchService;

    @Override
    public void onGetTypes(ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<TypeDocumentRespone> call = documentSearchService.getTypes();
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetFields(ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<FieldDocumentRespone> call = documentSearchService.getFields();
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetPrioritys(ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<PriorityDocumentRespone> call = documentSearchService.getPrioritys();
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCountDocumentSearchDao(DocumentSearchRequest documentSearchRequest, ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<CountDocumentSearchRespone> call = documentSearchService.getCount(documentSearchRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onRecordsDocumentSearchDao(DocumentSearchRequest documentSearchRequest, ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<DocumentSearchRespone> call = documentSearchService.getRecords(documentSearchRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCountDocumentAdvanceSearchDao(DocumentAdvanceSearchRequest documentAdvanceSearchRequest, ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<CountDocumentAdvanceSearchRespone> call = documentSearchService.getCountAdvance(documentAdvanceSearchRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onRecordsDocumentAdvanceSearchDao(DocumentAdvanceSearchRequest documentAdvanceSearchRequest, ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<DocumentAdvanceSearchRespone> call = documentSearchService.getRecordsAdvance(documentAdvanceSearchRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

}
