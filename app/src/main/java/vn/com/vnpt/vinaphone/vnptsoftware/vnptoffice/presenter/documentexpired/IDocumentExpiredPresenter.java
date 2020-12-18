package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentexpired;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentExpiredRequest;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public interface IDocumentExpiredPresenter {
    void getCount(DocumentExpiredRequest documentExpiredRequest);
    void getRecords(DocumentExpiredRequest documentExpiredRequest);
    void getBitmapDiagram(String insId, String defId);
    void getDetail(int id);
    void getLogs(int id);
    void getAttachFiles(int id);
    void getRelatedDocs(int id);
    void getRelatedFiles(int id);
    void mark(int id);
}
