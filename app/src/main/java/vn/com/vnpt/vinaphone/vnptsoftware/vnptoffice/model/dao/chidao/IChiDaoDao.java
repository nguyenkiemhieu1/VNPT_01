package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.chidao;

import okhttp3.MultipartBody;
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
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IChiDaoDao {
    void onGetChiDaoNhanDao(ChiDaoNhanRequest chiDaoNhanRequest, ICallFinishedListener iCallFinishedListener);
    void onGetChiDaoGuiDao(ChiDaoGuiRequest chiDaoGuiRequest, ICallFinishedListener iCallFinishedListener);
    void onUploadFileDao(MultipartBody.Part[] files, ICallFinishedListener iCallFinishedListener);
    void onSaveChiDao(ChiDaoRequest chiDaoRequest, ICallFinishedListener iCallFinishedListener);
    void onGetPersonChiDao(PersonChiDaoRequest personChiDaoRequest, ICallFinishedListener iCallFinishedListener);
    void onSavePersonChiDao(SavePersonChiDaoRequest savePersonChiDaoRequest, ICallFinishedListener iCallFinishedListener);
    void onGetPersonReceiveChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest, ICallFinishedListener iCallFinishedListener);
    void onGetPersonGroupChiDao(ICallFinishedListener iCallFinishedListener);
    void onSendChiDao(SendChiDaoRequest sendChiDaoRequest, ICallFinishedListener iCallFinishedListener);
    void onGetFlow(String id, ICallFinishedListener iCallFinishedListener);
    void onGetFile(String id, ICallFinishedListener iCallFinishedListener);
    void onDelete(String id, ICallFinishedListener iCallFinishedListener);
    void onGetDetail(String id, ICallFinishedListener iCallFinishedListener);
    void onPersonInGroupChiDao(PersonInGroupChiDaoRequest personInGroupChiDaoRequest, ICallFinishedListener iCallFinishedListener);
    void onPersonReplyChiDao(ReplyChiDaoRequest replyChiDaoRequest, ICallFinishedListener iCallFinishedListener);
    void onGetPersonReceivedChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest, ICallFinishedListener iCallFinishedListener);
    void onGetDonViNhan(ICallFinishedListener iCallFinishedListener);
    void onGetDanhSachDonViNhan(DanhSachDonViNhanRequest danhSachDonViNhanRequest, ICallFinishedListener iCallFinishedListener);

}
