package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentwaiting;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ButtonDocumentViewFilesRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.FinishDocumentSameRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListButtonSendSameRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SignDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SigningDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.service.syncdata.HandleSyncService;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public interface IDocumentWaitingDao {
    void onCountDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetCount handleGetCount);
    void onRecordsDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetRecords handleGetRecords);
    void ongetListButtonSendSame(ListButtonSendSameRequest listButtonSendSameRequest,HandleSyncService.HandleGetListButtonSendSame handleGetListButtonSendSame );
    void onFinishDocumentSame(FinishDocumentSameRequest finishDocumentSameRequest, HandleSyncService.HandleFinishDocumentSame handleFinishDocumentSame);
    void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener);
    void onGetDetail(int docId, ICallFinishedListener callFinishedListener);
    void onGetLogs(int docId, ICallFinishedListener callFinishedListener);
    void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener);
    void onCheckMarkDocument(int docId, ICallFinishedListener callFinishedListener);
    void onMarkDocument(int docId, ICallFinishedListener callFinishedListener);
    void onCheckCommentDocument(int docId, ICallFinishedListener callFinishedListener);
    void onCommentDocument(CommentDocumentRequest commentDocumentRequest, ICallFinishedListener callFinishedListener);
    void onSigningDocument(SigningDocumentRequest signingDocumentRequest, ICallFinishedListener callFinishedListener);
    void onFinish(int id,String comment ,ICallFinishedListener iCallFinishedListener);
    void onCheckFinishDocument(int docId, ICallFinishedListener callFinishedListener);
    void onKyVanBanCA(SignDocumentRequest signDocumentRequest, ICallFinishedListener iCallFinishedListener);
    void onKyVanBan(SignDocumentRequest signDocumentRequest, ICallFinishedListener iCallFinishedListener);
    void onKyVanBanPKI(SignDocumentRequest signDocumentRequest, ICallFinishedListener iCallFinishedListener);
    void signServiceOtp(String otpSign, ICallFinishedListener iCallFinishedListener);
    void resendServiceOtp(ICallFinishedListener iCallFinishedListener);
    void onGetLinkEditFiles(int fileId,ICallFinishedListener iCallFinishedListener);
  }
