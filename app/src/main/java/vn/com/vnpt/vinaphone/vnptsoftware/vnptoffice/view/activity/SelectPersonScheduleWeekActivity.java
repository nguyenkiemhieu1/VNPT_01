package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonAndUnitScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSaveDocumentResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SaveDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule.ISchedulePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule.SchedulePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.AddAndDeletePersonScheduleEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.InitEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeViewDocumentSaved;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeViewFactory;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeViewPersonSendXemDBFactory;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeViewScheduleWeek;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleWeekView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ISelectPersonScheduleWeekView;

import static android.telephony.PhoneNumberUtils.compare;

public class SelectPersonScheduleWeekActivity extends BaseActivity implements ISelectPersonScheduleWeekView, ILoginView, IExceptionErrorView {
    @BindView(R.id.btnExtend)
    TextView btnExtend;
    @BindView(R.id.btnCollapse)
    LinearLayout btnCollapse;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.tv_nodata)
    TextView tv_nodata;
    @BindView(R.id.layout_person)
    LinearLayout layout_person;
    @BindView(R.id.layoutViewPerson)
    LinearLayout layoutViewPerson;
    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnSelect;
    private TextView tvTitle;
    private ViewGroup viewGroup;
    private TreeNode root;
    private TreeView treeView;
    private String key = "";
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ISchedulePresenter schedulePresenter = new SchedulePresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private ApplicationSharedPreferences appPrefs;
    private AddAndDeletePersonScheduleEvent event;
    private Timer timer;
    private ArrayList<PersonAndUnitScheduleWeekInfo> listSelectPersonAndUnit;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person_schedule_week);
        ButterKnife.bind(this);
        Init();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();

    }

    private void Init() {
        timer = new Timer();
        connectionDetector = new ConnectionDetector(this);
        appPrefs = Application.getApp().getAppPrefs();
        toolbar = findViewById(R.id.toolbarSelectPerson);
        listSelectPersonAndUnit = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnBack = toolbar.findViewById(R.id.btnBack);
        btnSelect = toolbar.findViewById(R.id.btnSelect);
        tvTitle = toolbar.findViewById(R.id.tvTitle);
        event = EventBus.getDefault().getStickyEvent(AddAndDeletePersonScheduleEvent.class);


        if (event != null && event.getType() != null && event.getListSelectPesonAndUnit() != null) {
            if (event.getType().equals("CHAIR")) {
                tvTitle.setText("Chọn chủ trì");
            } else {
                tvTitle.setText("Chọn người tham gia");
            }
            listSelectPersonAndUnit = event.getListSelectPesonAndUnit();
        }
        getListChairOrJoin();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (event != null && event.getType() != null) {
                    if (listSelectPersonAndUnit != null && listSelectPersonAndUnit.size() > 0) {
                        event.setListSelectPesonAndUnit(listSelectPersonAndUnit);
                        EventBus.getDefault().postSticky(event);
                        finish();
                    } else {
                        AlertDialogManager.showAlertDialog(SelectPersonScheduleWeekActivity.this, getString(R.string.str_dialog_thongbao), getString(R.string.PERSON_REQUIERD), true, AlertDialogManager.ERROR);
                    }
//                    if (getSelectPersonTreeview() != null && getSelectPersonTreeview().size() > 0) {
//                        event.setListSelectPesonAndUnit(getSelectPersonTreeview());
//                        EventBus.getDefault().postSticky(event);
//                        finish();
//                    } else {
//                        AlertDialogManager.showAlertDialog(SelectPersonScheduleWeekActivity.this, getString(R.string.str_dialog_thongbao), getString(R.string.PERSON_REQUIERD), true, AlertDialogManager.ERROR);
//                    }
                }
            }
        });

        edt_name.addTextChangedListener(
                new TextWatcher() {

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

                                        if (edt_name.getText() != null && !edt_name.getText().toString().trim().equals("")) {
                                            key = edt_name.getText().toString().trim();
                                        } else {
                                            key = "";
                                        }
                                        View view = getCurrentFocus();
                                        if (view != null) {
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        }
                                        getListChairOrJoin();
                                    }
                                });
                            }
                        }, Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
    }


    private void getListChairOrJoin() {//lấy danh sách chọn người nhận
        if (connectionDetector.isConnectingToInternet()) {
            schedulePresenter.getPersonAndUnitScheduleWeek(new PersonAndUnitScheduleWeekRequest(key));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }


    }

    private void autoSelectPersonTreeView(ArrayList<PersonAndUnitScheduleWeekInfo> listSelectPersonAndUnit) {
        if (listSelectPersonAndUnit != null && listSelectPersonAndUnit != null) {
            if (listSelectPersonAndUnit.size() > 0) {
                if (treeView != null) {
                    List<TreeNode> allTree = treeView.getAllNodes();
                    if (allTree != null) {
                        for (int i = 0; i < allTree.size(); i++) {
                            for (int j = 0; j < listSelectPersonAndUnit.size(); j++) {
                                if (((PersonAndUnitScheduleWeekInfo) allTree.get(i).getValue()).getId().equals(listSelectPersonAndUnit.get(j).getId())) {
                                    allTree.get(i).setSelectedXLC(true);
                                    treeView.refreshTreeView();
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    @Override
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        hideProgress();
    }

    @Override
    public void onErrorLogin(APIError apiError) {

    }

    @Override
    public void onSuccess(List<PersonAndUnitScheduleWeekInfo> personAndUnitScheduleWeekInfos) {
        layoutViewPerson.removeAllViews();
        if (personAndUnitScheduleWeekInfos != null && personAndUnitScheduleWeekInfos.size() > 0) {
            layoutViewPerson.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
            displayTreeView(personAndUnitScheduleWeekInfos);
        } else {
            tv_nodata.setVisibility(View.VISIBLE);
            layoutViewPerson.setVisibility(View.GONE);
        }
    }

    private ArrayList<PersonAndUnitScheduleWeekInfo> getSelectPersonTreeview() {
        ArrayList<PersonAndUnitScheduleWeekInfo> personList = new ArrayList<>();
        if (treeView != null && treeView.getSelectedXLCNodes() != null) {
            List<TreeNode> list = treeView.getSelectedXLCNodes();
            for (int i = 0; i < list.size(); i++) {
                if (((PersonAndUnitScheduleWeekInfo) list.get(i).getValue()) != null) {
                    personList.add(((PersonAndUnitScheduleWeekInfo) list.get(i).getValue()));
                }
            }
        }
        return personList;

    }

    private void displayTreeView(List<PersonAndUnitScheduleWeekInfo> list) {
        List<PersonAndUnitScheduleWeekInfo> listData = new ArrayList<>();
        root = TreeNode.root();
        if (list == null) {
            return;
        }
        listData = buildUnitTree(list, null);
        if (listData != null && listData.size() > 0) {
            buildTreeData(listData, 0, root);
        }
        treeView = new TreeView(root, this, new MyNodeViewScheduleWeek(listSelectPersonAndUnit));
        View view = treeView.getView();
        treeView.selectMultilXLC(2);
        autoSelectPersonTreeView(listSelectPersonAndUnit);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layoutViewPerson.addView(view);
    }

    private List<PersonAndUnitScheduleWeekInfo> buildUnitTree(List<PersonAndUnitScheduleWeekInfo> list, final String id) {

        final List<PersonAndUnitScheduleWeekInfo> results = new ArrayList<>();
        if (list == null) {
            return null;
        }
        for (PersonAndUnitScheduleWeekInfo unit : list) {
            if (unit.getParentId() == id || (unit.getParentId() != null && unit.getParentId().equalsIgnoreCase(id))) {
                PersonAndUnitScheduleWeekInfo dicItem = new PersonAndUnitScheduleWeekInfo();
                dicItem.setId(unit.getId());
                dicItem.setName(unit.getName());
                dicItem.setParentId(unit.getParentId());
                dicItem.setChildrenList(buildUnitTree(list, unit.getId()));
                results.add(dicItem);
            }
        }
        return results;
    }

    private void buildTreeData(List<PersonAndUnitScheduleWeekInfo> listData, int level, TreeNode root) {
        if (listData == null || root == null) {
            return;
        }
        if (level > 2) {
            Log.e("buildTreeData", level + ((PersonAndUnitScheduleWeekInfo) root.getValue()).getName());
        }
        for (PersonAndUnitScheduleWeekInfo itemData : listData) {
            TreeNode treeNode = new TreeNode(itemData);
            treeNode.setLevel(level);
            if (itemData.getChildrenList() != null && itemData.getChildrenList().size() > 0) {
                int newlevel = level + 1;
                buildTreeData(itemData.getChildrenList(), newlevel, treeNode);
            }
            root.addChild(treeNode);
            if (level == 1) {
                root.setExpanded(true);
            }

        }
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
            AlertDialogManager.showAlertDialog(this, getString(R.string.str_dialog_thongbao), apiError.getMessage(), true, AlertDialogManager.ERROR);

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
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }


}