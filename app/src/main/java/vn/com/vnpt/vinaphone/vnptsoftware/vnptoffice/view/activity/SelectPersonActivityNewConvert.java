package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.core.util.Function;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import okhttp3.MultipartBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SelectProcessAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SelectProcessV2Adapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SelectSendNotifyAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Event;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SearchPersonRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.JobPositionInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonGroupChangeDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonOrUnitExpectedInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonProcessInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeChangeDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.changedocument.ChangeDocumentPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.changedocument.IChangeDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FinishEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonProcessCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonSendNotifyCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonSendNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocNotificationEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocWaitingtEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.RemoveSelectPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SelectGroupPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SelectGroupSendDocEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TaiFileNewSendEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonProcessCheck;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonSendNotifyCheck;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeViewFactory;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeViewPersonSendXemDBFactory;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IChangeDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IFilterPersonView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ISelectPersonView;

import static android.telephony.PhoneNumberUtils.compare;


public class SelectPersonActivityNewConvert extends BaseActivity implements IChangeDocumentView, ISelectPersonView, ILoginView, IFilterPersonView, IExceptionErrorView {

    @BindView(R.id.layout_donvi_lt)
    LinearLayout layout_donvi_lt;
    @BindView(R.id.layout_send_lienthong)
    LinearLayout layout_send_lienthong;
    @BindView(R.id.btnMorongXL)
    TextView btnMorongXL;
    @BindView(R.id.btnThuGonXL)
    LinearLayout btnThuGonXL;
    @BindView(R.id.btnMorongLT)
    TextView btnMorongLT;
    @BindView(R.id.btnThuGonLT)
    TextView btnThuGonLT;
    @BindView(R.id.tv_nodata_process_send)
    TextView tvNodataProcessSend;
    @BindView(R.id.recycler_view_process)
    RecyclerView recyclerviewProcess;
    @BindView(R.id.layout_process_send)
    LinearLayout layoutProcessSend;
    @BindView(R.id.layout_donvi_xemdb)
    LinearLayout layoutDonviXemdb;
    @BindView(R.id.layout_send_xemdb)
    LinearLayout layoutSendXemdb;
    @BindView(R.id.btnSelectDV)
    Button btnSelectDV;
    @BindView(R.id.btnSelectCN)
    Button btnSelectCN;
    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnSelect;
    private TextView tvTitle;
    private IChangeDocumentPresenter changeDocumentPresenter = new ChangeDocumentPresenterImpl(this, this, this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private String jobSelected = "";
    private String unitSelected = "";
    @BindView(R.id.layout_person)
    LinearLayout layout_person;
    @BindView(R.id.layout_process)
    LinearLayout layout_process;
    @BindView(R.id.layout_send_notify)
    LinearLayout layout_send_notify;
    @BindView(R.id.layout_donvi)
    LinearLayout layout_donvi;
    @BindView(R.id.tv_hoten)
    TextView tv_hoten;
    @BindView(R.id.tv_load_data)
    TextView tvLoadData;
    @BindView(R.id.tv_xuly_chinh)
    TextView tv_xuly_chinh;
    @BindView(R.id.tv_dong_xuly)
    TextView tv_dong_xuly;
    @BindView(R.id.tv_hoten_donvi)
    TextView tv_hoten_donvi;
    @BindView(R.id.tv_xuly_chinh_donvi)
    TextView tv_xuly_chinh_donvi;
    @BindView(R.id.layoutFilter)
    LinearLayout layoutFilter;
    @BindView(R.id.sPosition)
    Spinner sPosition;
    @BindView(R.id.sUnit)
    Spinner sUnit;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.layoutDisplay)
    LinearLayout layoutDisplay;
    @BindView(R.id.layoutDisplay1)
    LinearLayout layoutDisplay1;
    RecyclerView.LayoutManager layoutManager;
    private boolean isExpandLienThong;
    private boolean isExpandProcess;
    private boolean isLienThong;
    private String type;

    private ViewGroup viewGroup;
    private ViewGroup viewGroupLienThong;
    private TreeNode root;
    private TreeView treeView;
    private TreeNode rootLienThong;
    private TreeView treeViewLienThong;
    private TypePersonEvent typePersonEvent;
    public static Activity activityThis;
    private boolean getLTAction = false;
    private List<PersonProcessInfo> processPersonInfoList = new ArrayList<>();
    private List<PersonOrUnitExpectedInfo> personOrUnitExpectedList = new ArrayList<>();
    private ApplicationSharedPreferences appPrefs;
    private SelectProcessAdapter selectProcessAdapter;
    private int DocId = 0;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private TypeChangeDocumentRequest typeChangeDocumentRequest;
    private TypeChangeDocument typeChangeDocument;
    private TypeChangeEvent typeChangeEvent;
    private SelectProcessV2Adapter selectProcessV2Adapter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person_new_convert);
        ButterKnife.bind(this);
        activityThis = this;
        init();
        getPersons();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void init() {
        if (getIntent() != null) {
            DocId = getIntent().getIntExtra("DOCID", 0);
        }
        appPrefs = Application.getApp().getAppPrefs();
        typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
        typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
        typeChangeEvent = EventBus.getDefault().getStickyEvent(TypeChangeEvent.class);
        root = TreeNode.root();
        rootLienThong = TreeNode.root();
        viewGroup = (LinearLayout) findViewById(R.id.layout_person);
        viewGroupLienThong = (LinearLayout) findViewById(R.id.layout_donvi_lt);
        isExpandProcess = true;
        isExpandLienThong = false;
        isLienThong = false;
        layout_person.setVisibility(View.VISIBLE);
        layout_donvi_lt.setVisibility(View.GONE);
        btnThuGonLT.setVisibility(View.VISIBLE);
        btnMorongLT.setVisibility(View.GONE);
        btnThuGonXL.setVisibility(View.VISIBLE);
        btnMorongXL.setVisibility(View.GONE);
        setupToolbar();
        connectionDetector = new ConnectionDetector(this);
        tv_hoten.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_hoten_donvi.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_xuly_chinh.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_xuly_chinh_donvi.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_dong_xuly.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
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
        layoutDisplay1.setOnTouchListener(new View.OnTouchListener() {
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
        txtName.addTextChangedListener(
                new TextWatcher() {
                    private Timer timer = new Timer();

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        View view = getCurrentFocus();
                                        if (view != null) {
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        }
                                        if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                                            changeDocumentPresenter.getPersonSend(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                                        } else {
                                            tvLoadData.setVisibility(View.VISIBLE);
                                            changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                                        }
                                    }
                                });
                            }
                        }, Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarSelectPerson);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        setTitle(getString(R.string.SELECT_PERSON));
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(Application.getApp().getTypeface());
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnSelect = (ImageView) toolbar.findViewById(R.id.btnSelect);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
                EventBus.getDefault().removeStickyEvent(TypePersonEvent.class);
                onBackPressed();
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPersonSendocument();
            }
        });
    }

    private void dataPersonSendocument() {
        boolean isSelected = true;
        boolean isSelectedXLC = false;
        boolean isSelectedLT = true;
        boolean isSelectedXLCLT = false;
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent != null) {
            listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
        }
        List<Person> personList;
        if (typePersonEvent != null) {
            if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_SEND)
                    || typePersonEvent.getType().equals(Constants.TYPE_PERSON_NOTIFY)
                    || typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)
                    || typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                switch (typePersonEvent.getType()) {
                    case Constants.TYPE_PERSON_SEND:
                        personList = send_notify_person();
                        if (personList != null && personList.size() > 0) {
                            for (Person person : personList) {
                                if (person.isXlc()) {
                                    isSelectedXLC = true;
                                    break;
                                }
                            }
                            listPersonEvent.setPersonSendList(personList);
                        } else {
                            isSelected = false;
                        }
                        break;
                    case Constants.TYPE_PERSON_NOTIFY:
                        personList = listPersonEvent.getPersonNotifyList();
                        personList = send_notify_person();
                        if (personList != null && personList.size() > 0) {
                            for (Person person : personList) {
                                if (person.isXlc()) {
                                    isSelectedXLC = true;
                                    break;
                                }
                            }
                            listPersonEvent.setPersonNotifyList(personList);
                        } else {
                            isSelected = false;
                        }
                        break;
                    case Constants.TYPE_SEND_PERSON_PROCESS:
                        personList = DeleteItemDuplicate(sendDataPersonSelected(treeView));
                        if (personList != null && personList.size() > 0) {
                            isSelectedXLC = true;
                            listPersonEvent.setPersonProcessList(personList);
                        } else {
                            isSelected = false;
                        }
                        if (isLienThong) {
                            personList = DeleteItemDuplicate(sendDataPersonSelected(treeViewLienThong));
                            if (personList != null && personList.size() > 0) {
                                isSelectedXLCLT = true;
                                listPersonEvent.setPersonLienThongList(personList);
                            } else {
                                isSelectedLT = false;
                            }
                        }
                        break;
                    case Constants.TYPE_PERSON_DIRECT:
                        personList = DeleteItemDuplicate(sendDataPersonSelected(treeView));
                        if (personList != null && personList.size() > 0) {
                            isSelectedXLC = true;
                            listPersonEvent.setPersonDirectList(personList);
                        } else {
                            isSelected = false;
                        }
                        if (isLienThong) {
                            personList = DeleteItemDuplicate(sendDataPersonSelected(treeViewLienThong));
                            if (personList != null && personList.size() > 0) {
                                isSelectedXLCLT = true;
                                listPersonEvent.setPersonLienThongList(personList);
                            } else {
                                isSelectedLT = false;
                            }
                        }
                        break;
                }
                EventBus.getDefault().postSticky(listPersonEvent);
            } else {
                if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                    personList = DeleteItemDuplicate(sendDataPersonSelected(treeView));
                    if (personList != null && personList.size() > 0) {
                        for (Person person : personList) {
                            if (person.isXem()) {
                                isSelected = true;
                                break;
                            }
                        }
                        listPersonEvent.setPersonNotifyList(personList);
                    } else {
                        isSelected = false;
                        isSelectedLT = false;
                    }
                    isSelectedXLC = true;
                } else {
                    if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_PROCESS)) {
                        List<Person> listPersonProgress = new ArrayList<>();
                        if (processPersonInfoList.size() > 0) {
                            for (PersonProcessInfo processInfo : processPersonInfoList) {
                                if (processInfo.isXlc() || processInfo.isDxl()) {

                                    Person person = new Person(processInfo.getUserId(), processInfo.getFullName(),
                                            processInfo.getSecondUnitName(), null, processInfo.isXlc(), processInfo.isDxl(), false, processInfo.getUnitId());
                                    listPersonProgress.add(person);
                                    if (processInfo.isXlc()) {
                                        isSelectedXLC = true;
                                    }
                                }
                            }
                            listPersonEvent.setPersonProcessList(listPersonProgress);
                        } else {
                            isSelected = false;
                            isSelectedLT = false;
                        }


                    } else {
                        personList = listPersonEvent.getPersonProcessList();
                        if (personList != null && personList.size() > 0) {
                            for (Person person : personList) {
                                if (person.isXlc()) {
                                    isSelectedXLC = true;
                                    break;
                                }
                            }
                            listPersonEvent.setPersonProcessList(personList);
                        } else {
                            isSelected = false;
                            isSelectedLT = false;
                        }
                    }
                }
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        }
        listPersonEvent.setPersonGroupList(new ArrayList<Person>());
        EventBus.getDefault().postSticky(listPersonEvent);
        if (isSelected) {
            if (isSelectedXLC || isSelectedXLCLT) {
                type = null;
                if (typeChangeEvent.getTypeChangeDocumentList() != null) {
                    if (typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable() != null && typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable().equals("false")) {//nếu !=null và comment = false thì chuyển văn bản luôn
                        send();
                    } else {
                        startActivity(new Intent(SelectPersonActivityNewConvert.this, NewSendDocumentActivity.class));
                    }
                } else {
                    startActivity(new Intent(SelectPersonActivityNewConvert.this, NewSendDocumentActivity.class));
                }

            } else {
                AlertDialogManager.showAlertDialog(SelectPersonActivityNewConvert.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
            }
        } else {
            if (isSelectedLT) {
                if (isSelectedXLC || isSelectedXLCLT) {
                    type = null;
                    if (typeChangeEvent.getTypeChangeDocumentList() != null) {
                        int typeSenDoccument = typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getType();
                        if (typeSenDoccument == 1 || typeSenDoccument == 2 || typeSenDoccument == 3 || typeSenDoccument == 4) {
                            if (typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable() != null && typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable().equals("false")) {//nếu 1=null và comment=false thì chuyển văn bản luôn
                                send();
                            } else {
                                startActivity(new Intent(SelectPersonActivityNewConvert.this, NewSendDocumentActivity.class));

                            }
                        } else {
                            startActivity(new Intent(SelectPersonActivityNewConvert.this, NewSendDocumentActivity.class));
                        }
                    } else {
                        startActivity(new Intent(SelectPersonActivityNewConvert.this, NewSendDocumentActivity.class));
                    }
                } else {
                    AlertDialogManager.showAlertDialog(SelectPersonActivityNewConvert.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                }
            } else {
                AlertDialogManager.showAlertDialog(SelectPersonActivityNewConvert.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
            }
        }

    }

    private void send() {
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)) {
            List<Person> personProcessList = listPersonEvent.getPersonProcessList();
            List<Person> personLTList = listPersonEvent.getPersonLienThongList();
            String xlc = "";
            String dxl = "";
            String xem = "";
            if ((personProcessList != null && personProcessList.size() > 0) ||
                    personLTList != null && personLTList.size() > 0) {
                int index = -1;
                if (personProcessList != null && personProcessList.size() > 0) {
                    for (int i = 0; i < personProcessList.size(); i++) {
                        if (personProcessList.get(i).isXlc()) {
                            index = i;
                            xlc += personProcessList.get(i).getId() + ",";
                        }
                        if (personProcessList.get(i).isDxl()) {
                            dxl += personProcessList.get(i).getId() + ",";
                        }
                        if (personProcessList.get(i).isXem()) {
                            xem += personProcessList.get(i).getId() + ",";
                        }
                    }
                }
                String xlc_lt = "";
                String dxl_lt = "";
                String xem_lt = "";
                if (personLTList != null && personLTList.size() > 0) {
                    for (Person person : personLTList) {
                        if (person.isXlc()) {
                            xlc_lt += person.getId() + ",";
                        }
                        if (person.isDxl()) {
                            dxl_lt += person.getId() + ",";
                        }
                        if (person.isXem()) {
                            xem_lt += person.getId() + ",";
                        }
                    }
                }
                index = 0;//Không bắt validate người nhận XLC
                if (index > -1) {
                    ChangeProcessRequest changeProcessRequest = new ChangeProcessRequest();
                    TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                    changeProcessRequest.setDocId(typeChangeDocumentRequest.getDocId());
                    changeProcessRequest.setPrimaryProcess(!xlc.equals("") ? xlc.substring(0, xlc.length() - 1) : "");
                    changeProcessRequest.setCoevalProcess(!dxl.equals("") ? dxl.substring(0, dxl.length() - 1) : "");
                    changeProcessRequest.setReferProcess(!xem.equals("") ? xem.substring(0, xem.length() - 1) : "");
                    changeProcessRequest.setPrimaryInternal(!xlc_lt.equals("") ? xlc_lt.substring(0, xlc_lt.length() - 1) : "");
                    changeProcessRequest.setCoevalInternal(!dxl_lt.equals("") ? dxl_lt.substring(0, dxl_lt.length() - 1) : "");
                    changeProcessRequest.setReferInternal(!xem_lt.equals("") ? xem_lt.substring(0, xem_lt.length() - 1) : "");
                    changeProcessRequest.setComment("");
                    changeProcessRequest.setType(Constants.TYPE_SEND_PROCESS_REQUEST);
                    changeProcessRequest.setSms(0);
                    changeProcessRequest.setFinish(1);
                    changeProcessRequest.setJob(0);
                    changeProcessRequest.setHanXuLy("");
                    changeProcessRequest.setFiles(new ArrayList<Object>());

                    if (xlc.isEmpty() && xlc_lt.isEmpty()) {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                getString(R.string.str_chuachon_xlc), true, AlertDialogManager.INFO);
                        return;
                    }

                    if (xlc.isEmpty() && dxl.isEmpty() && xem.isEmpty()
                            && xlc_lt.isEmpty() && dxl_lt.isEmpty() && xem_lt.isEmpty()) {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                    } else {
                        changeDocumentPresenter.changeProcess(changeProcessRequest);
                    }

                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                }
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
            }
        } else {
            if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                try {
                    List<Person> personTBList = listPersonEvent.getPersonNotifyList();
                    String xem = "";
                    if (personTBList != null && personTBList.size() > 0) {
                        for (Person person : personTBList) {
                            if (person.isXem()) {
                                xem += person.getId() + ",";
                            }
                        }
                        ChangeDocumentNotifyRequest changeDocumentNotifyRequest = new ChangeDocumentNotifyRequest();
                        changeDocumentNotifyRequest.setDocId(typeChangeDocumentRequest.getDocId());
                        changeDocumentNotifyRequest.setComment("");
                        changeDocumentNotifyRequest.setPrimaryProcess("");
                        changeDocumentNotifyRequest.setCoevalProcess("");
                        changeDocumentNotifyRequest.setReferProcess(!xem.equals("") ? xem.substring(0, xem.length() - 1) : "");
                        changeDocumentNotifyRequest.setPrimaryInternal("");
                        changeDocumentNotifyRequest.setCoevalInternal("");
                        changeDocumentNotifyRequest.setReferInternal("");
                        changeDocumentNotifyRequest.setType("1");
                        changeDocumentNotifyRequest.setSms(0);
                        changeDocumentNotifyRequest.setJob(0);
                        changeDocumentNotifyRequest.setHanXuLy("");
                        changeDocumentNotifyRequest.setFiles(new ArrayList<Object>());

                        if (xem.isEmpty()) {
                            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                    getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                        } else {
                            changeDocumentPresenter.changeDocNotify(changeDocumentNotifyRequest);
                        }

                    } else {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                    }
                } catch (Exception e) {
                    System.out.println("================lỗi================= " + e);
                }
            } else {

                List<TypeChangeDocument> typeChangeDocumentList = typeChangeEvent.getTypeChangeDocumentList();
                if (typeChangeDocumentList.get(typeChangeEvent.getSelected()).getNextStep().equals("get_cleck_publish") &&
                        (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE) ||
                                typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND))) {
                    List<Person> personXLCList = listPersonEvent.getPersonDirectList();
                    List<Person> personLTList = listPersonEvent.getPersonLienThongList();
                    String xlc = "";
                    String dxl = "";
                    String xem = "";
                    if ((personXLCList != null && personXLCList.size() > 0) ||
                            personLTList != null && personLTList.size() > 0) {
                        if (personXLCList != null && personXLCList.size() > 0) {
                            for (Person person : personXLCList) {
                                if (person.isXlc()) {
                                    xlc += person.getId() + ",";
                                }
                                if (person.isDxl()) {
                                    dxl += person.getId() + ",";
                                }
                                if (person.isXem()) {
                                    xem += person.getId() + ",";
                                }
                            }
                        }
                        String xlc_lt = "";
                        String dxl_lt = "";
                        String xem_lt = "";
                        if (personLTList != null && personLTList.size() > 0) {
                            for (Person person : personLTList) {
                                if (person.isXlc()) {
                                    xlc_lt += person.getId() + ",";
                                }
                                if (person.isDxl()) {
                                    dxl_lt += person.getId() + ",";
                                }
                                if (person.isXem()) {
                                    xem_lt += person.getId() + ",";
                                }
                            }
                        }
                        TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                        ChangeDocumentDirectRequest changeDocumentDirectRequest = new ChangeDocumentDirectRequest();
                        changeDocumentDirectRequest.setDocId(typeChangeDocumentRequest.getDocId());
                        changeDocumentDirectRequest.setComment("");
                        changeDocumentDirectRequest.setPrimaryProcess(!xlc.equals("") ? xlc.substring(0, xlc.length() - 1) : "");
                        changeDocumentDirectRequest.setCoevalProcess(!dxl.equals("") ? dxl.substring(0, dxl.length() - 1) : "");
                        changeDocumentDirectRequest.setReferProcess(!xem.equals("") ? xem.substring(0, xem.length() - 1) : "");
                        changeDocumentDirectRequest.setPrimaryInternal(!xlc_lt.equals("") ? xlc_lt.substring(0, xlc_lt.length() - 1) : "");
                        changeDocumentDirectRequest.setCoevalInternal(!dxl_lt.equals("") ? dxl_lt.substring(0, dxl_lt.length() - 1) : "");
                        changeDocumentDirectRequest.setReferInternal(!xem_lt.equals("") ? xem_lt.substring(0, xem_lt.length() - 1) : "");
                        changeDocumentDirectRequest.setApprovedValue(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                        changeDocumentDirectRequest.setStrAction(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                        changeDocumentDirectRequest.setProcessDefinitionId(typeChangeDocumentRequest.getProcessDefinitionId());
                        changeDocumentDirectRequest.setActionType(String.valueOf(type));
                        changeDocumentDirectRequest.setSms(0);
                        changeDocumentDirectRequest.setJob(0);
                        changeDocumentDirectRequest.setHanXuLy("");
                        changeDocumentDirectRequest.setFiles(new ArrayList<Object>());

                        if (xlc.isEmpty() && xlc_lt.isEmpty()) {
                            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                    getString(R.string.str_chuachon_xlc), true, AlertDialogManager.INFO);
                            return;
                        }

                        if (xlc.isEmpty() && dxl.isEmpty() && xem.isEmpty()
                                && xlc_lt.isEmpty() && dxl_lt.isEmpty() && xem_lt.isEmpty()) {
                            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                    getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                        } else {
                            changeDocumentPresenter.changeDirect(changeDocumentDirectRequest);
                        }

                    } else {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                    }
                } else {
                    List<Person> personProcessList = listPersonEvent.getPersonProcessList();
                    List<Person> personSendList = listPersonEvent.getPersonSendList();
                    if (personProcessList != null && personProcessList.size() > 0) {
                        String dongXuLy = "";
                        int index = -1;
                        for (int i = 0; i < personProcessList.size(); i++) {
                            if (personProcessList.get(i).isXlc()) {
                                index = i;
                            } else {
                                dongXuLy += personProcessList.get(i).getId() + ",";
                            }
                        }
                        if (index > -1) {
                            String dongGui = "";
                            if (personSendList != null && personSendList.size() > 0) {
                                for (Person person : personSendList) {
                                    if (person.isXlc()) {
                                        dongGui += person.getId() + ",";
                                    }
                                }
                            }
                            TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                            if (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND)) {
                                ChangeDocumentSendRequest changeDocumentSendRequest = new ChangeDocumentSendRequest();
                                changeDocumentSendRequest.setDocId(typeChangeDocumentRequest.getDocId());
                                changeDocumentSendRequest.setChuTri(personProcessList.get(index).getId());
                                changeDocumentSendRequest.setPhoiHop(!dongXuLy.equals("") ? dongXuLy.substring(0, dongXuLy.length() - 1) : "");
                                changeDocumentSendRequest.setSFunc(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getNextStep());
                                changeDocumentSendRequest.setSApproved(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                                changeDocumentSendRequest.setSMore("");
                                changeDocumentSendRequest.setAssigner("");
                                changeDocumentSendRequest.setAct(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                                changeDocumentSendRequest.setDongGui(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                                changeDocumentSendRequest.setFormId("");
                                changeDocumentSendRequest.setSendType(String.valueOf(type));
                                changeDocumentSendRequest.setJob(0);
                                changeDocumentSendRequest.setHanXuLy("");
                                changeDocumentSendRequest.setFiles(new ArrayList<Object>());

                                changeDocumentPresenter.changeSend(changeDocumentSendRequest);

                            }
                            if (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE)) {
                                ChangeDocumentReceiveRequest changeDocumentReceiveRequest = new ChangeDocumentReceiveRequest();
                                changeDocumentReceiveRequest.setDocId(typeChangeDocumentRequest.getDocId());
                                changeDocumentReceiveRequest.setChuTri(personProcessList.get(index).getId());
                                changeDocumentReceiveRequest.setApprovedValue(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                                changeDocumentReceiveRequest.setDongXuLy(!dongXuLy.equals("") ? dongXuLy.substring(0, dongXuLy.length() - 1) : "");
                                changeDocumentReceiveRequest.setStrAction(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                                changeDocumentReceiveRequest.setProcessDefinitionId(typeChangeDocumentRequest.getProcessDefinitionId());
                                changeDocumentReceiveRequest.setIsBanHanh("0");
                                changeDocumentReceiveRequest.setDongGui(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                                changeDocumentReceiveRequest.setComment("");
                                changeDocumentReceiveRequest.setSendType(String.valueOf(type));
                                changeDocumentReceiveRequest.setJob(0);
                                changeDocumentReceiveRequest.setHanXuLy("");
                                changeDocumentReceiveRequest.setFiles(new ArrayList<Object>());
                                changeDocumentPresenter.changeReceive(changeDocumentReceiveRequest);
                            }
                        } else {
                            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                    }
                }
            }
        }
    }

    private List<Person> DeleteItemDuplicate(List<Person> personList) {//xóa phần tử trùng lặp trong List
        if (personList != null) {
            for (int i = 0; i < personList.size(); i++) {
                for (int j = i + 1; j < personList.size(); j++) {
                    if (personList.get(i).equals(personList.get(j))) {
                        personList.remove(i);
                    }
                }
            }
            return personList;
        } else {
            return null;
        }
    }


    private List<Person> sendDataPersonSelected(TreeView treeView) {
        List<Person> personList = new ArrayList<>();
        List<TreeNode> selectedNodes = new ArrayList<TreeNode>();
        List<TreeNode> selectedXEMNodes = new ArrayList<TreeNode>();
        List<TreeNode> selectedPHNodes = new ArrayList<TreeNode>();
        if (treeView != null) {
            selectedNodes = treeView.getSelectedXLCNodes();
            selectedXEMNodes = treeView.getSelectedXEMNodes();
            selectedPHNodes = treeView.getSelectedPHNodes();
        }

        if (selectedNodes != null && selectedNodes.size() > 0) {

            for (int i = 0; i < selectedNodes.size(); i++) {

                Person personXLC = new Person(((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getId(),
                        ((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getName(),
                        ((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getName(), null,
                        ((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getParentId(),
                        true, false, false, 0);
                if (personXLC != null) {
                    personList.add(personXLC);
                }
            }
        }
        if (selectedXEMNodes != null && selectedXEMNodes.size() > 0) {

            for (int i = 0; i < selectedXEMNodes.size(); i++) {
                Person personXEM = new Person(((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getId(),
                        ((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getName(),
                        ((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getName(), null,
                        ((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getParentId(),
                        false, false, true, 0);
                if (personXEM != null) {
                    personList.add(personXEM);
                }
            }
        }
        if (selectedPHNodes != null && selectedPHNodes.size() > 0) {
            for (int i = 0; i < selectedPHNodes.size(); i++) {
                Person personPHN = new Person(((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getId(),
                        ((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getName(),
                        ((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getName(), null,
                        ((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getParentId(),
                        false, true, false, 0);
                if (personPHN != null) {
                    personList.add(personPHN);
                }
            }
        }
        return personList;
    }

    private List<Person> send_notify_person() {
        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
        //if (personList == null) {
        List<Person> personList = new ArrayList<>();
        //}
        if (personSendNotifyChecks != null && personSendNotifyChecks.size() > 0) {
            for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                View view = personSendNotifyChecks.get(i).getView();
                CheckBox checkXLChinh = (CheckBox) view.findViewById(R.id.checkXLChinh);
                //if (personSendNotifyChecks.get(i).isNotParent()) {
                if (checkXLChinh.isChecked()) {
//                        int index = -1;
//                        for (int j = 0; j < personList.size(); j++) {
//                            if (personSendNotifyChecks.get(i).getId().equals(personList.get(j).getId())) {
//                                index = j;
//                                break;
//                            }
//                        }
                    //if (index == -1) {
                    List<PersonSendNotifyInfo> personSendNotifyInfoList = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
                    for (int j = 0; j < personSendNotifyInfoList.size(); j++) {
                        if (personSendNotifyInfoList.get(j).getId().equals(personSendNotifyChecks.get(i).getId())) {
                            Person person = new Person(personSendNotifyChecks.get(i).getId(), personSendNotifyChecks.get(i).getName(),
                                    personSendNotifyInfoList.get(j).getName(), null, personSendNotifyChecks.get(i).getParentId(), true, false, false, 0);
                            personList.add(person);
                            break;
                        }// else {
//                            if (personSendNotifyChecks.get(i).getParentId() == null || personSendNotifyChecks.get(i).getParentId().trim().equals("")) {
//                                Person person = new Person(personSendNotifyChecks.get(i).getId(), personSendNotifyChecks.get(i).getName(),
//                                        personSendNotifyChecks.get(i).getName(), null, true, false, false, 0);
//                                personList.add(person);
//                                break;
//                            }
//                        }
                    }
                    //}
                }
                //}
            }
        }
        return personList;
    }


    private void getPersons() {
        if (connectionDetector.isConnectingToInternet()) {
            tvLoadData.setVisibility(View.VISIBLE);
            if (typePersonEvent != null) {
                switch (typePersonEvent.getType()) {
                    case Constants.TYPE_PERSON_PROCESS:
                        changeDocumentPresenter.getPersonProcess(EventBus.getDefault().getStickyEvent(ListProcessRequest.class));
                        break;
                    default:
                        if (typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)) {
                            getLTAction = true;
                            changeDocumentPresenter.getUnits(0);
                        } else {
                            if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                                getLTAction = true;
                                changeDocumentPresenter.getUnits(1);
                            } else {
                                btnSelectDV.setVisibility(View.VISIBLE);
                                changeDocumentPresenter.getUnits(0);
                            }
                        }
                        if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                            btnSelectDV.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getPersonOrUnitExpected(int docId) {//lấy danh sách cá nhân/đơn vị dự kiến
        changeDocumentPresenter.getPersonOrUnitExpected(docId);
    }

    private void getLienThongChuyenXL() {
        if (connectionDetector.isConnectingToInternet()) {
            changeDocumentPresenter.getLienThongXL();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getLienThongChuyenBH() {
        if (connectionDetector.isConnectingToInternet()) {
            TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
            showProgress();
            String docId = "";
            if (typeChangeDocumentRequest.getDocId().contains("[")) { //áp dụng cho chuyển đồng thời văn bản
                String replace = typeChangeDocumentRequest.getDocId().replace("[", "");
                String replace1 = replace.replace("]", "");
                List<String> myList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
                docId = myList.get(0);
            } else {
                docId = typeChangeDocumentRequest.getDocId();
            }
            changeDocumentPresenter.getLienThongBH(docId);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }
    
    private void displayPersonProcess(List<PersonProcessInfo> processInfoList) {
        layoutFilter.setVisibility(View.GONE);
        layout_process.setVisibility(View.GONE);
        layoutProcessSend.setVisibility(View.VISIBLE);
        layout_send_notify.setVisibility(View.GONE);
        layout_send_lienthong.setVisibility(View.GONE);
        layoutSendXemdb.setVisibility(View.GONE);
        if (processInfoList != null && processInfoList.size() > 0) {
            tvNodataProcessSend.setVisibility(View.GONE);
            recyclerviewProcess.setVisibility(View.VISIBLE);
            recyclerviewProcess.setHasFixedSize(false);
            layoutManager = new LinearLayoutManager(this);
            recyclerviewProcess.setNestedScrollingEnabled(false);
            recyclerviewProcess.setLayoutManager(layoutManager);
            selectProcessV2Adapter = new SelectProcessV2Adapter(this, R.layout.item_select_process_list, processInfoList);
            recyclerviewProcess.setAdapter(selectProcessV2Adapter);
            if (processInfoList.size() == 1) {
                processPersonInfoList.get(0).setXlc(true);
                processPersonInfoList.get(0).setDxl(false);
                selectProcessV2Adapter.notifyItemChanged(0);
                if (typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable() != null && typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable().equals("false")) {//nếu !=null và commnent =false thì chuyển văn bản luôn
                    List<Person> personList = new ArrayList<>();
                    Person person = new Person(processPersonInfoList.get(0).getUserId(), processPersonInfoList.get(0).getFullName(),
                            processPersonInfoList.get(0).getSecondUnitName(), null, true, false, false, processPersonInfoList.get(0).getUnitId());
                    personList.add(person);
                    ListPersonEvent listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
                    listPersonEvent.setPersonProcessList(personList);
                    EventBus.getDefault().postSticky(listPersonEvent);
                    send();
                } else {
                    dataPersonSendocument();
                }
            }
        } else {
            tvNodataProcessSend.setVisibility(View.VISIBLE);
            recyclerviewProcess.setVisibility(View.GONE);
        }
    }


    private void displayLienThongTree(List<PersonSendNotifyInfo> lienThongInfoList) {
        List<PersonSendNotifyInfo> listData = new ArrayList<>();
        isLienThong = true;
        layout_send_lienthong.setVisibility(View.VISIBLE);
        viewGroupLienThong.setVisibility(View.VISIBLE);
        viewGroupLienThong.removeAllViewsInLayout();

        if (lienThongInfoList == null) {
            return;
        }
        listData = buildUnitTree(lienThongInfoList, null);
        if (listData != null && listData.size() > 0) {
            buildTreeData(listData, 0, rootLienThong);
        }
        treeViewLienThong = new TreeView(rootLienThong, this, new MyNodeViewFactory());

        View view = treeViewLienThong.getView();
        if (checkMultilXLC()) {
            treeViewLienThong.selectMultilXLC(2);
        }
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroupLienThong.addView(view);
        updateDataSelectNormalTreeView(treeViewLienThong, 2);
        hideProgress();
    }


    private void displayTreeViewSendProcess(List<PersonSendNotifyInfo> personSendNotifyInfoList, int typeDisplay, ViewGroup viewGroup) {

        List<PersonSendNotifyInfo> listData = new ArrayList<>();
        List<PersonSendNotifyInfo> listDataParent = new ArrayList<>();

        // Type SendProcess
        if (typeDisplay == 1) {
            layoutFilter.setVisibility(View.VISIBLE);
            layout_process.setVisibility(View.VISIBLE);
            layout_send_notify.setVisibility(View.GONE);
            layoutProcessSend.setVisibility(View.GONE);
            layoutSendXemdb.setVisibility(View.GONE);
            btnThuGonXL.setVisibility(View.VISIBLE);
            viewGroup.removeAllViewsInLayout();
        }

        // Type Send XemDB
        else if (typeDisplay == 2) {
            layoutFilter.setVisibility(View.VISIBLE);
            layout_process.setVisibility(View.GONE);
            layout_send_notify.setVisibility(View.GONE);
            layoutProcessSend.setVisibility(View.GONE);
            layoutSendXemdb.setVisibility(View.VISIBLE);
            viewGroup.removeAllViewsInLayout();

        }

        root = TreeNode.root();
        if (personSendNotifyInfoList == null) {
            btnThuGonXL.setVisibility(View.GONE);
            viewGroup.addView(textviewNotData());
            return;
        } else
            for (int i = 0; i < personSendNotifyInfoList.size(); i++) {
                if (personSendNotifyInfoList.get(i).getParentId() == null ||
                        personSendNotifyInfoList.get(i).getParentId().equals("null")) {
                    listData = buildUnitTree(personSendNotifyInfoList, null);
                    break;
                } else {
                    boolean isExisted = false;
                    for (int j = 0; j < personSendNotifyInfoList.size(); j++) {
                        if (personSendNotifyInfoList.get(j).getId().equals(personSendNotifyInfoList.get(i).getParentId())) {
                            isExisted = true;
                            break;
                        }
                    }
                    if (!isExisted) {
                        listDataParent.addAll(buildUnitTree(personSendNotifyInfoList, personSendNotifyInfoList.get(i).getParentId()));
                    }
                }
            }
        if (listData != null && listData.size() > 0) {
            buildTreeData(listData, 0, root);
        } else if (listDataParent != null && listDataParent.size() > 0) {
            buildTreeData(listDataParent, 0, root);
        }
        // Type SendProcess
        if (typeDisplay == 1) {
            treeView = new TreeView(root, this, new MyNodeViewFactory());
        }
        // Type Send XemDB
        else if (typeDisplay == 2) {
            treeView = new TreeView(root, this, new MyNodeViewPersonSendXemDBFactory());

        }
        View view = treeView.getView();
        if (checkMultilXLC()) {
            treeView.selectMultilXLC(2);
        }
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.addView(view);

        treeView.expandLevel(0);
        updateDataSelectNormalTreeView(treeView, 1);
    }

    private View textviewNotData() {
        btnThuGonXL.setVisibility(View.GONE);
        final TextView textView = new TextView(this);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._14sp));
        textView.setText("Không có dữ liệu");
        View view = textView;
        view.setBackgroundColor(Color.WHITE);
        return view;
    }

    private void buildTreeData(List<PersonSendNotifyInfo> listData, int level, TreeNode root) {
        if (listData == null || root == null) {
            return;
        }
        if (level > 2) {
            Log.e("buildTreeData", level + ((PersonSendNotifyInfo) root.getValue()).getName());
        }
        for (PersonSendNotifyInfo itemData : listData) {
            TreeNode treeNode = new TreeNode(itemData);
            treeNode.setLevel(level);
            if (itemData.getChildrenList() != null && itemData.getChildrenList().size() > 0) {
                int newlevel = level + 1;
                buildTreeData(itemData.getChildrenList(), newlevel, treeNode);
            }
            root.addChild(treeNode);
        }
    }

    private void displayPersonSendNotify(List<PersonSendNotifyInfo> personSendNotifyInfoList) {
        EventBus.getDefault().postSticky(new PersonSendNotifyEvent(personSendNotifyInfoList));
        layout_process.setVisibility(View.GONE);
        layoutFilter.setVisibility(View.VISIBLE);
        layout_send_notify.setVisibility(View.VISIBLE);
        layout_send_lienthong.setVisibility(View.GONE);
        layoutProcessSend.setVisibility(View.GONE);
        layout_donvi.removeAllViewsInLayout();
        if (personSendNotifyInfoList != null && personSendNotifyInfoList.size() > 0) {
            List<PersonSendNotifyInfo> lstTitle = new ArrayList<>();
            for (int i = 0; i < personSendNotifyInfoList.size(); i++) {
                if (personSendNotifyInfoList.get(i).getParentId() == null || personSendNotifyInfoList.get(i).getParentId().equals("null")) {
                    lstTitle.add(personSendNotifyInfoList.get(i));
                } else {
                    boolean isExisted = false;
                    for (int j = 0; j < personSendNotifyInfoList.size(); j++) {
                        if (personSendNotifyInfoList.get(j).getId().equals(personSendNotifyInfoList.get(i).getParentId())) {
                            isExisted = true;
                            break;
                        }
                    }
                    if (!isExisted) {
                        lstTitle.add(personSendNotifyInfoList.get(i));
                    }
                }
            }
            List<Integer> sizes = new ArrayList<>();
            List<Boolean> touchs = new ArrayList<>();
            for (int i = 0; i < lstTitle.size(); i++) {
                int count = 0;
                for (int j = 0; j < personSendNotifyInfoList.size(); j++) {
                    if (lstTitle.get(i).getId().equals(personSendNotifyInfoList.get(j).getParentId())) {
                        count++;
                    }
                }
                sizes.add(count);
                touchs.add(false);
            }
            List<PersonSendNotifyCheck> personSendNotifyChecks = new ArrayList<>();
            EventBus.getDefault().postSticky(new PersonSendNotifyCheckEvent(personSendNotifyChecks));
            SelectSendNotifyAdapter selectSendNotifyAdapter = new SelectSendNotifyAdapter(this, R.layout.item_person_send_notify_list, R.layout.item_person_send_notify_detail, lstTitle, sizes, touchs);
            int adapterCount = selectSendNotifyAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = selectSendNotifyAdapter.getView(i, null, null);
                PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                PersonSendNotifyCheck personSendNotifyCheck = new PersonSendNotifyCheck(item, lstTitle.get(i).getId(), null, lstTitle.get(i).getName(), null, true);
                personSendNotifyChecks.add(personSendNotifyCheck);
                layout_donvi.addView(item);
                EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
            }
        }
    }

    private void displayJobPossition(final List<JobPositionInfo> jobPositionInfos) {
        if (jobPositionInfos != null && jobPositionInfos.size() > 0) {
            String[] spinnerItems = new String[jobPositionInfos.size() + 1];
            spinnerItems[0] = getString(R.string.SELECT_JOB_POSSITION);
            for (int i = 0; i < jobPositionInfos.size(); i++) {
                spinnerItems[i + 1] = jobPositionInfos.get(i).getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTypeface(Application.getApp().getTypeface());
                    ((TextView) v).setTextColor(getResources().getColor(R.color.md_black));
                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    ((TextView) v).setTypeface(Application.getApp().getTypeface());
                    ((TextView) v).setTextColor(getResources().getColor(R.color.md_black));
                    return v;
                }
            };
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            sPosition.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            sPosition.setSelection(0);
            sPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position > 0) {
                        jobSelected = jobPositionInfos.get(position - 1).getId();
                    } else {
                        jobSelected = "";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }

    private void displayUnit(final List<UnitInfo> unitInfos) {
        if (unitInfos != null && unitInfos.size() > 0) {
            String[] spinnerItems = new String[unitInfos.size() + 1];
            spinnerItems[0] = getString(R.string.SELECT_UNIT);
            for (int i = 0; i < unitInfos.size(); i++) {
                spinnerItems[i + 1] = unitInfos.get(i).getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTypeface(Application.getApp().getTypeface());
                    ((TextView) v).setTextColor(getResources().getColor(R.color.md_black));
                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    ((TextView) v).setTypeface(Application.getApp().getTypeface());
                    ((TextView) v).setTextColor(getResources().getColor(R.color.md_black));
                    return v;
                }
            };
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            sUnit.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            sUnit.setSelection(0);
            sUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position > 0) {
                        unitSelected = unitInfos.get(position - 1).getId();
                    } else {
                        unitSelected = "";
                    }

                    if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                        changeDocumentPresenter.getPersonSend(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                    } else {
                        tvLoadData.setVisibility(View.VISIBLE);
                        showProgress();
                        changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

        } else {
            showProgress();
            changeDocumentPresenter.getPersonSend(new SearchPersonRequest("", "", ""));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSuccess(Object object) {
        hideProgress();
        tvLoadData.setVisibility(View.GONE);
        if (typePersonEvent != null) {
            switch (typePersonEvent.getType()) {
                case Constants.TYPE_PERSON_PROCESS:
                    processPersonInfoList = ConvertUtils.fromJSONList(object, PersonProcessInfo.class);
                    displayPersonProcess(processPersonInfoList);
                    if (processPersonInfoList != null && processPersonInfoList.size() > 1) {
                        getPersonOrUnitExpected(DocId);
                    }


                    break;
                case Constants.TYPE_SEND_PERSON_PROCESS:
                    List<PersonSendNotifyInfo> personSendNotifyInfos = ConvertUtils.fromJSONList(object, PersonSendNotifyInfo.class);
                    displayTreeViewSendProcess(personSendNotifyInfos, 1, viewGroup);
                    getPersonOrUnitExpected(DocId);//lấy danh sách cá nhân/đơn vị dự kiến
                    if (getLTAction) {
                        getLienThongChuyenXL();
                    }

                    getLTAction = false;
                    break;
                case Constants.TYPE_PERSON_DIRECT:
                    List<PersonSendNotifyInfo> personSendNotifyInfo1s = ConvertUtils.fromJSONList(object, PersonSendNotifyInfo.class);
                    displayTreeViewSendProcess(personSendNotifyInfo1s, 1, viewGroup);
                    getPersonOrUnitExpected(DocId);//lấy danh sách cá nhân/đơn vị dự kiến

                    if (getLTAction) {
                        getLienThongChuyenBH();
                    }
                    getLTAction = false;
                    break;
                case Constants.TYPE_SEND_VIEW:
                    List<PersonSendNotifyInfo> personSendNotifyInfo2s = ConvertUtils.fromJSONList(object, PersonSendNotifyInfo.class);
                    displayTreeViewSendProcess(personSendNotifyInfo2s, 2, layoutDonviXemdb);
                    getPersonOrUnitExpected(DocId);//lấy danh sách cá nhân/đơn vị dự kiến
                    break;
                default:
                    List<PersonSendNotifyInfo> personSendNotifyInfoList = ConvertUtils.fromJSONList(object, PersonSendNotifyInfo.class);
                    displayPersonSendNotify(personSendNotifyInfoList);
                    break;
            }
        }


    }

    @Override
    public void onSuccessJobPossitions(List<JobPositionInfo> jobPositionInfos) {
        displayJobPossition(jobPositionInfos);
    }

    @Override
    public void onSuccessUnits(List<UnitInfo> unitInfos) {
        displayUnit(unitInfos);
    }

    @Override
    public void onSuccessChangeDoc() {
        EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
        EventBus.getDefault().removeStickyEvent(TypePersonEvent.class);
        EventBus.getDefault().removeStickyEvent(TaiFileNewSendEvent.class);

        EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
        EventBus.getDefault().postSticky(new ReloadDocNotificationEvent(true));
        FinishEvent finishEvent = EventBus.getDefault().getStickyEvent(FinishEvent.class);
        if(finishEvent!=null){
            if(finishEvent.isFinish()){
                EventBus.getDefault().postSticky(finishEvent);
            }
        }
        Toast.makeText(this, getString(R.string.CHANGE_DOC_SUCCESS), Toast.LENGTH_LONG).show();
        SelectPersonActivityNewConvert.activityThis.finish();
        finish();
    }

    @Override
    public void onSuccessFormList(List<String> listForm) {

    }

    @Override
    public void onSuccessGroupPerson(List<PersonGroupChangeDocInfo> personGroupChangeDocInfos) {

    }

    @Override
    public void onSuccessUpLoad(List<Object> object) {

    }

    @Override
    public void onError(APIError apiError) {
        sendExceptionError(apiError);
        hideProgress();
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(appPrefs.getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
        if (apiError.getMessage().equals("changeFail")) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_DOC_TITLE_ERROR), getString(R.string.CHANGE_DOC_FAIL), true, AlertDialogManager.ERROR);
        }
        if (apiError.getMessage().contains("document_processed")) {
            Toast.makeText(this, getString(R.string.CHANGED_DOC), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "" + apiError.getMessage(), Toast.LENGTH_LONG).show();

    }


    @Override
    public void onSuccessLienThong(List<PersonSendNotifyInfo> lienThongInfos) {

        if (lienThongInfos != null && lienThongInfos.size() > 0) {
//            displayLienThong(lienThongInfos);
            displayLienThongTree(lienThongInfos);
        } else {
            hideProgress();
            layout_send_lienthong.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccessPersonOrUnit(Object object) {
        List<PersonOrUnitExpectedInfo> personOrUnitExpectedInfos = ConvertUtils.fromJSONList(object, PersonOrUnitExpectedInfo.class);
        personOrUnitExpectedList = personOrUnitExpectedInfos;
        ArrayList<Person> personList = new ArrayList<Person>();
        Person person = null;
        if (typePersonEvent != null) {
            switch (typePersonEvent.getType()) {

                case Constants.TYPE_PERSON_DIRECT:
                    for (int i = 0; i < personOrUnitExpectedInfos.size(); i++) {
                        if (personOrUnitExpectedInfos.get(i).getStatus().equals("xlc")) {
                            person = new Person(personOrUnitExpectedInfos.get(i).getId(), "", "", "", true, false, false, 0);
                            personList.add(person);
                        } else if (personOrUnitExpectedList.get(i).getStatus().equals("dxl")) {
                            person = new Person(personOrUnitExpectedInfos.get(i).getId(), "", "", "", false, true, false, 0);
                            personList.add(person);
                        } else if (personOrUnitExpectedList.get(i).getStatus().equals("xem")) {
                            person = new Person(personOrUnitExpectedInfos.get(i).getId(), "", "", "", false, false, true, 0);
                            personList.add(person);
                        }
                    }
                    CheckAutoPersonOrUnitExpected(treeView, treeViewLienThong, personList);

            }
        }


    }

    @OnClick({R.id.btnSearch, R.id.btnSelectCN, R.id.btnSelectDV})
    public void searchPerson(View view) {
        Intent intent = new Intent(this, SelectGroupPersonActivity.class);
        if (connectionDetector.isConnectingToInternet()) {
            ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
            switch (view.getId()) {
                case R.id.btnSearch:
                    if (txtName.getText() != null && !txtName.getText().toString().trim().equals("")) {
                        if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                            changeDocumentPresenter.getPersonSend(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                        } else {
                            tvLoadData.setVisibility(View.VISIBLE);
                            changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                        }
                    } else {
                        if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                            changeDocumentPresenter.getPersonSend(new SearchPersonRequest(unitSelected, "", jobSelected));
                        } else {
                            tvLoadData.setVisibility(View.VISIBLE);
                            changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest(unitSelected, "", jobSelected));
                        }
                    }
                    break;
                case R.id.btnSelectCN:
                    savePerson();
                    listPersonEvent.setPersonGroupList(new ArrayList<Person>());
                    EventBus.getDefault().postSticky(listPersonEvent);
                    if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                        intent.putExtra("type", "2");
                        type = "2";
                    } else {
                        intent.putExtra("type", "0");
                        type = "0";
                    }
                    startActivity(intent);
                    break;
                case R.id.btnSelectDV:
                    savePerson();
                    type = "1";
                    listPersonEvent.setPersonGroupList(new ArrayList<Person>());
                    EventBus.getDefault().postSticky(listPersonEvent);
                    intent.putExtra("type", "1");
                    startActivity(intent);
                    break;
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void savePerson() {
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        List<Person> personList = null;
        if (typePersonEvent != null) {
            if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_SEND)
                    || typePersonEvent.getType().equals(Constants.TYPE_PERSON_NOTIFY)
                    || typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)
                    || typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                switch (typePersonEvent.getType()) {
                    case Constants.TYPE_PERSON_SEND:
                        personList = listPersonEvent.getPersonSendList();
                        personList = send_notify_person();
                        listPersonEvent.setPersonSendList(personList);
                        break;
                    case Constants.TYPE_PERSON_NOTIFY:
                        personList = listPersonEvent.getPersonNotifyList();
                        personList = send_notify_person();
                        listPersonEvent.setPersonNotifyList(personList);
                        break;
                    case Constants.TYPE_SEND_PERSON_PROCESS:

                        personList = DeleteItemDuplicate(sendDataPersonSelected(treeView));
                        listPersonEvent.setPersonProcessList(personList);
                        if (isLienThong) {
                            personList = DeleteItemDuplicate(sendDataPersonSelected(treeViewLienThong));
                            if (personList != null && personList.size() > 0) {
                                listPersonEvent.setPersonLienThongList(personList);
                            }
                        }
                        break;
                    case Constants.TYPE_PERSON_DIRECT:
                        personList = DeleteItemDuplicate(sendDataPersonSelected(treeViewLienThong));
                        if (personList != null && personList.size() > 0) {
                            listPersonEvent.setPersonDirectList(personList);
                        }
                        if (isLienThong) {
                            personList = DeleteItemDuplicate(sendDataPersonSelected(treeViewLienThong));
                            if (personList != null && personList.size() > 0) {
                                listPersonEvent.setPersonLienThongList(personList);
                            }
                        }
                        break;
                }
                EventBus.getDefault().postSticky(listPersonEvent);
            } else {
                if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                    personList = DeleteItemDuplicate(sendDataPersonSelected(treeView));
                    if (personList != null && personList.size() > 0) {
                        listPersonEvent.setPersonNotifyList(personList);
                    } else {

                    }
                } else {
                    personList = listPersonEvent.getPersonProcessList();
                    if (personList != null && personList.size() > 0) {

                        listPersonEvent.setPersonProcessList(personList);
                    } else {

                    }
                }
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        if (connectionDetector.isConnectingToInternet()) {
            appPrefs.setToken(loginInfo.getToken());
            if (typePersonEvent != null) {
                switch (typePersonEvent.getType()) {
                    case Constants.TYPE_PERSON_PROCESS:
                        changeDocumentPresenter.getPersonProcess(EventBus.getDefault().getStickyEvent(ListProcessRequest.class));
                        break;
                    default:
                        //changeDocumentPresenter.getJobPossitions();
                        if (typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)) {
                            changeDocumentPresenter.getUnits(0);
                            tvLoadData.setVisibility(View.VISIBLE);
                            changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest("", "", ""));
                            getLienThongChuyenXL();
                        } else {
                            if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                                changeDocumentPresenter.getUnits(1);
                                changeDocumentPresenter.getPersonSend(new SearchPersonRequest("", "", ""));
                                getLienThongChuyenBH();
                            } else {
                                tvLoadData.setVisibility(View.VISIBLE);
                                changeDocumentPresenter.getUnits(0);
                                changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest("", "", ""));
                            }
                        }
                        if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                            btnSelectDV.setVisibility(View.GONE);
                        }
                        break;
//                case Constants.TYPE_PERSON_SEND:
//                    changeDocumentPresenter.getPersonSend(new SearchPersonRequest("", "", ""));
//                    break;
//                case Constants.TYPE_PERSON_NOTIFY:
//                    changeDocumentPresenter.getPersonSend(new SearchPersonRequest("", "", ""));
//                    break;
//                case Constants.TYPE_SEND_PERSON_PROCESS:
//                    changeDocumentPresenter.getPersonSend(new SearchPersonRequest("", "", ""));
//                    break;
                }
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        appPrefs.removeAll();
        startActivity(new Intent(SelectPersonActivityNewConvert.this, LoginActivity.class));
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

    @OnClick({R.id.btnMorongXL, R.id.btnThuGonXL, R.id.btnMorongLT, R.id.btnThuGonLT})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMorongXL:
                layout_person.setVisibility(View.VISIBLE);
                btnMorongXL.setVisibility(View.GONE);
                btnThuGonXL.setVisibility(View.VISIBLE);
                break;
            case R.id.btnThuGonXL:
                layout_person.setVisibility(View.GONE);
                btnMorongXL.setVisibility(View.VISIBLE);
                btnThuGonXL.setVisibility(View.GONE);
                break;
            case R.id.btnMorongLT:
                layout_donvi_lt.setVisibility(View.VISIBLE);
                btnMorongLT.setVisibility(View.GONE);
                btnThuGonLT.setVisibility(View.VISIBLE);
                break;
            case R.id.btnThuGonLT:
                layout_donvi_lt.setVisibility(View.GONE);
                btnMorongLT.setVisibility(View.VISIBLE);
                btnThuGonLT.setVisibility(View.GONE);
                break;
        }
    }


    private void updateDataSelectNormalTreeView(TreeView treeView, int typeTreeView) {
        //Xu ly bat dong bo
        new LongOperation(treeView, typeTreeView).execute();
    }

    private List<PersonSendNotifyInfo> buildUnitTree(List<PersonSendNotifyInfo> personSendTreeNodeNotifyInfoList, final String id) {
        final List<PersonSendNotifyInfo> results = new ArrayList<>();
        if (personSendTreeNodeNotifyInfoList == null) {
            return null;
        }
        for (PersonSendNotifyInfo unit : personSendTreeNodeNotifyInfoList) {
            if (!unit.isTrace() && (unit.getParentId() == id || (unit.getParentId() != null && unit.getParentId().equalsIgnoreCase(id)))) {
                unit.setTrace(true);
                PersonSendNotifyInfo dicItem = new PersonSendNotifyInfo();
                dicItem.setId(unit.getId());
                dicItem.setName(unit.getName());
                dicItem.setParentId(unit.getParentId());
                dicItem.setChildrenList(buildUnitTree(personSendTreeNodeNotifyInfoList, unit.getId()));
                results.add(dicItem);
            }
        }
        return results;
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

    private class LongOperation extends AsyncTask<Void, Void, Boolean> {
        TreeView treeView;
        int typeTreeView;

        public LongOperation(TreeView treeView, int typeTreeView) {
            this.treeView = treeView;
            this.typeTreeView = typeTreeView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            hideProgress();
            if (aBoolean) {
                if (typePersonEvent != null) {
                    switch (typePersonEvent.getType()) {
                        case Constants.TYPE_PERSON_DIRECT:
                        case Constants.TYPE_SEND_VIEW:
                        case Constants.TYPE_SEND_PERSON_PROCESS:
                            treeView.refreshTreeView();
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            List<Person> personList = new ArrayList<>();
            ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
            if (listPersonEvent == null)
                return false;
            if (typePersonEvent != null) {
                switch (typePersonEvent.getType()) {
                    case Constants.TYPE_PERSON_PROCESS:
                        break;
                    case Constants.TYPE_PERSON_DIRECT:
                        if (typeTreeView == 1) {
                            personList = listPersonEvent.getPersonDirectList();
                        } else if (typeTreeView == 2) {
                            personList = listPersonEvent.getPersonLienThongList();
                        } else if (typeTreeView == 3 || typeTreeView == 4) {
                            personList = listPersonEvent.getPersonGroupList();
                        }
                        if (personList == null)
                            return false;
                        return updateDataTreeView(treeView, personList, typeTreeView);
                    case Constants.TYPE_SEND_PERSON_PROCESS:
                        if (typeTreeView == 1) {
                            personList = listPersonEvent.getPersonProcessList();
                        } else if (typeTreeView == 2) {
                            personList = listPersonEvent.getPersonLienThongList();
                        } else if (typeTreeView == 3 || typeTreeView == 4) {
                            personList = listPersonEvent.getPersonGroupList();
                        }
                        if (personList == null)
                            return false;
                        return updateDataTreeView(treeView, personList, typeTreeView);
                    case Constants.TYPE_SEND_VIEW:
                        if (typeTreeView == 1) {
                            personList = listPersonEvent.getPersonNotifyList();
                        } else if (typeTreeView == 3 || typeTreeView == 4) {
                            personList = listPersonEvent.getPersonGroupList();
                        }
                        if (personList == null)
                            return false;
                        return updateDataTreeView(treeView, personList, typeTreeView);
                    default:
                        break;
                }
            }
            return true;
        }

    }

    private void getTreeViewById(TreeView treeView, String idTreeView) {
        if (treeView == null) {
            return;
        }
        List<TreeNode> allTree = treeView.getAllNodes();
        if (allTree == null) {
            return;
        }
        for (int i = 0; i < allTree.size(); i++) {
            if (idTreeView.equalsIgnoreCase(((PersonSendNotifyInfo) allTree.get(i).getValue()).getId())) {
                treeView.settingViewTreeNode(allTree.get(i));
            }
        }
    }

    private void CheckAutoPersonOrUnitExpected(TreeView treeView, TreeView treeViewLienThong, ArrayList<Person> personArrayList) {
        if (treeView != null) {
            List<TreeNode> allTree = treeView.getAllNodes();
            if (allTree != null) {
                for (int i = 0; i < allTree.size(); i++) {
                    for (int j = 0; j < personArrayList.size(); j++) {
                        if (((PersonSendNotifyInfo) allTree.get(i).getValue()).getId().equals(personArrayList.get(j).getId())) {
                            allTree.get(i).setSelectedXLC(personArrayList.get(j).isXlc());
                            allTree.get(i).setSelectedPH(personArrayList.get(j).isDxl());
                            allTree.get(i).setSelectedXEM(personArrayList.get(j).isXem());
                            treeView.refreshTreeView();
                        }
                    }
                }
            }
        }
        if (treeViewLienThong != null) {
            List<TreeNode> allTreeLienthong = treeViewLienThong.getAllNodes();
            if (allTreeLienthong != null) {
                for (int i = 0; i < allTreeLienthong.size(); i++) {
                    for (int j = 0; j < personArrayList.size(); j++) {
                        if (((PersonSendNotifyInfo) allTreeLienthong.get(i).getValue()).getId().equals(personArrayList.get(j).getId())) {
                            allTreeLienthong.get(i).setSelectedXLC(personArrayList.get(j).isXlc());
                            allTreeLienthong.get(i).setSelectedPH(personArrayList.get(j).isDxl());
                            allTreeLienthong.get(i).setSelectedXEM(personArrayList.get(j).isXem());
                            treeViewLienThong.refreshTreeView();
                        }
                    }
                }
            }
        }


    }

    private void unselectPersonProgessTreeView(TreeView treeView, TreeView treeViewLienThong, RemoveSelectPersonEvent removeSelectPersonEvent) {
        if (removeSelectPersonEvent == null) {
            return;
        }
        ArrayList<Person> ListPersonRemove = new ArrayList<>();
        ArrayList<Person> ListPersonLienThongRemove = new ArrayList<>();

        if (removeSelectPersonEvent.getPersonProcessRemove() != null && removeSelectPersonEvent.getPersonProcessRemove().size() > 0) {
            ListPersonRemove = removeSelectPersonEvent.getPersonProcessRemove();
        } else if (removeSelectPersonEvent.getPersonDirectRemove() != null && removeSelectPersonEvent.getPersonDirectRemove().size() > 0) {
            ListPersonRemove = removeSelectPersonEvent.getPersonDirectRemove();
        } else if (removeSelectPersonEvent.getPersonNotifyRemove() != null && removeSelectPersonEvent.getPersonNotifyRemove().size() > 0) {
            ListPersonRemove = removeSelectPersonEvent.getPersonNotifyRemove();
        }

        if (removeSelectPersonEvent.getPersonLienThongRemove() != null) {
            ListPersonLienThongRemove = removeSelectPersonEvent.getPersonLienThongRemove();
        }

        if (treeView != null) {
            List<TreeNode> allTree = treeView.getAllNodes();
            if (allTree != null) {
                for (int i = 0; i < allTree.size(); i++) {
                    for (int j = 0; j < ListPersonRemove.size(); j++) {
                        if (((PersonSendNotifyInfo) allTree.get(i).getValue()).getId().equals(ListPersonRemove.get(j).getId())) {
                            allTree.get(i).setSelectedXLC(false);
                            allTree.get(i).setSelectedPH(false);
                            allTree.get(i).setSelectedXEM(false);
                            treeView.refreshTreeView();
                        }
                    }
                }
            }
        }
        if (treeViewLienThong != null) {
            List<TreeNode> allTreeLienthong = treeViewLienThong.getAllNodes();
            if (allTreeLienthong != null) {
                for (int i = 0; i < allTreeLienthong.size(); i++) {
                    for (int j = 0; j < ListPersonLienThongRemove.size(); j++) {
                        if (((PersonSendNotifyInfo) allTreeLienthong.get(i).getValue()).getId().equals(ListPersonLienThongRemove.get(j).getId())) {
                            allTreeLienthong.get(i).setSelectedXLC(false);
                            allTreeLienthong.get(i).setSelectedPH(false);
                            allTreeLienthong.get(i).setSelectedXEM(false);
                            treeViewLienThong.refreshTreeView();
                        }
                    }
                }
            }
        }


    }

    //check auto peson khi list trả về chỉ có 1. Áp dụng cho all type chuyển
    private void CheckAutoPerson(TreeView treeView, TreeView treeViewLienThong, ArrayList<Person> personArrayList) {
        if (treeView != null) {
            List<TreeNode> allTree = treeView.getAllNodes();
            if (allTree != null) {
                for (int i = 0; i < allTree.size(); i++) {
                    for (int j = 0; j < personArrayList.size(); j++) {
                        if (((PersonSendNotifyInfo) allTree.get(i).getValue()).getId().equals(personArrayList.get(j).getId())) {
                            allTree.get(i).setSelectedXLC(personArrayList.get(j).isXlc());
                            allTree.get(i).setSelectedPH(personArrayList.get(j).isDxl());
                            allTree.get(i).setSelectedXEM(personArrayList.get(j).isXem());
                            treeViewLienThong.updatePositionSelect(allTree.get(i));
                        }
                    }
                }
            }
        }
        if (treeViewLienThong != null) {
            List<TreeNode> allTreeLienthong = treeViewLienThong.getAllNodes();
            if (allTreeLienthong != null) {
                for (int i = 0; i < allTreeLienthong.size(); i++) {
                    for (int j = 0; j < personArrayList.size(); j++) {
                        if (((PersonSendNotifyInfo) allTreeLienthong.get(i).getValue()).getId().equals(personArrayList.get(j).getId())) {
                            allTreeLienthong.get(i).setSelectedXLC(personArrayList.get(j).isXlc());
                            allTreeLienthong.get(i).setSelectedPH(personArrayList.get(j).isDxl());
                            allTreeLienthong.get(i).setSelectedXEM(personArrayList.get(j).isXem());
                            treeViewLienThong.updatePositionSelect(allTreeLienthong.get(i));
                        }
                    }
                }
            }
        }


    }

    private boolean updateDataTreeView(TreeView treeView, List<Person> personList, int typeTreeView) {
        if (treeView == null) {
            return false;
        }
        List<TreeNode> allTree = treeView.getAllNodes();
        if (allTree == null) {
            return false;
        }
        for (int i = 0; i < allTree.size(); i++) {

            for (int j = 0; j < personList.size(); j++) {
                String intIdPerson = "";
                if (type != null && type.equalsIgnoreCase("1")) {
                    if (personList.get(j).getId().contains("|")) {
                        intIdPerson = "U" + personList.get(j).getId().split("\\|")[1];
                    } else {
                        intIdPerson = "U" + personList.get(j).getId();
                    }
                } else {
                    intIdPerson = personList.get(j).getId();
                }
                if (intIdPerson.equalsIgnoreCase(((PersonSendNotifyInfo) allTree.get(i).getValue()).getId())) {
                    if (typeTreeView == 3 || typeTreeView == 4) {
                        if (personList.get(j).isXlc()) {
                            if (treeView.positionSelect() != -1) {
                                allTree.get(treeView.positionSelect()).setSelectedXLC(false);
                            }
                            treeView.updatePositionSelect(allTree.get(i));

                        }
                        allTree.get(i).setSelectedXLC(personList.get(j).isXlc());
                        allTree.get(i).setSelectedPH(personList.get(j).isDxl());
                        allTree.get(i).setSelectedXEM(personList.get(j).isXem());
                    } else if (compare((((PersonSendNotifyInfo) allTree.get(i).getValue()).getParentId()), personList.get(j).getParrentId())) {
                        allTree.get(i).setSelectedXLC(personList.get(j).isXlc());
                        allTree.get(i).setSelectedPH(personList.get(j).isDxl());
                        allTree.get(i).setSelectedXEM(personList.get(j).isXem());
                    }
                }

            }
        }
        return true;
    }

    private boolean checkMultilXLC() {
        return
                appPrefs.getAccount() != null && ((LoginInfo) appPrefs.getAccountLogin()).getMultilPrimary() != null && ((LoginInfo) appPrefs.getAccountLogin()).getMultilPrimary()
                        .equalsIgnoreCase("true") &&
                        (typePersonEvent.getType().equalsIgnoreCase(Constants.TYPE_SEND_PERSON_PROCESS) ||
                                typePersonEvent.getType().equalsIgnoreCase(Constants.TYPE_PERSON_DIRECT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityThis = null;
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeStickyEvent(SelectGroupPersonEvent.class);
        EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
        EventBus.getDefault().removeStickyEvent(SelectGroupSendDocEvent.class);
        EventBus.getDefault().removeStickyEvent(RemoveSelectPersonEvent.class);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SelectGroupPersonEvent selectGroupPersonEvent) {
        if (selectGroupPersonEvent != null && selectGroupPersonEvent.isLoad()) {
            updateDataSelectNormalTreeView(treeView, 3);
            updateDataSelectNormalTreeView(treeViewLienThong, 4);
        }
        EventBus.getDefault().removeStickyEvent(SelectGroupPersonEvent.class);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(RemoveSelectPersonEvent removeSelectPersonEvent) {
        if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_PROCESS)) {
            if (removeSelectPersonEvent.getPersonProcessRemove().size() > 0) {
                for (int i = 0; i < processPersonInfoList.size(); i++) {
                    for (Person person : removeSelectPersonEvent.getPersonProcessRemove()) {
                        if (person.getId().equals(processPersonInfoList.get(i).getUserId())) {
                            processPersonInfoList.get(i).setXlc(false);
                            processPersonInfoList.get(i).setDxl(false);
                            selectProcessV2Adapter.notifyItemChanged(i);
                            break;
                        }
                    }

                }
            }
        } else {
            unselectPersonProgessTreeView(treeView, treeViewLienThong, removeSelectPersonEvent);
        }
        EventBus.getDefault().removeStickyEvent(RemoveSelectPersonEvent.class);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SelectGroupSendDocEvent selectGroupSendDocEvent) {
        if (selectGroupSendDocEvent != null && selectGroupSendDocEvent.isEditEvent()) {
            updateDataSelectNormalTreeView(treeView, 1);
            updateDataSelectNormalTreeView(treeViewLienThong, 2);
        }
        EventBus.getDefault().removeStickyEvent(SelectGroupSendDocEvent.class);
    }


}
