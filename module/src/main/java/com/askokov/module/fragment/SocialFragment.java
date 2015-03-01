package com.askokov.module.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.askokov.module.R;

/**
 *
 */
public class SocialFragment extends Fragment {

    private static final String TAG = "SocialFragment";

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.social_layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");

        super.onViewCreated(view, savedInstanceState);
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

        Button btnFB = (Button)getView().findViewById(R.id.btnFB);
        btnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.i(TAG, "Click Facebook");
                Toast.makeText(getView().getContext(), "Facebook posting...", Toast.LENGTH_LONG).show();
            }
        });

        Button btnVK = (Button)getView().findViewById(R.id.btnVK);
        btnVK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.i(TAG, "Click VKontakte");
                Toast.makeText(getView().getContext(), "VKontakte posting...", Toast.LENGTH_LONG).show();
            }
        });

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
}
