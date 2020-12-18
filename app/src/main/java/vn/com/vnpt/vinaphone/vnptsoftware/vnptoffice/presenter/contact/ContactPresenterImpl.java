package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.contact;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.contact.ContactDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ContactInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ContactRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.syncevent.IContactSync;

import static vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application.resources;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class ContactPresenterImpl implements IContactPresenter, ICallFinishedListener {
    public IContactSync contactSync;
    public ContactDao contactDao;

    public ContactPresenterImpl(IContactSync contactSync) {
        this.contactSync = contactSync;
        this.contactDao = new ContactDao();
    }

    @Override
    public void getContacts() {
        if (contactSync != null) {
            contactSync.showProgressSync();
            contactDao.onSendGetContactsDao(this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        ContactRespone contactRespone = (ContactRespone) object;
        contactSync.hideProgressSync();
        if (contactRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            contactSync.onSuccessSync(ConvertUtils.fromJSONList(contactRespone.getData(), ContactInfo.class));
        } else {
            contactSync.onErrorSync(new APIError(0,  resources.getString(R.string.str_ERROR_DIALOG)));
        }
    }

    @Override
    public void onCallError(Object object) {
        contactSync.onErrorSync((APIError) object);
        contactSync.hideProgressSync();
    }

}
