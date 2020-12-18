package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobHandLingResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LienThongInfo;

public class HandlingPrgressAdapter extends RecyclerView.Adapter<HandlingPrgressAdapter.ViewHolder> {

    private List<GetListJobHandLingResponse.Data> listData;
    private Context mContext;

    public HandlingPrgressAdapter(List<GetListJobHandLingResponse.Data> listData, Context mContext) {
        this.listData = listData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HandlingPrgressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_handling_progress, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HandlingPrgressAdapter.ViewHolder holder, int position) {
        GetListJobHandLingResponse.Data item = listData.get(position);
        holder.tvUnitPerson.setText(item.getName());
        holder.tvHandlingContent.setText(item.getContent());
        holder.tvDateBegin.setText(item.getStartDate());
        holder.tvDateFinish.setText(item.getEndDate());
        holder.tvContentStatus.setText(item.getStatus());
        if (item.getFiles() != null && item.getFiles().size() > 0) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext.getApplicationContext());
            holder.recyclerViewFile.setLayoutManager(mLayoutManager);
            holder.recyclerViewFile.setItemAnimator(new DefaultItemAnimator());
            AttachFileCustomAdapter adapter = new AttachFileCustomAdapter(mContext, R.layout.item_file_attach_list, item.getFiles());
            holder.recyclerViewFile.setAdapter(adapter);
        }

    }

    @Override
    public int getItemCount() {
        return this.listData != null ? listData.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_unit_person)
        TextView tvUnitPerson;
        @BindView(R.id.tv_handling_content)
        TextView tvHandlingContent;
        @BindView(R.id.recycler_view_file)
        RecyclerView recyclerViewFile;
        @BindView(R.id.tv_content_status)
        TextView tvContentStatus;
        @BindView(R.id.tv_date_begin)
        TextView tvDateBegin;
        @BindView(R.id.tv_date_finish)
        TextView tvDateFinish;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
