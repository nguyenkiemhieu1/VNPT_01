package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface ISelectPersonView {
    void onSuccess(Object listPerson);
    void onError(APIError apiError);
    void onSuccessLienThong(List<PersonSendNotifyInfo> lienThongInfos);
    void onSuccessPersonOrUnit(Object object);
    void showProgress();
    void hideProgress();
}