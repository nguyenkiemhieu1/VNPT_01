package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.ItemPerformRecyclerViewAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.ItemRecyclerSubTaskViewAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListPersonUnitDetailResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSubTaskResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.IWorkManagePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.WorkManagePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.BaseActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.WorkManageEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDetailFragmentView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class UnitPerFormFragment extends Fragment implements IDetailFragmentView, ILoginView, IExceptionErrorView {
    IWorkManagePresenter iWorkManagePresenter = new WorkManagePresenterImpl(this);
    private List<GetListPersonUnitDetailResponse.Data> lstData = new ArrayList<>();
    ItemPerformRecyclerViewAdapter adapter;

    private ApplicationSharedPreferences appPrefs;
    private ConnectionDetector connectionDetector;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private String idWork = "";

    public UnitPerFormFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_perform_list, container, false);
        appPrefs = Application.getApp().getAppPrefs();
        connectionDetector = new ConnectionDetector(getContext());
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            adapter = new ItemPerformRecyclerViewAdapter(lstData);
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSuccessSubListData(List<GetListSubTaskResponse.Data> listData) {

    }

    @Override
    public void onSuccessUnitPersonListData(List<GetListPersonUnitDetailResponse.Data> listData) {
        if (listData != null && listData.size() > 0) {
            this.lstData.clear();
            this.lstData.addAll(listData);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onErrorListData(APIError apiError) {
        if (!getActivity().isFinishing()) {
            sendExceptionError(apiError);
            if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
                if (connectionDetector.isConnectingToInternet()) {
                    loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
                } else {
                    AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            } else {
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), apiError.getMessage() != null && !apiError.getMessage().trim().equals("") ? apiError.getMessage().trim() : "Có lỗi xảy ra!\nVui lòng thử lại sau!", true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(WorkManageEvent.class);
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        if (!getActivity().isFinishing()) {
            Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
            getListDataUnitPerSon(idWork);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void showProgress() {
        if (!getActivity().isFinishing()) {
            if (getActivity() instanceof BaseActivity)
                ((BaseActivity) getActivity()).showProgressDialog();
        }
    }

    @Override
    public void hideProgress() {
        if (!getActivity().isFinishing()) {
            if (getActivity() instanceof BaseActivity)
                ((BaseActivity) getActivity()).hideProgressDialog();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WorkManageEvent event) {
        /* Do something */
        if (event != null) {
            if (event.getId() != null) {
                idWork = event.getId();
                getListDataUnitPerSon(idWork);
            }

        }
    }

    private void getListDataUnitPerSon(String idWork) {
        iWorkManagePresenter.getListUnitPersonDetailProcess(idWork);
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
