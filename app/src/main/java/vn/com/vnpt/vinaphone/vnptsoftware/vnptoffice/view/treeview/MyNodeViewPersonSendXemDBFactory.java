package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;


public class MyNodeViewPersonSendXemDBFactory extends BaseNodeViewFactory {

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelNodeViewBinder(view,true);
            case 1:
                return new SecondLevelNodeViewBinder(view,true);
            case 2:
                return new ThirdLevelNodeViewBinder(view,true);
            default:
                return new ThirdLevelNodeViewBinder(view,true);
        }
    }
}
