package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.filebrowser.FileChooser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.AttachFileCustomAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.FileChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.FileUploadWorkAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.UnitPersonCreateWorkAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileUploadWorkInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CreateSubTaskRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ObjectAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailJobManageResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListBossSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobAssignRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListStatusComboxRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateStatusJobResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.IWorkCreateSubTaskPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.IWorkManagePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.WorkCreateSubTaskPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.WorkManagePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog.DialogPersonTaoCVClass;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog.DialogUnitClass;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListUnitSelectEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListUnitUserTaoCVEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListUserSelectEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SelectUnitForPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TimeTaoCongViecEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UserSelectEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.ScheduleWeekFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ICreeateSubTaskView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkDetailView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkManageView;

public class TaoCongViecMoiActivity extends BaseActivity implements ICreeateSubTaskView, IWorkManageView, IWorkDetailView, IExceptionErrorView {
    private static final int SELECT_FILE_RESULT_SUCCESS = 1;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.spin_assign_task)
    Spinner spinAssignTask;
    @BindView(R.id.spin_name_work)
    EditText spinNameWork;
    @BindView(R.id.edt_name_work)
    EditText edtNameWork;
    @BindView(R.id.image_upload_file)
    ImageView imageUploadFile;
    @BindView(R.id.recycler_view_file_upload)
    RecyclerView recyclerViewFileUpload;
    @BindView(R.id.spin_work_level)
    Spinner spinWorkLevel;
    @BindView(R.id.edt_date_finish)
    EditText edtDateFinish;
    @BindView(R.id.spin_unit)
    EditText spinUnit;
    @BindView(R.id.spin_person)
    EditText spinPerson;
    @BindView(R.id.recycler_view_unit_person)
    RecyclerView recyclerViewUnitPerson;

    IWorkCreateSubTaskPresenter workCreateSubTaskPresenter = new WorkCreateSubTaskPresenterImpl(this);
    IWorkManagePresenter iWorkManagePresenter = new WorkManagePresenterImpl(this, this);
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private CreateSubTaskRequest createSubTaskRequest;

    String idDayParent = "";
    List<PersonSendNotifyInfo> listUnitData = new ArrayList<>();
    private DialogPersonTaoCVClass dialogPersonClass;
    private DialogUnitClass dialogUnitClass;
    private List<ObjectAssignRequest> listUnitPersonData = new ArrayList<>();
    private UnitPersonCreateWorkAdapter unitPersonCreateWorkAdapter;
    private FileUploadWorkAdapter fileUploadWorkAdapter;
    private ApplicationSharedPreferences appPrefs;
    private boolean isShowDialogUnit = true;
    private List<ObjectAssignRequest> noRepeat;
    private ArrayList<FileUploadWorkInfo> listfileUpload;
    private RecyclerView.LayoutManager layoutManager;
    private List<vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao> fileChiDaos;
    private String unitUser;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_cong_viec_moi);
        //    ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("LIST_FILE_UPLOAD", toJSON(listfileUpload));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            try {
                JSONArray jArray = new JSONArray(savedInstanceState.getString("LIST_FILE_UPLOAD"));
                if (jArray != null) {
                    listfileUpload.clear();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        FileUploadWorkInfo fileUploadWorkInfo = new FileUploadWorkInfo();
                        fileUploadWorkInfo.setName(json.getString("name"));
                        fileUploadWorkInfo.setUri(Uri.parse(json.getString("uri")));
                        fileUploadWorkInfo.setFileRoot(json.getBoolean("fileRoot"));
                        listfileUpload.add(fileUploadWorkInfo);
                    }
                    fileUploadWorkAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    String toJSON(ArrayList<FileUploadWorkInfo> list) {
        JSONArray jsonArray = new JSONArray();
        for (FileUploadWorkInfo file : list) {
            JSONObject json = new JSONObject();
            try {
                json.put("name", file.getName());
                json.put("uri", file.getUri().toString());
                json.put("fileRoot", file.isFileRoot());
                jsonArray.put(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        createSubTaskRequest = new CreateSubTaskRequest();
        appPrefs = Application.getApp().getAppPrefs();
        unitUser = appPrefs.getUnitUser();
        edtNameWork.setText("");
        spinNameWork.setText("");

        tvTitle.setText(getString(R.string.text_title_create_work_new));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        getListBossData();
        getListWorkLevel();
        noRepeat = new ArrayList<ObjectAssignRequest>();

        listfileUpload = new ArrayList<FileUploadWorkInfo>();
        recyclerViewFileUpload.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewFileUpload.setNestedScrollingEnabled(false);
        recyclerViewFileUpload.setLayoutManager(layoutManager);
        fileUploadWorkAdapter = new FileUploadWorkAdapter(this, listfileUpload);
        recyclerViewFileUpload.setAdapter(fileUploadWorkAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnClick({R.id.edt_date_finish, R.id.spin_unit, R.id.spin_person, R.id.btn_create_work, R.id.image_upload_file})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_date_finish:
                showDialogPicker();
                break;
            case R.id.spin_unit:
                if (edtDateFinish.getText().toString().isEmpty()) {
                    AlertDialogManager.showAlertDialog(this, getResources().getString(R.string.str_dialog_thongbao),
                            getResources().getString(R.string.check_create_work_new), true, AlertDialogManager.ERROR);
                    return;
                }

                if (listUnitData != null && listUnitData.size() > 0) {
                    showDialogUnitSelect();
                } else {
                    getListUnitData();
                }

                break;
            case R.id.spin_person:
                if (edtDateFinish.getText().toString().isEmpty()) {
                    AlertDialogManager.showAlertDialog(this, getResources().getString(R.string.str_dialog_thongbao),
                            getResources().getString(R.string.check_create_work_new), true, AlertDialogManager.ERROR);
                    return;
                }

                if (listUnitData != null && listUnitData.size() > 0) {
                    getListPersonData(unitUser);
                } else {
                    isShowDialogUnit = false;
                    getListUnitData();
                }
                break;
            case R.id.image_upload_file:
                Intent i2 = new Intent(this, FileChooser.class);
                i2.putExtra(com.aditya.filebrowser.Constants.SELECTION_MODE, com.aditya.filebrowser.Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
                startActivityForResult(i2, SELECT_FILE_RESULT_SUCCESS);
                break;
            case R.id.btn_create_work:
                if (spinNameWork.getText().toString().trim().isEmpty()) {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                            getResources().getString(R.string.check_ten_cong_viec), true, AlertDialogManager.WARNING);
                    spinNameWork.requestFocus();
                    return;
                }
                if (edtNameWork.getText().toString().trim().isEmpty()) {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                            getResources().getString(R.string.check_noidung_cong_viec), true, AlertDialogManager.WARNING);
                    edtNameWork.requestFocus();
                    return;
                }
                if (edtDateFinish.getText().toString().trim().isEmpty()) {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                            getResources().getString(R.string.check_ngay_het_han_cong_viec), true, AlertDialogManager.WARNING);
                    return;
                }
                // Check hạn xử lý cá nhân / đơn vị
                String strMaxDate = edtDateFinish.getText().toString().trim();
                Date maxDate = null;
                try {
                    maxDate = new SimpleDateFormat("dd/MM/yyyy").parse(strMaxDate);
                    for (ObjectAssignRequest objCheckDate : listUnitPersonData) {
                        String strEndDate = objCheckDate.getEndDate();
                        Date endDate = null;
                        endDate = new SimpleDateFormat("dd/MM/yyyy").parse(strEndDate);
                        if (maxDate.compareTo(endDate) < 0) {
                            Log.d("Thao", "Date1 is before Date2");
                            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                    "Ngày hết hạn đơn vị/cá nhân: " + objCheckDate.getNameUser() + " phải nhỏ hơn hạn chung !",
                                    true, AlertDialogManager.WARNING);
                            return;
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ListUnitUserTaoCVEvent event = EventBus.getDefault().getStickyEvent(ListUnitUserTaoCVEvent.class);
                if (event == null) {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                            "Phải chọn đơn vị/cá nhân xử lý chính", true, AlertDialogManager.WARNING);
                    return;
                } else {
                    if (event.getListData() == null) {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                "Phải chọn đơn vị/cá nhân xử lý chính", true, AlertDialogManager.WARNING);
                        return;
                    } else {
                        List<ObjectAssignRequest> listUnitData = new ArrayList<>();
                        for (int i = 0; i < event.getListData().size(); i++) {
                            if (event.getListData().get(i).getUserId().equalsIgnoreCase("")) {
                                listUnitData.add(event.getListData().get(i));
                            }
                        }
                        createSubTaskRequest.setUnits(listUnitData);

                        List<ObjectAssignRequest> lisUserData = new ArrayList<>();
                        for (int i = 0; i < event.getListData().size(); i++) {
                            if (!event.getListData().get(i).getUserId().equalsIgnoreCase("")) {
                                lisUserData.add(event.getListData().get(i));
                            }
                        }
                        createSubTaskRequest.setUsers(lisUserData);
                    }
                }
                callApiCreateWork();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case SELECT_FILE_RESULT_SUCCESS:
                if (resultCode == RESULT_OK) {
                    ArrayList<Uri> sessionSelectedFiles = data.getParcelableArrayListExtra(com.aditya.filebrowser.Constants.SELECTED_ITEMS);
                    if (sessionSelectedFiles != null && sessionSelectedFiles.size() > 0) {
                        for (Uri uri : sessionSelectedFiles) {
                            if (!checkDuplicate(uri)) {
                                String[] uriStr = uri.getPath().split("/");
                                if (validateExtension(uriStr[uriStr.length - 1])) {
                                    File file = new File(uri.getPath());
                                    if (validateSize(file)) {
                                        listfileUpload.add(new FileUploadWorkInfo(uriStr[uriStr.length - 1], uri, false));
                                        fileUploadWorkAdapter.notifyItemInserted(listfileUpload.size());
                                    } else {
                                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.INVALID_FILE_SIZE), true, AlertDialogManager.ERROR);
                                    }
                                } else {
                                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.INVALID_FILE_EXT), true, AlertDialogManager.ERROR);
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    private boolean checkDuplicate(Uri uri) {
        String[] uriStr = uri.getPath().split("/");
        if (listfileUpload != null) {
            for (FileUploadWorkInfo file : listfileUpload) {
                if (file.getUri().equals(uri) || file.getName().equalsIgnoreCase(uriStr[uriStr.length - 1])) {
                    return true;
                }
            }
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

    private void callApiCreateWork() {
        if (spinNameWork.getText().toString().trim().isEmpty() || edtNameWork.getText().toString().trim().isEmpty()
                || edtDateFinish.getText().toString().trim().isEmpty()) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                    "Cần nhập liệu đủ các trường Tên công việc, Nội dung, hạn xử lý", true, AlertDialogManager.WARNING);
        } else {
            boolean isXLC = false;
            createSubTaskRequest.setName(spinNameWork.getText().toString().trim());
            createSubTaskRequest.setContent(edtNameWork.getText().toString().trim());
            createSubTaskRequest.setHanXuLy(edtDateFinish.getText().toString().trim());
            createSubTaskRequest.setParentId("");
            createSubTaskRequest.setParentNxlId("");
            if (createSubTaskRequest.getUsers() != null && createSubTaskRequest.getUsers().size() > 0
                    || (createSubTaskRequest.getUnits() != null && createSubTaskRequest.getUnits().size() > 0)) {
                if (createSubTaskRequest.getUsers() != null && createSubTaskRequest.getUsers().size() > 0) {
                    for (int i = 0; i < createSubTaskRequest.getUsers().size(); i++) {

                        if (createSubTaskRequest.getUsers().get(i).getVaiTro().equalsIgnoreCase("1")) {
                            isXLC = true;
                            break;
                        }
                    }
                }
                if (!isXLC) {
                    if (createSubTaskRequest.getUnits() != null && createSubTaskRequest.getUnits().size() > 0) {
                        for (int i = 0; i < createSubTaskRequest.getUnits().size(); i++) {

                            if (createSubTaskRequest.getUnits().get(i).getVaiTro().equalsIgnoreCase("1")) {
                                isXLC = true;
                                break;
                            }
                        }
                    }
                }

                if (isXLC) {
                    if (listfileUpload != null && listfileUpload.size() > 0) {
                        upLoadFile();
                    } else {
                        workCreateSubTaskPresenter.createSubTask(createSubTaskRequest);
                    }

                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                            "Phải chọn đơn vị/cá nhân xử lý chính", true, AlertDialogManager.WARNING);
                }

            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                        "Phải chọn đơn vị/cá nhân xử lý chính", true, AlertDialogManager.WARNING);
            }
        }
    }

    private void upLoadFile() {
        MultipartBody.Part[] parts = new MultipartBody.Part[listfileUpload.size()];

        for (int i = 0; i < listfileUpload.size(); i++) {
            parts[i] = prepareFilePart("fileupload", listfileUpload.get(i).getUri());
        }
        workCreateSubTaskPresenter.UploadFileWork(parts);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = new File(fileUri.getPath());
        String mimeType = URLConnection.guessContentTypeFromName(URLEncoder.encode(file.getName()));//getMimeType(fileUri.getPath())
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private void getListPersonData(String unitId) {
        workCreateSubTaskPresenter.getListPerson(unitId, 1);
    }

    private void getListUnitData() {
        workCreateSubTaskPresenter.getListUnit(1);
    }

    private void getListBossData() {
        workCreateSubTaskPresenter.getListBoss();
    }

    private void getListWorkLevel() {
        iWorkManagePresenter.getListStatusCombox("MUCDOCONGVIEC");
    }

    @Override
    public void onSuccessGetListBossData(List<GetListBossSubTaskResponse.Data> listData) {
        initDataSpinner(listData);
    }

    @Override
    public void onSuccessGetListUnitData(List<PersonSendNotifyInfo> listData) {
        if (listData != null && listData.size() > 0) {
            listUnitData.addAll(listData);
            if (isShowDialogUnit) {
                showDialogUnitSelect();
            } else {
                isShowDialogUnit = true;
                getListPersonData(unitUser);
            }
        }
    }

    @Override
    public void onSuccessGetListPersonData(List<PersonSendNotifyInfo> listData) {
        if (dialogPersonClass != null && dialogPersonClass.isShowing()) {
            dialogPersonClass.updatePersonList(listData, noRepeat);
        } else {
            dialogPersonClass = new DialogPersonTaoCVClass(this, edtDateFinish.getText().toString().trim()
                    , listData, listUnitData, noRepeat);
            dialogPersonClass.show();
        }
    }

    @Override
    public void onSuccessGetListFileData(List<AttachFileInfo> listData) {
    }

    @Override
    public void onSuccessCreateSubTask(UpdateStatusJobResponse response) {
        Toast.makeText(this, getString(R.string.text_sucssess_create_work_new), Toast.LENGTH_LONG).show();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Intent intent = new Intent();
        intent.putExtra("CREATE_WORK_SUCCESS", "TRUE");
        setResult(500, intent);
        finish();
    }

    @Override
    public void onSuccessUpLoad(List<String> object) {
        createSubTaskRequest.setFiles(object);
        workCreateSubTaskPresenter.createSubTask(createSubTaskRequest);
    }

    @Override
    public void onError(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR),
                    getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR),
                    apiError.getMessage(), true, AlertDialogManager.ERROR);
        }
    }


    @Override
    public void onSuccessGetListData(List<GetListJobAssignRespone.Data> listData) {

    }

    @Override
    public void onSuccessGetListStatus(List<GetListStatusComboxRespone.Data> listData) {
        if (listData != null && listData.size() > 0) {
            initDataWorkLevel(listData);
        }
    }


    @Override
    public void onSuccessGetDetailData(DetailJobManageResponse.Data data) {
        unitPersonCreateWorkAdapter.updateDateParent(idDayParent);
    }

    @Override
    public void onErrorGetListData(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR),
                    getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR),
                    apiError.getMessage(), true, AlertDialogManager.ERROR);
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ListUnitSelectEvent event) {
        /* Do something */
        if (event != null && event.getListData().size() > 0) {
            listUnitPersonData.addAll(event.getListData());
            List<ObjectAssignRequest> allEvents = listUnitPersonData;
            for (ObjectAssignRequest obj : allEvents) {
                boolean isFound = false;
                for (ObjectAssignRequest e : noRepeat) {
                    if (e.getNameUser().equals(obj.getNameUser()) || (e.equals(obj))) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) noRepeat.add(obj);
            }
            unitPersonCreateWorkAdapter = new UnitPersonCreateWorkAdapter(noRepeat, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
            recyclerViewUnitPerson.setLayoutManager(mLayoutManager);
            recyclerViewUnitPerson.setAdapter(unitPersonCreateWorkAdapter);
            EventBus.getDefault().postSticky(new ListUnitUserTaoCVEvent(noRepeat));
        }
        unitPersonCreateWorkAdapter.notifyDataSetChanged();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UserSelectEvent event) {
        if (event != null)
            if (event.getUserData() != null && event.getUserData() instanceof ObjectAssignRequest) {
                ObjectAssignRequest objectAssignRequest = (ObjectAssignRequest) event.getUserData();
                listUnitPersonData.remove(objectAssignRequest);
                if (event.getTypeAction() == 3) {
                    noRepeat.remove(objectAssignRequest);
                }
            }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ListUserSelectEvent event) {
        /* Do something */
        if (event != null && event.getListData().size() > 0) {
            listUnitPersonData.addAll(event.getListData());
            List<ObjectAssignRequest> allEvents = listUnitPersonData;
            for (ObjectAssignRequest obj : allEvents) {
                boolean isFound = false;
                for (ObjectAssignRequest e : noRepeat) {
                    if (e.getNameUser().equals(obj.getNameUser()) || (e.equals(obj))) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) noRepeat.add(obj);
            }
            unitPersonCreateWorkAdapter = new UnitPersonCreateWorkAdapter(noRepeat, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
            recyclerViewUnitPerson.setLayoutManager(mLayoutManager);
            recyclerViewUnitPerson.setAdapter(unitPersonCreateWorkAdapter);
            EventBus.getDefault().removeStickyEvent(ListUserSelectEvent.class);
            EventBus.getDefault().postSticky(new ListUnitUserTaoCVEvent(noRepeat));
        }
        unitPersonCreateWorkAdapter.notifyDataSetChanged();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SelectUnitForPersonEvent event) {
        /* Do something */
        if (event != null) {
            unitUser = event.getUnitSelected().getId();
            getListPersonData(unitUser);
        }
    }

    private void showDialogUnitSelect() {
        dialogUnitClass = new DialogUnitClass(this,
                edtDateFinish.getText().toString().trim(), listUnitData, noRepeat);
        dialogUnitClass.show();
    }

    private void showDialogPicker() {
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
                edtDateFinish.setText(strDate);
                EventBus.getDefault().postSticky(new TimeTaoCongViecEvent(strDate));
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePicker.show();
    }

    private void initDataWorkLevel(List<GetListStatusComboxRespone.Data> listData) {
        final ArrayAdapter<GetListStatusComboxRespone.Data> adapter = new ArrayAdapter<GetListStatusComboxRespone.Data>
                (this, android.R.layout.simple_spinner_item, listData) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinWorkLevel.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinWorkLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                GetListStatusComboxRespone.Data dataItem = (GetListStatusComboxRespone.Data) spinWorkLevel.getSelectedItem();
                createSubTaskRequest.setPriority(String.valueOf(dataItem.getValue()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinWorkLevel.setSelection(0, true);
    }

    private void initDataSpinner(List<GetListBossSubTaskResponse.Data> listData) {
        if (listData == null) {
            listData = new ArrayList<>();
            listData.add(new GetListBossSubTaskResponse.Data("Lãnh đạo chỉ đạo", ""));
        } else {
            listData.add(0, new GetListBossSubTaskResponse.Data("Lãnh đạo chỉ đạo", ""));
        }
        final ArrayAdapter<GetListBossSubTaskResponse.Data> adapter = new ArrayAdapter<GetListBossSubTaskResponse.Data>
                (this, android.R.layout.simple_spinner_item, listData) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinAssignTask.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinAssignTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                GetListBossSubTaskResponse.Data dataItem = (GetListBossSubTaskResponse.Data)
                        spinAssignTask.getSelectedItem();
                createSubTaskRequest.setLanhDaoGiaoViec(dataItem.getUserId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinAssignTask.setSelection(0, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(ListUnitSelectEvent.class);
        EventBus.getDefault().removeStickyEvent(ListUserSelectEvent.class);
        EventBus.getDefault().removeStickyEvent(SelectUnitForPersonEvent.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().removeStickyEvent(ListUnitSelectEvent.class);
        EventBus.getDefault().removeStickyEvent(ListUserSelectEvent.class);
        EventBus.getDefault().removeStickyEvent(SelectUnitForPersonEvent.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
