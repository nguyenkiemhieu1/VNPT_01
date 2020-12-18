package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IChiDaoView {
    void onSuccess(List<Object> object);
    void onSuccess(Object object);
    void onSuccess();
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
    void onSuccessDonViNhan(Object object);
    void onSuccessDanhSachDonViNhan(Object object);
}