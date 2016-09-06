package com.example.fragmenttransitions;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Displays a grid of pictures
 *
 * @author bherbst
 */
public class GridFragment extends Fragment implements KittenClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new KittenGridAdapter(ResourceLoader.RESOUCE_COUNT, this));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
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
            kittenDetails.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            kittenDetails.setSharedElementReturnTransition(transition);
        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(holder.image, "detail_image_" + position)
                .addSharedElement(holder.title, "detail_title_" + position)
                .addSharedElement(holder.container, "detail_container_" + position)
                .replace(R.id.container, kittenDetails)
                .addToBackStack(null)
                .commit();
    }
}
