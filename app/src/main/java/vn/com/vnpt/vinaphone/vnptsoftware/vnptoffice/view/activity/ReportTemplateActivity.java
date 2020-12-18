package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypeReportEvent;

public class ReportTemplateActivity extends BaseActivity {

    private Toolbar toolbar;
    private ImageView btnBack;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_template);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarReport);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        title = (TextView) toolbar.findViewById(R.id.title);
        title.setTypeface(Application.getApp().getTypeface());
        String type = EventBus.getDefault().getStickyEvent(TypeReportEvent.class).getType();
        switch (type) {
            case "1":
                title.setText(getResources().getStringArray(R.array.string_array_report)[1]);
                break;
            case "2":
                title.setText(getResources().getStringArray(R.array.string_array_report)[2]);
                break;
            case "3":
                title.setText(getResources().getStringArray(R.array.string_array_report)[3]);
                break;
        }
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
