package com.askokov.module.fragment;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import com.askokov.module.R;

public class VideoFragment extends Fragment {

    private static final String TAG = "VideoFragment";

    private String video;

    public static VideoFragment createInstance(final String video) {
        VideoFragment fragment = new VideoFragment();
        fragment.video = video;

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        return inflater.inflate(R.layout.video_layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");

        super.onViewCreated(view, savedInstanceState);

        final VideoView videoView = (VideoView) view.findViewById(R.id.video_place);

        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        final MediaController mediaController = new MediaController(view.getContext());
        mediaController.setAnchorView(videoView);
        mediaController.setPadding(6, 0, 6, 0);

        //Hack ---------
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        mediaController.setLayoutParams(lp);

        ((ViewGroup) mediaController.getParent()).removeView(mediaController);
        ((FrameLayout) view.findViewById(R.id.video_wrapper)).addView(mediaController);
        //--------------

        videoView.setMediaController(mediaController);
        videoView.setVideoPath(video);
        //videoView.setVisibility(View.VISIBLE);
        videoView.setZOrderMediaOverlay(false);

        mediaController.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                Log.i(TAG, "----- VideoView.onPrepared");

                videoView.start();
                videoView.requestFocus();
            }
        });
    }
}
