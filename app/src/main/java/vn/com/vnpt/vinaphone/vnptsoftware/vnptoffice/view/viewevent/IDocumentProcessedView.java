package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentProcessed;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentProcessedInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IDocumentProcessedView {
    void onSuccessRecords(List<DocumentProcessedInfo> documentProcessedInfoList);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentProcessed countDocumentProcessed);
    void showProgress();
    void hideProgress();
}