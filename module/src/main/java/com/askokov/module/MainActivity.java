package com.askokov.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import com.askokov.module.order.OrderManager;
import com.askokov.module.order.ReverseManager;

/**
 * Main activity for application
 */
public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private static final String POSITION = "position";

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate");

        setContentView(R.layout.main_layout);
        Log.i(TAG, "setContentView");

        final List<String> list = getImageList(getAssets());
        int position = savedInstanceState != null ? savedInstanceState.getInt(POSITION, 0) : 0;
        OrderManager orderManager = new ReverseManager(list.size());

        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(list.size() - 1);
        seekBar.setProgress(position);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                Log.i(TAG, "onProgressChanged: progress=" + progress);
                pager.setCurrentItem(progress);
            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {
                Log.i(TAG, "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                Log.i(TAG, "onStopTrackingTouch");
            }
        });

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), list, orderManager);
        final TextView textView = (TextView) findViewById(R.id.textHint);
        textView.setText(list.get(pagerAdapter.getDataPosition(position)));

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setPageMargin(10);

        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i1) {
                Log.i(TAG, "onPageScrolled: i=" + i + ", v=" + v + ", i1=" + i1);
            }

            @Override
            public void onPageSelected(final int i) {
                Log.i(TAG, "onPageSelected: " + i);
                seekBar.setProgress(i);
                textView.setText(list.get(pagerAdapter.getDataPosition(i)));
            }

            @Override
            public void onPageScrollStateChanged(final int i) {
                Log.i(TAG, "onPageScrollStateChanged: " + i);
            }
        });
        pager.setCurrentItem(position);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        Log.i(TAG, "onSaveInstanceState: outState=" + outState);
        outState.putInt(POSITION, pager.getCurrentItem());
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");

        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.i(TAG, "onStop");
    }

    /**
     * Retrieve image names list
     *
     * @return images list
     */
    public List<String> getImageList(AssetManager assetManager) {
        List<String> list = new ArrayList<String>();

        try {
            String[] files = assetManager.list("images/view");

            if (files != null && files.length > 0) {
                list.addAll(Arrays.asList(files));
            }
        } catch (IOException ex) {
            Log.i(TAG, "read files list error", ex);
        }

        return list;
    }
}
