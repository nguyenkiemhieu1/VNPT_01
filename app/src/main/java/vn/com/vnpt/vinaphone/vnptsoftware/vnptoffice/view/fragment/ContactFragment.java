package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Validator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.ContactAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.ContactSearchAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.RealmDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.builder.ContactBuilder;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Contact;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ContactInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.contact.ContactPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.contact.IContactPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.BaseActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ContactResultEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.InitEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KeywordEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.syncevent.IContactSync;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.ILoginView;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment implements IExceptionErrorView , ILoginView,  IContactSync{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Realm realm;
    @BindView(R.id.txtNoData) TextView txtNoData;
    @BindView(R.id.edtKeyword) EditText edtKeyword;
    @BindView(R.id.layout_contact) LinearLayout layoutContact;
    @BindView(R.id.layoutDisplay) ScrollView layoutDisplay;
    private String keyword;
    private RealmResults<Contact> contacts;
    private List<Contact> contactList;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private IContactPresenter contactPresenter = new ContactPresenterImpl(this);
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private OnFragmentInteractionListener mListener;
    private ApplicationSharedPreferences appPrefs;
    private ConnectionDetector connectionDetector;
    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
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
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        connectionDetector = new ConnectionDetector(getContext());
        ((MainActivity) getActivity()).hideNotify();
        appPrefs = Application.getApp().getAppPrefs();
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;
            }
        });
        edtKeyword.setTypeface(Application.getApp().getTypeface());
        edtKeyword.addTextChangedListener(
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
                                        keyword = edtKeyword.getText().toString();
                                        View view = getActivity().getCurrentFocus();
                                        if (view != null) {
                                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        }
                                        search();
                                    }
                                });
                            }
                        }, Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
        getContact();
    }
    private void getContact() {
        if (connectionDetector.isConnectingToInternet()) {
            contactPresenter.getContacts();
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }
    private void displayContact() {
        layoutContact.removeAllViewsInLayout();
        realm = RealmDao.with(this).getRealm();
        Map<String, String> mapFields = new HashMap<String, String>();
        mapFields.put("parentId", null);
        contacts = RealmDao.with(this).findFilterAnd(Contact.class, mapFields);
        if (contacts != null && contacts.size() > 0) {
            txtNoData.setVisibility(View.GONE);
            List<Integer> sizes = new ArrayList<Integer>(contacts.size());
            List<Integer> countUsers = new ArrayList<Integer>(contacts.size());
            for (Contact contact : contacts) {
                mapFields = new HashMap<String, String>();
                mapFields.put("parentId", contact.getId());
                long count = RealmDao.with(this).countFilterAnd(Contact.class, mapFields);
                sizes.add((int) count);
                mapFields.put("isUser", "true");
                count = RealmDao.with(this).countFilterAnd(Contact.class, mapFields);
                countUsers.add((int) count);
            }
            ContactAdapter contactAdapter = new ContactAdapter(getContext(), R.layout.item_contact_list, R.layout.item_contact_detail, R.layout.item_contact_list_detail, contacts, sizes, countUsers);
            int adapterCount = contactAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = contactAdapter.getView(i, null, null);
                layoutContact.addView(item);
            }
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void displaySearchContact() {
        layoutContact.removeAllViewsInLayout();
        List<Contact> lstParent = new ArrayList<>();
        EventBus.getDefault().postSticky(new ContactResultEvent(contactList));
        if (contactList != null && contactList.size() > 0) {
            txtNoData.setVisibility(View.GONE);
            for (int i = 0; i < contactList.size(); i++) {
                if (contactList.get(i).getParentId() == null) {
                    lstParent.add(contactList.get(i));
                }
            }
            List<Integer> sizes = new ArrayList<Integer>(lstParent.size());
            List<Integer> countUsers = new ArrayList<Integer>(lstParent.size());
            for (Contact contact : lstParent) {
                int count = 0;
                int countUser = 0;
                for (int i = 0; i < contactList.size(); i++) {
                    if (contactList.get(i).getParentId() != null && contactList.get(i).getParentId().equals(contact.getId())) {
                        count++;
                        if (contactList.get(i).getIsUser().equals("true")) {
                            countUser++;
                        }
                    }
                }
                sizes.add(count);
                countUsers.add(countUser);
            }
            ContactSearchAdapter contactAdapter = new ContactSearchAdapter(getContext(), R.layout.item_contact_list, R.layout.item_contact_detail, R.layout.item_contact_list_detail, lstParent, sizes, countUsers);
            int adapterCount = contactAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = contactAdapter.getView(i, null, null);
                layoutContact.addView(item);
            }
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void search() {
        Map<String, String> mapFields = new HashMap<String, String>();
        mapFields.put("userName", keyword != null ? keyword.trim() : "");
        mapFields.put("position", keyword != null ? keyword.trim() : "");
        mapFields.put("unitName", keyword != null ? keyword.trim() : "");
        mapFields.put("phone", keyword != null ? keyword.trim() : "");
        mapFields.put("email", keyword != null ? keyword.trim() : "");
        RealmResults<Contact> contactFilter = RealmDao.with(this).findFilterOr(Contact.class, mapFields);
        contactList = realm.copyFromRealm(contactFilter);
        for (Contact contact : realm.copyFromRealm(contactFilter)) {
            searchParent(contact);
            searchChild(contact);
        }
        EventBus.getDefault().postSticky(new KeywordEvent(keyword != null ? keyword.trim() : ""));
        displaySearchContact();
    }

    private void searchChild(Contact contact) {
        Stack<Contact> stack = new Stack<>();
        stack.push(contact);
        while (stack.size() > 0) {
            Contact cont = stack.pop();
            if (!contactList.contains(cont)) {
                contactList.add(cont);
            }
            Map<String, String> mapFields = new HashMap<String, String>();
            mapFields.put("parentId", cont.getId());
            RealmResults<Contact> contactFilter = RealmDao.with(this).findFilterAnd(Contact.class, mapFields);
            if (contactFilter != null && contactFilter.size() > 0) {
                for (int i = 0; i < contactFilter.size(); i++) {
                    stack.push(realm.copyFromRealm(contactFilter.get(i)));
                }
            }
        }
    }

    private void searchParent(Contact contact) {
        if (!contactList.contains(contact)) {
            contactList.add(contact);
        }
        if (contact.getParentId() != null && !contact.getParentId().equals("")) {
            Contact contactParent = RealmDao.with(this).findByKey(Contact.class, contact.getParentId(), "id");
            if (contactParent != null) {
                searchParent(realm.copyFromRealm(contactParent));
            }
        }
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

    @Override
    public void onSuccessSync(List<ContactInfo> contactInfos) {
        realm = RealmDao.with(this).getRealm();
        clearContacts();
        if (contactInfos != null && contactInfos.size() > 0) {
            txtNoData.setVisibility(View.GONE);
            ContactBuilder contactBuilder = new ContactBuilder(getContext());
            saveContacts(contactBuilder.convertFromContactInfos(contactInfos));
            displayContact();
        }else {
            txtNoData.setVisibility(View.VISIBLE);
        }

    }
    private void clearContacts() {
        realm.beginTransaction();
        realm.delete(Contact.class);
        realm.commitTransaction();
    }
    private void saveContacts(final List<Contact> contacts) {
        realm.beginTransaction();
        realm.copyToRealm(contacts);
        realm.commitTransaction();
    }

    @Override
    public void onErrorSync(APIError apiError) {

    }

    @Override
    public void showProgressSync() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressDialog();
    }

    @Override
    public void hideProgressSync() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).hideProgressDialog();
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            contactPresenter.getContacts();
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {

    }

    @Override
    public void showProgress() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressDialog();
    }

    @Override
    public void hideProgress() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).hideProgressDialog();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String init) {/* Init */};

}
