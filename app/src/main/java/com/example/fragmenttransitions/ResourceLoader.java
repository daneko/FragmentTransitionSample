package com.example.fragmenttransitions;

import android.support.annotation.DrawableRes;

/**
 */
public class ResourceLoader {

    private ResourceLoader() {

    }

    @DrawableRes
    public static int load(int num) {
        switch (num % 6) {
            case 0:
                return R.drawable.placekitten_1;
            case 1:
                return R.drawable.placekitten_2;
            case 2:
                return R.drawable.placekitten_3;
            case 3:
                return R.drawable.placekitten_4;
            case 4:
                return R.drawable.placekitten_5;
            case 5:
                return R.drawable.placekitten_6;
        }
        return R.drawable.placekitten_1;
    }
}
