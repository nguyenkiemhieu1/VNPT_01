package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.changedocument;

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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */

public interface IChangeDocumentDao {
    void onSendGetTypeChangeDocumentDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener);

    void onSendGetTypeChangeDocumentViewFilesDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener);

    void onGetPersonProcessDao(ListProcessRequest listProcessRequest, ICallFinishedListener iCallFinishedListener);

    void onGetPersonOrUnitExpectedDao(int docId, ICallFinishedListener iCallFinishedListener);

    void onGetPersonSendDao(SearchPersonRequest searchPersonRequest, ICallFinishedListener iCallFinishedListener);

    void onGetPersonNotifyDao(ICallFinishedListener iCallFinishedListener);

    void onChangeSendDao(ChangeDocumentSendRequest changeDocumentSendRequest, ICallFinishedListener iCallFinishedListener);

    void onChangeReceiveDao(ChangeDocumentReceiveRequest changeDocumentReceiveRequest, ICallFinishedListener iCallFinishedListener);

    void onChangeProcessDao(ChangeProcessRequest changeProcessRequest, ICallFinishedListener iCallFinishedListener);

    void onChangeNotifyDao(ChangeNotifyRequest changeNotifyRequest, ICallFinishedListener iCallFinishedListener);

    void onChangeDirectDao(ChangeDocumentDirectRequest changeDocumentDirectRequest, ICallFinishedListener iCallFinishedListener);

    void onGetJobPossitionDao(ICallFinishedListener iCallFinishedListener);

    void onGetUnitDao(int type, ICallFinishedListener iCallFinishedListener);

    void onGetLienThongBHDao(String id, ICallFinishedListener iCallFinishedListener);

    void onGetLienTHongXLDao(ICallFinishedListener iCallFinishedListener);

    void onChangeDocNotifyDao(ChangeDocumentNotifyRequest changeDocumentNotifyRequest, ICallFinishedListener iCallFinishedListener);

    void onGetGroupPersonCNDao(ICallFinishedListener iCallFinishedListener);

    void onGetGroupPersonDVDao(ICallFinishedListener iCallFinishedListener);

    void onGetPersonSendProcessDao(SearchPersonRequest searchPersonRequest, ICallFinishedListener iCallFinishedListener);

    void onGetListFormProcessDao(ICallFinishedListener iCallFinishedListener);

    void onGetCountDocumentDao(ICallFinishedListener iCallFinishedListener);

    void onSendUpLoadFileDao(MultipartBody.Part[] files, ICallFinishedListener iCallFinishedListener);
}
