package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.CountNumberDenDiEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.ExpandedMenuModel;

/**
 * Created by LinhLK 0948012236 on 9/20/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<ExpandedMenuModel> mListDataHeader; // header titles

    // child data in format of header title, child title
    private HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> mListDataChild;
    ExpandableListView expandList;

    public ExpandableListAdapter(Context context, List<ExpandedMenuModel> listDataHeader, HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listChildData, ExpandableListView mView) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.expandList = mView;
    }

    @Override
    public int getGroupCount() {
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (mListDataHeader.get(groupPosition).isChildren()) {
            childCount = mListDataChild.get(mListDataHeader.get(groupPosition)).size();
        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandedMenuModel headerTitle = (ExpandedMenuModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listheader, null);
        }
        LinearLayout itemHeader = (LinearLayout) convertView.findViewById(R.id.itemHeader);
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.submenu);
        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconimage);
        ImageView img_down = (ImageView) convertView.findViewById(R.id.img_down);
        if (isExpanded) {
            img_down.setImageResource(R.drawable.icon_arrowdown);
        } else {
            img_down.setImageResource(R.drawable.icon_arrowright);
        }

        TextView txtNumberDoc = (TextView) convertView.findViewById(R.id.number_Doc_header);
        lblListHeader.setTypeface(Application.getApp().getTypeface());
        lblListHeader.setText(headerTitle.getIconName());
        headerIcon.setImageResource(headerTitle.getIconImg());

        CountNumberDenDiEvent event = EventBus.getDefault().getStickyEvent(CountNumberDenDiEvent.class);
        if (event != null) {
            if (headerTitle.getId() == Constants.VANBANDEN && (event.getNumberDen() > 0)) {//VĂN BẢN ĐẾN
                txtNumberDoc.setVisibility(View.VISIBLE);
                txtNumberDoc.setText(String.valueOf(event.getNumberDen()));
            } else if (headerTitle.getId() == Constants.VANBANDI && (event.getNumberDi() > 0)) {//VĂN BẢN ĐI
                txtNumberDoc.setVisibility(View.VISIBLE);
                txtNumberDoc.setText(String.valueOf(event.getNumberDi()));
            } else if (headerTitle.getId() == Constants.VANBANXEMDEBIET && (event.getNumberXemDeBiet() > 0)) {//VĂN BẢN XEM ĐỂ BIẾT
                txtNumberDoc.setVisibility(View.VISIBLE);
                txtNumberDoc.setText(String.valueOf(event.getNumberXemDeBiet()));
            } else if (headerTitle.getId() == Constants.QUANLYCONGVIEC && (event.getNumberQLCV() > 0)) {//QUẢN LÝ CÔNG VIỆC
                txtNumberDoc.setVisibility(View.VISIBLE);
                txtNumberDoc.setText(String.valueOf(event.getNumberQLCV()));
            } else if (headerTitle.getId() == Constants.THONGTINDIEUHANH && (event.getNumberTTDH() > 0)) {//THÔNG TIN DIỀU HÀNH
                txtNumberDoc.setVisibility(View.VISIBLE);
                txtNumberDoc.setText(String.valueOf(event.getNumberTTDH()));
            } else {
                txtNumberDoc.setVisibility(View.GONE);
            }
        } else {
            txtNumberDoc.setVisibility(View.GONE);
        }

        // menu nào có child thì show
        if (mListDataHeader.get(groupPosition).isChildren()) {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            img_down.setVisibility(View.VISIBLE);
        } else {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            img_down.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ExpandedMenuModel child = (ExpandedMenuModel) getChild(groupPosition, childPosition);
        final ExpandedMenuModel groupObject = (ExpandedMenuModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_submenu, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.submenu);
        TextView txtNumberDoc = (TextView) convertView.findViewById(R.id.number_Doc);
        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconsubimage);

        if (groupObject.getId() == Constants.VANBANDI || groupObject.getId() == Constants.QUANLYCONGVIEC
                || groupObject.getId() == Constants.VANBANDEN || groupObject.getId() == Constants.THONGTINDIEUHANH) {
            if (child.getNumber() > 0) {
                txtNumberDoc.setVisibility(View.VISIBLE);
                txtNumberDoc.setText(child.getNumber() + "");
            } else {
                txtNumberDoc.setVisibility(View.GONE);
            }
        } else {
            txtNumberDoc.setVisibility(View.GONE);
        }
        txtListChild.setTypeface(Application.getApp().getTypeface());
        txtListChild.setText(child.getIconName());
        headerIcon.setImageResource(child.getIconImg());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void updateDataEx(List<ExpandedMenuModel> listDataHeader, HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listChildData) {
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        notifyDataSetChanged();
    }
}
