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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.ScheduleBossDepartInfoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SpinnerScheduleDepartAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleDepartmentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DepartmentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleDepartBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule.IScheduleDepartPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule.ScheduleDepartPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.SpinnerScheduleDepart;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleDepartView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleDepartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleDepartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleDepartFragment extends Fragment implements IScheduleDepartView, ILoginView, IExceptionErrorView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.sNhom)
    Spinner sNhom;
    @BindView(R.id.tuanHienTai)
    TextView tuanHienTai;
    @BindView(R.id.btnChonNgay)
    ImageView btnChonNgay;
    @BindView(R.id.rcv_schedule_depart)
    RecyclerView rcv_schedule_depart;
    @BindView(R.id.lichNull)
    TextView lichNull;
    @BindView(R.id.ln_lich)
    LinearLayout ln_lich;
    @BindView(R.id.ln_sangchieu)
    LinearLayout ln_sangchieu;
    private static SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_SCHEDULE);
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;
    private ApplicationSharedPreferences appPrefs;
    private List<DepartmentInfo> departmentList;
    private List<SpinnerScheduleDepart> lst;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private IScheduleDepartPresenter scheduleDepartPresenter = new ScheduleDepartPresenterImpl(this);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String paramDay;
    private String startDate;
    private String endDate;
    private String id;
    private boolean Endprogess = false;

    private OnFragmentInteractionListener mListener;

    public ScheduleDepartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleDepartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleDepartFragment newInstance(String param1, String param2) {
        ScheduleDepartFragment fragment = new ScheduleDepartFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_depart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        paramDay = getCurrentDay();
        tuanHienTai.setText(getString(R.string.tv_ngay_hop, "Tuần " + calculateWeekNumber(paramDay)));
        try {
            startDate = getFirstDayLastDayofWeek(paramDay).split("#")[0];
            endDate = getFirstDayLastDayofWeek(paramDay).split("#")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        appPrefs = Application.getApp().getAppPrefs();
        connectionDetector = new ConnectionDetector(getActivity());
        progressDialog = new ProgressDialog(getContext());

        scheduleDepartPresenter.getDepartments();
        Endprogess=false;// để = false để khi thực hiện xong sẽ ko ẩn dialog đi nữa vì vẫn cần bật dialog để load tiếp dữ liệu
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

    private String getCurrentDay() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
//        Date currentTime = localCalendar.getTime();
        int currentDay = localCalendar.get(Calendar.DATE);
        int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
        int currentYear = localCalendar.get(Calendar.YEAR);

        String strDay = "";
        String strMonth = "";
        if (currentDay < 10) {
            strDay = "0" + String.valueOf(currentDay);
        } else {
            strDay = String.valueOf(currentDay);
        }

        if (currentMonth < 10) {
            strMonth = "0" + String.valueOf(currentMonth);
        } else {
            strMonth = String.valueOf(currentMonth);
        }

        return strDay + "/" + strMonth + "/" + currentYear;
    }

    private void getAnyDay() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                //Lấy ngày sau khi chọn datepicker
                String strDay = "";
                String strMonth = "";
                if (dayOfMonth < 10) {
                    strDay = "0" + String.valueOf(dayOfMonth);
                } else {
                    strDay = String.valueOf(dayOfMonth);
                }
                if (monthOfYear < 9) {
                    strMonth = "0" + String.valueOf(monthOfYear + 1);
                } else {
                    strMonth = String.valueOf(monthOfYear + 1);
                }
                String strDate = String.valueOf(strDay) + "/" + String.valueOf(strMonth)
                        + "/" + String.valueOf(year);
                tuanHienTai.setText(getString(R.string.tv_ngay_hop, "Tuần " + calculateWeekNumber(strDate)));
                try {
                    startDate = getFirstDayLastDayofWeek(strDate).split("#")[0];
                    endDate = getFirstDayLastDayofWeek(strDate).split("#")[1];
                    getScheduleDepart(startDate, endDate, id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, yy, mm, dd);
        datePicker.show();
    }

    private void getScheduleDepart(String startDate, String endDate, String id) {
        if (connectionDetector.isConnectingToInternet()) {
            scheduleDepartPresenter.getScheduleDepartments(new ScheduleDepartmentRequest(startDate, endDate, id));
            Endprogess=true;// thực hiện load dữ liệu ở lần cuối cùng nên sẽ cho phép tắt dialog
        } else {
            AlertDialogManager.showAlertDialog(getActivity(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private int calculateWeekNumber(String strDate) {
        int week = 0;
        try {
            Date date = sdf.parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            week = cal.get(Calendar.WEEK_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return week;
    }

    private String getFirstDayLastDayofWeek(String paramDay) {
        String monday = "", sunday = "";
        try {
            Date date = sdf.parse(paramDay);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                cal.add(Calendar.DATE, -1);
            }
            Date firstDay = cal.getTime();
            monday = sdf.format(firstDay);
            cal.add(Calendar.DATE, 6);
            Date lastDay = cal.getTime();
            sunday = sdf.format(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String startDatevsendDate = monday + "#" + sunday;
        return startDatevsendDate;
    }


    @OnClick({R.id.btnChonNgay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnChonNgay:
                getAnyDay();
                break;
        }
    }


    private void displayDepart() {
        lst = new ArrayList<>();
        //lst.add(new SpinnerScheduleDepart(getString(R.string.DEFAULT_DEPART), null));
        if (departmentList != null && departmentList.size() > 0) {
            for (int i = 0; i < departmentList.size(); i++) {
                lst.add(new SpinnerScheduleDepart(departmentList.get(i).getName(), departmentList.get(i).getId()));
            }
        }
        setupSpinnerDepart();
    }

    private void setupSpinnerDepart() {
        SpinnerScheduleDepartAdapter myAdapter = new SpinnerScheduleDepartAdapter(getActivity(), 0, lst);
        sNhom.setAdapter(myAdapter);
        sNhom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id = lst.get(i).getId();
                getScheduleDepart(startDate, endDate, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }

    @Override
    public void onSuceessDepart(List<DepartmentInfo> departments) {
        departmentList = departments;
        displayDepart();
    }

    @Override
    public void onSuccessScheduleDepart(List<ScheduleDepartBossInfo> schedules) {
        if (schedules == null) {
            ln_lich.setVisibility(View.GONE);
            ln_sangchieu.setVisibility(View.GONE);
            lichNull.setVisibility(View.VISIBLE);
        } else {
            ln_sangchieu.setVisibility(View.VISIBLE);
            ln_lich.setVisibility(View.VISIBLE);
            lichNull.setVisibility(View.GONE);
            ScheduleBossDepartInfoAdapter adapterSang = new ScheduleBossDepartInfoAdapter(getContext(), schedules);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rcv_schedule_depart.setLayoutManager(mLayoutManager);
            rcv_schedule_depart.setItemAnimator(new DefaultItemAnimator());
            rcv_schedule_depart.setAdapter(adapterSang);
            rcv_schedule_depart.setNestedScrollingEnabled(true);
            adapterSang.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_SCHEDULE_DEPART_ERROR), true, AlertDialogManager.ERROR);
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
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            getScheduleDepart(startDate, endDate, id);
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
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
        //check xem dialog nếu hiện rồi thì ko cho hiện
        if (progressDialog != null && progressDialog.isShowing() == false) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing() && Endprogess == true) {
            progressDialog.dismiss();
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
}
