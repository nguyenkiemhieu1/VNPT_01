package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentnotification;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentNotificationRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public interface IDocumentNotificationDao {
    void onCountDocumentNotificationDao(DocumentNotificationRequest documentNotificationRequest, ICallFinishedListener iCallFinishedListener);
    void onRecordsDocumentNotificationDao(DocumentNotificationRequest documentNotificationRequest, ICallFinishedListener iCallFinishedListener);
    void onGetDetail(int docId, ICallFinishedListener callFinishedListener);
    void onGetLogs(int docId, ICallFinishedListener callFinishedListener);
    void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener);
    void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener);
    void onMarkDocument(int docId, ICallFinishedListener callFinishedListener);
    void onFinish(int id,String comment ,ICallFinishedListener iCallFinishedListener);
    void onCheckFinishDocument(int docId, ICallFinishedListener callFinishedListener);
}
