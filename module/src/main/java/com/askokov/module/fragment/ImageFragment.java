package com.askokov.module.fragment;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.askokov.module.R;

public class ImageFragment extends Fragment {

    private static final String TAG = "ImageFragment";

    private String image;

    public static ImageFragment createInstance(final String image) {
        ImageFragment fragment = new ImageFragment();
        fragment.image = image;

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        return inflater.inflate(R.layout.image_layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");

        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = (ImageView) view.findViewById(R.id.imagePlace);
        Drawable d = loadDrawable(image, getActivity().getAssets());
        imageView.setImageDrawable(d);
    }

    /**
     * Called when a fragment is first attached to its activity.
     * {@link #onCreate(android.os.Bundle)} will be called after this.
     *
     * @param activity
     */
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach");
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "onActivityCreated");
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link android.app.Activity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();

        Log.i(TAG, "onDetach");
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link android.app.Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
    }

    /**
     * Called when the Fragment is no longer started.  This is generally
     * tied to {@link android.app.Activity#onStop() Activity.onStop} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStop() {
        super.onStop();

        Log.i(TAG, "onStop");
    }

    /**
     * Load image from assets
     *
     * @param image image
     * @return drawable
     */
    public Drawable loadDrawable(String image, AssetManager assetManager) {
        Log.i(TAG, "loadDrawable: " + image);
        Drawable d = null;
        try {
            InputStream is = assetManager.open("images/view/" + image);
            d = Drawable.createFromStream(is, null);
        } catch (IOException ex) {
            Log.i(TAG, "load image <" + image + "> error");
        }

        return d;
    }
}
