package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aditya.filebrowser.Constants;
import com.aditya.filebrowser.FileChooser;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.FileChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.PersonChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.PersonReceiveChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.GroupPerson;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.PersonReceiveChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SendChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao.ChiDaoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao.IChiDaoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.BaseActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KeywordEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoAddEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoResultEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IChiDaoView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendChiDaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendChiDaoFragment extends Fragment implements IChiDaoView, ILoginView, IExceptionErrorView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.lineStep1)
    View lineStep1;
    @BindView(R.id.imgStep1)
    ImageView imgStep1;
    @BindView(R.id.txtStep1)
    TextView txtStep1;
    @BindView(R.id.step1)
    LinearLayout step1;
    @BindView(R.id.lineStep2)
    View lineStep2;
    @BindView(R.id.imgStep2)
    ImageView imgStep2;
    @BindView(R.id.txtStep2)
    TextView txtStep2;
    @BindView(R.id.step2)
    LinearLayout step2;
    @BindView(R.id.lineStep3)
    View lineStep3;
    @BindView(R.id.imgStep3)
    ImageView imgStep3;
    @BindView(R.id.txtStep3)
    TextView txtStep3;
    @BindView(R.id.step3)
    LinearLayout step3;
    Unbinder unbinder;
    @BindView(R.id.tv_tieude)
    EditText tvTieude;
    @BindView(R.id.tv_noidung)
    EditText tvNoidung;
    @BindView(R.id.tv_filedinhkem_label)
    TextView tvFiledinhkemLabel;
    @BindView(R.id.layoutStep1)
    LinearLayout layoutStep1;
    @BindView(R.id.layoutStep2)
    LinearLayout layoutStep2;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnSendAll)
    Button btnSendAll;
    @BindView(R.id.layoutStep3)
    LinearLayout layoutStep3;
    @BindView(R.id.layoutDisplay)
    LinearLayout layoutDisplay;
    @BindView(R.id.btnSelectFile)
    TextView btnSelectFile;
    @BindView(R.id.layoutFile)
    LinearLayout layoutFile;
    private static final int SELECT_FILE_RESULT_SUCCESS = 1;
    @BindView(R.id.edtKeywordName)
    EditText edtKeywordName;
    @BindView(R.id.edtKeywordNameReceive)
    EditText edtKeywordNameReceive;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.txtNoDataReceive)
    TextView txtNoDataReceive;
    @BindView(R.id.layout_contact)
    LinearLayout layoutContact;
    @BindView(R.id.layoutDisplayStep)
    LinearLayout layoutDisplayStep;
    @BindView(R.id.layout_receive)
    LinearLayout layout_receive;
    @BindView(R.id.layoutSave)
    LinearLayout layoutSave;
    @BindView(R.id.sNhom)
    Spinner sNhom;
    private ArrayList<Uri> selectedFiles;
    private int stepCurrent;
    private IChiDaoPresenter chiDaoPresenter = new ChiDaoPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private List<String> fileNames;
    private String event;
    private List<PersonChiDao> personChiDaos;
    private String thongTinId;
    private List<PersonReceiveChiDao> personReceiveChiDaos;
    private List<GroupPerson> groupPersonList;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SendChiDaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendChiDaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendChiDaoFragment newInstance(String param1, String param2) {
        SendChiDaoFragment fragment = new SendChiDaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_chi_dao, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedFiles = new ArrayList<>();
        appPrefs = Application.getApp().getAppPrefs();
        stepCurrent = 1;
        step1.performClick();
        connectionDetector = new ConnectionDetector(getContext());
        edtKeywordName.setTypeface(Application.getApp().getTypeface());
        edtKeywordNameReceive.setTypeface(Application.getApp().getTypeface());
        edtKeywordName.addTextChangedListener(
                new TextWatcher() {
                    private Timer timer = new Timer();

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        View view = getActivity().getCurrentFocus();
                                        if (view != null) {
                                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        }
                                        getPersons();
                                    }
                                });
                            }
                        }, vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
        edtKeywordNameReceive.setTypeface(Application.getApp().getTypeface());
        edtKeywordNameReceive.addTextChangedListener(
                new TextWatcher() {
                    private Timer timer = new Timer();

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        View view = getActivity().getCurrentFocus();
                                        if (view != null) {
                                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        }
                                        getPersonsReceive();
                                    }
                                });
                            }
                        }, vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
    }

    @OnClick({R.id.step1, R.id.step2, R.id.step3})
    public void onStep(View view) {
        switch (view.getId()) {
            case R.id.step1:
                stepCurrent = 1;
                layoutStep1.setVisibility(View.VISIBLE);
                layoutStep2.setVisibility(View.GONE);
                layoutStep3.setVisibility(View.GONE);
                lineStep1.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
                lineStep2.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep3.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1));
                imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2_disable));
                imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3_disable));
                txtStep1.setTextColor(getResources().getColor(R.color.colorTextBlue));
                txtStep2.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep3.setTextColor(getResources().getColor(R.color.md_grey_600));
                btnCancel.setVisibility(View.VISIBLE);
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp);
                layoutSave.setVisibility(View.VISIBLE);
                btnSendAll.setVisibility(View.GONE);
                break;
            case R.id.step2:
                stepCurrent = 2;
                layoutStep1.setVisibility(View.GONE);
                layoutStep2.setVisibility(View.VISIBLE);
                layoutStep3.setVisibility(View.GONE);
                lineStep1.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep2.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
                lineStep3.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1_disable));
                imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2));
                imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3_disable));
                txtStep1.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep2.setTextColor(getResources().getColor(R.color.colorTextBlue));
                txtStep3.setTextColor(getResources().getColor(R.color.md_grey_600));
                btnCancel.setVisibility(View.GONE);
                layoutSave.setVisibility(View.VISIBLE);
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
                btnSendAll.setVisibility(View.GONE);
                getGroupUser();
                break;
            case R.id.step3:
                stepCurrent = 3;
                layoutStep1.setVisibility(View.GONE);
                layoutStep2.setVisibility(View.GONE);
                layoutStep3.setVisibility(View.VISIBLE);
                lineStep1.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep2.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep3.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
                imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1_disable));
                imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2_disable));
                imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3));
                txtStep1.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep2.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep3.setTextColor(getResources().getColor(R.color.colorTextBlue));
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
                layoutSave.setVisibility(View.GONE);
                btnSendAll.setVisibility(View.VISIBLE);
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
                getPersonsReceive();
                break;
        }
    }

    @OnClick({R.id.btnSave, R.id.btnCancel, R.id.btnSelectFile, R.id.btnSendAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                saveInfo();
                break;
            case R.id.btnCancel:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ChiDaoFragment()).commit();
                break;
            case R.id.btnSelectFile:
                Intent i2 = new Intent(getContext(), FileChooser.class);
                i2.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
                startActivityForResult(i2, SELECT_FILE_RESULT_SUCCESS);
                break;
            case R.id.btnSendAll:
                send();
                break;
        }
    }

    private void getPersonsReceive() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON_REVEICE";
            chiDaoPresenter.getPersonReceiveChiDao(new PersonReceiveChiDaoRequest("", "", thongTinId, edtKeywordNameReceive.getText().toString().trim()));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getPersons() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON";
            chiDaoPresenter.getPersonChiDao(new PersonChiDaoRequest(edtKeywordName.getText().toString().trim()));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getGroupUser() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_GROUP";
            chiDaoPresenter.getPersonGroupChiDao();
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void send() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "SEND_CHIDAO";
            chiDaoPresenter.send(new SendChiDaoRequest(thongTinId, "0", ""));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void saveInfo() {
        if (connectionDetector.isConnectingToInternet()) {
            switch (stepCurrent) {
                case 1:
                    event = "SAVE_CHIDAO";
                    fileNames = new ArrayList<>();
                    if (tvTieude.getText() != null && !tvTieude.getText().toString().trim().equals("")) {
                        if (selectedFiles != null && selectedFiles.size() > 0) {
                            MultipartBody.Part[] parts = new MultipartBody.Part[selectedFiles.size()];
                            int i = -1;
                            for (Uri uri : selectedFiles) {
                                i++;
                                parts[i] = prepareFilePart("fileupload", uri);
                                String[] uriStr = uri.getPath().split("/");
                                fileNames.add(uriStr[uriStr.length - 1]);
                            }
                            chiDaoPresenter.uploadFiles(parts);
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.TIEUDE_REQUIERD), true, AlertDialogManager.ERROR);
                    }
                    break;
                case 2:
                    event = "SAVE_PERSON";
                    List<String> empAdd = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class).getEmails();
                    if (empAdd != null && empAdd.size() > 0) {
                        chiDaoPresenter.savePersonChiDao(new SavePersonChiDaoRequest(thongTinId, empAdd, new ArrayList<String>()));
                    } else {
                        AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.PERSON_REQUIERD), true, AlertDialogManager.ERROR);
                    }
                    break;
            }
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccess(Object object) {
        if (event != null && event.equals("SAVE_CHIDAO")) {
            thongTinId = (String) object;
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_CHIDAO_SUCCESS), true, AlertDialogManager.SUCCESS);
            step2.performClick();
        }
        if (event != null && event.equals("GET_PERSON")) {
            EventBus.getDefault().postSticky(new KeywordEvent(edtKeywordName.getText().toString().trim()));
            personChiDaos = ConvertUtils.fromJSONList(object, PersonChiDao.class);
            displayPerson();
        }
        if (event != null && event.equals("SAVE_PERSON")) {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_PERSON_CHIDAO_SUCCESS), true, AlertDialogManager.SUCCESS);
            step3.performClick();
        }
        if (event != null && event.equals("GET_PERSON_REVEICE")) {
            personReceiveChiDaos = ConvertUtils.fromJSONList(object, PersonReceiveChiDao.class);
            displayPersonReceive();
        }
        if (event != null && event.equals("GET_GROUP")) {
            getPersons();
            groupPersonList = ConvertUtils.fromJSONList(object, GroupPerson.class);
            displayPersonGroup();
        }
        if (event != null && event.equals("SEND_CHIDAO")) {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.SEND_CHIDAO_SUCCESS), true, AlertDialogManager.SUCCESS);
        }
    }

    @Override
    public void onSuccess() {
        ChiDaoRequest chiDaoRequest = new ChiDaoRequest("", tvTieude.getText().toString().trim(),
                tvNoidung.getText().toString().trim(), fileNames, new ArrayList<String>(), "", 0);
        chiDaoPresenter.saveChiDao(chiDaoRequest);
    }

    @Override
    public void onError(APIError apiError) {
        sendExceptionError(apiError);
        if (apiError.getCode() == vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } else {
            if (event != null && event.equals("SAVE_CHIDAO")) {
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON")) {
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("SAVE_PERSON")) {
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON_REVEICE")) {
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_GROUP")) {
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_GROUP_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("SEND_CHIDAO")) {
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.SEND_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            if (event != null && event.equals("SAVE_CHIDAO")) {
                saveInfo();
            }
            if (event != null && event.equals("GET_PERSON")) {
                getPersons();
            }
            if (event != null && event.equals("SAVE_PERSON")) {
                saveInfo();
            }
            if (event != null && event.equals("GET_PERSON_REVEICE")) {
                getPersonsReceive();
            }
            if (event != null && event.equals("GET_GROUP")) {
                getGroupUser();
            }
            if (event != null && event.equals("SEND_CHIDAO")) {
                send();
            }
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case SELECT_FILE_RESULT_SUCCESS:
                if (resultCode == RESULT_OK) {
                    ArrayList<Uri> sessionSelectedFiles = data.getParcelableArrayListExtra(Constants.SELECTED_ITEMS);
                    if (sessionSelectedFiles != null && sessionSelectedFiles.size() > 0) {
                        List<FileChiDao> fileChiDaos = new ArrayList<>();
                        for (Uri uri : sessionSelectedFiles) {
                            if (!selectedFiles.contains(uri)) {
                                String[] uriStr = uri.getPath().split("/");
                                if (validateExtension(uriStr[uriStr.length - 1])) {
                                    File file = new File(uri.getPath());
                                    if (validateSize(file)) {
                                        fileChiDaos.add(new FileChiDao(uriStr[uriStr.length - 1]));
                                        selectedFiles.add(uri);
                                    } else {
                                        AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.INVALID_FILE_SIZE), true, AlertDialogManager.ERROR);
                                    }
                                } else {
                                    AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.INVALID_FILE_EXT), true, AlertDialogManager.ERROR);
                                }
                            }
                        }
                        FileChiDaoAdapter fileChiDaoAdapter = new FileChiDaoAdapter(getContext(), R.layout.item_file_related_list, fileChiDaos, "SEND");
                        int adapterCount = fileChiDaoAdapter.getCount();
                        for (int i = 0; i < adapterCount; i++) {
                            View item = fileChiDaoAdapter.getView(i, null, null);
                            layoutFile.addView(item);
                        }
                    }
                }
                break;
        }
    }

    private void displayPersonGroup() {
        List<String> lst = new ArrayList<>();
        lst.add(getString(R.string.DEFAULT_GROUP));
        if (groupPersonList != null && groupPersonList.size() > 0) {
            for (int i = 0; i < groupPersonList.size(); i++) {
                lst.add(groupPersonList.get(i).getName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lst) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sNhom.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sNhom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void displayPersonReceive() {
        layout_receive.removeAllViewsInLayout();
        if (personReceiveChiDaos != null && personReceiveChiDaos.size() > 0) {
            txtNoDataReceive.setVisibility(View.GONE);
            PersonReceiveChiDaoAdapter contactAdapter = new PersonReceiveChiDaoAdapter(getContext(), R.layout.item_person_chidao_send, personReceiveChiDaos, thongTinId);
            int adapterCount = contactAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = contactAdapter.getView(i, null, null);
                layout_receive.addView(item);
            }
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void displayPerson() {
        layoutContact.removeAllViewsInLayout();
        List<PersonChiDao> lstParent = new ArrayList<>();
        EventBus.getDefault().postSticky(new PersonChiDaoResultEvent(personChiDaos));
        EventBus.getDefault().postSticky(new PersonChiDaoAddEvent(new ArrayList<String>()));
        if (personChiDaos != null && personChiDaos.size() > 0) {
            txtNoData.setVisibility(View.GONE);
            for (int i = 0; i < personChiDaos.size(); i++) {
                if (personChiDaos.get(i).getParentId() == null) {
                    lstParent.add(personChiDaos.get(i));
                }
            }
            List<Integer> sizes = new ArrayList<Integer>(lstParent.size());
            List<Integer> countUsers = new ArrayList<Integer>(lstParent.size());
            for (PersonChiDao contact : lstParent) {
                int count = 0;
                int countUser = 0;
                for (int i = 0; i < personChiDaos.size(); i++) {
                    if (personChiDaos.get(i).getParentId() != null && personChiDaos.get(i).getParentId().equals(contact.getUnitId())) {
                        count++;
                        if (personChiDaos.get(i).getChucVu() != null && !personChiDaos.get(i).getChucVu().trim().equals("")) {
                            countUser++;
                        }
                    }
                }
                sizes.add(count);
                countUsers.add(countUser);
            }
            PersonChiDaoAdapter contactAdapter = new PersonChiDaoAdapter(getContext(), R.layout.item_person_chidao_list, R.layout.item_person_chidao_detail, R.layout.item_person_chidao_list_detail, lstParent, sizes, countUsers, "SEND");
            int adapterCount = contactAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = contactAdapter.getView(i, null, null);
                layoutContact.addView(item);
            }
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(List<Object> object) {

    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    private boolean validateExtension(String filename) {
        if (filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DOC)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DOCX)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.XLS)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.XLSX)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.PDF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DOCX)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.JPG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.JPEG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.PNG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.GIF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.TIFF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.BMP)) {
            return true;
        }
        return false;
    }

    private boolean validateSize(File file) {
        double size = (double) file.length() / (1024 * 1024);
        if (size < 50) {
            return true;
        }
        return false;
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = new File(fileUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse(getMimeType(fileUri.getPath())), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    @Override
    public void showProgress() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressDialog();
    }

    @Override
    public void hideProgress() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressDialog();
    }

    @Override
    public void onSuccessDonViNhan(Object object) {

    }

    @Override
    public void onSuccessDanhSachDonViNhan(Object object) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }

    private void sendExceptionError(APIError apiError){
        ExceptionRequest exceptionRequest = new ExceptionRequest();
        LoginInfo eventbus = EventBus.getDefault().getStickyEvent(LoginInfo.class);
        if(eventbus != null){
            exceptionRequest.setUserId(eventbus.getUsername());
        } else {
            exceptionRequest.setUserId("");
        }
        exceptionRequest.setDevice(appPrefs.getDeviceName());
        ExceptionCallAPIEvent error = EventBus.getDefault().getStickyEvent(ExceptionCallAPIEvent.class);
        if(error != null){
            exceptionRequest.setFunction(error.getUrlAPI());
        } else {
            exceptionRequest.setFunction("");
        }
        exceptionRequest.setException(apiError.getMessage());
        iExceptionErrorPresenter.sendExceptionError(exceptionRequest);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
