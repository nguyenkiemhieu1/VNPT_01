package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.userinfo.UserInfoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.PersonReceiveChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.EditchiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ForwardChiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ReplyChiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.SendChiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.KeywordEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoAddEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonChiDaoResultEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonEditChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonChiDaoCheck;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class PersonChiDaoAdapter extends ArrayAdapter<PersonChiDao> {
    private Context context;
    private Activity activity;
    private int resource;
    private int resourceDetail;
    private int resourceListDetail;
    private List<PersonChiDao> personChiDaos;
    private List<Boolean> touchs;
    private List<Integer> sizes;
    private List<Integer> countUsers;
    private ICallFinishedListener callFinishedListener;
    private ConnectionDetector connectionDetector;
    private UserInfoDao userInfoDao;
    private String type;
//    private ProgressDialog progressDialog;

    public PersonChiDaoAdapter(Context context, int resource, int resourceDetail, int resourceListDetail,
                               List<PersonChiDao> personChiDaos, List<Integer> sizes, List<Integer> countUsers, String type) {
        super(context, resource, personChiDaos);
        this.context = context;
        this.activity = (Activity) context;
        this.resource = resource;
        this.resourceDetail = resourceDetail;
        this.resourceListDetail = resourceListDetail;
        this.personChiDaos = personChiDaos;
        this.sizes = sizes;
        this.countUsers = countUsers;
        this.type = type;
        touchs = new ArrayList<Boolean>(personChiDaos.size());
        for (int i = 0; i < personChiDaos.size(); i++) {
            touchs.add(true);
        }
//        progressDialog = new ProgressDialog((Activity) context);
//        progressDialog.setMessage("Please wait...");
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        String keyword = EventBus.getDefault().getStickyEvent(KeywordEvent.class).getKeyword();
        List<PersonChiDao> personChiDaoAllList = EventBus.getDefault().getStickyEvent(PersonChiDaoResultEvent.class).getPersonChiDaoList();
        if (sizes.get(position) > 0) {
            view = inflater.inflate(this.resource, null);
            final TextView txtNameContact = (TextView) view.findViewById(R.id.txtNameContact);
            final CheckBox checkChon = (CheckBox) view.findViewById(R.id.checkChon);
            ImageView checked = (ImageView) view.findViewById(R.id.checked);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) txtNameContact.getLayoutParams();
            params.setMargins(0, 35, 0, 0);
            if (countUsers.get(position).intValue() > 0) {
                txtNameContact.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
                if (keyword != null && !keyword.equals("") && personChiDaos.get(position).getFullName().toUpperCase().contains(keyword.toUpperCase())) {
                    int index = personChiDaos.get(position).getFullName().toUpperCase().indexOf(keyword.toUpperCase());
                    String keyColor = "<font color='red'>" + personChiDaos.get(position).getFullName().substring(index, keyword.length() + index) + "</font>";
                    txtNameContact.setText(Html.fromHtml(personChiDaos.get(position).getFullName().substring(0, index) + keyColor + personChiDaos.get(position).getFullName().substring(index + keyword.length(), personChiDaos.get(position).getFullName().length())));
                } else {
                    txtNameContact.setText(" " + personChiDaos.get(position).getFullName() + " " + "(" + countUsers.get(position).intValue() + ")");
                }
            } else {
                txtNameContact.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
                if (keyword != null && !keyword.equals("") && personChiDaos.get(position).getFullName().toUpperCase().contains(keyword.toUpperCase())) {
                    int index = personChiDaos.get(position).getFullName().toUpperCase().indexOf(keyword.toUpperCase());
                    String keyColor = "<font color='red'>" + personChiDaos.get(position).getFullName().substring(index, keyword.length() + index) + "</font>";
                    txtNameContact.setText(Html.fromHtml(personChiDaos.get(position).getFullName().substring(0, index) + keyColor + personChiDaos.get(position).getFullName().substring(index + keyword.length(), personChiDaos.get(position).getFullName().length())));
                } else {
                    txtNameContact.setText(" " + personChiDaos.get(position).getFullName());
                }
            }
            final LinearLayout layoutContact = (LinearLayout) view.findViewById(R.id.subContact);
            final ListView listView = (ListView) view.findViewById(R.id.listview_content);

            if (touchs.get(position)) {
                txtNameContact.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
                layoutContact.setVisibility(View.VISIBLE);
                List<PersonChiDao> personChiDaoList = EventBus.getDefault().getStickyEvent(PersonChiDaoResultEvent.class).getPersonChiDaoList();
                List<PersonChiDao> subContacts = new ArrayList<>();
                for (int i = 0; i < personChiDaoList.size(); i++) {
                    if (personChiDaoList.get(i).getParentId() != null && personChiDaoList.get(i).getParentId().equals(personChiDaos.get(position).getUnitId())
                            && personChiDaoList.get(i).getId() != personChiDaos.get(position).getId()) {
                        subContacts.add(personChiDaoList.get(i));
                    }
                }
                List<Integer> sizes = new ArrayList<Integer>(subContacts.size());
                List<Integer> countUsers = new ArrayList<Integer>(subContacts.size());
                for (PersonChiDao contact : subContacts) {
                    int count = 0;
                    int countUser = 0;
                    for (int i = 0; i < personChiDaoList.size(); i++) {
                        if (personChiDaoList.get(i).getParentId() != null && personChiDaoList.get(i).getParentId().equals(contact.getUnitId())
                                && personChiDaoList.get(i).getId() != contact.getId() && !personChiDaoList.get(i).getParentId().equals(contact.getParentId())) {
                            count++;
                            if (personChiDaoList.get(i).getChucVu() != null && !personChiDaoList.get(i).getChucVu().trim().equals("")) {
                                countUser++;
                            }
                        }
                    }
                    sizes.add(count);
                    countUsers.add(countUser);
                }
                PersonChiDaoAdapter contactAdapter = new PersonChiDaoAdapter(context, R.layout.item_person_chidao_list_sub, R.layout.item_person_chidao_detail, R.layout.item_person_chidao_list_detail, subContacts, sizes, countUsers, type);
//                listView.setAdapter(contactAdapter);
//                setListViewHeightBasedOnChildren(view,listView,contactAdapter);
//                view.invalidate();
                int adapterCount = contactAdapter.getCount();
                for (int i = 0; i < adapterCount; i++) {
                    View item = contactAdapter.getView(i, null, null);
                    layoutContact.addView(item);
                }
            }

            txtNameContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //layoutContact.removeAllViewsInLayout();
                    if (!touchs.get(position)) {
//
                        touchs.set(position, true);
                        txtNameContact.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
                        layoutContact.setVisibility(View.VISIBLE);
                    } else {
                        txtNameContact.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                        layoutContact.setVisibility(View.GONE);
                        touchs.set(position, false);
                    }
                }
            });
            checkChon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!txtNameContact.getText().toString().equalsIgnoreCase("Khác")){
                        new ProgressTask(position, checkChon,txtNameContact,layoutContact).execute();
                    }
//
                }
            });
//
            PersonEditChiDaoEvent personEditChiDaoEvent = EventBus.getDefault().getStickyEvent(PersonEditChiDaoEvent.class);
            if (personEditChiDaoEvent != null) {
                if (personEditChiDaoEvent.getPersonReceiveChiDaos() != null) {
                    List<PersonReceiveChiDao> personReceiveChiDaos = personEditChiDaoEvent.getPersonReceiveChiDaos();
                    for (int i = 0; i < personReceiveChiDaos.size(); i++) {
                        if (personReceiveChiDaos.get(i).getNgayNhan() != null && personReceiveChiDaos.get(i).getId().equals(personChiDaos.get(position).getId())) {
                            checkChon.setVisibility(View.GONE);
                            checked.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        } else {
            if (personChiDaos.get(position).getChucVu() != null && !personChiDaos.get(position).getChucVu().trim().equals("")) {
                view = inflater.inflate(this.resourceDetail, null);
                TextView txtName = (TextView) view.findViewById(R.id.txtName);
                TextView txtPosition = (TextView) view.findViewById(R.id.txtPosition);
                TextView txtPhone = (TextView) view.findViewById(R.id.txtPhone);
                TextView txtEmail = (TextView) view.findViewById(R.id.txtEmail);
                final CheckBox checkChon = (CheckBox) view.findViewById(R.id.checkChon);
                ImageView checked = (ImageView) view.findViewById(R.id.checked);
                final ImageView avatarUser = (ImageView) view.findViewById(R.id.img_anh_dai_dien);
                txtName.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
                txtPosition.setTypeface(Application.getApp().getTypeface());
                txtPhone.setTypeface(Application.getApp().getTypeface());
                txtEmail.setTypeface(Application.getApp().getTypeface());
                Glide.with(context.getApplicationContext()).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                        .bitmapTransform(new CircleTransform(context)).into(avatarUser);
                if (personChiDaos.get(position).getFullName() != null && !personChiDaos.get(position).getFullName().equals("")
                        && !personChiDaos.get(position).getFullName().equals("null")) {
                    if (keyword != null && !keyword.equals("") && personChiDaos.get(position).getFullName().toUpperCase().contains(keyword.toUpperCase())) {
                        int index = personChiDaos.get(position).getFullName().toUpperCase().indexOf(keyword.toUpperCase());
                        String keyColor = "<font color='red'>" + personChiDaos.get(position).getFullName().substring(index, keyword.length() + index) + "</font>";
                        txtName.setText(Html.fromHtml(personChiDaos.get(position).getFullName().substring(0, index) + keyColor + personChiDaos.get(position).getFullName().substring(index + keyword.length(), personChiDaos.get(position).getFullName().length())));
                    } else {
                        txtName.setText(personChiDaos.get(position).getFullName());
                    }
                }
                PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
                List<String> chons = emails.getEmails();
                if (chons != null) {
                    if (chons.contains(personChiDaos.get(position).getId())) {
                        checkChon.setChecked(true);
                    }
                }
                PersonEditChiDaoEvent personEditChiDaoEvent = EventBus.getDefault().getStickyEvent(PersonEditChiDaoEvent.class);
                if (personEditChiDaoEvent != null) {
                    if (personEditChiDaoEvent.getPersonReceiveChiDaos() != null) {
                        List<PersonReceiveChiDao> personReceiveChiDaos = personEditChiDaoEvent.getPersonReceiveChiDaos();
                        for (int i = 0; i < personReceiveChiDaos.size(); i++) {
                            if (personReceiveChiDaos.get(i).getNgayNhan() != null && personReceiveChiDaos.get(i).getId().equals(personChiDaos.get(position).getId())) {
                                checkChon.setVisibility(View.GONE);
                                checked.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                txtPosition.setText(personChiDaos.get(position).getChucVu());
                //txtPhone.setText(personChiDaos.get(position).getPhone());
                txtEmail.setText(personChiDaos.get(position).getEmail());
                connectionDetector = new ConnectionDetector(context);
//                if (connectionDetector.isConnectingToInternet()) {
//                    callFinishedListener = new ICallFinishedListener() {
//                        @Override
//                        public void onCallSuccess(Object object) {
//                            try {
//                                ResponseBody responseBody = (ResponseBody) object;
//                                Glide.with(context).load(responseBody.bytes()).error(R.drawable.ic_avatar)
//                                        .bitmapTransform(new CircleTransform(context)).into(avatarUser);
//                            } catch (Exception ex) {
//
//                            }
//                        }
//
//                        @Override
//                        public void onCallError(Object object) {
//                        }
//                    };
//                    userInfoDao = new UserInfoDao();
//                    userInfoDao.onGetAvatarDao(callFinishedListener, personChiDaos.get(position).getId());
//                }
                checkChon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean checked = false;
                        PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
                        List<String> chons = emails.getEmails();
                        if (checkChon.isChecked()) {
                            if (!chons.contains(personChiDaos.get(position).getId())) {
                                chons.add(personChiDaos.get(position).getId());
                            }
                        } else {
                            chons.remove(personChiDaos.get(position).getId());
                            List<String> lst = new ArrayList<String>();
                            List<PersonChiDao> personChiDaoList = EventBus.getDefault().getStickyEvent(PersonChiDaoResultEvent.class).getPersonChiDaoList();
                            Stack<PersonChiDao> stack = new Stack<>();
                            stack.push(personChiDaos.get(position));
                            while (stack.size() > 0) {
                                PersonChiDao cont = stack.pop();
                                chons.remove(cont.getId());
                                for (int i = 0; i < personChiDaoList.size(); i++) {
                                    if (cont.getParentId() == null || cont.getParentId().trim().equals("")) {
                                        stack.clear();
                                        break;
                                    }
                                    if (checked) {
                                        if (cont.getParentId() != null && !cont.getParentId().trim().equals("") && cont.getParentId().equals(personChiDaoList.get(i).getUnitId())
                                                && personChiDaoList.get(i).getId() != cont.getId()
                                                && (cont.getChucVu() == null || cont.getChucVu().trim().equals(""))) {
                                            stack.push(personChiDaoList.get(i));
                                            break;
                                        }
                                    } else {
                                        if (cont.getParentId() != null && !cont.getParentId().trim().equals("") && cont.getParentId().equals(personChiDaoList.get(i).getUnitId())
                                                && personChiDaoList.get(i).getId() != cont.getId()) {
                                            stack.push(personChiDaoList.get(i));
                                            checked = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        PersonChiDaoCheckEvent personChiDaoCheckEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoCheckEvent.class);
                        List<PersonChiDaoCheck> personChiDaoChecks = personChiDaoCheckEvent.getPersonChiDaoCheckList();
                        for (int i = 0; i < personChiDaoChecks.size(); i++) {
                            checked = false;
                            for (int j = 0; j < chons.size(); j++) {
                                if (personChiDaoChecks.get(i).getId() != null && personChiDaoChecks.get(i).getId().equals(chons.get(j))) {
                                    checked = true;
                                    break;
                                } else {
                                    if (personChiDaoChecks.get(i).getId() == null) {
                                        Toast.makeText(context, String.valueOf(i), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            if (checked) {
                                View view1 = personChiDaoChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkChon);
                                check.setChecked(true);
                                personChiDaoChecks.get(i).setView(view1);
                            } else {
                                View view1 = personChiDaoChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkChon);
                                check.setChecked(false);
                                personChiDaoChecks.get(i).setView(view1);
                            }
                        }
                        personChiDaoCheckEvent.setPersonChiDaoCheckList(personChiDaoChecks);
                        EventBus.getDefault().postSticky(personChiDaoCheckEvent);
                        emails.setEmails(chons);
                        EventBus.getDefault().postSticky(emails);
                    }
                });
            } else {
                view = inflater.inflate(this.resourceListDetail, null);
                TextView txtNameContact = (TextView) view.findViewById(R.id.txtNameContact);
                final CheckBox checkChon = (CheckBox) view.findViewById(R.id.checkChon);
                ImageView checked = (ImageView) view.findViewById(R.id.checked);
                txtNameContact.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
                if (personChiDaos.get(position).getFullName() != null && !personChiDaos.get(position).getFullName().equals("")
                        && !personChiDaos.get(position).getFullName().equals("null")) {
                    if (keyword != null && !keyword.equals("") && personChiDaos.get(position).getFullName().toUpperCase().contains(keyword.toUpperCase())) {
                        int index = personChiDaos.get(position).getFullName().toUpperCase().indexOf(keyword.toUpperCase());
                        String keyColor = "<font color='red'>" + personChiDaos.get(position).getFullName().substring(index, keyword.length() + index) + "</font>";
                        txtNameContact.setText(Html.fromHtml(personChiDaos.get(position).getFullName().substring(0, index) + keyColor + personChiDaos.get(position).getFullName().substring(index + keyword.length(), personChiDaos.get(position).getFullName().length())));
                    } else {
                        txtNameContact.setText(personChiDaos.get(position).getFullName());
                    }
                }
                checkChon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
                        List<String> chons = emails.getEmails();
                        boolean checked = false;
                        if (checkChon.isChecked()) {
                            if (!chons.contains(personChiDaos.get(position).getId())) {
                                chons.add(personChiDaos.get(position).getId());
                            }
                        } else {
                            chons.remove(personChiDaos.get(position).getId());
                            List<String> lst = new ArrayList<String>();
                            List<PersonChiDao> personChiDaoList = EventBus.getDefault().getStickyEvent(PersonChiDaoResultEvent.class).getPersonChiDaoList();
                            Stack<PersonChiDao> stack = new Stack<>();
                            stack.push(personChiDaos.get(position));
                            while (stack.size() > 0) {
                                PersonChiDao cont = stack.pop();
                                chons.remove(cont.getId());
                                for (int i = 0; i < personChiDaoList.size(); i++) {
                                    if (cont.getParentId() == null || cont.getParentId().trim().equals("")) {
                                        stack.clear();
                                        break;
                                    }
                                    if (checked) {
                                        if (cont.getParentId() != null && !cont.getParentId().trim().equals("") && cont.getParentId().equals(personChiDaoList.get(i).getUnitId())
                                                && personChiDaoList.get(i).getId() != cont.getId()
                                                && (cont.getChucVu() == null || cont.getChucVu().trim().equals(""))) {
                                            stack.push(personChiDaoList.get(i));
                                            break;
                                        }
                                    } else {
                                        if (cont.getParentId() != null && !cont.getParentId().trim().equals("") && cont.getParentId().equals(personChiDaoList.get(i).getUnitId())
                                                && personChiDaoList.get(i).getId() != cont.getId()) {
                                            stack.push(personChiDaoList.get(i));
                                            checked = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        PersonChiDaoCheckEvent personChiDaoCheckEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoCheckEvent.class);
                        List<PersonChiDaoCheck> personChiDaoChecks = personChiDaoCheckEvent.getPersonChiDaoCheckList();
                        for (int i = 0; i < personChiDaoChecks.size(); i++) {
                            checked = false;
                            for (int j = 0; j < chons.size(); j++) {
                                if (personChiDaoChecks.get(i).getId().equals(chons.get(j))) {
                                    checked = true;
                                    break;
                                }
                            }
                            if (checked) {
                                View view1 = personChiDaoChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkChon);
                                check.setChecked(true);
                                personChiDaoChecks.get(i).setView(view1);
                            } else {
                                View view1 = personChiDaoChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkChon);
                                check.setChecked(false);
                                personChiDaoChecks.get(i).setView(view1);
                            }
                        }
                        personChiDaoCheckEvent.setPersonChiDaoCheckList(personChiDaoChecks);
                        EventBus.getDefault().postSticky(personChiDaoCheckEvent);
                        emails.setEmails(chons);
                        EventBus.getDefault().postSticky(emails);
                    }
                });
                PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
                List<String> chons = emails.getEmails();
                if (chons != null) {
                    if (chons.contains(personChiDaos.get(position).getId())) {
                        checkChon.setChecked(true);
                    }
                }
                PersonEditChiDaoEvent personEditChiDaoEvent = EventBus.getDefault().getStickyEvent(PersonEditChiDaoEvent.class);
                if (personEditChiDaoEvent != null) {
                    if (personEditChiDaoEvent.getPersonReceiveChiDaos() != null) {
                        List<PersonReceiveChiDao> personReceiveChiDaos = personEditChiDaoEvent.getPersonReceiveChiDaos();
                        for (int i = 0; i < personReceiveChiDaos.size(); i++) {
                            if (personReceiveChiDaos.get(i).getNgayNhan() != null && personReceiveChiDaos.get(i).getId().equals(personChiDaos.get(position).getId())) {
                                checkChon.setVisibility(View.GONE);
                                checked.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }
        }
        PersonChiDaoCheckEvent personChiDaoCheckEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoCheckEvent.class);
        List<PersonChiDaoCheck> personChiDaoChecks = personChiDaoCheckEvent.getPersonChiDaoCheckList();
        PersonChiDaoCheck personChiDaoCheck = new PersonChiDaoCheck(view, personChiDaos.get(position).getId());
        personChiDaoChecks.add(personChiDaoCheck);
        personChiDaoCheckEvent.setPersonChiDaoCheckList(personChiDaoChecks);
        EventBus.getDefault().postSticky(personChiDaoCheckEvent);
        if (personChiDaoChecks.size() == personChiDaoAllList.size()) {
            switch (type) {
                case "SEND":
                    ((SendChiDaoV2Activity) context).showContact();
                    break;
                case "EDIT":
                    ((EditchiDaoV2Activity) context).showContact();
                    break;
                case "REPLY":
                    ((ReplyChiDaoV2Activity) context).showContact();
                    break;
                case "FW":
                    ((ForwardChiDaoV2Activity) context).showContact();
                    break;
            }
        }
        return view;
    }

    public void updateAdapter() {
        PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
        List<String> chons = emails.getEmails();
        PersonChiDaoCheckEvent personChiDaoCheckEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoCheckEvent.class);
        List<PersonChiDaoCheck> personChiDaoChecks = personChiDaoCheckEvent.getPersonChiDaoCheckList();
        for (int i = 0; i < personChiDaoChecks.size(); i++) {
            boolean checked = false;
            for (int j = 0; j < chons.size(); j++) {
                if (personChiDaoChecks.get(i).getId().equals(chons.get(j))) {
                    checked = true;
                    break;
                }
            }
            if (checked) {
                View view1 = personChiDaoChecks.get(i).getView();
                CheckBox check = (CheckBox) view1.findViewById(R.id.checkChon);
                check.setChecked(true);
                personChiDaoChecks.get(i).setView(view1);
            } else {
                View view1 = personChiDaoChecks.get(i).getView();
                CheckBox check = (CheckBox) view1.findViewById(R.id.checkChon);
                check.setChecked(false);
                personChiDaoChecks.get(i).setView(view1);
            }
        }
        personChiDaoCheckEvent.setPersonChiDaoCheckList(personChiDaoChecks);
        EventBus.getDefault().postSticky(personChiDaoCheckEvent);
        emails.setEmails(chons);
        EventBus.getDefault().postSticky(emails);
//        if (!touchs.get(position) && checkChon.isChecked()) {
//            touchs.set(position, true);
//            txtNameContact.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
//            layoutContact.setVisibility(View.VISIBLE);
//        }
    }
    private class ProgressTask extends AsyncTask<Void, Long, Boolean> {
        int position;
        CheckBox checkChon;
        TextView txtNameContact;
        LinearLayout layoutContact;
        private ProgressDialog dialog;

        public ProgressTask(int position, CheckBox checkChon, TextView txtNameContact, LinearLayout layoutContact) {
            this.position = position;
            this.checkChon = checkChon;
            this.txtNameContact = txtNameContact;
            this.layoutContact = layoutContact;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            try {
//                this.dialog.setMessage("Vui lòng đợi...");
//                this.dialog.show();
//            } catch (final Throwable th) {
//            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
            List<String> chons = emails.getEmails();
            List<PersonChiDao> personChiDaoList = EventBus.getDefault().getStickyEvent(PersonChiDaoResultEvent.class).getPersonChiDaoList();
            Stack<PersonChiDao> stack = new Stack<>();
            stack.push(personChiDaos.get(position));
            while (stack.size() > 0) {
                PersonChiDao cont = stack.pop();
                if (checkChon.isChecked()) {
                    if (!chons.contains(cont.getId())) {
                        chons.add(cont.getId());
                    }
                } else {
                    chons.remove(cont.getId());
                }
                for (int i = 0; i < personChiDaoList.size(); i++) {
                    if (cont.getUnitId().equals(personChiDaoList.get(i).getParentId())
                            && personChiDaoList.get(i).getId() != cont.getId()
                            && (cont.getChucVu() == null || cont.getChucVu().trim().equals(""))) {
                        stack.push(personChiDaoList.get(i));
                    }
                }
            }
            if (!checkChon.isChecked()) {
                stack = new Stack<>();
                stack.push(personChiDaos.get(position));
                while (stack.size() > 0) {
                    PersonChiDao cont = stack.pop();
                    chons.remove(cont.getId());
                    for (int i = 0; i < personChiDaoList.size(); i++) {
                        if (cont.getParentId() != null && !cont.getParentId().trim().equals("") && cont.getParentId().equals(personChiDaoList.get(i).getUnitId())
                                && personChiDaoList.get(i).getId() != cont.getId()
                                && (cont.getChucVu() == null || cont.getChucVu().trim().equals(""))) {
                            stack.push(personChiDaoList.get(i));
                            break;
                        }
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
            List<String> chons = emails.getEmails();
            PersonChiDaoCheckEvent personChiDaoCheckEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoCheckEvent.class);
            List<PersonChiDaoCheck> personChiDaoChecks = personChiDaoCheckEvent.getPersonChiDaoCheckList();
            for (int i = 0; i < personChiDaoChecks.size(); i++) {
                boolean checked = false;
                for (int j = 0; j < chons.size(); j++) {
                    if (personChiDaoChecks.get(i).getId().equals(chons.get(j))) {
                        checked = true;
                        break;
                    }
                }
                if (checked) {
                    View view1 = personChiDaoChecks.get(i).getView();
                    CheckBox check = (CheckBox) view1.findViewById(R.id.checkChon);
                    check.setChecked(true);
                    personChiDaoChecks.get(i).setView(view1);
                } else {
                    View view1 = personChiDaoChecks.get(i).getView();
                    CheckBox check = (CheckBox) view1.findViewById(R.id.checkChon);
                    check.setChecked(false);
                    personChiDaoChecks.get(i).setView(view1);
                }
            }
            personChiDaoCheckEvent.setPersonChiDaoCheckList(personChiDaoChecks);
            EventBus.getDefault().postSticky(personChiDaoCheckEvent);
            emails.setEmails(chons);
            EventBus.getDefault().postSticky(emails);
            if (!touchs.get(position) && checkChon.isChecked()) {
                touchs.set(position, true);
                txtNameContact.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
                layoutContact.setVisibility(View.VISIBLE);
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
