package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.AttachFileAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.LogAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.RelatedDocumentAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.DetailDocumentWaiting;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting.DocumentWaitingPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting.IDocumentWaitingPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDetailDocumentWaitingView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class CommentActivity extends BaseActivity implements IDetailDocumentWaitingView, ILoginView, IExceptionErrorView {

    @BindView(R.id.tv_trichyeu) TextView tv_trichyeu;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_ngayden) TextView tv_ngayden;
    @BindView(R.id.tv_ngayden_label) TextView tv_ngayden_label;
    @BindView(R.id.tv_kh) TextView tvKH;
    @BindView(R.id.tv_so_ki_hieu_label) TextView tvKH_label;
    @BindView(R.id.tv_cqbh) TextView tvCQBH;
    @BindView(R.id.tv_cqbh_label) TextView tvCQBH_label;
    @BindView(R.id.tv_ngayvb) TextView tvNgayVB;
    @BindView(R.id.tv_ngayvb_label) TextView tvNgayVB_label;
    @BindView(R.id.tv_hinhthucvb) TextView tv_hinhthucvb;
    @BindView(R.id.tv_hinhthucvb_label) TextView tv_hinhthucvb_label;
    @BindView(R.id.tv_sovb) TextView tv_sovb;
    @BindView(R.id.tv_sovb_label) TextView tv_sovb_label;
    @BindView(R.id.tv_soden) TextView tv_soden;
    @BindView(R.id.tv_soden_label) TextView tv_soden_label;
    @BindView(R.id.tv_do_khan) TextView tvDoKhan;
    @BindView(R.id.tv_do_khan_label) TextView tvDoKhan_label;
    @BindView(R.id.tv_hanxuly) TextView tv_hanxuly;
    @BindView(R.id.tv_hanxuly_label) TextView tv_hanxuly_label;
    @BindView(R.id.tv_sotrang) TextView tv_sotrang;
    @BindView(R.id.tv_sotrang_label) TextView tv_sotrang_label;
    @BindView(R.id.tv_hinhthucgui) TextView tv_hinhthucgui;
    @BindView(R.id.tv_hinhthucgui_label) TextView tv_hinhthucgui_label;
    @BindView(R.id.tv_filedinhkem_label) TextView tv_filedinhkem_label;
    @BindView(R.id.tv_vblienquan) TextView tv_vblienquan;
    @BindView(R.id.tv_comment_label) TextView tv_comment_label;
    @BindView(R.id.layout_file_attach) LinearLayout layout_file_attach;
    @BindView(R.id.layout_related_docs) LinearLayout layout_related_docs;
    @BindView(R.id.layout_log) LinearLayout layout_log;
    @BindView(R.id.txtComment) EditText txtComment;
    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnSend;
    private IDocumentWaitingPresenter documentWaitingPresenter = new DocumentWaitingPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    @BindView(R.id.layoutDisplay) ScrollView layoutDisplay;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setupToolbar();
        init();
        getDetail();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        setTitle(getString(R.string.SEND_COMMENT_LABEL));
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnSend = (ImageView) toolbar.findViewById(R.id.btnSend);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.isConnectingToInternet()) {
                    if (txtComment.getText() != null && !txtComment.getText().toString().trim().equals("")) {
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
                        documentWaitingPresenter.comment(new CommentDocumentRequest(detailDocumentInfo.getId(), txtComment.getText().toString().trim()));
                    } else {
                        AlertDialogManager.showAlertDialog(CommentActivity.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_COMMENT), true, AlertDialogManager.INFO);
                    }
                } else {
                    AlertDialogManager.showAlertDialog(CommentActivity.this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        });
    }

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();
        progressDialog = new ProgressDialog(this);
        connectionDetector = new ConnectionDetector(this);
        tv_trichyeu.setTypeface(Application.getApp().getTypeface());
        tvTitle.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_ngayden.setTypeface(Application.getApp().getTypeface());
        tv_ngayden_label.setTypeface(Application.getApp().getTypeface());
        tvKH.setTypeface(Application.getApp().getTypeface());
        tvCQBH.setTypeface(Application.getApp().getTypeface());
        tvNgayVB.setTypeface(Application.getApp().getTypeface());
        tvDoKhan.setTypeface(Application.getApp().getTypeface());
        tv_hinhthucvb.setTypeface(Application.getApp().getTypeface());
        tvKH_label.setTypeface(Application.getApp().getTypeface());
        tvCQBH_label.setTypeface(Application.getApp().getTypeface());
        tvNgayVB_label.setTypeface(Application.getApp().getTypeface());
        tvDoKhan_label.setTypeface(Application.getApp().getTypeface());
        tv_hinhthucvb_label.setTypeface(Application.getApp().getTypeface());
        tv_sovb.setTypeface(Application.getApp().getTypeface());
        tv_sovb_label.setTypeface(Application.getApp().getTypeface());
        tv_soden.setTypeface(Application.getApp().getTypeface());
        tv_soden_label.setTypeface(Application.getApp().getTypeface());
        tv_hanxuly.setTypeface(Application.getApp().getTypeface());
        tv_hanxuly_label.setTypeface(Application.getApp().getTypeface());
        tv_sotrang.setTypeface(Application.getApp().getTypeface());
        tv_sotrang_label.setTypeface(Application.getApp().getTypeface());
        tv_hinhthucgui.setTypeface(Application.getApp().getTypeface());
        tv_hinhthucgui_label.setTypeface(Application.getApp().getTypeface());
        tv_filedinhkem_label.setTypeface(Application.getApp().getTypeface());
        tv_vblienquan.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_comment_label.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtComment.setTypeface(Application.getApp().getTypeface());
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
    }

    private void getDetail() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getDetail(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getAttachFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getAttachFiles(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedDocs() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getRelatedDocs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getLogs() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getLogs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void fillDetail(DetailDocumentWaiting detailDocumentWaiting) {
        tvTitle.setText(detailDocumentWaiting.getTrichYeu());
        tvCQBH.setText(detailDocumentWaiting.getDonViBanHanh());
        tvKH.setText(detailDocumentWaiting.getSoKiHieu());
        tv_ngayden.setText(detailDocumentWaiting.getNgayDenDi());
        tvNgayVB.setText(detailDocumentWaiting.getNgayVanBan());
        tv_hinhthucvb.setText(detailDocumentWaiting.getHinhThucVanBan());
        tv_sovb.setText(detailDocumentWaiting.getSoVanBan());
        tv_soden.setText(String.valueOf(detailDocumentWaiting.getSoDenDi()));
        tvDoKhan.setText(detailDocumentWaiting.getDoMat());
        tv_hanxuly.setText(detailDocumentWaiting.getHanGiaiQuyet());
        tv_sotrang.setText(String.valueOf(detailDocumentWaiting.getSoTrang()));
        tv_hinhthucgui.setText(detailDocumentWaiting.getHinhThucGui());
    }

    private void fillAttachFiles(List<AttachFileInfo> attachFileInfoList) {
        AttachFileAdapter attachFileAdapter = new AttachFileAdapter(this, R.layout.item_file_attach_list, attachFileInfoList);
        int adapterCount = attachFileAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = attachFileAdapter.getView(i, null, null);
            layout_file_attach.addView(item);
        }
    }

    private void fillRelatedDocs(List<RelatedDocumentInfo> relatedDocumentInfoList) {
        RelatedDocumentAdapter relatedDocumentAdapter = new RelatedDocumentAdapter(this, R.layout.item_related_doc_list, relatedDocumentInfoList);
        int adapterCount = relatedDocumentAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = relatedDocumentAdapter.getView(i, null, null);
            layout_related_docs.addView(item);
        }
    }

    private void fillLogs(List<UnitLogInfo> logInfoList) {
        if (layout_log != null) {
            layout_log.removeAllViews();
        }
        LogAdapter logAdapter = new LogAdapter(this, R.layout.item_log_list, logInfoList);
        int adapterCount = logAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = logAdapter.getView(i, null, null);
            layout_log.addView(item);
        }
    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof DetailDocumentWaiting) {
            DetailDocumentWaiting detailDocumentWaiting = (DetailDocumentWaiting) object;
            fillDetail(detailDocumentWaiting);
            getAttachFiles();
            getRelatedDocs();
            getLogs();
        }
        if (object instanceof List) {
            List<Object> lstObj = (List) object;
            if (lstObj.get(0) instanceof AttachFileInfo) {
                List<AttachFileInfo> attachFileInfoList = (List<AttachFileInfo>) object;
                fillAttachFiles(attachFileInfoList);
            }
            if (lstObj.get(0) instanceof RelatedDocumentInfo) {
                List<RelatedDocumentInfo> relatedDocumentInfoList = (List<RelatedDocumentInfo>) object;
                fillRelatedDocs(relatedDocumentInfoList);
            }
            if (lstObj.get(0) instanceof UnitLogInfo) {
                List<UnitLogInfo> logInfoList = (List<UnitLogInfo>) object;
                fillLogs(logInfoList);
            }
        }
    }

    @Override
    public void onComment() {
        txtComment.setText("");
        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_SUCCESS), getString(R.string.COMMENT_SUCCESS), true, AlertDialogManager.SUCCESS);
        getLogs();
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
            AlertDialogManager.showAlertDialog(this, getString(R.string.COMMENT_DOC_TITLE_ERROR), getString(R.string.COMMENT_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        getDetail();
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(CommentActivity.this, LoginActivity.class));
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
    public void onMark() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCheckFinish(boolean isFinish) {

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
