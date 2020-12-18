package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonSendNotifyCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonSendNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonSendNotifyCheck;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class SelectSendProcessAdapter extends ArrayAdapter<PersonSendNotifyInfo> {
    private Context context;
    private int resource;
    private int resourceDetail;
    private List<PersonSendNotifyInfo> personSendNotifyInfoList;
    private List<Boolean> touchs;
    private List<Integer> sizes;
    private String type;

    public SelectSendProcessAdapter(Context context, int resource, int resourceDetail, List<PersonSendNotifyInfo> personSendNotifyInfoList, List<Integer> sizes, List<Boolean> touchs, String type) {
        super(context, resource, personSendNotifyInfoList);
        this.context = context;
        this.resource = resource;
        this.resourceDetail = resourceDetail;
        this.personSendNotifyInfoList = personSendNotifyInfoList;
        this.sizes = sizes;
        this.touchs = touchs;
        this.type = type;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        boolean notParent = true;
        List<Person> personList = new ArrayList<>();
        List<Person> personGroupList = new ArrayList<>();
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
        if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
            if (listPersonEvent != null) {
                personList = listPersonEvent.getPersonDirectList();
                personGroupList = listPersonEvent.getPersonGroupList();
            }
        } else {
            if (listPersonEvent != null) {
                personList = listPersonEvent.getPersonProcessList();
                personGroupList = listPersonEvent.getPersonGroupList();
            }
        }
        if (personList == null) {
            personList = new ArrayList<Person>();
        }
        if (personGroupList == null) {
            personGroupList = new ArrayList<Person>();
        }
        if (sizes.get(position) > 0) {
            view = inflater.inflate(this.resource, null);
            notParent = false;
            final TextView txtNameDonvi = (TextView) view.findViewById(R.id.txtNameDonvi);
            final CheckBox checkDongXL = (CheckBox) view.findViewById(R.id.checkDongXL);
            final CheckBox checkXemDB = (CheckBox) view.findViewById(R.id.checkXemDB);
            final RadioButton checkXLChinh = (RadioButton) view.findViewById(R.id.checkXLChinh);
            txtNameDonvi.setTypeface(Application.getApp().getTypeface());
            txtNameDonvi.setText(" " + personSendNotifyInfoList.get(position).getName());
            if (personList != null && personList.size() > 0) {
                for (Person person : personList) {
                    if (personSendNotifyInfoList.get(position).getId().equals(person.getId())) {
                        if (person.isXlc()) {
                            checkXLChinh.setChecked(true);
                            checkDongXL.setChecked(false);
                            //checkDongXL.setEnabled(false);
                            checkXemDB.setChecked(false);
                            //checkXemDB.setEnabled(false);
                        }
                        if (person.isDxl()) {
                            checkDongXL.setChecked(true);
                            checkXemDB.setChecked(false);
                            checkXLChinh.setChecked(false);
                        }
                        if (person.isXem()) {
                            checkDongXL.setChecked(false);
                            checkXemDB.setChecked(true);
                            checkXLChinh.setChecked(false);
                        }
                    }
                }
            }
            if (personGroupList != null && personGroupList.size() > 0) {
                if (type.equals("1")) {
                    for (Person person : personGroupList) {
                        if (personSendNotifyInfoList.get(position).getId().equals("U" + person.getId().split("\\|")[1])) {
                            if (person.isXlc()) {
                                checkXLChinh.setChecked(true);
                                checkDongXL.setChecked(false);
                                //checkDongXL.setEnabled(false);
                                checkXemDB.setChecked(false);
                                //checkXemDB.setEnabled(false);
                            }
                            if (person.isDxl()) {
                                checkDongXL.setChecked(true);
                                checkXemDB.setChecked(false);
                                checkXLChinh.setChecked(false);
                            }
                            if (person.isXem()) {
                                checkDongXL.setChecked(false);
                                checkXemDB.setChecked(true);
                                checkXLChinh.setChecked(false);
                            }
                        }
                    }
                }
                if (type.equals("0") || type.equals("2")) {
                    for (Person person : personGroupList) {
                        if (personSendNotifyInfoList.get(position).getId().equals(person.getId())) {
                            if (person.isXlc()) {
                                checkXLChinh.setChecked(true);
                                checkDongXL.setChecked(false);
                                //checkDongXL.setEnabled(false);
                                checkXemDB.setChecked(false);
                                //checkXemDB.setEnabled(false);
                            }
                            if (person.isDxl()) {
                                checkDongXL.setChecked(true);
                                checkXemDB.setChecked(false);
                                checkXLChinh.setChecked(false);
                            }
                            if (person.isXem()) {
                                checkDongXL.setChecked(false);
                                checkXemDB.setChecked(true);
                                checkXLChinh.setChecked(false);
                            }
                        }
                    }
                }
            }
            final LinearLayout layoutPersonDonvi = (LinearLayout) view.findViewById(R.id.personDonvi);
            if (!touchs.get(position)) {
                layoutPersonDonvi.setPadding(40, 0, 0, 0);
                txtNameDonvi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
                layoutPersonDonvi.setVisibility(View.VISIBLE);
                touchs.set(position, true);
                List<PersonSendNotifyInfo> lst = new ArrayList<>();
                List<PersonSendNotifyInfo> personSendNotifyInfos = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
                for (int i = 0; i < personSendNotifyInfos.size(); i++) {
                    if (personSendNotifyInfoList.get(position).getId().equals(personSendNotifyInfos.get(i).getParentId())) {
                        lst.add(personSendNotifyInfos.get(i));
                    }
                }
                List<Integer> sizes = new ArrayList<Integer>(lst.size());
                List<Boolean> touchs = new ArrayList<>();
                for (int i = 0; i < lst.size(); i++) {
                    int count = 0;
                    for (int j = 0; j < personSendNotifyInfos.size(); j++) {
                        if (lst.get(i).getId().equals(personSendNotifyInfos.get(j).getParentId())) {
                            count++;
                        }
                    }
                    sizes.add(count);
                    touchs.add(false);
                }
                SelectSendProcessAdapter selectSendNotifyAdapter = new SelectSendProcessAdapter(context, R.layout.item_person_send_process_list, R.layout.item_person_send_process_detail, lst, sizes, touchs, type);
                int adapterCount = selectSendNotifyAdapter.getCount();
                for (int i = 0; i < adapterCount; i++) {
                    View item = selectSendNotifyAdapter.getView(i, null, null);
                    layoutPersonDonvi.addView(item);
                }
            }
            txtNameDonvi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!touchs.get(position)) {
                        txtNameDonvi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
                        layoutPersonDonvi.setVisibility(View.VISIBLE);
                        touchs.set(position, true);
                    } else {
                        txtNameDonvi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                        layoutPersonDonvi.setVisibility(View.GONE);
                        touchs.set(position, false);
                    }
                }
            });
            checkXLChinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkXLChinh.isChecked()) {
                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            View view1 = personSendNotifyChecks.get(i).getView();
                            RadioButton checkXLChinh = (RadioButton) view1.findViewById(R.id.checkXLChinh);
                            CheckBox checkDongXL = (CheckBox) view1.findViewById(R.id.checkDongXL);
                            CheckBox checkXemDB = (CheckBox) view1.findViewById(R.id.checkXemDB);
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                checkDongXL.setChecked(false);
                                //checkDongXL.setEnabled(false);
                                checkXemDB.setChecked(false);
                                //checkXemDB.setEnabled(false);
                            }
                            if (checkXLChinh.isChecked() && !personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                checkXLChinh.setChecked(false);
                                checkDongXL.setEnabled(true);
                                checkXemDB.setEnabled(true);
                            }
                            personSendNotifyChecks.get(i).setView(view1);
                        }
                        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
                        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
                    }
                }
            });
            checkXemDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkXemDB.isEnabled()) {
                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                View view1 = personSendNotifyChecks.get(i).getView();
                                RadioButton checkXLChinh = (RadioButton) view1.findViewById(R.id.checkXLChinh);
                                CheckBox checkXemDBView = (CheckBox) view1.findViewById(R.id.checkXemDB);
                                CheckBox checkDongXL = (CheckBox) view1.findViewById(R.id.checkDongXL);
                                checkXemDBView.setChecked(checkXemDB.isChecked());
                                if (checkXemDB.isChecked()) {
                                    if (checkDongXL.isChecked()) {
                                        checkDongXL.setChecked(false);
                                    }
                                    if (checkXLChinh.isChecked()) {
                                        checkXLChinh.setChecked(false);
                                    }
                                }
                                personSendNotifyChecks.get(i).setView(view1);
                                break;
                            }
                        }
                        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
                        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
                    }
                }
            });
            checkDongXL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkDongXL.isEnabled()) {
                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                View view1 = personSendNotifyChecks.get(i).getView();
                                RadioButton checkXLChinh = (RadioButton) view1.findViewById(R.id.checkXLChinh);
                                CheckBox checkDongXLView = (CheckBox) view1.findViewById(R.id.checkDongXL);
                                CheckBox checkXemDB = (CheckBox) view1.findViewById(R.id.checkXemDB);
                                checkDongXLView.setChecked(checkDongXL.isChecked());
                                if (checkDongXL.isChecked()) {
                                    if (checkXemDB.isChecked()) {
                                        checkXemDB.setChecked(false);
                                    }
                                }
                                if (checkXLChinh.isChecked()) {
                                    checkXLChinh.setChecked(false);
                                }
                                personSendNotifyChecks.get(i).setView(view1);
                                break;
                            }
                        }
                        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
                        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
                    }
                }
            });
        } else {
            view = inflater.inflate(this.resourceDetail, null);
            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            final CheckBox checkDongXL = (CheckBox) view.findViewById(R.id.checkDongXL);
            final CheckBox checkXemDB = (CheckBox) view.findViewById(R.id.checkXemDB);
            final RadioButton checkXLChinh = (RadioButton) view.findViewById(R.id.checkXLChinh);
            txtName.setTypeface(Application.getApp().getTypeface());
            txtName.setText(" " + personSendNotifyInfoList.get(position).getName());
            if (personList != null && personList.size() > 0) {
                for (Person person : personList) {
                    if (personSendNotifyInfoList.get(position).getId().equals(person.getId())) {
                        if (person.isXlc()) {
                            checkXLChinh.setChecked(true);
                            checkDongXL.setChecked(false);
                            checkXemDB.setChecked(false);
                        }
                        if (person.isDxl()) {
                            checkDongXL.setChecked(true);
                            checkXLChinh.setChecked(false);
                            checkXemDB.setChecked(false);
                        }
                        if (person.isXem()) {
                            checkDongXL.setChecked(false);
                            checkXLChinh.setChecked(false);
                            checkXemDB.setChecked(true);
                        }
                    }
                }
            }
            if (personGroupList != null && personGroupList.size() > 0) {
                for (Person person : personGroupList) {
                    if (personSendNotifyInfoList.get(position).getId().equals(person.getId())) {
                        if (person.isXlc()) {
                            checkXLChinh.setChecked(true);
                            checkDongXL.setChecked(false);
                            checkXemDB.setChecked(false);
                        }
                        if (person.isDxl()) {
                            checkDongXL.setChecked(true);
                            checkXLChinh.setChecked(false);
                            checkXemDB.setChecked(false);
                        }
                        if (person.isXem()) {
                            checkDongXL.setChecked(false);
                            checkXLChinh.setChecked(false);
                            checkXemDB.setChecked(true);
                        }
                    }
                }
            }
            checkXLChinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkXLChinh.isChecked()) {
                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            View view1 = personSendNotifyChecks.get(i).getView();
                            RadioButton checkXLChinh = (RadioButton) view1.findViewById(R.id.checkXLChinh);
                            CheckBox checkDongXL = (CheckBox) view1.findViewById(R.id.checkDongXL);
                            CheckBox checkXemDB = (CheckBox) view1.findViewById(R.id.checkXemDB);
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                checkDongXL.setChecked(false);
                                //checkDongXL.setEnabled(false);
                                checkXemDB.setChecked(false);
                                //checkXemDB.setEnabled(false);
                            }
                            if (checkXLChinh.isChecked() && !personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                checkXLChinh.setChecked(false);
                                checkDongXL.setEnabled(true);
                                checkXemDB.setEnabled(true);
                            }
                            personSendNotifyChecks.get(i).setView(view1);
                        }
                        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
                        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
                    }
                }
            });
            checkXemDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkXemDB.isEnabled()) {
                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                View view1 = personSendNotifyChecks.get(i).getView();
                                RadioButton checkXLChinh = (RadioButton) view1.findViewById(R.id.checkXLChinh);
                                CheckBox checkXemDBView = (CheckBox) view1.findViewById(R.id.checkXemDB);
                                CheckBox checkDongXL = (CheckBox) view1.findViewById(R.id.checkDongXL);
                                checkXemDBView.setChecked(checkXemDB.isChecked());
                                if (checkXemDB.isChecked()) {
                                    if (checkDongXL.isChecked()) {
                                        checkDongXL.setChecked(false);
                                    }
                                    if (checkXLChinh.isChecked()) {
                                        checkXLChinh.setChecked(false);
                                    }
                                }
                                personSendNotifyChecks.get(i).setView(view1);
                                break;
                            }
                        }
                        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
                        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
                    }
                }
            });
            checkDongXL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkDongXL.isEnabled()) {
                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                View view1 = personSendNotifyChecks.get(i).getView();
                                RadioButton checkXLChinh = (RadioButton) view1.findViewById(R.id.checkXLChinh);
                                CheckBox checkDongXLView = (CheckBox) view1.findViewById(R.id.checkDongXL);
                                CheckBox checkXemDB = (CheckBox) view1.findViewById(R.id.checkXemDB);
                                checkDongXLView.setChecked(checkDongXL.isChecked());
                                if (checkDongXL.isChecked()) {
                                    if (checkXemDB.isChecked()) {
                                        checkXemDB.setChecked(false);
                                    }
                                    if (checkXLChinh.isChecked()) {
                                        checkXLChinh.setChecked(false);
                                    }
                                }
                                personSendNotifyChecks.get(i).setView(view1);
                                break;
                            }
                        }
                        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
                        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
                    }
                }
            });
        }
        PersonSendNotifyCheck personSendNotifyCheck = new PersonSendNotifyCheck(view, personSendNotifyInfoList.get(position).getId(), personSendNotifyInfoList.get(position).getParentId(),
                                                                    personSendNotifyInfoList.get(position).getName(), null, notParent);
        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
        personSendNotifyChecks.add(personSendNotifyCheck);
        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
        return view;
    }

}

