package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IScheduleDetailView {
    void onSuccess(Object schedule);
    void onError(APIError apiError);
}