package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.chidao;

import org.greenrobot.eventbus.EventBus;

import okhttp3.MultipartBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoGuiRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DanhSachDonViNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonInGroupChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReplyChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SendChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChiDaoFlowRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DanhSachDonViNhanRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DeleteChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DonViNhanRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.FileChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonGroupChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonInGroupChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonReceiveChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ReplyChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SaveChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SavePersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.SendChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.UploadResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IChiDaoService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class ChiDaoDao extends BaseDao implements IChiDaoDao {
    private IChiDaoService chiDaoService;

    @Override
    public void onGetChiDaoNhanDao(ChiDaoNhanRequest chiDaoNhanRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<ChiDaoRespone> call = chiDaoService.getChiDaoNhan(chiDaoNhanRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetChiDaoGuiDao(ChiDaoGuiRequest chiDaoGuiRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<ChiDaoRespone> call = chiDaoService.getChiDaoGui(chiDaoGuiRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onUploadFileDao(MultipartBody.Part[] files, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<UploadResponse> call = chiDaoService.uploadFiles( files);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onSaveChiDao(ChiDaoRequest chiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<SaveChiDaoRespone> call = null;
        if (chiDaoRequest.getId() != null && !chiDaoRequest.getId().trim().equals("")) {
            call = chiDaoService.editChiDao(chiDaoRequest);
            EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
        } else {
            call = chiDaoService.createChiDao(chiDaoRequest);
            EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
        }
        call(call, iCallFinishedListener);

    }

    @Override
    public void onGetPersonChiDao(PersonChiDaoRequest personChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<PersonChiDaoRespone> call = chiDaoService.getPersons(personChiDaoRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onSavePersonChiDao(SavePersonChiDaoRequest savePersonChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<SavePersonChiDaoRespone> call = chiDaoService.savePersons(savePersonChiDaoRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetPersonReceiveChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<PersonReceiveChiDaoRespone> call = chiDaoService.getPersonsReceive(personReceiveChiDaoRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetPersonGroupChiDao(ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<PersonGroupChiDaoRespone> call = chiDaoService.getPersonsGroup();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onSendChiDao(SendChiDaoRequest sendChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<SendChiDaoRespone> call = chiDaoService.send(sendChiDaoRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetFlow(String id, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<ChiDaoFlowRespone> call = chiDaoService.getFlows(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetFile(String id, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<FileChiDaoRespone> call = chiDaoService.getFiles(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onDelete(String id, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<DeleteChiDaoRespone> call = chiDaoService.delete(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetDetail(String id, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<DetailChiDaoRespone> call = chiDaoService.getDetail(id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onPersonInGroupChiDao(PersonInGroupChiDaoRequest personInGroupChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<PersonInGroupChiDaoRespone> call = chiDaoService.getPersonInGroup(personInGroupChiDaoRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onPersonReplyChiDao(ReplyChiDaoRequest replyChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<ReplyChiDaoRespone> call = chiDaoService.getPersonReply(replyChiDaoRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetPersonReceivedChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<PersonReceiveChiDaoRespone> call = chiDaoService.getPersonsReceived(personReceiveChiDaoRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
    @Override
    public void onGetDonViNhan(ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<DonViNhanRespone> call = chiDaoService.getDonViNhan();
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetDanhSachDonViNhan(DanhSachDonViNhanRequest danhSachDonViNhanRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<DanhSachDonViNhanRespone> call = chiDaoService.getDanhSachDonViNhan(danhSachDonViNhanRequest);
        call(call, iCallFinishedListener);
    }


}
