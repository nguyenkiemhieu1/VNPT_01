package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobAssignRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListStatusComboxRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IWorkManageView {
    void onSuccessGetListData(List<GetListJobAssignRespone.Data> listData);
    void onSuccessGetListStatus(List<GetListStatusComboxRespone.Data> listData);
    void onErrorGetListData(APIError apiError);
    void showProgress();
    void hideProgress();
}