package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentSearch;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentSearchInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IDocumentSearchView {
    void onSuccessRecords(List<DocumentSearchInfo> documentSearchInfos);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentSearch countDocumentSearch);
    void showProgress();
    void hideProgress();
}