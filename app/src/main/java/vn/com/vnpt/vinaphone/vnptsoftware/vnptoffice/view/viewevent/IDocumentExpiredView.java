package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentExpired;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentExpiredInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IDocumentExpiredView {
    void onSuccessRecords(List<DocumentExpiredInfo> documentExpiredInfos);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentExpired countDocumentExpired);
    void showProgress();
    void hideProgress();
}