package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.filebrowser.Constants;
import com.aditya.filebrowser.FileChooser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import me.texy.treeview.TreeViewOperatingV2;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.FileChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.FileUploadChidaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.FileUploadWorkAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.PersonChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.PersonReceiveChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.PersonReceiveChiDaoV2Adapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SpinnerCheckboxAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.ChiDaoFlow;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.DonViNhan;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileUploadWorkInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.GroupPerson;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.PersonReceiveChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DanhSachDonViNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonInGroupChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SendChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao.ChiDaoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao.IChiDaoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ChoiseGroupEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KeywordEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoAddEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoDeleteEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoOldEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoResultEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonSendChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ResponseChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ResponsedChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ShowProgressEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonChiDaoCheck;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.StateVO;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeOperatingViewV2Factory;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IChiDaoView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class ForwardChiDaoV2Activity extends BaseActivity implements IChiDaoView, ILoginView,
        PersonReceiveChiDaoV2Adapter.OnItemOperatingClickListener {
    private static final String TAG = "HUYTESTVER2";
    @BindView(R.id.lineStep1)
    View lineStep1;
    @BindView(R.id.imgStep1)
    ImageView imgStep1;
    @BindView(R.id.txtStep1)
    TextView txtStep1;
    @BindView(R.id.step1)
    LinearLayout step1;
    @BindView(R.id.lineStep2)
    View lineStep2;
    @BindView(R.id.imgStep2)
    ImageView imgStep2;
    @BindView(R.id.txtStep2)
    TextView txtStep2;
    @BindView(R.id.step2)
    LinearLayout step2;
    @BindView(R.id.lineStep3)
    View lineStep3;
    @BindView(R.id.imgStep3)
    ImageView imgStep3;
    @BindView(R.id.txtStep3)
    TextView txtStep3;
    @BindView(R.id.step3)
    LinearLayout step3;
    @BindView(R.id.tv_tieude)
    EditText tvTieude;
    @BindView(R.id.tv_noidung)
    EditText tvNoidung;
    @BindView(R.id.tv_filedinhkem_label)
    TextView tvFiledinhkemLabel;
    @BindView(R.id.layoutStep1)
    LinearLayout layoutStep1;
    @BindView(R.id.layoutStep2)
    LinearLayout layoutStep2;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnSendAll)
    Button btnSendAll;
    @BindView(R.id.layoutStep3)
    LinearLayout layoutStep3;
    @BindView(R.id.layoutDisplay)
    LinearLayout layoutDisplay;
    @BindView(R.id.btnSelectFile)
    TextView btnSelectFile;
    private static final int SELECT_FILE_RESULT_SUCCESS = 1;
    @BindView(R.id.edtKeywordName)
    EditText edtKeywordName;
    @BindView(R.id.edtKeywordNameReceive)
    EditText edtKeywordNameReceive;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.txtNoDataReceive)
    TextView txtNoDataReceive;
    @BindView(R.id.layout_contact)
    LinearLayout layoutContact;
    @BindView(R.id.layoutDisplayStep)
    LinearLayout layoutDisplayStep;
    @BindView(R.id.layout_receive)
    ListView layout_receive;
    @BindView(R.id.layoutSave)
    LinearLayout layoutSave;
    @BindView(R.id.sNhom)
    Spinner sNhom;
    @BindView(R.id.sDonViGui)
    Spinner sDonViGui;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.checkSMS)
    CheckBox checkSMS;
    @BindView(R.id.layoutXuLyTitle)
    LinearLayout layoutXuLyTitle;
    @BindView(R.id.btnSelect)
    Button btnSelect;
    @BindView(R.id.btnSavePerson)
    Button btnSavePerson;
    @BindView(R.id.btnBackStep1)
    Button btnBackStep1;
    @BindView(R.id.layoutSavePerson)
    LinearLayout layoutSavePerson;
    @BindView(R.id.btnBackStep2)
    Button btnBackStep2;
    @BindView(R.id.layoutSend)
    LinearLayout layoutSend;
    @BindView(R.id.recycler_view_file_upload)
    RecyclerView recyclerViewFileUpload;
    Boolean checkLoadListCompare = false;


    private int stepCurrent;
    private IChiDaoPresenter chiDaoPresenter = new ChiDaoPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private List<String> fileNames;
    private String event;
    private List<PersonChiDao> danhSachDonViNhanList;
    private List<PersonChiDao> personChiDaos;
    private String thongTinId = "";
    private List<PersonReceiveChiDao> personReceiveChiDaos;
    private List<GroupPerson> groupPersonList;
    private ChiDaoFlow chiDaoFlow;
    private ArrayList<vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao> listfileUpload;
    private ArrayList<String> listDeleteFile;
    private List<String> listIdFile = new ArrayList<>();
    private String groups;
    private List<String> personInGroup;
    private int nextStep;
    private TreeViewOperatingV2 treeViewOperatingV2;
    private TreeNode root;
    List<String> empOld = new ArrayList<>();
    PersonReceiveChiDao personReceive;
    PersonReceiveChiDaoV2Adapter personReceiveAdapter;
    FileUploadChidaoAdapter fileUploadChidaoAdapter;

    private String agentId = "";
    String currentGroup;
    String currentUnit;
    HashMap<String, List<String>> empOldMap = new HashMap<>();
    HashMap<String, List<String>> empAddMap = new HashMap<>();
    HashMap<String, List<String>> empDeleteMap = new HashMap<>();
    HashMap<String, List<String>> compareGroupMap = new HashMap<>();
    private ApplicationSharedPreferences appPrefs = Application.getApp().getAppPrefs();
    private List<String> personInGroupOld = new ArrayList<>();
    private List<String> personGroupDelete = new ArrayList<>();
    private List<String> personInGroupAdd = new ArrayList<>();
    private List<String> listGroupChecked = new ArrayList<>();
    private List<DonViNhan> listDonViNhan;
    private RecyclerView.LayoutManager layoutManager;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forward_chi_dao_v2);
        init();
    }

    private void init() {
        personInGroup = new ArrayList<>();
        connectionDetector = new ConnectionDetector(this);
        ButterKnife.bind(this);
        listfileUpload = new ArrayList<vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao>();
        listDeleteFile = new ArrayList<>();
        recyclerViewFileUpload.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewFileUpload.setNestedScrollingEnabled(false);
        recyclerViewFileUpload.setLayoutManager(layoutManager);
        fileUploadChidaoAdapter = new FileUploadChidaoAdapter(this, listfileUpload, listDeleteFile);
        recyclerViewFileUpload.setAdapter(fileUploadChidaoAdapter);

        tvTitle.setText(getResources().getString(R.string.FORWARD_CHIDAO));
        chiDaoFlow = EventBus.getDefault().getStickyEvent(ResponseChiDaoEvent.class).getChiDaoFlow();
        tvTieude.setText("ChT: " + chiDaoFlow.getTieuDe());
        if (chiDaoFlow != null) {
            if (chiDaoFlow.getNoiDung() != null && !chiDaoFlow.getNoiDung().trim().equals("")) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    tvNoidung.setText(Html.fromHtml(chiDaoFlow.getNoiDung()));
                } else {
                    tvNoidung.setText(Html.fromHtml(chiDaoFlow.getNoiDung(), Html.FROM_HTML_MODE_COMPACT));
                }
            }
            if (chiDaoFlow.getId() != null) {
                getFiles(chiDaoFlow.getId());

            }
        }

        checkSMS.setChecked(false);
        listDeleteFile = new ArrayList<>();
        stepCurrent = 1;
        step1.performClick();
        edtKeywordName.setTypeface(Application.getApp().getTypeface());
        edtKeywordNameReceive.setTypeface(Application.getApp().getTypeface());
        edtKeywordName.addTextChangedListener(
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
                                        hideSoftInput();
                                        getDanhSachDonViNhan(edtKeywordName.getText().toString().trim(), agentId);
                                    }
                                });
                            }
                        }, vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
        edtKeywordNameReceive.addTextChangedListener(
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
                                        hideSoftInput();
                                        getPersonsReceive();
                                    }
                                });
                            }
                        }, vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DELAY_TIME_SEARCH);
                    }
                }
        );


    }

    @Override
    public void removeOnclickListener(PersonReceiveChiDao personReceiveRemove) {
        event = "GET_REMOVE_PERSON_OPERATING";
        this.personReceive = personReceiveRemove;
        for (DonViNhan donViNhan : listDonViNhan) {
            if (empOldMap.get(donViNhan.getId()) != null)
                for (int i = 0; i < empOldMap.get(donViNhan.getId()).size(); i++) {
                    if (empOldMap.get(donViNhan.getId()).get(i).equalsIgnoreCase(personReceiveRemove.getId()))
                        empOldMap.get(donViNhan.getId()).remove(i);
                }
        }
        new ProgressTask(2).execute();
        chiDaoPresenter.savePersonChiDao(new SavePersonChiDaoRequest(thongTinId, new ArrayList<String>(),
                Arrays.asList(personReceiveRemove.getId())));
        List<TreeNode> allTree = treeViewOperatingV2.getAllNodes();
        for (int i = 0; i < allTree.size(); i++) { // xóa đi cái node ở tree hiện tại
            PersonChiDao p = ((PersonChiDao) allTree.get(i).getValue());
            if (p.getId().equalsIgnoreCase(personReceiveRemove.getId())) {
                allTree.get(i).setSelectedXLC(false);
            }
        }
        treeViewOperatingV2.refreshTreeView();
        listxoa.add(personReceiveRemove.getId());
        removeCheck(listxoa);
    }

    private void removeCheck(List<String> list) {
        if (groupPersonList != null && list != null) {
            for (int i = 0; i < groupPersonList.size(); i++) {
                if (compareGroupMap.get(groupPersonList.get(i).getId()) != null && compareGroupMap.get(groupPersonList.get(i).getId()).size() > 0) {
                    if (removeCheckGroup(compareGroupMap.get(groupPersonList.get(i).getId()), list)) {
                        for (int j = 2; j < lst.size(); j++) {
                            if (lst.get(j).getId().equals(groupPersonList.get(i).getId())) {
                                lst.get(j).setSelected(false);
                            }
                        }
                    }
                }
            }
            setCheckSpinner();
        }
    }

    private void setCheckSpinner() {
        int check = 0;
        for (int i = 2; i < lst.size(); i++) {
            if (lst.get(i).isSelected()) {
                check++;
                lst.get(0).setTitle(lst.get(i).getTitle());
            }
        }
        if (check == lst.size() - 2) {
            lst.get(0).setTitle(lst.get(1).getTitle());
        } else if (check == 0) {
            lst.get(0).setTitle(getString(R.string.DEFAULT_GROUP));
        }


        myAdapter.notifyDataSetChanged();
    }

    private boolean removeCheckGroup(List<String> list, List<String> list1) {
        int check = 0;
        if (list1.size() < list.size()) {
            return false;
        } else {
            for (int i = 0; i < list1.size(); i++) {
                if (list.contains(list1.get(i))) {
                    check++;
                }
            }
        }
        return (check == list.size());
    }


    @Override
    public void sendOnclickListener(PersonReceiveChiDao personReceiveSend) {
        event = "GET_SEND_PERSON_OPERATING";
        this.personReceive = personReceiveSend;
        chiDaoPresenter.send(new SendChiDaoRequest(thongTinId, checkSMS.isChecked() ? "1" : "0", personReceive.getId()));
    }

    private void getPersonsReceive() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON_REVEICE";
            chiDaoPresenter.getPersonReceiveChiDao(new PersonReceiveChiDaoRequest("", "", thongTinId, edtKeywordNameReceive.getText().toString().trim()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getPersonInGroup() {
        if (connectionDetector.isConnectingToInternet()) {
            chiDaoPresenter.getPersonIngroup(new PersonInGroupChiDaoRequest(groups));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getDonViNhan() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_DONVI_NHAN";
            chiDaoPresenter.getDonViNhan();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    //list danh sách sau khi chọn spiner đơn vị

    private void getPersons() {
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new PersonChiDaoAddEvent(new ArrayList<String>()));
            event = "GET_PERSON";
            chiDaoPresenter.getPersonChiDao(new PersonChiDaoRequest(edtKeywordName.getText().toString().trim()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getPersonInGroup(String groups) {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON_IN_GROUP";
            chiDaoPresenter.getPersonIngroup(new PersonInGroupChiDaoRequest(groups));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }


    private void getGroupUser() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_GROUP";
            chiDaoPresenter.getPersonGroupChiDao();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getFiles(String id) {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_FILE";
            chiDaoPresenter.getFileChiDao(id);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void send() {
        if (connectionDetector.isConnectingToInternet()) {
            boolean check_send = false;
            if (personReceiveChiDaos != null && personReceiveChiDaos.size() > 0) {
                for (int i = 0; i < personReceiveChiDaos.size(); i++) {
                    if (personReceiveChiDaos.get(i).getNgayNhan() == null) {
                        check_send = true;
                        break;
                    }
                }
            }
            if (check_send) {
                event = "SEND_CHIDAO";
                chiDaoPresenter.send(new SendChiDaoRequest(thongTinId, checkSMS.isChecked() ? "1" : "0", ""));
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_SENT_ALL), true, AlertDialogManager.ERROR);
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void refreshAfterSendAll() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON_REVEICE";
            chiDaoPresenter.getPersonReceiveChiDao(new PersonReceiveChiDaoRequest("", "", thongTinId, ""));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void saveInfo() {
        if (connectionDetector.isConnectingToInternet()) {
            switch (stepCurrent) {
                case 1:
                    event = "SAVE_CHIDAO";
                    fileNames = new ArrayList<>();
                    for (FileChiDao file : listfileUpload) {
                        fileNames.add(file.getName());
                    }
                    if (tvTieude.getText() != null && !tvTieude.getText().toString().trim().equals("")) {
                        if (listfileUpload != null && listfileUpload.size() > 0) {
                            for (FileChiDao file : listfileUpload) {
                                if (!file.isFileRoot()) {
                                    if (!file.isSended()) {
                                        upLoadFile();
                                        return;
                                    }
                                }
                            }
                        }
                        EventBus.getDefault().postSticky(new ShowProgressEvent(true));
                        ChiDaoRequest chiDaoRequest = new ChiDaoRequest(thongTinId, tvTieude.getText().toString().trim(),
                                tvNoidung.getText().toString().trim(), fileNames, listDeleteFile, chiDaoFlow.getId(), 1);
                        chiDaoPresenter.saveChiDao(chiDaoRequest);

                    } else {
                        tvTieude.setError(getString(R.string.TIEUDE_REQUIERD));
                        //AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.TIEUDE_REQUIERD), true, AlertDialogManager.ERROR);
                    }
                    break;
                case 2:
                    event = "SAVE_PERSON";
                    saveData();
                    break;
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void upLoadFile() {
        MultipartBody.Part[] parts = new MultipartBody.Part[listfileUpload.size()];
        int postion = 0;
        for (int i = 0; i < listfileUpload.size(); i++) {
            if (!listfileUpload.get(i).isFileRoot()) {
                if (!listfileUpload.get(i).isSended()) {
                    if (listfileUpload.get(i).getUri() != null) {
                        parts[postion] = prepareFilePart("fileupload", listfileUpload.get(i).getUri());
                        postion++;
                    }
                }


            }

        }
        chiDaoPresenter.uploadFiles(parts);
    }

    public void saveData() {
        if (treeViewOperatingV2 != null) {
            List<TreeNode> selectedNodes = treeViewOperatingV2.getSelectedXLCNodes(); // láy danh sách người hiện đang chọn
            List<String> empDelete = new ArrayList<>(); // danh sách người xóa
            List<String> empAdd = new ArrayList<>(); // danh sách người thêm gửi lên server
            List<String> empAddPerson = new ArrayList<>();
            if (selectedNodes.size() > 0 || (personInGroup != null && personInGroup.size() > 0)
                    || personInGroupOld.size() > 0 || empOldMap.get(currentGroup).size() > 0) {
                empAddPerson.clear();
                for (int i = 0; i < selectedNodes.size(); i++) {
                    PersonChiDao personChiDao = (PersonChiDao) selectedNodes.get(i).getValue();
//                    if ((personChiDao.getChucVu() != null && !personChiDao.getChucVu().trim().equals("")))
//                        empAdd.add(personChiDao.getId());
//                    if ((personChiDao.getChucVu() != null && !personChiDao.getChucVu().trim().equals(""))
//                            && (empOldMap.get(currentGroup) != null && !empOldMap.get(currentGroup).contains(personChiDao.getId()))) {
//                        empAddPerson.add(personChiDao.getId()); // tìm các emp mới muốn add
//                    }
                    empAdd.add(personChiDao.getId());
                    empAddPerson.add(personChiDao.getId()); // tìm các emp mới muốn add

                }
                if (empOldMap.get(currentGroup) != null && empOldMap.get(currentGroup).size() > 0) {
                    for (int i = 0; i < empOldMap.get(currentGroup).size(); i++) {
                        if (!empAdd.contains(empOldMap.get(currentGroup).get(i))) {
                            empDelete.add(empOldMap.get(currentGroup).get(i));
                        }
                    }
                }
                if (empAdd != null) {
                    empOldMap.get(currentGroup).clear();
                    empOldMap.get(currentGroup).addAll(empAdd);
                }
                if (empAddPerson != null && empAddPerson.size() > 0) {
                    empAddMap.get(currentGroup).clear();
                    empAddMap.get(currentGroup).addAll(empAddPerson);
                }
                if (empDelete != null && empDelete.size() > 0) {
                    empDeleteMap.get(currentGroup).clear();
                    empDeleteMap.get(currentGroup).addAll(empDelete);
                }
                personInGroupAdd.clear();
                for (String pInGroup : personInGroup) {
                    if (!personInGroupOld.contains(pInGroup)) {
                        personInGroupAdd.add(pInGroup);
                    }
                }
                personGroupDelete.clear();
                for (String pInGroupOld : personInGroupOld) {
                    if (personInGroup != null && !personInGroup.contains(pInGroupOld)) {
                        personGroupDelete.add(pInGroupOld);
                    }
                }
                personInGroupOld.clear();
                personInGroupOld.addAll(personInGroup);
            } else {

            }
        }

    }

    public void sendSever() {
        List<String> listAdd = new ArrayList<>();
        for (int i = 0; i < listDonViNhan.size(); i++) {
            if (empAddMap.get(listDonViNhan.get(i).getId()) != null) {
                listAdd.addAll(empAddMap.get(listDonViNhan.get(i).getId()));
                empAddMap.get(listDonViNhan.get(i).getId()).clear();
            }
        }
        List<String> listDelete = new ArrayList<>();
        for (int i = 0; i < listDonViNhan.size(); i++) {
            if (empDeleteMap.get(listDonViNhan.get(i).getId()) != null) {
                listDelete.addAll(empDeleteMap.get(listDonViNhan.get(i).getId()));
                empDeleteMap.get(listDonViNhan.get(i).getId()).clear();
            }
        }
        if (personInGroupAdd != null && personInGroupAdd.size() > 0) {
            listAdd.addAll(personInGroupAdd);
        }
        if (personGroupDelete != null && personGroupDelete.size() > 0) {
            listDelete.addAll(personGroupDelete);
        }
        if ((listAdd != null && listAdd.size() > 0) || (listDelete != null && listDelete.size() > 0)) {
            listAdd = removeRepeated(listAdd);
            listDelete = removeRepeated(listDelete);
        }
        if (listAdd.size() > 0 || listDelete.size() > 0) {
            chiDaoPresenter
                    .savePersonChiDao
                            (new SavePersonChiDaoRequest(thongTinId, listAdd, listDelete));
            listAdd.clear();
            listDelete.clear();
        } else
            nextStep3();
    }

    @OnClick({R.id.step1, R.id.step2, R.id.step3})
    public void onStep(View view) {
        switch (view.getId()) {
            case R.id.step1:
                nextStep = 1;
                stepCurrent = 1;
                layoutStep1.setVisibility(View.VISIBLE);
                layoutStep2.setVisibility(View.GONE);
                layoutStep3.setVisibility(View.GONE);
                lineStep1.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
                lineStep2.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep3.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1));
                imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2_disable));
                imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3_disable));
                txtStep1.setTextColor(getResources().getColor(R.color.colorTextBlue));
                txtStep2.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep3.setTextColor(getResources().getColor(R.color.md_grey_600));
                //btnCancel.setVisibility(View.VISIBLE);
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp);
                layoutSave.setVisibility(View.VISIBLE);
                layoutSavePerson.setVisibility(View.GONE);
                layoutSend.setVisibility(View.GONE);
                break;
            case R.id.step2:
                stepCurrent = 2;
                layoutStep1.setVisibility(View.GONE);
                layoutStep2.setVisibility(View.VISIBLE);
                layoutStep3.setVisibility(View.GONE);
                lineStep1.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep2.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
                lineStep3.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1_disable));
                imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2));
                imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3_disable));
                txtStep1.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep2.setTextColor(getResources().getColor(R.color.colorTextBlue));
                txtStep3.setTextColor(getResources().getColor(R.color.md_grey_600));
                //btnCancel.setVisibility(View.GONE);
                layoutSave.setVisibility(View.GONE);
                layoutSavePerson.setVisibility(View.VISIBLE);
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
                layoutSend.setVisibility(View.GONE);
                getGroupUser();
                break;
            case R.id.step3:
                stepCurrent = 3;
                layoutStep1.setVisibility(View.GONE);
                layoutStep2.setVisibility(View.GONE);
                layoutStep3.setVisibility(View.VISIBLE);
                lineStep1.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep2.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep3.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
                imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1_disable));
                imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2_disable));
                imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3));
                txtStep1.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep2.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep3.setTextColor(getResources().getColor(R.color.colorTextBlue));
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
                layoutSave.setVisibility(View.GONE);
                layoutSavePerson.setVisibility(View.GONE);
                layoutSend.setVisibility(View.VISIBLE);
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
                getPersonsReceive();
                break;
        }   //Remove empOld, update list treeview
        if (empOld != null && empOld.size() > 0
                && empOld.contains(personReceive.getId())) {
            empOld.remove(personReceive.getId());
        }
        new ProgressTask(2).execute();
    }

    @OnClick({R.id.btnSave, R.id.btnCancel, R.id.btnSelectFile, R.id.btnSendAll, R.id.btnBack, R.id.btnSelect, R.id.btnBackStep1, R.id.btnBackStep2, R.id.btnSavePerson})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSavePerson:
                saveInfo();
                sendSever();
                break;
            case R.id.btnBackStep1:
                backStep1();
                break;
            case R.id.btnBackStep2:
                backStep2();
                break;
            case R.id.btnSave:
                saveInfo();
                break;
            case R.id.btnSelect:
                nextStep = 2;
                saveInfo();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnSelectFile:
                Intent i2 = new Intent(this, FileChooser.class);
                i2.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
                startActivityForResult(i2, SELECT_FILE_RESULT_SUCCESS);
                break;
            case R.id.btnSendAll:
                send();
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }

    private void backStep1() {
        nextStep = 1;
        stepCurrent = 1;
        layoutStep1.setVisibility(View.VISIBLE);
        layoutStep2.setVisibility(View.GONE);
        layoutStep3.setVisibility(View.GONE);
        lineStep1.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
        lineStep2.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        lineStep3.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1));
        imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2_disable));
        imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3_disable));
        txtStep1.setTextColor(getResources().getColor(R.color.colorTextBlue));
        txtStep2.setTextColor(getResources().getColor(R.color.md_grey_600));
        txtStep3.setTextColor(getResources().getColor(R.color.md_grey_600));
        //btnCancel.setVisibility(View.VISIBLE);
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp);
        layoutSave.setVisibility(View.VISIBLE);
        layoutSavePerson.setVisibility(View.GONE);
        layoutSend.setVisibility(View.GONE);
    }

    private void backStep2() {
        stepCurrent = 2;
        layoutStep1.setVisibility(View.GONE);
        layoutStep2.setVisibility(View.VISIBLE);
        layoutStep3.setVisibility(View.GONE);
        lineStep1.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        lineStep2.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
        lineStep3.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1_disable));
        imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2));
        imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3_disable));
        txtStep1.setTextColor(getResources().getColor(R.color.md_grey_600));
        txtStep2.setTextColor(getResources().getColor(R.color.colorTextBlue));
        txtStep3.setTextColor(getResources().getColor(R.color.md_grey_600));
        //btnCancel.setVisibility(View.GONE);
        layoutSave.setVisibility(View.GONE);
        layoutSavePerson.setVisibility(View.VISIBLE);
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
        layoutSend.setVisibility(View.GONE);
        //getGroupUser();
        checkDelete();
    }

    private void nextStep3() {
        stepCurrent = 3;
        layoutStep1.setVisibility(View.GONE);
        layoutStep2.setVisibility(View.GONE);
        layoutStep3.setVisibility(View.VISIBLE);
        lineStep1.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        lineStep2.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        lineStep3.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
        imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1_disable));
        imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2_disable));
        imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3));
        txtStep1.setTextColor(getResources().getColor(R.color.md_grey_600));
        txtStep2.setTextColor(getResources().getColor(R.color.md_grey_600));
        txtStep3.setTextColor(getResources().getColor(R.color.colorTextBlue));
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
        layoutSave.setVisibility(View.GONE);
        layoutSavePerson.setVisibility(View.GONE);
        layoutSend.setVisibility(View.VISIBLE);
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
        getPersonsReceive();
    }

    private void checkDelete() {
        PersonChiDaoDeleteEvent personChiDaoDeleteEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoDeleteEvent.class);
        PersonChiDaoOldEvent personChiDaoOldEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoOldEvent.class);
        if (personChiDaoDeleteEvent != null) {
            List<String> deletes = personChiDaoDeleteEvent.getEmails();
            List<String> olds = personChiDaoOldEvent.getMails();
            if (deletes != null && deletes.size() > 0) {
                for (String delete : deletes) {
                    if (olds.contains(delete)) {
                        olds.remove(delete);
                        PersonChiDaoCheckEvent personChiDaoCheckEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoCheckEvent.class);
                        List<PersonChiDaoCheck> personChiDaoChecks = personChiDaoCheckEvent.getPersonChiDaoCheckList();
                        for (int i = 0; i < personChiDaoChecks.size(); i++) {
                            if (personChiDaoChecks.get(i).getId().equals(delete)) {
                                View view1 = personChiDaoChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkChon);
                                check.setChecked(false);
                                personChiDaoChecks.get(i).setView(view1);
                                personChiDaoCheckEvent.setPersonChiDaoCheckList(personChiDaoChecks);
                                EventBus.getDefault().postSticky(personChiDaoCheckEvent);
                                break;
                            }
                        }
                    }
                }
            }
            personChiDaoOldEvent.setMails(olds);
            EventBus.getDefault().postSticky(personChiDaoOldEvent);
            EventBus.getDefault().removeStickyEvent(PersonChiDaoDeleteEvent.class);
        }
    }

    @Override
    public void onSuccess(Object object) {
        if (event != null && event.equals("GET_PERSON")) {
            EventBus.getDefault().postSticky(new KeywordEvent(edtKeywordName.getText().toString().trim()));
            personChiDaos = ConvertUtils.fromJSONList(object, PersonChiDao.class);
            displayPerson();
        }
        if (event != null && event.equals("GET_GROUP")) {
            groupPersonList = ConvertUtils.fromJSONList(object, GroupPerson.class);
            if (checkLoadListCompare) {
                displayPersonGroup();
                getDonViNhan();
            } else {
                if (groupPersonList != null && groupPersonList.size() > 0) {
                    for (int i = 0; i < groupPersonList.size(); i++) {
                        String id = groupPersonList.get(i).getId();
                        currentUnit = id;
                        if (compareGroupMap.get(id) == null) {
                            compareGroupMap.put(id, new ArrayList<String>());
                        }
                    }
                    if (groupPersonList.get(0).getId() != null)
                        getPersonInGroup(groupPersonList.get(0).getId());
                } else checkLoadListCompare = true;
            }
        }

        if (event.equals("GET_FILE")) {
            ArrayList<FileChiDao> list = ConvertUtils.fromJSONArraylist(object, vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao.class);
            if (list != null && list.size() > 0) {
                listfileUpload = list;
                fileUploadChidaoAdapter = new FileUploadChidaoAdapter(this, listfileUpload, listDeleteFile);
                recyclerViewFileUpload.setAdapter(fileUploadChidaoAdapter);
            }
            getGroupUser();
        }
        if (event != null && event.equals("SAVE_CHIDAO")) {
            String data = (String) object;
            if (data != null && !data.equals("1")) {
                thongTinId = data;
            }
            Toast.makeText(this, getString(R.string.SAVE_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
            hideSoftInput();
            if (nextStep == 2) {
                step2.performClick();
            } else {
                finish();
            }
        }
        if (event != null && event.equals("GET_PERSON_REVEICE")) {
            personReceiveChiDaos = ConvertUtils.fromJSONList(object, PersonReceiveChiDao.class);
            displayPersonReceive();
        }
        if (event != null && event.equals("SAVE_PERSON")) {
            //AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_PERSON_CHIDAO_SUCCESS), true, AlertDialogManager.SUCCESS);
            Toast.makeText(this, getString(R.string.SAVE_PERSON_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
            step3.performClick();
        }
        if (event != null && event.equals("SEND_CHIDAO")) {
            Toast.makeText(this, getString(R.string.SEND_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
            //AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SEND_CHIDAO_SUCCESS), true, AlertDialogManager.SUCCESS);
            //refreshAfterSendAll();
            onBackPressed();
        }
        if (event != null && event.equals("GET_REMOVE_PERSON_OPERATING")) {
            Toast.makeText(this, getString(R.string.REMOVE_PERSON_SUCCESS), Toast.LENGTH_LONG).show();

            //Update adapter Receive
            if (personReceive != null) {
                personReceiveChiDaos.remove(personReceive);
                personReceiveAdapter.updateDataSetChanged(personReceiveChiDaos);
            }
            //Remove empOld, update list treeview
            if (empOld != null && empOld.size() > 0
                    && empOld.contains(personReceive.getId())) {
                empOld.remove(personReceive.getId());
            }
            new ProgressTask(2).execute();


        }
        if (event != null && event.equals("GET_SEND_PERSON_OPERATING")) {
            Toast.makeText(this, getString(R.string.SEND_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
            //Update adapter Receive
            if (personReceive != null) {
                personReceiveChiDaos.get(personReceiveChiDaos.indexOf(personReceive)).setNgayNhan("Đã gửi");
                personReceiveAdapter.updateDataSetChanged(personReceiveChiDaos);
            }
        }
    }

    @Override
    public void onSuccessDonViNhan(Object object) {
        if (event != null && event.equals("GET_DONVI_NHAN")) {
            if (object instanceof List) {
                List<Object> lstObj = (List) object;
                if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof DonViNhan) {
                    listDonViNhan = (List<DonViNhan>) object;
                    initSpinerDonViNhan(listDonViNhan);
                }
            }
        }
    }

    ArrayList<String> listxoa = new ArrayList<>();

    @Override
    public void onSuccessDanhSachDonViNhan(Object object) {
        if (event != null && event.equals("GET_DANHSACH_DONVI_NHAN")) {

            if (object instanceof List) {
                for (int i = 0; i < listxoa.size(); i++) {
                    empOldMap.get(currentGroup).remove(listxoa.get(i));
                }
                List<Object> lstObj = (List) object;
                if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof PersonChiDao) {
                    danhSachDonViNhanList = (List<PersonChiDao>) object;
                    txtNoData.setVisibility(View.VISIBLE);
                    layoutContact.removeAllViews();
                    new ProgressTask(9).execute();
                    hideProgress();
                }


            }
        }
    }

    @Override
    public void onSuccess() {
        for (FileChiDao file : listfileUpload) {
            file.setSended(true);
        }
        ChiDaoRequest chiDaoRequest = new ChiDaoRequest(thongTinId, tvTieude.getText().toString().trim(),
                tvNoidung.getText().toString().trim(), fileNames, listDeleteFile, chiDaoFlow.getId(), 1);
        chiDaoPresenter.saveChiDao(chiDaoRequest);
    }

    int posstionGroupPersonList = 0;

    @Override
    public void onSuccess(List<Object> object) {
        if (checkLoadListCompare) {
            if (personInGroup == null) {
                personInGroup = new ArrayList<>();
            }
            personInGroup = ConvertUtils.fromJSONList(object, String.class);
            new ProgressTask(1).execute();
            hideProgress();
        } else {
            if (compareGroupMap.get(groupPersonList.get(posstionGroupPersonList).getId()) != null) {
                compareGroupMap.get(groupPersonList.get(posstionGroupPersonList).getId()).clear();
                compareGroupMap.get(groupPersonList.get(posstionGroupPersonList).getId()).addAll(ConvertUtils.fromJSONList(object, String.class));
            }
            if (posstionGroupPersonList < groupPersonList.size() - 1) {
                posstionGroupPersonList++;
                if (groupPersonList.get(posstionGroupPersonList).getId() != null)
                    getPersonInGroup(groupPersonList.get(posstionGroupPersonList).getId());
            } else {
                checkLoadListCompare = true;
            }
        }
    }

    @Override
    public void onError(APIError apiError) {
        if (apiError.getCode() == vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } else {
            if (event != null && event.equals("SAVE_CHIDAO")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("SAVE_PERSON")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON_REVEICE")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_GROUP")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_GROUP_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("SEND_CHIDAO")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SEND_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON_IN_GROUP")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_IN_GROUP_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            if (event != null && event.equals("SAVE_CHIDAO")) {
                saveInfo();
            }
            if (event != null && event.equals("GET_PERSON")) {
                getPersons();
            }
            if (event != null && event.equals("SAVE_PERSON")) {
                saveInfo();
            }
            if (event != null && event.equals("GET_PERSON_REVEICE")) {
                getPersonsReceive();
            }
            if (event != null && event.equals("GET_GROUP")) {
                getGroupUser();
            }
            if (event != null && event.equals("SEND_CHIDAO")) {
                send();
            }
            if (event != null && event.equals("GET_PERSON_IN_GROUP")) {
                getPersonInGroup();
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    public void hideSoftInput() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case SELECT_FILE_RESULT_SUCCESS:
                if (resultCode == RESULT_OK) {
                    ArrayList<Uri> sessionSelectedFiles = data.getParcelableArrayListExtra(Constants.SELECTED_ITEMS);
                    if (sessionSelectedFiles != null && sessionSelectedFiles.size() > 0) {
                        for (Uri uri : sessionSelectedFiles) {
                            if (!checkDuplicate(uri)) {
                                String[] uriStr = uri.getPath().split("/");
                                if (validateExtension(uriStr[uriStr.length - 1])) {
                                    if (validateSize(new File(uri.getPath()))) {
                                        vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao fileChiDao = new vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao();
                                        fileChiDao.setName(uriStr[uriStr.length - 1]);
                                        fileChiDao.setUri(uri);
                                        fileChiDao.setFileRoot(false);
                                        listfileUpload.add(fileChiDao);
                                        fileUploadChidaoAdapter.notifyItemInserted(listfileUpload.size());
                                    } else {
                                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.INVALID_FILE_SIZE), true, AlertDialogManager.ERROR);
                                    }
                                } else {
                                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.INVALID_FILE_EXT), true, AlertDialogManager.ERROR);
                                }
                            }
                        }
//
                    }
                }
                break;
        }
    }

    private boolean checkDuplicate(Uri uri) {
        String[] uriStr = uri.getPath().split("/");
        if (listfileUpload != null) {
            for (vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao file : listfileUpload) {
                if (!file.isFileRoot()) {
                    if (file.getUri().equals(uri) || file.getName().equalsIgnoreCase(uriStr[uriStr.length - 1])) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    private List<StateVO> lst;

    private void displayPersonGroup() {
        lst = new ArrayList<>();
        lst.add(new StateVO(getString(R.string.DEFAULT_GROUP), false, null));
        if (groupPersonList != null && groupPersonList.size() > 0) {
            lst.add(new StateVO(getString(R.string.ALL_GROUP), false, null));
            for (int i = 0; i < groupPersonList.size(); i++) {
                lst.add(new StateVO(groupPersonList.get(i).getName(), false, groupPersonList.get(i).getId()));
            }
        }
        setupSpinnerNhom();
    }

    SpinnerCheckboxAdapter myAdapter;

    private void setupSpinnerNhom() {
        myAdapter = new SpinnerCheckboxAdapter(this, 0, lst, "FW");
        sNhom.setAdapter(myAdapter);
    }

    private void displayPersonReceive() {
        if (personReceiveChiDaos != null && personReceiveChiDaos.size() > 0) {
            txtNoDataReceive.setVisibility(View.GONE);
            if (personReceiveAdapter == null) {
                personReceiveAdapter = new PersonReceiveChiDaoV2Adapter(this, R.layout.item_person_chidao_send,
                        personReceiveChiDaos, thongTinId);
                layout_receive.setAdapter(personReceiveAdapter);
            } else {
                personReceiveAdapter.updateDataSetChanged(personReceiveChiDaos);
            }

        } else {
            txtNoDataReceive.setVisibility(View.VISIBLE);
        }
    }

    private void displayPerson() {
        hideProgress();
        layoutContact.removeAllViews();


        new ProgressTask(9).execute();
    }

    private boolean init = true;

    public void showContact() {
        layoutContact.setVisibility(View.VISIBLE);
        txtNoData.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());//getMimeType(fileUri.getPath())
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
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
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    @Override
    public void onBackPressed() {
        hideSoftInput();
        EventBus.getDefault().removeStickyEvent(ChoiseGroupEvent.class);
        EventBus.getDefault().removeStickyEvent(PersonChiDaoAddEvent.class);
        EventBus.getDefault().removeStickyEvent(PersonChiDaoResultEvent.class);
        EventBus.getDefault().removeStickyEvent(PersonChiDaoOldEvent.class);
        EventBus.getDefault().postSticky(new ResponsedChiDaoEvent(true));
        finish();
    }

    @OnCheckedChanged(R.id.checkSMS)
    public void checkSMS(CompoundButton compoundButton, boolean b) {
        EventBus.getDefault().postSticky(new Boolean(b));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ChoiseGroupEvent choiseGroupEvent) {
        String type = choiseGroupEvent.getType();
        if (type.equals("FW")) {
            groups = choiseGroupEvent.getGroups();
            listGroupChecked.clear();
            if (groups == null || groups.trim().equals("")) {
                new ProgressTask(3).execute();
            } else {
                String[] l = groups.split(",");
                if (l != null && l.length > 0) {
                    for (String s : l) {
                        listGroupChecked.add(s);
                    }
                }
                new ProgressTask(3).execute();
                getPersonInGroup();
            }
        }
    }

    private class ProgressTask extends AsyncTask<Void, Long, Boolean> {

        private int typeUpdateData;
        List<Integer> sizes;
        List<Integer> countUsers;
        List<PersonChiDaoCheck> personChiDaoChecks;
        List<PersonChiDao> lstParent;

        public ProgressTask(int typeUpdateData) {
            this.typeUpdateData = typeUpdateData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return updateDataTreeView(typeUpdateData);

        }

        @Override
        protected void onPostExecute(Boolean result) {
            hideProgressDialog();
            if (result) {
                if (typeUpdateData == 9) {
                    txtNoData.setVisibility(View.GONE);
                    treeViewOperatingV2 = new TreeViewOperatingV2(root, ForwardChiDaoV2Activity.this, new MyNodeOperatingViewV2Factory());
                    View view = treeViewOperatingV2.getView();
                    view.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    layoutContact.addView(view);
                    treeViewOperatingV2.expandLevel(0);
                    new ProgressTask(3).execute();
                } else {
                    treeViewOperatingV2.refreshTreeView();
                }

            } else if (typeUpdateData == 9) {
                txtNoData.setText(getString(R.string.str_khongthay_danhsach));
                txtNoData.setVisibility(View.VISIBLE);
            }

        }
    }

    private List<String> removeRepeated(List<String> al) {
// add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(al);
        al.clear();
        al.addAll(hs);
        return al;
    }

    private List<PersonChiDao> buildUnitTree(List<PersonChiDao> personSendTreeNodeNotifyInfoList, final String id) {

        final List<PersonChiDao> results = new ArrayList<>();
        if (personSendTreeNodeNotifyInfoList == null) {
            return null;
        }
        for (PersonChiDao unit : personSendTreeNodeNotifyInfoList) {
            if (!unit.isTrace() && (unit.getParentId() == id || (unit.getParentId() != null &&
                    unit.getParentId().equalsIgnoreCase(id)))) {
                unit.setTrace(true);
                PersonChiDao dicItem = new PersonChiDao();
                dicItem.setId(unit.getId());
                dicItem.setFullName(unit.getFullName());
                dicItem.setParentId(unit.getParentId());
                dicItem.setChucVu(unit.getChucVu());
                dicItem.setEmail(unit.getEmail());
                dicItem.setChildrenList(buildUnitTree(personSendTreeNodeNotifyInfoList, unit.getId()));
                results.add(dicItem);
            }
        }

        return results;
    }

    private void buildTreeData(List<PersonChiDao> listData, int level, TreeNode root) {
        if (listData == null || root == null) {
            return;
        }
        for (PersonChiDao itemData : listData) {
            TreeNode treeNode = new TreeNode(itemData);
            treeNode.setLevel(level);
            if (itemData.getChildrenList() != null && itemData.getChildrenList().size() > 0) {
                int newlevel = level + 1;
                buildTreeData(itemData.getChildrenList(), newlevel, treeNode);
            }
            root.addChild(treeNode);
        }
    }

    private boolean updateDataTreeView(int typeUpdate) {

        if (typeUpdate == 9) {
            List<PersonChiDao> listData = new ArrayList<>();
            root = TreeNode.root();
            if (danhSachDonViNhanList == null) {
                return false;
            }
            listData = buildUnitTree(danhSachDonViNhanList, null);
            if (listData != null && listData.size() > 0) {
                buildTreeData(listData, 0, root);
            }
            return true;
        }

        if (treeViewOperatingV2 == null) {
            return false;
        }
        List<TreeNode> allTree = treeViewOperatingV2.getAllNodes();
        for (TreeNode treeNode : allTree) {

            for (String s : empOldMap.get(currentGroup)) {

                if (((PersonChiDao) treeNode.getValue()).getId().contains(s)) { // add nè
                    treeNode.setSelectedXLC(true);
                }
            }

        }
        if (allTree == null) {
            return false;
        }
        if (typeUpdate == 1 && personInGroup != null && personInGroup.size() > 0) {
            for (int i = 0; i < allTree.size(); i++) {
                for (int j = 0; j < personInGroup.size(); j++) {
                    String intIdPerson = personInGroup.get(j);
                    if (((PersonChiDao) allTree.get(i).getValue()).getId().equalsIgnoreCase(intIdPerson)) {
                        if (!isNumeric(((PersonChiDao) allTree.get(i).getValue()).getId())) {
                            expanded(allTree.get(i), true);
                            allTree.get(i).setSelectedXLC(true);
                        }
                    }
                }
            }

            return true;
        } else if (typeUpdate == 2 && personReceive != null) {
            for (int i = 0; i < allTree.size(); i++) {
                String intIdPerson = personReceive.getId();
                if (intIdPerson.equalsIgnoreCase
                        (((PersonChiDao) allTree.get(i).getValue()).getId())) {
                    allTree.get(i).setSelectedXLC(false);
                    break;
                }
            }
            return true;
        } else if (typeUpdate == 3 && personInGroup != null && personInGroup.size() > 0) {
            for (int i = 0; i < allTree.size(); i++) {
                for (int j = 0; j < personInGroup.size(); j++) {
                    String intIdPerson = ((PersonChiDao) allTree.get(i).getValue()).getId();
                    if (intIdPerson != null)
                        if (intIdPerson.equalsIgnoreCase(personInGroup.get(j))) {
                            expanded(allTree.get(i), false);
                            allTree.get(i).setSelectedXLC(false);
                        }
                }
            }
            personInGroup.clear();
            return true;
        }

        return false;
    }

    void expanded(TreeNode treeNode, boolean isExpanded) { //expanded đệ quy :# luôn
        if (treeNode.getParent() == null)
            return;
        else {
            treeNode.getParent().setExpanded(isExpanded);
            expanded(treeNode.getParent(), isExpanded);
        }

    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void initSpinerDonViNhan(final List<DonViNhan> listDonViNhan) {
        final ArrayAdapter<DonViNhan> adapter = new ArrayAdapter<DonViNhan>
                (this, android.R.layout.simple_spinner_item, listDonViNhan) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                ((TextView) v).setText(listDonViNhan.get(position).getName());
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                ((TextView) v).setText(listDonViNhan.get(position).getName());
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sDonViGui.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        sDonViGui.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                saveData();

                DonViNhan dataItem = (DonViNhan) sDonViGui.getSelectedItem();
                agentId = dataItem.getId();
                showProgress();
                getDanhSachDonViNhan(edtKeywordName.getText().toString().trim(), agentId);
                if (empOldMap.get(agentId) == null && empOldMap.get(agentId) == null && empOldMap.get(agentId) == null) {
                    empOldMap.put(agentId, new ArrayList<String>());
                    empAddMap.put(agentId, new ArrayList<String>());
                    empDeleteMap.put(agentId, new ArrayList<String>());
                }
                currentGroup = agentId;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        int index = selectSpinnerValue(listDonViNhan, appPrefs.getAccountLogin().getAgent());
        sDonViGui.setSelection(index, true);
    }

    private int selectSpinnerValue(List<DonViNhan> ListSpinner, String myString) // get index user in spinner
    {
        int index = 0;
        for (int i = 0; i < ListSpinner.size(); i++) {
            if (ListSpinner.get(i).getId().equals(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void getDanhSachDonViNhan(String param, String agentId) {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_DANHSACH_DONVI_NHAN";
            chiDaoPresenter.getDanhSachDonViNhan(new DanhSachDonViNhanRequest(param, agentId));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

}