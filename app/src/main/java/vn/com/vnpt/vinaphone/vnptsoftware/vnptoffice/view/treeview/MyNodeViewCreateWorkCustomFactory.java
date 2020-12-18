package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;


public class MyNodeViewCreateWorkCustomFactory extends BaseNodeViewFactory {

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelNodeViewBinder(view,3);
            case 1:
                return new SecondLevelNodeViewBinder(view,3);
            case 2:
                return new ThirdLevelNodeViewBinder(view,3);
            default:
                return new ThirdLevelNodeViewBinder(view,3);
        }
    }
}
