package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonGroupChangeDocInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IChangeDocumentView {
    void onSuccessChangeDoc();
    void onSuccessFormList(List<String> listForm);
    void onSuccessGroupPerson(List<PersonGroupChangeDocInfo> personGroupChangeDocInfos);
    void onSuccessUpLoad(List<Object> object);
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}