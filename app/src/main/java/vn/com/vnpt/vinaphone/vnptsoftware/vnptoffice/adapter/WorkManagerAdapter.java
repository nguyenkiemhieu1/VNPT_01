package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListJobAssignRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailWorkManagementActivity;

public class WorkManagerAdapter extends BaseAdapter {

    private Context context;
    private int typeWork;
    private List<GetListJobAssignRespone.Data> listData;

    public WorkManagerAdapter(List<GetListJobAssignRespone.Data> listData, Context context, int typeWork) {
        this.context = context;
        this.listData = listData;
        this.typeWork = typeWork;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return typeWork;
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public GetListJobAssignRespone.Data getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_item_work_need_to_solve, parent, false);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final GetListJobAssignRespone.Data dataItem = getItem(position);

        if (dataItem.isDondoc()) {
            holder.tvUrge.setVisibility(View.VISIBLE);
        } else {
            holder.tvUrge.setVisibility(View.GONE);
        }
        holder.tvTitle.setText(dataItem.getName());
        holder.tvDate.setText(dataItem.getStartDate());
        holder.tvUrge.setText(dataItem.getStartDate());
        holder.tvDateFinish.setText(dataItem.getEndDate());
        holder.tvLeadAssign.setText(dataItem.getLanhDaoGiaoViec());
        holder.tvStatusDoc.setText(dataItem.getStatus());
        holder.tvStatus.setText(dataItem.getVaitro());
        holder.tvPriorityLevel.setText(dataItem.getMucDo());
//
//        holder.itemDocument.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(context, DetailWorkManagementActivity.class);
//                i.putExtra("id", dataItem.getId());
//                i.putExtra("nxlid", dataItem.getNxlId());
//                i.putExtra("typeWork", typeWork);
//                context.startActivity(i);
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_urge)
        TextView tvUrge;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_trang_thai_label)
        TextView tvTrangThaiLabel;
        @BindView(R.id.tv_trang_thai)
        TextView tvTrangThai;
        @BindView(R.id.tv_lead_assign)
        TextView tvLeadAssign;
        @BindView(R.id.img_file_dinh_kem)
        ImageView imgFileDinhKem;
        @BindView(R.id.tv_priority_level)
        TextView tvPriorityLevel;
        @BindView(R.id.tv_date_finish)
        TextView tvDateFinish;
        @BindView(R.id.tv_status_doc)
        TextView tvStatusDoc;
        @BindView(R.id.itemDocument)
        LinearLayout itemDocument;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    public void updateTypeViewItem(int typeView) {
        this.typeWork = typeView;
    }

}
