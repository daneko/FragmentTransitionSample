package com.example.fragmenttransitions;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.fragmenttransitions.details.DetailsFragment;

import java.util.List;
import java.util.Map;

/**
 * Displays a grid of pictures
 *
 * @author bherbst
 */
public class GridFragment extends Fragment implements KittenClickListener {

    private static final String TAG = GridFragment.class.getSimpleName();
    private GridLayoutManager manager;
    private KittenGridAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        adapter = new KittenGridAdapter(ResourceLoader.RESOUCE_COUNT, this);

        manager = new GridLayoutManager(getContext(), 2);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @Nullable
    public KittenViewHolder getBackToViewHolder(int pos) {
        recyclerView.scrollToPosition(pos);
        return adapter.getViewHolder(pos);
    }

    @Override
    public void onKittenClicked(KittenViewHolder holder, int position) {
        DetailsFragment kittenDetails = DetailsFragment.newInstance(position);

        // Note that we need the API version check here because the actual transition classes (e.g. Fade)
        // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
        // ARE available in the support library (though they don't do anything on API < 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = TransitionInflater.from(getActivity()).
                    inflateTransition(R.transition.to_detail_transition);
            kittenDetails.setSharedElementEnterTransition(transition);

//            kittenDetails.setSharedElementReturnTransition(transition);
            kittenDetails.setEnterTransition(new Fade());
            setExitTransition(new Fade());
//            setEnterTransition(new Slide());
//            setSharedElementEnterTransition(transition);
            kittenDetails.setTargetFragment(this, 1234);
        }

        final FragmentTransaction fragmentTransaction = getFragmentManager()
                .beginTransaction()
                .add(R.id.container, kittenDetails)
                .detach(this);

        for (Map.Entry<View, String> entry : mappingGenerator(holder, position).entrySet()) {
            fragmentTransaction.addSharedElement(entry.getKey(), entry.getValue());
        }

        fragmentTransaction.commit();
    }

    private Map<View, String> mappingGenerator(KittenViewHolder holder, int position) {
        Map<View, String> result = new ArrayMap<>();
        result.put(holder.image, "detail_image_" + position);
        result.put(holder.title, "detail_title_" + position);
        result.put(holder.container, "detail_container_" + position);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.v(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.v(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onStart() {
        Log.v(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.v(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.v(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.v(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onViewStateRestored : " + savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v(TAG, "onSaveInstanceState");
        outState.putBundle("test", new Bundle());
        super.onSaveInstanceState(outState);
    }
}
