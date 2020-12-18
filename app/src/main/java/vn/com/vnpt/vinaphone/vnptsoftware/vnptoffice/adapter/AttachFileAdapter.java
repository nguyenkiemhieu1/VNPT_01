package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.downloadfile.DownloadFileDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.login.LoginDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog.DialogProgress;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FileIdKyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.1
 */

public class AttachFileAdapter extends ArrayAdapter<AttachFileInfo> implements IExceptionErrorView {
    private Context context;
    private int resource;
    private List<AttachFileInfo> attachFileInfoList;
    private ConnectionDetector connectionDetector;
    private ICallFinishedListener callFinishedListener;
    private DialogProgress progressDialog;
    private LoginDao loginDao;
    private DownloadFileDao downloadFileDao;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private ApplicationSharedPreferences appPrefs;
    private boolean createFile;
    public AttachFileAdapter(Context context, int resource, List<AttachFileInfo> attachFileInfoList) {
        super(context, resource, attachFileInfoList);
        this.context = context;
        this.resource = resource;
        this.attachFileInfoList = attachFileInfoList;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        LinearLayout itemAttackFile = (LinearLayout) view.findViewById(R.id.itemAttackFile);
        ImageView ic_file = (ImageView) view.findViewById(R.id.ic_file);
        TextView filename = (TextView) view.findViewById(R.id.filename);
        TextView info = (TextView) view.findViewById(R.id.info);
        filename.setTypeface(Application.getApp().getTypeface());
        final AttachFileInfo attachFileInfo = attachFileInfoList.get(position);
        if (attachFileInfo.getName() != null) {
            filename.setText(attachFileInfo.getName());
        }

        if (attachFileInfo.getInfo() != null && attachFileInfo.getInfo().size() > 0) {
            info.setVisibility(View.VISIBLE);
            String ln = "";
            for (int i = 0; i < attachFileInfo.getInfo().size(); i++) {
                ln += attachFileInfo.getInfo().get(i) + "\n";
            }
            info.setText(ln);
        } else {
            info.setVisibility(View.GONE);
        }

        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.DOC) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.DOCX)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_doc));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.XLS) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.XLSX)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_xls));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PPT) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PPTX)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ppt));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PDF)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pdf));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.ZIP)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_zip));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.RAR)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_rar));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TXT)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_txt));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.MPP)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_mpp));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPEG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PNG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.GIF)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TIFF)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.BMP)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_image));
        }
        itemAttackFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                downloadFileAttactk(attachFileInfo);
                getViewFileDocument(attachFileInfo);
            }

        });
        return view;
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

    private void downloadFileAttactk(final AttachFileInfo attachFileInfo) {
        {
            connectionDetector = new ConnectionDetector(context);
            downloadFileDao = new DownloadFileDao();
            callFinishedListener = new ICallFinishedListener() {
                @Override
                public void onCallSuccess(Object object) {
                    if (object instanceof ResponseBody) {
                        progressDialog.hideProgressDialog();
                        ResponseBody responseBody = (ResponseBody) object;
                        if (responseBody != null) {
                            if (checkPermissonApp(responseBody, attachFileInfo.getName())) {
                                createFile = false;
                                AlertDialogManager.showAlertDialog(context, context.getString(R.string.DOWNLOAD_FILE_SUCCESS),
                                        context.getString(R.string.str_tai_file_toi_thu_muc) + "" + context.getString(R.string.app_name), true, AlertDialogManager.SUCCESS);
                                responseBody.close();
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

    private void viewFileDialog(InputStream inputStream, final String fileName, final AttachFileInfo attachFileInfo) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.layout_dialog_view_file);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title_label);
        ImageView ivCloseDialog = (ImageView) dialog.findViewById(R.id.ivCloseDialog);
        ImageView ivShareFile = (ImageView) dialog.findViewById(R.id.ivShareFile);
        ImageView btnDownFile = (ImageView) dialog.findViewById(R.id.ivDownFile);
        PDFView pdfView = dialog.findViewById(R.id.pdf_view);
        ImageView image_view_file = dialog.findViewById(R.id.image_view_file);

        tvTitle.setText(fileName);
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPEG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PNG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.GIF)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TIFF)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.BMP)) {
            pdfView.setVisibility(View.GONE);
            image_view_file.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                Glide.with(context).load(bitmap).into(image_view_file);
            image_view_file.setImageBitmap(bitmap);
        } else {

            pdfView.setVisibility(View.VISIBLE);
            image_view_file.setVisibility(View.GONE);
            pdfView.fromStream(inputStream).spacing(12).enableAnnotationRendering(true).enableAntialiasing(true).load();
        }
        dialog.show();
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

}
