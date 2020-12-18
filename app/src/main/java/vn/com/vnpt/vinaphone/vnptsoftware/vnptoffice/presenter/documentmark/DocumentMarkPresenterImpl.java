package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentmark;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentmark.DocumentMarkDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentMarkRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentMark;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentMarkInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDetailDocumentMarkView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentDiagramView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentMarkView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public class DocumentMarkPresenterImpl implements IDocumentMarkPresenter, ICallFinishedListener {
    public IDocumentMarkView documentMarkView;
    public IDetailDocumentMarkView detailDocumentMarkView;
    public IDocumentDiagramView documentDiagramView;
    public DocumentMarkDao documentMarkDao;

    public DocumentMarkPresenterImpl(IDocumentMarkView documentMarkView) {
        this.documentMarkView = documentMarkView;
        this.documentMarkDao = new DocumentMarkDao();
    }

    public DocumentMarkPresenterImpl(IDetailDocumentMarkView detailDocumentMarkView) {
        this.detailDocumentMarkView = detailDocumentMarkView;
        this.documentMarkDao = new DocumentMarkDao();
    }

    public DocumentMarkPresenterImpl(IDocumentDiagramView documentDiagramView) {
        this.documentDiagramView = documentDiagramView;
        this.documentMarkDao = new DocumentMarkDao();
    }

    @Override
    public void getCount(DocumentMarkRequest documentMarkRequest) {
        if (documentMarkView != null) {
            documentMarkView.showProgress();
            documentMarkDao.onCountDocumentMarkDao(documentMarkRequest, this);
        }
    }

    @Override
    public void getRecords(DocumentMarkRequest documentMarkRequest) {
        if (documentMarkView != null) {
            documentMarkView.showProgress();
            documentMarkDao.onRecordsDocumentMarkDao(documentMarkRequest, this);
        }
    }

    @Override
    public void getDetail(int id) {
        if (detailDocumentMarkView != null) {
            detailDocumentMarkView.showProgress();
            documentMarkDao.onGetDetail(id, this);
        }
    }

    @Override
    public void getLogs(int id) {
        if (detailDocumentMarkView != null) {
            documentMarkDao.onGetLogs(id, this);
        }
    }

    @Override
    public void getAttachFiles(int id) {
        if (detailDocumentMarkView != null) {
            documentMarkDao.onGetAttachFiles(id, this);
        }
    }

    @Override
    public void getRelatedDocs(int id) {
        if (detailDocumentMarkView != null) {
            documentMarkDao.onGetRelatedDocs(id, this);
        }
    }

    @Override
    public void getRelatedFiles(int id) {
        if (detailDocumentMarkView != null) {
            documentMarkDao.onGetRelatedFiles(id, this);
        }
    }

    @Override
    public void getBitmapDiagram(String insId, String defId) {
        if (documentDiagramView != null) {
            documentDiagramView.showProgress();
            documentMarkDao.onGetBitmapDiagram(insId, defId, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof CountDocumentMarkRespone) {
            documentMarkView.hideProgress();
            CountDocumentMarkRespone countDocumentMarkRespone = (CountDocumentMarkRespone) object;
            if (countDocumentMarkRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentMarkView.onSuccessCount(ConvertUtils.fromJSON(countDocumentMarkRespone.getData(), CountDocumentMark.class));
            } else {
                documentMarkView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof DocumentMarkRespone) {
            documentMarkView.hideProgress();
            DocumentMarkRespone documentMarkRespone = (DocumentMarkRespone) object;
            if (documentMarkRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentMarkView.onSuccessRecords(ConvertUtils.fromJSONList(documentMarkRespone.getData(), DocumentMarkInfo.class));
            } else {
                documentMarkView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof DetailDocumentMarkRespone) {
            DetailDocumentMarkRespone detailDocumentMarkRespone = (DetailDocumentMarkRespone) object;
            if (detailDocumentMarkRespone != null && detailDocumentMarkRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentMarkView.onSuccess(detailDocumentMarkRespone.getData());
            } else {
                detailDocumentMarkView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof AttachFileRespone) {
            AttachFileRespone attachFileRespone = (AttachFileRespone) object;
            if (attachFileRespone != null && attachFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentMarkView.onSuccess(ConvertUtils.fromJSONList(attachFileRespone.getData(), AttachFileInfo.class));
            } else {
                detailDocumentMarkView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof RelatedDocumentRespone) {
            RelatedDocumentRespone relatedDocumentRespone = (RelatedDocumentRespone) object;
            if (relatedDocumentRespone != null && relatedDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentMarkView.onSuccess(ConvertUtils.fromJSONList(relatedDocumentRespone.getData(), RelatedDocumentInfo.class));
            } else {
                detailDocumentMarkView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof LogRespone) {
            detailDocumentMarkView.hideProgress();
            LogRespone logRespone = (LogRespone) object;
            if (logRespone != null && logRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentMarkView.onSuccess(ConvertUtils.fromJSONList(logRespone.getData(), UnitLogInfo.class));
            } else {
                detailDocumentMarkView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof ResponseBody) {
            documentDiagramView.hideProgress();
            ResponseBody responseBody = (ResponseBody) object;
            if (responseBody != null) {
                Bitmap bm = BitmapFactory.decodeStream(responseBody.byteStream());
                documentDiagramView.onSuccessDiagram(bm);
            } else {
                documentDiagramView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof RelatedFileRespone) {
            RelatedFileRespone relatedFileRespone = (RelatedFileRespone) object;
            if (relatedFileRespone != null && relatedFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentMarkView.onSuccess(ConvertUtils.fromJSONList(relatedFileRespone.getData(), RelatedFileInfo.class));
            } else {
                detailDocumentMarkView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (documentMarkView != null) {
            documentMarkView.hideProgress();
            documentMarkView.onError((APIError) object);
        }
        if (detailDocumentMarkView != null) {
            detailDocumentMarkView.hideProgress();
            detailDocumentMarkView.onError((APIError) object);
        }
        if (documentDiagramView != null) {
            documentDiagramView.hideProgress();
            documentDiagramView.onError((APIError) object);
        }
    }

}
