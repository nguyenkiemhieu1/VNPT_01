package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.syncevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ContactInfo;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IContactSync {
    void onSuccessSync(List<ContactInfo> contactInfos);
    void onErrorSync(APIError apiError);
    void showProgressSync();
    void hideProgressSync();
}