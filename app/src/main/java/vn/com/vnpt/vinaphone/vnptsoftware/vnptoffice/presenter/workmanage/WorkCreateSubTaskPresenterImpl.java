package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage;

import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.workmanage.WorkManageDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.AddPersonWorkRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CreateSubTaskRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.GetListJobAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateStatusJobRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailJobManageResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListBossSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListFileSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobAssignRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobHandLingResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListPersonSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListPersonUnitDetailResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListStatusComboxRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListUnitSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateStatusJobResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UploadFileWorkResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UploadResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IAddPersonSubTaskView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ICreeateSubTaskView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDetailFragmentView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IHandlingProgressView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IJobUpdateStatusView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkDetailView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkManageView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 4/20/2017.
 */

public class WorkCreateSubTaskPresenterImpl implements IWorkCreateSubTaskPresenter, ICallFinishedListener {
    public WorkManageDao workManageDao;
    public ICreeateSubTaskView creeateSubTaskView;
    public IAddPersonSubTaskView addPersonSubTaskView;
    private int typeView = 1;
    private int typeAddCreate = 1;

    public WorkCreateSubTaskPresenterImpl(ICreeateSubTaskView creeateSubTaskView) {
        this.workManageDao = new WorkManageDao();
        this.creeateSubTaskView = creeateSubTaskView;
    }

    public WorkCreateSubTaskPresenterImpl(IAddPersonSubTaskView addPersonSubTaskView) {
        this.workManageDao = new WorkManageDao();
        this.addPersonSubTaskView = addPersonSubTaskView;
    }

    @Override
    public void getListBoss() {
        if (creeateSubTaskView != null) {
            creeateSubTaskView.showProgress();
            workManageDao.onGetListBoss(this);
        }
    }

    @Override
    public void getListUnit(int typeView) {
        this.typeView = typeView;
        if (typeView == 1) {
            if (creeateSubTaskView != null) {
                creeateSubTaskView.showProgress();
                workManageDao.onGetListUnit(this);
            }
        } else if (typeView == 2) {
            if (addPersonSubTaskView != null) {
                addPersonSubTaskView.showProgress();
                workManageDao.onGetListUnit(this);
            }
        }

    }

    @Override
    public void getListPerson(String idUnit, int typeView) {
        this.typeView = typeView;
        if (typeView == 1) {
            if (creeateSubTaskView != null) {
                creeateSubTaskView.showProgress();
                workManageDao.onGetListPerson(idUnit, this);
            }
        } else if (typeView == 2) {
            if (addPersonSubTaskView != null) {
                addPersonSubTaskView.showProgress();
                workManageDao.onGetListPerson(idUnit, this);
            }
        }

    }

    @Override
    public void getListFileSubTask(String idWork) {
        if (creeateSubTaskView != null) {
            creeateSubTaskView.showProgress();
            workManageDao.onGetListFileSubTask(idWork, this);
        }
    }

    @Override
    public void createSubTask(CreateSubTaskRequest createSubTaskRequest) {
        typeAddCreate = 1;
        if (creeateSubTaskView != null) {
            creeateSubTaskView.showProgress();
            workManageDao.onCreateSubTask(createSubTaskRequest, this);
        }
    }

    @Override
    public void addPersonTask(AddPersonWorkRequest addPersonWorkRequest) {
        typeAddCreate = 2;
        if (addPersonSubTaskView != null) {
            addPersonSubTaskView.showProgress();
            workManageDao.onAddPersonTask(addPersonWorkRequest, this);
        }
    }

    @Override
    public void UploadFileWork(MultipartBody.Part[] files) {
        if (creeateSubTaskView != null) {
            creeateSubTaskView.showProgress();
            workManageDao.onUpLoadFileWork(files, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (creeateSubTaskView != null) {
            creeateSubTaskView.hideProgress();
        }
        if (addPersonSubTaskView != null) {
            addPersonSubTaskView.hideProgress();
        }
        if (object instanceof GetListBossSubTaskResponse) {
            GetListBossSubTaskResponse getListBossSubTaskResponse = (GetListBossSubTaskResponse) object;
            if (getListBossSubTaskResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                creeateSubTaskView.onSuccessGetListBossData(getListBossSubTaskResponse.getData());
            } else {
                creeateSubTaskView.onError(new APIError(0, getListBossSubTaskResponse.getResponeAPI().getMessage()));
            }
        } else if (object instanceof GetListUnitSubTaskResponse) {
            GetListUnitSubTaskResponse getListUnitSubTaskResponse = (GetListUnitSubTaskResponse) object;
            if (getListUnitSubTaskResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (getListUnitSubTaskResponse.getData() != null && getListUnitSubTaskResponse.getData().size() > 0) {
                    if (typeView == 1) {
                        creeateSubTaskView.onSuccessGetListUnitData(getListUnitSubTaskResponse.getData());
                    } else if (typeView == 2) {
                        addPersonSubTaskView.onSuccessGetListUnitData(getListUnitSubTaskResponse.getData());
                    }
                }
            } else {
                if (typeView == 1) {
                    creeateSubTaskView.onError(new APIError(0, getListUnitSubTaskResponse.getResponeAPI().getMessage()));
                } else {
                    addPersonSubTaskView.onErrorAddPersonData(new APIError(0, getListUnitSubTaskResponse.getResponeAPI().getMessage()));
                }

            }
        } else if (object instanceof GetListPersonSubTaskResponse) {
            GetListPersonSubTaskResponse getListPersonSubTaskResponse = (GetListPersonSubTaskResponse) object;
            if (getListPersonSubTaskResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (getListPersonSubTaskResponse.getData() != null && getListPersonSubTaskResponse.getData().size() > 0) {
                    if (typeView == 1) {
                        creeateSubTaskView.onSuccessGetListPersonData(getListPersonSubTaskResponse.getData());
                    } else if (typeView == 2) {
                        addPersonSubTaskView.onSuccessGetListPersonData(getListPersonSubTaskResponse.getData());
                    }

                }
            } else {
                if (typeView == 1) {
                    creeateSubTaskView.onError(new APIError(0, getListPersonSubTaskResponse.getResponeAPI().getMessage()));
                } else {
                    addPersonSubTaskView.onErrorAddPersonData(new APIError(0, getListPersonSubTaskResponse.getResponeAPI().getMessage()));
                }

            }
        } else if (object instanceof UpdateStatusJobResponse) {
            UpdateStatusJobResponse updateStatusJobResponse = (UpdateStatusJobResponse) object;
            if (updateStatusJobResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (typeAddCreate == 1) {
                    creeateSubTaskView.onSuccessCreateSubTask(updateStatusJobResponse);
                } else if (typeAddCreate == 2) {
                    addPersonSubTaskView.onSuccessAddPersonData(updateStatusJobResponse);
                }

            } else {
                if (typeAddCreate == 1) {
                    creeateSubTaskView.onError(new APIError(0, updateStatusJobResponse.getResponeAPI().getMessage()));
                } else if (typeAddCreate == 2) {
                    addPersonSubTaskView.onErrorAddPersonData(new APIError(0, updateStatusJobResponse.getResponeAPI().getMessage()));
                }

            }
        } else if (object instanceof GetListFileSubTaskResponse) {
            GetListFileSubTaskResponse getListFileSubTaskResponse = (GetListFileSubTaskResponse) object;
            if (getListFileSubTaskResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                creeateSubTaskView.onSuccessGetListFileData(getListFileSubTaskResponse.getData());
            } else {
                creeateSubTaskView.onError(new APIError(0, getListFileSubTaskResponse.getResponeAPI().getMessage()));
            }

        } else if (object instanceof UploadFileWorkResponse) {
            UploadFileWorkResponse uploadResponse = (UploadFileWorkResponse) object;
            if (uploadResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                creeateSubTaskView.onSuccessUpLoad(uploadResponse.getData());
            } else {
                creeateSubTaskView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (creeateSubTaskView != null) {
            creeateSubTaskView.hideProgress();
            creeateSubTaskView.onError((APIError) object);
        }
        if (addPersonSubTaskView != null) {
            addPersonSubTaskView.hideProgress();
            addPersonSubTaskView.onErrorAddPersonData((APIError) object);
        }
    }
}
