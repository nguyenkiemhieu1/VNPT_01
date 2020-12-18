package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentAdvanceSearch;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentAdvanceSearchInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IDocumentResultSearchView {
    void onSuccessRecords(List<DocumentAdvanceSearchInfo> documentAdvanceSearchInfos);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentAdvanceSearch countDocumentAdvanceSearch);
    void showProgress();
    void hideProgress();
}