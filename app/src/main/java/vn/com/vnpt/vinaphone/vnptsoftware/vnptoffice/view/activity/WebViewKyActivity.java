package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.AttachNewFileAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.FileUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentwaiting.DocumentWaitingDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SignDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SigningOtpRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FileIdKyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KiThanhCongEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KiThatbaiEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.NhanWebViewEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocWaitingtEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;

public class WebViewKyActivity extends BaseActivity implements IExceptionErrorView {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.text)
    TextView text;

    private EditText edtOtp;
    private Button btnResend;
    private Button btnCancel;
    private Button btnOk;

    private String link = "";
    private String hanXyLySign = "";
    private String commentSign = "";
    private ProgressDialog progressDialog;
    private DocumentWaitingDao documentWaitingDao;
    private ICallFinishedListener callFinishedListener;
    private Dialog dialogOtp;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private String DocumentId = null;

    @SuppressLint({"ClickableViewAccessibility", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_ky);
        appPrefs = Application.getApp().getAppPrefs();

        Intent intent = getIntent();
        link = intent.getExtras().getString("linkweb");

        if (intent.getExtras().getString("title") != null) {
            text.setText(getString(R.string.LOGIN_BTN_LABEL));
        }
        if (intent.getExtras().getString("hanxuly") != null) {
            hanXyLySign = intent.getExtras().getString("hanxuly");
        }
        if (intent.getExtras().getString("comment") != null) {
            commentSign = intent.getExtras().getString("comment");
        }
        if (intent.getExtras().getString("DOCUMENT_ID") != null) {
            DocumentId = intent.getExtras().getString("DOCUMENT_ID");
        }

        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.v("okhttp", "+++------URL: " + url);
                return false;

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.v("okhttp","urlsss=====: "+url);
                String baseUrl = Application.getApp().getBaseAPIUrl();
                String domainWeb = url.substring(0, baseUrl.length());
                if (domainWeb.equals(baseUrl)) {
                    if (url.contains("/api/handlesignserver/success")) {
                        FileIdKyEvent fileIdKyEvent = EventBus.getDefault().getStickyEvent(FileIdKyEvent.class);
                        if (DocumentId != null) {
                            signdocumentAttach(Integer.parseInt(DocumentId), fileIdKyEvent.getFileId());
                        }
                    }
                    if (url.contains("/api/handlesignserver/error")) {
                        AlertDialogManager.showAlertDialog(WebViewKyActivity.this, getString(R.string.LOGIN_TITLE_ERROR),
                                "", true, AlertDialogManager.ERROR);
//                        if (webView.canGoBack()) {
//                            webView.goBack();
//                        }
                    }
                }
            }

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(WebViewKyActivity.this);
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += getString(R.string.str_button_tieptuchucnang);

                builder.setTitle("SSL Certificate Error");
                builder.setMessage(message);
                builder.setPositiveButton(getString(R.string.str_button_tieptuc), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton(getString(R.string.str_button_quaylai), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });

        webView.loadUrl(link);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        dialogOtp = new Dialog(this);
        dialogOtp.setContentView(R.layout.layout_dialog_comment_sign_otp);
        dialogOtp.setCancelable(false);
        dialogOtp.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        edtOtp = dialogOtp.findViewById(R.id.edt_otp);
        btnResend = dialogOtp.findViewById(R.id.btn_resend_otp);
        btnCancel = dialogOtp.findViewById(R.id.btn_cancel);
        btnOk = dialogOtp.findViewById(R.id.btn_ok);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NhanWebViewEvent event) {
        event = EventBus.getDefault().getStickyEvent(NhanWebViewEvent.class);
        if (event.getId() == 1) {

            // ky
            FileIdKyEvent fileIdKyEvent = EventBus.getDefault().getStickyEvent(FileIdKyEvent.class);
            if (DocumentId != null) {
                signdocumentAttach(Integer.parseInt(DocumentId), fileIdKyEvent.getFileId());
            }


        }
    }

    private void signdocumentAttach(int docid, int fileid) {
        progressDialog = new ProgressDialog(this);
        documentWaitingDao = new DocumentWaitingDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {
                if (object instanceof SigningRespone) {
                    hideProgressDialog();
                    if (((SigningRespone) object).getResponeAPI().getCode().equals("0")) {
                        if (((SigningRespone) object).getData().equals("OTP_REQUIRE")) {
                            showDialogOtp();
                        } else {
                            Toast.makeText(WebViewKyActivity.this, getString(R.string.str_ky_thanhcong), Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().postSticky(new KiThanhCongEvent(1));
                            EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
                            finish();
                        }

                    } else if (((SigningRespone) object).getResponeAPI().getCode().equals("LOGIN_TO_SIGN_SERVER")) {

                        String url = ((SigningRespone) object).getResponeAPI().getMessage();
//                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

                        Intent i = new Intent(WebViewKyActivity.this, WebViewKyActivity.class);
                        i.putExtra("linkweb", url);
                        i.putExtra("title", "Đăng nhập");
                        i.putExtra("DOCUMENT_ID", DocumentId);
                        i.putExtra("hanxuly", hanXyLySign);
                        i.putExtra("comment", commentSign);
                        startActivity(i);
                    }

                }
            }

            @Override
            public void onCallError(Object object) {
                hideProgressDialog();
                APIError apiError = (APIError) object;
                sendExceptionError(apiError);
                DialogSignErorrs(apiError.getMessage());
            }
        };
        showProgressDialog();

        documentWaitingDao.onKyVanBan(new SignDocumentRequest(docid, fileid, hanXyLySign, commentSign), callFinishedListener);
    }

    private void DialogSignErorrs(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WebViewKyActivity.this);
        builder.setTitle(getString(R.string.MARK_SIGN_TITLE_ERROR));
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                EventBus.getDefault().postSticky(new KiThatbaiEvent(true));
                finish();

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showDialogOtp() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils.hideKeyboard(WebViewKyActivity.this);
                dialogOtp.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtOtp.getText().toString() != null && !edtOtp.getText().toString().isEmpty()) {
                    signOtpAttach(edtOtp.getText().toString());
                } else {
                    Toast.makeText(WebViewKyActivity.this, getString(R.string.str_nhap_otp), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtpAttach();
            }
        });
        dialogOtp.show();
    }

    private void resendOtpAttach() {
        documentWaitingDao = new DocumentWaitingDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {
                if (object instanceof SigningRespone) {
                    hideProgressDialog();
                    if (((SigningRespone) object).getResponeAPI().getCode().equals("0")) {
                        if (((SigningRespone) object).getData().equalsIgnoreCase("true")) {
                            Toast.makeText(WebViewKyActivity.this, getString(R.string.str_gui_otp_thanhcong), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(WebViewKyActivity.this, WebViewKyActivity.this.getString(R.string.str_dialog_thongbao),
                                WebViewKyActivity.this.getString(R.string.str_dialog_resend_otp_faild), true, AlertDialogManager.ERROR);
                    }

                }
            }

            @Override
            public void onCallError(Object object) {
                hideProgressDialog();
                APIError apiError = (APIError) object;
                sendExceptionError(apiError);
                AlertDialogManager.showAlertDialog(WebViewKyActivity.this, WebViewKyActivity.this.getString(R.string.str_dialog_thongbao),
                        apiError.getMessage(), true, AlertDialogManager.ERROR);
            }
        };
        showProgressDialog();

        documentWaitingDao.resendServiceOtp(callFinishedListener);
    }

    private void signOtpAttach(final String otp) {
        progressDialog = new ProgressDialog(this);
        documentWaitingDao = new DocumentWaitingDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {
                if (object instanceof SigningOtpRespone) {
                    if (dialogOtp != null && dialogOtp.isShowing()) {
                        dialogOtp.dismiss();
                    }
                    hideProgressDialog();
                    if (((SigningOtpRespone) object).getResponeAPI().getCode().equals("0")) {
                        if (((SigningOtpRespone) object).getData() == null) {
                            Toast.makeText(WebViewKyActivity.this, getString(R.string.str_ky_thanhcong), Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().postSticky(new KiThanhCongEvent(1));
                            EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
                            finish();
                        } else {
                            if (((SigningOtpRespone) object).getData().getDocId() != null) {
                                hanXyLySign = ((SigningOtpRespone) object).getData().getHanXuLy();
                                commentSign = ((SigningOtpRespone) object).getData().getComment();
                                signdocumentAttach(Integer.parseInt(((SigningOtpRespone) object).getData().getDocId()),
                                        ((SigningOtpRespone) object).getData().getFileId());
                            }
                        }
                    } else {
                        DialogSignErorrs(getString(R.string.str_dialog_kychuathanhcong));
                    }

                }
            }

            @Override
            public void onCallError(Object object) {
                hideProgressDialog();
                APIError apiError = (APIError) object;
                sendExceptionError(apiError);
                DialogSignErorrs(apiError.getMessage());
            }
        };
        showProgressDialog();

        documentWaitingDao.signServiceOtp(otp, callFinishedListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().postSticky(new NhanWebViewEvent(0));
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

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(link)) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.btnBack})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                break;
        }
    }


}
