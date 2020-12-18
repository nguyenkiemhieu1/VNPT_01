package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.ChiDaoFlow;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.userinfo.IUserInfoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.userinfo.UserInfoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.FileChiDaoActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ForwardChiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ListReceiveActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ReplyChiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ChiDaoFlowsEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.CloseProgressEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ResponseChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IUserAvatarView;

/**
 * Created by LinhLK - 0948012236 on 9/5/17.
 */

public class ReplyChiDaoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChiDaoFlow> datalist;
    private Context mContext;
    public final int TYPE_NEW = 0;
    public final int TYPE_LOAD = 1;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements IUserAvatarView {
        @BindView(R.id.img_anh_dai_dien)
        ImageView imgAnhDaiDien;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.btnFile)
        ImageView btnFile;
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtContent)
        TextView txtContent;
        @BindView(R.id.btnReply)
        LinearLayout btnReply;
        @BindView(R.id.btnForward)
        LinearLayout btnForward;
        @BindView(R.id.btnReplyAll)
        LinearLayout btnReplyAll;
        @BindView(R.id.txtNguoiNhan)
        TextView txtNguoiNhan;
        @BindView(R.id.layoutSubReply)
        LinearLayout layoutSubReply;
        @BindView(R.id.rvReplys)
        RecyclerView rvReplys;
        private ChiDaoFlow item;
        private IUserInfoPresenter userInfoPresenter = new UserInfoPresenterImpl(this);

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindData(final ChiDaoFlow newItem) {
            item = newItem;
            txtName.setText(newItem.getTenNguoiTao());
            txtDate.setText(newItem.getNgayTao());
            txtTitle.setText(newItem.getTieuDe());
            if (newItem.getNoiDung() != null && !newItem.getNoiDung().trim().equals("")) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    txtContent.setText(Html.fromHtml(newItem.getNoiDung()));
                } else {
                    txtContent.setText(Html.fromHtml(newItem.getNoiDung(), Html.FROM_HTML_MODE_COMPACT));
                }
            } else {
                txtContent.setText("");
            }
            if (newItem.getFileCount() > 0) {
                btnFile.setVisibility(View.VISIBLE);
            } else {
                btnFile.setVisibility(View.GONE);
            }
            String nguoiNhan = "";
            String[] nguoiNhans = newItem.getUserCount().split("\\|");
            if (nguoiNhans[1].equals("1")) {
                nguoiNhan += mContext.getString(R.string.str_ban);
            }
            if (!nguoiNhans[0].equals("0")) {
                if (nguoiNhans[1].equals("1")) {
                    nguoiNhan += " và " + nguoiNhans[0] + " "+ mContext.getString(R.string.str_nguoi_khac);
                } else {
                    nguoiNhan += nguoiNhans[0] + " "+ mContext.getString(R.string.str_nguoi_khac);
                }
            }
            txtNguoiNhan.setText(nguoiNhan);
            List<ChiDaoFlow> chiDaoFlows = EventBus.getDefault().getStickyEvent(ChiDaoFlowsEvent.class).getChiDaoFlows();
            List<ChiDaoFlow> subFlows = new ArrayList<>();
            for (int i = 0 ; i < chiDaoFlows.size(); i++) {
                if (chiDaoFlows.get(i).getParentId() != null && chiDaoFlows.get(i).getParentId().trim().equals(newItem.getId())) {
                    subFlows.add(chiDaoFlows.get(i));
                }
            }
            if (subFlows.size() > 0) {
                layoutSubReply.setVisibility(View.VISIBLE);
                SubReplyChiDaoAdapter adapter = new SubReplyChiDaoAdapter(mContext, subFlows);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                rvReplys.setLayoutManager(mLayoutManager);
                rvReplys.setItemAnimator(new DefaultItemAnimator());
                rvReplys.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                layoutSubReply.setVisibility(View.GONE);
            }
            userInfoPresenter.getAvatar(newItem.getUserId());
        }

        @OnClick({R.id.btnFile, R.id.btnReply, R.id.btnForward, R.id.btnReplyAll, R.id.txtNguoiNhan})
        public void onViewClicked(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.btnFile:
                    intent = new Intent(mContext, FileChiDaoActivity.class);
                    intent.putExtra("id", item.getId());
                    mContext.startActivity(intent);
                    break;
                case R.id.btnReply:
                    EventBus.getDefault().postSticky(new CloseProgressEvent(false));
//                    intent = new Intent(mContext, ReplyChiDaoActivity.class);
                    intent = new Intent(mContext, ReplyChiDaoV2Activity.class);
                    intent.putExtra("parentId", item.getId());
                    intent.putExtra("typeReply", "1");
                    intent.putExtra("title", "TrL: " + item.getTieuDe());
                    mContext.startActivity(intent);
                    break;
                case R.id.btnForward:
                    EventBus.getDefault().postSticky(new CloseProgressEvent(false));
//                    intent = new Intent(mContext, ForwardChiDaoActivity.class);
                    intent = new Intent(mContext, ForwardChiDaoV2Activity.class);
                    EventBus.getDefault().postSticky(new ResponseChiDaoEvent(item));
                    mContext.startActivity(intent);
                    break;
                case R.id.btnReplyAll:
                    EventBus.getDefault().postSticky(new CloseProgressEvent(false));
//                    intent = new Intent(mContext, ReplyChiDaoActivity.class);
                    intent = new Intent(mContext, ReplyChiDaoV2Activity.class);
                    intent.putExtra("parentId", item.getId());
                    intent.putExtra("typeReply", "2");
                    intent.putExtra("title", "TrL: " + item.getTieuDe());
                    mContext.startActivity(intent);
                    break;
                case R.id.txtNguoiNhan:
                    intent = new Intent(mContext, ListReceiveActivity.class);
                    intent.putExtra("id", item.getId());
                    mContext.startActivity(intent);
                    break;
            }
        }

        @Override
        public void onErrorAvatar(APIError apiError) {

        }

        @Override
        public void onSuccessAvatar(byte[] bitmap) {
            Glide.with(mContext).load(bitmap).error(R.drawable.ic_avatar)
                    .bitmapTransform(new CircleTransform(mContext)).into(imgAnhDaiDien);
        }
    }

    public ReplyChiDaoAdapter(Context mContext, List<ChiDaoFlow> datalist) {
        this.mContext = mContext;
        this.datalist = datalist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_NEW) {
            return new MyViewHolder(inflater.inflate(R.layout.item_reply, parent, false));
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
        if (datalist.get(position) instanceof ChiDaoFlow) {
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
