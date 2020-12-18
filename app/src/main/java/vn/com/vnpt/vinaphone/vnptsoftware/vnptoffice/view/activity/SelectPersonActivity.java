package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SelectLienThongAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SelectProcessAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SelectSendNotifyAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SelectSendProcessAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SelectSendXemDBAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SearchPersonRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.JobPositionInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LienThongInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonProcessInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.changedocument.IChangeDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonProcessCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonSendNotifyCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonSendNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SelectGroupPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonProcessCheck;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonSendNotifyCheck;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IFilterPersonView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class SelectPersonActivity extends BaseActivity implements  ILoginView, IFilterPersonView, IExceptionErrorView {

    @BindView(R.id.layout_donvi_lt)
    LinearLayout layout_donvi_lt;
    @BindView(R.id.layout_send_lienthong)
    LinearLayout layout_send_lienthong;
    @BindView(R.id.btnMorongXL)
    TextView btnMorongXL;
    @BindView(R.id.btnThuGonXL)
    TextView btnThuGonXL;
    @BindView(R.id.btnMorongLT)
    TextView btnMorongLT;
    @BindView(R.id.btnThuGonLT)
    TextView btnThuGonLT;
    @BindView(R.id.layout_person_process)
    LinearLayout layoutPersonProcess;
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
//    private IChangeDocumentPresenter changeDocumentPresenter = new ChangeDocumentPresenterImpl(this, this);
    private IChangeDocumentPresenter changeDocumentPresenter;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;
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
    private boolean isExpandLienThong;
    private boolean isExpandProcess;
    private boolean isLienThong;
    private String type;

    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person);
        ButterKnife.bind(this);
        init();
        getPersons();
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
                TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
                boolean isSelected = true;
                boolean isSelectedXLC = false;
                boolean isSelectedLT = true;
                boolean isSelectedXLCLT = false;
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
                                personList = listPersonEvent.getPersonProcessList();
                                personList = send_process_person();
                                if (personList != null && personList.size() > 0) {
//                                    for (Person person : personList) {
//                                        if (person.isXlc()) {
//                                            isSelectedXLC = true;
//                                            break;
//                                        }
//                                    }
                                    isSelectedXLC = true;
                                    listPersonEvent.setPersonProcessList(personList);
                                } else {
                                    isSelected = false;
                                }
                                if (isLienThong) {
                                    personList = listPersonEvent.getPersonLienThongList();
                                    if (personList != null && personList.size() > 0) {
//                                        for (Person person : personList) {
//                                            if (person.isXlc()) {
//                                                isSelectedXLCLT = true;
//                                                break;
//                                            }
//                                        }
                                        isSelectedXLCLT = true;
                                        listPersonEvent.setPersonLienThongList(personList);
                                    } else {
                                        isSelectedLT = false;
                                    }
                                }
                                break;
                            case Constants.TYPE_PERSON_DIRECT:
                                personList = listPersonEvent.getPersonDirectList();
                                personList = send_notify_person_new();
                                if (personList != null && personList.size() > 0) {
//                                    for (Person person : personList) {
//                                        if (person.isXlc()) {
//                                            isSelectedXLC = true;
//                                            break;
//                                        }
//                                    }
                                    isSelectedXLC = true;
                                    listPersonEvent.setPersonDirectList(personList);
                                } else {
                                    isSelected = false;
                                }
                                if (isLienThong) {
                                    personList = listPersonEvent.getPersonLienThongList();
                                    if (personList != null && personList.size() > 0) {
//                                        for (Person person : personList) {
//                                            if (person.isXlc()) {
//                                                isSelectedXLCLT = true;
//                                                break;
//                                            }
//                                        }
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
                            personList = listPersonEvent.getPersonNotifyList();
                            personList = send_xemdb_person();
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
                        EventBus.getDefault().postSticky(listPersonEvent);
                    }
                }
                if (isSelected) {
                    if (isSelectedXLC || isSelectedXLCLT) {
                        startActivity(new Intent(SelectPersonActivity.this, NewSendDocumentActivity.class));
                        finish();
                    } else {
                        AlertDialogManager.showAlertDialog(SelectPersonActivity.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                    }
                } else {
                    if (isSelectedLT) {
                        if (isSelectedXLC || isSelectedXLCLT) {
                            startActivity(new Intent(SelectPersonActivity.this, NewSendDocumentActivity.class));
                            finish();
                        } else {
                            AlertDialogManager.showAlertDialog(SelectPersonActivity.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(SelectPersonActivity.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                    }
                }
                listPersonEvent.setPersonGroupList(new ArrayList<Person>());
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        });
    }

    private List<Person> send_process_person() {
        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
        //if (personList == null) {
        List<Person> personList = new ArrayList<>();
        //}
        if (personSendNotifyChecks != null && personSendNotifyChecks.size() > 0) {
            for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                View view = personSendNotifyChecks.get(i).getView();
                RadioButton checkXLChinh = (RadioButton) view.findViewById(R.id.checkXLChinh);
                CheckBox checkDongXL = (CheckBox) view.findViewById(R.id.checkDongXL);
                CheckBox checkXemDB = (CheckBox) view.findViewById(R.id.checkXemDB);
                //if (personSendNotifyChecks.get(i).isNotParent()) {
                if (checkDongXL.isChecked()) {
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
                                    personSendNotifyInfoList.get(j).getName(), null, false, true, false, 0);
                            personList.add(person);
                            break;
                        }
                    }
                    //}
                }
                //}
                if (checkXemDB.isChecked()) {
                    List<PersonSendNotifyInfo> personSendNotifyInfoList = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
                    for (int j = 0; j < personSendNotifyInfoList.size(); j++) {
                        if (personSendNotifyInfoList.get(j).getId().equals(personSendNotifyChecks.get(i).getId())) {
                            Person person = new Person(personSendNotifyChecks.get(i).getId(), personSendNotifyChecks.get(i).getName(),
                                    personSendNotifyInfoList.get(j).getName(), null, false, false, true, 0);
                            personList.add(person);
                            break;
                        }
                    }
                }
                if (checkXLChinh.isChecked()) {
//                    int index = -1;
//                    for (int j = 0; j < personList.size(); j++) {
//                        if (personSendNotifyChecks.get(i).getId().equals(personList.get(j).getId())) {
//                            index = j;
//                            break;
//                        }
//                    }
                    //if (index == -1) {
                    List<PersonSendNotifyInfo> personSendNotifyInfoList = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
                    for (int j = 0; j < personSendNotifyInfoList.size(); j++) {
                        if (personSendNotifyInfoList.get(j).getId().equals(personSendNotifyChecks.get(i).getId())) {
                            Person person = new Person(personSendNotifyChecks.get(i).getId(), personSendNotifyChecks.get(i).getName(),
                                    personSendNotifyInfoList.get(j).getName(), null, true, false, false, 0);
                            personList.add(person);
                            break;
                        }
                    }
//                    } else {
//                        personList.get(index).setXlc(true);
//                        personList.get(index).setDxl(false);
//                    }
                }
            }
        }
        return personList;
    }

    private List<Person> send_xemdb_person() {
        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
        //if (personList == null) {
        List<Person> personList = new ArrayList<>();
        //}
        if (personSendNotifyChecks != null && personSendNotifyChecks.size() > 0) {
            for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                View view = personSendNotifyChecks.get(i).getView();
                CheckBox checkXemDB = (CheckBox) view.findViewById(R.id.checkXemDB);
                if (checkXemDB.isChecked()) {
                    List<PersonSendNotifyInfo> personSendNotifyInfoList = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
                    for (int j = 0; j < personSendNotifyInfoList.size(); j++) {
                        if (personSendNotifyInfoList.get(j).getId().equals(personSendNotifyChecks.get(i).getParentId())) {
                            Person person = new Person(personSendNotifyChecks.get(i).getId(), personSendNotifyChecks.get(i).getName(),
                                    personSendNotifyInfoList.get(j).getName(), null, false, false, true, 0);
                            personList.add(person);
                            break;
                        }
                    }
                }
            }
        }
        return personList;
    }

    private List<Person> send_notify_person_new() {
        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
        //if (personList == null) {
        List<Person> personList = new ArrayList<>();
        //}
        if (personSendNotifyChecks != null && personSendNotifyChecks.size() > 0) {
            for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                View view = personSendNotifyChecks.get(i).getView();
                RadioButton checkXLChinh = (RadioButton) view.findViewById(R.id.checkXLChinh);
                CheckBox checkDongXL = (CheckBox) view.findViewById(R.id.checkDongXL);
                CheckBox checkXemDB = (CheckBox) view.findViewById(R.id.checkXemDB);
                //if (personSendNotifyChecks.get(i).isNotParent()) {
                if (checkDongXL.isChecked()) {
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
                                    personSendNotifyInfoList.get(j).getName(), null, false, true, false, 0);
                            personList.add(person);
                            break;
                        }
                    }
                    //}
                }
                //}
                if (checkXemDB.isChecked()) {
                    List<PersonSendNotifyInfo> personSendNotifyInfoList = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
                    for (int j = 0; j < personSendNotifyInfoList.size(); j++) {
                        if (personSendNotifyInfoList.get(j).getId().equals(personSendNotifyChecks.get(i).getId())) {
                            Person person = new Person(personSendNotifyChecks.get(i).getId(), personSendNotifyChecks.get(i).getName(),
                                    personSendNotifyInfoList.get(j).getName(), null, false, false, true, 0);
                            personList.add(person);
                            break;
                        }
                    }
                }
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
                                    personSendNotifyInfoList.get(j).getName(), null, true, false, false, 0);
                            personList.add(person);
                            break;
                       } //else {
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
                                    personSendNotifyInfoList.get(j).getName(), null, true, false, false, 0);
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

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();
        isExpandProcess = true;
        isExpandLienThong = false;
        isLienThong = false;
        layout_person.setVisibility(View.VISIBLE);
        layout_donvi_lt.setVisibility(View.GONE);
        btnThuGonLT.setVisibility(View.GONE);
        btnMorongLT.setVisibility(View.VISIBLE);
        btnThuGonXL.setVisibility(View.VISIBLE);
        btnMorongXL.setVisibility(View.GONE);
        setupToolbar();
        connectionDetector = new ConnectionDetector(this);
        progressDialog = new ProgressDialog(this);
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
                                        TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
                                        if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                                            changeDocumentPresenter.getPersonSend(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                                        } else {
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

    private void getPersons() {
        if (connectionDetector.isConnectingToInternet()) {
            TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
            if (typePersonEvent != null) {
                switch (typePersonEvent.getType()) {
                    case Constants.TYPE_PERSON_PROCESS:
                        changeDocumentPresenter.getPersonProcess(EventBus.getDefault().getStickyEvent(ListProcessRequest.class));
                        break;
                    default:
                        //changeDocumentPresenter.getJobPossitions();
                        if (typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)) {
                            changeDocumentPresenter.getUnits(0);
                            btnSelectDV.setVisibility(View.GONE);
                            changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest("", "", ""));
                            getLienThongChuyenXL();
                        } else {
                            if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                                changeDocumentPresenter.getUnits(1);
                                changeDocumentPresenter.getPersonSend(new SearchPersonRequest("", "", ""));
                                getLienThongChuyenBH();
                            } else {
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
            changeDocumentPresenter.getLienThongBH(typeChangeDocumentRequest.getDocId());
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
        layoutPersonProcess.removeAllViewsInLayout();
        if (processInfoList != null && processInfoList.size() > 0) {
            List<PersonProcessCheck> personProcessChecks = new ArrayList<>();
            EventBus.getDefault().postSticky(new PersonProcessCheckEvent(personProcessChecks));
            SelectProcessAdapter selectProcessAdapter = new SelectProcessAdapter(this, R.layout.item_select_process_list, processInfoList);
            int adapterCount = selectProcessAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = selectProcessAdapter.getView(i, null, null);
                layoutPersonProcess.addView(item);
            }
        }
    }

    private void displayLienThong(List<LienThongInfo> lienThongInfoList) {
        isLienThong = true;
        layout_send_lienthong.setVisibility(View.VISIBLE);
        layout_donvi_lt.removeAllViewsInLayout();
        if (lienThongInfoList != null && lienThongInfoList.size() > 0) {
            List<PersonProcessCheck> personProcessChecks = new ArrayList<>();
            EventBus.getDefault().postSticky(new PersonProcessCheckEvent(personProcessChecks));
            SelectLienThongAdapter selectProcessAdapter = new SelectLienThongAdapter(this, R.layout.item_person_lienthong_list, lienThongInfoList);
            int adapterCount = selectProcessAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = selectProcessAdapter.getView(i, null, null);
                //layout_donvi_lt.addView(item);
            }
        }
    }

    private void displaySendProcess(List<PersonSendNotifyInfo> personSendNotifyInfoList) {
        layoutFilter.setVisibility(View.VISIBLE);
        EventBus.getDefault().postSticky(new PersonSendNotifyEvent(personSendNotifyInfoList));
        layout_process.setVisibility(View.VISIBLE);
        layout_send_notify.setVisibility(View.GONE);
        layoutProcessSend.setVisibility(View.GONE);
        layoutSendXemdb.setVisibility(View.GONE);
        layout_person.removeAllViewsInLayout();
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
            SelectSendProcessAdapter selectSendProcessAdapter = new SelectSendProcessAdapter(this, R.layout.item_person_send_process_list, R.layout.item_person_send_process_detail, lstTitle, sizes, touchs, type);
            int adapterCount = selectSendProcessAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = selectSendProcessAdapter.getView(i, null, null);
                PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                PersonSendNotifyCheck personSendNotifyCheck = new PersonSendNotifyCheck(item, lstTitle.get(i).getId(), null, lstTitle.get(i).getName(), null, true);
                personSendNotifyChecks.add(personSendNotifyCheck);
                layout_person.addView(item);
                EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
            }
        }
    }

    private void displaySendXemDB(List<PersonSendNotifyInfo> personSendNotifyInfoList) {
        layoutFilter.setVisibility(View.VISIBLE);
        EventBus.getDefault().postSticky(new PersonSendNotifyEvent(personSendNotifyInfoList));
        layout_process.setVisibility(View.GONE);
        layout_send_notify.setVisibility(View.GONE);
        layoutProcessSend.setVisibility(View.GONE);
        layoutSendXemdb.setVisibility(View.VISIBLE);
        layoutDonviXemdb.removeAllViewsInLayout();
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
            SelectSendXemDBAdapter selectSendProcessAdapter = new SelectSendXemDBAdapter(this, R.layout.item_person_send_xemdb_list, R.layout.item_person_send_xemdb_detail, lstTitle, sizes, touchs);
            int adapterCount = selectSendProcessAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = selectSendProcessAdapter.getView(i, null, null);
                PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                PersonSendNotifyCheck personSendNotifyCheck = new PersonSendNotifyCheck(item, lstTitle.get(i).getId(), null, lstTitle.get(i).getName(), null, true);
                personSendNotifyChecks.add(personSendNotifyCheck);
                layoutDonviXemdb.addView(item);
                EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
            }
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
                    TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
                    if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                        changeDocumentPresenter.getPersonSend(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                    } else {
                        changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
//        ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(this);
//        ListPersonPreEvent listPersonPreEvent = applicationSharedPreferences.getPersonPre();
//        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
//        listPersonEvent.setPersonProcessList(listPersonPreEvent.getPersonProcessPreList());
//        listPersonEvent.setPersonSendList(listPersonPreEvent.getPersonSendPreList());
//        listPersonEvent.setPersonNotifyList(listPersonPreEvent.getPersonNotifyPreList());
//        EventBus.getDefault().postSticky(listPersonEvent);
//        startActivity(new Intent(this, SendDocumentActivity.class));
        finish();
    }

    public void onSuccess(Object listPerson) {
        TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
        if (typePersonEvent != null) {
            switch (typePersonEvent.getType()) {
                case Constants.TYPE_PERSON_PROCESS:
                    List<PersonProcessInfo> processInfoList = ConvertUtils.fromJSONList(listPerson, PersonProcessInfo.class);
                    displayPersonProcess(processInfoList);
                    break;
                case Constants.TYPE_SEND_PERSON_PROCESS:
                    List<PersonSendNotifyInfo> personSendNotifyInfos = ConvertUtils.fromJSONList(listPerson, PersonSendNotifyInfo.class);
                    displaySendProcess(personSendNotifyInfos);
                    break;
                case Constants.TYPE_PERSON_DIRECT:
                    List<PersonSendNotifyInfo> personSendNotifyInfo1s = ConvertUtils.fromJSONList(listPerson, PersonSendNotifyInfo.class);
                    displaySendProcess(personSendNotifyInfo1s);
                    break;
                case Constants.TYPE_SEND_VIEW:
                    List<PersonSendNotifyInfo> personSendNotifyInfo2s = ConvertUtils.fromJSONList(listPerson, PersonSendNotifyInfo.class);
                    displaySendXemDB(personSendNotifyInfo2s);
                    break;
                default:
                    List<PersonSendNotifyInfo> personSendNotifyInfoList = ConvertUtils.fromJSONList(listPerson, PersonSendNotifyInfo.class);
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

    public void onSuccessLienThong(List<LienThongInfo> lienThongInfos) {
        if (lienThongInfos != null && lienThongInfos.size() > 0) {
            displayLienThong(lienThongInfos);
        } else {
            layout_send_lienthong.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btnSearch, R.id.btnSelectCN, R.id.btnSelectDV})
    public void searchPerson(View view) {
        Intent intent = new Intent(this, SelectGroupPersonActivity.class);
        if (connectionDetector.isConnectingToInternet()) {
            TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
            ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
            switch (view.getId()) {
                case R.id.btnSearch:
                    if (txtName.getText() != null && !txtName.getText().toString().trim().equals("")) {
                        if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                            changeDocumentPresenter.getPersonSend(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                        } else {
                            changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
                        }
                    } else {
                        if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                            changeDocumentPresenter.getPersonSend(new SearchPersonRequest(unitSelected, "", jobSelected));
                        } else {
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
        TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
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
                        personList = listPersonEvent.getPersonProcessList();
                        personList = send_process_person();
                            listPersonEvent.setPersonProcessList(personList);
                        if (isLienThong) {
                            personList = listPersonEvent.getPersonLienThongList();
                            if (personList != null && personList.size() > 0) {
                                listPersonEvent.setPersonLienThongList(personList);
                            }
                        }
                        break;
                    case Constants.TYPE_PERSON_DIRECT:
                        personList = listPersonEvent.getPersonDirectList();
                        personList = send_notify_person_new();
                        if (personList != null && personList.size() > 0) {
                            listPersonEvent.setPersonDirectList(personList);
                        }
                        if (isLienThong) {
                            personList = listPersonEvent.getPersonLienThongList();
                            if (personList != null && personList.size() > 0) {
                                listPersonEvent.setPersonLienThongList(personList);
                            }
                        }
                        break;
                }
                EventBus.getDefault().postSticky(listPersonEvent);
            } else {
                if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                    personList = listPersonEvent.getPersonNotifyList();
                    personList = send_xemdb_person();
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
            Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
            TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
            if (typePersonEvent != null) {
                switch (typePersonEvent.getType()) {
                    case Constants.TYPE_PERSON_PROCESS:
                        changeDocumentPresenter.getPersonProcess(EventBus.getDefault().getStickyEvent(ListProcessRequest.class));
                        break;
                    default:
                        //changeDocumentPresenter.getJobPossitions();
                        if (typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)) {
                            changeDocumentPresenter.getUnits(0);
                            changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest("", "", ""));
                            getLienThongChuyenXL();
                        } else {
                            if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                                changeDocumentPresenter.getUnits(1);
                                changeDocumentPresenter.getPersonSend(new SearchPersonRequest("", "", ""));
                                getLienThongChuyenBH();
                            } else {
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
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(SelectPersonActivity.this, LoginActivity.class));
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SelectGroupPersonEvent selectGroupPersonEvent) {
        if (selectGroupPersonEvent != null && selectGroupPersonEvent.isLoad()) {
            getPersons();
        }
        EventBus.getDefault().removeStickyEvent(SelectGroupPersonEvent.class);
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
