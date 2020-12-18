package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.historydocument;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentwaiting.DocumentWaitingDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IHistoryDocumentView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public class HistoryDocumentPresenterImpl implements IHistoryDocumentPresenter, ICallFinishedListener {
    public IHistoryDocumentView historyDocumentView;
    public DocumentWaitingDao documentWaitingDao;

    public HistoryDocumentPresenterImpl(IHistoryDocumentView historyDocumentView) {
        this.historyDocumentView = historyDocumentView;
        this.documentWaitingDao = new DocumentWaitingDao();
    }

    @Override
    public void getLogs(int id) {
        if (historyDocumentView != null) {
            documentWaitingDao.onGetLogs(id, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof LogRespone) {
            historyDocumentView.hideProgress();
            LogRespone logRespone = (LogRespone) object;
            if (logRespone != null && logRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                historyDocumentView.onSuccess(ConvertUtils.fromJSONList(logRespone.getData(), UnitLogInfo.class));
            } else {
                historyDocumentView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        historyDocumentView.hideProgress();
        historyDocumentView.onError((APIError) object);
    }

}
