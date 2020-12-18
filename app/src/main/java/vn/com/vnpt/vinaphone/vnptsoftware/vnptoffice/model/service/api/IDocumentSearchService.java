package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentAdvanceSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DocumentSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentAdvanceSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CountDocumentSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentAdvanceSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FieldDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PriorityDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public interface IDocumentSearchService {
    @GET(ServiceUrl.GET_LIST_TYPE_DOC_URL)
    Call<TypeDocumentRespone> getTypes();
    @GET(ServiceUrl.GET_LIST_FIELD_DOC_URL)
    Call<FieldDocumentRespone> getFields();
    @GET(ServiceUrl.GET_LIST_PRIORITY_DOC_URL)
    Call<PriorityDocumentRespone> getPrioritys();
    @POST(ServiceUrl.GET_COUNT_DOC_SEARCH_URL)
    Call<CountDocumentSearchRespone> getCount(@Body DocumentSearchRequest documentSearchRequest);
    @POST(ServiceUrl.GET_DOC_SEARCH_URL)
    Call<DocumentSearchRespone> getRecords(@Body DocumentSearchRequest documentSearchRequest);
    @POST(ServiceUrl.GET_COUNT_DOC_ADVANCE_SEARCH_URL)
    Call<CountDocumentAdvanceSearchRespone> getCountAdvance(@Body DocumentAdvanceSearchRequest documentAdvanceSearchRequest);
    @POST(ServiceUrl.GET_DOC_ADVANCE_SEARCH_URL)
    Call<DocumentAdvanceSearchRespone> getRecordsAdvance(@Body DocumentAdvanceSearchRequest documentAdvanceSearchRequest);
}