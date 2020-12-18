package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonAndUnitScheduleWeekInfo;

public class MyNodeViewScheduleWeek extends BaseNodeViewFactory {
    private ArrayList<PersonAndUnitScheduleWeekInfo> listSelectPesonAndUnit;

    public MyNodeViewScheduleWeek(ArrayList<PersonAndUnitScheduleWeekInfo> listSelectPesonAndUnit) {
        this.listSelectPesonAndUnit = listSelectPesonAndUnit;
    }

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelScheduleWeek(view,listSelectPesonAndUnit ,true);
            case 1:
                return new SecondLevelScheduleWeek(view,listSelectPesonAndUnit , true);
            case 2:
                return new ThirdLevelScheduleWeek(view, listSelectPesonAndUnit ,true);
            default:
                return new ThirdLevelScheduleWeek(view, listSelectPesonAndUnit ,true);
        }
    }
}

