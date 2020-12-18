package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.report;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReportDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReportWorkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IReportService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */

public class ReportDao extends BaseDao implements IReportDao {

    private IReportService reportService;

    @Override
    public void onGetReportDocumentDao(ICallFinishedListener iCallFinishedListener) {
        reportService = BaseService.createService(IReportService.class);
        Call<ReportDocumentRespone> call = reportService.getReportDocument();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetReportWorkDao(int month, ICallFinishedListener iCallFinishedListener) {
        reportService = BaseService.createService(IReportService.class);
        Call<ReportWorkRespone> call = reportService.getReportWork(month);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

}
