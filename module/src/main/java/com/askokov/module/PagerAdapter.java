package com.askokov.module;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import com.askokov.module.fragment.PhotoFragment;
import com.askokov.module.fragment.VideoFragment;
import com.askokov.module.order.OrderManager;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "PagerAdapter";

    private OrderManager orderManager;
    private List<String> data;
    private String cameraAbsolutePath;
    private MainActivity.PagerType type;

    public PagerAdapter(final FragmentManager fm, final List<String> data,
                        final OrderManager orderManager, final String cameraAbsolutePath) {
        super(fm);
        this.data = data;
        this.orderManager = orderManager;
        this.cameraAbsolutePath = cameraAbsolutePath;
    }

    public void setType(final MainActivity.PagerType type) {
        this.type = type;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(final int position) {
        Log.i(TAG, "getItem: " + position);

        Fragment fragment = null;
        switch (type) {
            case PHOTO:
                fragment = PhotoFragment.createInstance(cameraAbsolutePath + data.get(getDataPosition(position)));
                break;
            case VIDEO:
                fragment = VideoFragment.createInstance(cameraAbsolutePath + data.get(getDataPosition(position)));
                break;
        }

        return fragment;
    }

    public int getDataPosition(final int position) {
        return orderManager.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }
}
