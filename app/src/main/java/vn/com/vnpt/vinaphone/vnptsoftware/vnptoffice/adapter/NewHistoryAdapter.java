package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class NewHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UnitLogInfo> logInfoList;
    private String status;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new MyViewHolder(inflater.inflate(R.layout.item_new_log, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).bindData(logInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return logInfoList != null ? logInfoList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_dv)
        TextView tvDv;
        @BindView(R.id.rcvDanhSach)
        RecyclerView rcvDanhSach;
        @BindView(R.id.layout_process)
        LinearLayout layoutProcess;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindData(final UnitLogInfo logInfo) {
            tvDv.setText(logInfo.getUnit());
            List<LogInfo> logInfos = ConvertUtils.fromJSONList(logInfo.getParameter(), LogInfo.class);
            if (logInfos != null && logInfos.size() > 0) {
                List<LogInfo> logs = new ArrayList<>();
                for (LogInfo log: logInfos) {
                    if (log.getStatus() != null && log.getStatus().equals(status)) {
                        logs.add(log);
                    }
                }
                NewHistoryDetailAdapter adapter = new NewHistoryDetailAdapter(mContext, logs, status);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                rcvDanhSach.setLayoutManager(mLayoutManager);
                rcvDanhSach.setItemAnimator(new DefaultItemAnimator());
                rcvDanhSach.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public NewHistoryAdapter(Context mContext, List<UnitLogInfo> datalist, String status) {
        this.mContext = mContext;
        this.logInfoList = datalist;
        this.status = status;
    }

}
