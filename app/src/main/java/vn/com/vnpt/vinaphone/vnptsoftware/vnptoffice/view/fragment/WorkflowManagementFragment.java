package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.WorkManagerAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.GetListJobAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobAssignRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListStatusComboxRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.IWorkManagePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.WorkManagePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.AddPersonWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.BaseActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.CreateSubTaskActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailWorkManagementActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.EditInforWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.HandlingProgressActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.UpdateStatusWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.WorkAssessActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UpdateSuccessEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UpdateWorkManageEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.WorkManageEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkManageView;

public class WorkflowManagementFragment extends Fragment implements IWorkManageView, IExceptionErrorView {

    @BindView(R.id.edt_name_work)
    EditText edtNameWork;
    @BindView(R.id.spin_status)
    Spinner spinStatus;
    @BindView(R.id.spin_unit)
    Spinner spinUnit;
    @BindView(R.id.listView_work)
    SwipeMenuListView listViewWork;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private static final String ARG_COLUMN_COUNT = "column-count";
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    private int mColumnCount = 1;
    private WorkManagerAdapter mAdapter;
    SwipeMenuCreator creator;
    IWorkManagePresenter iWorkManagePresenter = new WorkManagePresenterImpl(this);
    private boolean flagLoading = false;
    private int pageNo = 1;
    private int pageRec = 15;
    private String nameWork = "";
    private String statusWork = "";
    private int typeOrganize = -1;
    private List<GetListJobAssignRespone.Data> listDataItem = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Timer timer = new Timer();
    private boolean isFirstShow = true;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private boolean EndProgess = false;

    public WorkflowManagementFragment() {

    }

    public static WorkflowManagementFragment newInstance(int typeSwipe) {
        WorkflowManagementFragment fragment = new WorkflowManagementFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, typeSwipe);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_management, container, false);
        ButterKnife.bind(this, view);
        initView();
        getListStatus();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isFirstShow = true;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                if (listDataItem != null && listDataItem.size() > 0) {
                    listDataItem.clear();
                }
                getApiListTask();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView() {
        mAdapter = new WorkManagerAdapter(listDataItem, getActivity(), 2);
        appPrefs = Application.getApp().getAppPrefs();
        progressDialog = new ProgressDialog(getActivity());
        createSwipeMenuCreator();
        listViewWork.setEmptyView(tvNodata);
        listViewWork.setAdapter(mAdapter);
        listViewWork.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (flagLoading == false) {
                        pageNo++;
                        flagLoading = true;
                        getApiListTask();
                    }
                }
            }
        });
        listViewWork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().postSticky(new WorkManageEvent(listDataItem.get(position).getId()
                        , listDataItem.get(position).getNxlId(), listDataItem.get(position).getEndDate()));
                Intent i = new Intent(getActivity(), DetailWorkManagementActivity.class);
                i.putExtra("id", listDataItem.get(position).getId());
                i.putExtra("nxlid", listDataItem.get(position).getNxlId());
                i.putExtra("typeWork", mColumnCount);
                getActivity().startActivity(i);
            }
        });
        listViewWork.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                swipeRefreshLayout.setEnabled(false);
            }

            @Override
            public void onSwipeEnd(int position) {
                swipeRefreshLayout.setEnabled(true);
            }
        });
        edtNameWork.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
                                nameWork = s.toString().trim();
                                pageNo = 1;
                                if (listDataItem != null && listDataItem.size() > 0) {
                                    listDataItem.clear();
                                }
                                getApiListTask();
                            }
                        });
                    }
                }, Constants.DELAY_TIME_SEARCH);
            }
        });
        if (mColumnCount == 1) {
            spinUnit.setVisibility(View.VISIBLE);
            initSpinerUnit();

        } else {
            spinUnit.setVisibility(View.GONE);
        }
    }

    private void initSpinerUnit() {
        List<GetListStatusComboxRespone.Data> listUnit = new ArrayList<>();
        listUnit.add(new GetListStatusComboxRespone.Data(getString(R.string.str_tatca), -1));
        listUnit.add(new GetListStatusComboxRespone.Data(getString(R.string.str_viec_canhan), 0));
        listUnit.add(new GetListStatusComboxRespone.Data(getString(R.string.str_viec_donvi), 1));
        final ArrayAdapter<GetListStatusComboxRespone.Data> adapter = new ArrayAdapter<GetListStatusComboxRespone.Data>
                (getActivity(), android.R.layout.simple_spinner_item, listUnit) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinUnit.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                GetListStatusComboxRespone.Data dataItem = (GetListStatusComboxRespone.Data) spinUnit.getSelectedItem();
                typeOrganize = dataItem.getValue();
                pageNo = 1;
                if (listDataItem != null && listDataItem.size() > 0) {
                    listDataItem.clear();
                }
                if (!isFirstShow) {
                    getApiListTask();
                } else {
                    isFirstShow = false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinUnit.setSelection(0, true);
    }

    private void getListStatus() {
        iWorkManagePresenter.getListStatusCombox("TRANGTHAITIMKIEM");
        EndProgess=false;
    }

    private void getApiListTask() {
        if (mColumnCount == 1) {
            iWorkManagePresenter.getListJobReceive(new GetListJobAssignRequest(pageNo, pageRec,
                    nameWork, statusWork, typeOrganize));
            EndProgess=true;
        } else {
            iWorkManagePresenter.getListJobAssign(new GetListJobAssignRequest(pageNo, pageRec,
                    nameWork, statusWork, null));
            EndProgess=true;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onSuccessGetListData(List<GetListJobAssignRespone.Data> listData) {
        if (listData != null && listData.size() > 0) {
            this.listDataItem.addAll(listData);
            mAdapter.notifyDataSetChanged();
            if (listData.size() >= pageRec) {
                flagLoading = false;
            } else {
                flagLoading = true;
            }
        } else {
            this.listDataItem.clear();
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onSuccessGetListStatus(List<GetListStatusComboxRespone.Data> listData) {
        if (listData != null && listData.size() > 0) {
            initDataSpinner(listData);
        }
//        getApiListTask();
    }

    private void initDataSpinner(List<GetListStatusComboxRespone.Data> listData) {
        final ArrayAdapter<GetListStatusComboxRespone.Data> adapter = new ArrayAdapter<GetListStatusComboxRespone.Data>
                (getActivity(), android.R.layout.simple_spinner_item, listData) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinStatus.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                GetListStatusComboxRespone.Data dataItem = (GetListStatusComboxRespone.Data) spinStatus.getSelectedItem();
                if (dataItem.getValue() != -1) {
                    statusWork = String.valueOf(dataItem.getValue());
                } else {
                    statusWork = "";
                }
                pageNo = 1;
                if (listDataItem != null && listDataItem.size() > 0) {
                    listDataItem.clear();
                }
                if (mColumnCount != 1) {
                    if (dataItem.getName().contains(getString(R.string.str_chua_hoanthanh))) {
                        mAdapter.updateTypeViewItem(0);
                    } else if (dataItem.getName().contains(getString(R.string.str_da_hoanthanh)) ||
                            dataItem.getName().contains(getString(R.string.str_cho_pheduyet)) ||
                            dataItem.getName().contains(getString(R.string.str_da_pheduyet))) {
                        mAdapter.updateTypeViewItem(1);
                    }
                } else {
                    if (dataItem.getName().contains(getString(R.string.str_da_hoanthanh)) ||
                            dataItem.getName().contains(getString(R.string.str_cho_pheduyet)) ||
                            dataItem.getName().contains(getString(R.string.str_da_pheduyet))) {
                        mAdapter.updateTypeViewItem(3);
                    } else {
                        mAdapter.updateTypeViewItem(2);
                    }
                }
                getApiListTask();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinStatus.setSelection(0, true);
    }

    private void createSwipeMenuCreator() {
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
//                        "Chưa hoàn thành"
                    case 0:
                        SwipeMenuItem openItem = new SwipeMenuItem(
                                getContext().getApplicationContext());
                        openItem.setWidth(dp2px(80));
                        openItem.setIcon(R.drawable.ic_add_person_work);
                        menu.addMenuItem(openItem);

                        SwipeMenuItem subTaskItem = new SwipeMenuItem(
                                getContext().getApplicationContext());
                        subTaskItem.setWidth(dp2px(80));
                        subTaskItem.setIcon(R.drawable.ic_update_infor_work);
                        menu.addMenuItem(subTaskItem);

                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getContext().getApplicationContext());
                        deleteItem.setWidth(dp2px(80));
                        deleteItem.setIcon(R.drawable.ic_progress_handling);
                        menu.addMenuItem(deleteItem);
                        break;
//                        "Đã hoàn thành") || ("Chờ phê duyệt")
                    case 1:
                        SwipeMenuItem openWorkEvaluating = new SwipeMenuItem(
                                getContext().getApplicationContext());
                        openWorkEvaluating.setWidth(dp2px(80));
                        openWorkEvaluating.setIcon(R.drawable.ic_work_evaluating_work);
                        menu.addMenuItem(openWorkEvaluating);

                        SwipeMenuItem progressHandling = new SwipeMenuItem(
                                getContext().getApplicationContext());
                        progressHandling.setWidth(dp2px(80));
                        progressHandling.setIcon(R.drawable.ic_progress_handling);
                        menu.addMenuItem(progressHandling);
                        break;

                    case 2:
                        SwipeMenuItem openItemReceive = new SwipeMenuItem(
                                getContext().getApplicationContext());
                        openItemReceive.setWidth(dp2px(80));
                        openItemReceive.setIcon(R.drawable.ic_update_status_work);
                        menu.addMenuItem(openItemReceive);

                        SwipeMenuItem deleteItemReceive = new SwipeMenuItem(
                                getContext().getApplicationContext());
                        deleteItemReceive.setWidth(dp2px(80));
                        deleteItemReceive.setIcon(R.drawable.ic_progress_handling);
                        menu.addMenuItem(deleteItemReceive);

                        SwipeMenuItem subTaskItemReceive = new SwipeMenuItem(
                                getContext().getApplicationContext());
                        subTaskItemReceive.setWidth(dp2px(80));
                        subTaskItemReceive.setIcon(R.drawable.ic_create_sub_task);
                        menu.addMenuItem(subTaskItemReceive);
                        break;
                    case 3:
                        SwipeMenuItem itemReceive = new SwipeMenuItem(
                                getContext().getApplicationContext());
                        itemReceive.setWidth(dp2px(80));
                        itemReceive.setIcon(R.drawable.ic_progress_handling);
                        menu.addMenuItem(itemReceive);
                        break;
                }
            }
        };
        // set creator
        listViewWork.setMenuCreator(creator);
        listViewWork.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                EventBus.getDefault().postSticky(new WorkManageEvent(listDataItem.get(position).getId(),
                        listDataItem.get(position).getNxlId(), listDataItem.get(position).getName(),
                        listDataItem.get(position).getEndDate()));

                UpdateWorkManageEvent updateWorkManageEvent = new UpdateWorkManageEvent();
                updateWorkManageEvent.setId(listDataItem.get(position).getId());
                updateWorkManageEvent.setName(listDataItem.get(position).getName());
                updateWorkManageEvent.setMucdo(listDataItem.get(position).getMucDo());
                updateWorkManageEvent.setStatus(listDataItem.get(position).getStatus());
                updateWorkManageEvent.setChuTri(listDataItem.get(position).getChuTri());
                updateWorkManageEvent.setEndDate(listDataItem.get(position).getEndDate());
                updateWorkManageEvent.setStatusDanhGia(listDataItem.get(position).getStatusDanhGia());
                updateWorkManageEvent.setNoidungDanhGia(listDataItem.get(position).getNoiDungDanhGia());
                EventBus.getDefault().postSticky(updateWorkManageEvent);


                switch (menu.getViewType()) {
//                        "Chưa hoàn thành"
                    case 0:
                        switch (index) {
                            case 0:
                                startActivity(new Intent(getActivity(), AddPersonWorkActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(getActivity(), EditInforWorkActivity.class));
                                break;
                            case 2:
                                startActivity(new Intent(getActivity(), HandlingProgressActivity.class));
                                break;
                        }
                        break;
//                        "Đã hoàn thành") || ("Chờ phê duyệt"||đã phê duyệt)
                    case 1:
                        switch (index) {
                            case 0:
                                startActivity(new Intent(getActivity(), WorkAssessActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(getActivity(), HandlingProgressActivity.class));
                                break;
                        }
                        break;
                    case 2:
                        switch (index) {
                            case 0:
                                startActivity(new Intent(getActivity(), UpdateStatusWorkActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(getActivity(), HandlingProgressActivity.class));
                                break;
                            case 2:
                                startActivity(new Intent(getActivity(), CreateSubTaskActivity.class));
                                break;
                        }
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), HandlingProgressActivity.class));
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onErrorGetListData(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            AlertDialogManager.showAlertDialog(getActivity(), getString(R.string.NETWORK_TITLE_ERROR),
                    getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        } else {
            AlertDialogManager.showAlertDialog(getActivity(), getString(R.string.NETWORK_TITLE_ERROR),
                    apiError.getMessage(), true, AlertDialogManager.ERROR);
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UpdateSuccessEvent event) {
        /* Do something */
        if (event != null && event.getSuccess() == 1) {
            pageNo = 1;
            if (listDataItem != null && listDataItem.size() > 0) {
                listDataItem.clear();
            }
            getApiListTask();
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
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }
}
