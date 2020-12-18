package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonProcessInfo;

public class SelectProcessV2Adapter extends RecyclerView.Adapter<SelectProcessV2Adapter.ViewHolder> {
    private Context context;
    private int resource;
    private List<PersonProcessInfo> personProcessInfoList;
    private int positionCheckBocXlcOLD;

    public SelectProcessV2Adapter(Context context, int resource, List<PersonProcessInfo> personProcessInfoList) {
        this.context = context;
        this.resource = resource;
        this.personProcessInfoList = personProcessInfoList;
        this.positionCheckBocXlcOLD = -1;
    }


    @NonNull
    @Override
    public SelectProcessV2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.resource, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectProcessV2Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtName.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        holder.txtDonVi.setTypeface(Application.getApp().getTypeface());
        holder.txtName.setText(personProcessInfoList.get(position).getFullName());
        if (personProcessInfoList.get(position).isXlc() || personProcessInfoList.get(position).isDxl()) {
            holder.txtName.setTextColor(ContextCompat.getColor(context, R.color.md_red_500));
        } else {
            holder.txtName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        holder.txtDonVi.setText(personProcessInfoList.get(position).getSecondUnitName());

        if (personProcessInfoList.get(position).isXlc()) {
            holder.checkboxXLC.setChecked(true);
        } else holder.checkboxXLC.setChecked(false);
        if (personProcessInfoList.get(position).isDxl()) {
            holder.checkboxDXL.setChecked(true);
        } else holder.checkboxDXL.setChecked(false);
        holder.checkboxXLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!personProcessInfoList.get(position).isXlc()) {
                    personProcessInfoList.get(position).setXlc(true);

                    if (personProcessInfoList.get(position).isDxl()) {
                        personProcessInfoList.get(position).setDxl(false);
                    }
                    if (positionCheckBocXlcOLD > -1 && positionCheckBocXlcOLD != position) {//kiểm tra xem đã click checkbox XLC lần nào chưa
                        personProcessInfoList.get(positionCheckBocXlcOLD).setXlc(false);
                        notifyItemChanged(positionCheckBocXlcOLD);
                    }
                    positionCheckBocXlcOLD = position;
                    notifyItemChanged(position);
                }

            }
        });
        holder.checkboxDXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personProcessInfoList.get(position).isDxl()) {
                    personProcessInfoList.get(position).setDxl(false);
                } else {
                    personProcessInfoList.get(position).setDxl(true);
                    personProcessInfoList.get(position).setXlc(false);
                }
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personProcessInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtDonVi;
        private RadioButton checkboxXLC;
        private CheckBox checkboxDXL;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDonVi = itemView.findViewById(R.id.txtDonVi);
            checkboxXLC = itemView.findViewById(R.id.checkXLChinh);
            checkboxDXL = itemView.findViewById(R.id.checkDongXL);
        }
    }
}
