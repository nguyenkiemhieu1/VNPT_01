package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.report;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */

public interface IReportDao {
    void onGetReportDocumentDao(ICallFinishedListener iCallFinishedListener);
    void onGetReportWorkDao(int month, ICallFinishedListener iCallFinishedListener);
}
