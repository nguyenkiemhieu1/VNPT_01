package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ObjectAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.TimeTaoCongViecEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UserSelectEvent;

public class UnitPersonCreateWorkAdapter extends RecyclerView.Adapter<UnitPersonCreateWorkAdapter.ViewHolder> {

    private List<ObjectAssignRequest> listData;
    private Context mContext;
    private String mDateParent = "";

    public UnitPersonCreateWorkAdapter(List<ObjectAssignRequest> listData, Context mContext) {
        this.listData = listData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_unit_person_create_work, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectAssignRequest item = listData.get(position);
        holder.tvUnitPerson.setText(item.getNameUser());
        holder.tvEndDate.setText(item.getEndDate());

    }

    @Override
    public int getItemCount() {
        return this.listData != null ? listData.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_unit_person)
        TextView tvUnitPerson;
        @BindView(R.id.tv_end_date)
        TextView tvEndDate;
        @BindView(R.id.iv_delete)
        FancyButton ivDelete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ivDelete.setOnClickListener(this);
            tvEndDate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_end_date:
                    showDialogPicker(getAdapterPosition());
                    break;
                case R.id.iv_delete:
                    removeAt(getAdapterPosition());
                    break;
            }
        }
    }

    public void removeAt(int position) {
        if (position >= 0 && position < listData.size()) {
            EventBus.getDefault().postSticky(new UserSelectEvent(listData.get(position), 1));
            listData.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listData.size());
        }
    }

    public void updateDateParent(String mDate) {
        mDateParent = mDate;
    }

    private void showDialogPicker(final int position) {

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Lấy ngày sau khi chọn datepicker
                String strDay = "";
                String strMonth = "";
                if (dayOfMonth < 10) {
                    strDay = "0" + String.valueOf(dayOfMonth);
                } else {
                    strDay = String.valueOf(dayOfMonth);
                }
                if (monthOfYear < 9) {
                    strMonth = "0" + String.valueOf(monthOfYear + 1);
                } else {
                    strMonth = String.valueOf(monthOfYear + 1);
                }
                String strDate = String.valueOf(strDay) + "/" + String.valueOf(strMonth)
                        + "/" + String.valueOf(year);
                listData.get(position).setEndDate(strDate);
                EventBus.getDefault().postSticky(new UserSelectEvent(listData.get(position), 2));
                notifyItemChanged(position);
            }
        }, yy, mm, dd);

        //setmin
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        //setmax
       // TimeTaoCongViecEvent eventMax = EventBus.getDefault().getStickyEvent(TimeTaoCongViecEvent.class);

        String strDate =listData.get(position).getEndDate();
        String arrDate[] = strDate.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(arrDate[2]), Integer.parseInt(arrDate[1]) - 1, Integer.parseInt(arrDate[0]));//Year,Mounth - 1,Day
        Calendar d = Calendar.getInstance();
        d.set(Calendar.HOUR_OF_DAY, 0);
        d.set(Calendar.MINUTE, 0);
        d.set(Calendar.SECOND, 0);
        d.set(Calendar.MILLISECOND, 0);

        if (d.getTime().getTime()-1000 >= coverStringToMilliseconds(strDate)) {
            AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.TITLE_NOTIFICATION),
                    "Công việc đã quá hạn xử lý! không thể chọn ngày", true, AlertDialogManager.WARNING);
            return;
        }
        datePicker.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePicker.show();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TimeTaoCongViecEvent event) {
        /* Do something */
        if (event != null) {
        }
    }

    private long coverStringToMilliseconds(String dataTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date mDate = sdf.parse(dataTime);
            long timeInMilliseconds = mDate.getTime();
            return timeInMilliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}