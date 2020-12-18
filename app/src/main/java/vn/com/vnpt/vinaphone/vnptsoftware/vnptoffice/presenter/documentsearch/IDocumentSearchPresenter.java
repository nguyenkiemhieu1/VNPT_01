package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentsearch;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentAdvanceSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSearchRequest;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public interface IDocumentSearchPresenter {
    void getTypes();
    void getFields();
    void getPrioritys();
    void getCount(DocumentSearchRequest documentSearchRequest);
    void getRecords(DocumentSearchRequest documentSearchRequest);
    void getCountAdvance(DocumentAdvanceSearchRequest documentAdvanceSearchRequest);
    void getRecordsAdvance(DocumentAdvanceSearchRequest documentAdvanceSearchRequest);
}
