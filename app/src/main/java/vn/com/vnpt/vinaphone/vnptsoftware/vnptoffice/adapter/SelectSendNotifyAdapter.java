package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonSendNotifyCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.PersonSendNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonSendNotifyCheck;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class SelectSendNotifyAdapter extends ArrayAdapter<PersonSendNotifyInfo> {
    private Context context;
    private int resource;
    private int resourceDetail;
    private List<PersonSendNotifyInfo> personSendNotifyInfoList;
    private List<Boolean> touchs;
    private List<Integer> sizes;

    public SelectSendNotifyAdapter(Context context, int resource, int resourceDetail, List<PersonSendNotifyInfo> personSendNotifyInfoList, List<Integer> sizes, List<Boolean> touchs) {
        super(context, resource, personSendNotifyInfoList);
        this.context = context;
        this.resource = resource;
        this.resourceDetail = resourceDetail;
        this.personSendNotifyInfoList = personSendNotifyInfoList;
        this.sizes = sizes;
        this.touchs = touchs;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        List<Person> personList = new ArrayList<>();
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent != null) {
            personList = listPersonEvent.getPersonSendList();
            if (personList == null || personList.size() > 0) {
                personList = listPersonEvent.getPersonNotifyList();
            }
            if (personList == null || personList.size() > 0) {
                personList = listPersonEvent.getPersonDirectList();
            }
            if (personList == null || personList.size() > 0) {
                personList = listPersonEvent.getPersonSendList();
            }
        }
        boolean notParent = true;
        if (sizes.get(position) > 0) {
            view = inflater.inflate(this.resource, null);
            notParent = false;
            final TextView txtNameDonvi = (TextView) view.findViewById(R.id.txtNameDonvi);
            final CheckBox checkXLChinh = (CheckBox) view.findViewById(R.id.checkXLChinh);
            txtNameDonvi.setTypeface(Application.getApp().getTypeface());
            txtNameDonvi.setText(personSendNotifyInfoList.get(position).getName());
            if (personList != null && personList.size() > 0) {
                for (Person person : personList) {
                    if (personSendNotifyInfoList.get(position).getId().equals(person.getId())) {
                        if (person.isXlc()) {
                            checkXLChinh.setChecked(true);
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
                SelectSendNotifyAdapter selectSendNotifyAdapter = new SelectSendNotifyAdapter(context, R.layout.item_person_send_notify_list, R.layout.item_person_send_notify_detail, lst, sizes, touchs);
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
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                View view1 = personSendNotifyChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkXLChinh);
                                check.setChecked(true);
                                personSendNotifyChecks.get(i).setView(view1);
                                break;
                            }
                        }
                        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
                        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
                    } else {
                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                View view1 = personSendNotifyChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkXLChinh);
                                check.setChecked(false);
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
            final CheckBox checkXLChinh = (CheckBox) view.findViewById(R.id.checkXLChinh);
            txtName.setTypeface(Application.getApp().getTypeface());
            txtName.setText(personSendNotifyInfoList.get(position).getName());
            if (personList != null && personList.size() > 0) {
                for (Person person : personList) {
                    if (personSendNotifyInfoList.get(position).getId().equals(person.getId())) {
                        if (person.isXlc()) {
                            checkXLChinh.setChecked(true);
                        }
                    }
                }
            }
            checkXLChinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkXLChinh.isChecked()) {
                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                View view1 = personSendNotifyChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkXLChinh);
                                check.setChecked(false);
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

