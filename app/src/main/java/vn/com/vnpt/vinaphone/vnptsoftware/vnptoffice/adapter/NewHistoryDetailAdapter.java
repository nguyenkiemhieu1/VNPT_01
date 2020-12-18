package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogInfo;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class NewHistoryDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<LogInfo> logInfoList;
    private String status;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new MyViewHolder(inflater.inflate(R.layout.item_new_log_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).bindData(logInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return logInfoList != null ? logInfoList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_noidung)
        TextView tvNoidung;
        @BindView(R.id.tv_xlc_lb)
        TextView tvXlcLb;
        @BindView(R.id.txtXLC)
        TextView txtXLC;
        @BindView(R.id.tv_ph_lb)
        TextView tvPhLb;
        @BindView(R.id.txtPH)
        TextView txtPH;
        @BindView(R.id.tv_xem_lb)
        TextView tvXemLb;
        @BindView(R.id.txtXem)
        TextView txtXem;
        @BindView(R.id.tv_xyk_lb)
        TextView tv_xyk_lb;
        @BindView(R.id.txtXYK)
        TextView txtXYK;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.layout_process)
        LinearLayout layoutProcess;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindData(final LogInfo logInfo) {
            tvName.setText(logInfo.getFullName());
            tvUsername.setText(logInfo.getUpdateBy());
            if (logInfo.getUpdateDate() != null && !logInfo.getUpdateDate().trim().equals("")) {
                tvDate.setText(logInfo.getUpdateDate());
            } else {
                tvDate.setText("");
            }
            if (!status.equals(Constants.NEW_HISTORY)) {
                if (logInfo.getComment() != null && !logInfo.getComment().trim().equals("")) {
                    tvNoidung.setText(logInfo.getComment());
                } else {
                    line.setVisibility(View.GONE);
                    tvNoidung.setVisibility(View.GONE);
                }
                if (logInfo.getChuyenToi() != null && !logInfo.getChuyenToi().equals("")) {
                    String[] data = logInfo.getChuyenToi().split("\\|");
                    try {
                        if (data[0] != null && !data[0].trim().equals("") && data[0].contains(":")) {
                            txtXLC.setText(data[0].split("\\:")[1]);
                            tvXlcLb.setText(data[0].split("\\:")[0] + ":");
                        } else {
                            if (data[0] != null && !data[0].trim().equals("")) {
                                txtXLC.setText(data[0]);
                            } else {
                                txtXLC.setVisibility(View.GONE);
                                tvXlcLb.setVisibility(View.GONE);
                            }
                        }
                    } catch (Exception ex) {
                        txtXLC.setVisibility(View.GONE);
                        tvXlcLb.setVisibility(View.GONE);
                    }
                    try {
                        if (data[1] != null && !data[1].trim().equals("") && data[1].contains(":")) {
                            txtPH.setText(data[1].split("\\:")[1]);
                            tvPhLb.setText(data[1].split("\\:")[0] + ":");
                        } else {
                            if (data[1] != null && !data[1].trim().equals("")) {
                                txtPH.setText(data[1]);
                            } else {
                                txtPH.setVisibility(View.GONE);
                                tvPhLb.setVisibility(View.GONE);
                            }
                        }
                    } catch (Exception ex) {
                        txtPH.setVisibility(View.GONE);
                        tvPhLb.setVisibility(View.GONE);
                    }
                    try {
                        if (data[2] != null && !data[2].trim().equals("") && data[2].contains(":")) {
                            txtXem.setText(data[2].split("\\:")[1]);
                            tvXemLb.setText(data[2].split("\\:")[0] + ":");
                        } else {
                            if (data[2] != null && !data[2].trim().equals("")) {
                                txtXem.setText(data[2]);
                            } else {
                                tvXemLb.setVisibility(View.GONE);
                                txtXem.setVisibility(View.GONE);
                            }
                        }
                    } catch (Exception ex) {
                        tvXemLb.setVisibility(View.GONE);
                        txtXem.setVisibility(View.GONE);
                    }
                    try {
                        if (data[3] != null && !data[3].trim().equals("") && data[3].contains(":")) {
                            txtXYK.setText(data[3].split("\\:")[1]);
                            tv_xyk_lb.setText(data[3].split("\\:")[0] + ":");
                        } else {
                            if (data[3] != null && !data[3].trim().equals("")) {
                                txtXYK.setText(data[3]);
                            } else {
                                txtXYK.setVisibility(View.GONE);
                                tv_xyk_lb.setVisibility(View.GONE);
                            }
                        }
                    } catch (Exception ex) {
                        txtXYK.setVisibility(View.GONE);
                        tv_xyk_lb.setVisibility(View.GONE);
                    }
                } else {
                    tvXlcLb.setVisibility(View.GONE);
                    txtXLC.setVisibility(View.GONE);
                    tvPhLb.setVisibility(View.GONE);
                    txtPH.setVisibility(View.GONE);
                    tvXemLb.setVisibility(View.GONE);
                    txtXem.setVisibility(View.GONE);
                    txtXYK.setVisibility(View.GONE);
                    tv_xyk_lb.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                }
            } else {
                tvNoidung.setVisibility(View.GONE);
                tvXlcLb.setVisibility(View.GONE);
                txtXLC.setVisibility(View.GONE);
                tvPhLb.setVisibility(View.GONE);
                txtPH.setVisibility(View.GONE);
                tvXemLb.setVisibility(View.GONE);
                txtXem.setVisibility(View.GONE);
                txtXYK.setVisibility(View.GONE);
                tv_xyk_lb.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
            }
        }
    }

    public NewHistoryDetailAdapter(Context mContext, List<LogInfo> datalist, String status) {
        this.mContext = mContext;
        this.logInfoList = datalist;
        this.status = status;
    }

}
