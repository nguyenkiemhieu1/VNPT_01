package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReportDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReportWorkInfo;

/**
 * Created by LinhLK - 0948012236 on 4/12/2017.
 */

public interface IReportView {
    void onSuccessDoc(ReportDocumentInfo object);
    void onSuccessWork(ReportWorkInfo object);
    void onError(APIError apiError);
}