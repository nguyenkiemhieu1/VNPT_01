package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.notify;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.notify.NotifyDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailNotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.NotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReadedNotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.INotifyView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IReadedNotifyView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class NotifyPresenterImpl implements INotifyPresenter, ICallFinishedListener {
    public INotifyView notifyView;
    public IReadedNotifyView readedNotifyView;
    public NotifyDao notifyDao;

    public NotifyPresenterImpl(INotifyView notifyView) {
        this.notifyView = notifyView;
        this.notifyDao = new NotifyDao();
    }

    public NotifyPresenterImpl(IReadedNotifyView readedNotifyView) {
        this.readedNotifyView = readedNotifyView;
        this.notifyDao = new NotifyDao();
    }

    public NotifyPresenterImpl(INotifyView notifyView, IReadedNotifyView readedNotifyView) {
        this.notifyView = notifyView;
        this.readedNotifyView = readedNotifyView;
        this.notifyDao = new NotifyDao();
    }

    @Override
    public void getNotifys(ListNotifyRequest listNotifyRequest) {
        if (notifyView != null) {
            notifyView.showProgress();
            notifyDao.onSendGetNotifysDao(listNotifyRequest, this);
        }
    }

    @Override
    public void readedNotifys(ReadedNotifyRequest readedNotifyRequest) {
        if (readedNotifyView != null) {
            notifyDao.onReadedNotifysDao(this, readedNotifyRequest);
        }
    }

    @Override
    public void getDetailNotify(int docId) {
        if (readedNotifyView != null) {
            readedNotifyView.showProgress();
            notifyDao.onGetDetailNotifyDao(this, docId);
        }
    }

    @Override
    public void getCountDocument() {
        if (notifyView != null) {
            notifyDao.onGetCountDocumentDao(this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {

        if (object instanceof NotifyRespone) {
            notifyView.hideProgress();
            NotifyRespone notifyRespone = (NotifyRespone) object;
            if (notifyRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                notifyView.onSuccess(notifyRespone);
            } else {
                notifyView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof ReadedNotifyRespone) {
            ReadedNotifyRespone readedNotifyRespone = (ReadedNotifyRespone) object;
            if (readedNotifyRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                readedNotifyView.onSuccess(readedNotifyRespone.getData().toUpperCase().equals(Constants.IS_READ));
            } else {
                readedNotifyView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof DetailNotifyRespone) {
            DetailNotifyRespone DetailNotifyRespone = (DetailNotifyRespone) object;
            if (DetailNotifyRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                readedNotifyView.onCheckStoreSuccess(ConvertUtils.fromJSON(DetailNotifyRespone.getData(), DetailNotifyInfo.class));
            } else {
                readedNotifyView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        notifyView.hideProgress();
        if (notifyView != null) {
            notifyView.onError((APIError) object);
        }
        if (readedNotifyView != null) {
            readedNotifyView.onError((APIError) object);
        }
    }

}
