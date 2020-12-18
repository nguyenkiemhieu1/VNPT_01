package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.changedocument;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.NumberCountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SearchPersonRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FormDataResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.JobPositionRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LienThongRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonGroupChangeDocRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonListRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonOrUnitExpectedResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.TypeChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UnitRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UploadResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IChangeDocumentService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 8/23/2017.
 */

public class ChangeDocumentDao extends BaseDao implements IChangeDocumentDao {
    private IChangeDocumentService changeDocumentService;

    @Override
    public void onSendGetTypeChangeDocumentDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<TypeChangeDocumentRespone> call = changeDocumentService.getTypeChange(typeChangeDocumentRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onSendGetTypeChangeDocumentViewFilesDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<TypeChangeDocumentRespone> call = changeDocumentService.getTypeChangeViewFiles(typeChangeDocumentRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));

    }

    @Override
    public void onGetPersonProcessDao(ListProcessRequest listProcessRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonListRespone> call = changeDocumentService.getPersonProcess(listProcessRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetPersonOrUnitExpectedDao(int docId, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonOrUnitExpectedResponse> call = changeDocumentService.getPersonOrUnitExpected(docId);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetPersonSendDao(SearchPersonRequest searchPersonRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonListRespone> call = changeDocumentService.getPersonSend(searchPersonRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetPersonNotifyDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonListRespone> call = changeDocumentService.getPersonNotify();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onChangeSendDao(ChangeDocumentSendRequest changeDocumentSendRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = changeDocumentService.changeSend(changeDocumentSendRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onChangeReceiveDao(ChangeDocumentReceiveRequest changeDocumentReceiveRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);

        Call<ChangeDocumentRespone> call;
        if (changeDocumentReceiveRequest.getDocId().contains("[")) {
            changeDocumentReceiveRequest.setDocId(convertStringListToString(changeDocumentReceiveRequest.getDocId()));
            call = changeDocumentService.sendDocumentComeSame(changeDocumentReceiveRequest);
        } else {
            call = changeDocumentService.changeReceive(changeDocumentReceiveRequest);
        }
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onChangeProcessDao(ChangeProcessRequest changeProcessRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call;
        if (changeProcessRequest.getDocId().contains("[")) {
            changeProcessRequest.setDocId(convertStringListToString(changeProcessRequest.getDocId()));
            call = changeDocumentService.sendProcessDocumentSame(changeProcessRequest);
        } else {
            call = changeDocumentService.changeProcess(changeProcessRequest);
        }
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onChangeNotifyDao(ChangeNotifyRequest changeNotifyRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call;
        if (changeNotifyRequest.getDocId().contains("[")) {
            changeNotifyRequest.setDocId(convertStringListToString(changeNotifyRequest.getDocId()));
            call = changeDocumentService.sendNotifyDocumentSame(changeNotifyRequest);
        } else {
            call = changeDocumentService.changeNotify(changeNotifyRequest);
        }
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onChangeDirectDao(ChangeDocumentDirectRequest changeDocumentDirectRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = null;
        if (changeDocumentDirectRequest.getDocId().contains("[")) {
            changeDocumentDirectRequest.setDocId(convertStringListToString(changeDocumentDirectRequest.getDocId()));
            call = changeDocumentService.issuingDocumentComeSame(changeDocumentDirectRequest);
        } else {
            call = changeDocumentService.changeDirect(changeDocumentDirectRequest);
        }

        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetJobPossitionDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<JobPositionRespone> call = changeDocumentService.getJobPossitions();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetUnitDao(int type, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        if (type == 0) {
            Call<UnitRespone> call = changeDocumentService.getUnits();
            call(call, iCallFinishedListener);
            EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
        }
        if (type == 1) {
            Call<UnitRespone> call = changeDocumentService.getUnitClerks();
            call(call, iCallFinishedListener);
            EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
        }
    }

    @Override
    public void onGetLienThongBHDao(String id, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<LienThongRespone> call = changeDocumentService.getLienThongBH(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetLienTHongXLDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<LienThongRespone> call = changeDocumentService.getLienThongXL();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onChangeDocNotifyDao(ChangeDocumentNotifyRequest changeDocumentNotifyRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = changeDocumentService.changeNotify(changeDocumentNotifyRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetGroupPersonCNDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonGroupChangeDocRespone> call = changeDocumentService.getGroupPersonCN();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetGroupPersonDVDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonGroupChangeDocRespone> call = changeDocumentService.getGroupPersonDV();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetPersonSendProcessDao(SearchPersonRequest searchPersonRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonListRespone> call = changeDocumentService.getPersonSendProcess(searchPersonRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetListFormProcessDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<FormDataResponse> call = changeDocumentService.getListFormContent();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetCountDocumentDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<NumberCountDocument> call = changeDocumentService.getCountDocument();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onSendUpLoadFileDao(MultipartBody.Part[] files, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<UploadResponse> call = changeDocumentService.uploadFiles(files);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    private String convertStringListToString(String stringList) {
        String replace = stringList.replace("[", "");
        String replace1 = replace.replace("]", "");

        return replace1;
    }

}
