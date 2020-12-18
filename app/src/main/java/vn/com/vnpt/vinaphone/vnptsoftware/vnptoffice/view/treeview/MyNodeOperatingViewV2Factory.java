package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewOperatingV2Binder;
import me.texy.treeview.base.BaseNodeViewOperatingV2Factory;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;


public class MyNodeOperatingViewV2Factory extends BaseNodeViewOperatingV2Factory {
    @Override
    public BaseNodeViewOperatingV2Binder getNodeViewOperatingBinder(View view, int level) {
        switch (level) {
            case 0:
                return new MultiLevelOperatingNodeViewBinder(view, R.layout.item_first_operating_level);
            case 1:
                return new MultiLevelOperatingNodeViewBinder(view, R.layout.item_second_operating_level);
            case 2:
                return new MultiLevelOperatingNodeViewBinder(view, R.layout.item_third_operating_level);
            case 3:
                return new MultiLevelOperatingNodeViewBinder(view, R.layout.item_four_operating_level);
            default:
                return new MultiLevelOperatingNodeViewBinder(view, R.layout.item_four_operating_level);
        }
    }
}
