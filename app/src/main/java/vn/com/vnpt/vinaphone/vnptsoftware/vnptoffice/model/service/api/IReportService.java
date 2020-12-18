package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReportDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReportWorkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface IReportService {
    @GET(ServiceUrl.GET_REPORT_DOCUMENT_URL)
    Call<ReportDocumentRespone> getReportDocument();
    @GET(ServiceUrl.GET_REPORT_WORK_URL)
    Call<ReportWorkRespone> getReportWork(@Path("month") int month);
}