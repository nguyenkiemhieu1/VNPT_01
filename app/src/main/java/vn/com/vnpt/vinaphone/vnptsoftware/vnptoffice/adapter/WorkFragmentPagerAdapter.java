package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.SubTaskFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment.UnitPerFormFragment;

public class WorkFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private int typeDetail;

    public WorkFragmentPagerAdapter(FragmentManager fm, Context mContext, int typeDetail) {
        super(fm);
        this.mContext = mContext;
        this.typeDetail = typeDetail;
    }

    @Override
    public Fragment getItem(int position) {
        if (typeDetail == 1) {
            if (position == 1) {
                return new SubTaskFragment();
            } else if (position == 0) {
                return new UnitPerFormFragment();
            }
        } else {
            if (position == 0) {
                return new UnitPerFormFragment();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        if (typeDetail == 1) {
            return 2;
        } else {
            return 1;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (typeDetail == 1) {
            switch (position) {
                case 1:
                    return mContext.getString(R.string.str_cong_viec_con);
                case 0:
                    return mContext.getString(R.string.str_canhan_donvi_thuchien);
                default:
                    return null;
            }
        } else {
            return mContext.getString(R.string.str_canhan_donvi_thuchien);
        }

    }
}
