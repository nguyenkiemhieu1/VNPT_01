package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogInfo;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class HistoryInfoAdapter extends ArrayAdapter<LogInfo> {
    private Context context;
    private int resource;
    private List<LogInfo> logInfoList;
    private String status;

    public HistoryInfoAdapter(Context context, int resource, List<LogInfo> logInfoList, String status) {
        super(context, resource, logInfoList);
        this.context = context;
        this.resource = resource;
        this.logInfoList = logInfoList;
        this.status = status;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        LogInfo logInfo = logInfoList.get(position);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        TextView txtDate = (TextView) view.findViewById(R.id.txtDate);
        TextView txtDateLB = (TextView) view.findViewById(R.id.txtDate_label);
        TextView txtChuyenToi_label = (TextView) view.findViewById(R.id.txtChuyenToi_label);
        TextView txtChuyenToi = (TextView) view.findViewById(R.id.txtChuyenToi);
        TextView txtDongGui_label = (TextView) view.findViewById(R.id.txtDongGui_label);
        TextView txtDongGui = (TextView) view.findViewById(R.id.txtDongGui);
        TextView txtChuyenXL_label = (TextView) view.findViewById(R.id.txtChuyenXL_label);
        TextView txtChuyenXL = (TextView) view.findViewById(R.id.txtChuyenXL);
        TextView txtThaoTac_label = (TextView) view.findViewById(R.id.txtThaoTac_label);
        TextView txtThaoTac = (TextView) view.findViewById(R.id.txtThaoTac);
        TextView txtNoiDung_label = (TextView) view.findViewById(R.id.txtNoiDung_label);
        TextView txtNoiDung = (TextView) view.findViewById(R.id.txtNoiDung);
        TextView txtXinykien_label = (TextView) view.findViewById(R.id.txtXinykien_label);
        TextView txtXinykien = (TextView) view.findViewById(R.id.txtXinykien);
        txtDateLB.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtDate.setTypeface(Application.getApp().getTypeface());
        txtEmail.setTypeface(Application.getApp().getTypeface());
        txtXinykien_label.setTypeface(Application.getApp().getTypeface());
        txtXinykien.setTypeface(Application.getApp().getTypeface());
        txtName.setTypeface(Application.getApp().getTypeface());
        txtDateLB.setTypeface(Application.getApp().getTypeface());
        txtChuyenToi_label.setTypeface(Application.getApp().getTypeface());
        txtChuyenToi.setTypeface(Application.getApp().getTypeface());
        txtDongGui_label.setTypeface(Application.getApp().getTypeface());
        txtDongGui.setTypeface(Application.getApp().getTypeface());
        txtChuyenXL_label.setTypeface(Application.getApp().getTypeface());
        txtChuyenXL.setTypeface(Application.getApp().getTypeface());
        txtThaoTac_label.setTypeface(Application.getApp().getTypeface());
        txtThaoTac.setTypeface(Application.getApp().getTypeface());
        txtNoiDung_label.setTypeface(Application.getApp().getTypeface());
        txtNoiDung.setTypeface(Application.getApp().getTypeface());
        txtName.setText(logInfo.getFullName());
        txtEmail.setText(logInfo.getUpdateBy());
        if (logInfo.getUpdateDate() != null && !logInfo.getUpdateDate().trim().equals("") ) {
            txtDate.setText(logInfo.getUpdateDate());
        }
        if (!status.equals(Constants.NEW_HISTORY)) {
            txtNoiDung.setText(logInfo.getComment());
            txtThaoTac.setText(logInfo.getAction());
            if (logInfo.getChuyenToi() != null && !logInfo.getChuyenToi().equals("")) {
                String[] data = logInfo.getChuyenToi().split("\\|");
                try {
                    if (data[0] != null && !data[0].trim().equals("") && data[0].contains(":")) {
                        txtChuyenToi.setText(data[0].split("\\:")[1]);
                        txtChuyenToi_label.setText(data[0].split("\\:")[0] + ":");
                    } else {
                        txtChuyenToi.setText(data[0]);
                    }
                } catch (Exception ex) {
                    txtChuyenToi.setText("");
                    txtChuyenToi_label.setText("");
                    txtChuyenToi.setVisibility(View.GONE);
                    txtChuyenToi_label.setVisibility(View.GONE);
                }
                try {
                    if (data[1] != null && !data[1].trim().equals("") && data[1].contains(":")) {
                        txtChuyenXL.setText(data[1].split("\\:")[1]);
                        txtChuyenXL_label.setText(data[1].split("\\:")[0] + ":");
                    } else {
                        txtChuyenXL.setText(data[1]);
                    }
                } catch (Exception ex) {
                    txtChuyenXL.setText("");
                    txtChuyenXL_label.setText("");
                    txtChuyenXL.setVisibility(View.GONE);
                    txtChuyenXL_label.setVisibility(View.GONE);
                }
                try {
                    if (data[2] != null && !data[2].trim().equals("") && data[2].contains(":")) {
                        txtDongGui.setText(data[2].split("\\:")[1]);
                        txtDongGui_label.setText(data[2].split("\\:")[0] + ":");
                    } else {
                        txtDongGui.setText(data[2]);
                    }
                } catch (Exception ex) {
                    txtDongGui.setText("");
                    txtDongGui_label.setText("");
                    txtDongGui.setVisibility(View.GONE);
                    txtDongGui_label.setVisibility(View.GONE);
                }
                try {
                    if (data[3] != null && !data[3].trim().equals("") && data[3].contains(":")) {
                        txtXinykien.setText(data[3].split("\\:")[1]);
                        txtXinykien_label.setText(data[3].split("\\:")[0] + ":");
                    } else {
                        txtXinykien.setText(data[3]);
                    }
                } catch (Exception ex) {
                    txtXinykien.setText("");
                    txtXinykien_label.setText("");
                    txtXinykien.setVisibility(View.GONE);
                    txtXinykien_label.setVisibility(View.GONE);
                }
            } else {
                txtChuyenToi.setText("");
                txtDongGui.setText("");
                txtChuyenXL.setText("");
                txtXinykien.setText("");
                txtChuyenToi_label.setText("");
                txtDongGui_label.setText("");
                txtChuyenXL_label.setText("");
                txtXinykien_label.setText("");
                txtXinykien.setVisibility(View.GONE);
                txtXinykien_label.setVisibility(View.GONE);
                txtChuyenToi_label.setVisibility(View.GONE);
                txtChuyenXL_label.setVisibility(View.GONE);
                txtDongGui_label.setVisibility(View.GONE);
                txtChuyenToi.setVisibility(View.GONE);
                txtDongGui.setVisibility(View.GONE);
                txtChuyenXL.setVisibility(View.GONE);
            }
        } else {
            txtChuyenToi.setVisibility(View.GONE);
            txtDongGui.setVisibility(View.GONE);
            txtChuyenXL.setVisibility(View.GONE);
            txtNoiDung.setVisibility(View.GONE);
            txtThaoTac.setVisibility(View.GONE);
            txtThaoTac_label.setVisibility(View.GONE);
            txtNoiDung_label.setVisibility(View.GONE);
            txtChuyenToi_label.setVisibility(View.GONE);
            txtChuyenXL_label.setVisibility(View.GONE);
            txtDongGui_label.setVisibility(View.GONE);
            txtXinykien.setVisibility(View.GONE);
            txtXinykien_label.setVisibility(View.GONE);
        }
        if (status.equals(Constants.PROCESSED_HISTORY)) {
            txtChuyenToi.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtDongGui.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtChuyenXL.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtNoiDung.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtThaoTac.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtThaoTac_label.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtNoiDung_label.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtChuyenToi_label.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtChuyenXL_label.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtDongGui_label.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtXinykien.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtXinykien_label.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtName.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtEmail.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
            txtDate.setTextColor(context.getResources().getColor(R.color.md_grey_processed));
        }
        return view;
    }
}
