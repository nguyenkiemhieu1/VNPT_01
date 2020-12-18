package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.RemoveSelectPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.PersonSendDoc;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class PersonSendDocAdapter extends ArrayAdapter<PersonSendDoc> {
    private Context context;
    private int resource;
    private List<PersonSendDoc> personSendDocList;
    private List<Person> listPersonProgress;
    private List<Person> listPersonDirect;
    private List<Person> listPersonNotify;
    private List<Person> listPersonLienthong;
    private ArrayList<Person> listUnseclectPersonProgress;
    private ArrayList<Person> listUnseclectPersonDirect;
    private ArrayList<Person> listUnseclectPersonNotify;
    private ArrayList<Person> listUnseclectPersonLienthong;

    public PersonSendDocAdapter(Context context, int resource, List<PersonSendDoc> personSendDocList) {
        super(context, resource, personSendDocList);
        this.context = context;
        this.resource = resource;
        this.personSendDocList = personSendDocList;
    }



    public PersonSendDocAdapter(Context context, int resource, List<PersonSendDoc> personSendDocList, List<Person> listPersonProgress, List<Person> listPersonDirect, List<Person> listPersonNotify, List<Person> listPersonLienthong, ArrayList<Person> listUnseclectPersonProgress,ArrayList<Person> listUnseclectPersonDirect,ArrayList<Person> listUnseclectPersonNotify, ArrayList<Person> listUnseclectPersonLienthong) {
        super(context, resource, personSendDocList);
        this.context = context;
        this.resource = resource;
        this.personSendDocList = personSendDocList;
        this.listPersonProgress = listPersonProgress;
        this.listPersonDirect = listPersonDirect;
        this.listPersonNotify = listPersonNotify;
        this.listPersonLienthong = listPersonLienthong;
        this.listUnseclectPersonProgress = listUnseclectPersonProgress;
        this.listUnseclectPersonDirect = listUnseclectPersonDirect;
        this.listUnseclectPersonNotify = listUnseclectPersonNotify;
        this.listUnseclectPersonLienthong = listUnseclectPersonLienthong;
    }



    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        final PersonSendDoc personSendDoc = personSendDocList.get(position);
        final LinearLayout itemPersons = (LinearLayout) view.findViewById(R.id.itemPersons);
        final TextView txtName = (TextView) view.findViewById(R.id.txtName);
        final ImageView btnRemove = (ImageView) view.findViewById(R.id.btnRemove);
        txtName.setText(personSendDoc.getName());
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                if (personSendDoc.getType().equals("LIENTHONG_LIST")) {
                    for (Person person : listPersonLienthong) {
                        if (person.getId().equals(personSendDoc.getId())) {
                            listUnseclectPersonLienthong.add(person);
                            listPersonLienthong.remove(person);
                            break;
                        }
                    }
                }
                if (personSendDoc.getType().equals("PROCESS_LIST")) {
                    for (Person person : listPersonProgress) {
                        if (person.getId().equals(personSendDoc.getId())) {
                            listUnseclectPersonProgress.add(person);
                            listPersonProgress.remove(person);
                            break;
                        }
                    }
                }
                if (personSendDoc.getType().equals("RIDRECT_LIST")) {
                    for (Person person : listPersonDirect) {
                        if (person.getId().equals(personSendDoc.getId())) {
                            listUnseclectPersonDirect.add(person);
                            listPersonDirect.remove(person);
                            break;
                        }
                    }
                }
                if (personSendDoc.getType().equals("NOTIFY_LIST")) {
                    List<Person> personList = listPersonEvent.getPersonNotifyList();
                    for (Person person : listPersonNotify) {
                        if (person.getId().equals(personSendDoc.getId())) {
                            listUnseclectPersonNotify.add(person);
                            listPersonNotify.remove(person);
                            break;
                        }
                    }
                }
                itemPersons.setVisibility(View.GONE);
            }
        });
        return view;
    }
}
