package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.changedocument;

import okhttp3.MultipartBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SearchPersonRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocRequest;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */

public interface IChangeDocumentPresenter {
    void getTypeChangeDocument(TypeChangeDocRequest typeChangeDocumentRequest);
    void getTypeChangeDocumentViewFiles(TypeChangeDocRequest typeChangeDocumentRequest);
    void getPersonProcess(ListProcessRequest listProcessRequest);
    void getPersonOrUnitExpected(int docId);//lấy danh sách cá nhân/đơn vị dự kiến
    void getPersonSend(SearchPersonRequest searchPersonRequest);
    void getPersonNotify();
    void getJobPossitions();
    void getUnits(int type);
    void changeSend(ChangeDocumentSendRequest changeDocumentSendRequest);
    void changeReceive(ChangeDocumentReceiveRequest changeDocumentReceiveRequest);
    void changeProcess(ChangeProcessRequest changeProcessRequest);
    void changeNotify(ChangeNotifyRequest changeNotifyRequest);
    void changeDirect(ChangeDocumentDirectRequest changeDocumentDirectRequest);
    void getLienThongXL();
    void getLienThongBH(String docId);
    void changeDocNotify(ChangeDocumentNotifyRequest changeDocumentNotifyRequest);
    void getPersonGroupCN();
    void getPersonGroupDV();
    void getPersonSendProcess(SearchPersonRequest searchPersonRequest);
    void getListFormContent();
    void sendUploadFile(MultipartBody.Part[] files);
}
