package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.AttachFileThongTinDieuHanhAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.ReplyChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.ChiDaoFlow;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao.ChiDaoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao.IChiDaoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.userinfo.IUserInfoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.userinfo.UserInfoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ChiDaoFlowsEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.CloseProgressEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ResponseChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ResponsedChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IChiDaoView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IUserAvatarView;

public class DetailChiDaoActivity extends BaseActivity implements ILoginView, IChiDaoView, IUserAvatarView, IExceptionErrorView {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.img_anh_dai_dien)
    ImageView imgAnhDaiDien;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtContent)
    TextView txtContent;
    @BindView(R.id.layoutFile)
    LinearLayout layoutFile;
    @BindView(R.id.rvReplys)
    RecyclerView rvReplys;
    @BindView(R.id.btnReply)
    LinearLayout btnReply;
    @BindView(R.id.btnForward)
    LinearLayout btnForward;
    @BindView(R.id.btnReplyAll)
    LinearLayout btnReplyAll;
    @BindView(R.id.txtNguoiNhan)
    TextView txtNguoiNhan;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.btnMore)
    TextView btnMore;
    private String id;
    private String event;
    private ChiDaoFlow chiDaoFlow;
    private List<ChiDaoFlow> chiDaoFlows;
    private List<ChiDaoFlow> chiDaoFlowReplys;
    private List<FileChiDao> fileChiDaos;
    private ReplyChiDaoAdapter adapter;
    private ProgressDialog progressDialog;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private IChiDaoPresenter chiDaoPresenter = new ChiDaoPresenterImpl(this);
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private IUserInfoPresenter userInfoPresenter = new UserInfoPresenterImpl(this);
    private String fileName;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chi_dao);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("ID_CHIDAO");
        appPrefs = Application.getApp().getAppPrefs();
        getFlow();
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
    }

    private void getFlow() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_FLOW";
            chiDaoPresenter.getFlowChiDao(id);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_FILE";
            chiDaoPresenter.getFileChiDao(id);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void fillData(ChiDaoFlow chiDaoFlow) {
        txtName.setText(chiDaoFlow.getTenNguoiTao());
        txtDate.setText(chiDaoFlow.getNgayTao());
        txtTitle.setText(chiDaoFlow.getTieuDe());
        btnMore.setVisibility(View.GONE);
        if (chiDaoFlow.getNoiDung() != null && !chiDaoFlow.getNoiDung().trim().equals("")) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                txtContent.setText(Html.fromHtml(chiDaoFlow.getNoiDung()));
            } else {
                txtContent.setText(Html.fromHtml(chiDaoFlow.getNoiDung(), Html.FROM_HTML_MODE_COMPACT));
            }
            int lineCount = txtContent.getLineCount();
            if (lineCount > 5) {
                txtContent.setMaxLines(5);
                btnMore.setVisibility(View.VISIBLE);
            } else {
                btnMore.setVisibility(View.GONE);
            }
        } else {
            txtContent.setText("");
        }
        String nguoiNhan = "";
        String[] nguoiNhans = chiDaoFlow.getUserCount().split("\\|");
        if (nguoiNhans[1].equals("1")) {
            nguoiNhan += getResources().getString(R.string.str_ban);
        }
        if (!nguoiNhans[0].equals("0")) {
            if (nguoiNhans[1].equals("1")) {
                nguoiNhan += " " + getString(R.string.str_va) + " " + nguoiNhans[0] + " " + getString(R.string.str_nguoi_khac);
            } else {
                nguoiNhan += nguoiNhans[0] + " " + getString(R.string.str_nguoi_khac);
            }
        }
        txtNguoiNhan.setText(nguoiNhan);
        userInfoPresenter.getAvatar(chiDaoFlow.getUserId());
    }

    private void fillFlow() {
        EventBus.getDefault().postSticky(new ChiDaoFlowsEvent(chiDaoFlows));
        adapter = new ReplyChiDaoAdapter(this, chiDaoFlowReplys);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvReplys.setLayoutManager(mLayoutManager);
        rvReplys.setItemAnimator(new DefaultItemAnimator());
        rvReplys.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (chiDaoFlowReplys != null && chiDaoFlowReplys.size() > 0) {
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void fillFiles() {
        layoutFile.removeAllViewsInLayout();
        AttachFileThongTinDieuHanhAdapter attachFileAdapter = new AttachFileThongTinDieuHanhAdapter(this, R.layout.item_file_attach_list, fileChiDaos);
        int adapterCount = attachFileAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            if (attachFileAdapter.getItem(i).getName() != null) {
                View item = attachFileAdapter.getView(i, null, null);
                layoutFile.addView(item);
            }
        }
    }

    private TextView createView(final int id, final String filename) {
        if (filename != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this);
            textView.setText(filename);
            textView.setId(id);
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTypeface(Application.getApp().getTypeface());
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            if (filename.trim().toUpperCase().endsWith(Constants.DOC) || filename.trim().toUpperCase().endsWith(Constants.DOCX)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_doc, 0, 0, 0);
            }
            if (filename.trim().toUpperCase().endsWith(Constants.XLS) || filename.trim().toUpperCase().endsWith(Constants.XLSX)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xls, 0, 0, 0);
            }
            if (filename.trim().toUpperCase().endsWith(Constants.PDF)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pdf, 0, 0, 0);
            }
            if (filename.trim().toUpperCase().endsWith(Constants.TXT)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_txt, 0, 0, 0);
            }
            if (filename.trim().toUpperCase().endsWith(Constants.JPG)
                    || filename.trim().toUpperCase().endsWith(Constants.JPEG)
                    || filename.trim().toUpperCase().endsWith(Constants.PNG)
                    || filename.trim().toUpperCase().endsWith(Constants.GIF)
                    || filename.trim().toUpperCase().endsWith(Constants.TIFF)
                    || filename.trim().toUpperCase().endsWith(Constants.BMP)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_image, 0, 0, 0);
            }
            textView.setLayoutParams(params);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (connectionDetector.isConnectingToInternet()) {
                        if (fileChiDaos.get(id).getHdd() != null && !fileChiDaos.get(id).getHdd().trim().equals("")) {
                            event = "DOWNLOAD";
                            fileName = filename;
                            chiDaoPresenter.downloadFileChiDao(new DownloadChiDaoRequest(fileChiDaos.get(id).getHdd().trim()));
                        } else {
                            AlertDialogManager.showAlertDialog(DetailChiDaoActivity.this, getString(R.string.DOWNLOAD_TITLE_ERROR), getString(R.string.NO_URL_ERROR), true, AlertDialogManager.ERROR);
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(DetailChiDaoActivity.this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                    }
                }
            });
            return textView;
        }
        return null;
    }

    @Override
    public void onSuccess(Object object) {
        if (event.endsWith("DOWNLOAD")) {
            fillFiles();
        }
        if (event.equals("GET_FILE")) {
            fileChiDaos = ConvertUtils.fromJSONList(object, FileChiDao.class);
            if (fileChiDaos != null && fileChiDaos.size() > 0) {
                fillFiles();
            }
        }
        if (event.equals("GET_FLOW")) {
            chiDaoFlows = ConvertUtils.fromJSONList(object, ChiDaoFlow.class);
            if (chiDaoFlows != null && chiDaoFlows.size() > 0) {
                int index = 0;
                chiDaoFlowReplys = new ArrayList<>();
                for (int i = 0; i < chiDaoFlows.size(); i++) {
                    if (chiDaoFlows.get(i).getParentId() != null && chiDaoFlows.get(i).getParentId().trim().equals(id)) {
                        chiDaoFlowReplys.add(chiDaoFlows.get(i));
                    }
                    if (chiDaoFlows.get(i).getParentId() == null || chiDaoFlows.get(i).getParentId().trim().equals("")
                            || chiDaoFlows.get(i).getParentId().trim().equals("0") || chiDaoFlows.get(i).getId().trim().equals(id)) {
                        fillData(chiDaoFlows.get(i));
                        index = i;
                        chiDaoFlow = chiDaoFlows.get(i);
                    }
                }
                chiDaoFlows.remove(index);
                fillFlow();
                getFiles();
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.CHIDAO_NOT_FOUND), true, AlertDialogManager.ERROR);
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
            if (event.equals("GET_FLOW")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_FLOW_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event.equals("GET_FILE")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_FILE_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            getFlow();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(List<Object> object) {

    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            hideProgressDialog();
        }
    }

    @Override
    public void onSuccessDonViNhan(Object object) {

    }

    @Override
    public void onSuccessDanhSachDonViNhan(Object object) {

    }

    @OnClick({R.id.btnReply, R.id.btnForward, R.id.btnReplyAll, R.id.txtNguoiNhan, R.id.btnBack, R.id.btnMore})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btnReply:
                EventBus.getDefault().postSticky(new CloseProgressEvent(false));
//                intent = new Intent(this, ReplyChiDaoActivity.class);
                intent = new Intent(this, ReplyChiDaoV2Activity.class);
                intent.putExtra("parentId", id);
                intent.putExtra("typeReply", "1");
                intent.putExtra("title", "TrL: " + chiDaoFlow.getTieuDe());
                startActivity(intent);
                break;
            case R.id.btnForward:
                EventBus.getDefault().postSticky(new CloseProgressEvent(false));
                intent = new Intent(this, ForwardChiDaoV2Activity.class);
                EventBus.getDefault().postSticky(new ResponseChiDaoEvent(chiDaoFlow));
                startActivity(intent);
                break;
            case R.id.btnReplyAll:
                EventBus.getDefault().postSticky(new CloseProgressEvent(false));
//                intent = new Intent(this, ReplyChiDaoActivity.class);
                intent = new Intent(this, ReplyChiDaoV2Activity.class);
                intent.putExtra("parentId", id);
                intent.putExtra("typeReply", "2");
                intent.putExtra("title", "TrL: " + chiDaoFlow.getTieuDe());
                startActivity(intent);
                break;
            case R.id.txtNguoiNhan:
                intent = new Intent(this, ListReceiveActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnMore:
                if (txtContent.getMaxLines() == 5) {
                    txtContent.setMaxLines(100000);
                    btnMore.setText(getResources().getString(R.string.str_rutgon));
                    return;
                }
                if (txtContent.getMaxLines() == 100000) {
                    btnMore.setText(getResources().getString(R.string.str_xemthem));
                    txtContent.setMaxLines(5);
                    return;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().removeStickyEvent(ChiDaoFlowsEvent.class);
        finish();
    }

    @Override
    public void onErrorAvatar(APIError apiError) {
        sendExceptionError(apiError);
    }

    @Override
    public void onSuccessAvatar(byte[] bitmap) {
        Glide.with(this).load(bitmap).error(R.drawable.ic_avatar)
                .bitmapTransform(new CircleTransform(this)).into(imgAnhDaiDien);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onEvent(ResponsedChiDaoEvent responsedChiDaoEvent) {
        EventBus.getDefault().removeStickyEvent(ResponsedChiDaoEvent.class);
        //boolean closeProgress = EventBus.getDefault().getStickyEvent(CloseProgressEvent.class).isCloseProgress();
        //if (!closeProgress && responsedChiDaoEvent != null && responsedChiDaoEvent.isResponse()) {
        EventBus.getDefault().postSticky(new CloseProgressEvent(true));
        getFlow();
        //}
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
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }
}
