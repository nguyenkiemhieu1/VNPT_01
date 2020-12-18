package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManagerFinish;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DanhGiaCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.UpdateCongViecRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobAssignRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListStatusComboxRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.IWorkManagePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.workmanage.WorkManagePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UpdateWorkManageEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDanhGiaJobView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IJobUpdateStatusView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IWorkManageView;

public class WorkAssessActivity extends BaseActivity implements IWorkManageView, IDanhGiaJobView, IExceptionErrorView {
    private Toolbar toolbar;
    private ImageView btnBack;

    @BindView(R.id.edt_content_work)
    EditText edt_content_work;
    @BindView(R.id.spin_work_level)
    Spinner spin_work_level;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    private String statusWork = "0";
    private String text_spinner = "";

    IWorkManagePresenter iWorkManagePresenter = new WorkManagePresenterImpl(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_assess);
        setupToolbar();
        ButterKnife.bind(this);
        appPrefs = Application.getApp().getAppPrefs();
        UpdateWorkManageEvent event = EventBus.getDefault().getStickyEvent(UpdateWorkManageEvent.class);
        if(event != null){
            text_spinner = event.getStatusDanhGia();
        }

        iWorkManagePresenter.getListStatusCombox("DANHGIACONGVIEC");
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


    @OnClick({R.id.btn_save, R.id.btn_close})
    public void submit(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                this.finish();
                break;
            case R.id.btn_save:
                if(edt_content_work.getText().toString().trim().isEmpty()){
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                            getString(R.string.text_danhgia), true, AlertDialogManager.WARNING);
                    return;
                }

                UpdateWorkManageEvent event = EventBus.getDefault().getStickyEvent(UpdateWorkManageEvent.class);

                DanhGiaCongViecRequest danhGiaCongViecRequest = new DanhGiaCongViecRequest();
                danhGiaCongViecRequest.setId(event.getId());
                danhGiaCongViecRequest.setContent(edt_content_work.getText().toString());
                danhGiaCongViecRequest.setStatus(statusWork);

                iWorkManagePresenter.danhgiaJob(danhGiaCongViecRequest);
                break;
        }
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
    public void onErrorGetListData(APIError apiError) {
        sendExceptionError(apiError);
    }

    @Override
    public void onSuccessDanhGiaJob() {
        AlertDialogManagerFinish.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                getString(R.string.str_gui_danhgia_thanhcong), true, AlertDialogManager.SUCCESS, this);
    }

    @Override
    public void onErrorGetDanhGia(APIError apiError) {
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
            try{
                if (dataItem.getValue() == Integer.valueOf(text_spinner)) {
                    statusWork = String.valueOf(dataItem.getValue());
                    spin_work_level.setSelection(Integer.valueOf(dataItem.getValue()));
                }
            } catch (Exception e){

            }

            try{
                if (dataItem.getName().equalsIgnoreCase(text_spinner)) {
                    statusWork = String.valueOf(dataItem.getValue());
                    spin_work_level.setSelection(Integer.valueOf(dataItem.getValue()));
                }
            } catch (Exception e){

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
