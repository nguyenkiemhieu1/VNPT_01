package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.CheckVersionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ExceptionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.CheckVersionResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ExceptionResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IExceptionErrorService {
    @POST(ServiceUrl.EXCEPTION_URL)
    Call<ExceptionResponse> exceptionError(@HeaderMap Map<String, String> headers, @Body ExceptionRequest exceptionRequest);
}