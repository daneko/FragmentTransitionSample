package com.example.fragmenttransitions.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;

import com.example.fragmenttransitions.R;
import com.example.fragmenttransitions.ResourceLoader;

/**
 */
public class DetailChildFragment extends Fragment {

    private static final String ARG_KITTEN_NUMBER = "arg_kitten_number";
    private static final String TAG = DetailChildFragment.class.getSimpleName();
    private View container;
    private ImageView image;
    private TextView tv;

    public static DetailChildFragment newInstance(
            @IntRange(from = 1) int kittenNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_KITTEN_NUMBER, kittenNumber);

        DetailChildFragment fragment = new DetailChildFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_child_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        int kittenNumber = args.containsKey(ARG_KITTEN_NUMBER) ? args.getInt(ARG_KITTEN_NUMBER) : 1;

        Log.v(TAG, "onViewCreated : num = " + kittenNumber);

        container = view;
        image = (ImageView) view.findViewById(R.id.image);
        image.setImageResource(ResourceLoader.load(kittenNumber));

        tv = (TextView) view.findViewById(R.id.detail_title);
        tv.setText(getString(R.string.title_text, kittenNumber));

        ViewCompat.setTransitionName(container, "detail_container_" + kittenNumber);
        ViewCompat.setTransitionName(image, "detail_image_" + kittenNumber);
        ViewCompat.setTransitionName(tv, "detail_title_" + kittenNumber);
    }

    public static class CurrentViewHolder {
        View container;
        ImageView imageView;
        TextView textView;

        CurrentViewHolder(View container,
                          ImageView imageView,
                          TextView textView) {
            this.container = container;
            this.imageView = imageView;
            this.textView = textView;
        }

        public View getContainer() {
            return container;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public CurrentViewHolder getCurrentViewHolder() {
        Log.v(TAG, "container : " + container);
        Log.v(TAG, "image: " + image);
        Log.v(TAG, "tv: " + tv);
        return new CurrentViewHolder(container, image, tv);
    }
}
