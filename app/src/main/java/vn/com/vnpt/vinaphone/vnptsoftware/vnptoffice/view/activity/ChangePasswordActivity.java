package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangePasswordRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.changepassword.ChangePasswordPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.changepassword.IChangePasswordPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IChangePasswordView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class ChangePasswordActivity extends BaseActivity implements ILoginView, IChangePasswordView, IExceptionErrorView {

    private ApplicationSharedPreferences appPrefs;
    private Toolbar toolbar;
    private IChangePasswordPresenter changePasswordPresenter = new ChangePasswordPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.txtOldPassword)
    TextView txtOldPassword;
    @BindView(R.id.txtNewPassword)
    TextView txtNewPassword;
    @BindView(R.id.txtConfirmPassword)
    TextView txtConfirmPassword;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.edtOldPassword)
    EditText edtOldPassword;
    @BindView(R.id.edtNewPassword)
    EditText edtNewPassword;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    @BindView(R.id.layoutDisplay)
    ConstraintLayout layoutDisplay;

    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        appPrefs = Application.getApp().getAppPrefs();
        setupToolbar();
        init();
    }

    @OnClick({R.id.btnUpdate})
    public void clickUpdate(View view) {
        if (edtOldPassword.getText().toString().trim().length() == 0) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), getString(R.string.OLD_PASSWORD_REQUIRED), true, AlertDialogManager.ERROR);
            return;
        }
        if (edtOldPassword.getText().toString().trim().length() < 8) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), getString(R.string.PASSWORD_INVALID_LENGTH), true, AlertDialogManager.ERROR);
            return;
        }
        if (edtNewPassword.getText().toString().trim().length() == 0) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), getString(R.string.NEW_PASSWORD_REQUIRED), true, AlertDialogManager.ERROR);
            return;
        }
        if (edtNewPassword.getText().toString().trim().length() < 8) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), getString(R.string.PASSWORD_INVALID_LENGTH), true, AlertDialogManager.ERROR);
            return;
        }

        java.util.regex.Pattern patt = java.util.regex.Pattern.compile(StringDef.PASSWORD_PATTERN);
        Matcher matcher = patt.matcher(edtNewPassword.getText().toString());
        matcher.matches();
        if (!matcher.matches()) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), getString(R.string.PASSWORD_INVALID_STRENGTH), true, AlertDialogManager.ERROR);
            return;
        }
        if (edtConfirmPassword.getText().toString().trim().length() == 0) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), getString(R.string.CONFIRM_PASSWORD_REQUIRED), true, AlertDialogManager.ERROR);
            return;
        }
        if (!edtConfirmPassword.getText().toString().equals(edtNewPassword.getText().toString())) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), getString(R.string.CONFIRM_PASSWORD_MISMACTH), true, AlertDialogManager.ERROR);
            return;
        }

        if (edtNewPassword.getText().toString().equals(edtOldPassword.getText().toString())) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), getString(R.string.DUPLICATE_PASSWORD_ERROR), true, AlertDialogManager.ERROR);
        } else {
            if (view.getId() == R.id.btnUpdate) {
                if (connectionDetector.isConnectingToInternet()) {
                    changePasswordPresenter.changePasswordPresenter(new ChangePasswordRequest(edtOldPassword.getText().toString(), edtNewPassword.getText().toString()));
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarChangePassword);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.CHANGE_PASSWORD_MENU));
    }

    private void init() {
        txtOldPassword.setTypeface(Application.getApp().getTypeface());
        txtNewPassword.setTypeface(Application.getApp().getTypeface());
        txtConfirmPassword.setTypeface(Application.getApp().getTypeface());
        edtOldPassword.setTypeface(Application.getApp().getTypeface());
        edtNewPassword.setTypeface(Application.getApp().getTypeface());
        edtConfirmPassword.setTypeface(Application.getApp().getTypeface());
        btnUpdate.setTypeface(Application.getApp().getTypeface());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });
    }

    @Override
    public void onSuccess() {
        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_SUCCESS), getString(R.string.CHANGE_PASSWORD_MESSAGE_SUCCESS), true, AlertDialogManager.SUCCESS);
    }

    @Override
    public void onError(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(appPrefs.getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), apiError.getMessage(), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void showChangePasswordProgress() {
        showProgressDialog();
    }

    @Override
    public void hideChangePasswordProgress() {
        hideProgressDialog();
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        appPrefs.setToken(loginInfo.getToken());
            if (connectionDetector.isConnectingToInternet()) {
                changePasswordPresenter.changePasswordPresenter(new ChangePasswordRequest(edtOldPassword.getText().toString(), edtNewPassword.getText().toString()));
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        appPrefs.removeAll();
        startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
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
