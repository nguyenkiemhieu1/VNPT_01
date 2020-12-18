package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailScheduleActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.LichEvent;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class ScheduleWeekAdapter extends RecyclerView.Adapter<ScheduleWeekAdapter.MyViewHolder> {
    private Context context;
    private List<ScheduleWeekInfo> scheduleWeekInfos;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_description_title, tv_chair_title, tv_Join_title, tv_location_title, tv_note_title;
        private TextView tv_date, tv_description, tv_chair, tv_Join, tv_location, tv_note;
        private ConstraintLayout item_schedule_week;


        public MyViewHolder(View view) {
            super(view);
            tv_description_title = view.findViewById(R.id.tv_description_title);
            tv_chair_title = view.findViewById(R.id.tv_chair_title);
            tv_Join_title = view.findViewById(R.id.tv_Join_title);
            tv_location_title = view.findViewById(R.id.tv_location_title);
            tv_note_title = view.findViewById(R.id.tv_note_title);

            tv_date = view.findViewById(R.id.tv_date);
            tv_description = view.findViewById(R.id.tv_description);
            tv_chair = view.findViewById(R.id.tv_chair);
            tv_Join = view.findViewById(R.id.tv_Join);
            tv_location = view.findViewById(R.id.tv_location);
            tv_note = view.findViewById(R.id.tv_note);
            item_schedule_week = view.findViewById(R.id.item_schedule_week);

        }

    }

    @Override
    public ScheduleWeekAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_week, parent, false);
        return new ScheduleWeekAdapter.MyViewHolder(itemView);
    }

    public ScheduleWeekAdapter(Context context, List<ScheduleWeekInfo> scheduleWeekInfos) {
        this.context = context;
        this.scheduleWeekInfos = scheduleWeekInfos;
    }

    @Override
    public void onBindViewHolder(ScheduleWeekAdapter.MyViewHolder holder, int position) {
        final ScheduleWeekInfo scheduleWeekInfo = scheduleWeekInfos.get(position);
        holder.tv_date.setText(scheduleWeekInfo.getTime() + "  " + scheduleWeekInfo.getDate());
        holder.tv_description.setText(scheduleWeekInfo.getContent());
        holder.tv_chair.setText(scheduleWeekInfo.getChuTri());
        holder.tv_Join.setText(scheduleWeekInfo.getThamGia());
        holder.tv_location.setText(scheduleWeekInfo.getLocation());
        holder.tv_note.setText(scheduleWeekInfo.getNote());
        holder.item_schedule_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                EventBus.getDefault().postSticky(new LichEvent(scheduleWeekInfo.get(), scheduleBoss.getType()));
//                Intent intent = new Intent(context, DetailScheduleActivity.class);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleWeekInfos != null && scheduleWeekInfos.size() > 0 ? scheduleWeekInfos.size() : 0;
    }
}
