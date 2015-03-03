package com.askokov.module.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.askokov.module.R;

public class PhotoFragment extends Fragment {

    private static final String TAG = "PhotoFragment";

    private String photo;

    public static PhotoFragment createInstance(final String photo) {
        PhotoFragment fragment = new PhotoFragment();
        fragment.photo = photo;

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        return inflater.inflate(R.layout.photo_layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");

        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = (ImageView) view.findViewById(R.id.photo_place);

        Bitmap bitmap = loadBitmap(photo);
        imageView.setImageBitmap(bitmap);
    }

    /*
    public Drawable loadDrawable(String image, AssetManager assetManager) {
        Log.i(TAG, "loadDrawable: " + image);
        Drawable d = null;
        try {
            InputStream is = assetManager.open("images/view/" + image);
            d = Drawable.createFromStream(is, null);
        } catch (IOException ex) {
            Log.i(TAG, "load photo <" + image + "> error");
        }

        return d;
    }
    */

    /**
     * Load bitmap from DCIM
     *
     * @param image photo
     * @return bitmap
     */
    public Bitmap loadBitmap(String image) {
        Log.i(TAG, "loadBitmap: " + image);

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(image, options);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(image, options);
    }

}
