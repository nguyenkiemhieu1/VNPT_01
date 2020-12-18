package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.viewevent;

import android.support.v7.widget.RecyclerView;

public abstract class OnScrollObserver extends RecyclerView.OnScrollListener {

    int scrollDist = 0;
    boolean isVisible = true;
    int MINIMUM = 25;

    public abstract void show();
    public abstract void hide();

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (isVisible && scrollDist > MINIMUM) {
            hide();
            scrollDist = 0;
            isVisible = false;
        }
        else if (!isVisible && scrollDist < -MINIMUM) {
            show();
            scrollDist = 0;
            isVisible = true;
        }

        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            scrollDist += dy;
        }
    }

}
