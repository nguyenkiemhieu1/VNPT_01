package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.AttachFileCongViecAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.AttachFileMeetingAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManagerFinish;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Event;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailJobManageResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobAssignRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListStatusComboxRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ListFileCongViecResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.IWorkManagePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.WorkManagePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KiThanhCongEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListFileCongViecEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UpdateSuccessEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UpdateWorkManageEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IJobUpdateView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IListFileCongViecView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkDetailView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkManageView;

public class EditInforWorkActivity extends BaseActivity implements IWorkManageView, IJobUpdateView, IListFileCongViecView,
        IWorkDetailView, IExceptionErrorView {

    private Toolbar toolbar;
    private ImageView btnBack;

    @BindView(R.id.tv_from)
    TextView tv_from;
    @BindView(R.id.edt_name_work)
    EditText edt_name_work;
    @BindView(R.id.edt_content_work)
    EditText edt_content_work;
    @BindView(R.id.spin_work_level)
    Spinner spin_work_level;
    @BindView(R.id.layout_file_attach)
    LinearLayout layout_file_attach;

    private String statusWork = "";
    private String text_spinner = "";
    private String id = "";
    private String status = "";
    private List<GetListJobAssignRespone.Data> listDataItem = new ArrayList<>();
    IWorkManagePresenter iWorkManagePresenter = new WorkManagePresenterImpl(this,this, this, this);

    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_infor_work);
        ButterKnife.bind(this);
        appPrefs = Application.getApp().getAppPrefs();
        setupToolbar();
        getListStatus();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(KiThanhCongEvent kiThanhCongEvent) {
    }

    private void getListStatus() {
        UpdateWorkManageEvent event = EventBus.getDefault().getStickyEvent(UpdateWorkManageEvent.class);
        id = event.getId();
        text_spinner = event.getMucdo();

        iWorkManagePresenter.getListFile(id);
        iWorkManagePresenter.getDetailAssignWork(id);
        iWorkManagePresenter.getListStatusCombox("MUCDOCONGVIEC");
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.btnBack, R.id.btn_save, R.id.btn_close, R.id.btn_date})
    public void submit(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                this.finish();
                break;
            case R.id.btn_close:
                this.finish();
                break;
            case R.id.btn_date:
                getAnyDay();
                break;
            case R.id.btn_save:
                if(edt_name_work.getText().toString().trim().isEmpty()){
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                            getString(R.string.text_tencongviec), true, AlertDialogManager.WARNING);
                    return;
                }

                if(edt_content_work.getText().toString().trim().isEmpty()){
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                            getString(R.string.text_danhgia), true, AlertDialogManager.WARNING);
                    return;
                }

                UpdateCongViecRequest updateCongViecRequest = new UpdateCongViecRequest();
                updateCongViecRequest.setId(id);
                updateCongViecRequest.setName(edt_name_work.getText().toString());
                updateCongViecRequest.setContent(edt_content_work.getText().toString());
                updateCongViecRequest.setExpireDate(tv_from.getText().toString());

                ListFileCongViecEvent list = EventBus.getDefault().getStickyEvent(ListFileCongViecEvent.class);
                if (list!=null)
                updateCongViecRequest.setListFile(list.getFileId());

                updateCongViecRequest.setPriority(statusWork);
                updateCongViecRequest.setStatus(status);

                iWorkManagePresenter.updateJob(updateCongViecRequest);

                break;
        }
    }

    private void getAnyDay() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
                tv_from.setText(strDate);

            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePicker.show();
    }

    @Override
    public void onSuccessUpdateJob() {
        EventBus.getDefault().postSticky(new UpdateSuccessEvent(1));
        AlertDialogManagerFinish.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                getString(R.string.TITLE_SUCCESS), true, AlertDialogManager.SUCCESS, this);
    }

    @Override
    public void onSuccessGetListData(List<GetListJobAssignRespone.Data> listData) {
    }

    @Override
    public void onSuccessGetListStatus(List<GetListStatusComboxRespone.Data> listData) {
        if (listData != null && listData.size() > 0) {
            initDataSpinner(listData);
        }
    }

    @Override
    public void onSuccessGetDetailData(DetailJobManageResponse.Data data) {
        if (data != null) {
            edt_name_work.setText(data.getName());
            edt_content_work.setText(data.getNoiDung());
            tv_from.setText(data.getNgayHetHan());
        }
    }

    @Override
    public void onErrorGetListData(APIError apiError) {
        sendExceptionError(apiError);
    }

    @Override
    public void onSuccessListFile(List<ListFileCongViecResponse.FilesObj> listData) {
        if (listData != null && listData.size() > 0) {
            List<String> arr = new ArrayList<>();

            for(ListFileCongViecResponse.FilesObj obj : listData){
                arr.add(obj.getId());
            }
            EventBus.getDefault().postSticky(new ListFileCongViecEvent(arr));

            layout_file_attach.setVisibility(View.VISIBLE);
            AttachFileCongViecAdapter attachFileAdapter = new AttachFileCongViecAdapter(this, R.layout.item_file_list_congviec, listData);
            int adapterCount = attachFileAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = attachFileAdapter.getView(i, null, null);
                layout_file_attach.addView(item);
            }
        }
    }

    @Override
    public void onErrorListFile(APIError apiError) {
        sendExceptionError(apiError);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
       hideProgressDialog();
    }

    private void initDataSpinner(List<GetListStatusComboxRespone.Data> listData) {
        final ArrayAdapter<GetListStatusComboxRespone.Data> adapter = new ArrayAdapter<GetListStatusComboxRespone.Data>
                (this, android.R.layout.simple_spinner_item, listData) {
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
        spin_work_level.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spin_work_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                GetListStatusComboxRespone.Data dataItem = (GetListStatusComboxRespone.Data) spin_work_level.getSelectedItem();
                if (dataItem.getValue() != -1) {
                    statusWork = String.valueOf(dataItem.getValue());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        for (GetListStatusComboxRespone.Data dataItem : listData) {
            if (dataItem.getName().contains(text_spinner)) {
                statusWork = String.valueOf(dataItem.getValue());
            }
        }

//
        for (int i = 0; i < spin_work_level.getAdapter().getCount(); i++) {
            if (spin_work_level.getAdapter().getItem(i).toString().contains(text_spinner)) {
                spin_work_level.setSelection(i);
            }
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
}
