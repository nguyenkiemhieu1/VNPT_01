package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UserInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.userinfo.IUserInfoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.userinfo.UserInfoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ContactEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IUserAvatarView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IUserInfoView;

public class ContactDetailActivity extends BaseActivity implements IUserInfoView, ILoginView, IUserAvatarView, IExceptionErrorView {

    private Toolbar toolbar;
    private ImageView btnBack;
    private ApplicationSharedPreferences appPrefs;
    private IUserInfoPresenter userInfoPresenter = new UserInfoPresenterImpl(this, this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    @BindView(R.id.avatarUser) ImageView avatarUser;
    @BindView(R.id.txtName) TextView txtName;
    @BindView(R.id.txtAddressLabel) TextView txtAddressLabel;
    @BindView(R.id.txtAddress) TextView txtAddress;
    @BindView(R.id.txtEmailLabel) TextView txtEmailLabel;
    @BindView(R.id.txtEmail) TextView txtEmail;
    @BindView(R.id.txtSexLabel) TextView txtSexLabel;
    @BindView(R.id.txtSex) TextView txtSex;
    @BindView(R.id.txtPhoneLabel) TextView txtPhoneLabel;
    @BindView(R.id.txtPhone) TextView txtPhone;
    @BindView(R.id.txtBornLabel) TextView txtBornLabel;
    @BindView(R.id.txtBorn) TextView txtBorn;
    @BindView(R.id.txtUserIdLabel) TextView txtUserIdLabel;
    @BindView(R.id.txtUserId) TextView txtUserId;
    @BindView(R.id.txtStatusLabel) TextView txtStatusLabel;
    @BindView(R.id.txtStatus) TextView txtStatus;
    @BindView(R.id.txtReligionLabel) TextView txtReligionLabel;
    @BindView(R.id.txtReligion) TextView txtReligion;
    @BindView(R.id.txtNationLabel) TextView txtNationLabel;
    @BindView(R.id.txtNation) TextView txtNation;
    @BindView(R.id.txtLevelLabel) TextView txtLevelLabel;
    @BindView(R.id.txtLevel) TextView txtLevel;
    @BindView(R.id.txtUnitLabel) TextView txtUnitLabel;
    @BindView(R.id.txtUnit) TextView txtUnit;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        setupToolbar();
        init();
        getInfo();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarUserInfo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        setTitle(getString(R.string.profile_title));
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        appPrefs = Application.getApp().getAppPrefs();
        txtName.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtAddress.setTypeface(Application.getApp().getTypeface());
        txtAddressLabel.setTypeface(Application.getApp().getTypeface());
        txtEmail.setTypeface(Application.getApp().getTypeface());
        txtEmailLabel.setTypeface(Application.getApp().getTypeface());
        txtSex.setTypeface(Application.getApp().getTypeface());
        txtSexLabel.setTypeface(Application.getApp().getTypeface());
        txtPhone.setTypeface(Application.getApp().getTypeface());
        txtPhoneLabel.setTypeface(Application.getApp().getTypeface());
        txtBorn.setTypeface(Application.getApp().getTypeface());
        txtBornLabel.setTypeface(Application.getApp().getTypeface());

        txtUserId.setTypeface(Application.getApp().getTypeface());
        txtUserIdLabel.setTypeface(Application.getApp().getTypeface());
        txtStatus.setTypeface(Application.getApp().getTypeface());
        txtStatusLabel.setTypeface(Application.getApp().getTypeface());
        txtReligion.setTypeface(Application.getApp().getTypeface());
        txtReligionLabel.setTypeface(Application.getApp().getTypeface());
        txtNation.setTypeface(Application.getApp().getTypeface());
        txtNationLabel.setTypeface(Application.getApp().getTypeface());
        txtLevel.setTypeface(Application.getApp().getTypeface());
        txtLevelLabel.setTypeface(Application.getApp().getTypeface());
        txtUnit.setTypeface(Application.getApp().getTypeface());
        txtUnitLabel.setTypeface(Application.getApp().getTypeface());
        Glide.with(this).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                .bitmapTransform(new CircleTransform(this)).into(avatarUser);
    }

    private void getInfo() {
        if (connectionDetector.isConnectingToInternet()) {
            userInfoPresenter.getUserInfo(EventBus.getDefault().getStickyEvent(ContactEvent.class).getId());
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessGetUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            txtName.setText(userInfo.getUsername());
            txtEmail.setText(userInfo.getEmail());
            txtPhone.setText(userInfo.getMobile());
            txtSex.setText(userInfo.getSexName());
            txtBorn.setText(userInfo.getDob());
            txtUserId.setText(userInfo.getUserId());
            txtNation.setText(userInfo.getDanToc());
            txtReligion.setText(userInfo.getTonGiao());
            txtLevel.setText(userInfo.getTrinhDo());
            txtAddress.setText(userInfo.getAddress());
            txtUnit.setText(userInfo.getUnitName());
            if (userInfo.getStatus() != null) {
                if (userInfo.getStatus().equals("0")) {
                    txtStatus.setText("[" + userInfo.getStatus() + "] " + getString(R.string.STATUS_CONTACT_ACTIVE_LABEL));
                }
                if (userInfo.getStatus().equals("1")) {
                    txtStatus.setText("[" + userInfo.getStatus() + "] " + getString(R.string.STATUS_CONTACT_DEACTIVE_LABEL));
                }
            } else {
                txtStatus.setText("");
            }
            userInfoPresenter.getAvatar(userInfo.getUserId());
//            if (userInfo.getAvatar() != null && !userInfo.getAvatar().trim().equals("")) {
//                Glide.with(this).load(userInfo.getAvatar()).error(R.drawable.ic_avatar)
//                        .bitmapTransform(new CircleTransform(this)).into(avatarUser);
//            } else {
//                Glide.with(this).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
//                        .bitmapTransform(new CircleTransform(this)).into(avatarUser);
//            }
        }
    }

    @Override
    public void onErrorGetUserInfo(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() != Constants.RESPONE_UNAUTHEN) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.GET_INFO_TITLE_ERROR),
                    apiError.getMessage() != null && !apiError.getMessage().trim().equals("") ? apiError.getMessage().trim() : "Có lỗi xảy ra!\nVui lòng thử lại sau!", true, AlertDialogManager.ERROR);
        } else {
            if (connectionDetector.isConnectingToInternet()) {
                appPrefs = Application.getApp().getAppPrefs();
                loginPresenter.getUserLoginPresenter(appPrefs.getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
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
    public void onSuccessLogin(LoginInfo loginInfo) {
        appPrefs = Application.getApp().getAppPrefs();
        appPrefs.setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            userInfoPresenter.getUserInfo();
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
    public void onErrorAvatar(APIError apiError) {
        sendExceptionError(apiError);
        Glide.with(this).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                .bitmapTransform(new CircleTransform(this)).into(avatarUser);
    }

    @Override
    public void onSuccessAvatar(byte[] bitmap) {
        Glide.with(this).load(bitmap).error(R.drawable.ic_avatar)
                .bitmapTransform(new CircleTransform(this)).into(avatarUser);
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
