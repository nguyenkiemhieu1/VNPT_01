package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentexpired;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentexpired.DocumentExpiredDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentExpiredRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentExpired;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentExpiredInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDetailDocumentExpiredView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentDiagramView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentExpiredView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public class DocumentExpiredPresenterImpl implements IDocumentExpiredPresenter, ICallFinishedListener {

    public IDocumentExpiredView documentExpiredView;
    public IDetailDocumentExpiredView detailDocumentExpiredView;
    public IDocumentDiagramView documentDiagramView;
    public DocumentExpiredDao documentExpiredDao;

    public DocumentExpiredPresenterImpl(IDocumentExpiredView documentExpiredView) {
        this.documentExpiredView = documentExpiredView;
        this.documentExpiredDao = new DocumentExpiredDao();
    }

    public DocumentExpiredPresenterImpl(IDetailDocumentExpiredView detailDocumentExpiredView) {
        this.detailDocumentExpiredView = detailDocumentExpiredView;
        this.documentExpiredDao = new DocumentExpiredDao();
    }

    public DocumentExpiredPresenterImpl(IDocumentDiagramView documentDiagramView) {
        this.documentDiagramView = documentDiagramView;
        this.documentExpiredDao = new DocumentExpiredDao();
    }

    @Override
    public void getCount(DocumentExpiredRequest documentExpiredRequest) {
        if (documentExpiredView != null) {
            documentExpiredView.showProgress();
            documentExpiredDao.onCountDocumentExpiredDao(documentExpiredRequest, this);
        }
    }

    @Override
    public void getRecords(DocumentExpiredRequest documentExpiredRequest) {
        if (documentExpiredView != null) {
            documentExpiredView.showProgress();
            documentExpiredDao.onRecordsDocumentExpiredDao(documentExpiredRequest, this);
        }
    }

    @Override
    public void getBitmapDiagram(String insId, String defId) {
        if (documentDiagramView != null) {
            documentDiagramView.showProgress();
            documentExpiredDao.onGetBitmapDiagram(insId, defId, this);
        }
    }

    @Override
    public void getDetail(int id) {
        if (detailDocumentExpiredView != null) {
            detailDocumentExpiredView.showProgress();
            documentExpiredDao.onGetDetail(id, this);
        }
    }

    @Override
    public void getLogs(int id) {
        if (detailDocumentExpiredView != null) {
            documentExpiredDao.onGetLogs(id, this);
        }
    }

    @Override
    public void getAttachFiles(int id) {
        if (detailDocumentExpiredView != null) {
            documentExpiredDao.onGetAttachFiles(id, this);
        }
    }

    @Override
    public void getRelatedDocs(int id) {
        if (detailDocumentExpiredView != null) {
            documentExpiredDao.onGetRelatedDocs(id, this);
        }
    }

    @Override
    public void getRelatedFiles(int id) {
        if (detailDocumentExpiredView != null) {
            documentExpiredDao.onGetRelatedFiles(id, this);
        }
    }

    @Override
    public void mark(int id) {
        if (detailDocumentExpiredView != null) {
            detailDocumentExpiredView.showProgress();
            documentExpiredDao.onMarkDocument(id, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof CountDocumentExpiredRespone) {
            documentExpiredView.hideProgress();
            CountDocumentExpiredRespone countDocumentExpiredRespone = (CountDocumentExpiredRespone) object;
            if (countDocumentExpiredRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentExpiredView.onSuccessCount(ConvertUtils.fromJSON(countDocumentExpiredRespone.getData(), CountDocumentExpired.class));
            } else {
                documentExpiredView.onError(new APIError(0,  resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof DocumentExpiredRespone) {
            documentExpiredView.hideProgress();
            DocumentExpiredRespone documentExpiredRespone = (DocumentExpiredRespone) object;
            if (documentExpiredRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentExpiredView.onSuccessRecords(ConvertUtils.fromJSONList(documentExpiredRespone.getData(), DocumentExpiredInfo.class));
            } else {
                documentExpiredView.onError(new APIError(0,  resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof ResponseBody) {
            documentDiagramView.hideProgress();
            ResponseBody responseBody = (ResponseBody) object;
            if (responseBody != null) {
                Bitmap bm = BitmapFactory.decodeStream(responseBody.byteStream());
                documentDiagramView.onSuccessDiagram(bm);
            } else {
                documentDiagramView.onError(new APIError(0,  resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof DetailDocumentExpiredRespone) {
            DetailDocumentExpiredRespone detailDocumentExpiredRespone = (DetailDocumentExpiredRespone) object;
            if (detailDocumentExpiredRespone != null && detailDocumentExpiredRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentExpiredView.onSuccess(detailDocumentExpiredRespone.getData());
            } else {
                detailDocumentExpiredView.onError(new APIError(0,  resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof AttachFileRespone) {
            AttachFileRespone attachFileRespone = (AttachFileRespone) object;
            if (attachFileRespone != null && attachFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentExpiredView.onSuccess(ConvertUtils.fromJSONList(attachFileRespone.getData(), AttachFileInfo.class));
            } else {
                detailDocumentExpiredView.onError(new APIError(0,  resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof RelatedDocumentRespone) {
            RelatedDocumentRespone relatedDocumentRespone = (RelatedDocumentRespone) object;
            if (relatedDocumentRespone != null && relatedDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentExpiredView.onSuccess(ConvertUtils.fromJSONList(relatedDocumentRespone.getData(), RelatedDocumentInfo.class));
            } else {
                detailDocumentExpiredView.onError(new APIError(0,  resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof LogRespone) {
            detailDocumentExpiredView.hideProgress();
            LogRespone logRespone = (LogRespone) object;
            if (logRespone != null && logRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentExpiredView.onSuccess(ConvertUtils.fromJSONList(logRespone.getData(), UnitLogInfo.class));
            } else {
                detailDocumentExpiredView.onError(new APIError(0,  resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof RelatedFileRespone) {
            RelatedFileRespone relatedFileRespone = (RelatedFileRespone) object;
            if (relatedFileRespone != null && relatedFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentExpiredView.onSuccess(ConvertUtils.fromJSONList(relatedFileRespone.getData(), RelatedFileInfo.class));
            } else {
                detailDocumentExpiredView.onError(new APIError(0,  resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof MarkDocumentRespone) {
            detailDocumentExpiredView.hideProgress();
            MarkDocumentRespone markDocumentRespone = (MarkDocumentRespone) object;
            if (markDocumentRespone != null && markDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (markDocumentRespone.getData() != null && markDocumentRespone.getData().equals(Constants.MARKED_SUCCESS)) {
                    detailDocumentExpiredView.onMark();
                } else {
                    detailDocumentExpiredView.onError(new APIError(0,  resources.getString(R.string.str_ERROR_TICK_DOCUMENT_DIALOG)));
                }
            } else {
                detailDocumentExpiredView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_TICK_DOCUMENT_DIALOG)));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (documentExpiredView != null) {
            documentExpiredView.hideProgress();
            documentExpiredView.onError((APIError) object);
        }
        if (detailDocumentExpiredView != null) {
            detailDocumentExpiredView.hideProgress();
            detailDocumentExpiredView.onError((APIError) object);
        }
        if (documentDiagramView != null) {
            documentDiagramView.hideProgress();
            documentDiagramView.onError((APIError) object);
        }
    }

}
