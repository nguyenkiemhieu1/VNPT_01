package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IDetailDocumentProcessedView {
    void onSuccess(Object object);
    void onError(APIError apiError);
    void onSuccessCheckRecover(boolean recover);
    void onSuccessRecover(boolean recover);
    void showProgress();
    void hideProgress();
    void onMark();
    void onChange();
}