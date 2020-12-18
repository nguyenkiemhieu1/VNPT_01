package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentsearch;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentAdvanceSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public interface IDocumentSearchDao {
    void onGetTypes(ICallFinishedListener callFinishedListener);
    void onGetFields(ICallFinishedListener callFinishedListener);
    void onGetPrioritys(ICallFinishedListener callFinishedListener);
    void onCountDocumentSearchDao(DocumentSearchRequest documentSearchRequest, ICallFinishedListener iCallFinishedListener);
    void onRecordsDocumentSearchDao(DocumentSearchRequest documentSearchRequest, ICallFinishedListener iCallFinishedListener);
    void onCountDocumentAdvanceSearchDao(DocumentAdvanceSearchRequest documentAdvanceSearchRequest, ICallFinishedListener iCallFinishedListener);
    void onRecordsDocumentAdvanceSearchDao(DocumentAdvanceSearchRequest documentAdvanceSearchRequest, ICallFinishedListener iCallFinishedListener);
}
