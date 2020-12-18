package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.chidao.ChiDaoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.login.LoginDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.ChiDaoGui;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.ChiDaoNhan;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DeleteChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailChiDaoActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.EditchiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.FileChiDaoActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.LoginEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;

/**
 * Created by LinhLK - 0948012236 on 9/5/17.
 */

public class ChiDaoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IExceptionErrorView {

    private List<Object> datalist;
    private Context mContext;
    private int type;
    public final int TYPE_NEW = 0;
    public final int TYPE_LOAD = 1;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    @Override
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date_label)
        TextView tvDateLabel;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.txtSubject)
        TextView txtSubject;
        @BindView(R.id.tv_person_label)
        TextView tvPersonLabel;
        @BindView(R.id.tv_status_lable)
        TextView tvStatusLabel;
        @BindView(R.id.tv_person)
        TextView tvPerson;
        @BindView(R.id.tv_file_lable)
        TextView tvFileDinhKemLable;
        @BindView(R.id.img_file_dinh_kem)
        ImageView imgFileDinhKem;
        @BindView(R.id.img_click)
        ImageView imgClick;
        @BindView(R.id.itemChiDao)
        ConstraintLayout itemChiDao;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        private ConnectionDetector connectionDetector;
        private ICallFinishedListener iCallFinishedListener;
        private LoginDao loginDao;
        private ChiDaoDao chiDaoDao;
        private Object chidao;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindData(final Object newItem) {
            chidao = newItem;
            if (type == 0) {
                ChiDaoNhan chiDaoNhan = ConvertUtils.fromJSON(chidao, ChiDaoNhan.class);
                txtSubject.setText(chiDaoNhan.getTieuDe());
                if (chiDaoNhan.getIsRead().equals("1")) {
                    txtSubject.setTextColor(mContext.getResources().getColor(R.color.md_grey_800));
                }
                if (chiDaoNhan.getIsRead().equals("0")) {
                    txtSubject.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                }
                tvDateLabel.setText(mContext.getResources().getString(R.string.tv_date_receive));
                tvDate.setText(chiDaoNhan.getNgayNhan());
                tvPersonLabel.setText(mContext.getResources().getString(R.string.tv_person_send));
                tvPerson.setText(chiDaoNhan.getNguoiGui());
                imgClick.setVisibility(View.GONE);

                if (chiDaoNhan.getFileCount() == 0) {
                    tvFileDinhKemLable.setVisibility(View.GONE);
                    imgFileDinhKem.setVisibility(View.GONE);

                } else {
                    tvFileDinhKemLable.setVisibility(View.VISIBLE);
                    imgFileDinhKem.setVisibility(View.VISIBLE);
                }

                tvStatusLabel.setVisibility(View.GONE);
                tvStatus.setVisibility(View.GONE);
            }
            if (type == 1) {
                ChiDaoGui chiDaoGui = ConvertUtils.fromJSON(chidao, ChiDaoGui.class);
                txtSubject.setText(chiDaoGui.getTieuDe());
                tvDateLabel.setText(mContext.getResources().getString(R.string.tv_date_create));

                tvPersonLabel.setVisibility(View.GONE);
                tvPerson.setVisibility(View.GONE);


                if (chiDaoGui.getFileCount() == 0) {
                    tvFileDinhKemLable.setVisibility(View.GONE);
                    imgFileDinhKem.setVisibility(View.GONE);
                } else {
                    tvFileDinhKemLable.setVisibility(View.VISIBLE);
                    imgFileDinhKem.setVisibility(View.VISIBLE);
                }
                tvStatusLabel.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.VISIBLE);

                tvDate.setText(chiDaoGui.getNgayTao());

                imgClick.setVisibility(View.VISIBLE);

                if (chiDaoGui.getDaGui() == null) {
                    tvStatus.setText(mContext.getResources().getString(R.string.tv_draft));
                    tvStatus.setTextColor(mContext.getResources().getColor(R.color.colorTextRed));
                } else {
                    if (chiDaoGui.getDaGui().equals("1")) {
                        tvStatus.setText(mContext.getResources().getString(R.string.tv_send));
                        tvStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    }
                }
            }
        }

        @OnClick({R.id.img_file_dinh_kem, R.id.img_click, R.id.itemChiDao})
        public void onViewClicked(View view) {
            ChiDaoGui chiDaoGui = null;
            ChiDaoNhan chiDaoNhan = null;
            if (type == 0) {
                chiDaoNhan = ConvertUtils.fromJSON(chidao, ChiDaoNhan.class);
            }
            if (type == 1) {
                chiDaoGui = ConvertUtils.fromJSON(chidao, ChiDaoGui.class);
            }
            switch (view.getId()) {
                case R.id.img_file_dinh_kem:
                    Intent i = new Intent(mContext, FileChiDaoActivity.class);
                    i.putExtra("id", chiDaoGui != null ? chiDaoGui.getId() : chiDaoNhan.getId());
                    mContext.startActivity(i);
                    break;
                case R.id.img_click:
                    List<String> labels = new ArrayList<String>();
                    final List<String> tags = new ArrayList<String>();
                    labels.add(mContext.getString(R.string.str_sua));
                    tags.add(Constants.EDIT_TAG);
                    if (chiDaoGui.getDaGui() == null || !chiDaoGui.getDaGui().equals("1")) {
                        labels.add(mContext.getString(R.string.str_xoa));
                        tags.add(Constants.REMOVE_TAG);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.weight_table_menu, R.id.textViewTableMenuItem, labels);
                    final ListPopupWindow listPopupWindow = new ListPopupWindow(mContext);
                    listPopupWindow.setAdapter(adapter);
                    listPopupWindow.setAnchorView(imgClick);
                    listPopupWindow.setWidth(420);
                    listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
                    listPopupWindow.setHorizontalOffset(-402); // margin left
                    listPopupWindow.setVerticalOffset(-20);
                    listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String tag = tags.get(i);
                            listPopupWindow.dismiss();
                            switch (tag) {
                                case Constants.EDIT_TAG:
                                    ChiDaoGui chidaoGui = ConvertUtils.fromJSON(chidao, ChiDaoGui.class);
//                                    Intent intent = new Intent(mContext, EditChiDaoActivity.class);
                                    Intent intent = new Intent(mContext, EditchiDaoV2Activity.class);
                                    intent.putExtra("id", chidaoGui.getId());
                                    mContext.startActivity(intent);
                                    break;
                                case Constants.REMOVE_TAG:
                                    chiDaoDao = new ChiDaoDao();
                                    connectionDetector = new ConnectionDetector(mContext);
                                    if (connectionDetector.isConnectingToInternet()) {
                                        iCallFinishedListener = new ICallFinishedListener() {
                                            @Override
                                            public void onCallSuccess(Object object) {
                                                if (object instanceof LoginRespone) {
                                                    LoginRespone loginRespone = (LoginRespone) object;
                                                    if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                                                        LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
                                                        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
                                                        if (connectionDetector.isConnectingToInternet()) {
                                                            if (EventBus.getDefault().getStickyEvent(LoginEvent.class).getAction().equals(Constants.MARK_ACTION)) {
                                                                ChiDaoGui chiDaoGui = ConvertUtils.fromJSON(chidao, ChiDaoGui.class);
                                                                chiDaoDao.onDelete(chiDaoGui.getId(), this);
                                                            }
                                                        } else {
                                                            AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                                                        }
                                                    }
                                                }
                                                if (object instanceof DeleteChiDaoRespone) {
                                                    DeleteChiDaoRespone deleteChiDaoRespone = (DeleteChiDaoRespone) object;
                                                    if (deleteChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                                                        datalist.remove(chidao);
                                                        Toast.makeText(mContext, mContext.getString(R.string.DELETE_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
                                                        notifyDataSetChanged();
                                                    } else {
                                                        AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.TITLE_NOTIFICATION), mContext.getString(R.string.DELETE_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
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
                                                    AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.TITLE_NOTIFICATION), mContext.getString(R.string.DELETE_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
                                                }
                                            }
                                        };
                                        ChiDaoGui chiDaoGui = ConvertUtils.fromJSON(chidao, ChiDaoGui.class);
                                        chiDaoDao.onDelete(chiDaoGui.getId(), iCallFinishedListener);
                                    } else {
                                        AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                                    }
                                    break;
                            }
                        }
                    });
                    listPopupWindow.show();
                    break;
                case R.id.itemChiDao:
                    if ((chiDaoGui != null && chiDaoGui.getDaGui() != null) || chiDaoNhan != null) {
                        Intent intent = new Intent(mContext, DetailChiDaoActivity.class);
                        intent.putExtra("ID_CHIDAO", chiDaoGui != null ? chiDaoGui.getId() : chiDaoNhan.getId());
                        mContext.startActivity(intent);
                    }
                    if (chiDaoGui != null && chiDaoGui.getDaGui() == null) {
//                        Intent intent = new Intent(mContext, EditChiDaoActivity.class);
                        Intent intent = new Intent(mContext, EditchiDaoV2Activity.class);
                        intent.putExtra("id", chiDaoGui.getId());
                        mContext.startActivity(intent);
                    }
                    break;
            }
        }

    }

    public ChiDaoAdapter(Context mContext, List<Object> datalist, int type) {
        this.mContext = mContext;
        this.datalist = datalist;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_NEW) {
            return new MyViewHolder(inflater.inflate(R.layout.chi_dao, parent, false));
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
        if (datalist.get(position) instanceof Object) {
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
