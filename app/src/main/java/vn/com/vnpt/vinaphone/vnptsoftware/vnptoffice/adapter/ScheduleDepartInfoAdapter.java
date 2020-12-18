package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleDepartInfo;

public class ScheduleDepartInfoAdapter extends RecyclerView.Adapter<ScheduleDepartInfoAdapter.MyViewHolder> {
    private Context context;
    private List<ScheduleDepartInfo> scheduleDepartInfosleInfos;

    public ScheduleDepartInfoAdapter(Context context, List<ScheduleDepartInfo> scheduleDepartInfosleInfos) {
        this.context = context;
        this.scheduleDepartInfosleInfos = scheduleDepartInfosleInfos;
    }

    @NonNull
    @Override
    public ScheduleDepartInfoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_content_schedule_depart, viewGroup, false);
        return new ScheduleDepartInfoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleDepartInfoAdapter.MyViewHolder holder, int position) {
        final ScheduleDepartInfo scheduleDepartInfo = scheduleDepartInfosleInfos.get(position);
        holder.tieuDe.setText(scheduleDepartInfo.getTitle());
        holder.diaDiem.setText(scheduleDepartInfo.getLocation());
        holder.thanhPhan.setText(scheduleDepartInfo.getAttendee());
    }

    @Override
    public int getItemCount() {
        return scheduleDepartInfosleInfos != null && scheduleDepartInfosleInfos.size() > 0 ? scheduleDepartInfosleInfos.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tieuDe;
        private TextView diaDiem;
        private TextView thanhPhan;

        public MyViewHolder(View view) {
            super(view);
            tieuDe = (TextView) view.findViewById(R.id.tieuDe);
            thanhPhan = (TextView) view.findViewById(R.id.thanhPhan);
            diaDiem = (TextView) view.findViewById(R.id.diaDiem);

        }

    }
}
