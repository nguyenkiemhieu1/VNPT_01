package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailScheduleActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.LichEvent;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class WeekBossAdapter extends RecyclerView.Adapter<WeekBossAdapter.MyViewHolder> {
    private Context context;
    private List<ScheduleInfo> scheduleInfos;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTime;
        private TextView noiDung;
        private TextView chuTri;
        private TextView thanhPhan;
        private TextView diaDiem;
        private TextView ghiChu;
        private LinearLayout itemLich;


        public MyViewHolder(View view) {
            super(view);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            noiDung = (TextView) view.findViewById(R.id.noiDung);
            chuTri = (TextView) view.findViewById(R.id.chuTri);
            thanhPhan = (TextView) view.findViewById(R.id.thanhPhan);
            diaDiem = (TextView) view.findViewById(R.id.diaDiem);
            ghiChu = (TextView) view.findViewById(R.id.ghiChu);
            itemLich = (LinearLayout) view.findViewById(R.id.itemLich);

        }

    }

    @Override
    public WeekBossAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_calendar, parent, false);
        return new WeekBossAdapter.MyViewHolder(itemView);
    }

    public WeekBossAdapter(Context context, List<ScheduleInfo> scheduleInfos) {
        this.context = context;
        this.scheduleInfos = scheduleInfos;
    }

    @Override
    public void onBindViewHolder(WeekBossAdapter.MyViewHolder holder, int position) {
        final ScheduleInfo scheduleBoss = scheduleInfos.get(position);
        holder.txtTime.setText(scheduleBoss.getStartTime().split(" ")[1] + " - " + scheduleBoss.getTitle());
        holder.noiDung.setText(scheduleBoss.getContent());
        holder.chuTri.setText(scheduleBoss.getChuTri());
        holder.thanhPhan.setText(scheduleBoss.getParticipation());
        holder.diaDiem.setText(scheduleBoss.getPosition());
        holder.ghiChu.setText(scheduleBoss.getNote());
        holder.itemLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new LichEvent(scheduleBoss.getId(),scheduleBoss.getType()));
                Intent intent = new Intent(context, DetailScheduleActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleInfos != null && scheduleInfos.size() > 0 ? scheduleInfos.size() : 0;
    }
}
