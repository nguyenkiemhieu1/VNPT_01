package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.filebrowser.FileChooser;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.FileChuyenVanBanAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.PersonSendDocAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.EventCheckSMS;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.EventTypeQLVB;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FinishEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KhoVanBanEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocNotificationEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocWaitingtEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.RemoveSelectPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SelectGroupSendDocEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TaiFileNewSendEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeTuDongGiaoViecEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonSendDoc;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IChangeDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class NewSendDocumentActivity extends BaseActivity implements IChangeDocumentView, ILoginView, IExceptionErrorView {

    @BindView(R.id.txtNote)
    EditText txtNote;
    @BindView(R.id.layoutXLC)
    LinearLayout layoutXLC;
    @BindView(R.id.layoutXLCTitle)
    LinearLayout layoutXLCTitle;
    @BindView(R.id.layoutPH)
    LinearLayout layoutPH;
    @BindView(R.id.layoutPHTitle)
    LinearLayout layoutPHTitle;
    @BindView(R.id.layoutXemDB)
    LinearLayout layoutXemDB;
    @BindView(R.id.layoutXemDBTitle)
    LinearLayout layoutXemDBTitle;
    @BindView(R.id.layoutDisplay)
    ScrollView layoutDisplay;
    @BindView(R.id.checkSMS)
    CheckBox checkSMS;
    @BindView(R.id.ll_send_sms)
    LinearLayout llSendSms;
    @BindView(R.id.ll_auto_send_job)
    LinearLayout ll_auto_send_job;
    @BindView(R.id.checkKetThucVanBan)
    CheckBox checkKetThucVanBan;
    @BindView(R.id.tv_form_content)
    TextView tvFormContent;
    @BindView(R.id.tv_delete_content)
    TextView tvDeleteContent;
    @BindView(R.id.btnChonNgay)
    ImageView btnChonNgay;
    @BindView(R.id.tvHanXuaLy)
    TextView tvHanXuaLy;
    @BindView(R.id.ln_han_xua_ly)
    LinearLayout ln_han_xua_ly;
    @BindView(R.id.ln_tu_dong_giao_viec)
    LinearLayout ln_tu_dong_giao_viec;
    @BindView(R.id.checkTuDongGiaoViec)
    CheckBox checkTuDongGiaoViec;
    @BindView(R.id.btnSelectFile)
    TextView btnSelectFile;
    @BindView(R.id.layoutFile)
    LinearLayout layoutFile;
    @BindView(R.id.ln_upLoadFile)
    LinearLayout ln_upLoadFile;
    private static final int SELECT_FILE_RESULT_SUCCESS = 1;
    private ArrayList<Uri> selectedFiles;
    private ArrayList<Uri> selectedOldFiles;
    private List<String> fileNames;
    private List<Object> listId = new ArrayList<>();
    private IChangeDocumentPresenter changeDocumentPresenter = new ChangeDocumentPresenterImpl(this);
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
    private List<String> listDataFormSend = new ArrayList<>();
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private List<Person> listPersonProgress = new ArrayList<>();
    private List<Person> listPersonLienthong = new ArrayList<>();
    private List<Person> listPersonDirect = new ArrayList<>();
    private List<Person> listPersonNotify = new ArrayList<>();
    private ListPersonEvent listPersonEvent;
    private TypePersonEvent typePersonEvent;
    private ArrayList<Person> listUnseclectPersonProgress = new ArrayList<>();
    private ArrayList<Person> listUnseclectPersonDirect = new ArrayList<>();
    private ArrayList<Person> listUnseclectPersonNotify = new ArrayList<>();
    private ArrayList<Person> listUnseclectPersonLienthong = new ArrayList<>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_send_document);
        init();
        ButterKnife.bind(this);
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
                onBackPressed();
            }
        });
        final TaiFileNewSendEvent taiFileNewSendEvent = EventBus.getDefault().getStickyEvent(TaiFileNewSendEvent.class);
        if (taiFileNewSendEvent != null && taiFileNewSendEvent.getUploadFile() != null) {
            if (taiFileNewSendEvent.getUploadFile().equalsIgnoreCase("true")) {
                ln_upLoadFile.setVisibility(View.VISIBLE);
            } else {
                ln_upLoadFile.setVisibility(View.GONE);
            }
        } else ln_upLoadFile.setVisibility(View.GONE);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.isConnectingToInternet()) {
                    if (taiFileNewSendEvent != null && taiFileNewSendEvent.getUploadFile() != null) {
                        if (taiFileNewSendEvent.getUploadFile().equalsIgnoreCase("true")) {
                            //Gửi file lên sv rồi lấy id file để dùng cho phương thức send()
                            sendFileName();
                        } else send();
                    } else {
                        send();
                    }
                } else {
                    AlertDialogManager.showAlertDialog(NewSendDocumentActivity.this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        });
        tvFormContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listDataFormSend != null && listDataFormSend.size() > 0) {
                    showDialogSelectForm();
                } else {
                    callApiGetListDataForm();
                }
            }
        });
        tvDeleteContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNote.setText("");
            }
        });
        btnChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnyDay();
            }
        });
        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(NewSendDocumentActivity.this, FileChooser.class);
                i2.putExtra(com.aditya.filebrowser.Constants.SELECTION_MODE, com.aditya.filebrowser.Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
                startActivityForResult(i2, SELECT_FILE_RESULT_SUCCESS);
            }
        });

    }

    private void getAnyDay() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                //Lấy ngày sau khi chọn datepicker
                String strDay = "";
                String strMonth = "";
                if (dayOfMonth < 10) {
                    strDay = "0" + String.valueOf(dayOfMonth);
                } else {
                    strDay = String.valueOf(dayOfMonth);
                }
                if (monthOfYear < 9) {
                    strMonth = "0" + String.valueOf(monthOfYear + 1);
                } else {
                    strMonth = String.valueOf(monthOfYear + 1);
                }
                String strDate = String.valueOf(strDay) + "/" + String.valueOf(strMonth)
                        + "/" + String.valueOf(year);
                tvHanXuaLy.setText(getString(R.string.tv_ngay_hop, strDate));
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePicker.show();
    }

    private void callApiGetListDataForm() {
        changeDocumentPresenter.getListFormContent();
    }

    private void showDialogSelectForm() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_dialog_form_content_send);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView lvFormContent = (ListView) dialog.findViewById(R.id.lv_form_content);
        final ImageView dismissDialog = (ImageView) dialog.findViewById(R.id.iv_dismiss_dialog);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.textview_form, listDataFormSend);
        lvFormContent.setAdapter(adapter);
        lvFormContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtNote.setText(listDataFormSend.get(position));
                txtNote.setSelection(txtNote.getText().length());
                dialog.dismiss();
            }
        });
        dismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void init() {
        selectedFiles = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        connectionDetector = new ConnectionDetector(this);
        appPrefs = Application.getApp().getAppPrefs();
        typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
        listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent != null) {
            if (listPersonEvent.getPersonProcessList() != null) {
                listPersonProgress = listPersonEvent.getPersonProcessList();
            }
            if (listPersonEvent.getPersonDirectList() != null) {
                listPersonDirect = listPersonEvent.getPersonDirectList();
            }
            if (listPersonEvent.getPersonNotifyList() != null) {
                listPersonNotify = listPersonEvent.getPersonNotifyList();
            }
            if (listPersonEvent.getPersonLienThongList() != null) {
                listPersonLienthong = listPersonEvent.getPersonLienThongList();
            }

        }
        EventTypeQLVB eventTypeQLVB = EventBus.getDefault().getStickyEvent(EventTypeQLVB.class);
        if (eventTypeQLVB != null) {
            if (eventTypeQLVB.getType() == 0) {
                ll_auto_send_job.setVisibility(View.VISIBLE);
            } else {
                ll_auto_send_job.setVisibility(View.GONE);
            }
        }

        // hien thi / an tu dong giao viec
        TypeTuDongGiaoViecEvent event = EventBus.getDefault().getStickyEvent(TypeTuDongGiaoViecEvent.class);
        if (event != null && (event.getType().equalsIgnoreCase(Constants.TYPE_SEND_PERSON_PROCESS)
                || event.getType().equalsIgnoreCase(Constants.TYPE_PERSON_DIRECT))) {
            ln_han_xua_ly.setVisibility(View.VISIBLE);
            ln_tu_dong_giao_viec.setVisibility(View.VISIBLE);
        } else {
            ln_han_xua_ly.setVisibility(View.GONE);
            ln_tu_dong_giao_viec.setVisibility(View.GONE);
        }

        // hien thị / ẩn : tự động giao việc, hạn xử lý
        // kho văn bản đi luôn ẩn
        KhoVanBanEvent eventKhoVanBan = EventBus.getDefault().getStickyEvent(KhoVanBanEvent.class);
        if (eventKhoVanBan != null) {
            if (eventKhoVanBan.getKhoVanBan().contains("DI_")) {
                ln_han_xua_ly.setVisibility(View.GONE);
                ln_tu_dong_giao_viec.setVisibility(View.GONE);
            }
        }

        EventCheckSMS eventCheckSMS = EventBus.getDefault().getStickyEvent(EventCheckSMS.class);
        if (eventCheckSMS != null && eventCheckSMS.isIsShow()) {
            llSendSms.setVisibility(View.VISIBLE);
        } else {
            llSendSms.setVisibility(View.GONE);
        }

        setupToolbar();
        txtNote.setTypeface(Application.getApp().getTypeface());
        typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
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
        displayPerson();
    }

    private void displayPerson() {
        List<PersonSendDoc> personSendDocXLCs = new ArrayList<>();
        List<PersonSendDoc> personSendDocDXLs = new ArrayList<>();
        List<PersonSendDoc> personSendDocXems = new ArrayList<>();
        PersonSendDocAdapter personSendDocXLCAdapter = null;
        PersonSendDocAdapter personSendDocDXLAdapter = null;
        PersonSendDocAdapter personSendDocXemAdapter = null;
        int countXLC = 0;
        int countDXL = 0;
        int countXem = 0;
        if (typePersonEvent != null) {
            if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_SEND)
                    || typePersonEvent.getType().equals(Constants.TYPE_PERSON_NOTIFY)
                    || typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)
                    || typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                switch (typePersonEvent.getType()) {
                    case Constants.TYPE_PERSON_SEND:
                        break;
                    case Constants.TYPE_PERSON_NOTIFY:
                        break;
                    case Constants.TYPE_SEND_PERSON_PROCESS:
                        if (listPersonProgress != null && listPersonProgress.size() > 0) {
                            for (Person person : listPersonProgress) {
                                if (person.isXlc()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                                    personSendDocXLCs.add(personSendDoc);
                                }
                                if (person.isDxl()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                                    personSendDocDXLs.add(personSendDoc);
                                }
                                if (person.isXem()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                                    personSendDocXems.add(personSendDoc);
                                }
                            }
                        }
                        if (listPersonLienthong != null && listPersonLienthong.size() > 0) {
                            for (Person person : listPersonLienthong) {
                                if (person.isXlc()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                    personSendDocXLCs.add(personSendDoc);
                                }
                                if (person.isDxl()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                    personSendDocDXLs.add(personSendDoc);
                                }
                                if (person.isXem()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                    personSendDocXems.add(personSendDoc);
                                }
                            }
                        }
                        personSendDocXLCAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXLCs,
                                listPersonProgress, listPersonDirect, listPersonNotify, listPersonLienthong, listUnseclectPersonProgress,
                                listUnseclectPersonDirect, listUnseclectPersonNotify, listUnseclectPersonLienthong);
                        countXLC = personSendDocXLCAdapter.getCount();
                        if (countXLC > 0) {
                            layoutXLCTitle.setVisibility(View.VISIBLE);
                            for (int i = 0; i < countXLC; i++) {
                                View view = personSendDocXLCAdapter.getView(i, null, null);
                                layoutXLC.addView(view);
                            }
                        } else {
                            layoutXLCTitle.setVisibility(View.GONE);
                        }
                        personSendDocDXLAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocDXLs,
                                listPersonProgress, listPersonDirect, listPersonNotify, listPersonLienthong, listUnseclectPersonProgress,
                                listUnseclectPersonDirect, listUnseclectPersonNotify, listUnseclectPersonLienthong);
                        countDXL = personSendDocDXLAdapter.getCount();
                        if (countDXL > 0) {
                            layoutPHTitle.setVisibility(View.VISIBLE);
                            for (int i = 0; i < countDXL; i++) {
                                View view = personSendDocDXLAdapter.getView(i, null, null);
                                layoutPH.addView(view);
                            }
                        } else {
                            layoutPHTitle.setVisibility(View.GONE);
                        }
                        personSendDocXemAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXems,
                                listPersonProgress, listPersonDirect, listPersonNotify, listPersonLienthong, listUnseclectPersonProgress,
                                listUnseclectPersonDirect, listUnseclectPersonNotify, listUnseclectPersonLienthong);
                        countXem = personSendDocXemAdapter.getCount();
                        if (countXem > 0) {
                            layoutXemDBTitle.setVisibility(View.VISIBLE);
                            for (int i = 0; i < countXem; i++) {
                                View view = personSendDocXemAdapter.getView(i, null, null);
                                layoutXemDB.addView(view);
                            }
                        } else {
                            layoutXemDBTitle.setVisibility(View.GONE);
                        }
                        break;
                    case Constants.TYPE_PERSON_DIRECT:
                        if (listPersonDirect != null && listPersonDirect.size() > 0) {
                            for (Person person : listPersonDirect) {
                                if (person.isXlc()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "RIDRECT_LIST");
                                    personSendDocXLCs.add(personSendDoc);
                                }
                                if (person.isDxl()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "RIDRECT_LIST");
                                    personSendDocDXLs.add(personSendDoc);
                                }
                                if (person.isXem()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "RIDRECT_LIST");
                                    personSendDocXems.add(personSendDoc);
                                }
                            }
                        }
                        if (listPersonLienthong != null && listPersonLienthong.size() > 0) {
                            for (Person person : listPersonLienthong) {
                                if (person.isXlc()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                    personSendDocXLCs.add(personSendDoc);
                                }
                                if (person.isDxl()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                    personSendDocDXLs.add(personSendDoc);
                                }
                                if (person.isXem()) {
                                    PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                    personSendDocXems.add(personSendDoc);
                                }
                            }
                        }
                        personSendDocXLCAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXLCs,
                                listPersonProgress, listPersonDirect, listPersonNotify, listPersonLienthong, listUnseclectPersonProgress,
                                listUnseclectPersonDirect, listUnseclectPersonNotify, listUnseclectPersonLienthong);
                        countXLC = personSendDocXLCAdapter.getCount();
                        if (countXLC > 0) {
                            layoutXLCTitle.setVisibility(View.VISIBLE);
                            for (int i = 0; i < countXLC; i++) {
                                View view = personSendDocXLCAdapter.getView(i, null, null);
                                layoutXLC.addView(view);
                            }
                        } else {
                            layoutXLCTitle.setVisibility(View.GONE);
                        }
                        personSendDocDXLAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocDXLs,
                                listPersonProgress, listPersonDirect, listPersonNotify, listPersonLienthong, listUnseclectPersonProgress,
                                listUnseclectPersonDirect, listUnseclectPersonNotify, listUnseclectPersonLienthong);
                        countDXL = personSendDocDXLAdapter.getCount();
                        if (countDXL > 0) {
                            layoutPHTitle.setVisibility(View.VISIBLE);
                            for (int i = 0; i < countDXL; i++) {
                                View view = personSendDocDXLAdapter.getView(i, null, null);
                                layoutPH.addView(view);
                            }
                        } else {
                            layoutPHTitle.setVisibility(View.GONE);
                        }
                        personSendDocXemAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXems,
                                listPersonProgress, listPersonDirect, listPersonNotify, listPersonLienthong, listUnseclectPersonProgress,
                                listUnseclectPersonDirect, listUnseclectPersonNotify, listUnseclectPersonLienthong);
                        countXem = personSendDocXemAdapter.getCount();
                        if (countXem > 0) {
                            layoutXemDBTitle.setVisibility(View.VISIBLE);
                            for (int i = 0; i < countXem; i++) {
                                View view = personSendDocXemAdapter.getView(i, null, null);
                                layoutXemDB.addView(view);
                            }
                        } else {
                            layoutXemDBTitle.setVisibility(View.GONE);
                        }
                        break;
                }
            } else {
                if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                    if (listPersonNotify != null && listPersonNotify.size() > 0) {
                        for (Person person : listPersonNotify) {
                            if (person.isXem()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "NOTIFY_LIST");
                                personSendDocXems.add(personSendDoc);
                            }
                        }
                        personSendDocXemAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXems,
                                listPersonProgress, listPersonDirect, listPersonNotify, listPersonLienthong, listUnseclectPersonProgress,
                                listUnseclectPersonDirect, listUnseclectPersonNotify, listUnseclectPersonLienthong);
                        countXem = personSendDocXemAdapter.getCount();
                        if (countXem > 0) {
                            layoutXemDBTitle.setVisibility(View.VISIBLE);
                            for (int i = 0; i < countXem; i++) {
                                View view = personSendDocXemAdapter.getView(i, null, null);
                                layoutXemDB.addView(view);
                            }
                        }

                    } else {
                        layoutXemDBTitle.setVisibility(View.GONE);
                    }
                    layoutPHTitle.setVisibility(View.GONE);
                    layoutXLCTitle.setVisibility(View.GONE);
                } else {
                    if (listPersonProgress != null && listPersonProgress.size() > 0) {
                        for (Person person : listPersonProgress) {
                            if (person.isXlc()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                                personSendDocXLCs.add(personSendDoc);
                            }
                            if (person.isDxl()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                                personSendDocDXLs.add(personSendDoc);
                            }
                            if (person.isXem()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                                personSendDocXems.add(personSendDoc);
                            }
                        }
                    }
                    personSendDocXLCAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXLCs,
                            listPersonProgress, listPersonDirect, listPersonNotify, listPersonLienthong, listUnseclectPersonProgress,
                            listUnseclectPersonDirect, listUnseclectPersonNotify, listUnseclectPersonLienthong);
                    countXLC = personSendDocXLCAdapter.getCount();
                    if (countXLC > 0) {
                        layoutXLCTitle.setVisibility(View.VISIBLE);
                        for (int i = 0; i < countXLC; i++) {
                            View view = personSendDocXLCAdapter.getView(i, null, null);
                            layoutXLC.addView(view);
                        }
                    } else {
                        layoutXLCTitle.setVisibility(View.GONE);
                    }
                    personSendDocDXLAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocDXLs,
                            listPersonProgress, listPersonDirect, listPersonNotify, listPersonLienthong, listUnseclectPersonProgress,
                            listUnseclectPersonDirect, listUnseclectPersonNotify, listUnseclectPersonLienthong);
                    countDXL = personSendDocDXLAdapter.getCount();
                    if (countDXL > 0) {
                        layoutPHTitle.setVisibility(View.VISIBLE);
                        for (int i = 0; i < countDXL; i++) {
                            View view = personSendDocDXLAdapter.getView(i, null, null);
                            layoutPH.addView(view);
                        }
                    } else {
                        layoutPHTitle.setVisibility(View.GONE);
                    }
                    layoutXemDBTitle.setVisibility(View.GONE);
                }
            }
        }
    }

    public void hideSoftInput() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void send() {
        hideSoftInput();
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
                    changeProcessRequest.setComment(txtNote.getText().toString().trim());
                    changeProcessRequest.setType(Constants.TYPE_SEND_PROCESS_REQUEST);
                    changeProcessRequest.setSms(checkSMS.isChecked() ? 1 : 0);
                    changeProcessRequest.setFinish(checkKetThucVanBan.isChecked() ? 1 : 0);
                    changeProcessRequest.setJob(checkTuDongGiaoViec.isChecked() ? 1 : 0);
                    changeProcessRequest.setHanXuLy(tvHanXuaLy.getText().toString().trim());
                    changeProcessRequest.setFiles(listId);

                    if (xlc.isEmpty() && xlc_lt.isEmpty() && checkTuDongGiaoViec.isChecked()) {
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
                        changeDocumentNotifyRequest.setComment(txtNote.getText().toString().trim());
                        changeDocumentNotifyRequest.setPrimaryProcess("");
                        changeDocumentNotifyRequest.setCoevalProcess("");
                        changeDocumentNotifyRequest.setReferProcess(!xem.equals("") ? xem.substring(0, xem.length() - 1) : "");
                        changeDocumentNotifyRequest.setPrimaryInternal("");
                        changeDocumentNotifyRequest.setCoevalInternal("");
                        changeDocumentNotifyRequest.setReferInternal("");
                        changeDocumentNotifyRequest.setType("1");
                        changeDocumentNotifyRequest.setSms(checkSMS.isChecked() ? 1 : 0);
                        changeDocumentNotifyRequest.setJob(checkTuDongGiaoViec.isChecked() ? 1 : 0);
                        changeDocumentNotifyRequest.setHanXuLy(tvHanXuaLy.getText().toString().trim());
                        changeDocumentNotifyRequest.setFiles(listId);

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
                TypeChangeEvent typeChangeEvent = EventBus.getDefault().getStickyEvent(TypeChangeEvent.class);
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
                        changeDocumentDirectRequest.setComment(txtNote.getText().toString().trim());
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
                        changeDocumentDirectRequest.setSms(checkSMS.isChecked() ? 1 : 0);
                        changeDocumentDirectRequest.setJob(checkTuDongGiaoViec.isChecked() ? 1 : 0);
                        changeDocumentDirectRequest.setHanXuLy(tvHanXuaLy.getText().toString().trim());
                        changeDocumentDirectRequest.setFiles(listId);

                        if (xlc.isEmpty() && xlc_lt.isEmpty() && checkTuDongGiaoViec.isChecked()) {
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
//                    List<Person> personProcessList = listPersonEvent.getPersonProcessList();
                    List<Person> personSendList = listPersonEvent.getPersonSendList();
                    if (listPersonProgress != null && listPersonProgress.size() > 0) {
                        String dongXuLy = "";
                        int index = -1;
                        for (int i = 0; i < listPersonProgress.size(); i++) {
                            if (listPersonProgress.get(i).isXlc()) {
                                index = i;
                            } else {
                                dongXuLy += listPersonProgress.get(i).getId() + ",";
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
                                changeDocumentSendRequest.setChuTri(listPersonProgress.get(index).getId());
                                changeDocumentSendRequest.setPhoiHop(!dongXuLy.equals("") ? dongXuLy.substring(0, dongXuLy.length() - 1) : "");
                                changeDocumentSendRequest.setSFunc(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getNextStep());
                                changeDocumentSendRequest.setSApproved(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                                changeDocumentSendRequest.setSMore(txtNote.getText().toString().trim());
                                changeDocumentSendRequest.setAssigner("");
                                changeDocumentSendRequest.setAct(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                                changeDocumentSendRequest.setDongGui(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                                changeDocumentSendRequest.setFormId("");
                                changeDocumentSendRequest.setSendType(String.valueOf(type));
                                changeDocumentSendRequest.setJob(checkTuDongGiaoViec.isChecked() ? 1 : 0);
                                changeDocumentSendRequest.setHanXuLy(tvHanXuaLy.getText().toString().trim());
                                changeDocumentSendRequest.setFiles(listId);

                                changeDocumentPresenter.changeSend(changeDocumentSendRequest);

                            }
                            if (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE)) {
                                ChangeDocumentReceiveRequest changeDocumentReceiveRequest = new ChangeDocumentReceiveRequest();
                                changeDocumentReceiveRequest.setDocId(typeChangeDocumentRequest.getDocId());
                                changeDocumentReceiveRequest.setChuTri(listPersonProgress.get(index).getId());
                                changeDocumentReceiveRequest.setApprovedValue(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                                changeDocumentReceiveRequest.setDongXuLy(!dongXuLy.equals("") ? dongXuLy.substring(0, dongXuLy.length() - 1) : "");
                                changeDocumentReceiveRequest.setStrAction(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                                changeDocumentReceiveRequest.setProcessDefinitionId(typeChangeDocumentRequest.getProcessDefinitionId());
                                changeDocumentReceiveRequest.setIsBanHanh("0");
                                changeDocumentReceiveRequest.setDongGui(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                                changeDocumentReceiveRequest.setComment(txtNote.getText().toString().trim());
                                changeDocumentReceiveRequest.setSendType(String.valueOf(type));
                                changeDocumentReceiveRequest.setJob(checkTuDongGiaoViec.isChecked() ? 1 : 0);
                                changeDocumentReceiveRequest.setHanXuLy(tvHanXuaLy.getText().toString().trim());
                                changeDocumentReceiveRequest.setFiles(listId);
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

    @Override
    public void onSuccessChangeDoc() {
        EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
        EventBus.getDefault().removeStickyEvent(TypePersonEvent.class);
        EventBus.getDefault().removeStickyEvent(TypeTuDongGiaoViecEvent.class);
        EventBus.getDefault().removeStickyEvent(TaiFileNewSendEvent.class);

        EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
        EventBus.getDefault().postSticky(new ReloadDocNotificationEvent(true));
        FinishEvent finishEvent = EventBus.getDefault().getStickyEvent(FinishEvent.class);
        if(finishEvent!=null){
            if(finishEvent.isFinish()){
                finishEvent.setFinish(true);
                EventBus.getDefault().postSticky(finishEvent);
            }
        }
        Toast.makeText(this, getString(R.string.CHANGE_DOC_SUCCESS), Toast.LENGTH_LONG).show();
        SelectPersonActivityNewConvert.activityThis.finish();
        finish();
    }

    @Override
    public void onSuccessFormList(List<String> listForm) {
        if (listForm != null && listForm.size() > 0) {
            listDataFormSend = listForm;
            showDialogSelectForm();
        }
    }

    @Override
    public void onSuccessGroupPerson(List<PersonGroupChangeDocInfo> personGroupChangeDocInfos) {

    }

    @Override
    public void onSuccessUpLoad(List<Object> object) {
        listId = object;
        send();
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
                AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_DOC_TITLE_ERROR), getString(R.string.CHANGE_DOC_FAIL), true, AlertDialogManager.ERROR);
            }
            if (apiError.getMessage().contains("document_processed")) {
                Toast.makeText(NewSendDocumentActivity.this, getString(R.string.CHANGED_DOC), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            send();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(NewSendDocumentActivity.this, LoginActivity.class));
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

    // Tải file
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case SELECT_FILE_RESULT_SUCCESS:
                if (resultCode == RESULT_OK) {
                    ArrayList<Uri> sessionSelectedFiles = data.getParcelableArrayListExtra(com.aditya.filebrowser.Constants.SELECTED_ITEMS);
                    if (sessionSelectedFiles != null && sessionSelectedFiles.size() > 0) {
                        List<FileChiDao> fileChiDaos = new ArrayList<>();
                        for (Uri uri : sessionSelectedFiles) {
                            String[] uriStr = uri.getPath().split("/");
                            if (!selectedFiles.contains(uri)) {
                                if (validateExtension(uriStr[uriStr.length - 1])) {
                                    File file = new File(uri.getPath());
                                    if (validateSize(file)) {
                                        fileChiDaos.add(new FileChiDao(uriStr[uriStr.length - 1]));
                                        selectedFiles.add(uri);
                                    } else {
                                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.INVALID_FILE_SIZE), true, AlertDialogManager.ERROR);
                                    }
                                } else {
                                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.INVALID_FILE_EXT), true, AlertDialogManager.ERROR);
                                }
                            } else {
                                Toast.makeText(this, getString(R.string.DUPLICATE_FILE), Toast.LENGTH_LONG).show();
                            }
                        }
                        FileChuyenVanBanAdapter fileChuyenVanBanAdapter = new FileChuyenVanBanAdapter(this, R.layout.item_file_chidao_list, fileChiDaos, "SEND");
                        int adapterCount = fileChuyenVanBanAdapter.getCount();
                        for (int i = 0; i < adapterCount; i++) {
                            View item = fileChuyenVanBanAdapter.getView(i, null, null);
                            layoutFile.addView(item);
                        }

                        //Gửi file
//                        sendFileName();
                    }
                }
                break;
        }
    }

    private void sendFileName() {
        fileNames = new ArrayList<>();
        if (selectedFiles != null && selectedFiles.size() > 0) {
            MultipartBody.Part[] parts = new MultipartBody.Part[selectedFiles.size()];
            int i = -1;
            if (selectedOldFiles == null) {
                selectedOldFiles = new ArrayList<>();
            }
            for (Uri uri : selectedFiles) {
                if (!selectedOldFiles.contains(uri)) {
                    i++;
                    parts[i] = prepareFilePart("fileupload", uri);
                    String[] uriStr = uri.getPath().split("/");
                    fileNames.add(uriStr[uriStr.length - 1]);
                    selectedOldFiles.add(uri);
                }
            }
            changeDocumentPresenter.sendUploadFile(parts);
        } else {
            send();
        }
    }

    private boolean validateExtension(String filename) {
        if (filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DOC)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DOCX)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.XLS)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.XLSX)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.PDF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.JPG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.JPEG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.PNG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.GIF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.TIFF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.BMP)) {
            return true;
        }
        return false;
    }

    private boolean validateSize(File file) {
        double size = (double) file.length() / (1024 * 1024);
        if (size < 50) {
            return true;
        }
        return false;
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = new File(fileUri.getPath());
        String mimeType = URLConnection.guessContentTypeFromName(URLEncoder.encode(file.getName()));//getMimeType(fileUri.getPath())
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public void removeFile(String filename) {
        for (int i = 0; i < selectedFiles.size(); i++) {
            Uri uri = selectedFiles.get(i);
            String[] uriStr = uri.getPath().split("/");
            if (filename.equals(uriStr[uriStr.length - 1])) {
                selectedFiles.remove(i);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        RemoveSelectPersonEvent removeSelectPersonEvent = new RemoveSelectPersonEvent();
        if (listUnseclectPersonProgress.size() > 0 || listUnseclectPersonDirect.size() > 0 || listUnseclectPersonNotify.size() > 0 || listUnseclectPersonLienthong.size() > 0) {
            if (listUnseclectPersonProgress.size() > 0) {
                removeSelectPersonEvent.setPersonProcessRemove(listUnseclectPersonProgress);
            }
            if (listUnseclectPersonDirect.size() > 0) {
                removeSelectPersonEvent.setPersonDirectRemove(listUnseclectPersonDirect);
            }
            if (listUnseclectPersonNotify.size() > 0) {
                removeSelectPersonEvent.setPersonNotifyRemove(listUnseclectPersonNotify);
            }
            if (listUnseclectPersonLienthong.size() > 0) {
                removeSelectPersonEvent.setPersonLienThongRemove(listUnseclectPersonLienthong);
            }
            EventBus.getDefault().postSticky(removeSelectPersonEvent);
        }
//        EventBus.getDefault().postSticky(new SelectGroupSendDocEvent(true));
        EventBus.getDefault().removeStickyEvent(TypeTuDongGiaoViecEvent.class);
        finish();
    }


}
