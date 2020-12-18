package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.MoveDocNodeViewBinder;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SaveDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SelectUnitForPersonEvent;

public class SecondLevelDocumentSaved extends MoveDocNodeViewBinder {
    TextView textView;
    ImageView imageView;
    RadioButton checkXLChinh;
    AppCompatCheckBox checkBoxPH;
    AppCompatCheckBox checkBoxXEM;
    private boolean hideButton;
    private int typeCheckBox = 1;

    public SecondLevelDocumentSaved(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
        imageView = (ImageView) itemView.findViewById(R.id.arrow_img);
    }

    public SecondLevelDocumentSaved(View itemView, boolean hide) {
        super(itemView);
        hideButton = hide;
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
        imageView = (ImageView) itemView.findViewById(R.id.arrow_img);
        checkXLChinh = (RadioButton) itemView.findViewById(R.id.checkXLChinh);
        checkBoxPH = (AppCompatCheckBox) itemView.findViewById(R.id.checkBoxPH);
        checkBoxXEM = (AppCompatCheckBox) itemView.findViewById(R.id.checkBoxXEM);
    }

    public SecondLevelDocumentSaved(View itemView, int type) {
        super(itemView);
        typeCheckBox = type;
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
        return R.layout.item_second_level;
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
        textView.setText(((SaveDocumentRespone) treeNode.getValue()).getName());

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
        if (typeCheckBox == 2) {
            EventBus.getDefault().postSticky(new SelectUnitForPersonEvent((PersonSendNotifyInfo) treeNode.getValue()));
        }
    }
}

