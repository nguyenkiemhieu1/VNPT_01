package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitLogInfo;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class LogAdapter extends ArrayAdapter<UnitLogInfo> {
    private Context context;
    private int resource;
    private List<UnitLogInfo> logInfoList;
    private List<Boolean> touchs;

    public LogAdapter(Context context, int resource, List<UnitLogInfo> logInfoList) {
        super(context, resource, logInfoList);
        this.context = context;
        this.resource = resource;
        this.logInfoList = logInfoList;
        touchs = new ArrayList<Boolean>(logInfoList.size());
        for (int i = 0; i < logInfoList.size(); i++) {
            touchs.add(false);
        }
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        final UnitLogInfo logInfo = logInfoList.get(position);
        final TextView unit = (TextView) view.findViewById(R.id.unit);
        unit.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        unit.setText(logInfo.getUnit());
        final LinearLayout layoutContact = (LinearLayout) view.findViewById(R.id.layoutSubLog);
        unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutContact.removeAllViewsInLayout();
                if (!touchs.get(position)) {
                    touchs.set(position, true);
                    unit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_minus_circle, 0);
                    List<LogInfo> logInfos = ConvertUtils.fromJSONList(logInfo.getParameter(), LogInfo.class);
                    if (logInfos != null && logInfos.size() > 0) {
                        LogInfoAdapter logInfoAdapter = new LogInfoAdapter(context, R.layout.item_log_detail, logInfos);
                        int adapterCount = logInfoAdapter.getCount();
                        for (int i = 0; i < adapterCount; i++) {
                            View item = logInfoAdapter.getView(i, null, null);
                            layoutContact.addView(item);
                        }
                        layoutContact.setVisibility(View.VISIBLE);
                    } else {
                        AlertDialogManager.showAlertDialog(context, context.getString(R.string.TITLE_NOTIFICATION), context.getString(R.string.NO_COMMENT), true, AlertDialogManager.INFO);
                    }
                } else {
                    unit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_plus, 0);
                    layoutContact.setVisibility(View.GONE);
                    touchs.set(position, false);
                }
            }
        });
        return view;
    }
}
