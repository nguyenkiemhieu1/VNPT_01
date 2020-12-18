package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.DocumentProcessedAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.DocumentProcessedParameter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentProcessedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentProcessed;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentProcessedInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentprocessed.DocumentProcessedPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentprocessed.IDocumentProcessedPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.BaseActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.InitEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocProcessedEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentProcessedView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DocumentProcessedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DocumentProcessedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentProcessedFragment extends Fragment implements IDocumentProcessedView, ILoginView, IExceptionErrorView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rcvDanhSach) RecyclerView rcvDanhSach;
    private DocumentProcessedAdapter documentProcessedAdapter;
    private List<DocumentProcessedInfo> documentProcessedInfoList;
    private int page = 1;
    private int totalPage;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private IDocumentProcessedPresenter documentProcessedPresenter = new DocumentProcessedPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.txtSearch) EditText txtSearch;
    @BindView(R.id.layoutSearch) LinearLayout layoutSearch;
    @BindView(R.id.txtNoData) TextView txtNoData;
    private String keyword = "";
    @BindView(R.id.layoutDisplay) LinearLayout layoutDisplay;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    public DocumentProcessedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocumentProcessedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocumentProcessedFragment newInstance(String param1, String param2) {
        DocumentProcessedFragment fragment = new DocumentProcessedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.fragment_document_processed, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        ((MainActivity) getActivity()).showNotify(false);
        appPrefs = Application.getApp().getAppPrefs();
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex){
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
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;
            }
        });
        txtSearch.setTypeface(Application.getApp().getTypeface());
        txtSearch.addTextChangedListener(
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
        documentProcessedInfoList = new ArrayList<DocumentProcessedInfo>();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRefresh();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        page = 1;
        connectionDetector = new ConnectionDetector(getContext());
        progressDialog = new ProgressDialog(getContext());
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
//            documentProcessedPresenter.getCount(new DocumentProcessedRequest(Constants.PAGE_NO_DEFAULT, String.valueOf(Constants.DISPLAY_RECORD_SIZE),
//                    new DocumentProcessedParameter(null, keyword)));
            documentProcessedPresenter.getRecords(new DocumentProcessedRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    new DocumentProcessedParameter(null, keyword)));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void loadRefresh() {
        page = 1;
        documentProcessedInfoList = new ArrayList<DocumentProcessedInfo>();
        EventBus.getDefault().postSticky(new InitEvent(true));
        if (connectionDetector.isConnectingToInternet()) {
//            documentProcessedPresenter.getCount(new DocumentProcessedRequest(Constants.PAGE_NO_DEFAULT, String.valueOf(Constants.DISPLAY_RECORD_SIZE),
//                    new DocumentProcessedParameter(null, keyword)));
            documentProcessedPresenter.getRecords(new DocumentProcessedRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    new DocumentProcessedParameter(null, keyword)));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void search() {
        page = 1;
        EventBus.getDefault().postSticky(new InitEvent(true));
        documentProcessedInfoList = new ArrayList<DocumentProcessedInfo>();
        if (connectionDetector.isConnectingToInternet()) {
//            documentProcessedPresenter.getCount(new DocumentProcessedRequest(Constants.PAGE_NO_DEFAULT, String.valueOf(Constants.DISPLAY_RECORD_SIZE),
//                    new DocumentProcessedParameter(null, keyword)));
            documentProcessedPresenter.getRecords(new DocumentProcessedRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    new DocumentProcessedParameter(null, keyword)));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
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
            EventBus.getDefault().postSticky(new InitEvent(true));
//            documentProcessedPresenter.getCount(new DocumentProcessedRequest(Constants.PAGE_NO_DEFAULT, String.valueOf(Constants.DISPLAY_RECORD_SIZE),
//                    new DocumentProcessedParameter(null, keyword)));
            documentProcessedPresenter.getRecords(new DocumentProcessedRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    new DocumentProcessedParameter(null, keyword)));
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

    private void prepareNewData() {
        documentProcessedAdapter = new DocumentProcessedAdapter(getContext(), documentProcessedInfoList);
        documentProcessedAdapter.setLoadMoreListener(new DocumentProcessedAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rcvDanhSach.post(new Runnable() {
                    @Override
                    public void run() {
                        page++;
//                            if (page <= totalPage + 1) {
//                                loadMore(page);
//                            }
                        if (documentProcessedInfoList.size() % Constants.DISPLAY_RECORD_SIZE == 0) {
                            loadMore(page);
                        }
                    }
                });
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcvDanhSach.setLayoutManager(mLayoutManager);
        rcvDanhSach.setItemAnimator(new DefaultItemAnimator());
        rcvDanhSach.setAdapter(documentProcessedAdapter);
        documentProcessedAdapter.notifyDataSetChanged();
        if (documentProcessedInfoList != null && documentProcessedInfoList.size() > 0) {
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void loadMore(int page) {
        if (connectionDetector.isConnectingToInternet()) {
            documentProcessedPresenter.getRecords(new DocumentProcessedRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    new DocumentProcessedParameter(null, keyword)));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessRecords(List<DocumentProcessedInfo> documentProcessedInfos) {
        if (EventBus.getDefault().getStickyEvent(InitEvent.class).isInit()) {
            if (documentProcessedInfos != null && documentProcessedInfos.size() > 0) {
                documentProcessedInfoList.addAll(documentProcessedInfos);
            }
            prepareNewData();
            EventBus.getDefault().postSticky(new InitEvent(false));
        } else {
            documentProcessedInfoList.add(new DocumentProcessedInfo());
            documentProcessedAdapter.notifyItemInserted(documentProcessedInfoList.size() - 1);
            documentProcessedInfoList.remove(documentProcessedInfoList.size() - 1);
            if (documentProcessedInfos != null && documentProcessedInfos.size() > 0) {
                documentProcessedInfoList.addAll(documentProcessedInfos);
            } else {
                documentProcessedAdapter.setMoreDataAvailable(false);
            }
            documentProcessedAdapter.notifyDataChanged();
        }
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
        }
    }

    @Override
    public void onSuccessCount(CountDocumentProcessed countDocumentProcessed) {
        totalPage = countDocumentProcessed.getPageNo();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            documentProcessedPresenter.getRecords(new DocumentProcessedRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    new DocumentProcessedParameter(null, keyword)));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
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
    public void onEvent(ReloadDocProcessedEvent reloadDocProcessedEvent) {
        if (reloadDocProcessedEvent != null && reloadDocProcessedEvent.isLoad()) {
            loadRefresh();
        }
        EventBus.getDefault().removeStickyEvent(ReloadDocProcessedEvent.class);
    }
}
