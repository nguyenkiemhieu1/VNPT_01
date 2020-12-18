package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.savedocument;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.savedocument.SaveDocumentDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSavedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FinishDocumentSaveRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSaveDocumentResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ISaveDocumentView;

public class SaveDocumentPresenterImpl implements ISaveDocumentPresenter, ICallFinishedListener {
    SaveDocumentDao saveDocumentDao;
    ISaveDocumentView iSaveDocumentView;

    public SaveDocumentPresenterImpl(ISaveDocumentView iSaveDocumentView) {

        this.iSaveDocumentView = iSaveDocumentView;
        saveDocumentDao = new SaveDocumentDao();
    }

    @Override
    public void onGetListDocumentSaved() {
        if (iSaveDocumentView != null) {
            iSaveDocumentView.showProgress();
            saveDocumentDao.onGetSaveDocumentDao(this);
        }
    }

    @Override
    public void onFinishDocumentSaved(DocumentSavedRequest documentSavedRequest) {
        if (iSaveDocumentView != null) {
            iSaveDocumentView.showProgress();
            saveDocumentDao.onPostSaveDocumentDao(documentSavedRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (iSaveDocumentView!=null) {
            if (object instanceof GetListSaveDocumentResponse) {
                GetListSaveDocumentResponse list = (GetListSaveDocumentResponse) object;
                if (list != null) {
                    iSaveDocumentView.onSuccess(list);
                } else {
                    iSaveDocumentView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
                }
            }
            if (object instanceof FinishDocumentSaveRespone) {
                FinishDocumentSaveRespone finishDocumentSaveRespone=(FinishDocumentSaveRespone) object;
                iSaveDocumentView.hideProgress();
                iSaveDocumentView.onSuccessPost();
            }
        }

    }

    @Override
    public void onCallError(Object object) {
        if(object instanceof APIError) {
            iSaveDocumentView.onError((APIError) object);
        }

    }
}
