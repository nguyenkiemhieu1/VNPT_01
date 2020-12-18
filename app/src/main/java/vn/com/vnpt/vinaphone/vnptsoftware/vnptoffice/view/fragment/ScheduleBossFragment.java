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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.WeekBossAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleInfo;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleBossFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleBossFragment extends Fragment implements IScheduleView, ILoginView, IExceptionErrorView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.ngayHienTai)
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
    @BindView(R.id.btnChonNgay)
    ImageView btnChonNgay;
    @BindView(R.id.rcv_meeting_sang)
    RecyclerView rcv_meeting_sang;
    @BindView(R.id.rcv_meeting_chieu)
    RecyclerView rcv_meeting_chieu;
    @BindView(R.id.tvSang)
    TextView tvSang;
    @BindView(R.id.tvChieu)
    TextView tvChieu;
    @BindView(R.id.lichNull)
    TextView lichNull;
    @BindView(R.id.ln_lich)
    LinearLayout ln_lich;
    @BindView(R.id.nsv_layout)
    NestedScrollView nsv_layout;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private Calendar firstDayOfWeek;
    private Calendar lastDayOfWeek;
    private static SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_SCHEDULE);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ISchedulePresenter schedulePresenter = new SchedulePresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String strThu2, strThu3, strThu4, strThu5, strThu6, strThu7, strCN;
    private String paramDay;

    private OnFragmentInteractionListener mListener;

    public ScheduleBossFragment() {

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
    public static ScheduleBossFragment newInstance(String param1, String param2) {
        ScheduleBossFragment fragment = new ScheduleBossFragment();
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
        View view = inflater.inflate(R.layout.fragment_schedule_boss, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).showNotify(true);
        paramDay = getCurrentDay();
        ngayHienTai.setText(getString(R.string.tv_ngay_hop, paramDay));
        currentDay();
        numberWeek();
        appPrefs = Application.getApp().getAppPrefs();
        connectionDetector = new ConnectionDetector(getContext());
        progressDialog = new ProgressDialog(getContext());
        search(paramDay);

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
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void currentDay() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
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

    @OnClick({R.id.thu2, R.id.thu3, R.id.thu4, R.id.thu5, R.id.thu6, R.id.thu7, R.id.chunhat, R.id.btnChonNgay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.thu2:
                ngayHienTai.setText(getString(R.string.tv_ngay_hop, strThu2));
                changeColorButton(thu2, thu3, thu4, thu5, thu6, thu7, chunhat);
                search(strThu2);
                break;
            case R.id.thu3:
                search(strThu3);
                ngayHienTai.setText(getString(R.string.tv_ngay_hop, strThu3));
                changeColorButton(thu3, thu2, thu4, thu5, thu6, thu7, chunhat);
                break;
            case R.id.thu4:
                search(strThu4);
                ngayHienTai.setText(getString(R.string.tv_ngay_hop, strThu4));
                changeColorButton(thu4, thu3, thu2, thu5, thu6, thu7, chunhat);
                break;
            case R.id.thu5:
                search(strThu5);
                ngayHienTai.setText(getString(R.string.tv_ngay_hop, strThu5));
                changeColorButton(thu5, thu3, thu4, thu2, thu6, thu7, chunhat);
                break;
            case R.id.thu6:
                search(strThu6);
                ngayHienTai.setText(getString(R.string.tv_ngay_hop, strThu6));
                changeColorButton(thu6, thu3, thu4, thu5, thu2, thu7, chunhat);
                break;
            case R.id.thu7:
                search(strThu7);
                ngayHienTai.setText(getString(R.string.tv_ngay_hop, strThu7));
                changeColorButton(thu7, thu3, thu4, thu5, thu6, thu2, chunhat);
                break;
            case R.id.chunhat:
                search(strCN);
                ngayHienTai.setText(getString(R.string.tv_ngay_hop, strCN));
                changeColorButton(chunhat, thu3, thu4, thu5, thu6, thu7, thu2);
                break;
            case R.id.btnChonNgay:
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
                ngayHienTai.setText(getString(R.string.tv_ngay_hop, strDate));
                search(strDate);

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
        }, yy, mm, dd);
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

    private void search(String paramDay) {
        if (connectionDetector.isConnectingToInternet()) {
            schedulePresenter.getWeekSchedulesBoss(new ScheduleBossRequest(paramDay, paramDay));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            search(paramDay);
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
    public void onSuccess(List<ScheduleInfo> schedules) {

    }

    @Override
    public void onSuccessBoss(List<ScheduleBossInfo> schedules) {
        if (schedules == null) {
            ln_lich.setVisibility(View.GONE);
            lichNull.setVisibility(View.VISIBLE);
        } else {
            ln_lich.setVisibility(View.VISIBLE);
            lichNull.setVisibility(View.GONE);

            List<ScheduleInfo> listSang = new ArrayList<>();
            for (ScheduleBossInfo obj : schedules) {
                if (obj.getDataSang() != null && obj.getDataSang().size() > 0) {
                    for(int i =0; i< obj.getDataSang().size(); i++){
                        listSang.add(obj.getDataSang().get(i));
                    }
                }
            }

            List<ScheduleInfo> listChieu = new ArrayList<>();
            for (ScheduleBossInfo obj : schedules) {
                if (obj.getDataChieu() != null && obj.getDataChieu().size() > 0) {
                    for(int i =0; i< obj.getDataChieu().size(); i++){
                        listChieu.add(obj.getDataChieu().get(i));
                    }
                }
            }

            //dataSang List
            if (listSang.isEmpty()) {
                tvSang.setVisibility(View.GONE);
                rcv_meeting_sang.setVisibility(View.GONE);
            } else {
                tvSang.setVisibility(View.VISIBLE);
                rcv_meeting_sang.setVisibility(View.VISIBLE);
                WeekBossAdapter adapterSang = new WeekBossAdapter(getContext(), listSang);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                rcv_meeting_sang.setLayoutManager(mLayoutManager);
                rcv_meeting_sang.setItemAnimator(new DefaultItemAnimator());
                rcv_meeting_sang.setAdapter(adapterSang);
                rcv_meeting_sang.setNestedScrollingEnabled(false);
                adapterSang.notifyDataSetChanged();
            }

            //dataChieu List
            if (listChieu.isEmpty()) {
                tvChieu.setVisibility(View.GONE);
                rcv_meeting_chieu.setVisibility(View.GONE);
            } else {
                tvChieu.setVisibility(View.VISIBLE);
                rcv_meeting_chieu.setVisibility(View.VISIBLE);
                WeekBossAdapter adapterChieu = new WeekBossAdapter(getContext(), listChieu);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                rcv_meeting_chieu.setLayoutManager(layoutManager);
                rcv_meeting_chieu.setItemAnimator(new DefaultItemAnimator());
                rcv_meeting_chieu.setAdapter(adapterChieu);
                rcv_meeting_chieu.setNestedScrollingEnabled(false);
                adapterChieu.notifyDataSetChanged();
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
