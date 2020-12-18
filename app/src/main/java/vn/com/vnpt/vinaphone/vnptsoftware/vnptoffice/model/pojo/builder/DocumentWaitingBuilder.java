package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.builder;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.DocumentWaiting;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentWaitingInfo;

/**
 * Created by LinhLK - 0948012236 on 9/8/2017.
 */

public class DocumentWaitingBuilder {
    private Context context;

    public DocumentWaitingBuilder(Context context) {
        this.context = context;
    }

    public List<DocumentWaiting> convertFromDocumentWaitingInfo(List<DocumentWaitingInfo> documentWaitingInfos) {
        List<DocumentWaiting> documentWaitings = new ArrayList<>();
        for (DocumentWaitingInfo documentWaitingInfo : documentWaitingInfos) {
            DocumentWaiting documentWaiting = new DocumentWaiting();
            documentWaiting.setId(documentWaitingInfo.getId());
            documentWaiting.setCoQuanBanHanh(documentWaitingInfo.getCoQuanBanHanh());
            documentWaiting.setDoKhan(documentWaitingInfo.getDoKhan());
            documentWaiting.setFileDinhKem(documentWaitingInfo.getHasFile());
            documentWaiting.setKiHieu(documentWaitingInfo.getSoKihieu());
            documentWaiting.setTrichYeu(documentWaitingInfo.getTrichYeu());
            documentWaiting.setNgayVanBan(documentWaitingInfo.getNgayVanBan());
            documentWaiting.setProcessDefinitionId(documentWaitingInfo.getProcessDefinitionId());
            documentWaiting.setProcessInstanceId(documentWaitingInfo.getProcessInstanceId());
            documentWaiting.setTrangThai(documentWaitingInfo.getIsRead());
            documentWaiting.setCongVanDenDi(documentWaitingInfo.getCongVanDenDi());
            documentWaiting.setIsCheck(documentWaitingInfo.getIsCheck());
            documentWaiting.setIsChuTri(documentWaitingInfo.getIsChuTri());
            documentWaiting.setIsComment(documentWaitingInfo.getIsComment());
            documentWaiting.setIsSign(documentWaitingInfo.getIsSign());
            if (documentWaitingInfo.getNgayNhan() != null && !documentWaitingInfo.getNgayNhan().trim().equals("")
                    && !documentWaitingInfo.getNgayNhan().trim().equals("null")) {
                String[] datetime = documentWaitingInfo.getNgayNhan().split(" ");
                if (datetime.length == 1) {
                    documentWaiting.setNgayNhan(datetime[0]);
                }
                if (datetime.length == 2) {
                    documentWaiting.setNgayNhan(datetime[0]);
                    documentWaiting.setThoiGianNhan(datetime[1]);
                }
            }
            documentWaitings.add(documentWaiting);
        }
        return documentWaitings.size() > 0 ? documentWaitings : null;
    }
}
