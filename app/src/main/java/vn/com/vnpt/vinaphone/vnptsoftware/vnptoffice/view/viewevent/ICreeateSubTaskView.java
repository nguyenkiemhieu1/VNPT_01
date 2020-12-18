package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListBossSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListPersonSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListUnitSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateStatusJobResponse;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface ICreeateSubTaskView {
    void onSuccessGetListBossData(List<GetListBossSubTaskResponse.Data> listData);

    void onSuccessGetListUnitData(List<PersonSendNotifyInfo> listData);

    void onSuccessGetListPersonData(List<PersonSendNotifyInfo> listData);
    void onSuccessGetListFileData(List<AttachFileInfo> listData);

    void onSuccessCreateSubTask(UpdateStatusJobResponse response);
    void onSuccessUpLoad(List<String> object);

    void onError(APIError apiError);

    void showProgress();

    void hideProgress();
}