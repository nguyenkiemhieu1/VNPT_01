package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.builder;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Contact;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ContactInfo;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class ContactBuilder {
    private Context context;

    public ContactBuilder(Context context) {
        this.context = context;
    }

    public List<Contact> convertFromContactInfos(List<ContactInfo> contactInfos) {
        List<Contact> contacts = new ArrayList<Contact>();
        int uid = 0;
        for (ContactInfo contactInfo : contactInfos) {
            Contact contact = new Contact();
            uid++;
            contact.setUid(uid);
            contact.setId(contactInfo.getId() != null && !contactInfo.getId().trim().equals("") ? contactInfo.getId().trim() : null);
            contact.setUserName(contactInfo.getUsername() != null && !contactInfo.getUsername().trim().equals("") ? contactInfo.getUsername().trim() : null);
            contact.setParentId(contactInfo.getParentId() != null && !contactInfo.getParentId().trim().equals("") ? contactInfo.getParentId() : null);
            contact.setPosition(contactInfo.getPosition() != null && !contactInfo.getPosition().trim().equals("") ? contactInfo.getPosition().trim() : null);
            contact.setUnitName(contactInfo.getUnitName() != null && !contactInfo.getUnitName().trim().equals("") ? contactInfo.getUnitName().trim() : null);
            if (contactInfo.getDataInfo() != null && !contactInfo.getDataInfo().trim().equals("")
                    && !contactInfo.getDataInfo().trim().equals("null")) {
                String[] dataArr = contactInfo.getDataInfo().split(";");
                try {
                    contact.setSex(dataArr[0] != null && !dataArr[0].trim().equals("") && !dataArr[0].trim().equals("null") ? dataArr[0] : null);
                    contact.setPhone(dataArr[1] != null && !dataArr[1].trim().equals("") && !dataArr[1].trim().equals("null") ? dataArr[1] : null);
                    contact.setEmail(dataArr[2] != null && !dataArr[2].trim().equals("") && !dataArr[2].trim().equals("null") ? dataArr[2] : null);
                    contact.setAddress(dataArr[3] != null && !dataArr[3].trim().equals("") && !dataArr[3].trim().equals("null") ? dataArr[3] : null);
                    contact.setNation(dataArr[4] != null && !dataArr[4].trim().equals("") && !dataArr[4].trim().equals("null") ? dataArr[4] : null);
                    contact.setReligion(dataArr[5] != null && !dataArr[5].trim().equals("") && !dataArr[5].trim().equals("null") ? dataArr[5] : null);
                    contact.setLevel(dataArr[6] != null && !dataArr[6].trim().equals("") && !dataArr[6].trim().equals("null") ? dataArr[6] : null);
                    contact.setStatus(dataArr[7] != null && !dataArr[7].trim().equals("") && !dataArr[7].trim().equals("null") ? "[" + dataArr[7] + "] " : null);
                    contact.setUserId(dataArr[8] != null && !dataArr[8].trim().equals("") && !dataArr[8].trim().equals("null") ? dataArr[8] : null);
                    contact.setDateBorn(dataArr[9] != null && !dataArr[9].trim().equals("") && !dataArr[9].trim().equals("null") ? dataArr[9] : null);
                    contact.setAvatar(dataArr[10] != null && !dataArr[10].trim().equals("") && !dataArr[10].trim().equals("null") ? dataArr[10] : null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (contact.getStatus() != null && contact.getStatus().contains("0")) {
                    contact.setStatus(contact.getStatus() + context.getString(R.string.STATUS_CONTACT_ACTIVE_LABEL));
                }
                if (contact.getStatus() != null && contact.getStatus().contains("1")) {
                    contact.setStatus(contact.getStatus() + R.string.STATUS_CONTACT_DEACTIVE_LABEL);
                }
            }
            if (contactInfo.getDataInfo() != null && !contactInfo.getDataInfo().trim().equals("")
                    && !contactInfo.getDataInfo().trim().equals("null") && contactInfo.getPosition() != null && !contactInfo.getPosition().trim().equals("")) {
                contact.setIsUser("true");
            } else {
                contact.setIsUser("false");
            }
            contacts.add(contact);
        }
        return contacts;
    }
}
