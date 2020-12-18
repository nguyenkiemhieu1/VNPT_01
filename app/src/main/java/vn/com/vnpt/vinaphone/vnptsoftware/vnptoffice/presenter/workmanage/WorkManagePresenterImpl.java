package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.workmanage.WorkManageDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DanhGiaCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.GetListJobAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateStatusJobRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DanhGiaCongViecResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailJobManageResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobAssignRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobHandLingResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListPersonUnitDetailResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListStatusComboxRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ListFileCongViecResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateJobResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateStatusJobResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.EditInforWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDanhGiaJobView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDetailFragmentView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IHandlingProgressView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IJobUpdateStatusView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IJobUpdateView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IListFileCongViecView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkDetailView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkManageView;

/**
 * Created by LinhLK - 0948012236 on 4/20/2017.
 */

public class WorkManagePresenterImpl implements IWorkManagePresenter, ICallFinishedListener {
    public IWorkManageView workManageView;
    public IWorkDetailView workDetailView;
    public IJobUpdateStatusView jobUpdateStatusView;
    public IJobUpdateView jobUpdateView;
    public IDanhGiaJobView danhGiaJobView;
    public IListFileCongViecView listFileCongViecView;
    public IHandlingProgressView handlingProgressView;
    public IDetailFragmentView iDetailFragmentView;
    public WorkManageDao workManageDao;

    public WorkManagePresenterImpl(IHandlingProgressView handlingProgressView) {
        this.handlingProgressView = handlingProgressView;
        this.workManageDao = new WorkManageDao();
    }

    public WorkManagePresenterImpl(IDetailFragmentView iDetailFragmentView) {
        this.iDetailFragmentView = iDetailFragmentView;
        this.workManageDao = new WorkManageDao();
    }

    public WorkManagePresenterImpl(IWorkManageView loginView) {
        this.workManageView = loginView;
        this.workManageDao = new WorkManageDao();
    }

    public WorkManagePresenterImpl(IWorkManageView loginView, IWorkDetailView workDetailView) {
        this.workDetailView = workDetailView;
        this.workManageView = loginView;
        this.workManageDao = new WorkManageDao();
    }

    public WorkManagePresenterImpl(IWorkDetailView workDetailView) {
        this.workDetailView = workDetailView;
        this.workManageDao = new WorkManageDao();
    }

    public WorkManagePresenterImpl(IWorkManageView workManageView, IJobUpdateStatusView jobUpdateStatusView) {
        this.workManageView = workManageView;
        this.jobUpdateStatusView = jobUpdateStatusView;
        this.workManageDao = new WorkManageDao();
    }

    public WorkManagePresenterImpl(IWorkManageView workManageView, IJobUpdateView iJobUpdateView, IListFileCongViecView iListFileCongViecView
            , IWorkDetailView iWorkDetailView) {
        this.workManageView = workManageView;
        this.jobUpdateView = iJobUpdateView;
        this.jobUpdateView = iJobUpdateView;
        this.listFileCongViecView = iListFileCongViecView;
        this.workDetailView = iWorkDetailView;
        this.workManageDao = new WorkManageDao();
    }

    public WorkManagePresenterImpl(IWorkManageView workManageView, IDanhGiaJobView danhGiaJobView) {
        this.workManageView = workManageView;
        this.danhGiaJobView = danhGiaJobView;
        this.workManageDao = new WorkManageDao();
    }

    @Override
    public void getListJobAssign(GetListJobAssignRequest getListJobAssignRequest) {
        if (workManageView != null) {
            workManageView.showProgress();
            workManageDao.onGetListJobAssignDao(getListJobAssignRequest, this);
        }
    }

    @Override
    public void getListJobReceive(GetListJobAssignRequest getListJobAssignRequest) {
        if (workManageView != null) {
            workManageView.showProgress();
            workManageDao.onGetListJobReceiveDao(getListJobAssignRequest, this);
        }
    }

    @Override
    public void getListStatusCombox(String typeDoc) {
        if (workManageView != null) {
            workManageView.showProgress();
            workManageDao.onGetListStatusComboxDao(typeDoc, this);
        }
    }

    @Override
    public void getDetailReceiveWork(String id, String nxlid) {
        if (workDetailView != null) {
            workDetailView.showProgress();
            workManageDao.onGetDetailJobReceiveDao(id, nxlid, this);
        }
    }

    @Override
    public void getDetailAssignWork(String id) {
        if (workDetailView != null) {
            workDetailView.showProgress();
            workManageDao.onGetDetailJobAssignDao(id, this);
        }
    }

    @Override
    public void updateStatusJob(UpdateStatusJobRequest request) {
        if (jobUpdateStatusView != null) {
            jobUpdateStatusView.showProgress();
            workManageDao.onUpdateStatusJobDao(request, this);
        }
    }

    @Override
    public void getListJobHandling(String id) {
        if (handlingProgressView != null) {
            handlingProgressView.showProgress();
            workManageDao.onGetListJobHandling(id, this);
        }
    }

    @Override
    public void getListSubTaskDetail(String id) {
        if (iDetailFragmentView != null) {
            iDetailFragmentView.showProgress();
            workManageDao.onGetListSubTaskDetail(id, this);
        }
    }

    @Override
    public void getListUnitPersonDetailProcess(String id) {
        if (iDetailFragmentView != null) {
            iDetailFragmentView.showProgress();
            workManageDao.onGetListUnitPersonDetail(id, this);
        }
    }

    @Override
    public void updateJob(UpdateCongViecRequest updateCongViecRequest) {
        if (jobUpdateView != null) {
            jobUpdateView.showProgress();
            workManageDao.onGetUpdatejob(updateCongViecRequest, this);
        }
    }

    @Override
    public void danhgiaJob(DanhGiaCongViecRequest danhGiaCongViecRequest) {
        if (danhGiaJobView != null) {
            danhGiaJobView.showProgress();
            workManageDao.onGetDanhGiajob(danhGiaCongViecRequest, this);
        }
    }

    @Override
    public void getListFile(String id) {
        if (listFileCongViecView != null) {
            listFileCongViecView.showProgress();
            workManageDao.onGetListFileCongViec(id, this);
        }
    }


    @Override
    public void onCallSuccess(Object object) {
        if (workManageView != null) {
            workManageView.hideProgress();
        }
        if (workDetailView != null) {
            workDetailView.hideProgress();
        }
        if (jobUpdateStatusView != null) {
            jobUpdateStatusView.hideProgress();
        }
        if (handlingProgressView != null) {
            handlingProgressView.hideProgress();
        }
        if (iDetailFragmentView != null) {
            iDetailFragmentView.hideProgress();
        }
        if (jobUpdateView != null) {
            jobUpdateView.hideProgress();
        }
        if (danhGiaJobView != null) {
            danhGiaJobView.hideProgress();
        }
        if (listFileCongViecView != null) {
            listFileCongViecView.hideProgress();
        }
        if (object instanceof GetListJobAssignRespone) {
            GetListJobAssignRespone getListJobAssignRespone = (GetListJobAssignRespone) object;
            if (getListJobAssignRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                workManageView.onSuccessGetListData(getListJobAssignRespone.getData());
            } else {
                workManageView.onErrorGetListData(new APIError(0, getListJobAssignRespone.getResponeAPI().getMessage()));
            }
        } else if (object instanceof GetListStatusComboxRespone) {
            GetListStatusComboxRespone getListJobStatusRespone = (GetListStatusComboxRespone) object;
            if (getListJobStatusRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                workManageView.onSuccessGetListStatus(getListJobStatusRespone.getData());
            } else {
                workManageView.onErrorGetListData(new APIError(0, getListJobStatusRespone.getResponeAPI().getMessage()));
            }
        }
        else if (object instanceof ListFileCongViecResponse) {
            ListFileCongViecResponse listFileCongViecResponse = (ListFileCongViecResponse) object;
            if (listFileCongViecResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (listFileCongViecResponse.getData() != null && listFileCongViecResponse.getData().size() > 0) {
                    listFileCongViecView.onSuccessListFile(listFileCongViecResponse.getData());
                }
            } else {
                listFileCongViecView.onErrorListFile(new APIError(0, listFileCongViecResponse.getResponeAPI().getMessage()));
            }
        }
        else if (object instanceof DetailJobManageResponse) {
            DetailJobManageResponse detailJobManageResponse = (DetailJobManageResponse) object;
            if (detailJobManageResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (detailJobManageResponse.getData() != null) {
                    workDetailView.onSuccessGetDetailData(detailJobManageResponse.getData());
                }
            } else {
                workDetailView.onErrorGetListData(new APIError(0, detailJobManageResponse.getResponeAPI().getMessage()));
            }
        }
        else if (object instanceof UpdateStatusJobResponse) {
            UpdateStatusJobResponse updateStatusJobResponse = (UpdateStatusJobResponse) object;
            if (updateStatusJobResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                jobUpdateStatusView.onSuccessUpdateJob();
            } else {
                jobUpdateStatusView.onErrorGetListData(new APIError(0, updateStatusJobResponse.getResponeAPI().getMessage()));
            }
        } else if (object instanceof UpdateJobResponse) {
            UpdateJobResponse updateStatusJobResponse = (UpdateJobResponse) object;
            if (updateStatusJobResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                jobUpdateView.onSuccessUpdateJob();
            } else {
                jobUpdateView.onErrorGetListData(new APIError(0, updateStatusJobResponse.getResponeAPI().getMessage()));
            }

        } else if (object instanceof DanhGiaCongViecResponse) {
            DanhGiaCongViecResponse danhGiaCongViecResponse = (DanhGiaCongViecResponse) object;
            if (danhGiaCongViecResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                danhGiaJobView.onSuccessDanhGiaJob();
            } else {
                danhGiaJobView.onErrorGetDanhGia(new APIError(0, danhGiaCongViecResponse.getResponeAPI().getMessage()));
            }

        } else if (object instanceof GetListJobHandLingResponse) {
            GetListJobHandLingResponse getListJobHandLingResponse = (GetListJobHandLingResponse) object;
            if (getListJobHandLingResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (getListJobHandLingResponse.getData() != null && getListJobHandLingResponse.getData().size() > 0) {

                    handlingProgressView.onSuccessJobHandLingData(getListJobHandLingResponse.getData());
                }
            } else {
                handlingProgressView.onErrorGetListData(new APIError(0, getListJobHandLingResponse.getResponeAPI().getMessage()));
            }
        } else if (object instanceof GetListSubTaskResponse) {
            GetListSubTaskResponse getListSubTaskResponse = (GetListSubTaskResponse) object;
            if (getListSubTaskResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (getListSubTaskResponse.getData() != null && getListSubTaskResponse.getData().size() > 0) {
                    iDetailFragmentView.onSuccessSubListData(getListSubTaskResponse.getData());
                }
            } else {
                iDetailFragmentView.onErrorListData(new APIError(0, getListSubTaskResponse.getResponeAPI().getMessage()));
            }
        } else if (object instanceof GetListPersonUnitDetailResponse) {
            GetListPersonUnitDetailResponse getListPersonUnitDetailResponse = (GetListPersonUnitDetailResponse) object;
            if (getListPersonUnitDetailResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (getListPersonUnitDetailResponse.getData() != null && getListPersonUnitDetailResponse.getData().size() > 0) {

                    iDetailFragmentView.onSuccessUnitPersonListData(getListPersonUnitDetailResponse.getData());
                }
            } else {
                iDetailFragmentView.onErrorListData(new APIError(0, getListPersonUnitDetailResponse.getResponeAPI().getMessage()));
            }
        }

    }

    @Override
    public void onCallError(Object object) {
        if (workDetailView != null) {
            workDetailView.onErrorGetListData((APIError) object);
            workDetailView.hideProgress();
        }
        if (workManageView != null) {
            workManageView.onErrorGetListData((APIError) object);
            workManageView.hideProgress();
        }
        if (jobUpdateStatusView != null) {
            jobUpdateStatusView.onErrorGetListData((APIError) object);
            jobUpdateStatusView.hideProgress();
        }
        if (handlingProgressView != null) {
            handlingProgressView.onErrorGetListData((APIError) object);
            handlingProgressView.hideProgress();
        }

    }
}
