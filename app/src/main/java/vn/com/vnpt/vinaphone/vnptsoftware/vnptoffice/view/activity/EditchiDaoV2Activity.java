package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;
// 10h53 .. 10h55
// 3h02

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.PersonReceiveChiDaoV2Adapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SpinnerCheckboxAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.ChiDaoFlow;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.DonViNhan;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.GroupPerson;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.PersonReceiveChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DanhSachDonViNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonInGroupChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SendChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao.ChiDaoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao.IChiDaoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ChoiseGroupEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoAddEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoResultEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonEditChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ShowProgressEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonChiDaoCheck;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.StateVO;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeOperatingViewV2Factory;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IChiDaoView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class EditchiDaoV2Activity extends BaseActivity implements IChiDaoView, ILoginView,
        PersonReceiveChiDaoV2Adapter.OnItemOperatingClickListener {

    String currentGroup;
    String currentUnit;
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
    @BindView(R.id.btnSaveDraft)
    Button btnSaveDraft;
    @BindView(R.id.btnSendAll)
    Button btnSendAll;
    @BindView(R.id.layoutStep3)
    LinearLayout layoutStep3;
    @BindView(R.id.layoutDisplay)
    LinearLayout layoutDisplay;
    @BindView(R.id.btnSelectFile)
    TextView btnSelectFile;
    @BindView(R.id.layoutFile)
    LinearLayout layoutFile;
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
    private ArrayList<Uri> selectedFiles;
    private int stepCurrent;
    private IChiDaoPresenter chiDaoPresenter = new ChiDaoPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private List<String> fileNames;
    private String event;
    private List<PersonChiDao> personChiDaos;
    private String thongTinId;
    private List<PersonReceiveChiDao> personReceiveChiDaos;
    private List<GroupPerson> groupPersonList;
    private List<DonViNhan> listDonViNhan;
    private List<PersonChiDao> danhSachDonViNhanList;
    private ChiDaoFlow chiDaoFlow;
    private List<vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao> fileChiDaos;
    private List<FileChiDao> fileChiDaoList;
    private List<String> deleteFiles;
    private String groups;
    private List<String> personInGroup;
    private List<String> personInGroupPre;
    private List<PersonReceiveChiDao> personEditChiDaos;
    private TreeViewOperatingV2 treeViewOperatingV2;
    private TreeNode root;
    PersonReceiveChiDaoV2Adapter personReceiveAdapter;
    private List<String> empOld = new ArrayList<>();
    HashMap<String, List<String>> empOldMap = new HashMap<>();
    private PersonReceiveChiDao personReceive;
    private String agentId = "";
    private boolean isLoad = true;


    List<String> personInGroupOld = new ArrayList<>();
    ArrayList<String> listxoa = new ArrayList<>();
    List<String> listAdd = new ArrayList<>();


    private List<String> personInGroupAdd = new ArrayList<>();
    private List<String> listGroupChecked = new ArrayList<>();


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chi_dao_v2);
        init();
    }

    private void init() {
        personInGroup = new ArrayList<>();
        ButterKnife.bind(this);
        thongTinId = getIntent().getStringExtra("id");
        selectedFiles = new ArrayList<>();
        stepCurrent = 1;
        deleteFiles = new ArrayList<>();
        checkSMS.setChecked(false);
        connectionDetector = new ConnectionDetector(this);
        edtKeywordName.setTypeface(Application.getApp().getTypeface());
        edtKeywordNameReceive.setTypeface(Application.getApp().getTypeface());

        // Tìm kiếm bước 2
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

        // Tìm kiếm bước 3
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
        getDetail();
    }

    private static final String TAG = "HUYTESTVER2";
    HashMap<String, List<String>> empAddMap = new HashMap<>();
    HashMap<String, List<String>> empDeleteMap = new HashMap<>();
    HashMap<String, List<String>> compareGroupMap = new HashMap<>();
    Boolean checkLoadListCompare = false;
    private List<String> personGroupDelete = new ArrayList<>();

    public void saveData() {
        if (treeViewOperatingV2 != null) {
            List<TreeNode> selectedNodes = treeViewOperatingV2.getSelectedXLCNodes(); // láy danh sách người hiện đang chọn
            List<String> empDelete = new ArrayList<>(); // danh sách người xóa
            List<String> empAdd = new ArrayList<>(); // danh sách người thêm gửi lên server
            List<String> empAddPerson = new ArrayList<>();
            if (selectedNodes.size() > 0 || (personInGroup != null && personInGroup.size() > 0)
                    || personInGroupOld.size() > 0 || empOldMap.get(currentGroup).size() > 0) {
                empAddPerson.clear();
                Log.d(TAG, "saveData: empaddperson:" + empAddPerson.size() +
                        "//empDelete:" + empDelete.size() +
                        "//empAdd:" + empAdd.size() +
                        "//empOldMap:" + empOldMap.get(currentGroup).size() +
                        "//empAddMap:" + empAddMap.get(currentGroup).size() +
                        "//empDeleteMap:" + empDeleteMap.get(currentGroup).size());
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
                Log.d(TAG, "saveData: empaddperson:" + empAddPerson.size() +
                        "//empDelete:" + empDelete.size() +
                        "//empAdd:" + empAdd.size() +
                        "//empOldMap:" + empOldMap.get(currentGroup).size() +
                        "//empAddMap:" + empAddMap.get(currentGroup).size() +
                        "//empDeleteMap:" + empDeleteMap.get(currentGroup).size());
                if (personInGroup != null)
                    for (String pInGroup : personInGroup) {
                        if (!personInGroupOld.contains(pInGroup)) {
                            personInGroupAdd.add(pInGroup);
                        }
                    }
                personGroupDelete.clear();
                if (personInGroupOld != null)
                    for (String pInGroupOld : personInGroupOld) {
                        if (personInGroup != null && !personInGroup.contains(pInGroupOld)) {
                            personGroupDelete.add(pInGroupOld);
                        }
                    }
                personInGroupOld.clear();
                personInGroupOld.addAll(personInGroup);
                for (String l : empAddMap.get(currentGroup)) {
                    Log.d(TAG, "listAddNew: " + l);
                }

                for (String l : empDeleteMap.get(currentGroup)) {
                    Log.d(TAG, "listDeleteNew: " + l);
                }
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
            sendSever(thongTinId, listAdd, listDelete);
            listAdd.clear();
            listDelete.clear();
        } else
            nextStep3();
    }


    private void getPersonsReceive() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON_REVEICE";
            chiDaoPresenter.getPersonReceiveChiDao(new PersonReceiveChiDaoRequest("", "", thongTinId, edtKeywordNameReceive.getText().toString().trim()));
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

    //spiner
    private void getDonViNhan() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_DONVI_NHAN";
            chiDaoPresenter.getDonViNhan();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    //list danh sách sau khi chọn spiner đơn vị
    private void getDanhSachDonViNhan(String param, String agentId) {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_DANHSACH_DONVI_NHAN";
            chiDaoPresenter.getDanhSachDonViNhan(new DanhSachDonViNhanRequest(param, agentId));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getDetail() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_DETAIL";
            showProgress();
            chiDaoPresenter.getDetailChiDao(thongTinId);
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

    private void getPersonInGroup(String groups) {
        if (connectionDetector.isConnectingToInternet()) {
            Log.d(TAG, "getPersonInGroup: " + groups);
            chiDaoPresenter.getPersonIngroup(new PersonInGroupChiDaoRequest(groups));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getPersonInGroup() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON_IN_GROUP";
            chiDaoPresenter.getPersonIngroup(new PersonInGroupChiDaoRequest(groups));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_FILE";
            chiDaoPresenter.getFileChiDao(thongTinId);
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
                    if (tvTieude.getText() != null && !tvTieude.getText().toString().trim().equals("")) {
                        if (selectedFiles != null && selectedFiles.size() > 0) {
                            MultipartBody.Part[] parts = new MultipartBody.Part[selectedFiles.size()];
                            int i = -1;
                            for (Uri uri : selectedFiles) {
                                i++;
                                parts[i] = prepareFilePart("fileupload", uri);
                                String[] uriStr = uri.getPath().split("/");
                                fileNames.add(uriStr[uriStr.length - 1]);
                            }
                            chiDaoPresenter.uploadFiles(parts);
                        } else {
                            EventBus.getDefault().postSticky(new ShowProgressEvent(true));
                            ChiDaoRequest chiDaoRequest = new ChiDaoRequest(chiDaoFlow.getId(), tvTieude.getText().toString().trim(),
                                    tvNoidung.getText().toString().trim(), fileNames, deleteFiles, "", 0);
                            chiDaoPresenter.saveChiDao(chiDaoRequest);
                        }
                    } else {
                        tvTieude.setError(getString(R.string.TIEUDE_REQUIERD));
                    }
                    break;
                case 2:
                    event = "SAVE_PERSON";
                    saveData();
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }


    private void filterNumber(List<String> empDelete1) {
        for (int i = 0; i < empDelete1.size(); i++) {
            if (isNumeric(empDelete1.get(i))) {
                empDelete1.remove(i);
            }
        }
    }

    private void filterAdd() {
        for (int i = 0; i < listAdd.size(); i++) {
            if (isNumeric(listAdd.get(i))) {
                listAdd.remove(i);
            }
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

    private void fillData() {
        tvTieude.setText(chiDaoFlow.getTieuDe());
        if (chiDaoFlow.getNoiDung() != null && !chiDaoFlow.getNoiDung().trim().equals("")) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                tvNoidung.setText(Html.fromHtml(chiDaoFlow.getNoiDung()));
            } else {
                tvNoidung.setText(Html.fromHtml(chiDaoFlow.getNoiDung(), Html.FROM_HTML_MODE_COMPACT));
            }
        } else {
            tvNoidung.setText("");
        }
    }

    private void fillFiles() {
        fileChiDaoList = new ArrayList<>();
        for (int i = 0; i < fileChiDaos.size(); i++) {
            FileChiDao fileChiDao = new FileChiDao(fileChiDaos.get(i).getName());
            fileChiDaoList.add(fileChiDao);
        }
        FileChiDaoAdapter fileChiDaoAdapter = new FileChiDaoAdapter(this, R.layout.item_file_chidao_list, fileChiDaoList, "EDIT");
        int adapterCount = fileChiDaoAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = fileChiDaoAdapter.getView(i, null, null);
            layoutFile.addView(item);
        }
    }

    @OnClick({R.id.btnSave, R.id.btnSaveDraft, R.id.btnSelectFile, R.id.btnSendAll, R.id.btnBack, R.id.btnBackStep1, R.id.btnBackStep2, R.id.btnSavePerson})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSavePerson:
                saveInfo(); // lưu dữ liệu vào local
                sendSever();
                break;
            case R.id.btnBackStep1:
                backStep1();
                break;
            case R.id.btnBackStep2:
                backStep2();
                getPersonsEdit();
                break;
            case R.id.btnSave:
                saveInfo();
                break;
            case R.id.btnSaveDraft:
                saveInfo();
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


    private void getPersonsEdit() {
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new PersonChiDaoAddEvent(new ArrayList<String>()));
            personInGroupPre = new ArrayList<>();
            event = "GET_PERSON_EDIT";
            if (personEditChiDaos == null)
                chiDaoPresenter.getPersonReceiveChiDao(new PersonReceiveChiDaoRequest("", "", thongTinId, ""));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }


    private void backStep1() {
        stepCurrent = 1;
        layoutStep1.setVisibility(View.VISIBLE);
        layoutStep2.setVisibility(View.GONE);
        layoutStep3.setVisibility(View.GONE);
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
        layoutSave.setVisibility(View.GONE);
        layoutSavePerson.setVisibility(View.VISIBLE);
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
        layoutSend.setVisibility(View.GONE);
        isLoad = false;
        getPersonsEdit();
    }

    private void nextStep3() {
        stepCurrent = 3;
        layoutStep1.setVisibility(View.GONE);
        layoutStep2.setVisibility(View.GONE);
        layoutStep3.setVisibility(View.VISIBLE);
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
        layoutSave.setVisibility(View.GONE);
        layoutSavePerson.setVisibility(View.GONE);
        layoutSend.setVisibility(View.VISIBLE);
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
        getPersonsReceive();
    }

    List<String> listpersonEditChiDaos = new ArrayList<>();

    @Override
    public void onSuccess(Object object) {
        if (event != null && event.equals("GET_FILE")) {
            fileChiDaos = ConvertUtils.fromJSONList(object, vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao.class);
            if (fileChiDaos != null && fileChiDaos.size() > 0) {
                fillFiles();
            }
            getGroupUser();
        }
        if (event != null && event.equals("GET_DETAIL")) {
            chiDaoFlow = ConvertUtils.fromJSON(object, ChiDaoFlow.class);
            fillData();
            getFiles();
            hideProgress();
        }
        if (event != null && event.equals("GET_GROUP")) {
            groupPersonList = ConvertUtils.fromJSONList(object, GroupPerson.class);
            getDonViNhan();
            displayPersonGroup();
            if (checkLoadListCompare) {
                removeCheck(listpersonEditChiDaos, true);
                for (int i = 0; i < lst.size(); i++) {
                    if (lst.get(i).isSelected()) {
                        personInGroupOld.addAll(compareGroupMap.get(lst.get(i).getId()));
                        personInGroup.addAll(compareGroupMap.get(lst.get(i).getId()));
                    }
                }
            } else {
                if (groupPersonList != null && groupPersonList.size() > 0)
                    for (int i = 0; i < groupPersonList.size(); i++) {
                        String id = groupPersonList.get(i).getId();
                        currentUnit = id;
                        if (compareGroupMap.get(id) == null) {
                            compareGroupMap.put(id, new ArrayList<String>());
                        }
                    }
                if (groupPersonList != null && groupPersonList.get(0).getId() != null)
                    getPersonInGroup(groupPersonList.get(0).getId());
//                if (groupPersonList == null) {
//                    checkLoadListCompare = true;
//                } else if (groupPersonList.size() == 0) checkLoadListCompare = true;
            }
        }

        if (event != null && event.equals("GET_PERSON_EDIT")) {
            personEditChiDaos = ConvertUtils.fromJSONList(object, PersonReceiveChiDao.class);
            EventBus.getDefault().postSticky(new PersonEditChiDaoEvent(personEditChiDaos));
            PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
            List<String> chons = emails.getEmails();
            if (chons == null) {
                chons = new ArrayList<>();
            }

            if (personEditChiDaos != null && personEditChiDaos.size() > 0) {
                for (int i = 0; i < personEditChiDaos.size(); i++) {
                    if (!chons.contains(personEditChiDaos.get(i).getId())) {
                        chons.add(personEditChiDaos.get(i).getId());
                    }
                    listpersonEditChiDaos.add(personEditChiDaos.get(i).getId());
                }
                emails.setEmails(chons);
            }
            new ProgressTask(3).execute();
            EventBus.getDefault().postSticky(emails);
            getGroupUser();


        }
        if (event != null && event.equals("SAVE_CHIDAO")) {
            //  Toast.makeText(this, getString(R.string.SAVE_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
            hideSoftInput();
            backStep2();

        }
        if (event != null && event.equals("GET_PERSON_REVEICE")) {
            // danh sách người đã lưu ở trên server
            personReceiveChiDaos = ConvertUtils.fromJSONList(object, PersonReceiveChiDao.class);
            displayPersonReceive();
        }
        if (event != null && event.equals("SAVE_PERSON")) {
            nextStep3();
            Toast.makeText(this, getString(R.string.SAVE_PERSON_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
        }

        if (event != null && event.equals("SEND_CHIDAO")) {
            Toast.makeText(this, getString(R.string.SEND_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
            onBackPressed();
        }
        if (event != null && event.equals("GET_REMOVE_PERSON_OPERATING")) {
            Toast.makeText(this, getString(R.string.REMOVE_PERSON_SUCCESS), Toast.LENGTH_LONG).show();
            //Update adapter Receive
            if (personReceive != null) {
                personReceiveChiDaos.remove(personReceive);
                personReceiveAdapter.updateDataSetChanged(personReceiveChiDaos);
            }
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

    @Override
    public void onSuccessDanhSachDonViNhan(Object object) {
        if (event != null && event.equals("GET_DANHSACH_DONVI_NHAN")) {
            if (object instanceof List) {
                List<Object> lstObj = (List) object;
                if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof PersonChiDao) {
                    danhSachDonViNhanList = (List<PersonChiDao>) object;
                    txtNoData.setVisibility(View.GONE);
                    layoutContact.removeAllViews();
                    new ProgressTask(9).execute();
                    hideProgress();
                } else {
                    txtNoData.setVisibility(View.VISIBLE);

                }

            }
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
                currentGroup = agentId;
                showProgress();
                if (empOldMap.get(agentId) == null && empOldMap.get(agentId) == null && empOldMap.get(agentId) == null) {
                    empOldMap.put(agentId, new ArrayList<String>());
                    empAddMap.put(agentId, new ArrayList<String>());
                    empDeleteMap.put(agentId, new ArrayList<String>());
                }
                getDanhSachDonViNhan(edtKeywordName.getText().toString().trim(), agentId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sDonViGui.setSelection(0, true);
    }

    @Override
    public void onSuccess() {
        ChiDaoRequest chiDaoRequest = new ChiDaoRequest(chiDaoFlow.getId(), tvTieude.getText().toString().trim(),
                tvNoidung.getText().toString().trim(), fileNames, deleteFiles, "", 0);
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
                Log.d(TAG, "onSuccess: compareGroupMap:" + groupPersonList.get(posstionGroupPersonList).getId() + ":" + compareGroupMap.get(groupPersonList.get(posstionGroupPersonList).getId()).size());
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

    private void sendSever(String id, List<String> empAdd, List<String> empDelete) { // gửi lên sever
        chiDaoPresenter
                .savePersonChiDao
                        (new SavePersonChiDaoRequest(id, empAdd, empDelete));
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
            if (event != null && event.equals("GET_DETAIL")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_DETAIL_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_FILE")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_FILE_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON_IN_GROUP")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_IN_GROUP_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON_EDIT")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
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
            if (event != null && event.equals("GET_DETAIL")) {
                getDetail();
            }
            if (event != null && event.equals("GET_PERSON_IN_GROUP")) {
                getPersonInGroup();
            }
            if (event != null && event.equals("GET_PERSON_EDIT")) {
                getPersonsEdit();
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
                        List<FileChiDao> fileChiDaos = new ArrayList<>();
                        for (Uri uri : sessionSelectedFiles) {
                            String[] uriStr = uri.getPath().split("/");
                            if (!checkDuplicate(uri)) {
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
                                Toast.makeText(this, getString(R.string.str_co_filetrung), Toast.LENGTH_LONG).show();
                            }
                        }
                        FileChiDaoAdapter fileChiDaoAdapter = new FileChiDaoAdapter(this, R.layout.item_file_chidao_list, fileChiDaos,
                                "EDIT");
                        int adapterCount = fileChiDaoAdapter.getCount();
                        for (int i = 0; i < adapterCount; i++) {
                            View item = fileChiDaoAdapter.getView(i, null, null);
                            layoutFile.addView(item);
                        }
                    }
                }
                break;
        }
    }

    private boolean checkDuplicate(Uri uri) {
        String[] uriStr = uri.getPath().split("/");
        if (selectedFiles.contains(uri)) {
            return true;
        } else {
            if (fileChiDaos != null && fileChiDaos.size() > 0) {
                for (int i = 0; i < fileChiDaos.size(); i++) {
                    if (fileChiDaos.get(i).getName().toUpperCase().equals(uriStr[uriStr.length - 1].toUpperCase())) {
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
        if (lst != null && lst.size() >= 2) {
            if (listGroupChecked.size() > 0) {
                String s = "";
                for (int i = 0; i < listGroupChecked.size(); i++) {
                    s = s + listGroupChecked.get(i) + ",";
                }
                getPersonInGroup(s.substring(0, s.length() - 1));
            }
            if (listGroupChecked != null && listGroupChecked.size() > 0) {
                if (listGroupChecked.size() == (lst.size() - 2)) {
                    lst.get(0).setTitle(lst.get(1).getTitle());
                    lst.get(1).setSelected(true);
                }
                for (String s : listGroupChecked) {
                    for (StateVO stateVO : lst) {
                        if (s.equals(stateVO.getId())) {
                            if (listGroupChecked.size() != (lst.size() - 2)) {
                                lst.get(0).setTitle(stateVO.getTitle());
                            }
                            stateVO.setSelected(true);
                        }
                    }
                }

            }
        }
        setupSpinnerNhom();
    }

    SpinnerCheckboxAdapter myAdapter;

    private void setupSpinnerNhom() {
        myAdapter = new SpinnerCheckboxAdapter(this, 0, lst, "EDIT");
        sNhom.setAdapter(myAdapter);
    }

    private void displayPersonReceive() {
        if (personReceiveChiDaos != null && personReceiveChiDaos.size() > 0) {
            Log.d("HUYTEST", "displayPersonReceive: " + personReceiveChiDaos.size());
            txtNoDataReceive.setVisibility(View.GONE);
            layout_receive.setVisibility(View.VISIBLE);
            if (personReceiveAdapter == null) {
                personReceiveAdapter = new PersonReceiveChiDaoV2Adapter(this, R.layout.item_person_chidao_send,
                        personReceiveChiDaos, thongTinId);
                layout_receive.setAdapter(personReceiveAdapter);
            } else {
                personReceiveAdapter.updateDataSetChanged(personReceiveChiDaos);
            }

        } else {
            txtNoDataReceive.setVisibility(View.VISIBLE);
            layout_receive.setVisibility(View.GONE);

        }
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
        EventBus.getDefault().removeStickyEvent(ChoiseGroupEvent.class);
        hideProgress();
    }

    @Override
    public void onBackPressed() {
        hideSoftInput();
        EventBus.getDefault().removeStickyEvent(ChoiseGroupEvent.class);
        EventBus.getDefault().removeStickyEvent(PersonChiDaoAddEvent.class);
        EventBus.getDefault().removeStickyEvent(PersonEditChiDaoEvent.class);
        EventBus.getDefault().removeStickyEvent(PersonChiDaoResultEvent.class);
        finish();
    }

    @OnCheckedChanged(R.id.checkSMS)
    public void checkSMS(CompoundButton compoundButton, boolean b) {
        EventBus.getDefault().postSticky(new Boolean(b));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ChoiseGroupEvent choiseGroupEvent) {
        String type = choiseGroupEvent.getType();
        if (type.equals("EDIT")) {
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


    @Override
    public void removeOnclickListener(PersonReceiveChiDao personReceiveRemove) {
        event = "GET_REMOVE_PERSON_OPERATING";
        this.personReceive = personReceiveRemove;
        chiDaoPresenter.savePersonChiDao(new SavePersonChiDaoRequest(thongTinId, new ArrayList<String>(),
                Arrays.asList(personReceiveRemove.getId())));


    }

    @Override
    public void sendOnclickListener(PersonReceiveChiDao personReceiveSend) {
        event = "GET_SEND_PERSON_OPERATING";
        this.personReceive = personReceiveSend;
        chiDaoPresenter.send(new SendChiDaoRequest(thongTinId, checkSMS.isChecked() ? "1" : "0", personReceive.getId()));
        List<TreeNode> allTree = treeViewOperatingV2.getAllNodes();
        for (int i = 0; i < allTree.size(); i++) { // xóa đi cái node ở tree hiện tại
            PersonChiDao p = ((PersonChiDao) allTree.get(i).getValue());
            if (p.getId().equalsIgnoreCase(personReceiveSend.getId())) {
                allTree.get(i).setSelectedXLC(false);
                Log.d(TAG, "removeOnclickListener: remove" + personReceiveSend.getId());
            }
        }
        treeViewOperatingV2.refreshTreeView();
        listxoa.add(personReceiveSend.getId());
        removeCheck(listxoa, false);
    }

    private void addEmpOldMap() {
        if (treeViewOperatingV2 != null && empOldMap.get(currentGroup) != null && personEditChiDaos != null) {
            Log.d(TAG, "run addEmpOldMap: ");
            List<TreeNode> allTree = treeViewOperatingV2.getAllNodes();
            if (personEditChiDaos != null && personEditChiDaos.size() > 0) {
                Log.d(TAG, "addEmpOldMap: personEditChiDaos" + personEditChiDaos.size());
                for (int i = 0; i < allTree.size(); i++) {
                    for (int j = 0; j < personEditChiDaos.size(); j++) {
                        String idPerson = personEditChiDaos.get(j).getId();
                        if (idPerson.equalsIgnoreCase
                                (((PersonChiDao) allTree.get(i).getValue()).getId())) {
                            empOldMap.get(currentGroup).add(idPerson);
                            Log.d(TAG, "addEmpOldMap: " + empOldMap.get(currentGroup).size());
                            personEditChiDaos.remove(j);
                        }
                    }
                }
                Log.d(TAG, "addEmpOldMap: personEditChiDaos" + personEditChiDaos.size());
            }
        }
    }

    private void removeCheck(List<String> list, boolean setSelected) {
        if (list != null && groupPersonList != null) {
            for (int i = 0; i < groupPersonList.size(); i++) {
                if (compareGroupMap.get(groupPersonList.get(i).getId()) != null && compareGroupMap.get(groupPersonList.get(i).getId()).size() > 0) {
                    if (removeCheckGroup(compareGroupMap.get(groupPersonList.get(i).getId()), list)) {
                        for (int j = 2; j < lst.size(); j++) {
                            if (lst.get(j).getId().equals(groupPersonList.get(i).getId())) {
                                lst.get(j).setSelected(setSelected);
                                Log.d(TAG, "removeCheck: " + lst.get(j).getTitle());
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
                    Log.d(TAG, "removeCheckGroup: " + check);
                }
            }
        }
        return (check == list.size());
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
            try {
                showProgressDialog();
            } catch (final Throwable th) {
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return updateDataTreeView(typeUpdateData);

        }

        @Override
        protected void onPostExecute(Boolean result) {
            hideProgress();
            Log.d("HUYTEST", "onPostExecute: " + result);
            if (result) {
                if (typeUpdateData == 9) {
                    txtNoData.setVisibility(View.GONE);
                    treeViewOperatingV2 = new TreeViewOperatingV2(root, EditchiDaoV2Activity.this, new MyNodeOperatingViewV2Factory());
                    View view = treeViewOperatingV2.getView();
                    view.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    layoutContact.addView(view);
                    treeViewOperatingV2.expandLevel(0);
                    addEmpOldMap();
                    new ProgressTask(1).execute();
                } else {
                    treeViewOperatingV2.refreshTreeView();
                }

            } else if (typeUpdateData == 9) {
                txtNoData.setText(getString(R.string.str_khongthay_danhsach));
                txtNoData.setVisibility(View.VISIBLE);
            }
        }
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
                    Log.d("HuyTest", "updateDataTreeView: " + treeNode.getId());
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

    public void removeFile(String filename) {
        for (int i = 0; i < fileChiDaos.size(); i++) {
            if (fileChiDaos.get(i).getName().toUpperCase().equals(filename.toUpperCase())) {
                if (fileChiDaos.get(i).getHdd() != null && !fileChiDaos.get(i).getHdd().trim().equals("")) {
                    deleteFiles.add(fileChiDaos.get(i).getHdd());
                }
                fileChiDaos.remove(i);
                return;
            }
        }
        for (int i = 0; i < selectedFiles.size(); i++) {
            Uri uri = selectedFiles.get(i);
            String[] uriStr = uri.getPath().split("/");
            if (filename.equals(uriStr[uriStr.length - 1])) {
                selectedFiles.remove(i);
                return;
            }
        }
    }

    public void showContact() {
        layoutContact.setVisibility(View.VISIBLE);
        txtNoData.setVisibility(View.GONE);
    }

    private List<String> removeRepeated(List<String> al) {
// add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(al);
        al.clear();
        al.addAll(hs);
        return al;
    }
}
