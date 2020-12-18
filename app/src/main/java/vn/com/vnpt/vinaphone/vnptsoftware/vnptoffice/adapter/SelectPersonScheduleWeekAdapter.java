package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleWeekInfo;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class SelectPersonScheduleWeekAdapter extends RecyclerView.Adapter<SelectPersonScheduleWeekAdapter.MyViewHolder> {
    private Context context;
    private List<PersonAndUnitScheduleWeekInfo> listPerson;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private Button btn_remove;


        public MyViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            btn_remove = view.findViewById(R.id.btn_remove);
        }

    }

    @Override
    public SelectPersonScheduleWeekAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_person_schedule_week, parent, false);
        return new SelectPersonScheduleWeekAdapter.MyViewHolder(itemView);
    }

    public SelectPersonScheduleWeekAdapter(Context context, List<PersonAndUnitScheduleWeekInfo> scheduleWeekInfos) {
        this.context = context;
        this.listPerson = scheduleWeekInfos;
    }

    @Override
    public void onBindViewHolder(SelectPersonScheduleWeekAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final PersonAndUnitScheduleWeekInfo person = listPerson.get(position);
        holder.tv_name.setText(person.getName());
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPerson.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, listPerson.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPerson.size();
    }
}
