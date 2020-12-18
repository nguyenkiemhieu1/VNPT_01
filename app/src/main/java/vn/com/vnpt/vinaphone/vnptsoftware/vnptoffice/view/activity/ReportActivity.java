package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReportDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReportWorkInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.report.IReportPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.report.ReportPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeReportEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IReportView;

public class ReportActivity extends BaseActivity implements IReportView, ILoginView, IExceptionErrorView {

    @BindView(R.id.tv_vanban)
    TextView tv_vanban;
    @BindView(R.id.tv_congviec) TextView tv_congviec;
    @BindView(R.id.tv_report_vanbandi) TextView tv_report_vanbandi;
    @BindView(R.id.tv_report_vanbanden) TextView tv_report_vanbanden;

    @BindView(R.id.tv_vbden_cho_xuly_label) TextView tv_vbden_cho_xuly_label;
    @BindView(R.id.tv_vbden_qua_han_label) TextView tv_vbden_qua_han_label;
    @BindView(R.id.tv_vbden_da_xuly_label) TextView tv_vbden_da_xuly_label;
    @BindView(R.id.tv_vbden_ban_hanh_label) TextView tv_vbden_ban_hanh_label;
    @BindView(R.id.tv_vbden_cho_xuly) TextView tv_vbden_cho_xuly;
    @BindView(R.id.tv_vbden_qua_han) TextView tv_vbden_qua_han;
    @BindView(R.id.tv_vbden_da_xuly) TextView tv_vbden_da_xuly;
    @BindView(R.id.tv_vbden_ban_hanh) TextView tv_vbden_ban_hanh;

    @BindView(R.id.tv_vbdi_cho_xuly_label) TextView tv_vbdi_cho_xuly_label;
    @BindView(R.id.tv_vbdi_qua_han_label) TextView tv_vbdi_qua_han_label;
    @BindView(R.id.tv_vbdi_da_xuly_label) TextView tv_vbdi_da_xuly_label;
    @BindView(R.id.tv_vbdi_ban_hanh_label) TextView tv_vbdi_ban_hanh_label;
    @BindView(R.id.tv_vbdi_cho_xuly) TextView tv_vbdi_cho_xuly;
    @BindView(R.id.tv_vbdi_qua_han) TextView tv_vbdi_qua_han;
    @BindView(R.id.tv_vbdi_da_xuly) TextView tv_vbdi_da_xuly;
    @BindView(R.id.tv_vbdi_ban_hanh) TextView tv_vbdi_ban_hanh;

    @BindView(R.id.tv_cv_chuathuchien_label) TextView tv_cv_chuathuchien_label;
    @BindView(R.id.tv_cv_chuathuchien) TextView tv_cv_chuathuchien;
    @BindView(R.id.tv_cv_dangthuchien_label) TextView tv_cv_dangthuchien_label;
    @BindView(R.id.tv_cv_dangthuchien) TextView tv_cv_dangthuchien;
    @BindView(R.id.tv_cv_dathuchien_label) TextView tv_cv_dathuchien_label;
    @BindView(R.id.tv_cv_dathuchien) TextView tv_cv_dathuchien;
    @BindView(R.id.tv_cv_quahan_label) TextView tv_cv_quahan_label;
    @BindView(R.id.tv_cv_quahan) TextView tv_cv_quahan;
    @BindView(R.id.tv_cv_dunghan_label) TextView tv_cv_dunghan_label;
    @BindView(R.id.tv_cv_dunghan) TextView tv_cv_dunghan;

    @BindView(R.id.sReport)
    Spinner sReport;
    @BindView(R.id.btnReport)
    Button btnReport;
    private Toolbar toolbar;
    private ImageView btnBack;

    private String type;
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;

    private IReportPresenter reportPresenter = new ReportPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);

    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        init();
    }

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();
        setupSpinner();
        setFont();
        setupToolbar();
        connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isConnectingToInternet()) {
            showProgress();
            getReportDoc();
            getReportWork();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setFont() {
        btnReport.setTypeface(Application.getApp().getTypeface());
        tv_congviec.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vanban.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_report_vanbanden.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_report_vanbandi.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbden_ban_hanh.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbden_cho_xuly.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbden_da_xuly.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbden_qua_han.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbdi_ban_hanh.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbdi_cho_xuly.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbdi_da_xuly.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbdi_qua_han.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_cv_chuathuchien.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_cv_dangthuchien.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_cv_dathuchien.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_cv_dunghan.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_cv_quahan.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbden_ban_hanh_label.setTypeface(Application.getApp().getTypeface());
        tv_vbden_cho_xuly_label.setTypeface(Application.getApp().getTypeface());
        tv_vbden_da_xuly_label.setTypeface(Application.getApp().getTypeface());
        tv_vbden_qua_han_label.setTypeface(Application.getApp().getTypeface());
        tv_vbdi_ban_hanh_label.setTypeface(Application.getApp().getTypeface());
        tv_vbdi_cho_xuly_label.setTypeface(Application.getApp().getTypeface());
        tv_vbdi_da_xuly_label.setTypeface(Application.getApp().getTypeface());
        tv_vbdi_qua_han_label.setTypeface(Application.getApp().getTypeface());
        tv_cv_chuathuchien_label.setTypeface(Application.getApp().getTypeface());
        tv_cv_dangthuchien_label.setTypeface(Application.getApp().getTypeface());
        tv_cv_dathuchien_label.setTypeface(Application.getApp().getTypeface());
        tv_cv_dunghan_label.setTypeface(Application.getApp().getTypeface());
        tv_cv_quahan_label.setTypeface(Application.getApp().getTypeface());
    }

    private void setupSpinner() {
        final String[] spinnerItems = getResources().getStringArray(R.array.string_array_report);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.md_black));
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.md_black));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sReport.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sReport.setSelection(0);
        sReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                type = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void displayReportDoc(ReportDocumentInfo object) {
        hideProgress();
        tv_vbden_ban_hanh.setText(object.getDenBanHanh());
        tv_vbden_cho_xuly.setText(object.getDenChoXuLy());
        tv_vbden_da_xuly.setText(object.getDenDaXuLy());
        tv_vbden_qua_han.setText(object.getDenQuaHan());
        tv_vbdi_ban_hanh.setText(object.getDiBanHanh());
        tv_vbdi_cho_xuly.setText(object.getDiChoXuLy());
        tv_vbdi_da_xuly.setText(object.getDiDaXuLy());
        tv_vbdi_qua_han.setText(object.getDiQuaHan());
    }

    private void displayReportWork(ReportWorkInfo object) {
      hideProgress();
        tv_cv_chuathuchien.setText(String.valueOf(object.getChuaThucHien()));
        tv_cv_dangthuchien.setText(String.valueOf(object.getDangThucHien()));
        tv_cv_dathuchien.setText(String.valueOf(object.getDaThucHien()));
        tv_cv_dunghan.setText(String.valueOf(object.getDungHan()));
        tv_cv_quahan.setText(String.valueOf(object.getQuaHan()));
    }

    @OnClick({R.id.btnReport})
    public void viewReport(View view) {
        if (view.getId() == R.id.btnReport) {
            if (type != null && !type.equals("0")) {
                EventBus.getDefault().postSticky(new TypeReportEvent(type));
                startActivity(new Intent(this, ReportTemplateActivity.class));
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.REPORT_TYPE_REQUIRED), true, AlertDialogManager.INFO);
            }
        }
    }

    private void getReportDoc() {
        reportPresenter.getReportDocument();
    }

    private void getReportWork() {
        Calendar now = Calendar.getInstance();
        reportPresenter.getReportWork(now.get(Calendar.MONTH) + 1);
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            getReportDoc();
            getReportWork();
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
    public void onSuccessDoc(ReportDocumentInfo object) {
        displayReportDoc(object);
    }

    @Override
    public void onSuccessWork(ReportWorkInfo object) {
        displayReportWork(object);
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
