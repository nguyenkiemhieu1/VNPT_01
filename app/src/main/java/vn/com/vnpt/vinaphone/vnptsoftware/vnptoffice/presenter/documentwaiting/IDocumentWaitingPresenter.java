package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.FinishDocumentSameRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListButtonSendSameRequest;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public interface IDocumentWaitingPresenter {
    void getRecords(DocumentWaitingRequest documentWaitingRequest);
    void getBitmapDiagram(String insId, String defId);
    void getDetail(int id);
    void getLogs(int id);
    void getAttachFiles(int id);
    void getRelatedDocs(int id);
    void getRelatedFiles(int id);
    void getListButtonSendSame(ListButtonSendSameRequest listButtonSendSameRequest);
    void finishDocumentAll(FinishDocumentSameRequest finishDocumentSameRequest);
    void comment(CommentDocumentRequest commentDocumentRequest);
    void mark(int id);
    void finish(int id,String comment);
    void checkFinish(int id);
}
