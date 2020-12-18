package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.PersonProcessAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonGroupChangeDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeChangeDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.changedocument.ChangeDocumentPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.changedocument.IChangeDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonPreEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.OnBackEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.StepPre;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IChangeDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IGetTypeChangeDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class SendDocumentActivity extends BaseActivity implements IChangeDocumentView, IGetTypeChangeDocumentView, ILoginView, IExceptionErrorView {

    private IChangeDocumentPresenter changeDocumentPresenter = new ChangeDocumentPresenterImpl(this, this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnSend;
    private TextView tvTitle;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private TypeChangeDocumentRequest typeChangeDocumentRequest;
    private int type;
    private int selected;
    @BindView(R.id.txtNote) EditText txtNote;
    @BindView(R.id.sHinhThucChuyen) Spinner sHinhThucChuyen;
    @BindView(R.id.layoutXuLy) LinearLayout layoutXuLy;
    @BindView(R.id.layoutDongGui) LinearLayout layoutDongGui;
    @BindView(R.id.layoutThongBao) LinearLayout layoutThongBao;
    @BindView(R.id.layoutTructiep) LinearLayout layoutTructiep;
    @BindView(R.id.btnXuly) ImageView btnXuly;
    @BindView(R.id.btnDongGui) ImageView btnDongGui;
    @BindView(R.id.btnThongBao) ImageView btnThongBao;
    @BindView(R.id.btnTructiep) ImageView btnTructiep;
    @BindView(R.id.lstXuLy) ListView lstXuLy;
    @BindView(R.id.lstDongGui) ListView lstDongGui;
    @BindView(R.id.lstThongBao) ListView lstThongBao;
    @BindView(R.id.lstTructiep) ListView lstTructiep;
    @BindView(R.id.txtXyLy) TextView txtXyLy;
    @BindView(R.id.tv_hoten) TextView tv_hoten;
    @BindView(R.id.tv_xuly_chinh) TextView tv_xuly_chinh;
    @BindView(R.id.tv_dong_xuly) TextView tv_dong_xuly;
    @BindView(R.id.txtDongGui) TextView txtDongGui;
    @BindView(R.id.tv_hoten_dong_gui) TextView tv_hoten_dong_gui;
    @BindView(R.id.tv_chon_dong_gui) TextView tv_chon_dong_gui;
    @BindView(R.id.txtThongBao) TextView txtThongBao;
    @BindView(R.id.tv_hoten_thong_bao) TextView tv_hoten_thong_bao;
    @BindView(R.id.tv_chon_thong_bao) TextView tv_chon_thong_bao;
    @BindView(R.id.txtTructiep) TextView txtTructiep;
    @BindView(R.id.tv_hoten_tructiep) TextView tv_hoten_tructiep;
    @BindView(R.id.tv_chon_tructiep) TextView tv_chon_tructiep;
    @BindView(R.id.layoutDisplay) ScrollView layoutDisplay;

    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_document);
        init();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnSend = (ImageView) toolbar.findViewById(R.id.btnSend);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application.getApp().getAppPrefs().removePersonPre();
                onBackPressed();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.isConnectingToInternet()) {
                    send();
                } else {
                    AlertDialogManager.showAlertDialog(SendDocumentActivity.this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        });
    }

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();
        connectionDetector = new ConnectionDetector(this);
        setupToolbar();
        txtNote.setTypeface(Application.getApp().getTypeface());
        typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
        tv_hoten.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_dong_xuly.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_xuly_chinh.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtXyLy.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtDongGui.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_hoten_dong_gui.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_chon_dong_gui.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtThongBao.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_hoten_thong_bao.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_chon_thong_bao.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtTructiep.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_hoten_tructiep.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_chon_tructiep.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;
            }
        });
        TypeChangeEvent typeChangeEvent = EventBus.getDefault().getStickyEvent(TypeChangeEvent.class);
        if (typeChangeEvent == null) {
            layoutXuLy.setVisibility(View.GONE);
            layoutDongGui.setVisibility(View.GONE);
            layoutThongBao.setVisibility(View.GONE);
            layoutTructiep.setVisibility(View.GONE);
            getTypeChange();
            ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(this);
            applicationSharedPreferences.setPersonPre(new ListPersonPreEvent(null, null, null));
        } else {
            setupTypeChange(typeChangeEvent.getTypeChangeDocumentList());
            sHinhThucChuyen.setSelection(typeChangeEvent.getSelected());
            txtNote.setText(typeChangeEvent.getNote());
        }
        displayPerson();
    }

    private void displayPerson() {
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent != null) {
            if (listPersonEvent.getPersonProcessList() != null && listPersonEvent.getPersonProcessList().size() > 0) {
                PersonProcessAdapter personProcessAdapter = new PersonProcessAdapter(this, R.layout.item_person_xuly_list, listPersonEvent.getPersonProcessList(), StringDef.XULY);
                lstXuLy.setAdapter(personProcessAdapter);
                View item = personProcessAdapter.getView(0, null, lstXuLy);
                item.measure(0, 0);
                ViewGroup.LayoutParams params = lstXuLy.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                if (personProcessAdapter.getCount() > 4) {
                    params.height = 2 * item.getMeasuredHeight();
                    lstXuLy.setLayoutParams(params);
//                } else {
//                    params.height = personProcessAdapter.getCount() * item.getMeasuredHeight();
//                    lstXuLy.setLayoutParams(params);
//                }
            } else {
                PersonProcessAdapter personProcessAdapter = new PersonProcessAdapter(this, R.layout.item_person_xuly_list, new ArrayList<Person>(), StringDef.XULY);
                lstXuLy.setAdapter(personProcessAdapter);
                ViewGroup.LayoutParams params = lstXuLy.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            if (listPersonEvent.getPersonSendList() != null && listPersonEvent.getPersonSendList().size() > 0) {
                PersonProcessAdapter personProcessAdapter = new PersonProcessAdapter(this, R.layout.item_person_dong_gui_list, listPersonEvent.getPersonSendList(), StringDef.DONGGUI);
                lstDongGui.setAdapter(personProcessAdapter);
                View item = personProcessAdapter.getView(0, null, lstDongGui);
                item.measure(0, 0);
                ViewGroup.LayoutParams params = lstDongGui.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                //if (personProcessAdapter.getCount() > 4) {
                    params.height = 2 * item.getMeasuredHeight();
                    lstDongGui.setLayoutParams(params);
//                } else {
//                    params.height = personProcessAdapter.getCount() * item.getMeasuredHeight();
//                    lstDongGui.setLayoutParams(params);
//                }
            } else {
                PersonProcessAdapter personProcessAdapter = new PersonProcessAdapter(this, R.layout.item_person_dong_gui_list, new ArrayList<Person>(), StringDef.DONGGUI);
                lstDongGui.setAdapter(personProcessAdapter);
                ViewGroup.LayoutParams params = lstDongGui.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            if (listPersonEvent.getPersonNotifyList() != null && listPersonEvent.getPersonNotifyList().size() > 0) {
                PersonProcessAdapter personProcessAdapter = new PersonProcessAdapter(this, R.layout.item_person_thong_bao_list, listPersonEvent.getPersonNotifyList(), StringDef.THONGBAO);
                lstThongBao.setAdapter(personProcessAdapter);
                View item = personProcessAdapter.getView(0, null, lstThongBao);
                item.measure(0, 0);
                ViewGroup.LayoutParams params = lstThongBao.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                //if (personProcessAdapter.getCount() > 4) {
                    params.height = 2 * item.getMeasuredHeight();
                    lstThongBao.setLayoutParams(params);
//                } else {
//                    params.height = personProcessAdapter.getCount() * item.getMeasuredHeight();
//                    lstThongBao.setLayoutParams(params);
//                }
            } else {
                PersonProcessAdapter personProcessAdapter = new PersonProcessAdapter(this, R.layout.item_person_thong_bao_list, new ArrayList<Person>(), StringDef.THONGBAO);
                lstThongBao.setAdapter(personProcessAdapter);
                ViewGroup.LayoutParams params = lstThongBao.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            if (listPersonEvent.getPersonDirectList() != null && listPersonEvent.getPersonDirectList().size() > 0) {
                PersonProcessAdapter personProcessAdapter = new PersonProcessAdapter(this, R.layout.item_person_tructiep_list, listPersonEvent.getPersonDirectList(), StringDef.TRUCTIEP);
                lstTructiep.setAdapter(personProcessAdapter);
                View item = personProcessAdapter.getView(0, null, lstTructiep);
                item.measure(0, 0);
                ViewGroup.LayoutParams params = lstTructiep.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                //if (personProcessAdapter.getCount() > 4) {
                    params.height = 2 * item.getMeasuredHeight();
                    lstTructiep.setLayoutParams(params);
//                } else {
//                    params.height = personProcessAdapter.getCount() * item.getMeasuredHeight();
//                    lstTructiep.setLayoutParams(params);
//                }
            } else {
                PersonProcessAdapter personProcessAdapter = new PersonProcessAdapter(this, R.layout.item_person_tructiep_list, new ArrayList<Person>(), StringDef.TRUCTIEP);
                lstTructiep.setAdapter(personProcessAdapter);
                ViewGroup.LayoutParams params = lstTructiep.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        }
    }

    private void send() {
//        if (txtNote.getText() == null || txtNote.getText().toString().trim().equals("")) {
//            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_NOTE_SEND), true, AlertDialogManager.INFO);
//            return;
//        }
        if (sHinhThucChuyen.getSelectedItemPosition() == 0) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_TYPE_CHANGE), true, AlertDialogManager.INFO);
            return;
        }
        if (sHinhThucChuyen.getSelectedItemPosition() == -1) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_SUPPORT_TYPE_CHANGE), true, AlertDialogManager.INFO);
            return;
        }
        EventBus.getDefault().postSticky(new StepPre("changeDoc"));
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (type == Constants.TYPE_SEND_NOTIFY || type == Constants.TYPE_SEND_PROCESS) {
            if (type == Constants.TYPE_SEND_PROCESS) {
                List<Person> personProcessList = listPersonEvent.getPersonProcessList();
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
                        ChangeProcessRequest changeProcessRequest = new ChangeProcessRequest();
                        TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                        changeProcessRequest.setDocId(typeChangeDocumentRequest.getDocId());
                       // changeProcessRequest.setXlc(personProcessList.get(index).getId());
                       // changeProcessRequest.setPhoiHop(!dongXuLy.equals("") ? dongXuLy.substring(0, dongXuLy.length() - 1) : "");
                        changeProcessRequest.setComment(txtNote.getText().toString().trim());
                        changeProcessRequest.setType(Constants.TYPE_SEND_PROCESS_REQUEST);
                        changeDocumentPresenter.changeProcess(changeProcessRequest);
                    } else {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                    }
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                }
            }
            if (type == Constants.TYPE_SEND_NOTIFY) {
                List<Person> personNotifyList = listPersonEvent.getPersonNotifyList();
                String dongGui = "";
                if (personNotifyList != null && personNotifyList.size() > 0) {
                    for (Person person : personNotifyList) {
                        if (person.isXlc()) {
                            dongGui += person.getId() + ",";
                        }
                    }
                    TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                    ChangeNotifyRequest changeNotifyRequest = new ChangeNotifyRequest();
                    changeNotifyRequest.setDocId(typeChangeDocumentRequest.getDocId());
                    changeNotifyRequest.setXlc(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                    changeNotifyRequest.setPhoiHop("");
                    changeNotifyRequest.setComment(txtNote.getText().toString().trim());
                    changeNotifyRequest.setType(Constants.TYPE_SEND_NOTIFY_REQUEST);
                    changeDocumentPresenter.changeNotify(changeNotifyRequest);
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_NOTIFY), true, AlertDialogManager.INFO);
                }
            }
        } else {
            TypeChangeEvent typeChangeEvent = EventBus.getDefault().getStickyEvent(TypeChangeEvent.class);
            List<TypeChangeDocument> typeChangeDocumentList = typeChangeEvent.getTypeChangeDocumentList();
            if (typeChangeDocumentList.get(selected - 1).getNextStep().equals("get_cleck_publish")
                    && (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE) ||
                    typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND))) {
                List<Person> personNotifyList = listPersonEvent.getPersonDirectList();
                String dongGui = "";
                if (personNotifyList != null && personNotifyList.size() > 0) {
                    for (Person person : personNotifyList) {
                        if (person.isXlc()) {
                            dongGui += person.getId() + ",";
                        }
                    }
                    TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                    ChangeDocumentDirectRequest changeDocumentDirectRequest = new ChangeDocumentDirectRequest();
                    changeDocumentDirectRequest.setDocId(typeChangeDocumentRequest.getDocId());
                    changeDocumentDirectRequest.setComment(txtNote.getText().toString().trim());
                    changeDocumentDirectRequest.setPrimaryProcess(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                    //changeDocumentDirectRequest.setInterconnect("");
                    changeDocumentDirectRequest.setApprovedValue(typeChangeDocumentList.get(typeChangeEvent.getSelected() - 1).getApprovedValue());
                    changeDocumentDirectRequest.setStrAction(typeChangeDocumentList.get(typeChangeEvent.getSelected() - 1).getName());
                    changeDocumentDirectRequest.setActionType(String.valueOf(type));
                    changeDocumentPresenter.changeDirect(changeDocumentDirectRequest);
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_DIRECT), true, AlertDialogManager.INFO);
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
                            changeDocumentSendRequest.setSFunc(typeChangeDocumentList.get(typeChangeEvent.getSelected() - 1).getNextStep());
                            changeDocumentSendRequest.setSApproved(typeChangeDocumentList.get(typeChangeEvent.getSelected() - 1).getApprovedValue());
                            changeDocumentSendRequest.setSMore(txtNote.getText().toString().trim());
                            changeDocumentSendRequest.setAssigner("");
                            changeDocumentSendRequest.setAct(typeChangeDocumentList.get(typeChangeEvent.getSelected() - 1).getName());
                            changeDocumentSendRequest.setDongGui(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                            changeDocumentSendRequest.setFormId("");
                            changeDocumentSendRequest.setSendType(String.valueOf(type));
                            changeDocumentPresenter.changeSend(changeDocumentSendRequest);
                        }
                        if (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE)) {
                            ChangeDocumentReceiveRequest changeDocumentReceiveRequest = new ChangeDocumentReceiveRequest();
                            changeDocumentReceiveRequest.setDocId(typeChangeDocumentRequest.getDocId());
                            changeDocumentReceiveRequest.setChuTri(personProcessList.get(index).getId());
                            changeDocumentReceiveRequest.setApprovedValue(typeChangeDocumentList.get(typeChangeEvent.getSelected() - 1).getApprovedValue());
                            changeDocumentReceiveRequest.setDongXuLy(!dongXuLy.equals("") ? dongXuLy.substring(0, dongXuLy.length() - 1) : "");
                            changeDocumentReceiveRequest.setStrAction(typeChangeDocumentList.get(typeChangeEvent.getSelected() - 1).getName());
                            changeDocumentReceiveRequest.setProcessDefinitionId(typeChangeDocumentRequest.getProcessDefinitionId());
                            changeDocumentReceiveRequest.setIsBanHanh("0");
                            changeDocumentReceiveRequest.setDongGui(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                            changeDocumentReceiveRequest.setComment(txtNote.getText().toString().trim());
                            changeDocumentReceiveRequest.setSendType(String.valueOf(type));
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

    private void setupTypeChange(final List<TypeChangeDocument> typeChangeDocumentList) {
        List<String> lst = new ArrayList<>();
        lst.add(getString(R.string.DEFAULT_TYPE_CHANGE_DOC));
        for (int i = 0; i < typeChangeDocumentList.size(); i++) {
            lst.add(typeChangeDocumentList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lst) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sHinhThucChuyen.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sHinhThucChuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selected = position;
                if (position > 0) {
                    ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                    if (listPersonEvent == null) {
                        listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
                    }
                    TypeChangeEvent typeChangeEvent = EventBus.getDefault().getStickyEvent(TypeChangeEvent.class);
                    if (position != typeChangeEvent.getSelected()) {
                        listPersonEvent.setPersonNotifyList(null);
                        listPersonEvent.setPersonProcessList(null);
                        listPersonEvent.setPersonSendList(null);
                        listPersonEvent.setPersonDirectList(null);
                    }
                    type = typeChangeDocumentList.get(position - 1).getType();
                    if (type == Constants.TYPE_SEND_NOTIFY || type == Constants.TYPE_SEND_PROCESS) {
                        if (type == Constants.TYPE_SEND_NOTIFY) {
                            layoutTructiep.setVisibility(View.GONE);
                            layoutThongBao.setVisibility(View.VISIBLE);
                            layoutXuLy.setVisibility(View.GONE);
                            layoutDongGui.setVisibility(View.GONE);
                            listPersonEvent.setPersonProcessList(null);
                            listPersonEvent.setPersonSendList(null);
                            listPersonEvent.setPersonDirectList(null);
                        }
                        if (type == Constants.TYPE_SEND_PROCESS) {
                            layoutTructiep.setVisibility(View.GONE);
                            layoutThongBao.setVisibility(View.GONE);
                            layoutXuLy.setVisibility(View.VISIBLE);
                            layoutDongGui.setVisibility(View.GONE);
                            listPersonEvent.setPersonNotifyList(null);
                            listPersonEvent.setPersonSendList(null);
                            listPersonEvent.setPersonDirectList(null);
                        }
                    } else {
                        if (typeChangeDocumentList.get(position - 1).getNextStep().equals("get_cleck_publish") && typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE)) {
                            layoutTructiep.setVisibility(View.VISIBLE);
                            layoutThongBao.setVisibility(View.GONE);
                            layoutXuLy.setVisibility(View.GONE);
                            layoutDongGui.setVisibility(View.GONE);
                            listPersonEvent.setPersonNotifyList(null);
                            listPersonEvent.setPersonProcessList(null);
                            listPersonEvent.setPersonSendList(null);
                        } else {
                            layoutTructiep.setVisibility(View.GONE);
                            layoutThongBao.setVisibility(View.GONE);
                            layoutXuLy.setVisibility(View.VISIBLE);
                            layoutDongGui.setVisibility(View.VISIBLE);
                            EventBus.getDefault().postSticky(new ListProcessRequest(typeChangeDocumentList.get(position - 1).getNextStep(),
                                    typeChangeDocumentList.get(position - 1).getRoleId(), typeChangeDocumentList.get(position - 1).getApprovedValue(),
                                    typeChangeDocumentRequest.getDocId(), ""));
                            listPersonEvent.setPersonNotifyList(null);
                            listPersonEvent.setPersonDirectList(null);
                        }
                    }
                    EventBus.getDefault().postSticky(listPersonEvent);
                } else {
                    layoutTructiep.setVisibility(View.GONE);
                    layoutXuLy.setVisibility(View.GONE);
                    layoutDongGui.setVisibility(View.GONE);
                    layoutThongBao.setVisibility(View.GONE);
                    EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
                }
                displayPerson();
                EventBus.getDefault().postSticky(new TypeChangeEvent("", position, typeChangeDocumentList));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getTypeChange() {
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new StepPre("getTypeChange"));
            changeDocumentPresenter.getTypeChangeDocument(new TypeChangeDocRequest(typeChangeDocumentRequest.getDocId(), typeChangeDocumentRequest.getProcessDefinitionId()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @OnClick({R.id.btnXuly, R.id.btnDongGui, R.id.btnThongBao, R.id.btnTructiep})
    public void addPerson(View view) {
        switch (view.getId()) {
            case R.id.btnXuly:
                if (type == Constants.TYPE_SEND_PROCESS) {
                    EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_SEND_PERSON_PROCESS));
                } else {
                    EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_PROCESS));
                }
                break;
            case R.id.btnDongGui:
                EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_SEND));
                break;
            case R.id.btnThongBao:
                EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_NOTIFY));
                break;
            case R.id.btnTructiep:
                EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_DIRECT));
                break;
        }
        TypeChangeEvent typeChangeEvent = EventBus.getDefault().getStickyEvent(TypeChangeEvent.class);
        typeChangeEvent.setNote(txtNote.getText().toString());
        EventBus.getDefault().postSticky(typeChangeEvent);
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(this);
        ListPersonPreEvent listPersonPreEvent = applicationSharedPreferences.getPersonPre();
        listPersonPreEvent.setPersonProcessPreList(listPersonEvent.getPersonProcessList());
        listPersonPreEvent.setPersonSendPreList(listPersonEvent.getPersonSendList());
        listPersonPreEvent.setPersonNotifyPreList(listPersonEvent.getPersonNotifyList());
        Application.getApp().getAppPrefs().setPersonPre(listPersonPreEvent);
        startActivity(new Intent(this, SelectPersonActivityNewConvert.class).putExtra("DOCID",Integer.parseInt(typeChangeDocumentRequest.getDocId())));
        finish();
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        String step = EventBus.getDefault().getStickyEvent(StepPre.class).getCall();
        if (connectionDetector.isConnectingToInternet()) {
            if (step.equals("getTypeChange")) {
                changeDocumentPresenter.getTypeChangeDocument(new TypeChangeDocRequest(typeChangeDocumentRequest.getDocId(), typeChangeDocumentRequest.getProcessDefinitionId()));
            }
            if (step.equals("changeDoc")) {
                send();
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(SendDocumentActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSuccessTypeChange(final List<TypeChangeDocument> typeChangeDocumentList) {
        List<String> lst = new ArrayList<>();
        lst.add(getString(R.string.DEFAULT_TYPE_CHANGE_DOC));
        for (int i = 0; i < typeChangeDocumentList.size(); i++) {
            lst.add(typeChangeDocumentList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lst) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sHinhThucChuyen.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sHinhThucChuyen.setSelection(0);
        sHinhThucChuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selected = position;
                if (position > 0) {
                    ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                    if (listPersonEvent == null) {
                        listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
                    }
                    type = typeChangeDocumentList.get(position - 1).getType();
                    TypeChangeEvent typeChangeEvent = EventBus.getDefault().getStickyEvent(TypeChangeEvent.class);
                    if (position != typeChangeEvent.getSelected()) {
                        listPersonEvent.setPersonNotifyList(null);
                        listPersonEvent.setPersonProcessList(null);
                        listPersonEvent.setPersonSendList(null);
                        listPersonEvent.setPersonDirectList(null);
                    }
                    if (type == Constants.TYPE_SEND_NOTIFY || type == Constants.TYPE_SEND_PROCESS) {
                        if (type == Constants.TYPE_SEND_NOTIFY) {
                            layoutTructiep.setVisibility(View.GONE);
                            layoutThongBao.setVisibility(View.VISIBLE);
                            layoutXuLy.setVisibility(View.GONE);
                            layoutDongGui.setVisibility(View.GONE);
                            listPersonEvent.setPersonProcessList(null);
                            listPersonEvent.setPersonSendList(null);
                            listPersonEvent.setPersonDirectList(null);
                        }
                        if (type == Constants.TYPE_SEND_PROCESS) {
                            layoutTructiep.setVisibility(View.GONE);
                            layoutThongBao.setVisibility(View.GONE);
                            layoutXuLy.setVisibility(View.VISIBLE);
                            layoutDongGui.setVisibility(View.GONE);
                            listPersonEvent.setPersonNotifyList(null);
                            listPersonEvent.setPersonSendList(null);
                            listPersonEvent.setPersonDirectList(null);
                        }
                    } else {
                        if (typeChangeDocumentList.get(position - 1).getNextStep() != null && typeChangeDocumentList.get(position - 1).getNextStep().equals("get_cleck_publish")
                                && typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE)) {
                            layoutTructiep.setVisibility(View.VISIBLE);
                            layoutThongBao.setVisibility(View.GONE);
                            layoutXuLy.setVisibility(View.GONE);
                            layoutDongGui.setVisibility(View.GONE);
                            listPersonEvent.setPersonNotifyList(null);
                            listPersonEvent.setPersonProcessList(null);
                            listPersonEvent.setPersonSendList(null);
                        } else {
                            layoutTructiep.setVisibility(View.GONE);
                            layoutThongBao.setVisibility(View.GONE);
                            layoutXuLy.setVisibility(View.VISIBLE);
                            layoutDongGui.setVisibility(View.VISIBLE);
                            EventBus.getDefault().postSticky(new ListProcessRequest(typeChangeDocumentList.get(position - 1).getNextStep(),
                                    typeChangeDocumentList.get(position - 1).getRoleId(), typeChangeDocumentList.get(position - 1).getApprovedValue(),
                                    typeChangeDocumentRequest.getDocId(), ""));
                            listPersonEvent.setPersonNotifyList(null);
                            listPersonEvent.setPersonDirectList(null);
                        }
                    }
                    EventBus.getDefault().postSticky(listPersonEvent);
                } else {
                    EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
                    layoutTructiep.setVisibility(View.GONE);
                    layoutXuLy.setVisibility(View.GONE);
                    layoutDongGui.setVisibility(View.GONE);
                    layoutThongBao.setVisibility(View.GONE);
                }
                EventBus.getDefault().postSticky(new TypeChangeEvent("", position, typeChangeDocumentList));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onErrorTypeChange(APIError apiError) {
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
    public void onSuccessChangeDoc() {
        showAlertDialog(getString(R.string.TITLE_SUCCESS), getString(R.string.CHANGE_DOC_SUCCESS));
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

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        final AlertDialog alertDialog = builder.create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        alertDialog.setIcon(R.drawable.success);

        // Setting OK Button
        alertDialog.setButton(getApplicationContext().getString(R.string.CLOSE_BUTTON), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                Application.getApp().getAppPrefs().removePersonPre();
                EventBus.getDefault().postSticky(new OnBackEvent(Constants.HOME_DEN_CHO_XU_LY));
                startActivity(new Intent(SendDocumentActivity.this, MainActivity.class));
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void showAlertDialogError(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        final AlertDialog alertDialog = builder.create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        alertDialog.setIcon(R.drawable.error);

        // Setting OK Button
        alertDialog.setButton(getApplicationContext().getString(R.string.CLOSE_BUTTON), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                Application.getApp().getAppPrefs().removePersonPre();
                EventBus.getDefault().postSticky(new OnBackEvent(Constants.HOME_DEN_CHO_XU_LY));
                startActivity(new Intent(SendDocumentActivity.this, MainActivity.class));
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
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
        } else {
            if (apiError.getMessage().equals("changeFail")) {
                String step = EventBus.getDefault().getStickyEvent(StepPre.class).getCall();
                if (step.equals("changeDoc")) {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_DOC_TITLE_ERROR), getString(R.string.CHANGE_DOC_FAIL), true, AlertDialogManager.ERROR);
                }
            }
            if (apiError.getMessage().contains("document_processed")) {
                String step = EventBus.getDefault().getStickyEvent(StepPre.class).getCall();
                if (step.equals("changeDoc")) {
                    showAlertDialogError(getString(R.string.CHANGE_DOC_TITLE_ERROR), getString(R.string.CHANGED_DOC));
                }
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
