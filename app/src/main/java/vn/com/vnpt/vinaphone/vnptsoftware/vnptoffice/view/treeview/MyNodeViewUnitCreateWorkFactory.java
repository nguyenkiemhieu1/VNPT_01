package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;


public class MyNodeViewUnitCreateWorkFactory extends BaseNodeViewFactory {

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelNodeViewBinder(view,2);
            case 1:
                return new SecondLevelNodeViewBinder(view,2);
            case 2:
                return new ThirdLevelNodeViewBinder(view,2);
            default:
                return new ThirdLevelNodeViewBinder(view,2);
        }
    }
}
