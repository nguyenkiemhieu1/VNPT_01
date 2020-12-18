package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;

public interface ISaveDocumentView {
    void onSuccess(Object listDocumentSaveD);
    void onSuccessPost();

    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}
