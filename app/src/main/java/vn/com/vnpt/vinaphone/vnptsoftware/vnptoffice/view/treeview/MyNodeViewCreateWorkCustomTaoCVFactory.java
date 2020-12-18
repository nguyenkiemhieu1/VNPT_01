package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewTaoCVBinder;
import me.texy.treeview.base.BaseNodeViewTaoCVFactory;


public class MyNodeViewCreateWorkCustomTaoCVFactory extends BaseNodeViewTaoCVFactory {

    @Override
    public BaseNodeViewTaoCVBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelNodeViewTaoCVBinder(view,3);
            case 1:
                return new SecondLevelNodeViewTaoCVBinder(view,3);
            case 2:
                return new ThirdLevelNodeViewTaoCVBinder(view,3);
            default:
                return new ThirdLevelNodeViewTaoCVBinder(view,3);
        }
    }
}
