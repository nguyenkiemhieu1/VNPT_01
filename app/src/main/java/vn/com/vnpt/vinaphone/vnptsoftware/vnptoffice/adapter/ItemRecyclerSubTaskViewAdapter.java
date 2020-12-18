package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetListSubTaskResponse;


public class ItemRecyclerSubTaskViewAdapter extends RecyclerView.Adapter<ItemRecyclerSubTaskViewAdapter.ViewHolder> {

    private final List<GetListSubTaskResponse.Data> mValues;

    public ItemRecyclerSubTaskViewAdapter(List<GetListSubTaskResponse.Data> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_subtask, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mCtContent.setText(holder.mItem.getContent());
        holder.mContentView.setText(mValues.get(position).getName());
        holder.mContentStatus.setText(mValues.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final TextView mCtContent;
        public final TextView mContentStatus;
        public GetListSubTaskResponse.Data mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.tv_content_handling);
            mCtContent = (TextView) view.findViewById(R.id.tv_ct_content);
            mContentStatus = (TextView) view.findViewById(R.id.tv_content_status);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
