package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentsearch;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentsearch.DocumentSearchDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentAdvanceSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentAdvanceSearch;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentAdvanceSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentSearch;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentAdvanceSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentAdvanceSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FieldDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PriorityDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentResultSearchView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentSearchView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IFilterSearchView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public class DocumentSearchPresenterImpl implements IDocumentSearchPresenter, ICallFinishedListener {
    public IFilterSearchView filterSearchView;
    public IDocumentSearchView documentSearchView;
    public IDocumentResultSearchView documentResultSearchView;
    public DocumentSearchDao documentSearchDao;

    public DocumentSearchPresenterImpl(IFilterSearchView filterSearchView) {
        this.filterSearchView = filterSearchView;
        this.documentSearchDao = new DocumentSearchDao();
    }

    public DocumentSearchPresenterImpl(IDocumentSearchView documentSearchView) {
        this.documentSearchView = documentSearchView;
        this.documentSearchDao = new DocumentSearchDao();
    }

    public DocumentSearchPresenterImpl(IDocumentResultSearchView documentResultSearchView) {
        this.documentResultSearchView = documentResultSearchView;
        this.documentSearchDao = new DocumentSearchDao();
    }

    @Override
    public void getTypes() {
        if (filterSearchView != null) {
            filterSearchView.showProgress();
            documentSearchDao.onGetTypes(this);
        }
    }

    @Override
    public void getFields() {
        if (filterSearchView != null) {
            documentSearchDao.onGetFields(this);
        }
    }

    @Override
    public void getPrioritys() {
        if (filterSearchView != null) {
            documentSearchDao.onGetPrioritys(this);
        }
    }

    @Override
    public void getCount(DocumentSearchRequest documentSearchRequest) {
        if (documentSearchView != null) {
            documentSearchView.showProgress();
            documentSearchDao.onCountDocumentSearchDao(documentSearchRequest, this);
        }
    }

    @Override
    public void getRecords(DocumentSearchRequest documentSearchRequest) {
        if (documentSearchView != null) {
            documentSearchView.showProgress();
            documentSearchDao.onRecordsDocumentSearchDao(documentSearchRequest, this);
        }
    }

    @Override
    public void getCountAdvance(DocumentAdvanceSearchRequest documentAdvanceSearchRequest) {
        if (documentResultSearchView != null) {
            documentResultSearchView.showProgress();
            documentSearchDao.onCountDocumentAdvanceSearchDao(documentAdvanceSearchRequest, this);
        }
    }

    @Override
    public void getRecordsAdvance(DocumentAdvanceSearchRequest documentAdvanceSearchRequest) {
        if (documentResultSearchView != null) {
            documentResultSearchView.showProgress();
            documentSearchDao.onRecordsDocumentAdvanceSearchDao(documentAdvanceSearchRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof TypeDocumentRespone) {
            filterSearchView.hideProgress();
            TypeDocumentRespone responseBody = (TypeDocumentRespone) object;
            if (responseBody != null) {
                filterSearchView.onSuccess(responseBody);
            } else {
                filterSearchView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof FieldDocumentRespone) {
            filterSearchView.hideProgress();
            FieldDocumentRespone responseBody = (FieldDocumentRespone) object;
            if (responseBody != null) {
                filterSearchView.onSuccess(responseBody);
            } else {
                filterSearchView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof PriorityDocumentRespone) {
            filterSearchView.hideProgress();
            PriorityDocumentRespone responseBody = (PriorityDocumentRespone) object;
            if (responseBody != null) {
                filterSearchView.onSuccess(responseBody);
            } else {
                filterSearchView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof CountDocumentSearchRespone) {
            CountDocumentSearchRespone countDocumentSearchRespone = (CountDocumentSearchRespone) object;
            if (countDocumentSearchRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentSearchView.onSuccessCount(ConvertUtils.fromJSON(countDocumentSearchRespone.getData(), CountDocumentSearch.class));
            } else {
                documentSearchView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof DocumentSearchRespone) {
            documentSearchView.hideProgress();
            DocumentSearchRespone documentSearchRespone = (DocumentSearchRespone) object;
            if (documentSearchRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentSearchView.onSuccessRecords(ConvertUtils.fromJSONList(documentSearchRespone.getData(), DocumentSearchInfo.class));
            } else {
                documentSearchView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof CountDocumentAdvanceSearchRespone) {
            CountDocumentAdvanceSearchRespone countDocumentSearchRespone = (CountDocumentAdvanceSearchRespone) object;
            if (countDocumentSearchRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentResultSearchView.onSuccessCount(ConvertUtils.fromJSON(countDocumentSearchRespone.getData(), CountDocumentAdvanceSearch.class));
            } else {
                documentResultSearchView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof DocumentAdvanceSearchRespone) {
            documentResultSearchView.hideProgress();
            DocumentAdvanceSearchRespone documentSearchRespone = (DocumentAdvanceSearchRespone) object;
            if (documentSearchRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentResultSearchView.onSuccessRecords(ConvertUtils.fromJSONList(documentSearchRespone.getData(), DocumentAdvanceSearchInfo.class));
            } else {
                documentResultSearchView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (filterSearchView != null) {
            filterSearchView.hideProgress();
            filterSearchView.onError((APIError) object);
        }
        if (documentSearchView != null) {
            documentSearchView.hideProgress();
            documentSearchView.onError((APIError) object);
        }
        if (documentResultSearchView != null) {
            documentResultSearchView.hideProgress();
            documentResultSearchView.onError((APIError) object);
        }
    }

}
