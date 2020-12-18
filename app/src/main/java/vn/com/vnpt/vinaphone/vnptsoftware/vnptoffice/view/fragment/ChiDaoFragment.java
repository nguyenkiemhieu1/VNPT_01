package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.ChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoGuiRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao.ChiDaoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao.IChiDaoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.BaseActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.SendChiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ChiDaotEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.InitEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadChiDaotEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IChiDaoView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChiDaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChiDaoFragment extends Fragment implements IChiDaoView, ILoginView, DatePickerDialog.OnDateSetListener, IExceptionErrorView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_from)
    EditText tvFrom;
    @BindView(R.id.tv_to)
    EditText tvTo;
    @BindView(R.id.tv_tieude)
    EditText tvTieude;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.rcvDanhSach)
    RecyclerView rcvDanhSach;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.layoutDisplay)
    LinearLayout layoutDisplay;
    private List<Object> list;
    private ChiDaoAdapter adapter;
    private IChiDaoPresenter chiDaoPresenter = new ChiDaoPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private int page = 1;
    private int type;
    private int _day;
    private int _month;
    private int _year;
    private String typeDate;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChiDaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChiDaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChiDaoFragment newInstance(String param1, String param2) {
        ChiDaoFragment fragment = new ChiDaoFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_dao, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = EventBus.getDefault().getStickyEvent(ChiDaotEvent.class).getType();
        if (type == 0) {
            ((MainActivity) getActivity()).setTitle(getString(R.string.CHIDAO_NHAN_MENU));
        }
        if (type == 1) {
            ((MainActivity) getActivity()).setTitle(getString(R.string.CHIDAO_GUI_MENU));
        }
    }

    private void init() {
        ((MainActivity) getActivity()).hideNotify();
        appPrefs = Application.getApp().getAppPrefs();
        Calendar calendar = Calendar.getInstance();
        _year = calendar.get(Calendar.YEAR);
        _month = calendar.get(Calendar.MONTH);
        _day = calendar.get(Calendar.DAY_OF_MONTH);
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
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });
        type = EventBus.getDefault().getStickyEvent(ChiDaotEvent.class).getType();
        tvTieude.setTypeface(Application.getApp().getTypeface());
        connectionDetector = new ConnectionDetector(getContext());
        progressDialog = new ProgressDialog(getContext());
        page = 1;
        list = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            if (type == 0) {
                chiDaoPresenter.getChiDaoNhan(new ChiDaoNhanRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                        tvFrom.getText().toString().trim(), tvTo.getText().toString().trim(), "", tvTieude.getText().toString().trim()));
            }
            if (type == 1) {
                chiDaoPresenter.getChiDaoGui(new ChiDaoGuiRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                        tvFrom.getText().toString().trim(), tvTo.getText().toString().trim(), tvTieude.getText().toString().trim()));
            }
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

    private void search() {
        page = 1;
        list = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            if (type == 0) {
                chiDaoPresenter.getChiDaoNhan(new ChiDaoNhanRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                        tvFrom.getText().toString().trim(), tvTo.getText().toString().trim(), "", tvTieude.getText().toString().trim()));
            }
            if (type == 1) {
                chiDaoPresenter.getChiDaoGui(new ChiDaoGuiRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                        tvFrom.getText().toString().trim(), tvTo.getText().toString().trim(), tvTieude.getText().toString().trim()));
            }
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void loadRefresh() {
        page = 1;
        list = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            if (type == 0) {
                chiDaoPresenter.getChiDaoNhan(new ChiDaoNhanRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                        tvFrom.getText().toString().trim(), tvTo.getText().toString().trim(), "", tvTieude.getText().toString().trim()));
            }
            if (type == 1) {
                chiDaoPresenter.getChiDaoGui(new ChiDaoGuiRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                        tvFrom.getText().toString().trim(), tvTo.getText().toString().trim(), tvTieude.getText().toString().trim()));
            }
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void prepareNewData() {
        adapter = new ChiDaoAdapter(getContext(), list, type);
        adapter.setLoadMoreListener(new ChiDaoAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rcvDanhSach.post(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        if (list.size() % Constants.DISPLAY_RECORD_SIZE == 0) {
                            loadMore(page);
                        }
                    }
                });
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcvDanhSach.setLayoutManager(mLayoutManager);
        rcvDanhSach.setItemAnimator(new DefaultItemAnimator());
        rcvDanhSach.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (list != null && list.size() > 0) {
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void loadMore(int page) {
        if (connectionDetector.isConnectingToInternet()) {
            if (type == 0) {
                chiDaoPresenter.getChiDaoNhan(new ChiDaoNhanRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                        tvFrom.getText().toString().trim(), tvTo.getText().toString().trim(), "", tvTieude.getText().toString().trim()));
            }
            if (type == 1) {
                chiDaoPresenter.getChiDaoGui(new ChiDaoGuiRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                        tvFrom.getText().toString().trim(), tvTo.getText().toString().trim(), tvTieude.getText().toString().trim()));
            }
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            if (type == 0) {
                chiDaoPresenter.getChiDaoNhan(new ChiDaoNhanRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                        tvFrom.getText().toString().trim(), tvTo.getText().toString().trim(), "", tvTieude.getText().toString().trim()));
            }
            if (type == 1) {
                chiDaoPresenter.getChiDaoGui(new ChiDaoGuiRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                        tvFrom.getText().toString().trim(), tvTo.getText().toString().trim(), tvTieude.getText().toString().trim()));
            }
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
    public void onSuccess(List<Object> objects) {
        if (EventBus.getDefault().getStickyEvent(InitEvent.class).isInit()) {
            if (objects != null && objects.size() > 0) {
                list.addAll(objects);
            }
            prepareNewData();
            EventBus.getDefault().postSticky(new InitEvent(false));
        } else {
            list.add(new Object());
            adapter.notifyItemInserted(list.size() - 1);
            list.remove(list.size() - 1);
            if (objects != null && objects.size() > 0) {
                list.addAll(objects);
            } else {
                adapter.setMoreDataAvailable(false);
            }
            adapter.notifyDataChanged();
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
    public void onSuccess(Object object) {

    }

    @Override
    public void onSuccess() {

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
    public void onSuccessDonViNhan(Object object) {

    }

    @Override
    public void onSuccessDanhSachDonViNhan(Object object) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_from, R.id.tv_to, R.id.btnSearch, R.id.btnAdd})
    public void onViewClicked(View view) {
        DatePickerDialog dialog = null;
        switch (view.getId()) {
            case R.id.tv_from:
                typeDate = "FROM";
                dialog = new DatePickerDialog(getContext(), this, _year, _month, _day);
                dialog.show();
                break;
            case R.id.tv_to:
                typeDate = "TO";
                dialog = new DatePickerDialog(getContext(), this, _year, _month, _day);
                dialog.show();
                break;
            case R.id.btnSearch:
                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }
                search();
                break;
            case R.id.btnAdd:
//                Intent intent = new Intent(getContext(), SendChiDaoActivity.class);
                Intent intent = new Intent(getContext(), SendChiDaoV2Activity.class);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        _year = year;
        _month = month;
        _day = dayOfMonth;
        updateDisplay();
    }

    private void updateDisplay() {
        int month = _month + 1;
        String monthStr = "";
        if (month < 10) {
            monthStr = "0" + String.valueOf(month);
        } else {
            monthStr = String.valueOf(month);
        }
        if (typeDate.equals("FROM")) {
            tvFrom.setText(new StringBuilder().append(_day).append("/").append(monthStr).append("/").append(_year));
           // tvFrom.setSelection(tvFrom.getText().toString().trim().length());
        }
        if (typeDate.equals("TO")) {
            tvTo.setText(new StringBuilder().append(_day).append("/").append(monthStr).append("/").append(_year));
         //   tvTo.setSelection(tvTo.getText().toString().trim().length());
        }
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
    public void onEvent(ReloadChiDaotEvent reloadChiDaotEvent) {
        if (reloadChiDaotEvent != null && reloadChiDaotEvent.isLoad()) {
            loadRefresh();
        }
        EventBus.getDefault().removeStickyEvent(ReloadChiDaotEvent.class);
    }
}
