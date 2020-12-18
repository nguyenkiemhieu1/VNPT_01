package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.userinfo.UserInfoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.NotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.NotifyActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.NotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.Notify;

/**
 * Created by ThaoPX - on 9/5/17.
 */

public class NotifyV2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NotifyInfo> datalist;
    private Context mContext;
    public final int TYPE_NEW = 0;
    public final int TYPE_LOAD = 1;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    private ConnectionDetector connectionDetector;
    private ICallFinishedListener callFinishedListener;
    private UserInfoDao userInfoDao;

    private NotifyActivity notifyActivity;
    private boolean tickAll;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtDate1) TextView txtDate1;
        @BindView(R.id.txtDate2) TextView txtDate2;
        @BindView(R.id.txtTitle) TextView txtTitle;
        @BindView(R.id.checkNotify) CheckBox checkNotify;
        @BindView(R.id.layoutNotifyDetail)
        LinearLayout layoutNotifyDetail;
        @BindView(R.id.img_anh_dai_dien)
        ImageView img_anh_dai_dien;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindData(final NotifyInfo newItem) {
            txtTitle.setTypeface(Application.getApp().getTypeface());
            txtDate1.setTypeface(Application.getApp().getTypeface());
            txtDate2.setTypeface(Application.getApp().getTypeface());
            txtTitle.setText(newItem.getTitle());

            if (newItem.getType() != null && Constants.TYPE_NOTIFY_SCHEDULE.contains(newItem.getType())) {
                Glide.with(mContext).load(R.drawable.icon_schedule_notify).error(R.drawable.icon_schedule_notify)
                        .bitmapTransform(new CircleTransform(mContext)).into(img_anh_dai_dien);
            } else {
                Glide.with(mContext).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                        .bitmapTransform(new CircleTransform(mContext)).into(img_anh_dai_dien);
                connectionDetector = new ConnectionDetector(mContext);
                if (connectionDetector.isConnectingToInternet()) {
                    callFinishedListener = new ICallFinishedListener() {
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
                        }
                    };
                    try {
                        userInfoDao = new UserInfoDao();
                        userInfoDao.onGetAvatarDao(callFinishedListener, newItem.getCreatedUser());
                    } catch (Exception ex) {

                    }
                }
            }

            try {
                String[] timeStr = newItem.getCreatedDate().split(" ");
                if (Constants.TYPE_NOTIFY_DOCUMENT.contains(newItem.getType())) {
                    txtDate1.setText(timeStr[0]);
                    txtDate2.setText(timeStr[1]);
                }
                if (Constants.TYPE_NOTIFY_SCHEDULE.contains(newItem.getType())) {
                    txtDate1.setText(timeStr[1]);
                    txtDate2.setText(timeStr[0]);
                    txtDate1.setTextColor(mContext.getResources().getColor(R.color.md_red_500));
                    txtDate2.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                } else {
                    txtDate1.setText(timeStr[0]);
                    txtDate2.setText(timeStr[1]);
                }
            } catch (Exception ex) {
                txtDate1.setText("");
                txtDate2.setText("");
            }
            if (tickAll) {
                checkNotify.setChecked(true);
            } else {
                checkNotify.setChecked(false);
            }
            checkNotify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NotifyEvent notifyEvent = EventBus.getDefault().getStickyEvent(NotifyEvent.class);
                    List<Notify> notifyList = notifyEvent.getNotifyList();
                    if (!checkNotify.isChecked()) {
                        for (int i = 0; i < notifyList.size(); i++) {
                            if (notifyList.get(i).getId().equals(newItem.getId())) {
                                notifyList.remove(i);
                                break;
                            }
                        }
                    } else {
                        notifyList.add(new Notify(newItem.getId()));
                    }
                    notifyEvent.setNotifyList(notifyList);
                    EventBus.getDefault().postSticky(notifyEvent);
                }
            });
            layoutNotifyDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String docID = "";
                    if(newItem.getLink() != null){
                        try {
                            docID = newItem.getLink().split("\\|")[1];
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    notifyActivity.getDetailNotify(newItem.getId(), docID);
                }
            });

        }

    }

    public NotifyV2Adapter(Context mContext, List<NotifyInfo> datalist, boolean tickAll) {
        this.mContext = mContext;
        this.datalist = datalist;
        this.tickAll = tickAll;
        this.notifyActivity = (NotifyActivity) mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_NEW) {
            return new MyViewHolder(inflater.inflate(R.layout.item_notify, parent, false));
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
        if (datalist.get(position) instanceof NotifyInfo) {
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
