package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.MoveDocNodeViewBinder;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LienThongInfo;

/**
 * Created by zxy on 17/4/23.
 */

public class FirstLevelLienThongNodeViewBinder extends MoveDocNodeViewBinder {
    TextView textView;
    ImageView imageView;
    public FirstLevelLienThongNodeViewBinder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
        imageView = (ImageView) itemView.findViewById(R.id.arrow_img);
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
        textView.setTypeface(Application.getApp().getTypeface());
        textView.setText(((LienThongInfo)treeNode.getValue()).getName());
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
}
