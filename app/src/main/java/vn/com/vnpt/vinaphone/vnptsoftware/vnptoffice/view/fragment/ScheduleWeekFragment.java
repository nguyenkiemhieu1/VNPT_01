package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.ScheduleWeekAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SpinnerScheduleDepartAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SpinnerScheduleWeekAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChairScheduleWeekInfor;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule.ISchedulePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule.SchedulePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.BaseActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleWeekView;


public class ScheduleWeekFragment extends Fragment implements IScheduleWeekView, ILoginView, IExceptionErrorView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_date_now)
    TextView ngayHienTai;
    @BindView(R.id.thu2)
    TextView thu2;
    @BindView(R.id.thu3)
    TextView thu3;
    @BindView(R.id.thu4)
    TextView thu4;
    @BindView(R.id.thu5)
    TextView thu5;
    @BindView(R.id.thu6)
    TextView thu6;
    @BindView(R.id.thu7)
    TextView thu7;
    @BindView(R.id.chunhat)
    TextView chunhat;
    @BindView(R.id.spinner_select)
    Spinner spinnerSelect;
    @BindView(R.id.tv_NoData)
    TextView tv_NoData;
    @BindView(R.id.recycler_schedule_week)
    RecyclerView recycler_schedule_week;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private Calendar firstDayOfWeek;
    private Calendar lastDayOfWeek;
    private static SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_SCHEDULE);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ISchedulePresenter schedulePresenter = new SchedulePresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;
    private ScheduleWeekAdapter scheduleWeekAdapter;
    RecyclerView.LayoutManager layoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String strThu2, strThu3, strThu4, strThu5, strThu6, strThu7, strCN;
    private String dateSelect;
    private String KeyChair = "";

    private OnFragmentInteractionListener mListener;

    public ScheduleWeekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleBossFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleWeekFragment newInstance(String param1, String param2) {
        ScheduleWeekFragment fragment = new ScheduleWeekFragment();
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
        View view = inflater.inflate(R.layout.fragment_schedule_week, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateSelect = getCurrentDay();
        Init();


    }

    private void Init() {
        ((MainActivity) getActivity()).showNotify(false);
        currentDay();
        numberWeek();
        appPrefs = Application.getApp().getAppPrefs();
        connectionDetector = new ConnectionDetector(getContext());
        progressDialog = new ProgressDialog(getContext());

        layoutManager = new LinearLayoutManager(getContext());
        recycler_schedule_week.setLayoutManager(layoutManager);
        recycler_schedule_week.setHasFixedSize(true);
        getListChair();

    }

    private void setTitle(int day, int mouth, int year) {
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, mouth);//0- january ..4-May
        calender.set(Calendar.DAY_OF_MONTH, day);
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setTitle("Lịch tuần " + calender.get(Calendar.WEEK_OF_YEAR));
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void numberWeek() {
        // Lấy ngày đầu tuần, cuối tuần
        firstDayOfWeek = Calendar.getInstance();
        while (firstDayOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            firstDayOfWeek.add(Calendar.DATE, -1);
        }
        firstDayOfWeek.set(Calendar.HOUR_OF_DAY, 8);
        firstDayOfWeek.set(Calendar.MINUTE, 0);
        lastDayOfWeek = Calendar.getInstance();
        if (lastDayOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            while (lastDayOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                lastDayOfWeek.add(Calendar.DATE, 1);
            }
        } else {
        }
        lastDayOfWeek.set(Calendar.HOUR_OF_DAY, 17);
        lastDayOfWeek.set(Calendar.MINUTE, 0);
        String txtDay = (sdf.format(firstDayOfWeek.getTime()) + " - " + sdf.format(lastDayOfWeek.getTime()));


        // Lấy danh sách các ngày trong tuần
        Calendar c = Calendar.getInstance();
        c.setTime(firstDayOfWeek.getTime());
        strThu2 = sdf.format(c.getTime());

        c.add(Calendar.DATE, 1);
        strThu3 = sdf.format(c.getTime());

        c.add(Calendar.DATE, 1);
        strThu4 = sdf.format(c.getTime());

        c.add(Calendar.DATE, 1);
        strThu5 = sdf.format(c.getTime());

        c.add(Calendar.DATE, 1);
        strThu6 = sdf.format(c.getTime());

        c.add(Calendar.DATE, 1);
        strThu7 = sdf.format(c.getTime());

        c.add(Calendar.DATE, 1);
        strCN = sdf.format(c.getTime());

        String listNgay = strThu2 + "-" + strThu3 + "-" + strThu4 + "-" + strThu5 + "-" + strThu6 + "-" + strThu7 + "-" + strCN;
        ngayHienTai.setText(strThu2 + " - " + strCN);
    }

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


    private void currentDay() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        setTitle(c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
        setColor(dayOfWeek);
    }

    private void setColor(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                changeColorButton(thu2, thu3, thu4, thu5, thu6, thu7, chunhat);
                break;
            case Calendar.TUESDAY:
                changeColorButton(thu3, thu2, thu4, thu5, thu6, thu7, chunhat);
                break;
            case Calendar.WEDNESDAY:
                changeColorButton(thu4, thu3, thu2, thu5, thu6, thu7, chunhat);
                break;
            case Calendar.THURSDAY:
                changeColorButton(thu5, thu3, thu4, thu2, thu6, thu7, chunhat);
                break;
            case Calendar.FRIDAY:
                changeColorButton(thu6, thu3, thu4, thu5, thu2, thu7, chunhat);
                break;
            case Calendar.SATURDAY:
                changeColorButton(thu7, thu3, thu4, thu5, thu6, thu2, chunhat);
                break;
            case Calendar.SUNDAY:
                changeColorButton(chunhat, thu3, thu4, thu5, thu6, thu7, thu2);
                break;
        }
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

    @OnClick({R.id.thu2, R.id.thu3, R.id.thu4, R.id.thu5, R.id.thu6, R.id.thu7, R.id.chunhat, R.id.tv_date_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.thu2:
                changeColorButton(thu2, thu3, thu4, thu5, thu6, thu7, chunhat);
                dateSelect = strThu2;
                getListScheduleWeek(strThu2, KeyChair);
                break;
            case R.id.thu3:
                dateSelect = strThu3;
                getListScheduleWeek(strThu3, KeyChair);
                changeColorButton(thu3, thu2, thu4, thu5, thu6, thu7, chunhat);
                break;
            case R.id.thu4:
                dateSelect = strThu4;
                getListScheduleWeek(strThu4, KeyChair);
                changeColorButton(thu4, thu3, thu2, thu5, thu6, thu7, chunhat);
                break;
            case R.id.thu5:
                dateSelect = strThu5;
                getListScheduleWeek(strThu5, KeyChair);
                changeColorButton(thu5, thu3, thu4, thu2, thu6, thu7, chunhat);
                break;
            case R.id.thu6:
                dateSelect = strThu6;
                getListScheduleWeek(strThu6, KeyChair);
                changeColorButton(thu6, thu3, thu4, thu5, thu2, thu7, chunhat);
                break;
            case R.id.thu7:
                dateSelect = strThu7;
                getListScheduleWeek(strThu7, KeyChair);
                changeColorButton(thu7, thu3, thu4, thu5, thu6, thu2, chunhat);
                break;
            case R.id.chunhat:
                dateSelect = strCN;
                getListScheduleWeek(strCN, KeyChair);
                changeColorButton(chunhat, thu3, thu4, thu5, thu6, thu7, thu2);
                break;
            case R.id.tv_date_now:
                getAnyDay();
                break;
        }

    }

    private void changeColorButton(TextView t2, TextView t3, TextView t4, TextView t5, TextView t6, TextView t7, TextView cn) {
        t2.setBackgroundResource(R.drawable.border_btn_search);
        t3.setBackgroundResource(R.color.md_cyan_500);
        t4.setBackgroundResource(R.color.md_cyan_500);
        t5.setBackgroundResource(R.color.md_cyan_500);
        t6.setBackgroundResource(R.color.md_cyan_500);
        t7.setBackgroundResource(R.color.md_cyan_500);
        cn.setBackgroundResource(R.color.md_cyan_500);
    }

    private void getAnyDay() {
        int yy = 0;
        int mm = 0;
        int dd = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = format.parse(dateSelect);
            dd = Integer.parseInt((String) DateFormat.format("dd", date));
            mm = Integer.parseInt((String) DateFormat.format("MM", date));
            yy = Integer.parseInt((String) DateFormat.format("yyyy", date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                dateSelect = strDate;
                setTitle(dayOfMonth, monthOfYear, year);
                getListScheduleWeek(strDate, KeyChair);

                try {
                    getListAnyDate(strDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //set lại màu cho thứ sau khi chọn ngày bất kỳ
                Date date = null;
                try {
                    date = sdf.parse(strDate);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);

                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    setColor(dayOfWeek);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, yy, mm - 1, dd);
        datePicker.show();


    }

    private void getListAnyDate(String strDate) throws ParseException {
        Date[] days = getDaysOfWeek(strDate, Calendar.getInstance().getFirstDayOfWeek());
        strThu2 = sdf.format(days[0]);
        strThu3 = sdf.format(days[1]);
        strThu4 = sdf.format(days[2]);
        strThu5 = sdf.format(days[3]);
        strThu6 = sdf.format(days[4]);
        strThu7 = sdf.format(days[5]);
        strCN = sdf.format(days[6]);
        ngayHienTai.setText(strThu2 + " - " + strCN);
    }

    private static Date[] getDaysOfWeek(String strDate, int firstDayOfWeek) throws ParseException {
        Date date = sdf.parse(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
        Date[] daysOfWeek = new Date[7];
        for (int i = 0; i < 7; i++) {
            daysOfWeek[i] = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return daysOfWeek;
    }

    private void getListScheduleWeek(String paramDay, String key) {
        if (connectionDetector.isConnectingToInternet()) {
            schedulePresenter.getScheduleWeek(new ScheduleWeekRequest(paramDay, paramDay, key));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getListChair() {
        if (connectionDetector.isConnectingToInternet()) {
            schedulePresenter.getChairScheduleWeek();
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void setupSpinnerDepart(List<ChairScheduleWeekInfor> listChair) {

        SpinnerScheduleWeekAdapter myAdapter = new SpinnerScheduleWeekAdapter(getActivity(), 0, listChair);
        spinnerSelect.setAdapter(myAdapter);
        spinnerSelect.setSelection(0);
        spinnerSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                KeyChair = listChair.get(i).getUserId();
                getListScheduleWeek(dateSelect, KeyChair);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            getListChair();
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
    public void onSuccess(List<ScheduleWeekInfo> scheduleWeekInfos) {
        if (scheduleWeekInfos != null && scheduleWeekInfos.size() > 0) {
            recycler_schedule_week.setVisibility(View.VISIBLE);
            tv_NoData.setVisibility(View.GONE);
            scheduleWeekAdapter = new ScheduleWeekAdapter(getContext(), scheduleWeekInfos);
            recycler_schedule_week.setAdapter(scheduleWeekAdapter);
        } else {
            recycler_schedule_week.setVisibility(View.GONE);
            tv_NoData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onGetChairSuccess(List<ChairScheduleWeekInfor> listChair) {
        if (listChair != null && listChair.size() > 0) {
            KeyChair = listChair.get(0).getUserId();
            setupSpinnerDepart(listChair);
        }
        getListScheduleWeek(dateSelect, KeyChair);
    }


    @Override
    public void onCreateScheduleWeekSuccess() {

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
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_SCHEDULE_BOSS_ERROR), true, AlertDialogManager.ERROR);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
