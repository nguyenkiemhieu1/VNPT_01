package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;
import com.github.lguipeng.library.animcheckbox.AnimCheckBox;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BuildConfig;
import butterknife.OnClick;
import io.realm.Realm;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConfigNotification;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.NotificationUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.RealmDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.builder.ContactBuilder;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Contact;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CheckVersionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.LoginRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ContactInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentNotificationInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentProcessedInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.contact.ContactPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.contact.IContactPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.notify.INotifyPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.notify.NotifyPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.version.IVersionPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.version.VersionPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.StepPre;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.syncevent.IContactSync;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ICheckVersionView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IReadedNotifyView;

public class LoginActivity extends BaseActivity implements ILoginView, Validator.ValidationListener, IContactSync,
        IReadedNotifyView, ICheckVersionView, IExceptionErrorView {

    private ApplicationSharedPreferences appPrefs;
    private boolean isValidateLogin;
    private Validator validator;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private IContactPresenter contactPresenter = new ContactPresenterImpl(this);
    private INotifyPresenter notifyPresenter = new NotifyPresenterImpl(this);
    private Realm realm;

    @BindView(R.id.tv_language)
    TextView tv_language;
    @BindView(R.id.image_language)
    ImageView image_language;
    @BindView(R.id.ckGhiNhoTaiKhoan)
    AnimCheckBox ckGhiNhoTaiKhoan;
    @NotEmpty(messageResId = R.string.USERNAME_REQUIRED)
    @Length(max = 100, messageResId = R.string.USERNAME_INVALID_LENGTH)
    @BindView(R.id.txtUserName)
    //@Pattern(regex = StringDef.USERNAME_PATTERN, messageResId = R.string.USERNAME_INVALID_STRENGTH)
            EditText txtUsername;
    @NotEmpty(messageResId = R.string.PASSWORD_REQUIRED)
    @Length(min = 8, messageResId = R.string.PASSWORD_INVALID_LENGTH)
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.etUserLayout)
    TextInputLayout etUserLayout;
    @BindView(R.id.etPasswordLayout)
    TextInputLayout etPasswordLayout;
    @BindView(R.id.txtGhiNhoTaiKhoan)
    TextView txtGhiNhoTaiKhoan;
    @BindView(R.id.btn_login)
    Button btnDangNhap;
    @BindView(R.id.txtname2)
    TextView txtname2;
    @BindView(R.id.txtname1)
    TextView txtname1;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    @BindView(R.id.layoutDisplay)
    ConstraintLayout layoutDisplay;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private IVersionPresenter versionPresenter = new VersionPresenterImpl(this);
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    //chuyenvien1phonga
    //Vnpt@123
    String language = "VI";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPrefs = Application.getApp().getAppPrefs();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_login);
        try {
            if (appPrefs.isSaveAccount() && appPrefs.getAccount() != null) {
                txtUsername.setText(appPrefs.getAccount().getUsername());
                txtPassword.setText(appPrefs.getAccount().getPassword());
            }
        } catch (Exception ex) {
        }
        if (appPrefs.isSaveAccount()) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.loginPresenter(appPrefs.getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
        validator = new Validator(this);
        validator.setValidationListener(this);
        addControls();

        //thay đổi ngôn ngữ
        checkNgonNgu();

        String nameDevice = getDeviceName();
        appPrefs.setDeviceName(nameDevice);
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + " " + model;
    }

    @SuppressLint("NewApi")
    private void checkNgonNgu() {

        String lang_local = Application.localeManager.getLanguage();
        if (lang_local != null) {
            switch (lang_local) {
                case "vi":
                    tv_language.setText(getString(R.string.str_vn));
                    image_language.setImageDrawable(getResources().getDrawable(R.drawable.icon_language_vn));
                    break;
                case "en":
                    tv_language.setText(getString(R.string.str_en));
                    image_language.setImageDrawable(getResources().getDrawable(R.drawable.icon_language_en));
                    break;
                case "lo":
                    tv_language.setText(getString(R.string.str_ls));
                    image_language.setImageDrawable(getResources().getDrawable(R.drawable.icon_language_ls));
                    break;
                default:
                    tv_language.setText(getString(R.string.str_vn));
                    image_language.setImageDrawable(getResources().getDrawable(R.drawable.icon_language_vn));
                    break;
            }
        } else {
            tv_language.setText(getString(R.string.str_vn));
            image_language.setImageDrawable(getResources().getDrawable(R.drawable.icon_language_vn));
        }


    }

    private void dialogLanguage() {
        String lang_local = Application.localeManager.getLanguage();
        int position = 0;
        if (lang_local != null) {
            switch (lang_local) {
                case "vi":
                    position = 0;
                    break;
                case "en":
                    position = 1;
                    break;
                case "lo":
                    position = 2;
                    break;
                default:
                    position = 0;
                    break;
            }
        } else {
            position = 0;
        }

        final String[] list = {getResources().getString(R.string.str_vn), getResources().getString(R.string.str_en), getResources().getString(R.string.str_ls)};
        AlertDialog.Builder aler = new AlertDialog.Builder(this);
        aler.setTitle(R.string.str_language);
        aler.setSingleChoiceItems(list, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        language = "VI";
                        setLanguage("vi");
                        break;
                    case 1:
                        language = "EN";
                        setLanguage("en");
                        break;
                    case 2:
                        language = "LA";
                        setLanguage("lo");
                        break;
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = aler.create();
        dialog.show();
    }

    private void setLanguage(String lang) {
        Application.localeManager.setNewLocale(this, lang);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
//        Intent i = new Intent(this, LoginActivity.class);
//        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }


    private void setFont() {
        txtname1.setTypeface(Application.getApp().getTypeface());
        txtname2.setTypeface(Application.getApp().getTypeface());
        etUserLayout.setTypeface(Application.getApp().getTypeface());
        etPasswordLayout.setTypeface(Application.getApp().getTypeface());
        txtUsername.setTypeface(Application.getApp().getTypeface());
        txtPassword.setTypeface(Application.getApp().getTypeface());
        txtGhiNhoTaiKhoan.setTypeface(Application.getApp().getTypeface());
        btnDangNhap.setTypeface(Application.getApp().getTypeface());
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

    private void addControls() {
        setFont();
        ckGhiNhoTaiKhoan.setChecked(appPrefs.isSaveAccount());
        try {
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // checking for type intent filter
                    if (intent.getAction().equals(ConfigNotification.REGISTRATION_COMPLETE)) {
                        // gcm successfully registered
                        // now subscribe to `global` topic to receive app wide notifications
                        FirebaseMessaging.getInstance().subscribeToTopic(ConfigNotification.TOPIC_GLOBAL);
                    } else if (intent.getAction().equals(ConfigNotification.PUSH_NOTIFICATION)) {
                        // new push notification is received
                    }
                }
            };
        } catch (Exception ex) {

        }
    }

    private boolean notification = false;

    private void handleNotification() {
        if (getIntent().getExtras() != null) {
            String data = getIntent().getStringExtra(ConfigNotification.NOTIFICATION_DATA);
            String type = null;
            String link = null;
            String id = null;
            if (data != null) {
                try {
                    JSONObject json = new JSONObject(data);
                    type = json.getString(ConfigNotification.NOTIFICATION_TYPE);
                    link = json.getString(ConfigNotification.NOTIFICATION_LINK);
                    id = json.getString(ConfigNotification.NOTIFICATION_IOFFICE_ID);
                } catch (Exception ex) {
                }
            } else {
                type = getIntent().getStringExtra(ConfigNotification.NOTIFICATION_TYPE);
                link = getIntent().getStringExtra(ConfigNotification.NOTIFICATION_LINK);
                id = getIntent().getStringExtra(ConfigNotification.NOTIFICATION_IOFFICE_ID);
            }
            if (type != null && !type.equals("") && link != null && !link.equals("") && id != null && !id.equals("")) {
                notification = true;
                redirectDisplay(type, link, id);
            }
        }
    }

    private String idDoc;

    private void redirectDisplay(String type, String link, String id) {
        try {
            if (type != null && Constants.TYPE_NOTIFY_DOCUMENT.contains(type)) {
                notification = true;
                idDoc = link.split("\\|")[1];
                notifyPresenter.getDetailNotify(Integer.parseInt(id));
            } else {
                if (type != null && Constants.TYPE_NOTIFY_SCHEDULE.contains(type)) {
                    try {
                        notification = true;
                        WeekViewEvent weekViewEvent = new WeekViewEvent();
                        weekViewEvent.setId(Long.parseLong(link));
                        weekViewEvent.setType(Constants.SCHEDULE_MEETING);
                        EventBus.getDefault().postSticky(weekViewEvent);
                        EventBus.getDefault().postSticky(new StepPre(Constants.SCHEDULE_LIST));
                        startActivity(new Intent(this, DetailScheduleActivity.class));
                        finish();
                    } catch (Exception ex) {
                    }
                } else {
                    if (type != null && Constants.TYPE_NOTIFY_CHIDAO.contains(type)) {
                        notification = true;
                        String[] links = link.split("\\|");
                        String idTT = null;
                        if (links.length > 1) {
                            idTT = link.split("\\|")[1];
                        } else {
                            idTT = link.split("\\|")[0];
                        }
                        Intent intent = new Intent(getApplicationContext(), DetailChiDaoActivity.class);
                        intent.putExtra(Constants.CONSTANTS_ID_CHIDAO, idTT);
                        startActivity(intent);
                        finish();
                    }
                    if (type != null && Constants.TYPE_NOTIFY_WORK.contains(type)) {
                        startActivity(new Intent(this, DetailWorkActivity.class));
                        finish();
                    }
                    if (type != null && Constants.TYPE_NOTIFY_PROFILE.contains(type)) {
                        startActivity(new Intent(this, ProfileWorkActivity.class));
                        finish();
                    }
                    if (type != null && Constants.TYPE_NOTIFY_MAIL.contains(type)) {
                        startActivity(new Intent(this, LetterActivity.class));
                        finish();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        notifyPresenter.readedNotifys(new ReadedNotifyRequest(id));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String data = getIntent().getStringExtra(ConfigNotification.NOTIFICATION_DATA);
        String type = null;
        String link = null;
        String id = null;
        if (data != null) {
            try {
                JSONObject json = new JSONObject(data);
                type = json.getString(ConfigNotification.NOTIFICATION_TYPE);
                link = json.getString(ConfigNotification.NOTIFICATION_LINK);
                id = json.getString(ConfigNotification.NOTIFICATION_IOFFICE_ID);
            } catch (Exception ex) {
            }
        } else {
            type = getIntent().getStringExtra(ConfigNotification.NOTIFICATION_TYPE);
            link = getIntent().getStringExtra(ConfigNotification.NOTIFICATION_LINK);
            id = getIntent().getStringExtra(ConfigNotification.NOTIFICATION_IOFFICE_ID);
        }
        if (type != null && !type.equals("") && link != null && !link.equals("") && id != null && !id.equals("")) {
            redirectDisplay(type, link, id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            // register GCM registration complete receiver
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(ConfigNotification.REGISTRATION_COMPLETE));
            // register new push message receiver
            // by doing this, the activity will be notified each time a new message arrives
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(ConfigNotification.PUSH_NOTIFICATION));
            // clear the notification area when the app is opened
            NotificationUtils.clearNotifications(getApplicationContext());
        } catch (Exception ex) {

        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {

        appPrefs.setLogined(true);
        appPrefs.setAccount(new LoginRequest(
                txtUsername.getText().toString(),
                txtPassword.getText().toString(),
                appPrefs.getFirebaseToken(),
                "ANDROID",
                appPrefs.getDeviceName(),
                language
        ));
        appPrefs.setToken(loginInfo.getToken());
        appPrefs.setUnitUser(loginInfo.getUnitId());
        appPrefs.setSaveAccount(ckGhiNhoTaiKhoan.isChecked());
        appPrefs.setAccountLogin(loginInfo);
        EventBus.getDefault().postSticky(loginInfo);
        if (connectionDetector.isConnectingToInternet()) {
            versionPresenter.checkVersion(new CheckVersionRequest("android", BuildConfig.VERSION_NAME));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void synchronousContact() {
        if (connectionDetector.isConnectingToInternet()) {
            contactPresenter.getContacts();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private boolean checkTimeGetContact() {
        String timeSync = appPrefs.getTimeGetContact();
        SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_CHECK);
        String now = sdf.format(new Date());
        if (!timeSync.equals(now)) {
            return true;
        }
        return false;
    }

    @Override
    public void onSuccessSync(List<ContactInfo> contactInfos) {
        realm = RealmDao.with(this).getRealm();
        clearContacts();
        if (contactInfos != null && contactInfos.size() > 0) {
//            SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_CHECK);
//            String now = sdf.format(new Date());
//            appPrefs.setTimeGetContact(now);
            ContactBuilder contactBuilder = new ContactBuilder(this);
            saveContacts(contactBuilder.convertFromContactInfos(contactInfos));
        } else {
            navigateToHome();
        }
    }

    @Override
    public void onErrorSync(APIError apiError) {
        sendExceptionError(apiError);
        navigateToHome();
    }

    @Override
    public void showProgressSync() {
    }

    @Override
    public void hideProgressSync() {
        hideProgressDialog();
    }

    private void clearContacts() {
        realm.beginTransaction();
        realm.delete(Contact.class);
        realm.commitTransaction();
    }

    private void saveContacts(final List<Contact> contacts) {
        realm.beginTransaction();
        realm.copyToRealm(contacts);
        realm.commitTransaction();
        navigateToHome();
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        if (!isFinishing()) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.LOGIN_TITLE_ERROR), getString(R.string.LOGIN_INVALID), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @OnClick({R.id.btn_login, R.id.tv_language, R.id.image_language})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (connectionDetector.isConnectingToInternet()) {
                    validator.validate();
                    if (isValidateLogin) {
                        loginPresenter.loginPresenter(new LoginRequest(txtUsername.getText().toString(),
                                        txtPassword.getText().toString(),
                                        appPrefs.getFirebaseToken(),
                                        "ANDROID",
                                        appPrefs.getDeviceName(),
                                        language
                                )
                        );
                    }
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
                break;
            case R.id.tv_language:
                dialogLanguage();
                break;
            case R.id.image_language:
                dialogLanguage();
                break;

        }
    }

    private void navigateToHome() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onValidationSucceeded() {
        isValidateLogin = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        isValidateLogin = false;
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.LOGIN_TITLE_ERROR), message, true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccess(boolean isRead) {

    }

    @Override
    public void onSuccess() {
        handleNotification();

        if (!notification) {
            navigateToHome();
//            if (appPrefs.getTimeGetContact() == null || checkTimeGetContact()) {
//                synchronousContact();
//            } else {
//                navigateToHome();
//            }
        }
    }

    @Override
    public void onError(APIError apiError) {
        if (!isFinishing()) {
            sendExceptionError(apiError);
            AlertDialogManager.showAlertDialog(this, getString(R.string.LOGIN_TITLE_ERROR), apiError.getMessage() != null && !apiError.getMessage().trim().equals("") ? apiError.getMessage().trim() : "Có lỗi xảy ra!\nVui lòng thử lại sau!", true, AlertDialogManager.ERROR);
            handleNotification();
            if (!notification) {
                navigateToHome();
            }
        }
    }

    @Override
    public void onCheckStoreSuccess(DetailNotifyInfo data) {
        if (data != null && data.getType() != null && !data.getType().trim().equals("")) {
            switch (data.getType()) {
                case Constants.CONSTANTS_CHOXULY:
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
                    finish();
                    break;
                case Constants.CONSTANTS_TRACUU:
                    DocumentSearchInfo item = new DocumentSearchInfo();
                    item.setId(idDoc);
                    EventBus.getDefault().postSticky(item);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(item.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                    startActivity(new Intent(this, DetailDocumentNotificationActivity.class));
                    finish();
                    break;
                case Constants.CONSTANTS_DAXULY:
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
                    finish();
                    break;
                case Constants.CONSTANTS_THONGBAO:
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
                    finish();
                    break;
                case Constants.CONSTANTS_WEB:
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.str_web), true, AlertDialogManager.INFO);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AlertDialogManager.dismisAlertDialog();
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
