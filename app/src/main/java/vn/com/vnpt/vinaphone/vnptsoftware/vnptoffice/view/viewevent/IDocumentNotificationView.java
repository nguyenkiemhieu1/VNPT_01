package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentNotification;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentNotificationInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IDocumentNotificationView {
    void onSuccessRecords(List<DocumentNotificationInfo> documentNotificationInfoList);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentNotification countDocumentNotification);
    void showProgress();
    void hideProgress();
}