package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonProcessInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonProcessCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonProcessCheck;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class SelectProcessAdapter extends ArrayAdapter<PersonProcessInfo> {
    private Context context;
    private int resource;
    private List<PersonProcessInfo> personProcessInfoList;

    public SelectProcessAdapter(Context context, int resource, List<PersonProcessInfo> personProcessInfoList) {
        super(context, resource, personProcessInfoList);
        this.context = context;
        this.resource = resource;
        this.personProcessInfoList = personProcessInfoList;
        this.notifyDataSetChanged();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        List<Person> personList = new ArrayList<>();
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent != null) {
            personList = listPersonEvent.getPersonProcessList();
        }
        final TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtDonVi = (TextView) view.findViewById(R.id.txtDonVi);
        final RadioButton checkXLChinh = (RadioButton) view.findViewById(R.id.checkXLChinh);
        final CheckBox checkDongXL = (CheckBox) view.findViewById(R.id.checkDongXL);
        txtName.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtDonVi.setTypeface(Application.getApp().getTypeface());
        txtName.setText(personProcessInfoList.get(position).getFullName());
        txtDonVi.setText(personProcessInfoList.get(position).getSecondUnitName());
        if (personList != null && personList.size() > 0) {
            for (Person person : personList) {
                if (personProcessInfoList.get(position).getUserId().equals(person.getId())
                        && personProcessInfoList.get(position).getUnitId() == person.getUnitId()) {
                    if (person.isXlc()) {
                        checkXLChinh.setChecked(true);
                        checkDongXL.setChecked(false);
                        txtName.setTextColor(ContextCompat.getColor(context, R.color.md_red_500));
                    }
                    if (person.isDxl()) {
                        checkDongXL.setChecked(true);
                        txtName.setTextColor(ContextCompat.getColor(context, R.color.md_red_500));
                    }
                    if(person.isXlc() && person.isDxl()){
                        txtName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    }
                }
            }
        }
        checkXLChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                List<Person> personList = listPersonEvent.getPersonProcessList();
                if (personList == null) {
                    personList = new ArrayList<Person>();
                }
                boolean isExisted = containPerson(position, personList);
                if (checkXLChinh.isChecked()) {
                    checkDongXL.setChecked(false);
                    if (!isExisted) {
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).isXlc()) {
                                personList.remove(i);
                                break;
                            }
                        }
                        Person person = new Person(personProcessInfoList.get(position).getUserId(), personProcessInfoList.get(position).getFullName(),
                                personProcessInfoList.get(position).getSecondUnitName(), null, true, false, false, personProcessInfoList.get(position).getUnitId());
                        personList.add(person);
                    } else {
                        int index = -1;
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).getId().equals(personProcessInfoList.get(position).getUserId())
                                    && personList.get(i).getUnitId() == personProcessInfoList.get(position).getUnitId()) {
                                personList.get(i).setXlc(true);
                                personList.get(i).setDxl(false);
                            } else {
                                if (personList.get(i).isXlc()) {
                                    index = i;
                                }
                            }
                        }
                        if (index > -1) {
                            personList.remove(index);
                        }
                    }
                    PersonProcessCheckEvent personProcessCheckEvent = EventBus.getDefault().getStickyEvent(PersonProcessCheckEvent.class);
                    List<PersonProcessCheck> personProcessChecks = personProcessCheckEvent.getPersonProcessChecks();
                    for (int i = 0; i < personProcessChecks.size(); i++) {
                        View view1 = personProcessChecks.get(i).getView();
                        CheckBox checkDongXL = (CheckBox) view1.findViewById(R.id.checkDongXL);
                        RadioButton checkXLChinh = (RadioButton) view1.findViewById(R.id.checkXLChinh);
                        if (personProcessChecks.get(i).getId().equals(personProcessInfoList.get(position).getUserId())
                                && personProcessChecks.get(i).getUnitId() == personProcessInfoList.get(position).getUnitId()) {
                            checkDongXL.setChecked(false);
                        } else {
                            checkXLChinh.setChecked(false);
                            checkDongXL.setEnabled(true);
                        }
                    }
                } else {
                    checkDongXL.setEnabled(true);
                    for (int i = 0; i < personList.size(); i++) {
                        if (personList.get(i).getId().equals(personProcessInfoList.get(position).getUserId())) {
                            personList.remove(i);
                            break;
                        }
                    }
                }
                listPersonEvent.setPersonProcessList(personList);
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        });
        checkDongXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                List<Person> personList = listPersonEvent.getPersonProcessList();
                if (personList == null) {
                    personList = new ArrayList<Person>();
                }
                boolean isExisted = containPerson(position, personList);
                if (checkDongXL.isChecked()) {
                    checkXLChinh.setChecked(false);
                    if (!isExisted) {
                        Person person = new Person(personProcessInfoList.get(position).getUserId(), personProcessInfoList.get(position).getFullName(),
                                personProcessInfoList.get(position).getSecondUnitName(), null, false, true, false, personProcessInfoList.get(position).getUnitId());
                        personList.add(person);
                    } else {
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).getId().equals(personProcessInfoList.get(position).getUserId())) {
                                personList.get(i).setDxl(true);
                                personList.get(i).setXlc(false);
                                break;
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < personList.size(); i++) {
                        if (personList.get(i).getId().equals(personProcessInfoList.get(position).getUserId())) {
                            personList.remove(i);
                            break;
                        }
                    }
                }
                listPersonEvent.setPersonProcessList(personList);
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        });
        PersonProcessCheck personProcessCheck = new PersonProcessCheck(view, personProcessInfoList.get(position).getUserId(), personProcessInfoList.get(position).getUnitId());
        PersonProcessCheckEvent personProcessCheckEvent = EventBus.getDefault().getStickyEvent(PersonProcessCheckEvent.class);
        List<PersonProcessCheck> personProcessChecks = personProcessCheckEvent.getPersonProcessChecks();
        personProcessChecks.add(personProcessCheck);
        personProcessCheckEvent.setPersonProcessChecks(personProcessChecks);
        EventBus.getDefault().postSticky(personProcessCheckEvent);
        return view;
    }

    private boolean containPerson(int position, List<Person> personList) {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(personProcessInfoList.get(position).getUserId())
                    && personList.get(i).getUnitId() == personProcessInfoList.get(position).getUnitId()) {
                return true;
            }
        }
        return false;
    }
}

