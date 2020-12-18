package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentAdvanceSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailDocumentNotificationActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.DetailDocumentInfo;

/**
 * Created by LinhLK - 0948012236 on 9/5/17.
 */

public class DocumentAdvanceSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DocumentAdvanceSearchInfo> datalist;
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date) TextView tvDate;
        @BindView(R.id.tv_time) TextView tvTime;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_trang_thai) TextView tvTrangThai;
        @BindView(R.id.tv_kh) TextView tvKH;
        @BindView(R.id.tv_cqbh) TextView tvCQBH;
        @BindView(R.id.tv_ngay_vb) TextView tvNgayVB;
        @BindView(R.id.tv_do_khan) TextView tvDoKhan;
        @BindView(R.id.img_file_dinh_kem) ImageView imgFileDinhkem;
        @BindView(R.id.tv_trang_thai_label) TextView tvTrangThai_label;
        @BindView(R.id.tv_kh_label) TextView tvKH_label;
        @BindView(R.id.tv_cqbh_label) TextView tvCQBH_label;
        @BindView(R.id.tv_ngay_vb_label) TextView tvNgayVB_label;
        @BindView(R.id.tv_do_khan_label) TextView tvDoKhan_label;
        @BindView(R.id.tv_file_attach_label) TextView imgFileDinhkem_label;
        @BindView(R.id.itemDocument) LinearLayout itemDocument;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindData(final DocumentAdvanceSearchInfo newItem) {
            tvDate.setTypeface(Application.getApp().getTypeface());
            tvTime.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
            tvTitle.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
            tvTrangThai.setTypeface(Application.getApp().getTypeface());
            tvKH.setTypeface(Application.getApp().getTypeface());
            tvCQBH.setTypeface(Application.getApp().getTypeface());
            tvNgayVB.setTypeface(Application.getApp().getTypeface());
            tvDoKhan.setTypeface(Application.getApp().getTypeface());
            tvTrangThai_label.setTypeface(Application.getApp().getTypeface());
            tvKH_label.setTypeface(Application.getApp().getTypeface());
            tvCQBH_label.setTypeface(Application.getApp().getTypeface());
            tvNgayVB_label.setTypeface(Application.getApp().getTypeface());
            tvDoKhan_label.setTypeface(Application.getApp().getTypeface());
            imgFileDinhkem_label.setTypeface(Application.getApp().getTypeface());
            if (newItem.getNgayNhan() != null) {
                try {
                    String[] arr = newItem.getNgayNhan().split(" ");
                    tvTime.setText(arr[1]);
                    tvDate.setText(arr[0]);
                } catch (Exception ex) {
                    tvTime.setText("");
                    tvDate.setText("");
                }
            }
            if (newItem.getTrichYeu() != null) {
                tvTitle.setText(newItem.getTrichYeu());
            }
            if (newItem.getIsRead() != null) {
                if (newItem.getIsRead().equals(Constants.IS_READ)) {
                    tvTrangThai.setText(" " + mContext.getString(R.string.IS_READ));
                    tvTitle.setTextColor(mContext.getResources().getColor(R.color.md_black));
                }
                if (newItem.getIsRead().equals(Constants.IS_NOT_READ)) {
                    tvTrangThai.setText(" " + mContext.getString(R.string.IS_NOT_READ));
                    tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                }
            }
            if (newItem.getSoKihieu() != null) {
                tvKH.setText(" " + newItem.getSoKihieu());
            }
            if (newItem.getCoQuanBanHanh() != null) {
                tvCQBH.setText(newItem.getCoQuanBanHanh());
            }
            if (newItem.getNgayVanBan() != null) {
                tvNgayVB.setText(" " + newItem.getNgayVanBan());
            }
            if (newItem.getDoKhan() != null) {
                tvDoKhan.setText(" " + newItem.getDoKhan());
                if (newItem.getDoKhan().equals(mContext.getString(R.string.str_thuong))) {
                    tvDoKhan.setTextColor(mContext.getResources().getColor(R.color.md_light_green_status));
                }
            }
            if (newItem.getHasFile() != null) {
                if (newItem.getHasFile().equals(Constants.HAS_FILE)) {
                    imgFileDinhkem.setVisibility(View.VISIBLE);
                }
                if (newItem.getHasFile().equals(Constants.HAS_NOT_FILE)) {
                    imgFileDinhkem.setVisibility(View.GONE);
                }
            } else {
                imgFileDinhkem.setVisibility(View.VISIBLE);
            }
            itemDocument.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(newItem);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(newItem.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                    mContext.startActivity(new Intent(mContext, DetailDocumentNotificationActivity.class));
                }
            });
        }

    }

    public DocumentAdvanceSearchAdapter(Context mContext, List<DocumentAdvanceSearchInfo> datalist) {
        this.mContext = mContext;
        this.datalist = datalist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_NEW) {
            return new MyViewHolder(inflater.inflate(R.layout.document_search, parent, false));
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
        if (datalist.get(position) instanceof DocumentAdvanceSearchInfo) {
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
