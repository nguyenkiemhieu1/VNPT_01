package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.FileUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.downloadfile.DownloadFileDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.login.LoginDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ImageViewActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;

/**
 * Created by LinhLK - 0948012236 on 9/5/17.
 */

public class ListFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FileChiDao> datalist;
    private Context mContext;
    public final int TYPE_NEW = 0;
    public final int TYPE_LOAD = 1;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    private File fileDownload;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements ICallFinishedListener, IExceptionErrorView {
        @BindView(R.id.ic_file)
        ImageView icFile;
        @BindView(R.id.filename)
        TextView filename;
        @BindView(R.id.btnDownload)
        LinearLayout btnDownload;
        @BindView(R.id.itemAttackFile)
        LinearLayout itemAttackFile;
        private ConnectionDetector connectionDetector;
        private ProgressDialog progressDialog;
        private LoginDao loginDao;
        private DownloadFileDao downloadFileDao;
        private FileChiDao item;
        private String event;
        private ApplicationSharedPreferences appPrefs;
        private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindData(final FileChiDao newItem) {
            item = newItem;
            filename.setText(newItem.getName() != null && !newItem.getName().trim().equals("") ? newItem.getName().trim() : "Noname");
            if (newItem.getName() != null && !newItem.getName().equals("")) {
                if (newItem.getName().trim().toUpperCase().endsWith(Constants.DOC) || newItem.getName().trim().toUpperCase().endsWith(Constants.DOCX)) {
                    icFile.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_file_word));
                }
                if (newItem.getName().trim().toUpperCase().endsWith(Constants.XLS) || newItem.getName().trim().toUpperCase().endsWith(Constants.XLSX)) {
                    icFile.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_file_xls));
                }
                if (newItem.getName().trim().toUpperCase().endsWith(Constants.PDF)) {
                    icFile.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_file_pdf));
                }
                if (newItem.getName().trim().toUpperCase().endsWith(Constants.JPG)
                        || newItem.getName().trim().toUpperCase().endsWith(Constants.JPEG)
                        || newItem.getName().trim().toUpperCase().endsWith(Constants.PNG)
                        || newItem.getName().trim().toUpperCase().endsWith(Constants.GIF)
                        || newItem.getName().trim().toUpperCase().endsWith(Constants.TIFF)
                        || newItem.getName().trim().toUpperCase().endsWith(Constants.BMP)) {
                    icFile.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_file_image));
                }
            } else {
                if (newItem.getHdd() == null || newItem.getHdd().trim().equals("")) {
                    btnDownload.setAlpha(0.6f);
                    btnDownload.setEnabled(false);
                }
                icFile.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_file_none));
            }
        }

        @OnClick({R.id.btnDownload, R.id.itemAttackFile})
        public void onViewClicked(View view) {
            connectionDetector = new ConnectionDetector(mContext);
            progressDialog = new ProgressDialog(mContext);
            downloadFileDao = new DownloadFileDao();
            progressDialog.setMessage(mContext.getString(R.string.DOWNLOADING_REQUEST));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
            switch (view.getId()) {
                case R.id.btnDownload:
                    if (connectionDetector.isConnectingToInternet()) {
                        if (item.getHdd() != null && !item.getHdd().trim().equals("")) {
                            event = "DOWNLOAD";
                            downloadFileDao.onDownloadFileChiDaoDao(new DownloadChiDaoRequest(item.getHdd().trim()), this);
                        } else {
                            AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.DOWNLOAD_TITLE_ERROR), mContext.getString(R.string.NO_URL_ERROR), true, AlertDialogManager.ERROR);
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                    }
                    break;
                case R.id.itemAttackFile:
                    if (connectionDetector.isConnectingToInternet()) {
                        if (item.getHdd() != null && !item.getHdd().trim().equals("")) {
                            event = "DOWNLOADandVIEW";
                            downloadFileDao.onDownloadFileChiDaoDao(new DownloadChiDaoRequest(item.getHdd().trim()), this);
                        } else {
                            AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.DOWNLOAD_TITLE_ERROR), mContext.getString(R.string.NO_URL_ERROR), true, AlertDialogManager.ERROR);
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                    }
                    break;
            }
        }

        @Override
        public void onCallSuccess(Object object) {
            if (object instanceof ResponseBody) {
                ResponseBody responseBody = (ResponseBody) object;
                progressDialog.dismiss();
                FileUtils fileUtils = new FileUtils(mContext);
                fileDownload = fileUtils.writeResponseBodyToDisk(responseBody, item.getName());
                if (fileDownload != null) {
                    if (event.equals("DOWNLOAD")) {
                        Toast.makeText(mContext, mContext.getString(R.string.DOWNLOAD_FILE_SUCCESS), Toast.LENGTH_LONG).show();
                    }
                    if (event.equals("DOWNLOADandVIEW")) {
                        if (item.getName() != null && !item.getName().equals("")) {
                            String ext = fileUtils.validateExtension(item.getName());
                            if (ext != null) {
                                if (ext.endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.PDF)) {
                                    fileUtils.openPDF(fileDownload);
                                } else {
                                    if (ext.endsWith(Constants.XLS) || ext.endsWith(Constants.XLSX)) {
                                        fileUtils.openExcel(fileDownload);
                                    } else {
                                        if (ext.endsWith(Constants.DOC) || ext.endsWith(Constants.DOCX)) {
                                            fileUtils.openWord(fileDownload);
                                        } else {
                                            EventBus.getDefault().postSticky(fileDownload);
                                            mContext.startActivity(new Intent(mContext, ImageViewActivity.class));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.DOWNLOAD_TITLE_ERROR), mContext.getString(R.string.DOWNLOAD_FILE_ERROR), true, AlertDialogManager.ERROR);
                }
            }
            if (object instanceof LoginRespone) {
                LoginRespone loginRespone = (LoginRespone) object;
                if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                    LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
                    Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
                    if (connectionDetector.isConnectingToInternet()) {
                        if (item.getHdd() != null && !item.getHdd().trim().equals("")) {
                            downloadFileDao.onDownloadFileChiDaoDao(new DownloadChiDaoRequest(item.getHdd().trim()), this);
                        } else {
                            progressDialog.dismiss();
                            AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.DOWNLOAD_TITLE_ERROR), mContext.getString(R.string.NO_URL_ERROR), true, AlertDialogManager.ERROR);
                        }
                    } else {
                        progressDialog.dismiss();
                        AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
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
                    loginDao.onSendLoginDao(Application.getApp().getAppPrefs().getAccount(), this);
                } else {
                    AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            } else {
                AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.DOWNLOAD_TITLE_ERROR), mContext.getString(R.string.DOWNLOAD_FILE_ERROR), true, AlertDialogManager.ERROR);
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
            appPrefs = Application.getApp().getAppPrefs();
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

    public ListFileAdapter(Context mContext, List<FileChiDao> datalist) {
        this.mContext = mContext;
        this.datalist = datalist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_NEW) {
            return new MyViewHolder(inflater.inflate(R.layout.item_file_list, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }
        if (getItemViewType(position) == TYPE_NEW) {
            ((MyViewHolder) holder).bindData(datalist.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (datalist.get(position) instanceof FileChiDao) {
            return TYPE_NEW;
        } else {
            return TYPE_LOAD;
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
    */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public void removeAll() {
        int size = this.datalist.size();
        this.datalist.clear();
        notifyItemRangeRemoved(0, size);
    }

}
