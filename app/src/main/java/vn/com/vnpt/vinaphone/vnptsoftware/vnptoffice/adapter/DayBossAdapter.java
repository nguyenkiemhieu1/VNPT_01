package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleBoss;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ScheduleDayBoss;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class DayBossAdapter extends RecyclerView.Adapter<DayBossAdapter.MyViewHolder> {

    private Context context;
    private List<ScheduleBoss> scheduleBossList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtDonVi, txtSang, txtChieu;

        public MyViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtDonVi = (TextView) view.findViewById(R.id.txtDonVi);
            txtSang = (TextView) view.findViewById(R.id.txtSang);
            txtChieu = (TextView) view.findViewById(R.id.txtChieu);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_meeting, parent, false);
        return new MyViewHolder(itemView);
    }

    public DayBossAdapter(Context context, List<ScheduleBoss> scheduleBossList) {
        this.context = context;
        this.scheduleBossList = scheduleBossList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ScheduleBoss scheduleBoss = scheduleBossList.get(position);
        holder.txtName.setText(scheduleBoss.getUsername());
        holder.txtDonVi.setText(scheduleBoss.getPosition());
        List<ScheduleDayBoss> days = scheduleBoss.getParameters();
        String sang = "";
        String chieu = "";
        for (int i = 0; i < days.size(); i++) {
            ScheduleDayBoss day = days.get(i);
            if (day.getCodeTime().equals("SANG")) {
                if (day.getContent() != null && !day.getContent().trim().equals("")) {
                    sang += "<font color='#9e9e9e'>" + "Nội dung: " + "</font>" + day.getContent() + "<br/>";
                }
                if (day.getParticipation() != null && !day.getParticipation().trim().equals("")) {
                    sang += "<font color='#9e9e9e'>" + "Thành phần: " + "</font>" + day.getParticipation() + "<br/>";
                }
                if (day.getPlace() != null && !day.getPlace().trim().equals("")) {
                    sang += "<font color='#9e9e9e'>" + "Địa điểm: " + "</font>" + day.getPlace() + "<br/>";
                }
                if (!sang.equals("")) {
                    sang = sang.substring(0, sang.length() - 5);
                }
            }
            if (day.getCodeTime().equals("CHIEU")) {
                if (day.getContent() != null && !day.getContent().trim().equals("")) {
                    chieu += "<font color='#9e9e9e'>" + "Nội dung: " + "</font>" + day.getContent() + "<br/>";
                }
                if (day.getParticipation() != null && !day.getParticipation().trim().equals("")) {
                    chieu += "<font color='#9e9e9e'>" + "Thành phần: " + "</font>" + day.getParticipation() + "<br/>";
                }
                if (day.getPlace() != null && !day.getPlace().trim().equals("")) {
                    chieu += "<font color='#9e9e9e'>" + "Địa điểm: " + "</font>" + day.getPlace() + "<br/>";
                }
                if (!chieu.equals("")) {
                    chieu = chieu.substring(0, chieu.length() - 5);
                }
            }
        }
        if (sang.equals("")) {
            holder.txtSang.setText(Html.fromHtml("<font color='red'>" + "Không có lịch công tác" + "</font>"));
        } else {
            holder.txtSang.setText(Html.fromHtml(sang));
        }
        if (chieu.equals("")) {
            holder.txtChieu.setText(Html.fromHtml("<font color='red'>" + "Không có lịch công tác" + "</font>"));
        } else {
            holder.txtChieu.setText(Html.fromHtml(chieu));
        }
    }

    @Override
    public int getItemCount() {
        return scheduleBossList != null && scheduleBossList.size() > 0 ? scheduleBossList.size() : 0;
    }

}
