package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailNotifyInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IReadedNotifyView {
    void onSuccess(boolean isRead);
    void onError(APIError apiError);
    void onCheckStoreSuccess(DetailNotifyInfo data);
    void showProgress();
    void hideProgress();
}