package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IHistoryDocumentView {
    void onSuccess(List<UnitLogInfo> unitLogInfoList);
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}