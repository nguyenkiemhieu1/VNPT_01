package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import android.graphics.Bitmap;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IDocumentDiagramView {
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
    void onSuccessDiagram(Bitmap bitmap);
}