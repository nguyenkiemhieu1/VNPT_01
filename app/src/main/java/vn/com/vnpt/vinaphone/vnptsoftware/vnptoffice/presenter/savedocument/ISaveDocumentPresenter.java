package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.savedocument;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSavedRequest;

public interface ISaveDocumentPresenter  {
    public void onGetListDocumentSaved();
    public void onFinishDocumentSaved(DocumentSavedRequest documentSavedRequest);

}
