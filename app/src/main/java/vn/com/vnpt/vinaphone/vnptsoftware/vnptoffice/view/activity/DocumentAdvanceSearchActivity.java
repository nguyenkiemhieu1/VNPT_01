package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.DocumentAdvanceSearchParameter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FieldDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FilterSearch;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PriorityDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentsearch.DocumentSearchPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentsearch.IDocumentSearchPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IFilterSearchView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class DocumentAdvanceSearchActivity extends BaseActivity implements IFilterSearchView, ILoginView, IExceptionErrorView {

    private Toolbar toolbar;
    private ImageView btnBack;
    private boolean more_search;
    @BindView(R.id.btnNgayBHFrom) ImageView btnNgayBHFrom;
    @BindView(R.id.btnNgaySTFrom) ImageView btnNgaySTFrom;
    @BindView(R.id.btnHanXLFrom) ImageView btnHanXLFrom;
    @BindView(R.id.btnNgayBHEnd) ImageView btnNgayBHEnd;
    @BindView(R.id.btnNgaySTEnd) ImageView btnNgaySTEnd;
    @BindView(R.id.btnHanXLEnd) ImageView btnHanXLEnd;
    @BindView(R.id.tv_trichyeu_label) TextView tv_trichyeu_label;
    @BindView(R.id.tv_trichyeu) EditText tv_trichyeu;
    @BindView(R.id.tv_so_ki_hieu_label) TextView tv_so_ki_hieu_label;
    @BindView(R.id.tv_so_ki_hieu) EditText tv_so_ki_hieu;
    @BindView(R.id.tv_sovb_label) TextView tv_sovb_label;
    @BindView(R.id.tv_sovb) EditText tv_sovb;
    @BindView(R.id.tv_more_search) TextView tv_more_search;
    @BindView(R.id.tv_checkbox) TextView tv_checkbox;
    @BindView(R.id.tv_y_kien_xl_label) TextView tv_y_kien_xl_label;
    @BindView(R.id.tv_y_kien_xl) EditText tv_y_kien_xl;
    @BindView(R.id.tv_y_toanvan_label) TextView tv_y_toanvan_label;
    @BindView(R.id.tv_y_toanvan) EditText tv_y_toanvan;
    @BindView(R.id.tv_cqbh_label) TextView tv_cqbh_label;
    @BindView(R.id.tv_cqbh) EditText tv_cqbh;
    @BindView(R.id.tv_so_vb_label) TextView tv_so_vb_label;
    @BindView(R.id.sSoVB) Spinner sSoVB;
    @BindView(R.id.tv_linhvuc_label) TextView tv_linhvuc_label;
    @BindView(R.id.sLinhVuc) Spinner sLinhVuc;
    @BindView(R.id.tv_hinhthuc_label) TextView tv_hinhthuc_label;
    @BindView(R.id.sHinhThuc) Spinner sHinhThuc;
    @BindView(R.id.tv_do_khan_label) TextView tv_do_khan_label;
    @BindView(R.id.sDoKhan) Spinner sDoKhan;
    @BindView(R.id.tv_ngaybh) TextView tv_ngaybh;
    @BindView(R.id.tv_ngayst) TextView tv_ngayst;
    @BindView(R.id.tv_hanxuly_label) TextView tv_hanxuly_label;
    @BindView(R.id.tv_ngaybh_from) TextView tv_ngaybh_from;
    @BindView(R.id.tv_ngaybh_to) TextView tv_ngaybh_to;
    @BindView(R.id.tv_hanxl_from) TextView tv_hanxl_from;
    @BindView(R.id.tv_hanxl_to) TextView tv_hanxl_to;
    @BindView(R.id.tv_ngayst_from) TextView tv_ngayst_from;
    @BindView(R.id.tv_ngayst_to) TextView tv_ngayst_to;
    @BindView(R.id.btnSearch) TextView btnSearch;
    @BindView(R.id.btnRefresh) TextView btnRefresh;
    @BindView(R.id.btnCancel) TextView btnCancel;
    @BindView(R.id.layoutMoreSearch) LinearLayout layoutMoreSearch;
    @BindView(R.id.checkboxSearch) CheckBox checkboxSearch;
    private IDocumentSearchPresenter documentSearchPresenter = new DocumentSearchPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private String type;
    private String field;
    private String priority;
    @BindView(R.id.layoutDisplay) ScrollView layoutDisplay;

    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private ApplicationSharedPreferences appPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_advance_search);
        init();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        setTitle(getString(R.string.ADVANCE_SEARCH_DOCUMENT_LABEL));
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();

        progressDialog = new ProgressDialog(this);
        connectionDetector = new ConnectionDetector(this);
        setupToolbar();
        layoutMoreSearch.setVisibility(View.GONE);
        more_search = false;
        btnSearch.setTypeface(Application.getApp().getTypeface());
        btnRefresh.setTypeface(Application.getApp().getTypeface());
        btnCancel.setTypeface(Application.getApp().getTypeface());
        tv_more_search.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_checkbox.setTypeface(Application.getApp().getTypeface());
        tv_ngaybh_from.setTypeface(Application.getApp().getTypeface());
        tv_ngaybh_to.setTypeface(Application.getApp().getTypeface());
        tv_ngayst_from.setTypeface(Application.getApp().getTypeface());
        tv_ngayst_to.setTypeface(Application.getApp().getTypeface());
        tv_hanxl_from.setTypeface(Application.getApp().getTypeface());
        tv_hanxl_to.setTypeface(Application.getApp().getTypeface());
        tv_ngaybh.setTypeface(Application.getApp().getTypeface());
        tv_hanxuly_label.setTypeface(Application.getApp().getTypeface());
        tv_ngayst.setTypeface(Application.getApp().getTypeface());
        tv_trichyeu.setTypeface(Application.getApp().getTypeface());
        tv_trichyeu_label.setTypeface(Application.getApp().getTypeface());
        tv_so_ki_hieu.setTypeface(Application.getApp().getTypeface());
        tv_so_ki_hieu_label.setTypeface(Application.getApp().getTypeface());
        tv_sovb.setTypeface(Application.getApp().getTypeface());
        tv_sovb_label.setTypeface(Application.getApp().getTypeface());
        tv_y_kien_xl.setTypeface(Application.getApp().getTypeface());
        tv_y_kien_xl_label.setTypeface(Application.getApp().getTypeface());
        tv_y_toanvan_label.setTypeface(Application.getApp().getTypeface());
        tv_y_toanvan.setTypeface(Application.getApp().getTypeface());
        tv_cqbh_label.setTypeface(Application.getApp().getTypeface());
        tv_cqbh.setTypeface(Application.getApp().getTypeface());
        tv_so_vb_label.setTypeface(Application.getApp().getTypeface());
        tv_linhvuc_label.setTypeface(Application.getApp().getTypeface());
        tv_hinhthuc_label.setTypeface(Application.getApp().getTypeface());
        tv_do_khan_label.setTypeface(Application.getApp().getTypeface());
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;
            }
        });
        getFilters();
    }

    private DocumentAdvanceSearchParameter getData() {
        DocumentAdvanceSearchParameter documentAdvanceSearchParameter =
                new DocumentAdvanceSearchParameter("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        if (tv_trichyeu.getText() != null && tv_trichyeu.getText().toString() != null && !tv_trichyeu.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setTrichYeu(tv_trichyeu.getText().toString());
        }
        if (tv_so_ki_hieu.getText() != null && tv_so_ki_hieu.getText().toString() != null && !tv_so_ki_hieu.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setSoKyHieu(tv_so_ki_hieu.getText().toString());
        }
        if (tv_sovb.getText() != null && tv_sovb.getText().toString() != null && !tv_sovb.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setSoDen(tv_sovb.getText().toString());
        }
        if (tv_cqbh.getText() != null && tv_cqbh.getText().toString() != null && !tv_cqbh.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setCoQuanBanHanh(tv_cqbh.getText().toString());
        }
        if (tv_y_toanvan.getText() != null && tv_y_toanvan.getText().toString() != null && !tv_y_toanvan.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setTxtToanVan(tv_y_toanvan.getText().toString());
        }
        if (tv_ngaybh_from.getText() != null && tv_ngaybh_from.getText().toString() != null && !tv_ngaybh_from.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setStartDateBanHanh(tv_ngaybh_from.getText().toString());
        }
        if (tv_ngaybh_to.getText() != null && tv_ngaybh_to.getText().toString() != null && !tv_ngaybh_to.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setEndDateBanHanh(tv_ngaybh_to.getText().toString());
        }
        if (tv_ngayst_from.getText() != null && tv_ngayst_from.getText().toString() != null && !tv_ngayst_from.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setStartDateSoanThao(tv_ngayst_from.getText().toString());
        }
        if (tv_ngayst_to.getText() != null && tv_ngayst_to.getText().toString() != null && !tv_ngayst_to.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setEndDateSoanThao(tv_ngayst_to.getText().toString());
        }
        if (tv_hanxl_from.getText() != null && tv_hanxl_from.getText().toString() != null && !tv_hanxl_from.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setStartDateHanXuLy(tv_hanxl_from.getText().toString());
        }
        if (tv_hanxl_to.getText() != null && tv_hanxl_to.getText().toString() != null && !tv_hanxl_to.getText().toString().equals("")) {
            documentAdvanceSearchParameter.setEndDateHanXuLy(tv_hanxl_to.getText().toString());
        }
        documentAdvanceSearchParameter.setType(type);
        documentAdvanceSearchParameter.setLinhVuc(field);
        documentAdvanceSearchParameter.setPriority(priority);
        if (!checkboxSearch.isChecked()) {
            documentAdvanceSearchParameter.setSearchToanVan("0");
        } else {
            documentAdvanceSearchParameter.setSearchToanVan("1");
        }
        return documentAdvanceSearchParameter;
    }

    @OnClick({R.id.btnSearch, R.id.btnRefresh, R.id.btnCancel, R.id.btnNgayBHFrom, R.id.btnNgayBHEnd, R.id.btnNgaySTFrom, R.id.btnNgaySTEnd, R.id.btnHanXLFrom, R.id.btnHanXLEnd, R.id.tv_more_search})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                if (tv_ngaybh_from.getText() != null && tv_ngaybh_from.getText().toString() != null && !tv_ngaybh_from.getText().toString().equals("")
                        && tv_ngaybh_to.getText() != null && tv_ngaybh_to.getText().toString() != null && !tv_ngaybh_to.getText().toString().equals("")) {
                    String[] dateFrom = tv_ngaybh_from.getText().toString().split("\\/");
                    String[] dateTo = tv_ngaybh_to.getText().toString().split("\\/");
                    if (!checkRangeDate(Integer.parseInt(dateFrom[2]), Integer.parseInt(dateFrom[1]), Integer.parseInt(dateFrom[0]),
                            Integer.parseInt(dateTo[2]), Integer.parseInt(dateTo[1]), Integer.parseInt(dateTo[0]))) {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NGAY_BH_ERROR), true, AlertDialogManager.INFO);
                        return;
                    }
                }
                if (tv_ngayst_from.getText() != null && tv_ngayst_from.getText().toString() != null && !tv_ngayst_from.getText().toString().equals("")
                        && tv_ngayst_to.getText() != null && tv_ngayst_to.getText().toString() != null && !tv_ngayst_to.getText().toString().equals("")) {
                    String[] dateFrom = tv_ngayst_from.getText().toString().split("\\/");
                    String[] dateTo = tv_ngayst_to.getText().toString().split("\\/");
                    if (!checkRangeDate(Integer.parseInt(dateFrom[2]), Integer.parseInt(dateFrom[1]), Integer.parseInt(dateFrom[0]),
                            Integer.parseInt(dateTo[2]), Integer.parseInt(dateTo[1]), Integer.parseInt(dateTo[0]))) {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NGAY_ST_ERROR), true, AlertDialogManager.INFO);
                        return;
                    }
                }
                if (tv_hanxl_from.getText() != null && tv_hanxl_from.getText().toString() != null && !tv_hanxl_from.getText().toString().equals("")
                        && tv_hanxl_to.getText() != null && tv_hanxl_to.getText().toString() != null && !tv_hanxl_to.getText().toString().equals("")) {
                    String[] dateFrom = tv_hanxl_from.getText().toString().split("\\/");
                    String[] dateTo = tv_hanxl_to.getText().toString().split("\\/");
                    if (!checkRangeDate(Integer.parseInt(dateFrom[2]), Integer.parseInt(dateFrom[1]), Integer.parseInt(dateFrom[0]),
                            Integer.parseInt(dateTo[2]), Integer.parseInt(dateTo[1]), Integer.parseInt(dateTo[0]))) {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.HAN_XL_ERROR), true, AlertDialogManager.INFO);
                        return;
                    }
                }
                EventBus.getDefault().postSticky(getData());
                startActivity(new Intent(this, ResultSearchDocumentActivity.class));
                break;
            case R.id.btnRefresh:
                refresh();
                break;
            case R.id.btnCancel:
                onBackPressed();
                break;
            case R.id.tv_more_search:
                if (!more_search) {
                    tv_more_search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_more_close, 0, 0, 0);
                    layoutMoreSearch.setVisibility(View.VISIBLE);
                    more_search = true;
                } else {
                    tv_more_search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_more, 0, 0, 0);
                    layoutMoreSearch.setVisibility(View.GONE);
                    more_search = false;
                }
                break;
            case R.id.btnNgayBHFrom:
                Calendar nowBH = Calendar.getInstance();
                int mYear = nowBH.get(Calendar.YEAR);
                int mMonth = nowBH.get(Calendar.MONTH);
                int mDay = nowBH.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialogStart = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDay;
                        if(dayOfMonth < 10){
                            strDay = "0" + dayOfMonth;
                        } else {
                            strDay = String.valueOf(dayOfMonth);
                        }
                        String strMonth;
                        if (monthOfYear + 1 < 10){
                            strMonth = "0" + String.valueOf(monthOfYear + 1);
                        } else {
                            strMonth = String.valueOf(monthOfYear + 1);
                        }
                        String ngay = strDay + "/" + strMonth + "/" + year;
                        tv_ngaybh_from.setText(ngay);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialogStart.show();
                break;
            case R.id.btnNgayBHEnd:
                nowBH = Calendar.getInstance();
                mYear = nowBH.get(Calendar.YEAR);
                mMonth = nowBH.get(Calendar.MONTH);
                mDay = nowBH.get(Calendar.DAY_OF_MONTH);
                datePickerDialogStart = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDay;
                        if(dayOfMonth < 10){
                            strDay = "0" + dayOfMonth;
                        } else {
                            strDay = String.valueOf(dayOfMonth);
                        }
                        String strMonth;
                        if (monthOfYear + 1 < 10){
                            strMonth = "0" + String.valueOf(monthOfYear + 1);
                        } else {
                            strMonth = String.valueOf(monthOfYear + 1);
                        }
                        String ngay = strDay + "/" + strMonth + "/" + year;
                        tv_ngaybh_to.setText(ngay);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialogStart.show();
                break;
            case R.id.btnNgaySTFrom:
                Calendar nowST = Calendar.getInstance();
                mYear = nowST.get(Calendar.YEAR);
                mMonth = nowST.get(Calendar.MONTH);
                mDay = nowST.get(Calendar.DAY_OF_MONTH);
                datePickerDialogStart = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDay;
                        if(dayOfMonth < 10){
                            strDay = "0" + dayOfMonth;
                        } else {
                            strDay = String.valueOf(dayOfMonth);
                        }
                        String strMonth;
                        if (monthOfYear + 1 < 10){
                            strMonth = "0" + String.valueOf(monthOfYear + 1);
                        } else {
                            strMonth = String.valueOf(monthOfYear + 1);
                        }
                        String ngay = strDay + "/" + strMonth + "/" + year;
                        tv_ngayst_from.setText(ngay);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialogStart.show();
                break;
            case R.id.btnNgaySTEnd:
                nowST = Calendar.getInstance();
                mYear = nowST.get(Calendar.YEAR);
                mMonth = nowST.get(Calendar.MONTH);
                mDay = nowST.get(Calendar.DAY_OF_MONTH);
                datePickerDialogStart = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDay;
                        if(dayOfMonth < 10){
                            strDay = "0" + dayOfMonth;
                        } else {
                            strDay = String.valueOf(dayOfMonth);
                        }
                        String strMonth;
                        if (monthOfYear + 1 < 10){
                            strMonth = "0" + String.valueOf(monthOfYear + 1);
                        } else {
                            strMonth = String.valueOf(monthOfYear + 1);
                        }
                        String ngay = strDay + "/" + strMonth + "/" + year;
                        tv_ngayst_to.setText(ngay);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialogStart.show();
                break;
            case R.id.btnHanXLFrom:
                Calendar nowHXL = Calendar.getInstance();
                mYear = nowHXL.get(Calendar.YEAR);
                mMonth = nowHXL.get(Calendar.MONTH);
                mDay = nowHXL.get(Calendar.DAY_OF_MONTH);
                datePickerDialogStart = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDay;
                        if(dayOfMonth < 10){
                            strDay = "0" + dayOfMonth;
                        } else {
                            strDay = String.valueOf(dayOfMonth);
                        }
                        String strMonth;
                        if (monthOfYear + 1 < 10){
                            strMonth = "0" + String.valueOf(monthOfYear + 1);
                        } else {
                            strMonth = String.valueOf(monthOfYear + 1);
                        }
                        String ngay = strDay + "/" + strMonth + "/" + year;
                        tv_hanxl_from.setText(ngay);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialogStart.show();
                break;
            case R.id.btnHanXLEnd:
                nowHXL = Calendar.getInstance();
                mYear = nowHXL.get(Calendar.YEAR);
                mMonth = nowHXL.get(Calendar.MONTH);
                mDay = nowHXL.get(Calendar.DAY_OF_MONTH);
                datePickerDialogStart = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDay;
                        if(dayOfMonth < 10){
                            strDay = "0" + dayOfMonth;
                        } else {
                            strDay = String.valueOf(dayOfMonth);
                        }
                        String strMonth;
                        if (monthOfYear + 1 < 10){
                            strMonth = "0" + String.valueOf(monthOfYear + 1);
                        } else {
                            strMonth = String.valueOf(monthOfYear + 1);
                        }
                        String ngay = strDay + "/" + strMonth + "/" + year;
                        tv_hanxl_to.setText(ngay);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialogStart.show();
                break;
        }
    }

    private void refresh() {
        tv_trichyeu.setText("");
        tv_so_ki_hieu.setText("");
        tv_sovb.setText("");
        tv_cqbh.setText("");
        tv_ngaybh_from.setText("");
        tv_ngaybh_to.setText("");
        tv_ngayst_from.setText("");
        tv_ngayst_to.setText("");
        tv_hanxl_from.setText("");
        tv_hanxl_to.setText("");
        tv_y_kien_xl.setText("");
        tv_y_toanvan.setText("");
        checkboxSearch.setChecked(false);
        sSoVB.setSelection(0);
        sHinhThuc.setSelection(0);
        sLinhVuc.setSelection(0);
        sDoKhan.setSelection(0);
    }

    private boolean checkRangeDate(int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        if (year > yearEnd) {
            return false;
        }
        if (year == yearEnd) {
            if (monthOfYear > monthOfYearEnd) {
                return false;
            }
            if (monthOfYear == monthOfYearEnd) {
                if (dayOfMonth > dayOfMonthEnd) {
                    return false;
                }
            }
        }
        return true;
    }

    private void getTypes() {
        if (connectionDetector.isConnectingToInternet()) {
            documentSearchPresenter.getTypes();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getFields() {
        if (connectionDetector.isConnectingToInternet()) {
            documentSearchPresenter.getFields();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getPrioritys() {
        if (connectionDetector.isConnectingToInternet()) {
            documentSearchPresenter.getPrioritys();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getFilters() {
        getTypes();
        getFields();
        getPrioritys();
    }

    private void fillTypes(TypeDocumentRespone typeDocumentRespone) {
        final List<FilterSearch> filterSearchList = ConvertUtils.fromJSONList(typeDocumentRespone.getData(), FilterSearch.class);
        List<String> lst = new ArrayList<>();
        lst.add(getString(R.string.DEFAULT_TYPE_DOC));
        for (int i = 0; i < filterSearchList.size(); i++) {
            lst.add(filterSearchList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lst) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sHinhThuc.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sHinhThuc.setSelection(0);
        sHinhThuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    type = filterSearchList.get(position - 1).getCode();
                } else {
                    type = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void fillFileds(FieldDocumentRespone fieldDocumentRespone) {
        final List<FilterSearch> filterSearchList = ConvertUtils.fromJSONList(fieldDocumentRespone.getData(), FilterSearch.class);
        List<String> lst = new ArrayList<>();
        lst.add(getString(R.string.DEFAULT_FIELD_DOC));
        for (int i = 0; i < filterSearchList.size(); i++) {
            lst.add(filterSearchList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lst) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sLinhVuc.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sLinhVuc.setSelection(0);
        sLinhVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    field = filterSearchList.get(position - 1).getCode();
                } else {
                    field = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void fillPrioritys(PriorityDocumentRespone priorityDocumentRespone) {
        final List<FilterSearch> filterSearchList = ConvertUtils.fromJSONList(priorityDocumentRespone.getData(), FilterSearch.class);
        List<String> lst = new ArrayList<>();
        lst.add(getString(R.string.DEFAULT_PRIORITY_DOC));
        for (int i = 0; i < filterSearchList.size(); i++) {
            lst.add(filterSearchList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lst) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sDoKhan.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sDoKhan.setSelection(0);
        sDoKhan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    priority = filterSearchList.get(position - 1).getCode();
                } else {
                    priority = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof TypeDocumentRespone) {
            TypeDocumentRespone typeDocumentRespone = (TypeDocumentRespone) object;
            fillTypes(typeDocumentRespone);
        }
        if (object instanceof FieldDocumentRespone) {
            FieldDocumentRespone fieldDocumentRespone = (FieldDocumentRespone) object;
            fillFileds(fieldDocumentRespone);
        }
        if (object instanceof PriorityDocumentRespone) {
            PriorityDocumentRespone priorityDocumentRespone = (PriorityDocumentRespone) object;
            fillPrioritys(priorityDocumentRespone);
        }
    }

    @Override
    public void onError(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        getFilters();
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(DocumentAdvanceSearchActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
      showProgressDialog();
    }

    @Override
    public void hideProgress() {
       hideProgressDialog();
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
