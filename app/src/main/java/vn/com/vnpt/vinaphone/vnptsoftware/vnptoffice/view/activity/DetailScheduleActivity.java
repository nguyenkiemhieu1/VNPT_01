package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.AttachFileMeetingAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.BussinessInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.MeetingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.notify.INotifyPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.notify.NotifyPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule.ISchedulePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule.SchedulePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.LichEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IReadedNotifyView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleDetailView;

public class DetailScheduleActivity extends BaseActivity implements IScheduleDetailView, ILoginView, IReadedNotifyView, IExceptionErrorView {

    @BindView(R.id.chuTri)
    TextView chuTri;
    @BindView(R.id.thamGia)
    TextView thamGia;
    @BindView(R.id.phongHop)
    TextView phongHop;
    @BindView(R.id.thoiGian)
    TextView thoiGian;
    @BindView(R.id.tieuDe)
    TextView tieuDe;
    @BindView(R.id.noiDung)
    TextView noiDung;
    @BindView(R.id.ghiChu)
    TextView ghiChu;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.dong)
    TextView dong;
    @BindView(R.id.ln_chutri)
    LinearLayout layoutChutri;
    @BindView(R.id.ln_thamgia)
    LinearLayout layoutThamgia;
    @BindView(R.id.ln_diadiem)
    LinearLayout layoutDiadiem;
    @BindView(R.id.ln_thoigian)
    LinearLayout layoutThoigian;
    @BindView(R.id.ln_tieude)
    LinearLayout layoutTieude;
    @BindView(R.id.ln_noidung)
    LinearLayout layoutNoidung;
    @BindView(R.id.ln_ghichu)
    LinearLayout layoutGhichu;
    @BindView(R.id.ln_btndong)
    LinearLayout layoutBtn;

    @BindView(R.id.layoutFileDK)
    LinearLayout layoutFileDK;
    @BindView(R.id.layout_file_attach)
    LinearLayout layout_file_attach;


    private Toolbar toolbar;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ISchedulePresenter schedulePresenter = new SchedulePresenterImpl(this);
    private WeekViewEvent event;
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;
    private INotifyPresenter notifyPresenter = new NotifyPresenterImpl(this);
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private ApplicationSharedPreferences appPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);
        connectionDetector = new ConnectionDetector(this);
        progressDialog = new ProgressDialog(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        appPrefs = Application.getApp().getAppPrefs();

        getDetail();
    }

    private void getDetail() {
        if (connectionDetector.isConnectingToInternet()) {
            LichEvent event = EventBus.getDefault().getStickyEvent(LichEvent.class);
            if (event.getType().equals("1")) {
                schedulePresenter.getDetailBussiness(Integer.parseInt(event.getId()));
            } else {
                schedulePresenter.getDetailMeeting(Integer.parseInt(event.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }



    @Override
    public void onSuccess(Object schedule) {
        if (schedule instanceof MeetingInfo) {
            MeetingInfo meetingInfo = (MeetingInfo) schedule;
            chuTri.setText(meetingInfo.getChuTri());
            thamGia.setText(meetingInfo.getThanhPhan());
            phongHop.setText(meetingInfo.getTenPhongHop());
            if (meetingInfo.getEndStart() != null && meetingInfo.getEndStart().isEmpty() == false && !meetingInfo.getEndStart().equals("23:59")) {
                thoiGian.setText(meetingInfo.getTimeStart() + " - " + meetingInfo.getEndStart());

            } else {
                thoiGian.setText(meetingInfo.getTimeStart() + " - " + "......");
            }

            tieuDe.setText(meetingInfo.getTitle());
            noiDung.setText(meetingInfo.getContent());
            ghiChu.setText(meetingInfo.getNote());

            if (meetingInfo.getFiles() != null && meetingInfo.getFiles().size() > 0) {
                layoutFileDK.setVisibility(View.VISIBLE);
                AttachFileMeetingAdapter attachFileAdapter = new AttachFileMeetingAdapter(this, R.layout.item_file_attach_list, meetingInfo.getFiles());
                int adapterCount = attachFileAdapter.getCount();
                for (int i = 0; i < adapterCount; i++) {
                    View item = attachFileAdapter.getView(i, null, null);
                    layout_file_attach.addView(item);
                }
            } else {
                layoutFileDK.setVisibility(View.GONE);
            }
        }
        if (schedule instanceof BussinessInfo) {
            BussinessInfo bussinessInfo = (BussinessInfo) schedule;
            layoutFileDK.setVisibility(View.GONE);
            layoutChutri.setVisibility(View.GONE);
            layoutBtn.setVisibility(View.GONE);
            thamGia.setText(bussinessInfo.getThanhPhan());
            phongHop.setText(bussinessInfo.getPosition());
            if (bussinessInfo.getEndTime() != null && bussinessInfo.getEndTime().isEmpty() == false && !bussinessInfo.getEndTime().equals("23:59")) {
                thoiGian.setText(bussinessInfo.getStartTime() + " - " + bussinessInfo.getEndTime());
            } else {
                thoiGian.setText(bussinessInfo.getStartTime() + " - " + "......");
            }
            tieuDe.setText(bussinessInfo.getTitle());
            noiDung.setText(bussinessInfo.getContent());
            ghiChu.setText(bussinessInfo.getNote());

        }
    }


    @Override
    public void onSuccess(boolean isRead) {

    }

    @Override
    public void onError(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onCheckStoreSuccess(DetailNotifyInfo data) {

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            LichEvent event = EventBus.getDefault().getStickyEvent(LichEvent.class);
            schedulePresenter.getDetailMeeting(Integer.parseInt(event.getId()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
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
       showProgressDialog();
    }

    @Override
    public void hideProgress() {
      hideProgressDialog();
    }

    @Override
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }

    private void sendExceptionError(APIError apiError){
        ExceptionRequest exceptionRequest = new ExceptionRequest();
        LoginInfo eventbus = EventBus.getDefault().getStickyEvent(LoginInfo.class);
        if(eventbus != null){
            exceptionRequest.setUserId(eventbus.getUsername());
        } else {
            exceptionRequest.setUserId("");
        }
        exceptionRequest.setDevice(appPrefs.getDeviceName());
        ExceptionCallAPIEvent error = EventBus.getDefault().getStickyEvent(ExceptionCallAPIEvent.class);
        if(error != null){
            exceptionRequest.setFunction(error.getUrlAPI());
        } else {
            exceptionRequest.setFunction("");
        }
        exceptionRequest.setException(apiError.getMessage());
        iExceptionErrorPresenter.sendExceptionError(exceptionRequest);
    }
}
