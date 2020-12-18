package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentprocessed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentprocessed.DocumentProcessedDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentProcessedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckDocProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckRecoverDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentProcessed;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailDocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentProcessedInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RecoverDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.CheckRecoverEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDetailDocumentProcessedView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentDiagramView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentProcessedView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public class DocumentProcessedPresenterImpl implements IDocumentProcessedPresenter, ICallFinishedListener {
    public IDocumentProcessedView documentProcessedView;
    public IDetailDocumentProcessedView detailDocumentProcessedView;
    public IDocumentDiagramView documentDiagramView;
    public DocumentProcessedDao documentProcessedDao;

    public DocumentProcessedPresenterImpl(IDocumentProcessedView documentProcessedView) {
        this.documentProcessedView = documentProcessedView;
        this.documentProcessedDao = new DocumentProcessedDao();
    }

    public DocumentProcessedPresenterImpl(IDetailDocumentProcessedView detailDocumentProcessedView) {
        this.detailDocumentProcessedView = detailDocumentProcessedView;
        this.documentProcessedDao = new DocumentProcessedDao();
    }

    public DocumentProcessedPresenterImpl(IDocumentDiagramView documentDiagramView) {
        this.documentDiagramView = documentDiagramView;
        this.documentProcessedDao = new DocumentProcessedDao();
    }

    @Override
    public void getBitmapDiagram(String insId, String defId) {
        if (documentDiagramView != null) {
            documentDiagramView.showProgress();
            documentProcessedDao.onGetBitmapDiagram(insId, defId, this);
        }
    }

    @Override
    public void mark(int id) {
        if (detailDocumentProcessedView != null) {
            detailDocumentProcessedView.showProgress();
            documentProcessedDao.onMarkDocument(id, this);
        }
    }

    @Override
    public void checkChange(int id) {
        if (detailDocumentProcessedView != null) {
            documentProcessedDao.onCheckChangeDocDocument(id, this);
        }
    }

    @Override
    public void getCount(DocumentProcessedRequest documentProcessedRequest) {
        if (documentProcessedView != null) {
            documentProcessedView.showProgress();
            documentProcessedDao.onCountDocumentProcessedDao(documentProcessedRequest, this);
        }
    }

    @Override
    public void getRecords(DocumentProcessedRequest documentProcessedRequest) {
        if (documentProcessedView != null) {
            documentProcessedView.showProgress();
            documentProcessedDao.onRecordsDocumentProcessedDao(documentProcessedRequest, this);
        }
    }

    @Override
    public void getDetail(int id) {
        if (detailDocumentProcessedView != null) {
            detailDocumentProcessedView.showProgress();
            documentProcessedDao.onGetDetail(id, this);
        }
    }

    @Override
    public void getLogs(int id) {
        if (detailDocumentProcessedView != null) {
            documentProcessedDao.onGetLogs(id, this);
        }
    }

    @Override
    public void getAttachFiles(int id) {
        if (detailDocumentProcessedView != null) {
            documentProcessedDao.onGetAttachFiles(id, this);
        }
    }

    @Override
    public void getRelatedDocs(int id) {
        if (detailDocumentProcessedView != null) {
            documentProcessedDao.onGetRelatedDocs(id, this);
        }
    }

    @Override
    public void getRelatedFiles(int id) {
        if (detailDocumentProcessedView != null) {
            documentProcessedDao.onGetRelatedFiles(id, this);
        }
    }

    @Override
    public void checkRecover(String type, int id) {
        if (detailDocumentProcessedView != null) {
            documentProcessedDao.onCheckRecoverDocumentDao(type, id, this);
        }
    }

    @Override
    public void recover(String type, int id) {
        if (detailDocumentProcessedView != null) {
            detailDocumentProcessedView.showProgress();
            documentProcessedDao.onRecoverDocumentDao(type, id, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
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
        if (object instanceof CountDocumentProcessedRespone) {
            documentProcessedView.hideProgress();
            CountDocumentProcessedRespone countDocumentProcessedRespone = (CountDocumentProcessedRespone) object;
            if (countDocumentProcessedRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentProcessedView.onSuccessCount(ConvertUtils.fromJSON(countDocumentProcessedRespone.getData(), CountDocumentProcessed.class));
            } else {
                documentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof DocumentProcessedRespone) {
            documentProcessedView.hideProgress();
            DocumentProcessedRespone documentProcessedRespone = (DocumentProcessedRespone) object;
            if (documentProcessedRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentProcessedView.onSuccessRecords(ConvertUtils.fromJSONList(documentProcessedRespone.getData(), DocumentProcessedInfo.class));
            } else {
                documentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof DetailDocumentProcessedRespone) {
            detailDocumentProcessedView.hideProgress();
            DetailDocumentProcessedRespone detailDocumentProcessedRespone = (DetailDocumentProcessedRespone) object;
            if (detailDocumentProcessedRespone != null && detailDocumentProcessedRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentProcessedView.onSuccess(detailDocumentProcessedRespone.getData());
            } else {
                detailDocumentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof AttachFileRespone) {
            AttachFileRespone attachFileRespone = (AttachFileRespone) object;
            if (attachFileRespone != null && attachFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentProcessedView.onSuccess(ConvertUtils.fromJSONList(attachFileRespone.getData(), AttachFileInfo.class));
            } else {
                detailDocumentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof RelatedFileRespone) {
            RelatedFileRespone relatedFileRespone = (RelatedFileRespone) object;
            if (relatedFileRespone != null && relatedFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentProcessedView.onSuccess(ConvertUtils.fromJSONList(relatedFileRespone.getData(), RelatedFileInfo.class));
            } else {
                detailDocumentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof RelatedDocumentRespone) {
            RelatedDocumentRespone relatedDocumentRespone = (RelatedDocumentRespone) object;
            if (relatedDocumentRespone != null && relatedDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentProcessedView.onSuccess(ConvertUtils.fromJSONList(relatedDocumentRespone.getData(), RelatedDocumentInfo.class));
            } else {
                detailDocumentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof LogRespone) {
            detailDocumentProcessedView.hideProgress();
            LogRespone logRespone = (LogRespone) object;
            if (logRespone != null && logRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentProcessedView.onSuccess(ConvertUtils.fromJSONList(logRespone.getData(), UnitLogInfo.class));
            } else {
                detailDocumentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof CheckRecoverDocumentRespone) {
            CheckRecoverDocumentRespone checkRecoverDocumentRespone = (CheckRecoverDocumentRespone) object;
            if (checkRecoverDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                CheckRecoverEvent checkRecoverEvent = EventBus.getDefault().getStickyEvent(CheckRecoverEvent.class);
                boolean display = false;
                if (checkRecoverEvent.getType().equals(Constants.DOCUMENT_RECEIVE)) {
                    if (checkRecoverDocumentRespone.getData() != null && checkRecoverDocumentRespone.getData().equals(Constants.DOCUMENT_RECEIVE_RECOVERED)) {
                        display = true;
                    }
                    if (checkRecoverDocumentRespone.getData() != null && checkRecoverDocumentRespone.getData().equals(Constants.DOCUMENT_RECEIVE_NOT_RECOVERED)) {
                        display = false;
                    }
                }
                if (checkRecoverEvent.getType().equals(Constants.DOCUMENT_SEND)) {
                    if (checkRecoverDocumentRespone.getData() == null) {
                        display = true;
                    } else {
                        if (checkRecoverDocumentRespone.getData().equals(Constants.DOCUMENT_SEND_NOT_RECOVERED)) {
                            display = false;
                        }
                    }
                }
                detailDocumentProcessedView.onSuccessCheckRecover(display);
            } else {
                detailDocumentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof RecoverDocumentRespone) {
            detailDocumentProcessedView.hideProgress();
            RecoverDocumentRespone recoverDocumentRespone = (RecoverDocumentRespone) object;
            if (recoverDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (recoverDocumentRespone.getData() != null && recoverDocumentRespone.getData().equals(Constants.DOCUMENT_RECOVERED_SUCCESS)) {
                    detailDocumentProcessedView.onSuccessCheckRecover(true);
                } else {
                    detailDocumentProcessedView.onSuccessCheckRecover(false);
                }
            } else {
                detailDocumentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof MarkDocumentRespone) {
            detailDocumentProcessedView.hideProgress();
            MarkDocumentRespone markDocumentRespone = (MarkDocumentRespone) object;
            if (markDocumentRespone != null && markDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (markDocumentRespone.getData() != null && markDocumentRespone.getData().equals(Constants.MARKED_SUCCESS)) {
                    detailDocumentProcessedView.onMark();
                } else {
                    detailDocumentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_TICK_DOCUMENT_DIALOG)));
                }
            } else {
                detailDocumentProcessedView.onError(new APIError(0,   resources.getString(R.string.str_ERROR_TICK_DOCUMENT_DIALOG)));
            }
        }
        if (object instanceof CheckDocProcessedRespone) {
            CheckDocProcessedRespone checkDocProcessedRespone = (CheckDocProcessedRespone) object;
            if (checkDocProcessedRespone != null && checkDocProcessedRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (checkDocProcessedRespone.getData() != null && checkDocProcessedRespone.getData().equals(Constants.IS_CHANGE_DOC)) {
                    detailDocumentProcessedView.onChange();
                } else {
                    detailDocumentProcessedView.onError(new APIError(0,resources.getString(R.string.str_ERROR_CHECK_DOCUMENT_DIALOG)));
                }
            } else {
                detailDocumentProcessedView.onError(new APIError(0, resources.getString(R.string.str_ERROR_CHECK_DOCUMENT_DIALOG)));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (documentProcessedView != null) {
            documentProcessedView.hideProgress();
            documentProcessedView.onError((APIError) object);
        }
        if (detailDocumentProcessedView != null) {
            detailDocumentProcessedView.hideProgress();
            detailDocumentProcessedView.onError((APIError) object);
        }
        if (documentDiagramView != null) {
            documentDiagramView.hideProgress();
            documentDiagramView.onError((APIError) object);
        }
    }

}
