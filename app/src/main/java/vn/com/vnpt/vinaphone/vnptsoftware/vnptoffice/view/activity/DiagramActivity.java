package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentexpired.DocumentExpiredPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentexpired.IDocumentExpiredPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentmark.DocumentMarkPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentmark.IDocumentMarkPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentnotification.DocumentNotificationPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentnotification.IDocumentNotificationPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentprocessed.DocumentProcessedPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentprocessed.IDocumentProcessedPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting.DocumentWaitingPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.documentwaiting.IDocumentWaitingPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.DiagramInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IDocumentDiagramView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

public class DiagramActivity extends BaseActivity implements IDocumentDiagramView, ILoginView {

    private Toolbar toolbar;
    private ImageView btnBack;
    @BindView(R.id.diagram) ImageView diagram;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private ProgressDialog progressDialog;
    private IDocumentWaitingPresenter documentWaitingPresenter = new DocumentWaitingPresenterImpl(this);
    private IDocumentExpiredPresenter documentExpiredPresenter = new DocumentExpiredPresenterImpl(this);
    private IDocumentNotificationPresenter documentNotificationPresenter = new DocumentNotificationPresenterImpl(this);
    private IDocumentMarkPresenter documentMarkPresenter = new DocumentMarkPresenterImpl(this);
    private IDocumentProcessedPresenter documentProcessedPresenter = new DocumentProcessedPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);
        setupToolbar();
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        if (connectionDetector.isConnectingToInternet()) {
            DiagramInfo diagramInfo = EventBus.getDefault().getStickyEvent(DiagramInfo.class);
            if (diagramInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getBitmapDiagram(diagramInfo.getInsId(), diagramInfo.getDefId());
            }
            if (diagramInfo.getType().equals(Constants.DOCUMENT_EXPIRED)) {
                documentExpiredPresenter.getBitmapDiagram(diagramInfo.getInsId(), diagramInfo.getDefId());
            }
            if (diagramInfo.getType().equals(Constants.DOCUMENT_PROCESSED)) {
                documentProcessedPresenter.getBitmapDiagram(diagramInfo.getInsId(), diagramInfo.getDefId());
            }
            if (diagramInfo.getType().equals(Constants.DOCUMENT_MARK)) {
                documentMarkPresenter.getBitmapDiagram(diagramInfo.getInsId(), diagramInfo.getDefId());
            }
            if (diagramInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getBitmapDiagram(diagramInfo.getInsId(), diagramInfo.getDefId());
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDiagram);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        setTitle(getString(R.string.change_password_title));
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onSuccessDiagram(Bitmap bitmap) {
        try {
            diagram.setImageBitmap(Bitmap.createScaledBitmap(bitmap, (bitmap.getWidth() * 3) / 2, bitmap.getHeight() * 3, false));
            diagram.setRotation((float) 90.0);
        } catch (Exception ex) {

        }
    }

    @Override
    public void onError(APIError apiError) {
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            DiagramInfo diagramInfo = EventBus.getDefault().getStickyEvent(DiagramInfo.class);
            if (diagramInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getBitmapDiagram(diagramInfo.getInsId(), diagramInfo.getDefId());
            }
            if (diagramInfo.getType().equals(Constants.DOCUMENT_EXPIRED)) {
                documentExpiredPresenter.getBitmapDiagram(diagramInfo.getInsId(), diagramInfo.getDefId());
            }
            if (diagramInfo.getType().equals(Constants.DOCUMENT_PROCESSED)) {
                documentProcessedPresenter.getBitmapDiagram(diagramInfo.getInsId(), diagramInfo.getDefId());
            }
            if (diagramInfo.getType().equals(Constants.DOCUMENT_MARK)) {
                documentMarkPresenter.getBitmapDiagram(diagramInfo.getInsId(), diagramInfo.getDefId());
            }
            if (diagramInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getBitmapDiagram(diagramInfo.getInsId(), diagramInfo.getDefId());
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(DiagramActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
       showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }
}
