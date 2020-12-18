package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.presenter.chidao;

import okhttp3.MultipartBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoGuiRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DanhSachDonViNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonInGroupChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReplyChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.SendChiDaoRequest;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IChiDaoPresenter {
    void getChiDaoNhan(ChiDaoNhanRequest chiDaoNhanRequest);
    void getChiDaoGui(ChiDaoGuiRequest chiDaoGuiRequest);
    void uploadFiles(MultipartBody.Part[] files);
    void saveChiDao(ChiDaoRequest chiDaoRequest);
    void getPersonChiDao(PersonChiDaoRequest personChiDaoRequest);
    void savePersonChiDao(SavePersonChiDaoRequest savePersonChiDaoRequest);
    void getPersonReceiveChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest);
    void getPersonGroupChiDao();
    void send(SendChiDaoRequest sendChiDaoRequest);
    void getFlowChiDao(String id);
    void getFileChiDao(String id);
    void getDetailChiDao(String id);
    void downloadFileChiDao(DownloadChiDaoRequest downloadChiDaoRequest);
    void getPersonIngroup(PersonInGroupChiDaoRequest personInGroupChiDaoRequest);
    void getPersonReply(ReplyChiDaoRequest replyChiDaoRequest);
    void getPersonReceivedChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest);
    void getDonViNhan();
    void getDanhSachDonViNhan(DanhSachDonViNhanRequest danhSachDonViNhanRequest);
}
