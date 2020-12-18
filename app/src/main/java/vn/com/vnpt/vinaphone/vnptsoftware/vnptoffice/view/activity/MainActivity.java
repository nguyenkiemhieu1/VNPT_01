package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import me.leolin.shortcutbadger.ShortcutBadger;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.ExpandableListAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.RealmDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.builder.ContactBuilder;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Contact;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.NumberCountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ContactInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.NotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.NotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.contact.ContactPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.contact.IContactPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.countdocument.CountDocumentPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.countdocument.ICountDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.logout.ILogoutPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.logout.LogoutPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.notify.INotifyPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.notify.NotifyPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.userinfo.IUserInfoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.userinfo.UserInfoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ChiDaotEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.CountNumberDenDiEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.EventCheckSMS;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.EventTypeQLVB;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListFileCongViecEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.NumberbadgeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.OnBackEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReadNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.StepPre;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TaiFileNewSendEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UpdateSuccessEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.ChiDaoFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.ContactFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.DocumentMarkFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.DocumentNotificationFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.DocumentWaitingFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.HomeFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.ScheduleBossFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.ScheduleDepartFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.ScheduleRegisterWeekFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.ScheduleWeekFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.WorkflowManagementFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.DefaultHome;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.ExpandedMenuModel;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.MyExceptionHandler;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.syncevent.IContactSync;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ICountDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILogoutView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.INotifyView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IUserAvatarView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IUserSwitchView;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_DEN_CHO_XU_LY;
import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_DEN_DA_XU_LY;
import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_DI_CHO_XU_LY;
import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_DI_DA_XU_LY;
import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_DI_DA_PHAT_HANH;
import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_VAN_BAN_DEN;
import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_VAN_BAN_DI;
import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_XEM_DE_BIET;
import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_QUAN_LY_CONG_VIEC;
import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_TTDH_NHAN;
import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.CONSTANTS_TTDH_GUI;

public class MainActivity extends BaseActivity implements ILogoutView, ILoginView, INotifyView, IUserAvatarView,
        IUserSwitchView, IContactSync, ICountDocumentView, IExceptionErrorView {

    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    private ILogoutPresenter logoutPresenter = new LogoutPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private INotifyPresenter notifyPresenter = new NotifyPresenterImpl(this);
    private IUserInfoPresenter userInfoPresenter = new UserInfoPresenterImpl(this, this);
    private ICountDocumentPresenter iCountDocumentPresenter = new CountDocumentPresenterImpl(this);

    private Realm realm;
    private Toolbar toolbar;
    private ImageView avatarUser;
    private TextView txtName;
    private TextView txtDonVi;
    private TextView tvTitle;
    private TextView txtTongThongBao;
    private ImageView layoutThongBao;
    private Handler mHandler = new Handler();
    //    private Timer mTimer = null;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private DrawerLayout mDrawerLayout;
    private ExpandableListView expandableList;
    private List<ExpandedMenuModel> listDataHeader;
    private HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listDataChild;
    private ImageView btnLeftMenu;
    private NavigationView navigationView;
    private List<LoginInfo.UserSwitch> listUserSwitch = new ArrayList<>();
    private ImageView ivSwitchUser;
    private LinearLayout ll_header_menu;
    private String idSwithUser = "";
    private IContactPresenter contactPresenter = new ContactPresenterImpl(this);
    private LoginInfo loginInfo;

    private List<NumberCountDocument.DataNumber> dataCountDoc;
    private ExpandableListAdapter mMenuAdapter;

    private int numberVbDen = 0;
    private int numberVbDi = 0;
    private int numberVbXemDeBiet = 0;
    private int numberQLCV = 0;
    private int numberTTDH = 0;
    private boolean doubleBackToExitPressedOnce = false;
    private int numberNotify = 0;
    private boolean checkOpenSchedules = false;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this, LoginActivity.class));

        init();

        EventBus.getDefault().postSticky(new ListFileCongViecEvent(null));
        EventBus.getDefault().postSticky(new UpdateSuccessEvent(0));
        EventBus.getDefault().postSticky(new TaiFileNewSendEvent("false"));
        EventBus.getDefault().postSticky(new ReadNotifyEvent(false));

        NumberbadgeEvent eventBus = EventBus.getDefault().getStickyEvent(NumberbadgeEvent.class);
        if (eventBus == null) {
            getCountNotification();
        }

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

    private void init() {
        progressDialog = new ProgressDialog(this);
        appPrefs = Application.getApp().getAppPrefs();
        setupToolbar();
        setupLeftMenu();
        laucherContent();
        //startSync();
        btnLeftMenu = (ImageView) findViewById(R.id.btnLeftMenu);
        btnLeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
                iCountDocumentPresenter.getCountDocument();

                View view = MainActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        idSwithUser = "";
        EventBus.getDefault().removeStickyEvent(EventCheckSMS.class);
    }

    @Override
    public void onSuccessLogout() {
        Application.getApp().getAppPrefs().removeAll();
        EventBus.getDefault().removeStickyEvent(NumberbadgeEvent.class);
        ShortcutBadger.removeCount(this);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onErrorLogout(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() != Constants.RESPONE_UNAUTHEN) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.LOGOUT_TITLE_ERROR), apiError.getMessage() != null && !apiError.getMessage().trim().equals("") ? apiError.getMessage().trim() : getString(R.string.str_error), true, AlertDialogManager.ERROR);
        } else {
            appPrefs = Application.getApp().getAppPrefs();
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(appPrefs.getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void showLogoutProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.LOGOUT_REQUEST));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLogoutProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        appPrefs.setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            logoutPresenter.logout();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        //clearDocumentWaiting();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSuccessSwitchUser(LoginInfo userInfo) {
        if (userInfo != null) {
            loginInfo = userInfo;
            appPrefs.setToken(loginInfo.getToken());
            appPrefs.setAccountLogin(loginInfo);

            EventBus.getDefault().postSticky(loginInfo);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    @Override
    public void onErrorSwitchUser(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), apiError.getMessage(), true, AlertDialogManager.ERROR);
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

    private void selectItemChild(int groupPosition, int childPostion) {
        Fragment fragment = null;
        String title = null;
        switch (groupPosition) {
            case Constants.VANBANDEN:
                ExpandedMenuModel menuModel = (ExpandedMenuModel) (expandableList.getExpandableListAdapter()
                        .getChild(groupPosition, childPostion));

                switch (menuModel.getKho()) {
                    case Constants.CONSTANTS_DEN_CHO_XU_LY:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(menuModel.getKho());
                        title = menuModel.getIconName();
                        EventBus.getDefault().postSticky(new EventTypeQLVB(0));
                        break;
                    case Constants.CONSTANTS_DEN_DA_XU_LY:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(menuModel.getKho());
                        title = menuModel.getIconName();
                        EventBus.getDefault().postSticky(new EventTypeQLVB(0));
                        break;
                    default:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(menuModel.getKho());
                        title = menuModel.getIconName();
                        break;

                }
                break;
            case Constants.VANBANDI:
                ExpandedMenuModel menuModel2 = (ExpandedMenuModel) (expandableList.getExpandableListAdapter()
                        .getChild(groupPosition, childPostion));
                switch (menuModel2.getKho()) {
                    case Constants.CONSTANTS_DI_CHO_XU_LY:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(menuModel2.getKho());
                        title = menuModel2.getIconName();
                        EventBus.getDefault().postSticky(new EventTypeQLVB(0));
                        break;
                    case Constants.CONSTANTS_DI_DA_PHAT_HANH:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(menuModel2.getKho());
                        title = menuModel2.getIconName();
                        EventBus.getDefault().postSticky(new EventTypeQLVB(0));
                        break;
                    case Constants.CONSTANTS_DI_DA_XU_LY:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(menuModel2.getKho());
                        title = menuModel2.getIconName();
                        EventBus.getDefault().postSticky(new EventTypeQLVB(0));
                        break;
                }
                break;
            case Constants.QUANLYLICH:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (childPostion) {
                    case Constants.QUANLYLICH_LICHTUAN:
                        fragment = new ScheduleWeekFragment();
                        break;
                    case Constants.QUANLYLICH_DANGKYLICHTUAN:
                        fragment = new ScheduleRegisterWeekFragment().newInstance();
                        break;
                }
                break;
            case Constants.QUANLYCONGVIEC:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (childPostion) {
                    case Constants.QUANLYCONGVIEC_CONGVIECDUOCGIAO:
                        fragment = new WorkflowManagementFragment().newInstance(1);
                        title = getString(R.string.tv_congviec_duocgiao);
                        break;
                    case Constants.QUANLYCONGVIEC_CONGVIECDAGIAO:
                        fragment = new WorkflowManagementFragment().newInstance(2);
                        title = getString(R.string.tv_congviec_dagiao);
                        break;
                    case Constants.QUANLYCONGVIEC_TAOCONGVIECMOI:
                        startActivityForResult(new Intent(this, TaoCongViecMoiActivity.class), 500);
                        break;
                }
                break;
            case Constants.SETTING:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (childPostion) {
                    case Constants.SETTING_PROFILE:
                        startActivity(new Intent(this, UserInfoActivity.class));
                        break;
                    case Constants.SETTING_CHANGE_PASSWORD:
                        startActivity(new Intent(this, ChangePasswordActivity.class));
                        break;
                    case Constants.SETTING_DEFAULT_HOME:
                        startActivity(new Intent(this, DefaultHomeActivity.class));
                        break;
                }
                break;
            case Constants.THONGTINDIEUHANH:
                hideNotify();
                switch (childPostion) {
                    case Constants.CHIDAO_NHAN:
                        EventBus.getDefault().postSticky(new ChiDaotEvent(0));
                        fragment = new ChiDaoFragment();
                        title = getString(R.string.CHIDAO_NHAN_MENU);
                        break;
                    case Constants.CHIDAO_GUI:
                        EventBus.getDefault().postSticky(new ChiDaotEvent(1));
                        fragment = new ChiDaoFragment();
                        title = getString(R.string.CHIDAO_GUI_MENU);
                        break;
                }
                break;
        }
        if (title != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            setTitle(title);
        }
    }

    private void selectItemGroup(int position) {
        checkOpenSchedules = false;
        Fragment fragment = null;
        String title = null;
        switch (position) {
            case Constants.HOME:
                fragment = new HomeFragment();
                title = getString(R.string.home);
                break;
            case Constants.VANBANDANHDAU:
                EventBus.getDefault().postSticky(new EventCheckSMS(true));
                fragment = new DocumentMarkFragment();
                title = getString(R.string.DOC_MARK_MENU);
                EventBus.getDefault().postSticky(new EventTypeQLVB(3));
                break;
            case Constants.VANBANXEMDEBIET:
                EventBus.getDefault().postSticky(new EventCheckSMS(false));
                fragment = new DocumentNotificationFragment();
                title = getString(R.string.DOC_NOTIFICATION_MENU);
                EventBus.getDefault().postSticky(new EventTypeQLVB(2));
                break;
            case Constants.TRACUUVANBAN:
                EventBus.getDefault().postSticky(new EventCheckSMS(true));
                mDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(this, DocumentSearchActivity.class));
                break;
            case Constants.DANHBA:
                fragment = new ContactFragment();
                title = getString(R.string.CONTACT_MENU);
                break;
            case Constants.LICHDONVI:
                fragment = new ScheduleBossFragment();
                title = getString(R.string.SCHEDULE_MENU);
                break;
            case Constants.LICHPHONGBAN:
                fragment = new ScheduleDepartFragment();
                title = getString(R.string.SCHEDULE_DEPART_MENU);
                break;
//             ẩn báo cáo thống kê
//            case Constants.REPORT:
//                startActivity(new Intent(this, ReportActivity.class));
//                break;
            case Constants.LOGOUT:
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                final AlertDialog alertDialog = builder.create();
                alertDialog.setTitle(this.getString(R.string.TITLE_CONFIRM));
                alertDialog.setMessage(this.getString(R.string.CONFIRM_LOGOUT));
                alertDialog.setIcon(R.drawable.ic_confirm);
                alertDialog.setButton2(this.getString(R.string.ACCEPT_BUTTON), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        realm = RealmDao.with(getApplication()).getRealm();
                        if (connectionDetector.isConnectingToInternet()) {
                            logoutPresenter.logout();
                        } else {
                            AlertDialogManager.showAlertDialog(MainActivity.this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                        }
                    }
                });
                alertDialog.setButton(this.getString(R.string.CLOSE_BUTTON), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
        }
        if (title != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            setTitle(title);
        }
    }

    private void getCountNotification() {
        if (connectionDetector.isConnectingToInternet()) {
            ListNotifyRequest listNotifyRequest = new ListNotifyRequest("1", "10");
            notifyPresenter.getNotifys(listNotifyRequest);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }


    private void laucherContent() {
        OnBackEvent onBackEvent = EventBus.getDefault().getStickyEvent(OnBackEvent.class);
        if (onBackEvent != null) {
            EventBus.getDefault().removeStickyEvent(OnBackEvent.class);
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (onBackEvent.getName().equals(Constants.HOME_DEN_CHO_XU_LY)) {
                EventBus.getDefault().postSticky(new EventTypeQLVB(0));
                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentWaitingFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[2]);
            }
        } else {
            String defaultHome = getDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername());
            if (defaultHome != null && !defaultHome.trim().equals("")) {
                goDefault(defaultHome);
            } else {
                goHome();
            }
            StepPre stepPre = EventBus.getDefault().getStickyEvent(StepPre.class);
            if (stepPre != null && stepPre.getCall().equals("Notify")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.FUNCTION_NO_SUPPORT_INFO), true, AlertDialogManager.INFO);
            }
        }
    }

    private String getDefaultHome(String userId) {
        DefaultHome results = RealmDao.with(Application.getApp()).findByKey(DefaultHome.class, userId, "userId");
        if (results != null && results.getName() != null && !results.getName().equals("")) {
            return results.getName();
        } else {
            return null;
        }
    }

    private void goDefault(String defaultHome) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (defaultHome) {
            case Constants.HOME_TRANGCHU:
                goHome();
                break;
            case Constants.HOME_TRANGTINTUC:
                goHome();
                break;
            case Constants.HOME_DEN_CHO_XU_LY:
                EventBus.getDefault().postSticky(new EventTypeQLVB(0));
                fragmentManager.beginTransaction().replace(R.id.content_frame, DocumentWaitingFragment.newInstance(Constants.CONSTANTS_DEN_CHO_XU_LY)
                        , new DocumentWaitingFragment().getClass().toString()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[1]);
                break;
            case Constants.HOME_DEN_DA_XU_LY:
                fragmentManager.beginTransaction().replace(R.id.content_frame, DocumentWaitingFragment.newInstance(Constants.HOME_DEN_DA_XU_LY)
                        , new DocumentWaitingFragment().getClass().toString()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[2]);
                break;
            case Constants.HOME_DI_CHO_XU_LY:
                fragmentManager.beginTransaction().replace(R.id.content_frame, DocumentWaitingFragment.newInstance(Constants.HOME_DI_CHO_XU_LY)
                        , new DocumentWaitingFragment().getClass().toString()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[3]);
                break;
            case Constants.HOME_DI_DA_PHAT_HANH:
                fragmentManager.beginTransaction().replace(R.id.content_frame, DocumentWaitingFragment.newInstance(Constants.HOME_DI_DA_PHAT_HANH)
                        , new DocumentWaitingFragment().getClass().toString()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[4]);
                break;
            case Constants.HOME_DI_DA_XU_LY:
                fragmentManager.beginTransaction().replace(R.id.content_frame, DocumentWaitingFragment.newInstance(Constants.HOME_DI_DA_XU_LY)
                        , new DocumentWaitingFragment().getClass().toString()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[5]);
                break;

            case Constants.HOME_VANBAN_XEMDEBIET:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentNotificationFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[6]);
                break;
//            case Constants.HOME_VANBANDENHAN:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentExpriedFragment()).commit();
//                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[4]);
//                break;
//            case Constants.HOME_VANBANTHONGBAO:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentNotificationFragment()).commit();
//                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[4]);
//                break;
            case Constants.HOME_VANBANDANHDAU:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentMarkFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[7]);
                break;
            case Constants.HOME_DANHBA:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ContactFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[8]);
                break;
            case Constants.HOME_QUANLYLICHHOP:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ScheduleBossFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[9]);
                break;
            case Constants.HOME_QUANLYLICHHOPPHONGBAN:
                //fragmentManager.beginTransaction().replace(R.id.content_frame, new ScheduleBossFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[10]);
                break;
//            case Constants.HOME_BAOCAO:
//                goHome();
//                break;
//            case Constants.HOME_BAOCAOTHONGKE:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new ReportFragment()).commit();
//                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[7]);
//                break;
            case Constants.HOME_THONGTINCHIDAO:
                hideNotify();
                EventBus.getDefault().postSticky(new ChiDaotEvent(0));
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ChiDaoFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[10]);
                break;
        }
    }

    public void goHome() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtTongThongBao = (TextView) toolbar.findViewById(R.id.tong_thong_bao);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        txtTongThongBao.setVisibility(View.GONE);
        txtTongThongBao.setText("");
        txtTongThongBao.setTypeface(Application.getApp().getTypeface());
        tvTitle.setTypeface(Application.getApp().getTypeface());
        layoutThongBao = toolbar.findViewById(R.id.image_thongbao);
        layoutThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOpenSchedules) {

                } else {
                    startActivity(new Intent(MainActivity.this, NotifyActivity.class));
                }

                //finish();
            }
        });
        setTitle(getString(R.string.home));


    }

    private void DialogTypeViewSchedule() {

    }


    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void hideNotify() {
        layoutThongBao.setVisibility(View.INVISIBLE);
        txtTongThongBao.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("NewApi")
    public void showNotify(boolean checkOpenSchedule) {
        checkOpenSchedules = checkOpenSchedule;
        layoutThongBao.setVisibility(View.VISIBLE);
        if (checkOpenSchedule) {
            layoutThongBao.setImageDrawable(getDrawable(R.drawable.icon_changviewschedule));
            txtTongThongBao.setVisibility(View.INVISIBLE);
        } else {
            layoutThongBao.setImageDrawable(getDrawable(R.drawable.thongbao_512x512));
            if (numberNotify > 0) {
                txtTongThongBao.setVisibility(View.VISIBLE);
            } else {
                txtTongThongBao.setVisibility(View.INVISIBLE);
            }
        }


    }

    private void setupLeftMenu() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        createHeader();
        createMenu();
        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);
        expandableList.setAdapter(mMenuAdapter);
        mMenuAdapter.notifyDataSetChanged();
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                selectItemChild(listDataHeader.get(i).getId(), i1);
                return false;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                selectItemGroup(listDataHeader.get(i).getId());
                return false;
            }
        });
    }

    private void createHeader() {
        loginInfo = appPrefs.getAccountLogin();
        View hView = navigationView.getHeaderView(0);
        txtName = (TextView) hView.findViewById(R.id.txtName);
        txtDonVi = (TextView) hView.findViewById(R.id.txtDonVi);
        avatarUser = (ImageView) hView.findViewById(R.id.avatarUser);
        ivSwitchUser = (ImageView) hView.findViewById(R.id.ivSwitchUser);
        ll_header_menu = (LinearLayout) hView.findViewById(R.id.ll_header_menu);
        txtName.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtDonVi.setTypeface(Application.getApp().getTypeface());
        txtName.setText(loginInfo.getFullName());
        txtDonVi.setText(loginInfo.getUnitName());
        userInfoPresenter.getAvatar(loginInfo.getUsername());
        if (loginInfo.getListSwitchUser() != null && loginInfo.getListSwitchUser().size() > 0) {
            listUserSwitch = loginInfo.getListSwitchUser();
            ivSwitchUser.setVisibility(View.VISIBLE);
        } else {
            ivSwitchUser.setVisibility(View.GONE);
        }
        ivSwitchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogSwitchUser();

            }
        });
//        if (loginInfo.getAvatar() == null) {
//            Glide.with(this).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar).bitmapTransform(new CircleTransform(this)).into(avatarUser);
//        } else {
//            Glide.with(this).load(loginInfo.getAvatar()).error(R.drawable.ic_avatar).bitmapTransform(new CircleTransform(this)).into(avatarUser);
//        }
    }

    private void createMenu() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<ExpandedMenuModel>>();

        // Adding header data

        listDataHeader.add(new ExpandedMenuModel(false, Constants.HOME, R.drawable.icon_home, getString(R.string.HOME_MENU), "")); //0
        listDataHeader.add(new ExpandedMenuModel(true, Constants.VANBANDEN, R.drawable.icon_folder_document, getString(R.string.VANBANDEN_MENU), ""));//1
        listDataHeader.add(new ExpandedMenuModel(true, Constants.VANBANDI, R.drawable.icon_folder_document, getString(R.string.VANBANDI_MENU), ""));//2
        listDataHeader.add(new ExpandedMenuModel(false, Constants.VANBANDANHDAU, R.drawable.icon_document_star, getString(R.string.DOC_MARK_MENU), ""));//3
        listDataHeader.add(new ExpandedMenuModel(false, Constants.VANBANXEMDEBIET, R.drawable.icon_document_view, getString(R.string.DOC_NOTIFICATION_MENU), ""));//4
        listDataHeader.add(new ExpandedMenuModel(false, Constants.TRACUUVANBAN, R.drawable.icon_document_search, getString(R.string.SEARCH_DOCUMENT_LABEL), ""));//5
        listDataHeader.add(new ExpandedMenuModel(false, Constants.DANHBA, R.drawable.icon_contact, getString(R.string.CONTACT_MENU), ""));//6

//        if (loginInfo.getRoles() != null && loginInfo.getConfigs() != null) {
//            if ((loginInfo.getConfigs().getLICH_TUAN_MOBILE_DISPLAY() != null && loginInfo.getConfigs().getLICH_TUAN_MOBILE_DISPLAY().equals("1")) || loginInfo.getRoles().contains("DANG_KY_LICH_TUAN")) {
//                listDataHeader.add(new ExpandedMenuModel(true, Constants.QUANLYLICH, R.drawable.icon_schedule_manager, getString(R.string.SCHEDULE_MANAGER_MENU), ""));//7
//            }
//            if (loginInfo.getConfigs().getLICH_DON_VI_MOBILE_DISPLAY() != null && loginInfo.getConfigs().getLICH_DON_VI_MOBILE_DISPLAY().equals("1")) {
//                listDataHeader.add(new ExpandedMenuModel(false, Constants.LICHDONVI, R.drawable.icon_calendar, getString(R.string.SCHEDULE_MENU), ""));//8
//            }
//        }
        listDataHeader.add(new ExpandedMenuModel(false, Constants.LICHDONVI, R.drawable.icon_calendar, getString(R.string.SCHEDULE_MENU), ""));//8
        if (loginInfo.getCalendarDisplay().equals("true")) {
            listDataHeader.add(new ExpandedMenuModel(false, Constants.LICHPHONGBAN, R.drawable.icon_calendar, getString(R.string.SCHEDULE_DEPART_MENU), ""));//9
        }
        listDataHeader.add(new ExpandedMenuModel(true, Constants.QUANLYCONGVIEC, R.drawable.icon_task_manager, getString(R.string.WORK_MENU), ""));//10
        listDataHeader.add(new ExpandedMenuModel(true, Constants.THONGTINDIEUHANH, R.drawable.icon_document_information, getString(R.string.CHIDAO_MENU), ""));//11
        //listDataHeader.add(new ExpandedMenuModel(false ,Constants.REPORT,R.drawable.ic_report, getString(R.string.REPORT_MENU),""));//12- ẩn menu
        listDataHeader.add(new ExpandedMenuModel(true, Constants.SETTING, R.drawable.icon_setting, getString(R.string.SETTING_MENU), ""));//13
        listDataHeader.add(new ExpandedMenuModel(false, Constants.LOGOUT, R.drawable.icon_logout, getString(R.string.LOG_OUT_MENU), ""));//14

        // Adding child data
        List<ExpandedMenuModel> heading = new ArrayList<ExpandedMenuModel>();
        List<LoginInfo.Menu.Child> childList = new ArrayList<LoginInfo.Menu.Child>();
        if (loginInfo != null && loginInfo.getMenu() != null && loginInfo.getMenu().size() > 0) {
            for (int i = 0; i < loginInfo.getMenu().size(); i++) {
                heading = new ArrayList<ExpandedMenuModel>();
                for (int j = 0; j < loginInfo.getMenu().get(i).getChild().size(); j++) {
                    LoginInfo.Menu.Child child = loginInfo.getMenu().get(i).getChild().get(j);
                    heading.add(new ExpandedMenuModel(getIdDocType(loginInfo.getMenu().get(i).getName()), getDrawDocType(child.getName()), child.getName(), getNumberDoc(child.getParam()), child.getParam()));
                }
                addChildMenu(listDataHeader, heading);
            }
        }

//        if (loginInfo.getRoles() != null && loginInfo.getConfigs() != null) {
//            if ((loginInfo.getConfigs().getLICH_TUAN_MOBILE_DISPLAY() != null && loginInfo.getConfigs().getLICH_TUAN_MOBILE_DISPLAY().equals("1")) || loginInfo.getRoles().contains("DANG_KY_LICH_TUAN")) {
//                heading = new ArrayList<ExpandedMenuModel>();
//                if (loginInfo.getConfigs().getLICH_TUAN_MOBILE_DISPLAY() != null && loginInfo.getConfigs().getLICH_TUAN_MOBILE_DISPLAY().equals("1")) {
//                    heading.add(new ExpandedMenuModel(Constants.QUANLYLICH, R.drawable.icon_calendar, getString(R.string.SCHEDULE_WEEK), getNumberDoc(getString(R.string.SCHEDULE_WEEK)), ""));
//                    if (loginInfo.getRoles() != null && loginInfo.getRoles().contains("DANG_KY_LICH_TUAN")) {
//                        heading.add(new ExpandedMenuModel(Constants.QUANLYLICH, R.drawable.icon_calendar, getString(R.string.SCHEDULE_REGISTER), getNumberDoc(getString(R.string.SCHEDULE_REGISTER)), ""));
//                    }
//                }
//                addChildMenu(listDataHeader, heading);
//            }
//        }

        heading = new ArrayList<ExpandedMenuModel>();
        heading.add(new ExpandedMenuModel(Constants.QUANLYCONGVIEC, R.drawable.icon_document_mission, getString(R.string.tv_congviec_duocgiao), getNumberDoc(getString(R.string.tv_congviec_duocgiao)), ""));
        heading.add(new ExpandedMenuModel(Constants.QUANLYCONGVIEC, R.drawable.icon_task_manager, getString(R.string.tv_congviec_dagiao), getNumberDoc(getString(R.string.tv_congviec_dagiao)), ""));
        heading.add(new ExpandedMenuModel(Constants.QUANLYCONGVIEC, R.drawable.icon_create_task, getString(R.string.tv_congviec_taomoi), getNumberDoc(getString(R.string.tv_congviec_taomoi)), ""));
        addChildMenu(listDataHeader, heading);


        heading = new ArrayList<ExpandedMenuModel>();
        heading.add(new ExpandedMenuModel(Constants.THONGTINDIEUHANH, R.drawable.icon_document_receive, getString(R.string.CHIDAO_NHAN_MENU), getNumberDoc(CONSTANTS_TTDH_NHAN), ""));
        heading.add(new ExpandedMenuModel(Constants.THONGTINDIEUHANH, R.drawable.icon_document_send, getString(R.string.CHIDAO_GUI_MENU), getNumberDoc(CONSTANTS_TTDH_GUI), ""));
        addChildMenu(listDataHeader, heading);

        heading = new ArrayList<ExpandedMenuModel>();
        heading.add(new ExpandedMenuModel(Constants.SETTING, R.drawable.icon_profile, getString(R.string.PROFILE_MENU), ""));
        heading.add(new ExpandedMenuModel(Constants.SETTING, R.drawable.icon_password, getString(R.string.CHANGE_PASSWORD_MENU), ""));
        heading.add(new ExpandedMenuModel(Constants.SETTING, R.drawable.icon_setting_default, getString(R.string.DEFAULT_HOME_MENU), ""));
        addChildMenu(listDataHeader, heading);
    }

    private void addChildMenu(List<ExpandedMenuModel> listDataHeader, List<ExpandedMenuModel> heading) {
        for (int i = 0; i < listDataHeader.size(); i++) {
            for (int j = 0; j < heading.size(); j++) {
                if (listDataHeader.get(i).getId() == (heading.get(j).getId()))
                    listDataChild.put(listDataHeader.get(i), heading);
            }
        }
    }

    private int getIdDocType(String typeVanBan) {
        switch (typeVanBan) {
            case CONSTANTS_VAN_BAN_DEN:
                return Constants.VANBANDEN;
            case CONSTANTS_VAN_BAN_DI:
                return Constants.VANBANDI;
        }
        return -1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

    @Override
    public void onSuccess(Object object) {
        NotifyRespone notifyRespone = (NotifyRespone) object;
        if (notifyRespone != null) {
            List<NotifyInfo> notifyInfos = ConvertUtils.fromJSONList(notifyRespone.getData().getNotifys(), NotifyInfo.class);

            if (notifyInfos != null && notifyInfos.size() > 0) {
                txtTongThongBao.setVisibility(View.VISIBLE);
                txtTongThongBao.setText(notifyRespone.getData().getCount());
                numberNotify = Integer.parseInt(notifyRespone.getData().getCount());

                int badgeCount = Integer.parseInt(notifyRespone.getData().getCount());
                ShortcutBadger.applyCount(getApplicationContext(), badgeCount);
            } else {
                txtTongThongBao.setVisibility(View.GONE);
                txtTongThongBao.setText("");
                numberNotify = 0;
            }
        }

    }

    @Override
    public void onSuccessCountDoc(NumberCountDocument countDocument) {
        if (countDocument != null && countDocument.getStatus().getCode().equalsIgnoreCase(Constants.RESPONE_SUCCESS)) {
            dataCountDoc = countDocument.getData();
            numberVbDen = 0;
            numberVbDi = 0;
            numberQLCV = 0;
            numberTTDH = 0;
            for (NumberCountDocument.DataNumber dataNumber : dataCountDoc) {
                if (dataNumber.getName().equals(CONSTANTS_DEN_CHO_XU_LY) || dataNumber.getName().equals(CONSTANTS_DEN_DA_XU_LY)) {
                    numberVbDen = numberVbDen + dataNumber.getNumber();
                }
                if (dataNumber.getName().equals(CONSTANTS_DI_CHO_XU_LY) || dataNumber.getName().equals(CONSTANTS_DI_DA_XU_LY) | dataNumber.getName().equals(CONSTANTS_DI_DA_PHAT_HANH)) {
                    numberVbDi = numberVbDi + dataNumber.getNumber();
                }
                if (dataNumber.getName().equals(CONSTANTS_XEM_DE_BIET)) {
                    numberVbXemDeBiet = dataNumber.getNumber();
                }
                if (dataNumber.getName().equals(Constants.CONSTANTS_CONG_VIEC_DUOC_GIAO)) {
                    numberQLCV = dataNumber.getNumber();
                }
                if (dataNumber.getName().equals(CONSTANTS_TTDH_NHAN)) {
                    numberTTDH = dataNumber.getNumber();
                }
            }
            EventBus.getDefault().postSticky(new CountNumberDenDiEvent(numberVbDen, numberVbDi, numberVbXemDeBiet, numberQLCV, numberTTDH));
            createMenu();
            mMenuAdapter.updateDataEx(listDataHeader, listDataChild);
        }
    }

    @Override
    public void onError(APIError apiError) {
        sendExceptionError(apiError);
    }

    @Override
    public void onErrorAvatar(APIError apiError) {
        sendExceptionError(apiError);
        Glide.with(this).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar).bitmapTransform(new CircleTransform(this)).into(avatarUser);
    }

    @Override
    public void onSuccessAvatar(byte[] bitmap) {
        Glide.with(this).load(bitmap).error(R.drawable.ic_avatar).bitmapTransform(new CircleTransform(this)).into(avatarUser);
    }

    private void createDialogSwitchUser() {
        final List<String> labels = new ArrayList<String>();
        for (int i = 0; i < listUserSwitch.size(); i++) {
            labels.add(listUserSwitch.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.weight_table_menu, R.id.textViewTableMenuItem, labels);
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(ll_header_menu);
        listPopupWindow.setContentWidth(measureContentWidth(adapter));
        listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setHorizontalOffset(5); // margin left
        listPopupWindow.setVerticalOffset(-100);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idSwithUser = listUserSwitch.get(position).getUserid();
                userInfoPresenter.getListSwitchUser(idSwithUser);
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }

    private int measureContentWidth(ListAdapter listAdapter) {
        ViewGroup mMeasureParent = null;
        int maxWidth = 0;
        View itemView = null;
        int itemType = 0;

        final ListAdapter adapter = listAdapter;
        final int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            final int positionType = adapter.getItemViewType(i);
            if (positionType != itemType) {
                itemType = positionType;
                itemView = null;
            }

            if (mMeasureParent == null) {
                mMeasureParent = new FrameLayout(this);
            }

            itemView = adapter.getView(i, itemView, mMeasureParent);
            itemView.measure(widthMeasureSpec, heightMeasureSpec);

            final int itemWidth = itemView.getMeasuredWidth();

            if (itemWidth > maxWidth) {
                maxWidth = itemWidth;
            }
        }

        return maxWidth;
    }

    private void synchronousContact() {
        if (connectionDetector.isConnectingToInternet()) {
            contactPresenter.getContacts();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR),
                    getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onSuccessSync(List<ContactInfo> contactInfos) {
        realm = RealmDao.with(this).getRealm();
        appPrefs.removeTimeGetContact();
        clearContacts();
        ContactBuilder contactBuilder = new ContactBuilder(this);
        saveContacts(contactBuilder.convertFromContactInfos(contactInfos));
    }


    @Override
    public void onErrorSync(APIError apiError) {
        sendExceptionError(apiError);
        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                apiError.getMessage() != null ? apiError.getMessage() : getString(R.string.str_loidongbo), true, AlertDialogManager.ERROR);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showProgressSync() {
        if (MainActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgressSync() {
        if (MainActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ReadNotifyEvent readNotifyEvent) {
        if (readNotifyEvent != null && readNotifyEvent.getReadNotify()) {
            getCountNotification();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(NumberbadgeEvent event) {
        if (event != null && event.getNumber() != null) {
            txtTongThongBao.setVisibility(View.VISIBLE);
            txtTongThongBao.setText(String.valueOf(event.getNumber()));
            numberNotify = event.getNumber();
        } else {
            txtTongThongBao.setVisibility(View.GONE);
            numberNotify = 0;
        }
    }

    private int getNumberDoc(String typeDoc) {
        if (dataCountDoc != null && dataCountDoc.size() > 0) {
            for (NumberCountDocument.DataNumber dataNumber : dataCountDoc) {
                if (dataNumber.name.equalsIgnoreCase(typeDoc)) {
                    return dataNumber.number;
                }
            }
        }
        return 0;
    }

    private int getDrawDocType(String typeVanBan) {
        switch (typeVanBan) {
            case Constants.CONSTANTS_VAN_BAN_DEN_CHO_XU_LY:
            case Constants.CONSTANTS_VAN_BAN_DEN_DA_XU_LY:
            case Constants.CONSTANTS_VAN_BAN_CHO_TGD_XU_LY:
                return R.drawable.icon_document;
            //case CONSTANTS_VAN_BAN_DEN:
            //  case Constants.CONSTANTS_VAN_BAN_DI:
            case Constants.CONSTANTS_VAN_BAN_CHO_PHE_DUYET:
                return R.drawable.icon_document;
            case Constants.CONSTANTS_VAN_BAN_CO_KY_SO:
            case Constants.CONSTANTS_VAN_BAN_DI_CHO_XLPH:
            case Constants.CONSTANTS_VAN_BAN_DI_CHO_XLC:
            case Constants.CONSTANTS_VAN_BAN_DEN_CHO_XLPH:
            case Constants.CONSTANTS_VAN_BAN_DEN_CHO_XLC:
            case Constants.CONSTANTS_VAN_BAN_DANH_DAU:
            case Constants.CONSTANTS_VAN_BAN_DI_KHONG_KY_SO:
                return R.drawable.icon_document_star;
            case Constants.CONSTANTS_VAN_BAN_DA_XU_LY:
                return R.drawable.icon_document;
            case Constants.CONSTANTS_VAN_BAN_XEM_DE_BIET:
                return R.drawable.icon_document_mission;
            default:
                return R.drawable.icon_document;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500)//back từ tạo mới công việc
        {
            if (data != null && data.getStringExtra("CREATE_WORK_SUCCESS") != null && data.getStringExtra("CREATE_WORK_SUCCESS").equals("TRUE")) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new WorkflowManagementFragment().newInstance(1)).commit();
                setTitle(getString(R.string.tv_congviec_duocgiao));
            }
        }
    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.getClass().getName().toString().equals(new HomeFragment().getClass().getName().toString())) {
                    if (fragment.isVisible()) {
                        if (doubleBackToExitPressedOnce) {
                            System.exit(0);
                        }
                        DoubleClickBack();
                    } else {
                        goHome();
                        setTitle(getResources().getString(R.string.home));
                    }
                } else {
                    goHome();
                    setTitle(getResources().getString(R.string.home));
                }

            }
        }

    }

    //ấn 2 lần để thoát ứng dụng
    private void DoubleClickBack() {
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Ấn Lần nữa để thoát ứng dụng !", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
