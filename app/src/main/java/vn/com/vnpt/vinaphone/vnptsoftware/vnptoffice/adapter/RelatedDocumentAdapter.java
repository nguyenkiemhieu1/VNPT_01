package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailDocumentNotificationActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.DetailDocumentInfo;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class RelatedDocumentAdapter extends ArrayAdapter<RelatedDocumentInfo> {
    private Context context;
    private int resource;
    private List<RelatedDocumentInfo> relatedDocumentInfoList;

    public RelatedDocumentAdapter(Context context, int resource, List<RelatedDocumentInfo> relatedDocumentInfoList) {
        super(context, resource, relatedDocumentInfoList);
        this.context = context;
        this.resource = resource;
        this.relatedDocumentInfoList = relatedDocumentInfoList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        TextView ngayVB_related_label = (TextView) view.findViewById(R.id.ngayVB_related_label);
        TextView titleRelated = (TextView) view.findViewById(R.id.titleRelated);
        TextView ngayVB_related = (TextView) view.findViewById(R.id.ngayVB_related);
        LinearLayout itemDocumentRelated = (LinearLayout) view.findViewById(R.id.itemDocumentRelated);
        ngayVB_related_label.setTypeface(Application.getApp().getTypeface());
        titleRelated.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        ngayVB_related.setTypeface(Application.getApp().getTypeface());
        final RelatedDocumentInfo relatedDocumentInfo = relatedDocumentInfoList.get(position);
        titleRelated.setText(relatedDocumentInfo.getName());
        ngayVB_related.setText(relatedDocumentInfo.getNgayVanBan());
        itemDocumentRelated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new DetailDocumentInfo(relatedDocumentInfo.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                context.startActivity(new Intent(context, DetailDocumentNotificationActivity.class));
            }
        });
        return view;
    }
}
