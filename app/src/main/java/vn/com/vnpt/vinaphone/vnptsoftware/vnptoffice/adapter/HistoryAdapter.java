package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class HistoryAdapter extends ArrayAdapter<UnitLogInfo> {
    private Context context;
    private int resource;
    private List<UnitLogInfo> logInfoList;
    private String status;

    public HistoryAdapter(Context context, int resource, List<UnitLogInfo> logInfoList, String status) {
        super(context, resource, logInfoList);
        this.context = context;
        this.resource = resource;
        this.logInfoList = logInfoList;
        this.status = status;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        final UnitLogInfo logInfo = logInfoList.get(position);
        List<LogInfo> logInfos = ConvertUtils.fromJSONList(logInfo.getParameter(), LogInfo.class);
        if (logInfos != null && logInfos.size() > 0) {
            final TextView unit = (TextView) view.findViewById(R.id.unit);
            unit.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
            unit.setText(logInfo.getUnit());
            final LinearLayout layoutContact = (LinearLayout) view.findViewById(R.id.layoutSubLog);
            HistoryInfoAdapter logInfoAdapter = new HistoryInfoAdapter(context, R.layout.item_history_detail, logInfos, status);
            int adapterCount = logInfoAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = logInfoAdapter.getView(i, null, null);
                if (logInfos.get(i).getStatus() != null && logInfos.get(i).getStatus().equals(status)) {
                    layoutContact.addView(item);
                }
            }
        }
        return view;
    }
}
