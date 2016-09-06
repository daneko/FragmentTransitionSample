package com.example.fragmenttransitions;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;

/**
 * Display details for a given kitten
 *
 * @author bherbst
 */
public class DetailsFragment extends Fragment {
    private static final String ARG_KITTEN_NUMBER = "argKittenNumber";

    /**
     * Create a new DetailsFragment
     */
    public static DetailsFragment newInstance(@IntRange(from = 1) int kittenNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_KITTEN_NUMBER, kittenNumber);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);

        ViewPager pager = (ViewPager) view.findViewById(R.id.view_pager);
        PagerAdapter adapter = new DetailFragmentAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        Bundle args = getArguments();
        int num = args.containsKey(ARG_KITTEN_NUMBER) ? args.getInt(ARG_KITTEN_NUMBER) : 1;
        Log.d("test", "num: " + num);
        pager.setCurrentItem(num);
        return view;
    }

    public static class DetailFragmentAdapter extends FragmentPagerAdapter {

        public DetailFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DetailChildFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return ResourceLoader.RESOUCE_COUNT;
        }
    }


    public static class DetailChildFragment extends Fragment {

        public static DetailChildFragment newInstance(@IntRange(from = 1) int kittenNumber) {
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

            ImageView image = (ImageView) view.findViewById(R.id.image);
            image.setImageResource(ResourceLoader.load(kittenNumber));

            TextView tv = (TextView) view.findViewById(R.id.detail_title);
            tv.setText(getString(R.string.title_text, kittenNumber));

            ViewCompat.setTransitionName(view, "detail_container_" + kittenNumber);
            ViewCompat.setTransitionName(image, "detail_image_" + kittenNumber);
            ViewCompat.setTransitionName(tv, "detail_title_" + kittenNumber);
        }
    }
}
