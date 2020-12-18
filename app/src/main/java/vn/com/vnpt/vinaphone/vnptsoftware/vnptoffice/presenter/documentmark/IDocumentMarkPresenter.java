package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentmark;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentMarkRequest;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public interface IDocumentMarkPresenter {
    void getCount(DocumentMarkRequest documentMarkRequest);
    void getRecords(DocumentMarkRequest documentMarkRequest);
    void getDetail(int id);
    void getLogs(int id);
    void getAttachFiles(int id);
    void getRelatedDocs(int id);
    void getRelatedFiles(int id);
    void getBitmapDiagram(String insId, String defId);
}
