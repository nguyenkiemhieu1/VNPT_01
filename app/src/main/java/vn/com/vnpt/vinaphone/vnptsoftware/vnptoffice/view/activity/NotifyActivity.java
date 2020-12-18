package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.NotifyV2Adapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentNotificationInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentProcessedInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.NotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.NotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.notify.INotifyPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.notify.NotifyPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.InitEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.NotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReadNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.WorkManageEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.Notify;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.INotifyView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IReadedNotifyView;

public class NotifyActivity extends BaseActivity implements INotifyView, IReadedNotifyView, ILoginView, IExceptionErrorView {

    private INotifyPresenter notifyPresenter = new NotifyPresenterImpl(this, this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    @BindView(R.id.tickAll)
    TextView tickAll;
    @BindView(R.id.tickReaded)
    TextView tickReaded;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.layoutHeader)
    LinearLayout layoutHeader;
    private boolean tickedAll;
    private Toolbar toolbar;
    private ImageView btnBack;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private List<NotifyInfo> notifyInfoList = new ArrayList<>();
    private int step;
    private ProgressDialog progressDialog;
    private String idDoc = "";

    private NotifyV2Adapter notifyV2Adapter;

    @BindView(R.id.rcvDanhSach)
    RecyclerView rcvDanhSach;

    private int page = 1;
    private int pageRec = 10;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        EventBus.getDefault().postSticky(new ReadNotifyEvent(false));
        init();
        getNotifys(page);
        EventBus.getDefault().postSticky(new InitEvent(true));
    }

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();

        setupToolbar();
        progressDialog = new ProgressDialog(this);
        tickAll.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tickReaded.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        EventBus.getDefault().postSticky(new NotifyEvent(new ArrayList<Notify>()));
        tickedAll = false;
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_notify);
        setSupportActionBar(toolbar);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(NotifyActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @OnClick({R.id.tickReaded, R.id.tickAll})
    public void tickEvent(View view) {
        switch (view.getId()) {
            case R.id.tickAll:

                NotifyV2Adapter notifyAdapter = null;
                if (notifyInfoList != null && notifyInfoList.size() > 0) {
                    NotifyEvent notifyEvent = EventBus.getDefault().getStickyEvent(NotifyEvent.class);
                    if (tickedAll) {
                        tickedAll = false;
                        notifyAdapter = new NotifyV2Adapter(this, notifyInfoList, false);
                        notifyEvent.setNotifyList(new ArrayList<Notify>());
                    } else {
                        tickedAll = true;
                        notifyAdapter = new NotifyV2Adapter(this, notifyInfoList, true);
                        List<Notify> notifyList = new ArrayList<>();
                        for (NotifyInfo notifyInfo : notifyInfoList) {
                            Notify notify = new Notify(notifyInfo.getId());
                            notifyList.add(notify);
                        }
                        notifyEvent.setNotifyList(notifyList);
                    }
                    EventBus.getDefault().postSticky(notifyEvent);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                    rcvDanhSach.setLayoutManager(mLayoutManager);
                    rcvDanhSach.setItemAnimator(new DefaultItemAnimator());
                    rcvDanhSach.setAdapter(notifyAdapter);
                    notifyAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tickReaded:
                readedNotify();
                break;
        }

    }

    private void readedNotify() {
        NotifyEvent notifyEvent = EventBus.getDefault().getStickyEvent(NotifyEvent.class);
        List<Notify> notifyList = notifyEvent.getNotifyList();
        String notifyIdStr = "";
        for (int i = 0; i < notifyList.size(); i++) {
            if (i < notifyList.size() - 1) {
                notifyIdStr += notifyList.get(i).getId() + ",";
            } else {
                notifyIdStr += notifyList.get(i).getId();
            }
        }
        if (connectionDetector.isConnectingToInternet()) {
            if (notifyIdStr.equals("")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_READED_ERROR), getString(R.string.UNCHECK_READED), true, AlertDialogManager.ERROR);
            } else {
                step = 2;
                notifyPresenter.readedNotifys(new ReadedNotifyRequest(notifyIdStr));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }

    }

    private void getNotifys(int pageNo) {
        if (connectionDetector.isConnectingToInternet()) {
            step = 1;
            ListNotifyRequest listNotifyRequest = new ListNotifyRequest(String.valueOf(pageNo), "10");
            notifyPresenter.getNotifys(listNotifyRequest);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_READED_ERROR), getString(R.string.READED_FAIL), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccess(boolean isRead) {
        if (isRead) {
            EventBus.getDefault().postSticky(new ReadNotifyEvent(true));
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_SUCCESS), getString(R.string.MARK_READ_SUCCESS),
                    true, AlertDialogManager.SUCCESS);

            page = 1;
            notifyInfoList.clear();
            getNotifys(page);

        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccess(Object object) {
        NotifyRespone notifyRespone = (NotifyRespone) object;

        if (notifyRespone != null) {

            List<NotifyInfo> notifyInfos = ConvertUtils.fromJSONList(notifyRespone.getData().getNotifys(), NotifyInfo.class);

            if (EventBus.getDefault().getStickyEvent(InitEvent.class).isInit()) {
                if (notifyInfos != null && notifyInfos.size() > 0) {
                    notifyInfoList.addAll(notifyInfos);
                }
                prepareNewData();
                EventBus.getDefault().postSticky(new InitEvent(false));
            } else {
                notifyInfoList.add(new NotifyInfo());
                notifyV2Adapter.notifyItemInserted(notifyInfoList.size() - 1);
                notifyInfoList.remove(notifyInfoList.size() - 1);
                if (notifyInfos != null && notifyInfos.size() > 0) {
                    notifyInfoList.addAll(notifyInfos);
                } else {
                    notifyV2Adapter.setMoreDataAvailable(false);
                }
                notifyV2Adapter.notifyDataChanged();
            }
        }

    }

    private void prepareNewData() {
        notifyV2Adapter = new NotifyV2Adapter(this, notifyInfoList, false);
        notifyV2Adapter.setLoadMoreListener(new NotifyV2Adapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rcvDanhSach.post(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        if (notifyInfoList.size() % Constants.DISPLAY_RECORD_SIZE == 0) {
                            getNotifys(page);
                        }
                    }
                });
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcvDanhSach.setLayoutManager(mLayoutManager);
        rcvDanhSach.setItemAnimator(new DefaultItemAnimator());
        rcvDanhSach.setAdapter(notifyV2Adapter);
        notifyV2Adapter.notifyDataSetChanged();
        if (notifyInfoList != null && notifyInfoList.size() > 0) {
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onError(APIError apiError) {
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
    public void onCheckStoreSuccess(DetailNotifyInfo data) {
        hideProgress();

        if (data != null && data.getType() != null && !data.getType().trim().equals("")) {
            switch (data.getType()) {
                case "CHOXULY":
                    DocumentWaitingInfo itemWaiting = new DocumentWaitingInfo();
                    itemWaiting.setId(idDoc);
                    itemWaiting.setIsChuTri(data.getParamDetailNotifyInfo().getIsChuTri());
                    itemWaiting.setIsCheck(data.getParamDetailNotifyInfo().getIsCheck());
                    itemWaiting.setProcessDefinitionId(data.getParamDetailNotifyInfo().getProcessKey());
                    itemWaiting.setCongVanDenDi(data.getParamDetailNotifyInfo().getCongVanDenDi());
                    EventBus.getDefault().postSticky(itemWaiting);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(itemWaiting.getId(), Constants.DOCUMENT_WAITING, null));
                    EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemWaiting.getId(), itemWaiting.getProcessDefinitionId(), itemWaiting.getCongVanDenDi()));
                    startActivity(new Intent(this, DetailDocumentWaitingActivity.class));

                    break;
                case "TRACUU":
                    DocumentSearchInfo item = new DocumentSearchInfo();
                    item.setId(idDoc);
                    EventBus.getDefault().postSticky(item);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(item.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                    startActivity(new Intent(this, DetailDocumentNotificationActivity.class));

                    break;
                case "DAXULY":
                    DocumentProcessedInfo itemProcessed = new DocumentProcessedInfo();
                    itemProcessed.setId(idDoc);
                    itemProcessed.setIsChuTri(data.getParamDetailNotifyInfo().getIsChuTri());
                    itemProcessed.setIsCheck(data.getParamDetailNotifyInfo().getIsCheck());
                    itemProcessed.setProcessDefinitionId(data.getParamDetailNotifyInfo().getProcessKey());
                    itemProcessed.setCongVanDenDi(data.getParamDetailNotifyInfo().getCongVanDenDi());
                    EventBus.getDefault().postSticky(itemProcessed);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(itemProcessed.getId(), Constants.DOCUMENT_PROCESSED, null));
                    EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemProcessed.getId(), itemProcessed.getProcessDefinitionId(), itemProcessed.getCongVanDenDi()));
                    startActivity(new Intent(this, DetailDocumentProcessedActivity.class));

                    break;
                case "THONGBAO":
                    DocumentNotificationInfo itemNotification = new DocumentNotificationInfo();
                    itemNotification.setId(idDoc);
                    itemNotification.setIsChuTri(data.getParamDetailNotifyInfo().getIsChuTri());
                    itemNotification.setIsCheck(data.getParamDetailNotifyInfo().getIsCheck());
                    itemNotification.setProcessDefinitionId(data.getParamDetailNotifyInfo().getProcessKey());
                    itemNotification.setCongVanDenDi(data.getParamDetailNotifyInfo().getCongVanDenDi());
                    EventBus.getDefault().postSticky(itemNotification);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(itemNotification.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                    EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemNotification.getId(), itemNotification.getProcessDefinitionId(), itemNotification.getCongVanDenDi()));
                    startActivity(new Intent(this, DetailDocumentNotificationActivity.class));

                    break;
                case "WEB":
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), "Bạn vui lòng truy cập website để xử lý thông báo này", true, AlertDialogManager.INFO);
                    break;
                case "CVDUOCGIAO":
                    if (data.getParamDetailNotifyInfo() == null || data.getParamDetailNotifyInfo().getIdCongViec() == null || data.getParamDetailNotifyInfo().getNxlId() == null) {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.str_ERROR_DIALOG), true, AlertDialogManager.ERROR);
                        return;
                    }
                    EventBus.getDefault().postSticky(new WorkManageEvent(data.getParamDetailNotifyInfo().getIdCongViec()
                            , data.getParamDetailNotifyInfo().getNxlId()));
                    Intent intentcv = new Intent(this, DetailWorkManagementActivity.class);
                    intentcv.putExtra("id", data.getParamDetailNotifyInfo().getIdCongViec());
                    intentcv.putExtra("nxlid", data.getParamDetailNotifyInfo().getNxlId());
                    intentcv.putExtra("typeWork", 1);//type =1 nếu là công việc được giao
                    startActivity(intentcv);
                    break;
                case "CVDAGIAO":
                    if (data.getParamDetailNotifyInfo() == null || data.getParamDetailNotifyInfo().getIdCongViec() == null || data.getParamDetailNotifyInfo().getNxlId() == null) {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.str_ERROR_DIALOG), true, AlertDialogManager.ERROR);
                        return;
                    }
                    EventBus.getDefault().postSticky(new WorkManageEvent(data.getParamDetailNotifyInfo().getIdCongViec()
                            , data.getParamDetailNotifyInfo().getNxlId()));
                    Intent intentcvd = new Intent(this, DetailWorkManagementActivity.class);
                    intentcvd.putExtra("id", data.getParamDetailNotifyInfo().getIdCongViec());
                    intentcvd.putExtra("nxlid", data.getParamDetailNotifyInfo().getNxlId());
                    intentcvd.putExtra("typeWork", 2);//type =2 nếu là công việc đã giao
                    startActivity(intentcvd);
                    break;
                case "TTDH":
                    if (data.getParamDetailNotifyInfo().getIdThongTin() != null) {
                        Intent intentDieuHanh = new Intent(this, DetailChiDaoActivity.class);
                        intentDieuHanh.putExtra("ID_CHIDAO", data.getParamDetailNotifyInfo().getIdThongTin());
                        startActivity(intentDieuHanh);

                    }
                    break;
            }
            EventBus.getDefault().postSticky(new ReadNotifyEvent(true));
        }

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        if (connectionDetector.isConnectingToInternet()) {
            if (step == 1) {
                page = 1;
                getNotifys(page);
            } else {
                readedNotify();
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(NotifyActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    public void getDetailNotify(String id, String idDoc) {
        this.idDoc = idDoc;
        notifyPresenter.getDetailNotify(Integer.parseInt(id));
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccessException(Object object) {
        hideProgress();
    }

    @Override
    public void onExceptionError(APIError apiError) {
        hideProgress();
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

}
