package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.AttachNewFileAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.LogAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.NewHistoryAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.RelatedDocumentAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.RelatedFileAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.DetailDocumentNotification;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentAdvanceSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentMarkInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentNotificationInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentnotification.DocumentNotificationPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentnotification.IDocumentNotificationPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog.DialogComentFinish;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FeedBackEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FinishEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KiThanhCongEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocAdvanceSearchEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocMarkEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocNotificationEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocSearchEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocWaitingtEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ResponsedChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SaveDocumentEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDetailDocumentNotificationView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class DetailDocumentNotificationActivity extends BaseActivity implements IDetailDocumentNotificationView, ILoginView, IExceptionErrorView {

    @BindView(R.id.tv_trichyeu)
    TextView tv_trichyeu;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_ngayden)
    TextView tv_ngayden;
    @BindView(R.id.tv_ngayden_label)
    TextView tv_ngayden_label;
    @BindView(R.id.tv_kh)
    TextView tvKH;
    @BindView(R.id.tv_so_ki_hieu_label)
    TextView tvKH_label;
    @BindView(R.id.tv_cqbh)
    TextView tvCQBH;
    @BindView(R.id.tv_cqbh_label)
    TextView tvCQBH_label;
    @BindView(R.id.tv_ngayvb)
    TextView tvNgayVB;
    @BindView(R.id.tv_ngayvb_label)
    TextView tvNgayVB_label;
    @BindView(R.id.tv_hinhthucvb)
    TextView tv_hinhthucvb;
    @BindView(R.id.tv_hinhthucvb_label)
    TextView tv_hinhthucvb_label;
    @BindView(R.id.tv_sovb)
    TextView tv_sovb;
    @BindView(R.id.tv_sovb_label)
    TextView tv_sovb_label;
    @BindView(R.id.tv_soden)
    TextView tv_soden;
    @BindView(R.id.tv_soden_label)
    TextView tv_soden_label;
    @BindView(R.id.tv_do_khan)
    TextView tvDoKhan;
    @BindView(R.id.tv_do_khan_label)
    TextView tvDoKhan_label;
    @BindView(R.id.tv_hanxuly)
    TextView tv_hanxuly;
    @BindView(R.id.tv_hanxuly_label)
    TextView tv_hanxuly_label;
    @BindView(R.id.tv_sotrang)
    TextView tv_sotrang;
    @BindView(R.id.tv_sotrang_label)
    TextView tv_sotrang_label;
    @BindView(R.id.tv_hinhthucgui)
    TextView tv_hinhthucgui;
    @BindView(R.id.tv_hinhthucgui_label)
    TextView tv_hinhthucgui_label;
    @BindView(R.id.tv_filedinhkem_label)
    TextView tv_filedinhkem_label;
    @BindView(R.id.tv_vblienquan)
    TextView tv_vblienquan;
    @BindView(R.id.tv_comment_label)
    TextView tv_comment_label;
    @BindView(R.id.layout_file_attach)
    RecyclerView layout_file_attach;
    @BindView(R.id.layout_related_docs)
    LinearLayout layout_related_docs;
    @BindView(R.id.layout_log)
    LinearLayout layout_log;
    @BindView(R.id.layout_file_related)
    LinearLayout layout_file_related;
    @BindView(R.id.layoutFileDK)
    LinearLayout layoutFileDK;
    @BindView(R.id.layoutVanBan)
    LinearLayout layoutVanBan;
    @BindView(R.id.btnMark)
    Button btnMark;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.btnHistory)
    Button btnHistory;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.recycler_history)
    RecyclerView recyclerHistory;
    @BindView(R.id.btnSave)
    Button btnSave;
    private Toolbar toolbar;
    private ImageView btnBack;
    private IDocumentNotificationPresenter documentNotificationPresenter = new DocumentNotificationPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean marked;
    private int id;
    private int type;
    private NewHistoryAdapter adapter;
    DetailDocumentNotification detailDocumentNotification;
    List<UnitLogInfo> logInfoList = new ArrayList<>();
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private boolean IsRead = false;

    private TypeChangeDocumentRequest typeChangeDocumentRequest;

    private int idDoc;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_document_notification);
        ButterKnife.bind(this);
        init();
        getDetail();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
        setTitle(getString(R.string.DETAIL_DOCUMENT_LABEL));
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (IsRead) {
            EventBus.getDefault().postSticky(new ReloadDocNotificationEvent(true));
        }
        switch (type) {
            case 0:
                EventBus.getDefault().removeStickyEvent(DocumentNotificationInfo.class);
                EventBus.getDefault().removeStickyEvent(DetailDocumentInfo.class);
                EventBus.getDefault().removeStickyEvent(TypeChangeDocumentRequest.class);
                break;
            case 1:
                EventBus.getDefault().removeStickyEvent(DocumentMarkInfo.class);
                EventBus.getDefault().removeStickyEvent(DetailDocumentInfo.class);
                EventBus.getDefault().removeStickyEvent(TypeChangeDocumentRequest.class);
                break;
            case 2:
                EventBus.getDefault().removeStickyEvent(DocumentSearchInfo.class);
                EventBus.getDefault().removeStickyEvent(DetailDocumentInfo.class);
                EventBus.getDefault().removeStickyEvent(TypeChangeDocumentRequest.class);
                break;
            case 3:
                EventBus.getDefault().removeStickyEvent(DocumentAdvanceSearchInfo.class);
                EventBus.getDefault().removeStickyEvent(DetailDocumentInfo.class);
                EventBus.getDefault().removeStickyEvent(TypeChangeDocumentRequest.class);
                break;
        }
        finish();
    }

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();
        setupToolbar();
        if (!appPrefs.getAccountLogin().isBriefDisplay())
        {
            btnSave.setVisibility(View.GONE);
        }
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
        layoutVanBan.setVisibility(View.GONE);
        layoutFileDK.setVisibility(View.GONE);
        DocumentNotificationInfo newItem = EventBus.getDefault().getStickyEvent(DocumentNotificationInfo.class);
        typeChangeDocumentRequest=EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
        if (newItem != null) {
            type = 0;
            id = Integer.parseInt(newItem.getId());
            btnMark.setVisibility(View.VISIBLE);
            if (!newItem.getIsCheck().equals(Constants.NOT_MARKED)) {
                marked = true;
                btnMark.setText(getResources().getString(R.string.str_huy_danhgiau));
            } else {
                marked = false;
                btnMark.setText(getResources().getString(R.string.str_danhgiau));
            }
        } else {
            btnMark.setVisibility(View.GONE);
            btnSend.setVisibility(View.GONE);
            btnFinish.setVisibility(View.GONE);
            DocumentMarkInfo newItem1 = EventBus.getDefault().getStickyEvent(DocumentMarkInfo.class);
            if (newItem1 != null) {
                type = 1;
                id = Integer.parseInt(newItem1.getId());
                btnMark.setVisibility(View.VISIBLE);
                if (!newItem1.getIsCheck().equals(Constants.NOT_MARKED)) {
                    marked = true;
                    btnMark.setText(getResources().getString(R.string.str_huy_danhgiau));
                } else {
                    marked = false;
                    btnMark.setText(getResources().getString(R.string.str_danhgiau));
                }
            } else {
                btnMark.setVisibility(View.GONE);
                btnHistory.setVisibility(View.GONE);
                DocumentSearchInfo newItem2 = EventBus.getDefault().getStickyEvent(DocumentSearchInfo.class);
                if (newItem2 != null) {
                    type = 2;
                    id = Integer.parseInt(newItem2.getId());
                    btnMark.setVisibility(View.VISIBLE);
                    if (!newItem2.getIsCheck().equals(Constants.NOT_MARKED)) {
                        marked = true;
                        btnMark.setText(getResources().getString(R.string.str_huy_danhgiau));
                    } else {
                        marked = false;
                        btnMark.setText(getResources().getString(R.string.str_danhgiau));
                    }
                } else {
                    btnMark.setVisibility(View.GONE);
                    DocumentAdvanceSearchInfo newItem3 = EventBus.getDefault().getStickyEvent(DocumentAdvanceSearchInfo.class);
                    if (newItem3 != null) {
                        type = 3;
                        id = Integer.parseInt(newItem3.getId());
                        btnMark.setVisibility(View.GONE);
//                        if (!newItem3.getIsCheck().equals(Constants.NOT_MARKED)) {
//                            marked = true;
//                            btnMark.setText("Hủy đánh dấu");
//                        } else {
//                            marked = false;
//                            btnMark.setText("Đánh dấu");
//                        }
                    } else {
                        btnMark.setVisibility(View.GONE);
                    }
                }
            }

        }
        adapter = new NewHistoryAdapter(this, logInfoList, Constants.PROCESSED_HISTORY);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerHistory.setFocusable(false);
        recyclerHistory.setNestedScrollingEnabled(false);
        recyclerHistory.setLayoutManager(mLayoutManager);
        recyclerHistory.setItemAnimator(new DefaultItemAnimator());
        recyclerHistory.setAdapter(adapter);
    }

    private void getDetail() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getDetail(Integer.parseInt(detailDocumentInfo.getId()));
                checkFinish(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getAttachFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getAttachFiles(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedDocs() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getRelatedDocs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getLogs() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getLogs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void checkFinish(int id) {
        documentNotificationPresenter.checkFinish(id);
    }

    private void getRelatedFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getRelatedFiles(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void fillRelatedFiles(List<RelatedFileInfo> relatedFileInfos) {
        RelatedFileAdapter relatedDocumentAdapter = new RelatedFileAdapter(this, R.layout.item_file_related_list, relatedFileInfos);
        int adapterCount = relatedDocumentAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = relatedDocumentAdapter.getView(i, null, null);
            layout_file_related.addView(item);
        }
    }

    private void fillDetail(DetailDocumentNotification detailDocumentNotification) {
        tvTitle.setText(detailDocumentNotification.getTrichYeu());
        if (detailDocumentNotification.getDonViBanHanh() != null && !detailDocumentNotification.getDonViBanHanh().trim().equals("")) {
            tvCQBH.setText(detailDocumentNotification.getDonViBanHanh());
        } else {
            tvCQBH.setVisibility(View.GONE);
            tvCQBH_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getSoKiHieu() != null && !detailDocumentNotification.getSoKiHieu().trim().equals("")) {
            tvKH.setText(detailDocumentNotification.getSoKiHieu());
        } else {
            tvKH.setVisibility(View.GONE);
            tvKH_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getNgayDenDi() != null && !detailDocumentNotification.getNgayDenDi().trim().equals("")) {
            tv_ngayden.setText(detailDocumentNotification.getNgayDenDi());
        } else {
            tv_ngayden.setVisibility(View.GONE);
            tv_ngayden_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getNgayVanBan() != null && !detailDocumentNotification.getNgayVanBan().trim().equals("")) {
            tvNgayVB.setText(detailDocumentNotification.getNgayVanBan());
        } else {
            tvNgayVB.setVisibility(View.GONE);
            tvNgayVB_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getHinhThucVanBan() != null && !detailDocumentNotification.getHinhThucVanBan().trim().equals("")) {
            tv_hinhthucvb.setText(detailDocumentNotification.getHinhThucVanBan());
        } else {
            tv_hinhthucvb.setVisibility(View.GONE);
            tv_hinhthucvb_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getSoVanBan() != null && !detailDocumentNotification.getSoVanBan().trim().equals("")) {
            tv_sovb.setText(detailDocumentNotification.getSoVanBan());
        } else {
            tv_sovb.setVisibility(View.GONE);
            tv_sovb_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getSoDenDi() > 0) {
            tv_soden.setText(String.valueOf(detailDocumentNotification.getSoDenDi()));
        } else {
            tv_soden.setVisibility(View.GONE);
            tv_soden_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getDoUuTien() != null && !detailDocumentNotification.getDoUuTien().trim().equals("")) {
            tvDoKhan.setText(detailDocumentNotification.getDoUuTien());
        } else {
            tvDoKhan.setVisibility(View.GONE);
            tvDoKhan_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getHanGiaiQuyet() != null && !detailDocumentNotification.getHanGiaiQuyet().trim().equals("")) {
            tv_hanxuly.setText(detailDocumentNotification.getHanGiaiQuyet());
        } else {
            tv_hanxuly.setVisibility(View.GONE);
            tv_hanxuly_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getSoTrang() > 0) {
            tv_sotrang.setText(String.valueOf(detailDocumentNotification.getSoTrang()));
        } else {
            tv_sotrang.setVisibility(View.GONE);
            tv_sotrang_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getHinhThucGui() != null && !detailDocumentNotification.getHinhThucGui().trim().equals("")) {
            tv_hinhthucgui.setText(detailDocumentNotification.getHinhThucGui());
        } else {
            tv_hinhthucgui.setVisibility(View.GONE);
            tv_hinhthucgui_label.setVisibility(View.GONE);
        }
    }

    AttachNewFileAdapter attachFileAdapter;

    private void fillAttachFiles(List<AttachFileInfo> attachFileInfoList) {
        if (attachFileInfoList != null && attachFileInfoList.size() > 0) {
            layoutFileDK.setVisibility(View.VISIBLE);
            layout_file_attach.setVisibility(View.VISIBLE);
            if (attachFileAdapter == null) {
                attachFileAdapter = new AttachNewFileAdapter(this, R.layout.item_file_attach_list, String.valueOf(detailDocumentNotification.getId()), attachFileInfoList,null);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                layout_file_attach.setLayoutManager(mLayoutManager);
                layout_file_attach.setItemAnimator(new DefaultItemAnimator());
                layout_file_attach.setAdapter(attachFileAdapter);

            } else {
                attachFileAdapter.updateListData(attachFileInfoList);
            }
        } else {
            layoutFileDK.setVisibility(View.GONE);
            layout_file_attach.setVisibility(View.GONE);
        }
    }

    private void fillRelatedDocs(List<RelatedDocumentInfo> relatedDocumentInfoList) {
        if (relatedDocumentInfoList != null && relatedDocumentInfoList.size() > 0) {
            layoutVanBan.setVisibility(View.VISIBLE);
            RelatedDocumentAdapter relatedDocumentAdapter = new RelatedDocumentAdapter(this, R.layout.item_related_doc_list, relatedDocumentInfoList);
            int adapterCount = relatedDocumentAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = relatedDocumentAdapter.getView(i, null, null);
                layout_related_docs.addView(item);
            }
        } else {
            layoutVanBan.setVisibility(View.GONE);
        }
    }

    private void fillLogs(List<UnitLogInfo> logInfoList) {
        LogAdapter logAdapter = new LogAdapter(this, R.layout.item_log_list, logInfoList);
        int adapterCount = logAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = logAdapter.getView(i, null, null);
            layout_log.addView(item);
        }
    }

    @Override
    public void onSuccess(Object object) {
        IsRead = true;//đã đọc văn bản=> reload lại danh sách văn bản khi back quay lại
        EventBus.getDefault().postSticky(new ReloadDocNotificationEvent(true));
        if (object instanceof DetailDocumentNotification) {
            EventBus.getDefault().postSticky(new ResponsedChiDaoEvent(true));
            detailDocumentNotification = (DetailDocumentNotification) object;
            idDoc = detailDocumentNotification.getId();

            fillDetail(detailDocumentNotification);
            getAttachFiles();
            getRelatedDocs();
            getRelatedFiles();
            getLogs();
        }
        if (object instanceof List) {
            List<Object> lstObj = (List) object;
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof AttachFileInfo) {
                List<AttachFileInfo> attachFileInfoList = (List<AttachFileInfo>) object;
                fillAttachFiles(attachFileInfoList);
            }
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof RelatedDocumentInfo) {
                List<RelatedDocumentInfo> relatedDocumentInfoList = (List<RelatedDocumentInfo>) object;
                fillRelatedDocs(relatedDocumentInfoList);
            }
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof RelatedFileInfo) {
                List<RelatedFileInfo> attachFileInfoList = (List<RelatedFileInfo>) object;
                fillRelatedFiles(attachFileInfoList);
            }
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof UnitLogInfo) {
                List<UnitLogInfo> logInfoListNew = (List<UnitLogInfo>) object;
                logInfoList.addAll(logInfoListNew);
                adapter.notifyDataSetChanged();
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
            AlertDialogManager.showAlertDialog(this, getString(R.string.GET_INFO_TITLE_ERROR), apiError.getMessage(), true, AlertDialogManager.ERROR);

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
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        getDetail();
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(DetailDocumentNotificationActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onMark() {
        if (marked) {
            marked = false;
            btnMark.setText(getResources().getString(R.string.str_danhgiau));
            Toast.makeText(this, getString(R.string.NOT_MARKED_SUCCESS), Toast.LENGTH_LONG).show();
        } else {
            marked = true;
            btnMark.setText(getResources().getString(R.string.str_huy_danhgiau));
            Toast.makeText(this, getString(R.string.MARKED_SUCCESS), Toast.LENGTH_LONG).show();
        }
        switch (type) {
            case 0:
                EventBus.getDefault().postSticky(new ReloadDocNotificationEvent(true));
                break;
            case 1:
                EventBus.getDefault().postSticky(new ReloadDocMarkEvent(true));
                break;
            case 2:
                EventBus.getDefault().postSticky(new ReloadDocSearchEvent(true));
                break;
            case 3:
                EventBus.getDefault().postSticky(new ReloadDocAdvanceSearchEvent(true));
                break;
        }
    }

    @Override
    public void onFinish() {
        Toast.makeText(this, getString(R.string.FINISH_SUCCESS), Toast.LENGTH_LONG).show();
        EventBus.getDefault().postSticky(new ReloadDocNotificationEvent(true));
        finish();
    }

    @Override
    public void onCheckFinish(boolean isFinish) {
        if (isFinish) {
            btnFinish.setVisibility(View.VISIBLE);
        } else {
            btnFinish.setVisibility(View.GONE);
        }

    }

    private void mark() {
        if (connectionDetector.isConnectingToInternet()) {
            documentNotificationPresenter.mark(id);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void send() {
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent == null) {
            listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
        }
        EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_SEND_VIEW));
        listPersonEvent.setPersonProcessList(null);
        listPersonEvent.setPersonSendList(null);
        listPersonEvent.setPersonDirectList(null);
        EventBus.getDefault().postSticky(listPersonEvent);
        EventBus.getDefault().postSticky(new FinishEvent(2, true));
        EventBus.getDefault().postSticky(new TypeChangeEvent("", 0, null));
        EventBus.getDefault().postSticky(typeChangeDocumentRequest);
        startActivity(new Intent(DetailDocumentNotificationActivity.this, SelectPersonActivityNewConvert.class).putExtra("DOCID", detailDocumentNotification.getId()));
        //finish();
    }

    private void finishDoc(String comment) {
        if (connectionDetector.isConnectingToInternet()) {
            documentNotificationPresenter.finish(detailDocumentNotification.getId(), comment);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @OnClick({R.id.btnMark, R.id.btnSend, R.id.btnFinish,R.id.btnHistory,R.id.btnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMark:
                mark();
                break;
            case R.id.btnSend:
                send();
                break;
            case R.id.btnFinish:
                if (Application.getApp().getAppPrefs().getAccountLogin().isCommentFinish()) {
                    DialogComentFinish dialogCommentFinish = new DialogComentFinish(this);
                    dialogCommentFinish.show();
                } else finishDoc(null);
                break;
            case R.id.btnHistory:
                switch (type) {
                    case 0:
                        EventBus.getDefault().postSticky(new DetailDocumentInfo(String.valueOf(id), Constants.DOCUMENT_NOTIFICATION, null));
                        break;
                    case 1:
                        EventBus.getDefault().postSticky(new DetailDocumentInfo(String.valueOf(id), Constants.DOCUMENT_MARK, null));
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                startActivity(new Intent(this, NewHistoryDocumentActivity.class));
                break;
            case R.id.btnSave:
                EventBus.getDefault().postSticky(new SaveDocumentEvent(idDoc));
                startActivity(new Intent(this, SaveDocumentActivity.class));
                break;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FinishEvent finishEvent) {
        if (finishEvent != null && finishEvent.getType() == 2 && finishEvent.isFinish()) {
            EventBus.getDefault().removeStickyEvent(FinishEvent.class);
            finish();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(KiThanhCongEvent kiThanhCongEvent) {
        if (kiThanhCongEvent != null && kiThanhCongEvent.getKyThanhCong() == 1) {

            getAttachFiles();
            EventBus.getDefault().removeStickyEvent(KiThanhCongEvent.class);
        }
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FeedBackEvent event) {
        if (event.getFeedBack() != null)
            finishDoc(event.getFeedBack());
        EventBus.getDefault().removeStickyEvent(FeedBackEvent.class);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(KiThanhCongEvent.class);
        EventBus.getDefault().removeStickyEvent(FinishEvent.class);
        EventBus.getDefault().removeStickyEvent(FeedBackEvent.class);
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
