package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.AttachFileCustomAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.WorkFragmentPagerAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailJobManageResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.IWorkManagePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.WorkManagePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UpdateWorkManageEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.WorkManageEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkDetailView;

public class DetailWorkManagementActivity extends BaseActivity implements IWorkDetailView, ILoginView, IExceptionErrorView {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_title_content)
    TextView tvTitleContent;
    @BindView(R.id.tv_date_assign)
    TextView tvDateAssign;
    @BindView(R.id.tv_expiration_date)
    TextView tvExpirationDate;
    @BindView(R.id.tv_prioritize_level)
    TextView tvPrioritizeLevel;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_content_assess)
    TextView tvContentAssess;
    @BindView(R.id.tv_filedinhkem_label)
    TextView tvFiledinhkemLabel;
    @BindView(R.id.layout_file_attach)
    RecyclerView layoutFileAttach;
    @BindView(R.id.layoutFileDK)
    LinearLayout layoutFileDK;
    @BindView(R.id.ll_progress)
    LinearLayout llProgress;
    @BindView(R.id.ll_update_status)
    LinearLayout llUpdateStatus;
    @BindView(R.id.ll_create_subtask)
    LinearLayout llCreateSubtask;
    @BindView(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    IWorkManagePresenter iWorkManagePresenter = new WorkManagePresenterImpl(this);
    WorkFragmentPagerAdapter adapter;
    @BindView(R.id.ll_option_receive)
    LinearLayout llOptionReceive;
    @BindView(R.id.ll_add_person)
    LinearLayout llAddPerson;
    @BindView(R.id.ll_update_infor)
    LinearLayout llUpdateInfor;
    @BindView(R.id.ll_handling_progress)
    LinearLayout llHandlingProgress;
    @BindView(R.id.ll_work_evaluating)
    LinearLayout llWorkEvaluating;
    @BindView(R.id.scrollView)
    NestedScrollView nestedScrollView;
    private int typeWork = 1;
    private String idWork = "";
    private String nxlid = "";
    private ProgressDialog progressDialog;
    DetailJobManageResponse.Data dataDetail;
    private ApplicationSharedPreferences appPrefs;
    private ConnectionDetector connectionDetector;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work_management);
        ButterKnife.bind(this);
        getDataIntent();
        initView();
        getDetailJob();
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        idWork = intent.getStringExtra("id");
        nxlid = intent.getStringExtra("nxlid");
        typeWork = intent.getIntExtra("typeWork", 1);
    }

    private void initView() {
        appPrefs = Application.getApp().getAppPrefs();
        connectionDetector = new ConnectionDetector(this);
        tvTitle.setText(getResources().getString(R.string.str_chitiet_congviec));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailWorkManagementActivity.this.finish();
            }
        });
        nestedScrollView.setFillViewport(true);
    }

    private void getDetailJob() {
        if (typeWork == 1) {
            iWorkManagePresenter.getDetailReceiveWork(idWork, nxlid);
        } else {
            iWorkManagePresenter.getDetailAssignWork(idWork);
        }
    }


    private void setUpViewOption(DetailJobManageResponse.Data data) {
        if (data != null) {
            if (data.getBtnAddAssign() != null && data.getBtnAddAssign().equals("true")) {
                llAddPerson.setVisibility(View.VISIBLE);
            } else {
                llAddPerson.setVisibility(View.GONE);
            }

            if (data.getBtnCreateJob() != null && data.getBtnCreateJob().equals("true")) {
                llCreateSubtask.setVisibility(View.VISIBLE);
            } else {
                llCreateSubtask.setVisibility(View.GONE);
            }

            if (data.getBtnEvaluate() != null && data.getBtnEvaluate().equals("true")) {
                llWorkEvaluating.setVisibility(View.VISIBLE);
            } else {
                llWorkEvaluating.setVisibility(View.GONE);
            }

            if (data.getBtnFlow() != null && data.getBtnFlow().equals("true")) {
                if (typeWork == 1) {
                    llProgress.setVisibility(View.VISIBLE);
                    llHandlingProgress.setVisibility(View.GONE);
                } else {
                    llHandlingProgress.setVisibility(View.VISIBLE);
                    llProgress.setVisibility(View.GONE);
                }

            } else {
                llHandlingProgress.setVisibility(View.GONE);
                llProgress.setVisibility(View.GONE);
            }

            if (data.getBtnStatus() != null && data.getBtnStatus().equals("true")) {
                llUpdateStatus.setVisibility(View.VISIBLE);
            } else {
                llUpdateStatus.setVisibility(View.GONE);
            }

            if (data.getBtnUpdate() != null && data.getBtnUpdate().equals("true")) {
                llUpdateInfor.setVisibility(View.VISIBLE);
            } else {
                llUpdateInfor.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onSuccessGetDetailData(DetailJobManageResponse.Data data) {
        if (data != null) {
            initDataToView(data);
        }

    }

    private void initDataToView(DetailJobManageResponse.Data data) {
        dataDetail = data;
        tvTitleContent.setText(data.getNoiDung());
        tvDateAssign.setText(data.getNgayGiaoViec());
        tvExpirationDate.setText(data.getNgayHetHan());
        tvPrioritizeLevel.setText(data.getPriority());
        if (typeWork == 1) {
            tvStatus.setText(data.getStatus());
        } else {
            tvStatus.setText(data.getStatusDanhGia());
        }
        tvStatus.setText(data.getStatus());
        tvContentAssess.setText(data.getNoiDungDanhGia());
        if (data.getFiles() != null && data.getFiles().size() > 0) {
            AttachFileCustomAdapter attachFileAdapter =
                    new AttachFileCustomAdapter(this, R.layout.item_file_attach_list, data.getFiles());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            layoutFileAttach.setLayoutManager(mLayoutManager);
            layoutFileAttach.setItemAnimator(new DefaultItemAnimator());
            layoutFileAttach.setAdapter(attachFileAdapter);
        }
        setUpViewOption(data);
        adapter = new WorkFragmentPagerAdapter(getSupportFragmentManager(), this, typeWork);
        viewpager.setAdapter(adapter);
        slidingTabs.setupWithViewPager(viewpager);
    }

    @Override
    public void onErrorGetListData(APIError apiError) {
        sendExceptionError(apiError);
        if (!isFinishing()) {
            sendExceptionError(apiError);
            if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
                if (connectionDetector.isConnectingToInternet()) {
                    loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), apiError.getMessage() != null && !apiError.getMessage().trim().equals("") ? apiError.getMessage().trim() : "Có lỗi xảy ra!\nVui lòng thử lại sau!", true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        if (!isFinishing()) {
            Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
            getDetailJob();
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        if (!isFinishing()) {
            showProgressDialog();
        }
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @OnClick({R.id.ll_add_person, R.id.ll_update_infor, R.id.ll_handling_progress, R.id.ll_work_evaluating,
            R.id.ll_progress, R.id.ll_update_status, R.id.ll_create_subtask})
    public void onViewClicked(View view) {
        EventBus.getDefault().postSticky(new WorkManageEvent(idWork, nxlid, dataDetail.getName(), dataDetail.getNgayHetHan()));
        UpdateWorkManageEvent updateWorkManageEvent = new UpdateWorkManageEvent();
        updateWorkManageEvent.setId(idWork);
        updateWorkManageEvent.setName(dataDetail.getName());
        updateWorkManageEvent.setMucdo(dataDetail.getPriority());
        updateWorkManageEvent.setStatus(dataDetail.getStatus());
        updateWorkManageEvent.setEndDate(dataDetail.getNgayHetHan());
        updateWorkManageEvent.setStatusDanhGia(dataDetail.getStatusDanhGia());
        updateWorkManageEvent.setNoidungDanhGia(dataDetail.getNoiDungDanhGia());
        updateWorkManageEvent.setNoiDung(dataDetail.getNoiDung());
        EventBus.getDefault().postSticky(updateWorkManageEvent);

        switch (view.getId()) {
            case R.id.ll_add_person:
                startActivity(new Intent(this, AddPersonWorkActivity.class));
                break;
            case R.id.ll_update_infor:
                startActivity(new Intent(this, EditInforWorkActivity.class));
                break;
            case R.id.ll_handling_progress:
            case R.id.ll_progress:
                startActivity(new Intent(this, HandlingProgressActivity.class));
                break;
            case R.id.ll_work_evaluating:
                startActivity(new Intent(this, WorkAssessActivity.class));
                break;
            case R.id.ll_update_status:
                startActivity(new Intent(this, UpdateStatusWorkActivity.class));
                break;
            case R.id.ll_create_subtask:
                startActivity(new Intent(this, CreateSubTaskActivity.class));
                break;
        }
    }

    private void sendExceptionError(APIError apiError) {
        ExceptionRequest exceptionRequest = new ExceptionRequest();
        LoginInfo eventbus = EventBus.getDefault().getStickyEvent(LoginInfo.class);
        if (eventbus != null) {
            exceptionRequest.setUserId(eventbus.getUsername());
        } else {
            exceptionRequest.setUserId("");
        }
        exceptionRequest.setDevice(appPrefs.getDeviceName());
        ExceptionCallAPIEvent error = EventBus.getDefault().getStickyEvent(ExceptionCallAPIEvent.class);
        if (error != null) {
            exceptionRequest.setFunction(error.getUrlAPI());
        } else {
            exceptionRequest.setFunction("");
        }
        exceptionRequest.setException(apiError.getMessage());
        iExceptionErrorPresenter.sendExceptionError(exceptionRequest);
    }

    @Override
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }
}
