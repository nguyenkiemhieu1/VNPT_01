package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentwaiting;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ButtonDocumentViewFilesRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.FinishDocumentSameRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListButtonSendSameRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SignDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SigningDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FinishDocumentSameResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetLinkEditFilesResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ListButtonSendSameResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeChangeDocumentViewFilesRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckCommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckFinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckMarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SigningOtpRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IDocumentWaitingService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.service.syncdata.HandleSyncService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 9/5/2017.
 */

public class DocumentWaitingDao extends BaseDao implements IDocumentWaitingDao {
    private IDocumentWaitingService documentWaitingService;

    @Override
    public void onCountDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetCount handleGetCount) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CountDocumentWaitingRespone> call = documentWaitingService.getCount(documentWaitingRequest);
        call(call, handleGetCount);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onRecordsDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetRecords handleGetRecords) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<DocumentWaitingRespone> call = documentWaitingService.getAll(documentWaitingRequest);
        call(call, handleGetRecords);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void ongetListButtonSendSame(ListButtonSendSameRequest listButtonSendSameRequest, HandleSyncService.HandleGetListButtonSendSame handleGetListButtonSendSame) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<ListButtonSendSameResponse> call = documentWaitingService.getListButtonSendSame(listButtonSendSameRequest);
        call(call, handleGetListButtonSendSame);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onFinishDocumentSame(FinishDocumentSameRequest finishDocumentSameRequest, HandleSyncService.HandleFinishDocumentSame handleFinishDocumentSame) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<FinishDocumentSameResponse> call = documentWaitingService.finishDocumentSame(finishDocumentSameRequest);
        call(call, handleFinishDocumentSame);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<ResponseBody> call = documentWaitingService.getBitmapDiagram(insId, defId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<DetailDocumentWaitingRespone> call = documentWaitingService.getDetail(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetLogs(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<LogRespone> call = documentWaitingService.getLogs(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<AttachFileRespone> call = documentWaitingService.getAttachFiles(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<RelatedDocumentRespone> call = documentWaitingService.getRelatedDocs(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<RelatedFileRespone> call = documentWaitingService.getRelatedFiles(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCheckMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CheckMarkDocumentRespone> call = documentWaitingService.checkMark(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<MarkDocumentRespone> call = documentWaitingService.mark(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCheckCommentDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CheckCommentDocumentRespone> call = documentWaitingService.checkComment(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCommentDocument(CommentDocumentRequest commentDocumentRequest, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CommentDocumentRespone> call = documentWaitingService.comment(commentDocumentRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onSigningDocument(SigningDocumentRequest signingDocumentRequest, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createServiceSigning(IDocumentWaitingService.class);
        Call<SigningRespone> call = documentWaitingService.signDoc(signingDocumentRequest);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onFinish(int id, String comment, ICallFinishedListener iCallFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<FinishDocumentRespone> call = documentWaitingService.finish(new CommentRequest(id, comment));
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onCheckFinishDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CheckFinishDocumentRespone> call = documentWaitingService.checkFinish(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onKyVanBanCA(SignDocumentRequest signDocumentRequest, ICallFinishedListener iCallFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<SigningRespone> call = documentWaitingService.signCAdocument(signDocumentRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onKyVanBan(SignDocumentRequest signDocumentRequest, ICallFinishedListener iCallFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<SigningRespone> call = documentWaitingService.signdocument(signDocumentRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onKyVanBanPKI(SignDocumentRequest signDocumentRequest, ICallFinishedListener iCallFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<SigningRespone> call = documentWaitingService.signdocumentPKI(signDocumentRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void signServiceOtp(String otpSign, ICallFinishedListener iCallFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<SigningOtpRespone> call = documentWaitingService.signServiceOtp(otpSign);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void resendServiceOtp(ICallFinishedListener iCallFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<SigningRespone> call = documentWaitingService.reSendServiceOtp();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetLinkEditFiles(int fileId, ICallFinishedListener iCallFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<GetLinkEditFilesResponse> call = documentWaitingService.GetLinkEditFile(fileId);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));

    }


}
