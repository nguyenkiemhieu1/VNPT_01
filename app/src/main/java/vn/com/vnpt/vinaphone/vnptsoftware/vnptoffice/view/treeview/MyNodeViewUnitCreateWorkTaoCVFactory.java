package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewTaoCVBinder;
import me.texy.treeview.base.BaseNodeViewTaoCVFactory;


public class MyNodeViewUnitCreateWorkTaoCVFactory extends BaseNodeViewTaoCVFactory {

    @Override
    public BaseNodeViewTaoCVBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelNodeViewTaoCVBinder(view,2);
            case 1:
                return new SecondLevelNodeViewTaoCVBinder(view,2);
            case 2:
                return new ThirdLevelNodeViewTaoCVBinder(view,2);
            default:
                return new ThirdLevelNodeViewTaoCVBinder(view,2);
        }
    }
}
