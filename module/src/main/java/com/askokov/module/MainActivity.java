package com.askokov.module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import com.askokov.module.order.OrderManager;
import com.askokov.module.order.SerialManager;

/**
 * Main activity for application
 */
public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private static final String POSITION = "Position";
    private static final String PAGER_TYPE = "PagerType";
    private static final String MEDIA_STORAGE = "MediaStorage";
    private static final String CAMERA = "/Camera/";
    private static final String EXT_JPG = ".jpg";
    private static final String EXT_3GP = ".3gp";
    private static final String EXT_MP4 = ".mp4";

    public enum PagerType {
        PHOTO,
        VIDEO
    }

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private SeekBar seekBar;
    private PagerType pagerType = PagerType.PHOTO;
    private File mediaStorage;
    private MenuItem menuPhoto;
    private MenuItem menuVideo;
    private TextView textHint;
    private TextView textStatus;
    private List<String> list = new ArrayList<String>();
    private int listPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.main_layout);
        restoreOrCreate(savedInstanceState);
        initActionBarIcon(pagerType, getActionBar());

        textStatus = (TextView) findViewById(R.id.textStatus);
        textHint = (TextView) findViewById(R.id.textHint);

        OrderManager orderManager = new SerialManager();
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), list, orderManager,
            mediaStorage.getAbsolutePath() + CAMERA);
        pagerAdapter.setType(pagerType);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setPageMargin(10);
        pager.setCurrentItem(listPosition);
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i1) {
                //Log.i(TAG, "onPageScrolled: i=" + i + ", v=" + v + ", i1=" + i1);
            }

            @Override
            public void onPageSelected(final int i) {
                Log.i(TAG, "onPageSelected: " + i);
                seekBar.setProgress(i);
                textHint.setText(list.get(pagerAdapter.getDataPosition(i)));
            }

            @Override
            public void onPageScrollStateChanged(final int i) {
                //Log.i(TAG, "onPageScrollStateChanged: " + i);
            }
        });

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                Log.i(TAG, "onProgressChanged: progress=" + progress);
                pager.setCurrentItem(progress);
            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {
                //Log.i(TAG, "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                //Log.i(TAG, "onStopTrackingTouch");
            }
        });

        init();
    }

    private void restoreOrCreate(final Bundle savedInstanceState) {
        listPosition = savedInstanceState != null ? savedInstanceState.getInt(POSITION, 0) : 0;

        if (savedInstanceState != null) {
            PagerType saved = (PagerType) savedInstanceState.getSerializable(PAGER_TYPE);

            if (saved != null) {
                pagerType = saved;
            }

            mediaStorage = (File) savedInstanceState.getSerializable(MEDIA_STORAGE);
        }

        if (mediaStorage == null) {
            mediaStorage = availableStorage();
        }
    }

    private void init() {
        Resources res = getResources();
        if (mediaStorage == null) {
            String text = String.format(res.getString(R.string.storage_not_available), res.getString(R.string.storage_photo));
            textStatus.setText(text);
        } else {
            String photosPath = mediaStorage.getAbsolutePath() + CAMERA;
            list.clear();
            list.addAll(getMediaList(photosPath, pagerType));

            String text = String.format(res.getString(R.string.storage_info), photosPath, list.size(), res.getString(R.string.storage_photo));
            textStatus.setText(text);
        }

        pagerAdapter.setType(pagerType);
        pagerAdapter.notifyDataSetChanged();

        seekBar.setMax(list.size() - 1);
        seekBar.setProgress(listPosition);

        textHint.setText(list.get(pagerAdapter.getDataPosition(listPosition)));
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        Log.i(TAG, "onSaveInstanceState: outState=" + outState);
        outState.putInt(POSITION, pager.getCurrentItem());
        outState.putSerializable(PAGER_TYPE, pagerType);
        outState.putSerializable(MEDIA_STORAGE, mediaStorage);
        //super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu");

        menuPhoto = menu.findItem(R.id.menu_photo);
        menuVideo = menu.findItem(R.id.menu_video);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_photo:
                Log.i(TAG, "----- Photo");
                pagerType = PagerType.PHOTO;

                break;

            case R.id.menu_video:
                Log.i(TAG, "----- Video");
                pagerType = PagerType.VIDEO;

                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        initActionBar(pagerType);
        init();
        return true;
    }

    private File availableStorage() {
        File storage = null;
        //Доступность SD
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //Environment.getExternalStorageDirectory() < API Level 8
            storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        } else {
            Log.i(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
        }

        return storage;
    }

    private List<String> getMediaList(String path, PagerType type) {
        List<String> list = new ArrayList<String>();

        Log.i(TAG, "Absolute path<" + path + ">");

        String[] files = new File(path).list();
        for (String f : files) {
            switch (type) {
                case PHOTO:
                    if (f.endsWith(EXT_JPG)) {
                        list.add(f);
                    }

                    break;
                case VIDEO:
                    if (f.endsWith(EXT_3GP) || f.endsWith(EXT_MP4)) {
                        list.add(f);
                    }

                    break;
            }
        }

        return list;
    }

    private void initActionBarIcon(PagerType type, ActionBar actionBar) {
        switch (type) {
            case PHOTO:
                actionBar.setIcon(R.drawable.camera);

                break;
            case VIDEO:
                actionBar.setIcon(R.drawable.camcorder);

                break;
        }
    }

    private void initActionBar(PagerType type) {
        final ActionBar actionBar = getActionBar();
        initActionBarIcon(type, actionBar);

        switch (type) {
            case PHOTO:
                menuVideo.setChecked(false);
                menuPhoto.setChecked(true);

                break;
            case VIDEO:
                menuPhoto.setChecked(false);
                menuVideo.setChecked(true);

                break;
        }
    }
}
