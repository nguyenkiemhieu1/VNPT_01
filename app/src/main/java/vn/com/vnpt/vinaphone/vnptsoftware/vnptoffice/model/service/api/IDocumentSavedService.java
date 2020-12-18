package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSavedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FinishDocumentSaveRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSaveDocumentResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

public interface IDocumentSavedService {
    @GET(ServiceUrl.GET_DOCUMENT_SAVED)
    Call<GetListSaveDocumentResponse> getListDocumentSaved();
    @POST(ServiceUrl.POST_DOCUMENT_SAVED)
    Call<FinishDocumentSaveRespone> postFinishDocumentSaved(@Body DocumentSavedRequest documentSavedRequest);
}
