package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.DetailDocumentWaiting;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeChangeDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.changedocument.ChangeDocumentPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.changedocument.IChangeDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting.DocumentWaitingPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting.IDocumentWaitingPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting.ItemClickGetTypeChangeButton;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonPreEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocWaitingtEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ResponsedChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SaveDocumentEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SaveFinishDocumentEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TaiFileNewSendEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeTuDongGiaoViecEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDetailDocumentWaitingView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IGetTypeChangeDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IHistoryDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class DetailDocumentWaitingActivity extends BaseActivity implements IDetailDocumentWaitingView, ILoginView, IGetTypeChangeDocumentView, IHistoryDocumentView, IExceptionErrorView, ItemClickGetTypeChangeButton {

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
    @BindView(R.id.layout_file_attach)
    RecyclerView layout_file_attach;
    @BindView(R.id.layout_file_related)
    LinearLayout layout_file_related;
    @BindView(R.id.layout_related_docs)
    LinearLayout layout_related_docs;
    @BindView(R.id.layout_log)
    LinearLayout layout_log;
    @BindView(R.id.layoutFileDK)
    LinearLayout layoutFileDK;
    @BindView(R.id.layoutAction)
    LinearLayout layoutAction;
    @BindView(R.id.layoutVanBan)
    LinearLayout layoutVanBan;
    @BindView(R.id.btnMove)
    Button btnMove;
    @BindView(R.id.btnMark)
    Button btnMark;
    @BindView(R.id.btn_send_comment)
    Button btn_send_comment;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.btnHistory)
    Button btnHistory;
    @BindView(R.id.btnChuyenXuLy)
    Button btnChuyenXuLy;
    @BindView(R.id.recycler_history)
    RecyclerView recyclerHistory;

    @BindView(R.id.btnSave)
    Button btnSave;

    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnSend;


    private ConnectionDetector connectionDetector;
    private boolean marked;
    private DocumentWaitingInfo newItem = null;

    private TypeChangeDocumentRequest typeChangeDocumentRequest;
    private NewHistoryAdapter adapter;
    List<UnitLogInfo> logInfoList = new ArrayList<>();
    private ApplicationSharedPreferences appPrefs;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private IChangeDocumentPresenter changeDocumentPresenter = new ChangeDocumentPresenterImpl(this);
    private IDocumentWaitingPresenter documentWaitingPresenter = new DocumentWaitingPresenterImpl(this);
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    private AttachNewFileAdapter attachFileAdapter;
    DetailDocumentWaiting detailDocumentWaiting;
    DetailDocumentInfo detailDocumentInfo;
    private Button buttonAnchor;  //buttonAnchor dùng để làm vị trí mở popup

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_document_waiting);
        ButterKnife.bind(this);
        init();
        getDetail();
        Log.d("hihihihi", "onEvent: ");
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
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnSend = (ImageView) toolbar.findViewById(R.id.btnSend);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().removeStickyEvent(TypeChangeEvent.class);
                EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
                EventBus.getDefault().removeStickyEvent(KiThanhCongEvent.class);
                startActivity(new Intent(DetailDocumentWaitingActivity.this, SendDocumentActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();
        setupToolbar();
        detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
        typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
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
        layoutVanBan.setVisibility(View.GONE);
        layoutFileDK.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);
        if (!appPrefs.getAccountLogin().isBriefDisplay()) {
            btnSave.setVisibility(View.GONE);
        }
        try {
            newItem = EventBus.getDefault().getStickyEvent(DocumentWaitingInfo.class);
            if (newItem != null) {
                layoutAction.setVisibility(View.VISIBLE);
                //ẩn hiện nút chuyển
                if (newItem.getIsChuTri() != null && newItem.getIsChuTri().equals(Constants.SEND_RULE)) {
                    btnMove.setVisibility(View.VISIBLE);
                } else {
                    btnMove.setVisibility(View.GONE);
                }

                //button đánh dấu văn bản
                if (newItem.getIsCheck() != null) {
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
                }

                // ẩn hiện nút chuyển xử lý
                if (newItem.getIsChuyenTiep() != null && newItem.getIsChuyenTiep().equals(Constants.COMMENT_RULE)) {
                    btnChuyenXuLy.setVisibility(View.VISIBLE);
                } else {
                    btnChuyenXuLy.setVisibility(View.GONE);
                }

                //ẩn hiện nút gửi ý kiến
                if (newItem.getIsComment() != null && newItem.getIsComment().equals("true")) {
                    btn_send_comment.setVisibility(View.VISIBLE);
                } else {
                    btn_send_comment.setVisibility(View.GONE);
                }

            } else {
                layoutAction.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }

        adapter = new NewHistoryAdapter(this, logInfoList, Constants.PROCESSED_HISTORY);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerHistory.setFocusable(false);
        recyclerHistory.setNestedScrollingEnabled(false);
        recyclerHistory.setLayoutManager(mLayoutManager);
        recyclerHistory.setItemAnimator(new DefaultItemAnimator());
        recyclerHistory.setAdapter(adapter);
    }

    private void checkFinish(int id) {
        documentWaitingPresenter.checkFinish(id);
    }

    private void getDetail() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getDetail(Integer.parseInt(detailDocumentInfo.getId()));
                checkFinish(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    public void getAttachFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getAttachFiles(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedDocs() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getRelatedDocs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getRelatedFiles(Integer.parseInt(detailDocumentInfo.getId()));
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

    private void getLogs() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getLogs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void fillDetail(DetailDocumentWaiting detailDocumentWaiting) {

        if (detailDocumentWaiting.getTrichYeu() != null) {
            tvTitle.setText(detailDocumentWaiting.getTrichYeu());
        }
        if (detailDocumentWaiting.getDonViBanHanh() != null && !detailDocumentWaiting.getDonViBanHanh().trim().equals("")) {
            tvCQBH.setText(detailDocumentWaiting.getDonViBanHanh());
        } else {
            tvCQBH.setVisibility(View.GONE);
            tvCQBH_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoKiHieu() != null && !detailDocumentWaiting.getSoKiHieu().trim().equals("")) {
            tvKH.setText(detailDocumentWaiting.getSoKiHieu());
        } else {
            tvKH.setVisibility(View.GONE);
            tvKH_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getNgayDenDi() != null && !detailDocumentWaiting.getNgayDenDi().trim().equals("")) {
            tv_ngayden.setText(detailDocumentWaiting.getNgayDenDi());
        } else {
            tv_ngayden.setVisibility(View.GONE);
            tv_ngayden_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getNgayVanBan() != null && !detailDocumentWaiting.getNgayVanBan().trim().equals("")) {
            tvNgayVB.setText(detailDocumentWaiting.getNgayVanBan());
        } else {
            tvNgayVB.setVisibility(View.GONE);
            tvNgayVB_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getHinhThucVanBan() != null && !detailDocumentWaiting.getHinhThucVanBan().trim().equals("")) {
            tv_hinhthucvb.setText(detailDocumentWaiting.getHinhThucVanBan());
        } else {
            tv_hinhthucvb.setVisibility(View.GONE);
            tv_hinhthucvb_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoVanBan() != null && !detailDocumentWaiting.getSoVanBan().trim().equals("")) {
            tv_sovb.setText(detailDocumentWaiting.getSoVanBan());
        } else {
            tv_sovb.setVisibility(View.GONE);
            tv_sovb_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoDenDi() > 0) {
            tv_soden.setText(String.valueOf(detailDocumentWaiting.getSoDenDi()));
        } else {
            tv_soden.setVisibility(View.GONE);
            tv_soden_label.setVisibility(View.GONE);
        }


        if (detailDocumentWaiting.getDoUuTien() != null && !detailDocumentWaiting.getDoUuTien().trim().equals("")) {
            tvDoKhan.setText(detailDocumentWaiting.getDoUuTien());
            if (detailDocumentWaiting.getDoUuTien().equals(getResources().getString(R.string.str_thuong))) {
                tvDoKhan.setTextColor(getResources().getColor(R.color.md_light_green_status));
            } else {
                tvDoKhan.setTextColor(getResources().getColor(R.color.colorTextRed));
            }
        } else {
            tvDoKhan.setVisibility(View.GONE);
            tvDoKhan_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getHanGiaiQuyet() != null && !detailDocumentWaiting.getHanGiaiQuyet().trim().equals("")) {
            tv_hanxuly.setText(detailDocumentWaiting.getHanGiaiQuyet());
        } else {
            tv_hanxuly.setVisibility(View.GONE);
            tv_hanxuly_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoTrang() > 0) {
            tv_sotrang.setText(String.valueOf(detailDocumentWaiting.getSoTrang()));
        } else {
            tv_sotrang.setVisibility(View.GONE);
            tv_sotrang_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getHinhThucGui() != null && !detailDocumentWaiting.getHinhThucGui().trim().equals("")) {
            tv_hinhthucgui.setText(detailDocumentWaiting.getHinhThucGui());
        } else {
            tv_hinhthucgui.setVisibility(View.GONE);
            tv_hinhthucgui_label.setVisibility(View.GONE);
        }

    }

    private void fillAttachFiles(List<AttachFileInfo> attachFileInfoList) {
        if (attachFileInfoList != null && attachFileInfoList.size() > 0) {
            layoutFileDK.setVisibility(View.VISIBLE);
            layout_file_attach.setVisibility(View.VISIBLE);
            if (attachFileAdapter == null) {
                attachFileAdapter = new AttachNewFileAdapter(this, R.layout.item_file_attach_list, String.valueOf(detailDocumentWaiting.getId()), attachFileInfoList, this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                layout_file_attach.setLayoutManager(mLayoutManager);
                layout_file_attach.setItemAnimator(new DefaultItemAnimator());
                layout_file_attach.setAdapter(attachFileAdapter);
            } else {
                attachFileAdapter.updateListData(attachFileInfoList);
            }
        } else {
            layout_file_attach.setVisibility(View.GONE);
            layoutFileDK.setVisibility(View.GONE);
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

    private void comment() {
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.dialog_comment, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 64);
        dialog.getWindow().setBackgroundDrawable(inset);
        dialog.setView(view);
        final EditText txtComment = (EditText) view.findViewById(R.id.txtComment);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        Button btnHuy = (Button) view.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput();
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (connectionDetector.isConnectingToInternet()) {
                    if (txtComment.getText() != null && !txtComment.getText().toString().trim().equals("")) {
                        hideSoftInput();
                        documentWaitingPresenter.comment(new CommentDocumentRequest(detailDocumentInfo.getId(), txtComment.getText().toString().trim()));
                    } else {
                        AlertDialogManager.showAlertDialog(DetailDocumentWaitingActivity.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_COMMENT), true, AlertDialogManager.INFO);
                    }
                } else {
                    AlertDialogManager.showAlertDialog(DetailDocumentWaitingActivity.this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        });
        dialog.show();
    }

    private void mark() {
        if (connectionDetector.isConnectingToInternet()) {
            documentWaitingPresenter.mark(detailDocumentWaiting.getId());
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void finishDoc(String comment) {
        if (connectionDetector.isConnectingToInternet()) {
            documentWaitingPresenter.finish(Integer.parseInt(newItem.getId()), comment);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private int idDoc;

    @Override
    public void onSuccess(Object object) {
        if (object instanceof DetailDocumentWaiting) {
            EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
            detailDocumentWaiting = (DetailDocumentWaiting) object;
            idDoc = detailDocumentWaiting.getId();
            fillDetail(detailDocumentWaiting);
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
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof RelatedFileInfo) {
                List<RelatedFileInfo> attachFileInfoList = (List<RelatedFileInfo>) object;
                fillRelatedFiles(attachFileInfoList);
            }
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof RelatedDocumentInfo) {
                List<RelatedDocumentInfo> relatedDocumentInfoList = (List<RelatedDocumentInfo>) object;
                fillRelatedDocs(relatedDocumentInfoList);
            }
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof UnitLogInfo) {
                List<UnitLogInfo> logInfoListNew = (List<UnitLogInfo>) object;
                logInfoList.addAll(logInfoListNew);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onComment() {
        Toast.makeText(this, getString(R.string.COMMENT_SUCCESS), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onSuccess(List<UnitLogInfo> unitLogInfoList) {

    }

    @Override
    public void onError(APIError apiError) {
        if (!isFinishing()) {
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
        startActivity(new Intent(DetailDocumentWaitingActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSuccessTypeChange(final List<TypeChangeDocument> typeChangeDocumentList) {
        if (typeChangeDocumentList != null && typeChangeDocumentList.size() > 0) {
            EventBus.getDefault().postSticky(typeChangeDocumentRequest);//truyền data qua newsendocumentActivity
            if (typeChangeDocumentList.size() == 1) {
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                if (listPersonEvent == null) {
                    listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
                }
                int type = typeChangeDocumentList.get(0).getType();
                if (typeChangeDocumentList.get(0).getUploadFile() != null) {
                    EventBus.getDefault().postSticky(new TaiFileNewSendEvent(typeChangeDocumentList.get(0).getUploadFile()));
                }
                if (type == Constants.TYPE_SEND_NOTIFY || type == Constants.TYPE_SEND_PROCESS) {
                    if (type == Constants.TYPE_SEND_PROCESS) {
                        EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_SEND_PERSON_PROCESS));
                        //event hien thi tu dong giao viec
                        EventBus.getDefault().postSticky(new TypeTuDongGiaoViecEvent(Constants.TYPE_SEND_PERSON_PROCESS));
                        listPersonEvent.setPersonNotifyList(null);
                        listPersonEvent.setPersonSendList(null);
                        listPersonEvent.setPersonDirectList(null);
                    }
                } else {
                    if (typeChangeDocumentList.get(0).getNextStep().equals("get_cleck_publish")
                            && (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE) ||
                            typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND))) {
                        EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_DIRECT));
                        //event hien thi tu dong giao viec
                        EventBus.getDefault().postSticky(new TypeTuDongGiaoViecEvent(Constants.TYPE_PERSON_DIRECT));

                        listPersonEvent.setPersonNotifyList(null);
                        listPersonEvent.setPersonProcessList(null);
                        listPersonEvent.setPersonSendList(null);
                    } else {
                        EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_PROCESS));
                        EventBus.getDefault().postSticky(new TypeChangeEvent("", 0, typeChangeDocumentList));
                        EventBus.getDefault().postSticky(new ListProcessRequest(typeChangeDocumentList.get(0).getNextStep(),
                                typeChangeDocumentList.get(0).getRoleId(), typeChangeDocumentList.get(0).getApprovedValue(),
                                typeChangeDocumentRequest.getDocId(), ""));
                        listPersonEvent.setPersonNotifyList(null);
                        listPersonEvent.setPersonDirectList(null);
                        listPersonEvent.setPersonLienThongList(null);
                    }
                }
                EventBus.getDefault().postSticky(listPersonEvent);
                EventBus.getDefault().postSticky(new FinishEvent(0, true));
                EventBus.getDefault().postSticky(new TypeChangeEvent("", 0, typeChangeDocumentList));
                listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(DetailDocumentWaitingActivity.this);
                ListPersonPreEvent listPersonPreEvent = applicationSharedPreferences.getPersonPre();
                listPersonPreEvent.setPersonProcessPreList(listPersonEvent.getPersonProcessList());
                listPersonPreEvent.setPersonSendPreList(listPersonEvent.getPersonSendList());
                listPersonPreEvent.setPersonNotifyPreList(listPersonEvent.getPersonNotifyList());
                Application.getApp().getAppPrefs().setPersonPre(listPersonPreEvent);
                startActivity(new Intent(DetailDocumentWaitingActivity.this, SelectPersonActivityNewConvert.class).putExtra("DOCID", detailDocumentWaiting.getId()));
                //finish();
            } else {
                List<String> labels = new ArrayList<String>();
                for (int i = 0; i < typeChangeDocumentList.size(); i++) {
                    labels.add(typeChangeDocumentList.get(i).getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.weight_table_menu, R.id.textViewTableMenuItem, labels);
                final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
                listPopupWindow.setAdapter(adapter);
                listPopupWindow.setAnchorView(buttonAnchor);
                listPopupWindow.setWidth(550);
                listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
                listPopupWindow.setHorizontalOffset(-402); // margin left
                listPopupWindow.setVerticalOffset(-20);
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                        if (listPersonEvent == null) {
                            listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
                        }
                        int type = typeChangeDocumentList.get(i).getType();
                        if (typeChangeDocumentList.get(i).getUploadFile() != null) {
                            EventBus.getDefault().postSticky(new TaiFileNewSendEvent(typeChangeDocumentList.get(i).getUploadFile()));
                        }
                        if (type == Constants.TYPE_SEND_NOTIFY || type == Constants.TYPE_SEND_PROCESS) {
                            if (type == Constants.TYPE_SEND_PROCESS) {
                                EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_SEND_PERSON_PROCESS));
                                //event hien thi tu dong giao viec
                                EventBus.getDefault().postSticky(new TypeTuDongGiaoViecEvent(Constants.TYPE_SEND_PERSON_PROCESS));
                                listPersonEvent.setPersonNotifyList(null);
                                listPersonEvent.setPersonSendList(null);
                                listPersonEvent.setPersonDirectList(null);
                            }
                        } else {
                            if (typeChangeDocumentList.get(i).getNextStep().equals("get_cleck_publish")
                                    && (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE) ||
                                    typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND))) {
                                EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_DIRECT));
                                //event hien thi tu dong giao viec
                                EventBus.getDefault().postSticky(new TypeTuDongGiaoViecEvent(Constants.TYPE_PERSON_DIRECT));
                                listPersonEvent.setPersonNotifyList(null);
                                listPersonEvent.setPersonProcessList(null);
                                listPersonEvent.setPersonSendList(null);
                            } else {
                                EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_PROCESS));
                                EventBus.getDefault().postSticky(new TypeChangeEvent("", i, typeChangeDocumentList));
                                EventBus.getDefault().postSticky(new ListProcessRequest(typeChangeDocumentList.get(i).getNextStep(),
                                        typeChangeDocumentList.get(i).getRoleId(), typeChangeDocumentList.get(i).getApprovedValue(),
                                        typeChangeDocumentRequest.getDocId(), ""));
                                listPersonEvent.setPersonNotifyList(null);
                                listPersonEvent.setPersonDirectList(null);
                                listPersonEvent.setPersonLienThongList(null);
                            }
                        }
                        EventBus.getDefault().postSticky(listPersonEvent);
                        EventBus.getDefault().postSticky(new TypeChangeEvent("", i, typeChangeDocumentList));
                        listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                        ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(DetailDocumentWaitingActivity.this);
                        ListPersonPreEvent listPersonPreEvent = applicationSharedPreferences.getPersonPre();
                        listPersonPreEvent.setPersonProcessPreList(listPersonEvent.getPersonProcessList());
                        listPersonPreEvent.setPersonSendPreList(listPersonEvent.getPersonSendList());
                        listPersonPreEvent.setPersonNotifyPreList(listPersonEvent.getPersonNotifyList());
                        Application.getApp().getAppPrefs().setPersonPre(listPersonPreEvent);
                        EventBus.getDefault().postSticky(new FinishEvent(0, true));
                        startActivity(new Intent(DetailDocumentWaitingActivity.this, SelectPersonActivityNewConvert.class).putExtra("DOCID", detailDocumentWaiting.getId()));
                        listPopupWindow.dismiss();
                    }
                });
                listPopupWindow.show();
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getResources().getString(R.string.str_khong_chuyenduoc), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorTypeChange(APIError apiError) {
        sendExceptionError(apiError);
        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), apiError.getMessage(), true, AlertDialogManager.ERROR);
    }

    @Override
    public void showProgress() {
        if (!isFinishing()) {
            showProgressDialog();
        }

    }

    @Override
    public void hideProgress() {
        if (!isFinishing()) {
            hideProgressDialog();
        }

    }

    @Override
    public void onMark() {
        if (marked) {
            marked = false;
            btnMark.setText(getResources().getString(R.string.tv_danhdau));
            Toast.makeText(this, getString(R.string.NOT_MARKED_SUCCESS), Toast.LENGTH_LONG).show();
        } else {
            marked = true;
            btnMark.setText(getResources().getString(R.string.str_huy_danhgiau));
            Toast.makeText(this, getString(R.string.MARKED_SUCCESS), Toast.LENGTH_LONG).show();
        }
        EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
    }

    @Override
    public void onFinish() {
        Toast.makeText(this, getString(R.string.FINISH_SUCCESS), Toast.LENGTH_LONG).show();
        EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
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

    @OnClick({R.id.btnMove, R.id.btnMark, R.id.btn_send_comment, R.id.btnFinish, R.id.btnHistory, R.id.btnChuyenXuLy, R.id.btnBack, R.id.btnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMove:
                ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(this);
                applicationSharedPreferences.setPersonPre(new ListPersonPreEvent(null, null, null));
                buttonAnchor = btnMove;
                changeDocumentPresenter.getTypeChangeDocument(new TypeChangeDocRequest(typeChangeDocumentRequest.getDocId(), typeChangeDocumentRequest.getProcessDefinitionId()));
                break;
            case R.id.btnMark:
                mark();
                break;
            case R.id.btn_send_comment:
                if (newItem != null) {
                    if (newItem.getIsComment().equals("true")) {
                        comment();
                    }
                }

                break;
            case R.id.btnFinish:
                if (Application.getApp().getAppPrefs().getAccountLogin().isCommentFinish()) {
                    DialogComentFinish dialogCommentFinish = new DialogComentFinish(this);
                    dialogCommentFinish.show();

                } else finishDoc(null);
                // }else finishDoc(null);
                break;
            case R.id.btnHistory:
                EventBus.getDefault().postSticky(new DetailDocumentInfo(String.valueOf(detailDocumentWaiting.getId()), Constants.DOCUMENT_WAITING, null));
                startActivity(new Intent(this, NewHistoryDocumentActivity.class));
                break;
            case R.id.btnChuyenXuLy:
                send();
                break;
            case R.id.btnSave:
                EventBus.getDefault().postSticky(new SaveDocumentEvent(idDoc));
                startActivity(new Intent(this, SaveDocumentActivity.class));
                break;
        }
    }

    private void send() {
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent == null) {
            listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
        }
        EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_SEND_PERSON_PROCESS));
        listPersonEvent.setPersonNotifyList(null);
        listPersonEvent.setPersonSendList(null);
        listPersonEvent.setPersonDirectList(null);
        EventBus.getDefault().postSticky(typeChangeDocumentRequest);
        EventBus.getDefault().postSticky(new FinishEvent(1, false));
        EventBus.getDefault().postSticky(listPersonEvent);
        EventBus.getDefault().postSticky(new TypeChangeEvent("", 0, null));
        startActivity(new Intent(DetailDocumentWaitingActivity.this, SelectPersonActivityNewConvert.class).putExtra("DOCID", detailDocumentWaiting.getId()));
        //finish();
    }

    public void hideSoftInput() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        EventBus.getDefault().removeStickyEvent(KiThanhCongEvent.class);
        EventBus.getDefault().removeStickyEvent(FeedBackEvent.class);
        EventBus.getDefault().removeStickyEvent(FinishEvent.class);
        EventBus.getDefault().removeStickyEvent(SaveFinishDocumentEvent.class);

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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FeedBackEvent event) {
        if (event.getFeedBack() != null)
            finishDoc(event.getFeedBack());
        EventBus.getDefault().removeStickyEvent(FeedBackEvent.class);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FinishEvent finishEvent) {
        if (finishEvent != null && finishEvent.getType() == 0 && finishEvent.isFinish()) {
            EventBus.getDefault().removeStickyEvent(FinishEvent.class);
            finish();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(KiThanhCongEvent kiThanhCongEvent) {
        if (kiThanhCongEvent != null && kiThanhCongEvent.getKyThanhCong() == 1) {
            getAttachFiles();
        }
        EventBus.getDefault().removeStickyEvent(KiThanhCongEvent.class);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SaveFinishDocumentEvent finishEvent) {
        if (finishEvent != null && finishEvent.isFinish()) {
            Toast.makeText(this, getString(R.string.FINISH_SUCCESS), Toast.LENGTH_LONG).show();
            EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
            finish();
        }
    }

    @Override
    public void ItemClickGetTypeChangeButtonDocument(Button button) {
        buttonAnchor = button;
        changeDocumentPresenter.getTypeChangeDocumentViewFiles(new TypeChangeDocRequest(typeChangeDocumentRequest.getDocId(), typeChangeDocumentRequest.getProcessDefinitionId()));

    }
}
