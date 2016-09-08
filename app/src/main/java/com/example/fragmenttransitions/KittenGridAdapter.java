package com.example.fragmenttransitions;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Adapts Views containing kittens to RecyclerView cells
 *
 * @author bherbst
 */
public class KittenGridAdapter extends RecyclerView.Adapter<KittenViewHolder> {
    private static final String TAG = KittenGridAdapter.class.getSimpleName();
    private final int mSize;
    private final KittenClickListener mListener;

    // hashcode...
    private final Map<KittenViewHolder, Integer> VHHolder = new HashMap<>();

    @Nullable
    public KittenViewHolder getViewHolder(int position) {
        for (Map.Entry<KittenViewHolder, Integer> entry : VHHolder.entrySet()) {
           if(entry.getValue() == position){
               return entry.getKey();
           }
        }
        return null;
    }

    /**
     * Constructor
     * @param size The number of kittens to show
     * @param listener A listener for kitten click events
     */
    public KittenGridAdapter(int size, KittenClickListener listener) {
        mSize = size;
        mListener = listener;
    }

    @Override
    public KittenViewHolder onCreateViewHolder(ViewGroup container, int position) {
        Log.v(TAG, "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View cell = inflater.inflate(R.layout.grid_item, container, false);

        return new KittenViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(final KittenViewHolder viewHolder, final int position) {
        Log.v(TAG, "onBindViewHolder");
        viewHolder.image.setImageResource(ResourceLoader.load(position));
        viewHolder.title.setText(viewHolder.title.getContext().getString(R.string.title_text, position));

        // It is important that each shared element in the source screen has a unique transition name.
        // For example, we can't just give all the images in our grid the transition name "kittenImage"
        // because then we would have conflicting transition names.
        // By appending "_image" to the position, we can support having multiple shared elements in each
        // grid cell in the future.
        ViewCompat.setTransitionName(viewHolder.image, position + "_image");
        ViewCompat.setTransitionName(viewHolder.title, position + "_title");
        ViewCompat.setTransitionName(viewHolder.container, position + "_container");

        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onKittenClicked(viewHolder, position);
            }
        });
        VHHolder.put(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    @Override
    public void onViewRecycled(KittenViewHolder holder) {
        Log.v(TAG, "onViewRecycled");
    }

    @Override
    public void onViewAttachedToWindow(KittenViewHolder holder) {
        Log.v(TAG, "onViewAttachedToWindow");
    }

    @Override
    public void onViewDetachedFromWindow(KittenViewHolder holder) {
        Log.v(TAG, "onViewDetachedFromWindow");
        VHHolder.remove(holder);
    }

    @Override
    public boolean onFailedToRecycleView(KittenViewHolder holder) {
        Log.v(TAG, "onFailedToRecycleView");
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        Log.v(TAG, "onDetachedFromRecyclerView");
    }
}
