package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import retrofit2.http.GET;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.SelectPersonScheduleWeekAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.WeekBossAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CreateScheduleWeekRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChairScheduleWeekInfor;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule.ISchedulePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.schedule.SchedulePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.BaseActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailDocumentWaitingActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.SelectPersonScheduleWeekActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.AddAndDeletePersonScheduleEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocWaitingtEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SelectGroupSendDocEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IScheduleWeekView;


public class ScheduleRegisterWeekFragment extends Fragment implements IScheduleWeekView, ILoginView, IExceptionErrorView {

    @BindView(R.id.tv_date_now)
    TextView tv_date_now;
    @BindView(R.id.tv_date_start)
    TextView tv_date_start;
    @BindView(R.id.tv_date_end)
    TextView tv_date_end;
    @BindView(R.id.edt_content)
    EditText edt_content;
    @BindView(R.id.edt_location)
    EditText edt_location;
    @BindView(R.id.edt_prepare)
    EditText edt_prepare;
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
    @BindView(R.id.recycler_view_chair)
    RecyclerView recyclerViewChair;
    @BindView(R.id.recycler_view_join)
    RecyclerView recyclerViewJoin;
    @BindView(R.id.imageAddChair)
    ImageView imageAddChair;
    @BindView(R.id.imageAddJoin)
    ImageView imageAddJoin;
    @BindView(R.id.btn_create)
    Button btn_create;

    private ArrayList<PersonAndUnitScheduleWeekInfo> personJoinList = new ArrayList<PersonAndUnitScheduleWeekInfo>();
    private ArrayList<PersonAndUnitScheduleWeekInfo> personCharList = new ArrayList<PersonAndUnitScheduleWeekInfo>();

    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private Calendar firstDayOfWeek;
    private Calendar lastDayOfWeek;
    private static SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_SCHEDULE);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ISchedulePresenter schedulePresenter = new SchedulePresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private SelectPersonScheduleWeekAdapter adapterChair, adapterJoin;
    RecyclerView.LayoutManager layoutManagerChair, layoutManagerJoin;

    // TODO: Rename and change types of parameters
    private String strThu2, strThu3, strThu4, strThu5, strThu6, strThu7, strCN, dateSelect;

    private OnFragmentInteractionListener mListener;

    public ScheduleRegisterWeekFragment() {

    }

    public static ScheduleRegisterWeekFragment newInstance() {
        ScheduleRegisterWeekFragment fragment = new ScheduleRegisterWeekFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_week_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();

    }

    private void Init() {
        ((MainActivity) getActivity()).showNotify(false);
        dateSelect = getCurrentDay();
        currentDay();
        numberWeek();
        appPrefs = Application.getApp().getAppPrefs();
        connectionDetector = new ConnectionDetector(getContext());

        layoutManagerChair = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManagerJoin = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        recyclerViewChair.setHasFixedSize(false);
        recyclerViewChair.setNestedScrollingEnabled(false);
        recyclerViewChair.setLayoutManager(layoutManagerChair);

        recyclerViewJoin.setHasFixedSize(false);
        recyclerViewJoin.setNestedScrollingEnabled(false);
        recyclerViewJoin.setLayoutManager(layoutManagerJoin);
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
        tv_date_now.setText(strThu2 + " - " + strCN);
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeStickyEvent(AddAndDeletePersonScheduleEvent.class);
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

    @OnClick({R.id.thu2, R.id.thu3
            , R.id.thu4, R.id.thu5
            , R.id.thu6, R.id.thu7
            , R.id.chunhat, R.id.tv_date_now
            , R.id.tv_date_start, R.id.tv_date_end
            , R.id.imageAddChair, R.id.imageAddJoin
            , R.id.btn_create})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.thu2:
                dateSelect = getString(R.string.tv_ngay_hop, strThu2);
                changeColorButton(thu2, thu3, thu4, thu5, thu6, thu7, chunhat);

                break;
            case R.id.thu3:
                dateSelect = getString(R.string.tv_ngay_hop, strThu3);
                changeColorButton(thu3, thu2, thu4, thu5, thu6, thu7, chunhat);
                break;
            case R.id.thu4:
                dateSelect = getString(R.string.tv_ngay_hop, strThu4);
                changeColorButton(thu4, thu3, thu2, thu5, thu6, thu7, chunhat);
                break;
            case R.id.thu5:
                dateSelect = getString(R.string.tv_ngay_hop, strThu5);
                changeColorButton(thu5, thu3, thu4, thu2, thu6, thu7, chunhat);
                break;
            case R.id.thu6:
                dateSelect = getString(R.string.tv_ngay_hop, strThu6);
                changeColorButton(thu6, thu3, thu4, thu5, thu2, thu7, chunhat);
                break;
            case R.id.thu7:
                dateSelect = getString(R.string.tv_ngay_hop, strThu7);
                changeColorButton(thu7, thu3, thu4, thu5, thu6, thu2, chunhat);
                break;
            case R.id.chunhat:
                dateSelect = getString(R.string.tv_ngay_hop, strCN);
                changeColorButton(chunhat, thu3, thu4, thu5, thu6, thu7, thu2);
                break;
            case R.id.tv_date_now:
                getAnyDay();
                break;
            case R.id.tv_date_start:
                getTimeStart();
                break;
            case R.id.tv_date_end:
                getTimeEnd();
                break;
            case R.id.btn_create:
                createScheduleWeek();
                break;

            case R.id.imageAddChair:
                AddAndDeletePersonScheduleEvent event = new AddAndDeletePersonScheduleEvent(null, null);
                event.setType("CHAIR");
                if (personCharList != null && personCharList.size() > 0) {
                    event.setListSelectPesonAndUnit(personCharList);
                }
                EventBus.getDefault().postSticky(event);
                Intent intent = new Intent(getContext(), SelectPersonScheduleWeekActivity.class);
                startActivity(intent);
                break;
            case R.id.imageAddJoin:
                AddAndDeletePersonScheduleEvent event1 = new AddAndDeletePersonScheduleEvent(null, null);
                event1.setType("JOIN");
                if (personJoinList != null && personJoinList.size() > 0) {
                    event1.setListSelectPesonAndUnit(personJoinList);
                }
                EventBus.getDefault().postSticky(event1);
                Intent intent1 = new Intent(getContext(), SelectPersonScheduleWeekActivity.class);
                startActivity(intent1);
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

    @SuppressLint("NewApi")
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
                setTitle(dayOfMonth, monthOfYear, year);
                dateSelect = strDate;
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


    private void getTimeStart() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour, minute;
                if (selectedHour < 10) {
                    hour = "0" + selectedHour;
                } else {
                    hour = "" + selectedHour;
                }
                if (selectedMinute < 10) {
                    minute = "0" + selectedMinute;
                } else {
                    minute = "" + selectedMinute;
                }

                String timeStart = hour + ":" + minute;

                String timeEnd = tv_date_end.getText().toString();
                if (timeEnd.length() > 0) {
                    if (compareTime(timeStart, timeEnd, "START")) {
                        tv_date_start.setText(timeStart);
                    }
                } else {
                    tv_date_start.setText(timeStart);
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void getTimeEnd() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour, minute;
                if (selectedHour < 10) {
                    hour = "0" + selectedHour;
                } else {
                    hour = "" + selectedHour;
                }
                if (selectedMinute < 10) {
                    minute = "0" + selectedMinute;
                } else {
                    minute = "" + selectedMinute;
                }

                String timeEnd = hour + ":" + minute;

                String timeStart = tv_date_start.getText().toString();
                if (timeStart.length() > 0) {
                    if (compareTime(timeStart, timeEnd, "END")) {
                        tv_date_end.setText(timeEnd);
                    }
                } else {
                    tv_date_end.setText(timeEnd);
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private boolean compareTime(String timeStart, String timeEnd, String typeClick) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date dateTimeStart = sdf.parse(timeStart);
            Date dateTimeEnd = sdf.parse(timeEnd);
            Log.v("okhttp", "toanpro:====" + dateTimeStart.compareTo(dateTimeEnd));
            if (typeClick.equals("START")) {
                if (dateTimeStart.compareTo(dateTimeEnd) == -1) {
                    return true;
                } else {
                    AlertDialogManager.showAlertDialog(getContext(), getString(R.string.str_dialog_thongbao), getString(R.string.TIME_START_ERORR), true, AlertDialogManager.ERROR);
                    return false;
                }

            } else {

                if (dateTimeEnd.compareTo(dateTimeStart) == 1) {
                    return true;
                } else {
                    AlertDialogManager.showAlertDialog(getContext(), getString(R.string.str_dialog_thongbao), getString(R.string.TIME_END_ERORR), true, AlertDialogManager.ERROR);
                    return false;
                }
            }

        } catch (ParseException e) {
            // Exception handling goes here
        }
        return false;

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
        tv_date_now.setText(strThu2 + " - " + strCN);
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

    private void createScheduleWeek() {
        String timeStart = "";
        if (tv_date_start.getText().toString().length() > 0) {
            timeStart = dateSelect + " " + tv_date_start.getText().toString();
        }
        String timeEnd = "";
        if (tv_date_end.getText().toString().length() > 0) {
            timeEnd = dateSelect + " " + tv_date_end.getText().toString();
        }
        String content = edt_content.getText().toString();
        String location = edt_location.getText().toString();
        String prepare = edt_prepare.getText().toString();
        String chairRequest = "";
        String joinRequest = "";
        String nameChairRequest = "";
        String nameJoinRequest = "";
        if (personCharList != null && personCharList.size() > 0) {
            for (PersonAndUnitScheduleWeekInfo person : personCharList) {
                if (person != null && person.getId() != null) {
                    if (chairRequest.length() > 0) {
                        chairRequest = chairRequest + "," + person.getId();
                        nameChairRequest = nameChairRequest + ", " + person.getName();
                    } else {
                        chairRequest = person.getId();
                        nameChairRequest = person.getName();
                    }

                }
            }
        }
        if (personJoinList != null && personJoinList.size() > 0) {
            for (PersonAndUnitScheduleWeekInfo person : personJoinList) {
                if (person != null && person.getId() != null) {
                    if (joinRequest.length() > 0) {
                        joinRequest = joinRequest + "," + person.getId();
                        nameJoinRequest = nameJoinRequest + ", " + person.getName();
                    } else {
                        joinRequest = person.getId();
                        nameJoinRequest = person.getName();
                    }

                }
            }
        }

        if (content.trim().length() == 0) {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.str_dialog_thongbao), getString(R.string.INPUT_SCHEDULE_WEEK_ERORR), true, AlertDialogManager.ERROR);
            return;
        }
        CreateScheduleWeekRequest createScheduleWeekRequest = new CreateScheduleWeekRequest(dateSelect, chairRequest, timeStart, timeEnd
                , content, location, prepare, joinRequest, nameChairRequest, nameJoinRequest);

        if (connectionDetector.isConnectingToInternet()) {
            schedulePresenter.createScheduleWeek(createScheduleWeekRequest);
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
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

    }

    @Override
    public void onGetChairSuccess(List<ChairScheduleWeekInfor> listChair) {

    }

    @Override
    public void onCreateScheduleWeekSuccess() {
        Toast.makeText(getContext(), getContext().getString(R.string.CREATE_SCHEDULE_WEEK_SUCCESS), Toast.LENGTH_LONG).show();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new ScheduleWeekFragment()).commit();
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
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), apiError.getMessage(), true, AlertDialogManager.ERROR);
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(AddAndDeletePersonScheduleEvent event) {
        if (event != null && event.getType() != null && event.getListSelectPesonAndUnit() != null) {
            if (event.getListSelectPesonAndUnit().size() > 0) {
                if (event.getType().equals("CHAIR")) {
                    personCharList = event.getListSelectPesonAndUnit();
                    adapterChair = new SelectPersonScheduleWeekAdapter(getContext(), personCharList);
                    recyclerViewChair.setAdapter(adapterChair);
                } else {
                    personJoinList = event.getListSelectPesonAndUnit();
                    adapterJoin = new SelectPersonScheduleWeekAdapter(getContext(), personJoinList);
                    recyclerViewJoin.setAdapter(adapterJoin);

                }
            } else
                Log.v("okhttp", "sackmkkdmd");
        }
    }
}
