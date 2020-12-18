package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ContactRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IContactService {
    @GET(ServiceUrl.GET_CONTACT_URL)
    Call<ContactRespone> getContacts();
}