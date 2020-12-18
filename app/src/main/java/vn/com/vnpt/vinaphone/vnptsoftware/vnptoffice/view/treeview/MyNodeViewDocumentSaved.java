package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;

public class MyNodeViewDocumentSaved extends BaseNodeViewFactory {
    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelDocumentSaved(view,true);
            case 1:
                return new SecondLevelDocumentSaved(view,true);
            case 2:
                return new ThirdLevelDocumentSaved(view,true);
            default:
                return new ThirdLevelDocumentSaved(view,true);
        }
    }
}

