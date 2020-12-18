package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ListButtonSendSameInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IDocumentWaitingView {
    void onSuccessRecords(List<DocumentWaitingInfo> documentWaitingInfoList);
    void onSuccessListButtonSendSame(List<ListButtonSendSameInfo> listButtonSendSameInfos);
    void onSuccessFinishDocumentSendSame();
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}