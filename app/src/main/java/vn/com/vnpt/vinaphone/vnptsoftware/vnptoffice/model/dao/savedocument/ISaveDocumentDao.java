package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.savedocument;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSavedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

public interface ISaveDocumentDao {
    void onGetSaveDocumentDao(ICallFinishedListener iCallFinishedListener);
    void onPostSaveDocumentDao(DocumentSavedRequest documentSavedRequest, ICallFinishedListener iCallFinishedListener);
}
