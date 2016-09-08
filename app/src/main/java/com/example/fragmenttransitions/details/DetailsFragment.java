package com.example.fragmenttransitions.details;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.fragmenttransitions.GridFragment;
import com.example.fragmenttransitions.KittenViewHolder;
import com.example.fragmenttransitions.R;

/**
 * Display details for a given kitten
 *
 * @author bherbst
 */
public class DetailsFragment extends Fragment {
    private static final String ARG_KITTEN_NUMBER = "argKittenNumber";
    private static final String TAG = DetailsFragment.class.getSimpleName();
    private ViewPager pager;
    private DetailFragmentAdapter adapter;
    private View root;

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
        root = inflater.inflate(R.layout.details_fragment, container, false);

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    performBackPress();
                    return true;
                }
                return false;
            }
        });

        pager = (ViewPager) root.findViewById(R.id.view_pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter = new DetailFragmentAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        Bundle args = getArguments();
        int num = args.containsKey(ARG_KITTEN_NUMBER) ? args.getInt(ARG_KITTEN_NUMBER) : 1;
        pager.setCurrentItem(num);
        return root;
    }

    private void performBackPress() {
        final int currentPos = pager.getCurrentItem();
        Log.v(TAG, "current pager pos: " + currentPos);
        GridFragment target = (GridFragment) getTargetFragment();
        final KittenViewHolder holder = target.getBackToViewHolder(currentPos);
        Log.v(TAG, "target.isInLayout " + target.isInLayout());
        Log.v(TAG, "target.isAdded " + target.isAdded());
        Log.v(TAG, "target.isHidden " + target.isHidden());
        Log.v(TAG, "target.isDetached " + target.isDetached());
        Log.v(TAG, "target.isRemoving " + target.isRemoving());
        Log.v(TAG, "target.isResumed " + target.isResumed());
        Log.v(TAG, "target.isVisible " + target.isVisible());
        if (holder == null || target.isDetached()) {
            Log.v(TAG, "attach");
            getFragmentManager().beginTransaction()
                    .attach(target)
                    .detach(this)
                    .attach(this)
                    .commit();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setExitTransition(new Fade());
            }

            Log.v(TAG, "remove this");
            getFragmentManager()
                    .beginTransaction()
                    .remove(this).commit();

            final DetailChildFragment detailChildFragment = (DetailChildFragment) adapter.instantiateItem(pager, currentPos);
            final DetailChildFragment.CurrentViewHolder pageViewHolder = detailChildFragment.getCurrentViewHolder();


            final AnimatorSet afterSet = new AnimatorSet();
            long duration = 300;
            final PointF cScale = calcScale(pageViewHolder.getContainer(), holder.getContainer());
            final PointF iScale = calcScale(pageViewHolder.getImageView(), holder.getImage());
            final PointF tScale = calcScale(pageViewHolder.getTextView(), holder.getTitle());
            afterSet
                    .play(scaleInAnimator(holder.getContainer(), cScale, duration))
//                    .with(scaleInAnimator(holder.getImage(), iScale, duration))
//                    .with(scaleInAnimator(holder.getTitle(), tScale, duration))
                    .with(translateAnimator(holder.getContainer(),
                            calcRelativeStartPoint(pageViewHolder.container, holder.getContainer(), cScale, getContext()),
                            new PointF(0, 0), duration))
                    .with(translateAnimator(holder.getImage(),
                            calcRelativeStartPoint(pageViewHolder.imageView, holder.getImage(), iScale, getContext()),
                            new PointF(0, 0), duration))
                    .with(translateAnimator(holder.getTitle(),
                            calcRelativeStartPoint(pageViewHolder.textView, holder.getTitle(), tScale, getContext()),
                            new PointF(0, 0), duration));
            afterSet.start();
        }
    }

    private static PointF calcScale(View base, View target) {
        float scaleX = (float) base.getWidth() / (float) target.getWidth();
        float scaleY = (float) base.getHeight() / (float) target.getHeight();
        return new PointF(scaleX, scaleY);
    }

    private static Animator scaleInAnimator(View target, PointF scale, long duration) {
        Log.v(TAG, "scale : " + scale);
        final AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(target, "scaleX", scale.x, 1f))
                .with(ObjectAnimator.ofFloat(target, "scaleY", scale.y, 1f));
        set.setDuration(duration);
        return set;
    }

    private static PointF calcRelativeStartPoint(View start, View end, PointF baseScale, Context context) {
        PointF sp = windowPos(start);
        PointF preEp = windowPos(end);
        float addX = 0f;//(float) (end.getWidth()) * (baseScale.x - 1f) / 2f;
        float addY = 0f;//(float) (end.getHeight()) * (baseScale.y - 1f) / 2f;
        PointF eP = new PointF((preEp.x + addX), (preEp.y + addY));
        Log.v(TAG, "trans calc sp: " + sp + " preEp: " + preEp + " ep: " + eP);
        return new PointF(px2dp(context, sp.x - eP.x), px2dp(context, sp.y - eP.y));
    }

    private static Animator translateAnimator(View animatorTarget, PointF from, PointF to, long duration) {
        Log.v(TAG, "from: " + from + " to: " + to);
        final ObjectAnimator transX = ObjectAnimator
                .ofFloat(animatorTarget, "translationX", from.x, to.x);
        final ObjectAnimator transY = ObjectAnimator
                .ofFloat(animatorTarget, "translationY", from.y, to.y);
        final AnimatorSet set = new AnimatorSet();
        set.play(transX).with(transY);
        set.setDuration(duration);
        return set;
    }

    private static PointF windowPos(View v) {
        int pos[] = new int[2];
        v.getLocationInWindow(pos);
        return new PointF(pos[0], pos[1]);
    }


    private static float px2dp(final Context context, final float dp) {
        return (dp / context.getResources().getDisplayMetrics().density);
    }
}
