package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobHandLingResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListPersonUnitDetailResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSubTaskResponse;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IDetailFragmentView {
    void onSuccessSubListData(List<GetListSubTaskResponse.Data> listData);
    void onSuccessUnitPersonListData(List<GetListPersonUnitDetailResponse.Data> listData);
    void onErrorListData(APIError apiError);
    void showProgress();
    void hideProgress();
}