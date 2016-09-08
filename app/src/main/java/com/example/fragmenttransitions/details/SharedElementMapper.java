package com.example.fragmenttransitions.details;

import android.view.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.ArrayMap;

import java.util.List;
import java.util.Map;

/**
 * Mappingは必ず成功するものを前提とすること
 */
class SharedElementMapper extends SharedElementCallback {
    Map<String, View> map = new ArrayMap<>();

    /**
     * {@link android.support.v4.app.BackStackRecord#remapSharedElements(BackStackRecord.TransitionState, Fragment, boolean)} 内で呼ばれている。
     * メソッド名的にここでMappingを変えれば…みたいなことだろうという前提でこれをOverrideしている
     */
    @Override
    public final void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        if (!map.isEmpty()) {
            sharedElements.clear();
            sharedElements.putAll(map);
        }
        super.onMapSharedElements(names, sharedElements);
    }

    public void setRemap(Map<String, View> map) {
        this.map.clear();
        this.map.putAll(map);
    }
}
