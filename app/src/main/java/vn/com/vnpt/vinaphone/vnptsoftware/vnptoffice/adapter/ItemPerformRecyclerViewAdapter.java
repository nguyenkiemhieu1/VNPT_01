package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListPersonUnitDetailResponse;

import java.util.List;

public class ItemPerformRecyclerViewAdapter extends RecyclerView.Adapter<ItemPerformRecyclerViewAdapter.ViewHolder> {

    private final List<GetListPersonUnitDetailResponse.Data> mValues;

    public ItemPerformRecyclerViewAdapter(List<GetListPersonUnitDetailResponse.Data> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_perform, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mUnitPerson.setText(holder.mItem.getName());
        holder.mRoleContent.setText(holder.mItem.getVaiTro());
        holder.mContentStatus.setText(holder.mItem.getStatus());
        holder.mDateBegin.setText(holder.mItem.getStartDate());
        holder.mDateFinish.setText(holder.mItem.getEndDate());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mUnitPerson;
        public TextView mRoleContent;
        public TextView mContentStatus;
        public TextView mDateBegin;
        public TextView mDateFinish;
        public GetListPersonUnitDetailResponse.Data mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUnitPerson = (TextView) view.findViewById(R.id.tv_unit_person);
            mRoleContent = (TextView) view.findViewById(R.id.tv_role_content);
            mContentStatus = (TextView) view.findViewById(R.id.tv_content_status);
            mDateBegin = (TextView) view.findViewById(R.id.tv_date_begin);
            mDateFinish = (TextView) view.findViewById(R.id.tv_date_finish);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mRoleContent.getText() + "'";
        }
    }
}
