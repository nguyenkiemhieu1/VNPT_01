package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.FeedBackEvent;

//import vn.com.vnpt.vinaphone.vnptsoftware.pmo.R;

public class DialogComentFinish extends Dialog {
    @BindView(R.id.txtFeedBack)
    EditText txtFeedBack;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnYes)
    Button btnYes;

    public DialogComentFinish(@NonNull Context context) {
        super(context);
        getWindow().setDimAmount(0.5f);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public DialogComentFinish(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogComentFinish(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_coment_finish);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.btnYes, R.id.btnCancel})
    public void OnClickView(View view)
    {
        switch (view.getId()) {
            case R.id.btnYes:
                EventBus.getDefault().postSticky(new FeedBackEvent(txtFeedBack.getText().toString().trim()));
                dismiss();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }
}
