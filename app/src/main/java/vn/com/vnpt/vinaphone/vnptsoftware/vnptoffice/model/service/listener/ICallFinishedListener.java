package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface ICallFinishedListener {
    void onCallSuccess(Object object);
    void onCallError(Object object);
}
