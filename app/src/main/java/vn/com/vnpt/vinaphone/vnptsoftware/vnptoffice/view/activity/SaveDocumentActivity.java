package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSavedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSaveDocumentResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SaveDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.savedocument.ISaveDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.savedocument.SaveDocumentPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog.DialogComentFinish;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FeedBackEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SaveDocumentEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SaveFinishDocumentEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeViewDocumentSaved;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ISaveDocumentView;

public class SaveDocumentActivity extends BaseActivity implements ISaveDocumentView, ILoginView, IExceptionErrorView {

    public static final String TAG = "SaveDocumentActivity";

    String event;
    private String id;
    private int docId;
    private ProgressDialog progressDialog;
    TreeNode root;
    TreeView treeView;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_save_finish)
    Button btnSaveFinish;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.layout_document)
    LinearLayout viewGround;
    @BindView(R.id.layoutAction)
    LinearLayout layoutAction;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;


    private Toolbar toolbar;
    private ImageView btnBack;

    private ISaveDocumentPresenter saveDocumentPresenter = new SaveDocumentPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ConnectionDetector connectionDetector;

    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    @OnClick({R.id.btn_save, R.id.btn_save_finish, R.id.btn_cancel})
    public void finishDocumentSave(View view) {
        if (connectionDetector.isConnectingToInternet()) {
            switch (view.getId()) {
                case R.id.btn_save:
                    saveDocument(0);
                    break;
                case R.id.btn_save_finish:
                    saveDocument(1);
                    break;
                case R.id.btn_cancel:
                    onBackPressed();
                    break;
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void saveDocument(int isFinish) {
        if (isFinish == 0) {
            event = "SAVE_EVENT";
        } else event = "FINISH_EVENT";
        if (connectionDetector.isConnectingToInternet()) {
            if (docId != 0) {
                if ((id = getIdSelectDocument()) != null) {
                    if (isFinish == 0) {
                        saveDocumentPresenter.onFinishDocumentSaved(new DocumentSavedRequest(id, docId, isFinish, null));
                    } else {
                        if (Application.getApp().getAppPrefs().getAccountLogin().isCommentFinish()) {
                            DialogComentFinish dialogComentFinish = new DialogComentFinish(this);
                            dialogComentFinish.show();
                        } else
                            saveDocumentPresenter.onFinishDocumentSaved(new DocumentSavedRequest(id, docId, isFinish, null));

                    }
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_SAVE), true, AlertDialogManager.INFO);
                }
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.str_ERROR_DIALOG), true, AlertDialogManager.INFO);
            }

        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarSaveDocument);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private String getIdSelectDocument() {
        if (treeView != null && treeView.getSelectedXLCNodes() != null) {
            List<TreeNode> list = treeView.getSelectedXLCNodes();
            if (list != null && list.size() == 1)
                if (list.get(0).getValue() instanceof SaveDocumentRespone) {
                    SaveDocumentRespone saveDocumentRespone = (SaveDocumentRespone) list.get(0).getValue();
                    return saveDocumentRespone.getId();
                }
        }
        return null;

    }

    public void init() {
        appPrefs = Application.getApp().getAppPrefs();
        connectionDetector = new ConnectionDetector(this);
        root = TreeNode.root();
        setupToolbar();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_document);
        ButterKnife.bind(this);
        init();
        getListDocumentSaved();


    }

    public void getListDocumentSaved() {
        if (connectionDetector.isConnectingToInternet()) {
            saveDocumentPresenter.onGetListDocumentSaved();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(SaveDocumentEvent.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSuccess(Object listDocumentSaveD) {
        hideProgress();
        if (listDocumentSaveD != null) {
            if (listDocumentSaveD instanceof GetListSaveDocumentResponse) {
                GetListSaveDocumentResponse getListSaveDocumentResponse = (GetListSaveDocumentResponse) listDocumentSaveD;
                List<SaveDocumentRespone> list = ConvertUtils.fromJSONList(getListSaveDocumentResponse.getList(), SaveDocumentRespone.class);
                if (list != null) {
                    tvNodata.setVisibility(View.GONE);
                    viewGround.setVisibility(View.VISIBLE);
                    displayTreeView(list);
                } else {
                    tvNodata.setVisibility(View.VISIBLE);
                    viewGround.setVisibility(View.GONE);
                }
            }
        }
        if (listDocumentSaveD == null) {
            Toast.makeText(this, R.string.text_sucssess_update_status_work, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccessPost() {
        if (event.equalsIgnoreCase("FINISH_EVENT")) {
            EventBus.getDefault().postSticky(new SaveFinishDocumentEvent(true));
            finish();
        } else {
            Toast.makeText(this, R.string.text_sucssess_save_document, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SaveDocumentEvent saveDocumentEvent) {
        if (saveDocumentEvent != null) {
            docId = saveDocumentEvent.getIdDoc();
            Log.d(TAG, "onEvent: " + docId);
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
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), apiError.getMessage() != null && !apiError.getMessage().trim().equals("") ? apiError.getMessage().trim() : "Có lỗi xảy ra!\nVui lòng thử lại sau!", true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        if (connectionDetector.isConnectingToInternet()) {
            Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
            getListDocumentSaved();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(SaveDocumentActivity.this, LoginActivity.class));
        finish();
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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {


    }

    private void displayTreeView(List<SaveDocumentRespone> list) {
        List<SaveDocumentRespone> listData = new ArrayList<>();

        if (list == null) {
            return;
        }
        listData = buildUnitTree(list, null);
        if (listData != null && listData.size() > 0) {
            buildTreeData(listData, 0, root);
        }
        treeView = new TreeView(root, this, new MyNodeViewDocumentSaved());

        View view = treeView.getView();
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGround.addView(view);
        // updateDataSelectNormalTreeView(treeViewLienThong, 2);
        hideProgress();
    }

    private List<SaveDocumentRespone> buildUnitTree(List<SaveDocumentRespone> list, final String id) {

        final List<SaveDocumentRespone> results = new ArrayList<>();
        if (list == null) {
            return null;
        }
        for (SaveDocumentRespone unit : list) {
            if (!unit.isTrace() && (unit.getParentId() == id || (unit.getParentId() != null &&
                    unit.getParentId().equalsIgnoreCase(id)))) {
                unit.setTrace(true);
                SaveDocumentRespone dicItem = new SaveDocumentRespone();
                dicItem.setId(unit.getId());
                dicItem.setName(unit.getName());
                dicItem.setParentId(unit.getParentId());
                dicItem.setChildrenList(buildUnitTree(list, unit.getId()));
                results.add(dicItem);
            }
        }
        return results;
    }

    private void buildTreeData(List<SaveDocumentRespone> listData, int level, TreeNode root) {
        if (listData == null || root == null) {
            return;
        }
        if (level > 2) {
            Log.e("buildTreeData", level + ((PersonSendNotifyInfo) root.getValue()).getName());
        }
        for (SaveDocumentRespone itemData : listData) {
            TreeNode treeNode = new TreeNode(itemData);
            treeNode.setLevel(level);
            if (itemData.getChildrenList() != null && itemData.getChildrenList().size() > 0) {
                int newlevel = level + 1;
                buildTreeData(itemData.getChildrenList(), newlevel, treeNode);
            }
            root.addChild(treeNode);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FeedBackEvent event) {
        if (event.getFeedBack() != null)
            saveDocumentPresenter.onFinishDocumentSaved(new DocumentSavedRequest(id, docId, 1, event.getFeedBack()));
        EventBus.getDefault().removeStickyEvent(FeedBackEvent.class);
    }


}
