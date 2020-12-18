package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentnotification;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentNotificationRequest;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public interface IDocumentNotificationPresenter {
    void getCount(DocumentNotificationRequest documentNotificationRequest);
    void getRecords(DocumentNotificationRequest documentNotificationRequest);
    void getDetail(int id);
    void getLogs(int id);
    void getAttachFiles(int id);
    void getRelatedDocs(int id);
    void getRelatedFiles(int id);
    void getBitmapDiagram(String insId, String defId);
    void checkFinish(int id);
    void finish(int id,String comment);
    void mark(int id);
}
