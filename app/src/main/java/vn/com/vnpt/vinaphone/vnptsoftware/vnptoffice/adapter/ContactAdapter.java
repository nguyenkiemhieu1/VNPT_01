package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;
import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.RealmDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.userinfo.UserInfoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Contact;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.ExceptionErrorPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.exceptionError.IExceptionErrorPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ContactDetailActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ContactEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent.IExceptionErrorView;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class ContactAdapter extends ArrayAdapter<Contact> implements IExceptionErrorView {
    private Context context;
    private int resource;
    private int resourceDetail;
    private int resourceListDetail;
    private RealmResults<Contact> contacts;
    private List<Boolean> touchs;
    private List<Integer> sizes;
    private List<Integer> countUsers;
    private ICallFinishedListener callFinishedListener;
    private ConnectionDetector connectionDetector;
    private UserInfoDao userInfoDao;
    private ApplicationSharedPreferences appPrefs;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    public ContactAdapter(Context context, int resource, int resourceDetail, int resourceListDetail, RealmResults<Contact> contacts, List<Integer> sizes, List<Integer> countUsers) {
        super(context, resource, contacts);
        this.context = context;
        this.resource = resource;
        this.resourceDetail = resourceDetail;
        this.resourceListDetail = resourceListDetail;
        this.contacts = contacts;
        this.sizes = sizes;
        this.countUsers = countUsers;
        touchs = new ArrayList<Boolean>(contacts.size());
        for (int i = 0; i < contacts.size(); i++) {
            touchs.add(false);
        }
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (sizes.get(position) > 0) {
            view = inflater.inflate(this.resource, null);
            final TextView txtNameContact = (TextView) view.findViewById(R.id.txtNameContact);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) txtNameContact.getLayoutParams();
            params.setMargins(0, 35, 0, 0);
            if (countUsers.get(position).intValue() > 0) {
                txtNameContact.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
                txtNameContact.setText(" " + contacts.get(position).getUserName() + " " + "(" + countUsers.get(position).intValue() + ")");
            } else {
                txtNameContact.setTypeface(Application.getApp().getTypeface());
                txtNameContact.setText(" " + contacts.get(position).getUserName());
            }
            final LinearLayout layoutContact = (LinearLayout) view.findViewById(R.id.subContact);
            txtNameContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutContact.removeAllViewsInLayout();
                    if (!touchs.get(position)) {
                        touchs.set(position, true);
                        txtNameContact.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
                        Map<String, String> mapFields = new HashMap<String, String>();
                        mapFields.put("parentId", contacts.get(position).getId());
                        RealmResults<Contact> subContacts = RealmDao.with((FragmentActivity) context).findFilterAnd(Contact.class, mapFields);
                        List<Integer> sizes = new ArrayList<Integer>(subContacts.size());
                        List<Integer> countUsers = new ArrayList<Integer>(subContacts.size());
                        for (Contact contact : subContacts) {
                            mapFields = new HashMap<String, String>();
                            mapFields.put("parentId", contact.getId());
                            long count = RealmDao.with((FragmentActivity) context).countFilterAnd(Contact.class, mapFields);
                            sizes.add((int) count);
                            mapFields.put("isUser", "true");
                            count = RealmDao.with((FragmentActivity) context).countFilterAnd(Contact.class, mapFields);
                            countUsers.add((int) count);
                        }
                        ContactAdapter contactAdapter = new ContactAdapter(context, R.layout.item_contact_list_sub, R.layout.item_contact_detail, R.layout.item_contact_list_detail, subContacts, sizes, countUsers);
                        int adapterCount = contactAdapter.getCount();
                        for (int i = 0; i < adapterCount; i++) {
                            View item = contactAdapter.getView(i, null, null);
                            layoutContact.addView(item);
                        }
                        layoutContact.setVisibility(View.VISIBLE);
                    } else {
                        txtNameContact.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                        layoutContact.setVisibility(View.GONE);
                        touchs.set(position, false);
                    }
                }
            });
        } else {
            if (contacts.get(position).getIsUser().equals("true")) {
                view = inflater.inflate(this.resourceDetail, null);
                TextView txtName = (TextView) view.findViewById(R.id.txtName);
                TextView txtPosition = (TextView) view.findViewById(R.id.txtPosition);
                TextView txtPhone = (TextView) view.findViewById(R.id.txtPhone);
                TextView txtEmail = (TextView) view.findViewById(R.id.txtEmail);
                final ImageView avatarUser = (ImageView) view.findViewById(R.id.img_anh_dai_dien);
                txtName.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
                txtPosition.setTypeface(Application.getApp().getTypeface());
                txtPhone.setTypeface(Application.getApp().getTypeface());
                txtEmail.setTypeface(Application.getApp().getTypeface());
                Glide.with(context).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                        .bitmapTransform(new CircleTransform(context)).into(avatarUser);
                if (contacts.get(position).getUserName() != null && !contacts.get(position).getUserName().equals("")
                        && !contacts.get(position).getUserName().equals("null")) {
                    txtName.setText(contacts.get(position).getUserName());
                }
                if (contacts.get(position).getPosition() != null && !contacts.get(position).getPosition().equals("")
                        && !contacts.get(position).getPosition().equals("null")) {
                    txtPosition.setText(contacts.get(position).getPosition());
                }
                if (contacts.get(position).getPhone() != null && !contacts.get(position).getPhone().equals("")
                        && !contacts.get(position).getPhone().equals("null")) {
                    txtPhone.setText(contacts.get(position).getPhone());
                }
                if (contacts.get(position).getEmail() != null && !contacts.get(position).getEmail().equals("")
                        && !contacts.get(position).getEmail().equals("null")) {
                    txtEmail.setText(contacts.get(position).getEmail());
                }
                LinearLayout layoutContactDetail = (LinearLayout) view.findViewById(R.id.layoutContactDetail);
                layoutContactDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().postSticky(new ContactEvent(contacts.get(position).getId()));
                        context.startActivity(new Intent(context, ContactDetailActivity.class));
                    }
                });
                connectionDetector = new ConnectionDetector(context);
                if (connectionDetector.isConnectingToInternet()) {
                    callFinishedListener = new ICallFinishedListener() {
                        @Override
                        public void onCallSuccess(Object object) {
                            try {
                                ResponseBody responseBody = (ResponseBody) object;
                                Glide.with(context).load(responseBody.bytes()).error(R.drawable.ic_avatar)
                                        .bitmapTransform(new CircleTransform(context)).into(avatarUser);
                            } catch (Exception ex) {

                            }
                        }
                        @Override
                        public void onCallError(Object object) {
                            APIError apiError = (APIError) object;
                            sendExceptionError(apiError);
                        }
                    };
                    userInfoDao = new UserInfoDao();
                    userInfoDao.onGetAvatarDao(callFinishedListener, contacts.get(position).getUserId());
                }
            } else {
                view = inflater.inflate(this.resourceListDetail, null);
                TextView txtNameContact = (TextView) view.findViewById(R.id.txtNameContact);
                txtNameContact.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
                if (contacts.get(position).getUserName() != null && !contacts.get(position).getUserName().equals("")
                        && !contacts.get(position).getUserName().equals("null")) {
                    txtNameContact.setText(contacts.get(position).getUserName());
                }
            }
        }
        return view;
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
        appPrefs = Application.getApp().getAppPrefs();
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
}
