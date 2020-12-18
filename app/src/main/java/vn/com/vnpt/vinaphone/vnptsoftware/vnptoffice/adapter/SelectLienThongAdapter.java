package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LienThongInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonProcessCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonProcessCheck;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class SelectLienThongAdapter extends ArrayAdapter<LienThongInfo> {
    private Context context;
    private int resource;
    private List<LienThongInfo> personProcessInfoList;

    public SelectLienThongAdapter(Context context, int resource, List<LienThongInfo> personProcessInfoList) {
        super(context, resource, personProcessInfoList);
        this.context = context;
        this.resource = resource;
        this.personProcessInfoList = personProcessInfoList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        List<Person> personList = new ArrayList<>();
        List<Person> personGroupList = new ArrayList<>();
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent != null) {
            personList = listPersonEvent.getPersonLienThongList();
            personGroupList = listPersonEvent.getPersonGroupList();
            if (personList == null) {
                personList = new ArrayList<Person>();
            }
            if (personGroupList == null) {
                personGroupList = new ArrayList<Person>();
            }
        }
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        final RadioButton checkXLChinh = (RadioButton) view.findViewById(R.id.checkXLChinh);
        final CheckBox checkDongXL = (CheckBox) view.findViewById(R.id.checkDongXL);
        final CheckBox checkXemDB = (CheckBox) view.findViewById(R.id.checkXemDB);
        txtName.setText(personProcessInfoList.get(position).getName());
        if (personList != null && personList.size() > 0) {
            for (Person person : personList) {
                if (personProcessInfoList.get(position).getId().equals(person.getId())) {
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
            for (Person person : personGroupList) {
                if (personProcessInfoList.get(position).getId().equals(person.getId())) {
                    if (person.isXlc()) {
                        checkXLChinh.setChecked(true);
                        checkDongXL.setChecked(false);
                        //checkDongXL.setEnabled(false);
                        checkXemDB.setChecked(false);
                        //checkXemDB.setEnabled(false);
                        boolean isExisted = containPerson(position, personList);
                        if (!isExisted) {
                            for (int i = 0; i < personList.size(); i++) {
                                if (personList.get(i).isXlc()) {
                                    personList.remove(i);
                                    break;
                                }
                            }
                            Person person1 = new Person(personProcessInfoList.get(position).getId(), personProcessInfoList.get(position).getName(),
                                    personProcessInfoList.get(position).getName(), null, true, false, false, 0);
                            personList.add(person1);
                        } else {
                            int index = -1;
                            for (int i = 0; i < personList.size(); i++) {
                                if (personList.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                                    personList.get(i).setXlc(true);
                                    personList.get(i).setDxl(false);
                                    personList.get(i).setXem(false);
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
                        listPersonEvent.setPersonLienThongList(personList);
                        EventBus.getDefault().postSticky(listPersonEvent);
                    }
                    if (person.isDxl()) {
                        checkDongXL.setChecked(true);
                        checkXemDB.setChecked(false);
                        checkXLChinh.setChecked(false);
                        boolean isExisted = containPerson(position, personList);
                        if (!isExisted) {
//                            for (int i = 0; i < personList.size(); i++) {
//                                if (personList.get(i).isXlc()) {
//                                    personList.remove(i);
//                                    break;
//                                }
//                            }
                            Person person1 = new Person(personProcessInfoList.get(position).getId(), personProcessInfoList.get(position).getName(),
                                    personProcessInfoList.get(position).getName(), null, false, true, false, 0);
                            personList.add(person1);
                        } else {
 //                           int index = -1;
                            for (int i = 0; i < personList.size(); i++) {
                                if (personList.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                                    personList.get(i).setXlc(false);
                                    personList.get(i).setDxl(true);
                                    personList.get(i).setXem(false);
                                }
//                                else {
//                                    if (personList.get(i).isXlc()) {
//                                        index = i;
//                                    }
//                                }
                            }
//                            if (index > -1) {
//                                personList.remove(index);
//                            }
                        }
                        listPersonEvent.setPersonLienThongList(personList);
                        EventBus.getDefault().postSticky(listPersonEvent);
                    }
                    if (person.isXem()) {
                        checkDongXL.setChecked(false);
                        checkXemDB.setChecked(true);
                        checkXLChinh.setChecked(false);
                        boolean isExisted = containPerson(position, personList);
                        if (!isExisted) {
//                            for (int i = 0; i < personList.size(); i++) {
//                                if (personList.get(i).isXlc()) {
//                                    personList.remove(i);
//                                    break;
//                                }
//                            }
                            Person person1 = new Person(personProcessInfoList.get(position).getId(), personProcessInfoList.get(position).getName(),
                                    personProcessInfoList.get(position).getName(), null, false, false, true, 0);
                            personList.add(person1);
                        } else {
  //                          int index = -1;
                            for (int i = 0; i < personList.size(); i++) {
                                if (personList.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                                    personList.get(i).setXlc(false);
                                    personList.get(i).setDxl(false);
                                    personList.get(i).setXem(true);
                                }
//                                else {
//                                    if (personList.get(i).isXlc()) {
//                                        index = i;
//                                    }
//                                }
                            }
//                            if (index > -1) {
//                                personList.remove(index);
//                            }
                        }
                        listPersonEvent.setPersonLienThongList(personList);
                        EventBus.getDefault().postSticky(listPersonEvent);
                    }
                }
            }
        }

        checkXLChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                List<Person> personList = listPersonEvent.getPersonLienThongList();
                if (personList == null) {
                    personList = new ArrayList<Person>();
                }
                boolean isExisted = containPerson(position, personList);
                if (checkXLChinh.isChecked()) {
                    checkDongXL.setChecked(false);
                    //checkDongXL.setEnabled(false);
                    checkXemDB.setChecked(false);
                    //checkXemDB.setEnabled(false);
                    if (!isExisted) {
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).isXlc()) {
                                personList.remove(i);
                                break;
                            }
                        }
                        Person person = new Person(personProcessInfoList.get(position).getId(), personProcessInfoList.get(position).getName(),
                                personProcessInfoList.get(position).getName(), null, true, false, false, 0);
                        personList.add(person);
                    } else {
                        int index = -1;
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                                personList.get(i).setXlc(true);
                                personList.get(i).setDxl(false);
                                personList.get(i).setXem(false);
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
                        CheckBox checkXemDB = (CheckBox) view1.findViewById(R.id.checkXemDB);
                        RadioButton checkXLChinh = (RadioButton) view1.findViewById(R.id.checkXLChinh);
                        if (personProcessChecks.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                            //checkDongXL.setEnabled(false);
                            checkDongXL.setChecked(false);
                            //checkXemDB.setEnabled(false);
                            checkXemDB.setChecked(false);
                        } else {
                            checkXLChinh.setChecked(false);
                            checkDongXL.setEnabled(true);
                            checkXemDB.setEnabled(true);
                        }
                    }
                    personProcessCheckEvent.setPersonProcessChecks(personProcessChecks);
                    EventBus.getDefault().postSticky(personProcessCheckEvent);
                } else {
                    checkDongXL.setEnabled(true);
                    checkXemDB.setEnabled(true);
                    for (int i = 0; i < personList.size(); i++) {
                        if (personList.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                            personList.remove(i);
                            break;
                        }
                    }
                }
                listPersonEvent.setPersonLienThongList(personList);
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        });
        checkDongXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                List<Person> personList = listPersonEvent.getPersonLienThongList();
                if (personList == null) {
                    personList = new ArrayList<Person>();
                }
                boolean isExisted = containPerson(position, personList);
                if (checkDongXL.isChecked()) {
                    if (!isExisted) {
                        Person person = new Person(personProcessInfoList.get(position).getId(), personProcessInfoList.get(position).getName(),
                                personProcessInfoList.get(position).getName(), null, false, true, false, 0);
                        personList.add(person);
                    } else {
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                                personList.get(i).setDxl(true);
                                personList.get(i).setXlc(false);
                                personList.get(i).setXem(false);
                                PersonProcessCheckEvent personProcessCheckEvent = EventBus.getDefault().getStickyEvent(PersonProcessCheckEvent.class);
                                List<PersonProcessCheck> personProcessChecks = personProcessCheckEvent.getPersonProcessChecks();
                                for (int j = 0; j < personProcessChecks.size(); j++) {
                                    View view1 = personProcessChecks.get(j).getView();
                                    RadioButton checkXLChinh = (RadioButton) view1.findViewById(R.id.checkXLChinh);
                                    CheckBox checkDongXL = (CheckBox) view1.findViewById(R.id.checkDongXL);
                                    CheckBox checkXemDB = (CheckBox) view1.findViewById(R.id.checkXemDB);
                                    if (personProcessChecks.get(j).getId().equals(personProcessInfoList.get(position).getId())) {
                                        checkDongXL.setChecked(true);
                                        checkXemDB.setChecked(false);
                                        checkXLChinh.setChecked(false);
                                        break;
                                    }
                                }
                                personProcessCheckEvent.setPersonProcessChecks(personProcessChecks);
                                EventBus.getDefault().postSticky(personProcessCheckEvent);
                                break;
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < personList.size(); i++) {
                        if (personList.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                            personList.remove(i);
                            break;
                        }
                    }
                }
                listPersonEvent.setPersonLienThongList(personList);
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        });
        checkXemDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                List<Person> personList = listPersonEvent.getPersonLienThongList();
                if (personList == null) {
                    personList = new ArrayList<Person>();
                }
                boolean isExisted = containPerson(position, personList);
                if (checkXemDB.isChecked()) {
                    if (!isExisted) {
                        Person person = new Person(personProcessInfoList.get(position).getId(), personProcessInfoList.get(position).getName(),
                                personProcessInfoList.get(position).getName(), null, false, false, true, 0);
                        personList.add(person);
                    } else {
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                                personList.get(i).setDxl(false);
                                personList.get(i).setXlc(false);
                                personList.get(i).setXem(true);
                                PersonProcessCheckEvent personProcessCheckEvent = EventBus.getDefault().getStickyEvent(PersonProcessCheckEvent.class);
                                List<PersonProcessCheck> personProcessChecks = personProcessCheckEvent.getPersonProcessChecks();
                                for (int j = 0; j < personProcessChecks.size(); j++) {
                                    View view1 = personProcessChecks.get(j).getView();
                                    RadioButton checkXLChinh = (RadioButton) view1.findViewById(R.id.checkXLChinh);
                                    CheckBox checkDongXL = (CheckBox) view1.findViewById(R.id.checkDongXL);
                                    CheckBox checkXemDB = (CheckBox) view1.findViewById(R.id.checkXemDB);
                                    if (personProcessChecks.get(j).getId().equals(personProcessInfoList.get(position).getId())) {
                                        checkDongXL.setChecked(false);
                                        checkXemDB.setChecked(true);
                                        checkXLChinh.setChecked(false);
                                        break;
                                    }
                                }
                                personProcessCheckEvent.setPersonProcessChecks(personProcessChecks);
                                EventBus.getDefault().postSticky(personProcessCheckEvent);
                                break;
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < personList.size(); i++) {
                        if (personList.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                            personList.remove(i);
                            break;
                        }
                    }
                }
                listPersonEvent.setPersonLienThongList(personList);
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        });
        PersonProcessCheck personProcessCheck = new PersonProcessCheck(view, personProcessInfoList.get(position).getId(), 0);
        PersonProcessCheckEvent personProcessCheckEvent = EventBus.getDefault().getStickyEvent(PersonProcessCheckEvent.class);
        List<PersonProcessCheck> personProcessChecks = personProcessCheckEvent.getPersonProcessChecks();
        personProcessChecks.add(personProcessCheck);
        personProcessCheckEvent.setPersonProcessChecks(personProcessChecks);
        EventBus.getDefault().postSticky(personProcessCheckEvent);
        return view;
    }

    private boolean containPerson(int position, List<Person> personList) {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(personProcessInfoList.get(position).getId())) {
                return true;
            }
        }
        return false;
    }

}

