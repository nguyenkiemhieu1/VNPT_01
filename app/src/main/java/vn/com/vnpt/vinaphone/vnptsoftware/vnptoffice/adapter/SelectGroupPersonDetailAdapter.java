package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonGroupChangeDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TypePersonEvent;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class SelectGroupPersonDetailAdapter extends RecyclerView.Adapter<SelectGroupPersonDetailAdapter.MyViewHolder> {

    private Context context;
    private List<PersonGroupChangeDocInfo> personGroupChangeDocInfoList;
    private boolean checkPHAll, checkXemDBAll;
    private CheckBox checkPHParent, checkXemDBParent;
    private String type;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public RadioButton checkXLChinh;
        public CheckBox checkDongXL, checkXemDB;

        public MyViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtName);
            checkXLChinh = (RadioButton) view.findViewById(R.id.checkXLChinh);
            checkDongXL = (CheckBox) view.findViewById(R.id.checkDongXL);
            checkXemDB = (CheckBox) view.findViewById(R.id.checkXemDB);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_person_send_process_detail, parent, false);
        return new MyViewHolder(itemView);
    }

    public SelectGroupPersonDetailAdapter(Context context, List<PersonGroupChangeDocInfo> personGroupChangeDocInfoList, boolean checkPHAll, boolean checkXemDBAll,
                                          CheckBox checkPHParent, CheckBox checkXemDBParent, String type) {
        this.context = context;
        this.personGroupChangeDocInfoList = personGroupChangeDocInfoList;
        this.checkPHAll = checkPHAll;
        this.checkXemDBAll = checkXemDBAll;
        this.checkPHParent = checkPHParent;
        this.checkXemDBParent = checkXemDBParent;
        this.type = type;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtName.setText(personGroupChangeDocInfoList.get(position).getName());
        if (checkPHAll) {
            holder.checkDongXL.setChecked(true);
            List<Person> personList = new ArrayList<>();
            //TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
            ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
            if (listPersonEvent != null) {
                personList = listPersonEvent.getPersonGroupList();
            }
            if (personList == null) {
                personList = new ArrayList<Person>();
            }
            for (int i = 0; i < personList.size(); i++) {
                if (personList.get(i).getId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                    if (personList.get(i).isXlc()) {
                        holder.checkXLChinh.setChecked(false);
                    }
                    if (personList.get(i).isXem()) {
                        holder.checkXemDB.setChecked(false);
                    }
                }
            }
            boolean isExisted = containPerson(position, personList);
            if (!isExisted) {
                Person person = new Person(personGroupChangeDocInfoList.get(position).getId(), personGroupChangeDocInfoList.get(position).getName(),
                        personGroupChangeDocInfoList.get(position).getName(), null, false, true, false, 0);
                personList.add(person);
            } else {
                for (int i = 0; i < personList.size(); i++) {
                    if (personList.get(i).getId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                        personList.get(i).setDxl(true);
                        personList.get(i).setXlc(false);
                        personList.get(i).setXem(false);
                        break;
                    }
                }
            }
            listPersonEvent.setPersonGroupList(personList);
            EventBus.getDefault().postSticky(listPersonEvent);
        } else {
            holder.checkDongXL.setChecked(false);
        }
        if (checkXemDBAll) {
            holder.checkXemDB.setChecked(true);
            List<Person> personList = new ArrayList<>();
            //TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
            ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
            if (listPersonEvent != null) {
                personList = listPersonEvent.getPersonGroupList();
            }
            if (personList == null) {
                personList = new ArrayList<Person>();
            }
            for (int i = 0; i < personList.size(); i++) {
                if (personList.get(i).getId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                    if (personList.get(i).isXlc()) {
                        holder.checkXLChinh.setChecked(false);
                    }
                    if (personList.get(i).isDxl()) {
                        holder.checkDongXL.setChecked(false);
                    }
                }
            }
            boolean isExisted = containPerson(position, personList);
            if (!isExisted) {
                Person person = new Person(personGroupChangeDocInfoList.get(position).getId(), personGroupChangeDocInfoList.get(position).getName(),
                        personGroupChangeDocInfoList.get(position).getName(), null, false, false, true, 0);
                personList.add(person);
            } else {
                for (int i = 0; i < personList.size(); i++) {
                    if (personList.get(i).getId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                        personList.get(i).setDxl(false);
                        personList.get(i).setXlc(false);
                        personList.get(i).setXem(true);
                        break;
                    }
                }
            }
            listPersonEvent.setPersonGroupList(personList);
            EventBus.getDefault().postSticky(listPersonEvent);
        } else {
            holder.checkXemDB.setChecked(false);
        }
        if (type.equals("2")) {
            holder.checkXLChinh.setVisibility(View.GONE);
            holder.checkDongXL.setVisibility(View.GONE);
        }
        holder.checkXLChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Person> personList = new ArrayList<>();
                List<Person> personPreList = new ArrayList<>();
                List<Person> personPreLTList = new ArrayList<>();
                //TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
                if (listPersonEvent != null) {
                    personList = listPersonEvent.getPersonGroupList();
                    switch (typePersonEvent.getType()) {
                        case Constants.TYPE_SEND_PERSON_PROCESS:
                            personPreList = listPersonEvent.getPersonProcessList();
                            personPreLTList = listPersonEvent.getPersonLienThongList();
                            break;
                        case Constants.TYPE_PERSON_DIRECT:
                            personPreList = listPersonEvent.getPersonDirectList();
                            personPreLTList = listPersonEvent.getPersonLienThongList();
                            break;
                        case Constants.TYPE_SEND_VIEW:
                            personPreList = listPersonEvent.getPersonNotifyList();
                            break;
                    }
                }
                if (personList == null) {
                    personList = new ArrayList<Person>();
                }
                if (personPreList == null) {
                    personPreList = new ArrayList<Person>();
                }
                if (personPreLTList == null) {
                    personPreLTList = new ArrayList<Person>();
                }
                for (int i = 0; i < personPreList.size(); i++) {
                    if (personPreList.get(i).getId() != personGroupChangeDocInfoList.get(position).getId() && personPreList.get(i).isXlc()
                            && (type.equals("2") || type.equals("0"))) {
                        personPreList.remove(i);
                        break;
                    }
                }
                for (int i = 0; i < personPreLTList.size(); i++) {
                    if (personPreLTList.get(i).getId() != personGroupChangeDocInfoList.get(position).getId() && personPreLTList.get(i).isXlc()
                            && type.equals("1")) {
                        personPreLTList.remove(i);
                        break;
                    }
                }
                switch (typePersonEvent.getType()) {
                    case Constants.TYPE_SEND_PERSON_PROCESS:
                        listPersonEvent.setPersonProcessList(personPreList);
                        listPersonEvent.setPersonLienThongList(personPreLTList);
                        break;
                    case Constants.TYPE_PERSON_DIRECT:
                        listPersonEvent.setPersonDirectList(personPreList);
                        listPersonEvent.setPersonLienThongList(personPreLTList);
                        break;
                    case Constants.TYPE_SEND_VIEW:
                        listPersonEvent.setPersonNotifyList(personPreList);
                        break;
                }
                boolean isExisted = containPerson(position, personList);
                    holder.checkDongXL.setChecked(false);
                    holder.checkXemDB.setChecked(false);
                    holder.checkXLChinh.setChecked(true);

                    // doi mau ten khi duoc click
                    holder.txtName.setTextColor(ContextCompat.getColor(context, R.color.md_red_500));
                    if (!isExisted) {
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).isXlc()) {
                                personList.remove(i);
                                break;
                            }
                        }
                        Person person = new Person(personGroupChangeDocInfoList.get(position).getId(), personGroupChangeDocInfoList.get(position).getName(),
                                personGroupChangeDocInfoList.get(position).getName(), null, true, false, false, 0);
                        personList.add(person);
                    } else {
                        int index = -1;
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).getId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                                personList.get(i).setXlc(true);
                                personList.get(i).setDxl(false);
                                personList.get(i).setXem(false);
                                if (checkPHParent.isChecked()) {
                                    checkPHParent.setChecked(false);
                                }
                                if (checkXemDBParent.isChecked()) {
                                    checkXemDBParent.setChecked(false);
                                }
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
                    MyViewHolder myViewHolder = EventBus.getDefault().getStickyEvent(MyViewHolder.class);
                    String id = EventBus.getDefault().getStickyEvent(String.class);
                    if (myViewHolder != null && id != null && !id.equals("")) {
                        if (myViewHolder.checkXLChinh.isChecked() && !id.equals(personGroupChangeDocInfoList.get(position).getId())) {
                            myViewHolder.checkXLChinh.setChecked(false);
                            myViewHolder.checkXemDB.setEnabled(true);
                            myViewHolder.checkDongXL.setEnabled(true);
                        }
                    }
                    EventBus.getDefault().postSticky(holder);
                    EventBus.getDefault().postSticky(personGroupChangeDocInfoList.get(position).getId());
                listPersonEvent.setPersonGroupList(personList);
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        });
        holder.checkDongXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.checkDongXL.isChecked()) {
                    if (checkPHParent.isChecked()) {
                        checkPHParent.setChecked(false);
                    }
                } else {
                    if (holder.checkXemDB.isChecked()) {
                        holder.checkXemDB.setChecked(false);
                    }
                }
                List<Person> personList = new ArrayList<>();
                //TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                if (listPersonEvent != null) {
                    personList = listPersonEvent.getPersonGroupList();
                }
                if (personList == null) {
                    personList = new ArrayList<Person>();
                }
                boolean isExisted = containPerson(position, personList);
                if (holder.checkDongXL.isChecked()) {
                    if (!isExisted) {
                        Person person = new Person(personGroupChangeDocInfoList.get(position).getId(), personGroupChangeDocInfoList.get(position).getName(),
                                personGroupChangeDocInfoList.get(position).getName(), null, false, true, false, 0);
                        personList.add(person);
                    } else {
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).getId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                                personList.get(i).setDxl(true);
                                personList.get(i).setXlc(false);
                                personList.get(i).setXem(false);
                                if (checkPHParent.isChecked()) {
                                    checkPHParent.setChecked(false);
                                }
                                break;
                            }
                        }
                    }
                    holder.checkXLChinh.setChecked(false);
                    holder.checkXemDB.setChecked(false);
                } else {
                    for (int i = 0; i < personList.size(); i++) {
                        if (personList.get(i).getId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                            personList.remove(i);
                            break;
                        }
                    }
                }
                listPersonEvent.setPersonGroupList(personList);
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        });
        holder.checkXemDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.checkXemDB.isChecked()) {
                    if (checkXemDBParent.isChecked()) {
                        checkXemDBParent.setChecked(false);
                    }
                } else {
                    if (holder.checkDongXL.isChecked()) {
                        holder.checkDongXL.setChecked(false);
                    }
                }
                List<Person> personList = new ArrayList<>();
                //TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                if (listPersonEvent != null) {
                    personList = listPersonEvent.getPersonGroupList();
                }
                if (personList == null) {
                    personList = new ArrayList<Person>();
                }
                boolean isExisted = containPerson(position, personList);
                if (holder.checkXemDB.isChecked()) {
                    if (!isExisted) {
                        Person person = new Person(personGroupChangeDocInfoList.get(position).getId(), personGroupChangeDocInfoList.get(position).getName(),
                                personGroupChangeDocInfoList.get(position).getName(), null, false, false, true, 0);
                        personList.add(person);
                    } else {
                        for (int i = 0; i < personList.size(); i++) {
                            if (personList.get(i).getId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                                personList.get(i).setDxl(false);
                                personList.get(i).setXlc(false);
                                personList.get(i).setXem(true);
                                if (checkXemDBParent.isChecked()) {
                                    checkXemDBParent.setChecked(false);
                                }
                                break;
                            }
                        }
                    }
                    holder.checkXLChinh.setChecked(false);
                    holder.checkDongXL.setChecked(false);
                } else {
                    for (int i = 0; i < personList.size(); i++) {
                        if (personList.get(i).getId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                            personList.remove(i);
                            break;
                        }
                    }
                }
                listPersonEvent.setPersonGroupList(personList);
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personGroupChangeDocInfoList != null && personGroupChangeDocInfoList.size() > 0 ? personGroupChangeDocInfoList.size() : 0;
    }

    private boolean containPerson(int position, List<Person> personList) {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                return true;
            }
        }
        return false;
    }

}
