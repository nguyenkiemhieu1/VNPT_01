package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.MenuHome;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ChiDaotEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.EventTypeQLVB;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.ChiDaoFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.ContactFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.DocumentWaitingFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.ScheduleBossFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.WorkflowManagementFragment;

public class MenuHomeAdapter extends RecyclerView.Adapter<MenuHomeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MenuHome> arrayListMenu;
    Locale locale = new Locale("vi", "VN");
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);


    public MenuHomeAdapter(Context context, ArrayList<MenuHome> arrayListMenu) {
        this.context = context;
        this.arrayListMenu = arrayListMenu;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_home, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.layoutItem.setBackground(arrayListMenu.get(position).getDrawableBackdround());
        holder.tv_title.setTypeface(Application.getApp().getTypeface());
        holder.tv_title.setText(arrayListMenu.get(position).getTitle());
        holder.image.setImageDrawable(arrayListMenu.get(position).getDrawableImgae());
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = arrayListMenu.get(position).getType();
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                switch (type) {
                    case "DOCUMENT":
                        EventBus.getDefault().postSticky(new EventTypeQLVB(0));
                        fragmentManager.beginTransaction().replace(R.id.content_frame, DocumentWaitingFragment.newInstance(Constants.CONSTANTS_DEN_CHO_XU_LY)
                                , new DocumentWaitingFragment().getClass().toString()).commit();
                        ((MainActivity) context).setTitle(Constants.CONSTANTS_VAN_BAN_DEN_CHO_XU_LY);
                        break;
                    case "SCHEDULE":
                        fragmentManager.beginTransaction().replace(R.id.content_frame, new ScheduleBossFragment()).commit();
                        ((MainActivity) context).setTitle(context.getString(R.string.SCHEDULE_MENU));
                        break;
                    case "CONTACT":
                        fragmentManager.beginTransaction().replace(R.id.content_frame, new ContactFragment()).commit();
                        ((MainActivity) context).setTitle(context.getString(R.string.CONTACT_MENU));
                        break;
                    case "OPERATING":
                        EventBus.getDefault().postSticky(new ChiDaotEvent(0));
                        fragmentManager.beginTransaction().replace(R.id.content_frame, new ChiDaoFragment()).commit();
                        ((MainActivity) context).setTitle(context.getString(R.string.CHIDAO_NHAN_MENU));
                        ((MainActivity) context).hideNotify();
                        break;
                    case "WORKMANAGER":
                        fragmentManager.beginTransaction().replace(R.id.content_frame, new WorkflowManagementFragment().newInstance(1)).commit();
                        ((MainActivity) context).setTitle(context.getString(R.string.tv_congviec_duocgiao));
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListMenu.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView tv_title;
        ConstraintLayout layoutItem;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_menu);
            tv_title = itemView.findViewById(R.id.tv_title);
            layoutItem = itemView.findViewById(R.id.layout_item);
        }
    }

}
