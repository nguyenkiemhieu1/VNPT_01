package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListBossSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateStatusJobResponse;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IAddPersonSubTaskView {

    void onSuccessGetListUnitData(List<PersonSendNotifyInfo> listData);

    void onSuccessGetListPersonData(List<PersonSendNotifyInfo> listData);

    void onSuccessAddPersonData(UpdateStatusJobResponse statusJobResponse);

    void onErrorAddPersonData(APIError apiError);

    void showProgress();

    void hideProgress();
}