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

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleDepartBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleDepartInfo;

public class ScheduleBossDepartInfoAdapter extends RecyclerView.Adapter<ScheduleBossDepartInfoAdapter.MyViewHolder> {

    private Context context;
    private List<ScheduleDepartBossInfo> scheduleDepartBossInfos;

    public ScheduleBossDepartInfoAdapter(Context context, List<ScheduleDepartBossInfo> scheduleDepartBossInfos) {
        this.context = context;
        this.scheduleDepartBossInfos = scheduleDepartBossInfos;
    }

    @NonNull
    @Override
    public ScheduleBossDepartInfoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule_depart, viewGroup, false);
        return new ScheduleBossDepartInfoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleBossDepartInfoAdapter.MyViewHolder holder, int position) {
        final ScheduleDepartBossInfo scheduleDepartBossInfo = scheduleDepartBossInfos.get(position);
        holder.txtTime.setText(scheduleDepartBossInfo.getDate());
        List<ScheduleDepartInfo> dataSang = scheduleDepartBossInfos.get(position).getDataSang();
        List<ScheduleDepartInfo> dataChieu = scheduleDepartBossInfos.get(position).getDataChieu();
        ScheduleDepartInfoAdapter adapterSang = new ScheduleDepartInfoAdapter(context, dataSang);
        RecyclerView.LayoutManager mLayoutManagerSang = new LinearLayoutManager(context);
        holder.rcvSang.setLayoutManager(mLayoutManagerSang);
        holder.rcvSang.setItemAnimator(new DefaultItemAnimator());
        holder.rcvSang.setAdapter(adapterSang);
        holder.rcvSang.setNestedScrollingEnabled(true);
        adapterSang.notifyDataSetChanged();
        ScheduleDepartInfoAdapter adapterChieu = new ScheduleDepartInfoAdapter(context, dataChieu);
        RecyclerView.LayoutManager mLayoutManagerChieu = new LinearLayoutManager(context);
        holder.rcvChieu.setLayoutManager(mLayoutManagerChieu);
        holder.rcvChieu.setItemAnimator(new DefaultItemAnimator());
        holder.rcvChieu.setAdapter(adapterChieu);
        holder.rcvChieu.setNestedScrollingEnabled(true);
        adapterChieu.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return scheduleDepartBossInfos != null && scheduleDepartBossInfos.size() > 0 ? scheduleDepartBossInfos.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTime;
        private RecyclerView rcvSang;
        private RecyclerView rcvChieu;

        public MyViewHolder(View view) {
            super(view);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            rcvSang = (RecyclerView) view.findViewById(R.id.rcv_schedule_depart_sang);
            rcvChieu = (RecyclerView) view.findViewById(R.id.rcv_schedule_depart_chieu);

        }
    }
}
