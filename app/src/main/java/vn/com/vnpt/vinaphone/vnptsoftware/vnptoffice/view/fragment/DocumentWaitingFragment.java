package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.DocumentWaitingAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.FinishDocumentSameRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListButtonSendSameRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ListButtonSendSameInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeChangeDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting.DocumentWaitingPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting.IDocumentWaitingPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.BaseActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailDocumentWaitingActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.SelectPersonActivityNewConvert;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FinishEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.InitEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KhoVanBanEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonPreEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocWaitingtEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ResponsedChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TaiFileNewSendEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeTuDongGiaoViecEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentWaitingView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IcheckShowAndHideButtonSame;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DocumentWaitingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DocumentWaitingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentWaitingFragment extends Fragment implements IDocumentWaitingView, ILoginView, IExceptionErrorView, IcheckShowAndHideButtonSame {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rcvDanhSach)
    RecyclerView rcvDanhSach;
    @BindView(R.id.txtSearch)
    EditText txtSearch;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.btn_send_same)
    Button btnSendSame;
    @BindView(R.id.btn_finish_all)
    Button btnFinishAll;
    @BindView(R.id.checkbox_select_all)
    CheckBox checkBoxAll;
    @BindView(R.id.layoutDisplay)
    ConstraintLayout layoutDisplay;

    private IDocumentWaitingPresenter documentWaitingPresenter = new DocumentWaitingPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;

    private DocumentWaitingAdapter documentWaitingAdapter;
    private List<DocumentWaitingInfo> documentWaitingInfoList;
    private int page = 1;
    private int totalPage;
    private String keyword = "";
    private Timer timer;
    private LoginInfo loginInfo;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private boolean checkShơwCheckBocInAdapter = false;
    private boolean checkButtonSendSame = false;
    private boolean checkButtonFinishSame = false;


    public DocumentWaitingFragment() {
        // Required empty public constructor
    }

    public static DocumentWaitingFragment newInstance(String param1, String param2) {
        DocumentWaitingFragment fragment = new DocumentWaitingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static DocumentWaitingFragment newInstance(String param1) {
        DocumentWaitingFragment fragment = new DocumentWaitingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document_waiting, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        timer = new Timer();
        ((MainActivity) getActivity()).showNotify(false);
        appPrefs = Application.getApp().getAppPrefs();
        loginInfo = appPrefs.getAccountLogin();
        checkButton(loginInfo);
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });
        rcvDanhSach.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                try {
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });
        //  txtSearch.setTypeface(Application.getApp().getTypeface());
        txtSearch.addTextChangedListener(
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
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (txtSearch.getText() != null && !txtSearch.getText().toString().trim().equals("")) {
                                            keyword = txtSearch.getText().toString().trim();
                                        } else {
                                            keyword = "";
                                        }
                                        View view = getActivity().getCurrentFocus();
                                        if (view != null) {
                                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        }
                                        search();
                                    }
                                });
                            }
                        }, Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
        //getAllDocuments();
        connectionDetector = new ConnectionDetector(getContext());
        progressDialog = new ProgressDialog(getContext());
        page = 1;
        documentWaitingInfoList = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            EventBus.getDefault().postSticky(new KhoVanBanEvent(mParam1));
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page)
                    , String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword, mParam1));

        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRefresh();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void checkButton(LoginInfo loginInfo) {
        if (loginInfo != null) {
            if (loginInfo.getMenu() != null && loginInfo.getMenu().size() > 0) {
                for (LoginInfo.Menu menu : loginInfo.getMenu()) {
                    for (LoginInfo.Menu.Child child : menu.getChild()) {
                        if (child.getParam().equals(mParam1)) {
                            if ((child.getBtnChuyenDongThoi() != null && child.getBtnChuyenDongThoi().equals("true")) || (child.getBtnKetThucDongThoi() != null && child.getBtnKetThucDongThoi().equals("true"))) {
                                if ((child.getBtnKetThucDongThoi() != null && child.getBtnKetThucDongThoi().equals("true"))) {
                                    checkButtonFinishSame = true;
                                } else {
                                    checkButtonFinishSame = false;
                                }
                                if ((child.getBtnChuyenDongThoi() != null && child.getBtnChuyenDongThoi().equals("true"))) {
                                    checkButtonSendSame = true;
                                } else {
                                    checkButtonSendSame = false;
                                }
                                checkShơwCheckBocInAdapter = true;
                                checkBoxAll.setVisibility(View.VISIBLE);
                            } else {
                                checkBoxAll.setVisibility(View.GONE);
                                checkShơwCheckBocInAdapter = false;
                            }
                            break;
                        }
                    }
                }

            }
        }
    }

    private void search() {
        page = 1;
        documentWaitingInfoList = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page),
                    String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword, mParam1));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void loadRefresh() {
        page = 1;
        documentWaitingInfoList = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page),
                    String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword, mParam1));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void prepareNewData() {
        btnSendSame.setVisibility(View.GONE);
        btnFinishAll.setVisibility(View.GONE);
        documentWaitingAdapter = new DocumentWaitingAdapter(getContext(), documentWaitingInfoList, checkShơwCheckBocInAdapter, this::onCheckShowAndeHideButton);
        documentWaitingAdapter.setLoadMoreListener(new DocumentWaitingAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rcvDanhSach.post(new Runnable() {
                    @Override
                    public void run() {
                        page++;
//                        if (page <= totalPage + 1) {
//                            loadMore(page);
//                        }
                        if (documentWaitingInfoList.size() % Constants.DISPLAY_RECORD_SIZE == 0) {
                            loadMore(page);
                        }
                    }
                });
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcvDanhSach.setLayoutManager(mLayoutManager);
        rcvDanhSach.setItemAnimator(new DefaultItemAnimator());
        rcvDanhSach.setAdapter(documentWaitingAdapter);
        documentWaitingAdapter.notifyDataSetChanged();
        if (documentWaitingInfoList != null && documentWaitingInfoList.size() > 0) {
            txtNoData.setVisibility(View.GONE);
            if (checkBoxAll.getVisibility() == View.VISIBLE) {
                checkBoxAll.setChecked(false);
            }
        } else {
            txtNoData.setVisibility(View.VISIBLE);
            checkBoxAll.setVisibility(View.GONE);
        }
    }

    private void loadMore(int page) {
        if (connectionDetector.isConnectingToInternet()) {
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page),
                    String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword, mParam1));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @OnClick({R.id.checkbox_select_all, R.id.btn_send_same, R.id.btn_finish_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.checkbox_select_all:
                if (documentWaitingInfoList != null && documentWaitingInfoList.size() > 0) {
                    if (documentWaitingAdapter != null) {
                        if (checkBoxAll.isChecked()) {
                            if (checkButtonSendSame) {
                                btnSendSame.setVisibility(View.VISIBLE);
                            } else {
                                btnSendSame.setVisibility(View.GONE);
                            }
                            if (checkButtonFinishSame) {
                                btnFinishAll.setVisibility(View.VISIBLE);
                            } else {
                                btnFinishAll.setVisibility(View.GONE);
                            }
                            selectAllListOrType();

                        } else {
                            btnSendSame.setVisibility(View.GONE);
                            btnFinishAll.setVisibility(View.GONE);
                            for (int i = 0; i < documentWaitingInfoList.size(); i++) {
                                if (documentWaitingInfoList.get(i).isSelect()) {
                                    documentWaitingInfoList.get(i).setSelect(false);
                                    documentWaitingAdapter.notifyItemChanged(i);
                                }
                            }
                        }

                    }
                }
                break;
            case R.id.btn_send_same:
                ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(getContext());
                applicationSharedPreferences.setPersonPre(new ListPersonPreEvent(null, null, null));
                getListButtonSendSame();
                break;
            case R.id.btn_finish_all:
                finishDocumentSame();
                break;
        }
    }

    private void selectAllListOrType() {
        if (documentWaitingInfoList != null && documentWaitingInfoList.size() > 0) {
            for (int i = 0; i < documentWaitingInfoList.size(); i++) {
                documentWaitingInfoList.get(i).setSelect(true);
                documentWaitingAdapter.notifyItemChanged(i);
            }
        }

    }

    private void getListButtonSendSame() {
        for (int i = 0; i < documentWaitingInfoList.size(); i++) {
            for (int j = i + 1; j < documentWaitingInfoList.size(); j++) {
                if (documentWaitingInfoList.get(i).isSelect() && documentWaitingInfoList.get(j).isSelect()) {
                    if (!documentWaitingInfoList.get(i).getType().equals(documentWaitingInfoList.get(j).getType())) {
                        AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.SELECT_DOCUMENT_TYPE_SAME_ERROR), getContext().getString(R.string.SELECT_DOCUMENT_TYPE_SAME_CONTENT_ERROR), true, AlertDialogManager.ERROR);
                        return;
                    }
                }

            }
        }
        if (documentWaitingInfoList != null && documentWaitingInfoList.size() > 0) {
            documentWaitingPresenter.getListButtonSendSame(new ListButtonSendSameRequest(getDocId(documentWaitingInfoList, false)));
        }
    }

    private void finishDocumentSame() {
        documentWaitingPresenter.finishDocumentAll(new FinishDocumentSameRequest(getDocId(documentWaitingInfoList, false)));
    }

    private String getDocId(List<DocumentWaitingInfo> list, boolean returnStringArray) {
        ArrayList<String> listDocId = new ArrayList<>();
        String docId = "";
        if (returnStringArray) {
            for (DocumentWaitingInfo item : list) {
                if (item != null && item.getId() != null && item.isSelect()) {
                    listDocId.add(item.getId());
                }
            }
            docId = listDocId.toString();
        } else {
            for (DocumentWaitingInfo item : list) {
                if (item != null && item.getId() != null && item.isSelect()) {
                    if (docId.trim().length() > 0) {
                        docId = docId + "," + item.getId();
                    } else {
                        docId = item.getId();
                    }
                }
            }
        }


        return docId;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            checkButton(loginInfo);
            EventBus.getDefault().postSticky(new InitEvent(true));
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page)
                    , String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword, mParam1));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onSuccessRecords(List<DocumentWaitingInfo> documentWaitingInfos) {
        if (EventBus.getDefault().getStickyEvent(InitEvent.class).isInit()) {
            if (documentWaitingInfos != null && documentWaitingInfos.size() > 0) {
                documentWaitingInfoList.addAll(documentWaitingInfos);
            }
            prepareNewData();
            EventBus.getDefault().postSticky(new InitEvent(false));
        } else {
            documentWaitingInfoList.add(new DocumentWaitingInfo());
            documentWaitingAdapter.notifyItemInserted(documentWaitingInfoList.size() - 1);
            documentWaitingInfoList.remove(documentWaitingInfoList.size() - 1);
            if (documentWaitingInfos != null && documentWaitingInfos.size() > 0) {
                if (checkBoxAll.isChecked()) {
                    for (int i = 0; i < documentWaitingInfos.size(); i++) {
                        documentWaitingInfos.get(i).setSelect(true);
                    }
                }

                documentWaitingInfoList.addAll(documentWaitingInfos);
            } else {
                documentWaitingAdapter.setMoreDataAvailable(false);
            }
            documentWaitingAdapter.notifyDataChanged();
        }
    }

    @Override
    public void onSuccessListButtonSendSame(List<ListButtonSendSameInfo> listButtonSendSameInfos) {
        List<TypeChangeDocument> typeChangeDocumentList = new ArrayList<>();
        for (ListButtonSendSameInfo item : listButtonSendSameInfos) {
            typeChangeDocumentList.add(convertToTypeChangeDocument(item));
        }
        if (typeChangeDocumentList != null && typeChangeDocumentList.size() > 0) {
            TypeChangeDocumentRequest typeChangeDocumentRequest = null;
            if (documentWaitingInfoList != null && documentWaitingInfoList.size() > 0) {
                typeChangeDocumentRequest = new TypeChangeDocumentRequest(getDocId(documentWaitingInfoList, true), documentWaitingInfoList.get(0).getProcessDefinitionId(), documentWaitingInfoList.get(0).getCongVanDenDi());
                EventBus.getDefault().postSticky(typeChangeDocumentRequest);//truyền data qua newsendocumentActivity
            }
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
                EventBus.getDefault().postSticky(new FinishEvent(0, false));
                EventBus.getDefault().postSticky(new TypeChangeEvent("", 0, typeChangeDocumentList));
                listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(getContext());
                ListPersonPreEvent listPersonPreEvent = applicationSharedPreferences.getPersonPre();
                listPersonPreEvent.setPersonProcessPreList(listPersonEvent.getPersonProcessList());
                listPersonPreEvent.setPersonSendPreList(listPersonEvent.getPersonSendList());
                listPersonPreEvent.setPersonNotifyPreList(listPersonEvent.getPersonNotifyList());
                Application.getApp().getAppPrefs().setPersonPre(listPersonPreEvent);
                startActivity(new Intent(getContext(), SelectPersonActivityNewConvert.class).putExtra("DOCID", getDocId(documentWaitingInfoList, true)));
                //finish();
            } else {
                List<String> labels = new ArrayList<String>();
                for (int i = 0; i < typeChangeDocumentList.size(); i++) {
                    labels.add(typeChangeDocumentList.get(i).getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.weight_table_menu, R.id.textViewTableMenuItem, labels);
                final ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
                listPopupWindow.setAdapter(adapter);
                listPopupWindow.setAnchorView(btnSendSame);
                listPopupWindow.setWidth(550);
                listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
                listPopupWindow.setHorizontalOffset(-402); // margin left
                listPopupWindow.setVerticalOffset(-20);
                TypeChangeDocumentRequest finalTypeChangeDocumentRequest = typeChangeDocumentRequest;
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
                                    && (finalTypeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE) ||
                                    finalTypeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND))) {
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
                                        finalTypeChangeDocumentRequest.getDocId(), ""));
                                listPersonEvent.setPersonNotifyList(null);
                                listPersonEvent.setPersonDirectList(null);
                                listPersonEvent.setPersonLienThongList(null);
                            }
                        }
                        EventBus.getDefault().postSticky(listPersonEvent);
                        EventBus.getDefault().postSticky(new TypeChangeEvent("", i, typeChangeDocumentList));
                        listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                        ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(getContext());
                        ListPersonPreEvent listPersonPreEvent = applicationSharedPreferences.getPersonPre();
                        listPersonPreEvent.setPersonProcessPreList(listPersonEvent.getPersonProcessList());
                        listPersonPreEvent.setPersonSendPreList(listPersonEvent.getPersonSendList());
                        listPersonPreEvent.setPersonNotifyPreList(listPersonEvent.getPersonNotifyList());
                        Application.getApp().getAppPrefs().setPersonPre(listPersonPreEvent);
                        EventBus.getDefault().postSticky(new FinishEvent(0, false));
                        startActivity(new Intent(getContext(), SelectPersonActivityNewConvert.class).putExtra("DOCID", getDocId(documentWaitingInfoList, true)));
                        listPopupWindow.dismiss();
                    }
                });
                listPopupWindow.show();
            }
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getResources().getString(R.string.str_khong_chuyenduoc), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessFinishDocumentSendSame() {
        Toast.makeText(getContext(), getString(R.string.FINISH_SUCCESS), Toast.LENGTH_LONG).show();
        loadRefresh();
    }

    private TypeChangeDocument convertToTypeChangeDocument(ListButtonSendSameInfo listButtonSendSameInfo) {
        TypeChangeDocument typeChangeDocument = new TypeChangeDocument();
        typeChangeDocument.setType(listButtonSendSameInfo.getType());
        typeChangeDocument.setNextStep(listButtonSendSameInfo.getNextStep());
        typeChangeDocument.setApprovedValue(listButtonSendSameInfo.getApprovedValue());
        typeChangeDocument.setName(listButtonSendSameInfo.getName());
        typeChangeDocument.setFormId(listButtonSendSameInfo.getFormId());
        typeChangeDocument.setRoleId(listButtonSendSameInfo.getRoleId());
        typeChangeDocument.setUploadFile(listButtonSendSameInfo.getUploadFile());
        typeChangeDocument.setCommentEnable(listButtonSendSameInfo.getCommentEnable());
        return typeChangeDocument;
    }

    @Override
    public void onError(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.str_dialog_thongbao), apiError.getMessage(), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void showProgress() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressDialog();
    }

    @Override
    public void hideProgress() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).hideProgressDialog();
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

    @Override
    public void onCheckShowAndeHideButton() {
        if (documentWaitingInfoList != null && documentWaitingInfoList.size() > 0) {
            for (int i = 0; i < documentWaitingInfoList.size(); i++) {
                if (documentWaitingInfoList.get(i).isSelect()) {
                    if (checkButtonFinishSame) {
                        btnFinishAll.setVisibility(View.VISIBLE);
                    } else {
                        btnFinishAll.setVisibility(View.GONE);
                    }

                    if (checkButtonSendSame) {
                        btnSendSame.setVisibility(View.VISIBLE);
                    } else {
                        btnSendSame.setVisibility(View.GONE);
                    }
                    return;
                } else {
                    if (btnSendSame.getVisibility() == View.VISIBLE) {
                        btnSendSame.setVisibility(View.GONE);
                    }
                    if (btnFinishAll.getVisibility() == View.VISIBLE) {
                        btnFinishAll.setVisibility(View.GONE);
                    }


                }
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ReloadDocWaitingtEvent reloadDocWaitingtEvent) {
        if (reloadDocWaitingtEvent != null && reloadDocWaitingtEvent.isLoad()) {
            loadRefresh();
        }
        EventBus.getDefault().removeStickyEvent(ReloadDocWaitingtEvent.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        EventBus.getDefault().removeStickyEvent(ReloadDocWaitingtEvent.class);
    }


}
