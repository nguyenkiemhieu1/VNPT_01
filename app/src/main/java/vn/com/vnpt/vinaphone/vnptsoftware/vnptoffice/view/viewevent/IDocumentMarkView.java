package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentMark;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentMarkInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IDocumentMarkView {
    void onSuccessRecords(List<DocumentMarkInfo> documentMarkInfoList);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentMark countDocumentMark);
    void showProgress();
    void hideProgress();
}