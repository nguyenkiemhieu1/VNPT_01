package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.MoveDocNodeViewBinder;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SaveDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SelectUnitForPersonEvent;

public class FirstLevelScheduleWeek extends MoveDocNodeViewBinder {
    TextView textView;
    ImageView imageView;
    RadioButton checkXLChinh;
    AppCompatCheckBox checkBoxPH;
    AppCompatCheckBox checkBoxXEM;
    private boolean hideButton;
    private ArrayList<PersonAndUnitScheduleWeekInfo> listSelectPesonAndUnit;


    public FirstLevelScheduleWeek(View itemView, ArrayList<PersonAndUnitScheduleWeekInfo> listSelectPesonAndUnit, boolean hide) {
        super(itemView);
        this.hideButton = hide;
        this.listSelectPesonAndUnit = listSelectPesonAndUnit;
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
        imageView = (ImageView) itemView.findViewById(R.id.arrow_img);
        checkXLChinh = (RadioButton) itemView.findViewById(R.id.checkXLChinh);
        checkBoxPH = (AppCompatCheckBox) itemView.findViewById(R.id.checkBoxPH);
        checkBoxXEM = (AppCompatCheckBox) itemView.findViewById(R.id.checkBoxXEM);
    }


    @Override
    public int getCheckXLCViewId() {
        return R.id.checkXLChinh;
    }

    @Override
    public int getCheckPHViewId() {
        return R.id.checkBoxPH;
    }

    @Override
    public int getCheckXEMViewId() {
        return R.id.checkBoxXEM;
    }

    @Override
    public int getNodeNameViewId() {
        return R.id.node_name_view;
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_first_level;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        if (!treeNode.hasChild()) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            if (treeNode.isExpanded()) {
                imageView.setImageResource(R.drawable.ic_minus);
            } else {
                imageView.setImageResource(R.drawable.ic_add);
            }
        }
        if (hideButton) {
            checkBoxXEM.setVisibility(View.GONE);
            checkBoxPH.setVisibility(View.GONE);
        }
        textView.setTypeface(Application.getApp().getTypeface());
        textView.setText(((PersonAndUnitScheduleWeekInfo) treeNode.getValue()).getName());
//        for (PersonAndUnitScheduleWeekInfo person : listSelectPesonAndUnit) {
//            if (person.getId().equals(((PersonAndUnitScheduleWeekInfo) treeNode.getValue()).getId())) {
//                checkXLChinh.setChecked(true);
//                return;
//            }
//        }
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        if (!treeNode.hasChild()) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            if (expand) {
                imageView.setImageResource(R.drawable.ic_minus);
            } else {
                imageView.setImageResource(R.drawable.ic_add);
            }
        }

    }

    @Override
    public void onNodeSelectedChanged(TreeNode treeNode, boolean selected) {
        super.onNodeSelectedChanged(treeNode, selected);
        PersonAndUnitScheduleWeekInfo personAndUnitScheduleWeekInfo = (PersonAndUnitScheduleWeekInfo) treeNode.getValue();
        if (selected) {
            boolean checkduplicate = false;
            for (PersonAndUnitScheduleWeekInfo person : listSelectPesonAndUnit) {
                if (person.getId().equals(personAndUnitScheduleWeekInfo.getId())) {
                    checkduplicate = true;
                    return;
                }
            }
            if (!checkduplicate) {
                listSelectPesonAndUnit.add((PersonAndUnitScheduleWeekInfo) treeNode.getValue());
            }
        } else {
            for (int i = 0; i < listSelectPesonAndUnit.size(); i++) {
                if (listSelectPesonAndUnit.get(i).getId().equals(personAndUnitScheduleWeekInfo.getId())) {
                    listSelectPesonAndUnit.remove(i);
                    return;
                }
            }
        }
    }
}

