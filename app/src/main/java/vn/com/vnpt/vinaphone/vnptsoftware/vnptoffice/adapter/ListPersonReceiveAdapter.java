package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.userinfo.UserInfoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.PersonReceiveChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;

/**
 * Created by LinhLK - 0948012236 on 9/5/17.
 */

public class ListPersonReceiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IExceptionErrorView {
    private List<PersonReceiveChiDao> datalist;
    private Context mContext;
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

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements ICallFinishedListener {
        @BindView(R.id.txtName) TextView txtName;
        @BindView(R.id.txtUnit) TextView txtUnit;
        @BindView(R.id.txtEmail) TextView txtEmail;
        @BindView(R.id.txtStatus) TextView txtStatus;
        @BindView(R.id.txtHour) TextView txtHour;
        @BindView(R.id.txtDate) TextView txtDate;
        @BindView(R.id.txtNgayXem) TextView txtNgayXem;
        @BindView(R.id.layout_trangthai) LinearLayout layout_trangthai;
        @BindView(R.id.layout_ngayxem) LinearLayout layout_ngayxem;
        @BindView(R.id.img_anh_dai_dien) ImageView img_anh_dai_dien;

        private ConnectionDetector connectionDetector;
        private UserInfoDao userInfoDao;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindData(final PersonReceiveChiDao person) {
            txtName.setText(person.getFullName());
            txtUnit.setText(person.getUnitName());
            txtEmail.setText(person.getEmail());
            if (person.getNgayNhan() != null) {
                try {
                    String[] arr = person.getNgayNhan().split(" ");
                    txtHour.setText(arr[1]);
                    txtDate.setText(arr[0]);
                } catch (Exception ex) {
                    txtHour.setText("");
                    txtDate.setText("");
                }
            }
            if (person.getNgayXem() == null) {
                txtStatus.setText(mContext.getResources().getString(R.string.tv_not_see));
                layout_ngayxem.setVisibility(View.GONE);
                layout_trangthai.setVisibility(View.VISIBLE);
            } else {
                txtNgayXem.setText(person.getNgayXem());
                layout_ngayxem.setVisibility(View.VISIBLE);
                layout_trangthai.setVisibility(View.GONE);
            }
            connectionDetector = new ConnectionDetector(mContext);
            if (connectionDetector.isConnectingToInternet()) {
                userInfoDao = new UserInfoDao();
                userInfoDao.onGetAvatarDao(this, person.getId());
            }
        }

        @Override
        public void onCallSuccess(Object object) {
            try {
                ResponseBody responseBody = (ResponseBody) object;
                Glide.with(mContext).load(responseBody.bytes()).error(R.drawable.ic_avatar)
                        .bitmapTransform(new CircleTransform(mContext)).into(img_anh_dai_dien);
            } catch (Exception ex) {

            }
        }

        @Override
        public void onCallError(Object object) {
            APIError apiError = (APIError) object;
            sendExceptionError(apiError);
        }
    }

    public ListPersonReceiveAdapter(Context mContext, List<PersonReceiveChiDao> datalist) {
        this.mContext = mContext;
        this.datalist = datalist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_NEW) {
            return new MyViewHolder(inflater.inflate(R.layout.item_person_receive, parent, false));
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
        if (datalist.get(position) instanceof PersonReceiveChiDao) {
            return TYPE_NEW;
        } else {
            return TYPE_LOAD;
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder{
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
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }

    public void removeAll(){
        int size = this.datalist.size();
        this.datalist.clear();
        notifyItemRangeRemoved(0, size);
    }

}
