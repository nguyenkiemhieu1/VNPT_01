package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.UnitPersonCreateWorkAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.AddPersonWorkRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ObjectAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UpdateStatusJobResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.IWorkCreateSubTaskPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.WorkCreateSubTaskPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog.DialogPersonClass;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog.DialogPersonTaoCVClass;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog.DialogUnitClass;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListUnitSelectEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListUserSelectEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SelectUnitForPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UserSelectEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.WorkManageEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IAddPersonSubTaskView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;

public class AddPersonWorkActivity extends BaseActivity implements IAddPersonSubTaskView, IExceptionErrorView {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.spin_unit)
    EditText spinUnit;
    @BindView(R.id.spin_person)
    EditText spinPerson;
    @BindView(R.id.recycler_view_unit_person)
    RecyclerView recyclerViewUnitPerson;
    @BindView(R.id.btn_update)
    FancyButton btnUpdate;
    @BindView(R.id.btn_close)
    FancyButton btnClose;
    IWorkCreateSubTaskPresenter workCreateSubTaskPresenter = new WorkCreateSubTaskPresenterImpl(this);
    private ProgressDialog progressDialog;
    List<PersonSendNotifyInfo> listUnitData = new ArrayList<>();
    private DialogPersonTaoCVClass dialogPersonClass;
    private DialogUnitClass dialogUnitClass;
    private ApplicationSharedPreferences appPrefs;
    private boolean isShowDialogUnit = true;
    private String idWork = "";
    private String endDate = "";
    private UnitPersonCreateWorkAdapter unitPersonCreateWorkAdapter;
    private List<ObjectAssignRequest> listUnitPersonData = new ArrayList<>();
    AddPersonWorkRequest addPersonWorkRequest;
    String unitUser;

    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().removeStickyEvent(ListUnitSelectEvent.class);
        EventBus.getDefault().removeStickyEvent(ListUserSelectEvent.class);
        EventBus.getDefault().removeStickyEvent(SelectUnitForPersonEvent.class);
    }


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person_work);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        appPrefs = Application.getApp().getAppPrefs();
        unitUser = appPrefs.getUnitUser();
        addPersonWorkRequest = new AddPersonWorkRequest();
        tvTitle.setText("Thêm người xử lý");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        unitPersonCreateWorkAdapter = new UnitPersonCreateWorkAdapter(listUnitPersonData, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerViewUnitPerson.setNestedScrollingEnabled(false);
        recyclerViewUnitPerson.setLayoutManager(mLayoutManager);
        recyclerViewUnitPerson.setAdapter(unitPersonCreateWorkAdapter);
    }

    @OnClick({R.id.spin_unit, R.id.spin_person, R.id.btn_update, R.id.btn_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.spin_unit:
                if (listUnitData != null && listUnitData.size() > 0) {
                    showDialogUnitSelect();
                } else {
                    getListUnitData();
                }
                break;
            case R.id.spin_person:
                if (listUnitData != null && listUnitData.size() > 0) {
                    getListPersonData(unitUser);
                } else {
                    isShowDialogUnit = false;
                    getListUnitData();
                }

                break;
            case R.id.btn_update:
                callApiAddPerson();
                break;
            case R.id.btn_close:
                finish();
                break;
        }

    }

    private void callApiAddPerson() {
        boolean isXLC = false;
        if (addPersonWorkRequest.getUsers() != null && addPersonWorkRequest.getUsers().size() > 0
                || (addPersonWorkRequest.getUnits() != null && addPersonWorkRequest.getUnits().size() > 0)) {
//            if (addPersonWorkRequest.getUsers() != null && addPersonWorkRequest.getUsers().size() > 0) {
//                for (int i = 0; i < addPersonWorkRequest.getUsers().size(); i++) {
//
//                    if (addPersonWorkRequest.getUsers().get(i).getVaiTro().equalsIgnoreCase("1")) {
//                        isXLC = true;
//                        break;
//                    }
//                }
//            }
//            if (!isXLC) {
//                if (addPersonWorkRequest.getUnits() != null && addPersonWorkRequest.getUnits().size() > 0) {
//                    for (int i = 0; i < addPersonWorkRequest.getUnits().size(); i++) {
//
//                        if (addPersonWorkRequest.getUnits().get(i).getVaiTro().equalsIgnoreCase("1")) {
//                            isXLC = true;
//                            break;
//                        }
//                    }
//                }
//            }
//
//            if (isXLC) {
            workCreateSubTaskPresenter.addPersonTask(addPersonWorkRequest);
//            } else {
//                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
//                        "Phải chọn đơn vị/cá nhân xử lý chính", true, AlertDialogManager.WARNING);
//            }

        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                    "Phải chọn đơn vị/cá nhân", true, AlertDialogManager.WARNING);
        }

    }

    private void getListPersonData(String unitId) {
        workCreateSubTaskPresenter.getListPerson(unitId, 2);
    }

    private void getListUnitData() {
        workCreateSubTaskPresenter.getListUnit(2);
    }

    private void showDialogUnitSelect() {
        dialogUnitClass = new DialogUnitClass(this,
                endDate, listUnitData, listUnitPersonData);
        dialogUnitClass.show();
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
        if (listData != null && listData.size() > 0) {
            if (dialogPersonClass != null && dialogPersonClass.isShowing()) {
                dialogPersonClass.updatePersonList(listData, listUnitPersonData);
            } else {
                dialogPersonClass = new DialogPersonTaoCVClass(this, endDate
                        , listData, listUnitData, listUnitPersonData);
                dialogPersonClass.show();
            }
        }
    }

    @Override
    public void onSuccessAddPersonData(UpdateStatusJobResponse statusJobResponse) {
        Toast.makeText(this, getString(R.string.text_sucssess_add_person), Toast.LENGTH_LONG).show();
        finish();

    }

    @Override
    public void onErrorAddPersonData(APIError apiError) {
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
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.PLEASE_WAIT));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (!this.isFinishing() && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WorkManageEvent event) {
        /* Do something */
        if (event != null) {
            idWork = event.getId();
            endDate = event.getDateEnd();
            addPersonWorkRequest.setId(idWork);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SelectUnitForPersonEvent event) {
        /* Do something */
        if (event != null) {
            unitUser = event.getUnitSelected().getId();
            getListPersonData(unitUser);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ListUnitSelectEvent event) {
        /* Do something */
        if (event != null && event.getListData().size() > 0) {
            listUnitPersonData.addAll(event.getListData());
            addPersonWorkRequest.setUnits(event.getListData());
        }
        unitPersonCreateWorkAdapter.notifyDataSetChanged();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ListUserSelectEvent event) {
        /* Do something */
        if (event != null && event.getListData().size() > 0) {
            listUnitPersonData.addAll(event.getListData());
            addPersonWorkRequest.setUsers(event.getListData());
        }
        unitPersonCreateWorkAdapter.notifyDataSetChanged();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UserSelectEvent event) {
        /* Do something */
        if (event != null) {
            if (event.getTypeAction() == 1) {
                if (addPersonWorkRequest.getUnits().contains(event.getUserData())) {
                    addPersonWorkRequest.getUnits().remove(event.getUserData());
                } else if (addPersonWorkRequest.getUsers().contains(event.getUserData())) {
                    addPersonWorkRequest.getUsers().remove(event.getUserData());
                }
            } else if (event.getTypeAction() == 2) {
                if (addPersonWorkRequest.getUnits().contains(event.getUserData())) {
                    int positionUpdate = addPersonWorkRequest.getUnits().indexOf(event.getUserData());
                    addPersonWorkRequest.getUnits().get(positionUpdate).setEndDate(event.getUserData().getEndDate());
                } else if (addPersonWorkRequest.getUsers().contains(event.getUserData())) {
                    int positionUpdate = addPersonWorkRequest.getUsers().indexOf(event.getUserData());
                    addPersonWorkRequest.getUsers().get(positionUpdate).setEndDate(event.getUserData().getEndDate());
                }
            }
            if (event.getTypeAction() == 3) {
                if (event.getUserData() != null && event.getUserData() instanceof ObjectAssignRequest) {
                    ObjectAssignRequest objectAssignRequest = (ObjectAssignRequest) event.getUserData();
                    listUnitPersonData.remove(objectAssignRequest);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(ListUnitSelectEvent.class);
        EventBus.getDefault().removeStickyEvent(ListUserSelectEvent.class);
        EventBus.getDefault().removeStickyEvent(SelectUnitForPersonEvent.class);
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