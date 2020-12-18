package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.apache.commons.io.IOUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.documentwaiting.DocumentWaitingDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.downloadfile.DownloadFileDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.login.LoginDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SignDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetLinkEditFilesResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SigningOtpRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting.ItemClickGetTypeChangeButton;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailDocumentWaitingActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.WebViewKyActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog.DialogProgress;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FileIdKyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FinishEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KiThanhCongEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KiThatbaiEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ReloadDocWaitingtEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;

/**
 * Created by LinhLK 0948012236 on 8/31/2017.1
 */

public class AttachNewFileAdapter extends RecyclerView.Adapter<AttachNewFileAdapter.ViewHolder> implements IExceptionErrorView {
    private Context context;
    private int resource;
    private List<AttachFileInfo> attachFileInfoList;
    private ConnectionDetector connectionDetector;
    private ICallFinishedListener callFinishedListener;
    private DialogProgress progressDialog;
    private LoginDao loginDao;
    private DownloadFileDao downloadFileDao;
    private DocumentWaitingDao documentWaitingDao;
    private String pathFileDownLoad;
    private String hanXyLySign = "";
    private String commentSign = "";
    private AttachFileInfo attachFileInfoCustom;
    boolean createFile = false;
    private ApplicationSharedPreferences appPrefs;
    private String documentId;
    private ItemClickGetTypeChangeButton itemClickGetTypeChangeButton;

    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    public AttachNewFileAdapter(Context context, int resource, String documentId, List<AttachFileInfo> attachFileInfoList, ItemClickGetTypeChangeButton itemClickGetTypeChangeButton) {
        this.context = context;
        this.resource = resource;
        this.attachFileInfoList = attachFileInfoList;
        this.documentId = documentId;
        this.itemClickGetTypeChangeButton = itemClickGetTypeChangeButton;
    }

    public void updateListData(List<AttachFileInfo> attachFileInfoList) {
        this.attachFileInfoList = attachFileInfoList;
        this.notifyDataSetChanged();
        if (dialogViewFile != null && dialogViewFile.isShowing()) {
            for (AttachFileInfo info : attachFileInfoList) {
                if (info.getId() == attachFileInfoCustom.getId()) {
                    attachFileInfoCustom = info;
                }
            }
            getViewFileDocument(attachFileInfoCustom);
        }
    }


    private void getViewFileDocument(final AttachFileInfo attachFileInfo) {
        progressDialog = new DialogProgress(context, context.getString(R.string.DOWNLOADING_REQUEST));
        downloadFileDao = new DownloadFileDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {
                if (object instanceof ResponseBody) {
                    progressDialog.hideProgressDialog();
                    ResponseBody responseBody = (ResponseBody) object;
                    InputStream inputStream = responseBody.byteStream();
                    viewFileDialog(inputStream, attachFileInfo.getName(), attachFileInfo);

                }
                if (object instanceof LoginRespone) {
                    LoginRespone loginRespone = (LoginRespone) object;
                    if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                        LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
                        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
                        if (connectionDetector.isConnectingToInternet()) {
                            downloadFileDao.onGetUrlFileDao(attachFileInfo.getId(), this);
                        } else {
                            AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                        }
                    }
                }
            }

            @Override
            public void onCallError(Object object) {
                progressDialog.hideProgressDialog();
                APIError apiError = (APIError) object;
                sendExceptionError(apiError);
                if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
                    if (connectionDetector.isConnectingToInternet()) {
                        loginDao = new LoginDao();
                        loginDao.onSendLoginDao(Application.getApp().getAppPrefs().getAccount(), callFinishedListener);
                    } else {
                        AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                    }
                } else {
                    AlertDialogManager.showAlertDialog(context, context.getString(R.string.DOWNLOAD_TITLE_ERROR), context.getString(R.string.DOWNLOAD_FILE_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        };

        progressDialog.showProgressDialog();
        downloadFileDao.onGetViewFileDocument(attachFileInfo.getId(), callFinishedListener);
    }

    private void downloadFileAttactk(final AttachFileInfo attachFileInfo, final int typeDownload) {
        {
            connectionDetector = new ConnectionDetector(context);
            progressDialog = new DialogProgress(context, context.getString(R.string.DOWNLOADING_REQUEST));
            downloadFileDao = new DownloadFileDao();
            callFinishedListener = new ICallFinishedListener() {
                @Override
                public void onCallSuccess(Object object) {
                    if (object instanceof ResponseBody) {
                        ResponseBody responseBody = (ResponseBody) object;
                        progressDialog.hideProgressDialog();
                        if (responseBody != null) {
                            if (checkPermissonApp(responseBody, attachFileInfo.getName())) {
                                createFile = false;
                                //Share File
                                if (typeDownload == 1) {
                                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                    sharingIntent.setType("text/plain");
                                    sharingIntent.putExtra(Intent.EXTRA_TEXT, attachFileInfo.getName());
                                    sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(pathFileDownLoad)));

                                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                    StrictMode.setVmPolicy(builder.build());

                                    context.startActivity(Intent.createChooser(sharingIntent, "Chia sẻ"));
                                }
                                //Download
                                else {
                                    AlertDialogManager.showAlertDialog(context, context.getString(R.string.DOWNLOAD_FILE_SUCCESS),
                                            context.getString(R.string.str_tai_file_toi_thu_muc) + "" + context.getString(R.string.app_name), true, AlertDialogManager.SUCCESS);
                                    responseBody.close();
                                }


//                            AlertDialogManager.showAlertDialog(context, context.getString(R.string.TITLE_SUCCESS), context.getString(R.string.DOWNLOAD_FILE_SUCCESS), true, AlertDialogManager.SUCCESS);
                            }
                        } else {
                            AlertDialogManager.showAlertDialog(context, context.getString(R.string.DOWNLOAD_TITLE_ERROR), context.getString(R.string.DOWNLOAD_FILE_ERROR), true, AlertDialogManager.ERROR);
                        }
                    }
                    if (object instanceof LoginRespone) {
                        LoginRespone loginRespone = (LoginRespone) object;
                        if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                            LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
                            Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
                            if (connectionDetector.isConnectingToInternet()) {
                                downloadFileDao.onDownloadFileDao(attachFileInfo.getId(), this);
                            } else {
                                AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                            }
                        }
                    }
                }

                @Override
                public void onCallError(Object object) {
                    APIError apiError = (APIError) object;
                    sendExceptionError(apiError);
                    if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
                        if (connectionDetector.isConnectingToInternet()) {
                            loginDao = new LoginDao();
                            loginDao.onSendLoginDao(Application.getApp().getAppPrefs().getAccount(), callFinishedListener);
                        } else {
                            AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(context, context.getString(R.string.DOWNLOAD_TITLE_ERROR), context.getString(R.string.DOWNLOAD_FILE_ERROR), true, AlertDialogManager.ERROR);
                    }
                }
            };
            progressDialog.showProgressDialog();
            downloadFileDao.onDownloadFileDao(attachFileInfo.getId(), callFinishedListener);
        }
    }

    private boolean checkPermissonApp(final ResponseBody body, final String filename) {
        Dexter.withActivity((Activity) context)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        createFile = writeResponseBodyToDisk(body, filename);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, final PermissionToken token) {/* ... */
                        new AlertDialog.Builder(context).setTitle(R.string.permission_rationale_title)
                                .setMessage(R.string.permission_rationale_message)
                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        token.cancelPermissionRequest();
                                    }
                                })
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        token.continuePermissionRequest();
                                    }
                                })
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        token.cancelPermissionRequest();
                                    }
                                })
                                .show();
                    }
                }).check();
        return createFile;
    }

    private boolean writeResponseBodyToDisk(final ResponseBody body, final String filename) {

        try {
            // todo change the file location/name according to your needs
            File root = new File(Environment.getExternalStorageDirectory() + "/Download/" + context.getString(R.string.app_name) + "/");
            if (!root.exists()) {
                root.mkdirs();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File fileDownload = new File(root + "/" + filename);
                if (fileDownload.exists()) {
                    fileDownload.delete();
                }
                fileDownload.createNewFile();
                byte[] fileReader = new byte[4096];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(fileDownload);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                pathFileDownLoad = fileDownload.getAbsolutePath();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }


        } catch (IOException e) {
            return false;
        }

    }

    private Dialog dialogViewFile;
    private PDFView pdfViewFile;
    private LinearLayout lnShowHideSign;
    private ImageView image_view_file;
    private ImageView btn_server_ViewFile;
    private Button btn_send_ViewFile;
    private ImageView btn_pki_ViewFile;
    private ImageView btn_ca_server_ViewFile;
    private ImageView btnEditViewFile;

    private void viewFileDialog(InputStream inputStream, final String fileName, final AttachFileInfo attachFileInfo) {
        if (dialogViewFile == null || !dialogViewFile.isShowing()) {
            dialogViewFile = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialogViewFile.setContentView(R.layout.layout_dialog_view_file);
            TextView tvTitle = (TextView) dialogViewFile.findViewById(R.id.tv_title_label);
            ImageView ivCloseDialog = (ImageView) dialogViewFile.findViewById(R.id.ivCloseDialog);
            ImageView ivShareFile = (ImageView) dialogViewFile.findViewById(R.id.ivShareFile);
            ImageView btnDownFile = (ImageView) dialogViewFile.findViewById(R.id.ivDownFile);

            btnEditViewFile = (ImageView) dialogViewFile.findViewById(R.id.btn_edit_file);
            btn_send_ViewFile = dialogViewFile.findViewById(R.id.btn_send);
            pdfViewFile = dialogViewFile.findViewById(R.id.pdf_view);
            image_view_file = dialogViewFile.findViewById(R.id.image_view_file);
            btn_server_ViewFile = (ImageView) dialogViewFile.findViewById(R.id.btn_server_file);
            btn_server_ViewFile.setVisibility(View.GONE);
            lnShowHideSign = (LinearLayout) dialogViewFile.findViewById(R.id.ll_show_hide_sign);
            btn_pki_ViewFile = dialogViewFile.findViewById(R.id.btn_pki_file);
            btn_ca_server_ViewFile = dialogViewFile.findViewById(R.id.btn_ca_server_file);
            if (attachFileInfo != null) {
                if (attachFileInfo.getEditFile() != null) {
                    if (attachFileInfo.getEditFile().equals("true")) {
                        btnEditViewFile.setVisibility(View.VISIBLE);
                    } else {
                        btnEditViewFile.setVisibility(View.GONE);
                    }
                } else {
                    btnEditViewFile.setVisibility(View.GONE);
                }

                if (attachFileInfo.getBtnChuyen() != null && attachFileInfo.getBtnChuyen().equals("true")) {
                    btn_send_ViewFile.setVisibility(View.VISIBLE);
                } else {
                    btn_send_ViewFile.setVisibility(View.GONE);
                }
                if (attachFileInfo.getIsKy().equals("true")) {
                    lnShowHideSign.setVisibility(View.GONE);

                } else {

                    lnShowHideSign.setVisibility(View.VISIBLE);
                    if (attachFileInfo.getKyCA() != null) {
                        if (attachFileInfo.getKyCA().equals("true")) {
                            btn_ca_server_ViewFile.setVisibility(View.VISIBLE);
                        } else {
                            btn_ca_server_ViewFile.setVisibility(View.GONE);
                        }
                    } else {
                        btn_ca_server_ViewFile.setVisibility(View.GONE);
                    }

                    if (attachFileInfo.getKyServer().equals("true")) {
                        btn_server_ViewFile.setVisibility(View.VISIBLE);
                    } else {
                        btn_server_ViewFile.setVisibility(View.GONE);
                    }

                    if (attachFileInfo.getKyPki().equals("true")) {
                        btn_pki_ViewFile.setVisibility(View.VISIBLE);
                    } else {
                        btn_pki_ViewFile.setVisibility(View.GONE);
                    }
                }

            }
            tvTitle.setText(fileName);
            btn_send_ViewFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickGetTypeChangeButton != null) {
                        itemClickGetTypeChangeButton.ItemClickGetTypeChangeButtonDocument(btn_send_ViewFile);
                    }
                }
            });
            ivCloseDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogViewFile.dismiss();
                }
            });
            ivShareFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (attachFileInfo != null) {
                        downloadFileAttactk(attachFileInfo, 1);
                    }
                }
            });
            btnDownFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (attachFileInfo != null) {
                        downloadFileAttactk(attachFileInfo, 2);
                    }
                }
            });
            btn_ca_server_ViewFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attachFileInfo != null) {
                        if (attachFileInfo.getCommentDisplay().equals("true")) {
                            showPopUpCommentSign(attachFileInfo);
                        } else {
                            EventBus.getDefault().postSticky(new FileIdKyEvent(attachFileInfo.getId()));
                            SignCadocumentAttach(Integer.parseInt(documentId), attachFileInfo.getId(), attachFileInfo);
                        }
                    }
                }
            });

            btn_server_ViewFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (attachFileInfo != null) {
                        if (attachFileInfo.getCommentDisplay().equals("true")) {
                            showPopUpCommentSign(attachFileInfo);
                        } else {
                            EventBus.getDefault().postSticky(new FileIdKyEvent(attachFileInfo.getId()));
                            signdocumentAttach(Integer.parseInt(documentId), attachFileInfo.getId(), attachFileInfo);
                        }
                    }
                }
            });

            btn_pki_ViewFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (attachFileInfo != null) {
                        if (attachFileInfo.getCommentDisplay().equals("true")) {
                            showPopUpCommentSign(attachFileInfo);
                        } else {
                            EventBus.getDefault().postSticky(new FileIdKyEvent(attachFileInfo.getId()));
                            signdocumentPKIAttach(Integer.parseInt(documentId), attachFileInfo.getId(), "", "", attachFileInfo);
                        }
                    }
                }
            });
            btnEditViewFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog = new DialogProgress(context, context.getString(R.string.DOWNLOADING_REQUEST));
                    documentWaitingDao = new DocumentWaitingDao();
                    callFinishedListener = new ICallFinishedListener() {
                        @Override
                        public void onCallSuccess(Object object) {
                            if (object instanceof GetLinkEditFilesResponse) {
                                progressDialog.hideProgressDialog();
                                if (((GetLinkEditFilesResponse) object).getResponeAPI().getCode().equals("0")) {
                                    GetLinkEditFilesResponse getLinkEditFilesResponse = (GetLinkEditFilesResponse) object;
                                    EditfileDialog(getLinkEditFilesResponse.getData(), attachFileInfo);
                                }
                            }

                        }

                        @Override
                        public void onCallError(Object object) {
                            progressDialog.hideProgressDialog();
                            APIError apiError = (APIError) object;
                            sendExceptionError(apiError);
                            DialogSignErorrs(apiError.getMessage());
                        }
                    };
                    progressDialog.showProgressDialog();

                    documentWaitingDao.onGetLinkEditFiles(attachFileInfo.getId(), callFinishedListener);

                }
            });

            if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPG)
                    || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPEG)
                    || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PNG)
                    || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.GIF)
                    || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TIFF)
                    || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.BMP)) {
                pdfViewFile.setVisibility(View.GONE);
                image_view_file.setVisibility(View.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                Glide.with(context).load(bitmap).into(image_view_file);
                image_view_file.setImageBitmap(bitmap);
            } else {
                pdfViewFile.setVisibility(View.VISIBLE);
                image_view_file.setVisibility(View.GONE);
                pdfViewFile.fromStream(inputStream).spacing(12).enableAnnotationRendering(true).enableAntialiasing(true).load();

            }
            dialogViewFile.show();
        } else {

            if (attachFileInfo != null) {
                if (attachFileInfo.getEditFile() != null) {
                    if (attachFileInfo.getEditFile().equals("true")) {
                        btnEditViewFile.setVisibility(View.VISIBLE);
                    } else {
                        btnEditViewFile.setVisibility(View.GONE);
                    }
                } else {
                    btnEditViewFile.setVisibility(View.GONE);
                }

                if (attachFileInfo.getBtnChuyen() != null && attachFileInfo.getBtnChuyen().equals("true")) {
                    btn_send_ViewFile.setVisibility(View.VISIBLE);
                } else {
                    btn_send_ViewFile.setVisibility(View.GONE);
                }
                if (attachFileInfo.getIsKy().equals("true")) {
                    lnShowHideSign.setVisibility(View.GONE);
                    btnEditViewFile.setVisibility(View.GONE);
                }

            }
            if (pdfViewFile != null && image_view_file != null) {

                if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPG)
                        || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPEG)
                        || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PNG)
                        || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.GIF)
                        || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TIFF)
                        || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.BMP)) {
                    pdfViewFile.setVisibility(View.GONE);
                    image_view_file.setVisibility(View.VISIBLE);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Glide.with(context).load(bitmap).into(image_view_file);
                } else {
                    pdfViewFile.setVisibility(View.VISIBLE);
                    image_view_file.setVisibility(View.GONE);
                    pdfViewFile.fromStream(inputStream).spacing(12).enableAnnotationRendering(true).enableAntialiasing(true).load();
                }

            }
        }


    }

    private void EditfileDialog(String url, AttachFileInfo attachFileInfo) {
        Dialog dialogs = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialogs.setContentView(R.layout.layout_dialog_edit_file);
        TextView tvTitle = dialogs.findViewById(R.id.tv_title);
        tvTitle.setTypeface(Application.getApp().getTypeface());
        ImageView btn_close = dialogs.findViewById(R.id.image_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs.cancel();
            }
        });
        WebView webView = dialogs.findViewById(R.id.webView_edit_file);
        webView.addJavascriptInterface(new WebAppInterface(context), "Android");
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String baseUrl = Application.getApp().getBaseAPIUrl();
                String domainWeb = url.substring(0, baseUrl.length());
                if (domainWeb.equals(baseUrl)) {
                    if (url.contains("/api/file/editsuccesscallback/")) {
                        if (dialogViewFile != null && dialogViewFile.isShowing()) {
                            getViewFileDocument(attachFileInfo);
                        }
//                        if (context instanceof DetailDocumentWaitingActivity) {
//                            ((DetailDocumentWaitingActivity) context).getAttachFiles();
//                        }
                        dialogs.cancel();
                    }
                    if (url.contains("fileediterror")) {
                    }
                }
            }
        });
//        dialogs.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialogs) {
//                if (dialogViewFile != null && dialogViewFile.isShowing()) {
//                    getViewFileDocument(attachFileInfo);
//                }
//            }
//        });
        dialogs.show();

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

    private void showPopUpCommentSign(final AttachFileInfo attachFileInfo) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_comment_sign);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText edtContentSign = (EditText) dialog.findViewById(R.id.edt_content_sign);
        edtContentSign.setText(commentSign);
        final TextView tvDateSign = (TextView) dialog.findViewById(R.id.tvDateSign);
        ImageView btn_ca_server_file = dialog.findViewById(R.id.btn_ca_server_file);
        ImageView btn_server_file = (ImageView) dialog.findViewById(R.id.btn_server_file);
        ImageView ivCalender = (ImageView) dialog.findViewById(R.id.iv_calender);
        ImageView btnCancel = (ImageView) dialog.findViewById(R.id.btn_cancel);
        ImageView btn_pki_file = (ImageView) dialog.findViewById(R.id.btn_pki_file);

        // show ẩn / hiện nút ký
        if (attachFileInfo != null) {
            if (attachFileInfo.getIsKy().equals("true")) {
                btn_ca_server_file.setVisibility(View.GONE);
                btn_server_file.setVisibility(View.GONE);
                btn_pki_file.setVisibility(View.GONE);
            } else {
                if (attachFileInfo.getKyCA() != null) {
                    if (attachFileInfo.getKyCA().equals("true")) {
                        btn_ca_server_file.setVisibility(View.VISIBLE);
                    } else {
                        btn_ca_server_file.setVisibility(View.GONE);
                    }
                } else {
                    btn_ca_server_file.setVisibility(View.GONE);
                }

                if (attachFileInfo.getKyServer().equals("true")) {
                    btn_server_file.setVisibility(View.VISIBLE);
                } else {
                    btn_server_file.setVisibility(View.GONE);
                }

                if (attachFileInfo.getKyPki().equals("true")) {
                    btn_pki_file.setVisibility(View.VISIBLE);
                } else {
                    btn_pki_file.setVisibility(View.GONE);
                }
            }

        }
        btn_ca_server_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (attachFileInfo != null) {
                    hanXyLySign = tvDateSign.getText().toString().trim();
                    commentSign = edtContentSign.getText().toString().trim();
                    EventBus.getDefault().postSticky(new FileIdKyEvent(attachFileInfo.getId()));
                    SignCadocumentAttach(Integer.parseInt(documentId), attachFileInfo.getId(), attachFileInfo);
                }
            }
        });
        btn_server_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (attachFileInfo != null) {
                    hanXyLySign = tvDateSign.getText().toString().trim();
                    commentSign = edtContentSign.getText().toString().trim();
                    EventBus.getDefault().postSticky(new FileIdKyEvent(attachFileInfo.getId()));
                    signdocumentAttach(Integer.parseInt(documentId), attachFileInfo.getId(), attachFileInfo);
                }
            }
        });

        btn_pki_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (attachFileInfo != null) {
//                    hanXyLySign = tvDateSign.getText().toString().trim();
//                    commentSign = edtContentSign.getText().toString().trim();
                    EventBus.getDefault().postSticky(new FileIdKyEvent(attachFileInfo.getId()));
                    signdocumentPKIAttach(Integer.parseInt(documentId), attachFileInfo.getId(), edtContentSign.getText().toString().trim(), tvDateSign.getText().toString().trim(), attachFileInfo);
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (view == null) {
                    view = new View(context);
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dialog.dismiss();
            }
        });
        ivCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //Lấy ngày sau khi chọn date picker
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
                        tvDateSign.setText(strDate);

                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        dialog.show();
    }

    @Override
    public AttachNewFileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(this.resource, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(AttachNewFileAdapter.ViewHolder viewHolder, int position) {
        viewHolder.filename.setTypeface(Application.getApp().getTypeface());
        final AttachFileInfo attachFileInfo = attachFileInfoList.get(position);

        if (attachFileInfo.getName() != null) {
            viewHolder.filename.setText(attachFileInfo.getName());
        }

        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.DOC) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.DOCX)) {
            viewHolder.ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_doc));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.XLS) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.XLSX)) {
            viewHolder.ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_xls));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PPT) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PPTX)) {
            viewHolder.ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ppt));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PDF)) {
            viewHolder.ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pdf));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.ZIP)) {
            viewHolder.ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_zip));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.RAR)) {
            viewHolder.ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_rar));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TXT)) {
            viewHolder.ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_txt));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.MPP)) {
            viewHolder.ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_mpp));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPEG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PNG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.GIF)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TIFF)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.BMP)) {
            viewHolder.ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_image));
        }
        viewHolder.filename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachFileInfoCustom = attachFileInfo;
                getViewFileDocument(attachFileInfo);
            }

        });

        if (attachFileInfo.getInfo() != null) {
            if (attachFileInfo.getInfo().size() > 0) {
                viewHolder.info.setVisibility(View.VISIBLE);
                String ln = "";
                for (int i = 0; i < attachFileInfo.getInfo().size(); i++) {
                    ln += attachFileInfo.getInfo().get(i) + "\n";
                }
                viewHolder.info.setText(ln);
            } else {
                viewHolder.info.setVisibility(View.GONE);
            }
        }

        if (attachFileInfo != null) {
            if (attachFileInfo.getIsKy().equals("true")) {
                viewHolder.btn_Ca_server_file.setVisibility(View.GONE);
                viewHolder.btn_server_file.setVisibility(View.GONE);
                viewHolder.btn_pki_file.setVisibility(View.GONE);
            } else {
                if (attachFileInfo.getKyCA() != null) {
                    if (attachFileInfo.getKyCA().equals("true")) {
                        viewHolder.btn_Ca_server_file.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.btn_Ca_server_file.setVisibility(View.GONE);
                    }
                } else {
                    viewHolder.btn_Ca_server_file.setVisibility(View.GONE);
                }

                if (attachFileInfo.getKyServer().equals("true")) {
                    viewHolder.btn_server_file.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.btn_server_file.setVisibility(View.GONE);
                }

                if (attachFileInfo.getKyPki().equals("true")) {
                    viewHolder.btn_pki_file.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.btn_pki_file.setVisibility(View.GONE);
                }
            }

        }
        viewHolder.btn_Ca_server_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attachFileInfo != null) {
                    attachFileInfoCustom = attachFileInfo;
                    if (attachFileInfo.getCommentDisplay().equals("true")) {
                        showPopUpCommentSign(attachFileInfo);
                    } else {
                        EventBus.getDefault().postSticky(new FileIdKyEvent(attachFileInfo.getId()));
                        SignCadocumentAttach(Integer.parseInt(documentId), attachFileInfo.getId(), attachFileInfo);
                    }
                }
            }
        });
        viewHolder.btn_server_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attachFileInfo != null) {
                    attachFileInfoCustom = attachFileInfo;
                    if (attachFileInfo.getCommentDisplay().equals("true")) {
                        showPopUpCommentSign(attachFileInfo);
                    } else {
                        EventBus.getDefault().postSticky(new FileIdKyEvent(attachFileInfo.getId()));
                        signdocumentAttach(Integer.parseInt(documentId), attachFileInfo.getId(), attachFileInfo);
                    }
                }
            }
        });

        viewHolder.btn_pki_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attachFileInfo != null) {
                    attachFileInfoCustom = attachFileInfo;
                    if (attachFileInfo.getCommentDisplay().equals("true")) {
                        showPopUpCommentSign(attachFileInfo);
                    } else {
                        EventBus.getDefault().postSticky(new FileIdKyEvent(attachFileInfo.getId()));
                        signdocumentPKIAttach(Integer.parseInt(documentId), attachFileInfo.getId(), "", "", attachFileInfo);
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return attachFileInfoList.size();
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
        appPrefs = Application.getApp().getAppPrefs();
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


    private void signdocumentAttach(int docid, int fileid, final AttachFileInfo attachFileInfo) {
        progressDialog = new DialogProgress(context, context.getString(R.string.DOWNLOADING_REQUEST));
        documentWaitingDao = new DocumentWaitingDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {

                if (object instanceof SigningRespone) {
                    progressDialog.hideProgressDialog();
                    if (((SigningRespone) object).getResponeAPI().getCode().equals("0")) {
                        if (((SigningRespone) object).getData().equals("OTP_REQUIRE")) {
                            showDialogOtp(attachFileInfo);
                        } else {
                            dialog_PKI_or_CAserver_or_server_Success(attachFileInfo);
                        }


                    } else if (((SigningRespone) object).getResponeAPI().getCode().equals("LOGIN_TO_SIGN_SERVER")) {

                        String url = ((SigningRespone) object).getResponeAPI().getMessage();
//                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

                        Intent i = new Intent(context, WebViewKyActivity.class);
                        i.putExtra("linkweb", url);
                        i.putExtra("title", "Đăng nhập");
                        i.putExtra("DOCUMENT_ID", documentId);
                        i.putExtra("hanxuly", hanXyLySign);
                        i.putExtra("comment", commentSign);
                        context.startActivity(i);
                    }
                }
            }

            @Override
            public void onCallError(Object object) {
                progressDialog.hideProgressDialog();
                APIError apiError = (APIError) object;
                sendExceptionError(apiError);
                DialogSignErorrs(apiError.getMessage());
            }
        };
        progressDialog.showProgressDialog();

        documentWaitingDao.onKyVanBan(new SignDocumentRequest(docid, fileid, hanXyLySign, commentSign), callFinishedListener);
    }

    private void SignCadocumentAttach(int docid, int fileid, final AttachFileInfo attachFileInfo) {
        progressDialog = new DialogProgress(context, context.getString(R.string.DOWNLOADING_REQUEST));
        documentWaitingDao = new DocumentWaitingDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {

                if (object instanceof SigningRespone) {
                    progressDialog.hideProgressDialog();
                    if (((SigningRespone) object).getResponeAPI().getCode().equals("0")) {
                        dialog_PKI_or_CAserver_or_server_Success(attachFileInfo);
                    }
                }
            }

            @Override
            public void onCallError(Object object) {
                progressDialog.hideProgressDialog();
                APIError apiError = (APIError) object;
                sendExceptionError(apiError);
                DialogSignErorrs(apiError.getMessage());
            }
        };
        progressDialog.showProgressDialog();

        documentWaitingDao.onKyVanBanCA(new SignDocumentRequest(docid, fileid, hanXyLySign, commentSign), callFinishedListener);
    }

    private Dialog dialogOtp;

    private void showDialogOtp(final AttachFileInfo attachFileInfo) {
        dialogOtp = new Dialog(context);
        dialogOtp.setContentView(R.layout.layout_dialog_comment_sign_otp);
        dialogOtp.setCancelable(false);
        dialogOtp.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText edtOtp = (EditText) dialogOtp.findViewById(R.id.edt_otp);
        final Button btnResend = (Button) dialogOtp.findViewById(R.id.btn_resend_otp);
        final Button btnCancel = (Button) dialogOtp.findViewById(R.id.btn_cancel);
        final Button btnOk = (Button) dialogOtp.findViewById(R.id.btn_ok);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogOtp != null && dialogOtp.isShowing()) {
                    dialogOtp.dismiss();
                }
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtOtp.getText().toString() != null && !edtOtp.getText().toString().isEmpty()) {
                    //ky khong thanh cong, giu nguyen man hinh nhap otp, xoa text
                    progressDialog = new DialogProgress(context, context.getString(R.string.DOWNLOADING_REQUEST));
                    documentWaitingDao = new DocumentWaitingDao();
                    callFinishedListener = new ICallFinishedListener() {
                        @Override
                        public void onCallSuccess(Object object) {
                            if (object instanceof SigningOtpRespone) {
                                progressDialog.hideProgressDialog();

                                if (((SigningOtpRespone) object).getResponeAPI().getCode().equals("0")) {
                                    if (dialogOtp != null && dialogOtp.isShowing()) {
                                        dialogOtp.dismiss();
                                    }

                                    if (((SigningOtpRespone) object).getData() == null) {
                                        attachFileInfoCustom.setIsKy("true");
                                        dialog_PKI_or_CAserver_or_server_Success(attachFileInfo);
                                    } else {
                                        hanXyLySign = ((SigningOtpRespone) object).getData().getHanXuLy();
                                        commentSign = ((SigningOtpRespone) object).getData().getComment();
                                        if (((SigningOtpRespone) object).getData().getDocId() != null) {
                                            signdocumentAttach(Integer.parseInt(((SigningOtpRespone) object).getData().getDocId()),
                                                    ((SigningOtpRespone) object).getData().getFileId(), attachFileInfo);
                                        }
                                    }
                                } else {
                                    edtOtp.setText("");
                                    AlertDialogManager.showAlertDialog(context, context.getString(R.string.str_dialog_thongbao),
                                            context.getString(R.string.str_dialog_kychuathanhcong), true, AlertDialogManager.ERROR);
                                }

                            }
                        }

                        @Override
                        public void onCallError(Object object) {
                            progressDialog.hideProgressDialog();
                            edtOtp.setText("");
                            APIError apiError = (APIError) object;
                            sendExceptionError(apiError);
                            DialogSignErorrs(apiError.getMessage());
                        }
                    };
                    progressDialog.showProgressDialog();

                    documentWaitingDao.signServiceOtp(edtOtp.getText().toString(), callFinishedListener);

                } else {
                    Toast.makeText(context, context.getString(R.string.str_nhap_ma_otp), Toast.LENGTH_SHORT).show();
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
        progressDialog = new DialogProgress(context, context.getString(R.string.DOWNLOADING_REQUEST));
        documentWaitingDao = new DocumentWaitingDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {
                if (object instanceof SigningRespone) {
                    progressDialog.hideProgressDialog();
                    if (((SigningRespone) object).getResponeAPI().getCode().equals("0")) {
                        if (((SigningRespone) object).getData().equalsIgnoreCase("true")) {
                            Toast.makeText(context, context.getString(R.string.tv_guilai_otp), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(context, context.getString(R.string.str_dialog_thongbao),
                                context.getString(R.string.str_dialog_resend_otp_faild), true, AlertDialogManager.ERROR);
                    }

                }
            }

            @Override
            public void onCallError(Object object) {
                progressDialog.hideProgressDialog();
                APIError apiError = (APIError) object;
                sendExceptionError(apiError);
                DialogSignErorrs(apiError.getMessage());
            }
        };
        progressDialog.showProgressDialog();

        documentWaitingDao.resendServiceOtp(callFinishedListener);
    }

    private void signdocumentPKIAttach(final int docid, int fileid, final String commnet, String hanxuly, final AttachFileInfo attachFileInfo) {
        progressDialog = new DialogProgress(context, context.getString(R.string.DOWNLOADING_REQUEST));
        documentWaitingDao = new DocumentWaitingDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {
                hanXyLySign = "";
                commentSign = "";
                if (object instanceof SigningRespone) {
                    progressDialog.hideProgressDialog();
                    if (((SigningRespone) object).getResponeAPI().getCode().equals("0")) {
                        dialog_PKI_or_CAserver_or_server_Success(attachFileInfo);
                    }

                }
            }

            @Override
            public void onCallError(Object object) {
                progressDialog.hideProgressDialog();
                APIError apiError = (APIError) object;
                sendExceptionError(apiError);
                DialogSignErorrs(apiError.getMessage());
            }
        };
        progressDialog.showProgressDialog();

        documentWaitingDao.onKyVanBanPKI(new SignDocumentRequest(docid, fileid, hanxuly, commnet), callFinishedListener);
    }

    private void dialog_PKI_or_CAserver_or_server_Success(final AttachFileInfo attachFileInfo) {
        commentSign = "";
        hanXyLySign = "";
        attachFileInfo.setIsKy("true");
        attachFileInfo.setBtnChuyen("true");
        attachFileInfo.setEditFile("false");
        attachFileInfoCustom = attachFileInfo;
        Toast.makeText(context, context.getString(R.string.str_ky_thanhcong), Toast.LENGTH_SHORT).show();
        if (context instanceof DetailDocumentWaitingActivity) {
            ((DetailDocumentWaitingActivity) context).getAttachFiles();
        }
    }

    private void DialogSignErorrs(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.MARK_SIGN_TITLE_ERROR));
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogss, int id) {
                if (dialogViewFile != null && dialogViewFile.isShowing()) {
                    dialogViewFile.cancel();
                }
                dialogss.cancel();


            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemAttackFile;
        private ImageView ic_file;
        private TextView filename;
        private ImageView btn_Ca_server_file;
        private ImageView btn_server_file;
        private ImageView btn_pki_file;
        private TextView info;

        public ViewHolder(View itemView) {
            super(itemView);
            itemAttackFile = itemView.findViewById(R.id.itemAttackFile);
            ic_file = itemView.findViewById(R.id.ic_file);
            filename = itemView.findViewById(R.id.filename);
            btn_Ca_server_file = itemView.findViewById(R.id.btn_ca_server_file);
            btn_server_file = itemView.findViewById(R.id.btn_server_file);
            btn_pki_file = itemView.findViewById(R.id.btn_pki_file);
            info = itemView.findViewById(R.id.info);
        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(KiThanhCongEvent kiThanhCongEvent) {
        if (kiThanhCongEvent != null && kiThanhCongEvent.getKyThanhCong() == 1) {
            dialog_PKI_or_CAserver_or_server_Success(attachFileInfoCustom);
        }
        EventBus.getDefault().removeStickyEvent(KiThanhCongEvent.class);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(KiThatbaiEvent kiThatbaiEvent) {
        if (kiThatbaiEvent != null && kiThatbaiEvent.isKyThatbai() == true) {
            if (dialogViewFile != null && dialogViewFile.isShowing()) {
                dialogViewFile.cancel();
            }
        }
        EventBus.getDefault().removeStickyEvent(KiThatbaiEvent.class);
    }


}
